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
     * @param ray of type Ray
     * @return the point's color of type Color
     */
    private Color calcColor(GeoPoint closestPoint, Ray ray) {
        return calcColor(closestPoint, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K)
                .add(_scene.ambientLight.getIntensity());
    }

    /**
     * Calculate a color on a point. For that, this method adds the intensity of the ambientLight,
     * the object's color and calcLocalEffects according tp the Phong Reflectance Model
     * RECURSIVE because global effect call calcolor
     *
     * @param intersection
     * @param ray
     * @param level
     * @param k
     * @return
     */
    private Color calcColor(GeoPoint intersection, Ray ray, int level, double k) {
        Color color = intersection._geometry.getEmission();
        color = color.add(calcLocalEffects(intersection, ray, k));
        return 1 == level ? color : color.add(calcGlobalEffects(intersection, ray.getDir(), level, k));

    }

    /**
     * @param geoPoint
     * @param v
     * @param level
     * @param k
     * @return
     */
    private Color calcGlobalEffects(GeoPoint geoPoint, Vector v, int level, double k) {
        Color color = Color.BLACK;
        Vector n = geoPoint._geometry.getNormal(geoPoint._point);
        Material material = geoPoint._geometry.getMaterial();
        double kkr = k * material.Kr;

        if (kkr > MIN_CALC_COLOR_K) {
            color = calcGlobalEffects(constructReflectedRay(geoPoint, v), level, material.Kr, kkr);
        }

        double kkt = k * material.Kt;
        if (kkt > MIN_CALC_COLOR_K) {
            color = color.add(calcGlobalEffects(constructRefractedRay(geoPoint, v), level, material.Kt, kkt));

        }
        return color;
    }

    private Color calcGlobalEffects(Ray ray, int level, double kx, double kkx) {
        GeoPoint gp = ray.findClosestGeoPoint(_scene.geometries.findGeoIntersections(ray));
        return (gp == null ? Color.BLACK.add(_scene.backGroundColor).scale(kx) : calcColor(gp, ray, level - 1, kkx)).scale(kkx);

    }



      /*  Color color = Color.BLACK;
        Material material = geoPoint._geometry.getMaterial();

        double kr = material.Kr;
        double kkr = k * kr;
        if (kkr > MIN_CALC_COLOR_K) {
            Ray reflectedRay = constructReflectedRay(geoPoint,ray);
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
*/


    /**
     * Method constructRefractedRay for calculate the RefractedRay
     *
     * @param geoPoint
     * @param v
     * @return
     */
    private Ray constructRefractedRay(GeoPoint geoPoint, Vector v) {
        Vector n = geoPoint._geometry.getNormal(geoPoint._point);
        return new Ray(geoPoint._point, v, n);

    }

    /**
     * Method constructReflectedRay for calculate the reflectedRay
     *
     * @param geoPoint
     * @param v
     * @return
     */
    private Ray constructReflectedRay(GeoPoint geoPoint, Vector v) {
        Vector n = geoPoint._geometry.getNormal(geoPoint._point);
        Vector r = v.subtract(n.scale(2 * v.dotProduct(n))).normalize();
        return new Ray(geoPoint._point, r, n);
    }

    /**
     * Method calcLocalEffects for adding diffusion/specular calculation
     *
     * @param intersection
     * @param ray
     * @return
     */
    private Color calcLocalEffects(GeoPoint intersection, Ray ray, double k) {
        Vector v = ray.getDir();
        Vector n = intersection._geometry.getNormal(intersection._point);
        double nv = alignZero(n.dotProduct(v));
        if (nv == 0) {
            return Color.BLACK;
        }
        Material material = intersection._geometry.getMaterial();
        int nShininess = material.nShininess;
        double kd = material.Kd;
        double ks = material.Ks;
        Color color = Color.BLACK;
        for (LightSource lightSource : _scene.lights) {
            Vector l = lightSource.getL(intersection._point);
            double nl = alignZero(n.dotProduct(l));
            if (nl * nv > 0) { // sign(nl) == sing(nv)
               // if (unshaded(lightSource, intersection)) {
                double ktr = transparency(lightSource, l, n,intersection);
                if (ktr * k > MIN_CALC_COLOR_K) {
                    Color lightIntensity = lightSource.getIntensity(intersection._point).scale(kd);
                    color = color.add(calcDiffusive(kd, l, n, lightIntensity),
                            calcSpecular(ks, l, n, v, nShininess, lightIntensity));
                }
            }
        }
        return color;

    }

    /**
     * @param light
     * @param l
     * @param n
     * @param geopoint
     * @return
     */
    @Deprecated
    /*private boolean unshaded(LightSource lightSource, GeoPoint geoPoint) {
        Point3D point = geoPoint._point;
        Vector n = geoPoint._geometry.getNormal(point);
        //Ray from point toward light direction offset by delta
        Ray lightRay = new Ray(point, lightSource, n, DELTA);
       //
        List<GeoPoint> intersection = _scene.geometries
                .findGeoIntersections(lightRay, lightSource.getDistance(point));
        return intersection == null;
    }*/

    private boolean unshaded(LightSource light, Vector l, Vector n, GeoPoint geopoint) {
        Vector lightDirection = l.scale(-1); // from point to light source
        Ray lightRay = new Ray(geopoint._point, lightDirection, n); // refactored ray head move
        List<GeoPoint> intersections = _scene.geometries.findGeoIntersections(lightRay);
        if (intersections == null) return true;
        double lightDistance = light.getDistance(geopoint._point);
        for (GeoPoint gp : intersections) {
            if (alignZero(gp._point.distance(geopoint._point) - lightDistance) <= 0 &&
                    gp._geometry.getMaterial().Kt == 0)
                return false;
        }
        return true;
    }

    private double transparency(LightSource light, Vector l, Vector n, GeoPoint geopoint) {
        Vector lightDirection = l.scale(-1); // from point to light source
        Point3D point = geopoint._point;
        Ray lightRay = new Ray(point, lightDirection, n);
        double lightDistance = light.getDistance(point);
        var intersections = _scene.geometries.findGeoIntersections(lightRay, lightDistance);
        if (intersections == null) return 1.0;
        double ktr = 1.0;
        for (GeoPoint gp : intersections) {

            ktr *= gp._geometry.getMaterial().getKd();
            if (ktr < MIN_CALC_COLOR_K) return 0.0;
        }
        return ktr;
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
