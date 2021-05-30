package renderer;

import static geometries.Intersectable.GeoPoint;
import static primitives.Util.alignZero;

import elements.LightSource;
import primitives.*;
import scene.Scene;

import java.util.List;

/**
 * BasicRayTracer class inherits from the abstract class RayTracerBase
 */
public class BasicRayTracer extends RayTracerBase {
    private static final double DELTA = 0.1;
    private static final double INITIAL_K = 1.0;
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final double MIN_CALC_COLOR_K = 0.001;


    /**
     * Constructor for BasicRayTracer receiving a scene object and activating the father constructor
     *
     * @param scene
     */
    public BasicRayTracer(Scene scene) {
        super(scene);
    }


    /**
     * Function traceRay finds the intersection points between a ray and the 3D model. If there are, calculates the closest point to the
     * start of the ray and returns the color on this point. If there are no intersections, returns the background color of the scene.
     *
     * @param ray
     * @return Color
     */
    @Override
    public Color traceRay(Ray ray) {
        List<GeoPoint> intersections = _scene.geometries.findGeoIntersections(ray);
        if (intersections != null) {
            GeoPoint closestPoint = ray.findClosestGeoPoint(intersections);
            return calcColor(closestPoint, ray);
        }
        return Color.BLACK;
    }

    /**
     * Calculate a color on a point. For that, this method adds the intensity of the ambientLight,
     * the object's color and calcLocalEffects according tp the Phong Reflectance Model
     *
     * @param intersection a GeoPoint
     * @param ray          of type Ray
     * @return the point's color of type Color
     */

    /**   IL A MIS EN EARA
     private Color calcColor(GeoPoint intersection, Ray ray) {
     Color emissionColor = intersection.geometry.getEmission();
     Color basicColor = emissionColor.add(_scene.ambientLight.getIntensity());
     return basicColor.add(calcLocalEffects(intersection, ray));
     }
     **/

    /**
     * Calculate a color on a point. For that, this method adds the intensity of the ambientLight,
     * the object's color and calcLocalEffects according tp the Phong Reflectance Model
     *
     *
     * @param ray          of type Ray
     * @return the point's color of type Color
     */
    private Color calcColor(GeoPoint geoPoint, Ray ray) {
        return calcColor(geoPoint, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K)
                .add(_scene.ambientLight.getIntensity());
    }

    private Color calcColor(GeoPoint geoPoint, Ray ray, int level, double k) {
        Color color = geoPoint.geometry.getEmission();
        color = color.add(calcLocalEffects(geoPoint, ray));
        return 1 == level ? color : color.add(calcGlobalEffects(geoPoint, ray, level, k));

    }

    private Color calcGlobalEffects(GeoPoint geoPoint, Ray ray, int level, double k) {
        Color color = Color.BLACK;
        Material material = geoPoint.geometry.getMaterial();

        double kr = material.Kr;
        double kkr = k * kr;
        if (kkr > MIN_CALC_COLOR_K) {
            Ray reflectedRay = constructReflectedRay(geoPoint, ray);
            List<GeoPoint> geoPointList1 = _scene.geometries.findGeoIntersections(reflectedRay);
            GeoPoint reflectedPoint = reflectedRay.findClosestGeoPoint(geoPointList1);
            if (reflectedPoint != null) {


                color = color.add(calcColor(reflectedPoint, reflectedRay, level - 1, kkr)).scale(kr);
            }
        }
        double kt = material.Kt;
        double kkt = k * kt;
        if (kkr > MIN_CALC_COLOR_K) {
            Ray refractedRay = constructRefractedRay(geoPoint, ray);
            List<GeoPoint> geoPointList2 = _scene.geometries.findGeoIntersections(refractedRay);
            GeoPoint refractedPoint = refractedRay.findClosestGeoPoint(geoPointList2);
            if (refractedPoint != null) {


                color = color.add(calcColor(refractedPoint, refractedRay, level - 1, kkt)).scale(kt);
            }

        }

        return color;
    }

    private Ray constructRefractedRay(GeoPoint geoPoint, Ray ray) {
        return new Ray(geoPoint.point, ray.getDir());

    }

    private Ray constructReflectedRay(GeoPoint geoPoint, Ray ray) {
        Vector n = geoPoint.geometry.getNormal(geoPoint.point);
        Vector v = ray.getDir();
        Vector r = v.subtract(n.scale(2 * v.dotProduct(n)));
        return new Ray(geoPoint.point, r);
    }

    /**
     * Method calcLocalEffects for adding diffusion/specular calculation
     *
     * @param intersection
     * @param ray
     * @return
     */
    private Color calcLocalEffects(GeoPoint intersection, Ray ray) {
        Vector v = ray.getDir();
        Vector n = intersection.geometry.getNormal(intersection.point);
        double nv = alignZero(n.dotProduct(v));
        if (nv == 0) {
            return Color.BLACK;
        }
        Material material = intersection.geometry.getMaterial();
        int nShininess = material.nShininess;
        double kd = material.Kd;
        double ks = material.Ks;
        Color color = Color.BLACK;
        for (LightSource lightSource : _scene.lights) {
            Vector l = lightSource.getL(intersection.point);
            double nl = alignZero(n.dotProduct(l));
            if (nl * nv > 0) { // sign(nl) == sing(nv)
                if (unshaded(lightSource, intersection)) {
                    Color lightIntensity = lightSource.getIntensity(intersection.point);
                    color = color.add(calcDiffusive(kd, l, n, lightIntensity),
                            calcSpecular(ks, l, n, v, nShininess, lightIntensity));
                }
            }
        }
        return color;

    }

    private boolean unshaded(LightSource lightSource, GeoPoint geoPoint) {
        Point3D point = geoPoint.point;
        Vector n = geoPoint.geometry.getNormal(point);
        Ray lightRay = new Ray(point, lightSource, n, DELTA);
        List<GeoPoint> intersection = _scene.geometries
                .findGeoIntersections(lightRay, lightSource.getDistance(point));
        return intersection == null;
    }

    /**
     * Method calcSpecular to calculate the specular light
     *
     * @param ks             specular coefficient
     * @param l              vector from the light's position Pl to the intersection point
     * @param n              geometry's normal
     * @param v              ray's direction vector
     * @param nShininess     the object’s shininess
     * @param lightIntensity light source's intensity
     * @return an object o type Color for specular light
     */
    private Color calcSpecular(double ks, Vector l, Vector n, Vector v, int nShininess, Color lightIntensity) {
        Vector r = l.subtract(n.scale(l.dotProduct(n) * 2));
        double vrMinus = v.scale(-1).dotProduct(r);
        double vrn = Math.pow(vrMinus, nShininess); // Math.pow(Math.max(0,minusVr),nShininess)
        return lightIntensity.scale(ks * vrn);

    }


    /**
     * Method calcDiffusive to calculate the diffusion light
     *
     * @param kd             diffuse coefficient
     * @param l              vector from the light's position Pl to the intersection point
     * @param n              geometry's normal
     * @param lightIntensity light source's intensity
     * @return an object of type Color for diffusion light
     */
    private Color calcDiffusive(double kd, Vector l, Vector n, Color lightIntensity) {
        double ln = Math.abs(l.dotProduct(n));
        return lightIntensity.scale(kd * ln);
    }

}
