package renderer;

import static geometries.Intersectable.GeoPoint;
import static primitives.Util.alignZero;

import elements.LightSource;
import primitives.Color;
import primitives.Material;
import primitives.Ray;
import primitives.Vector;
import scene.Scene;

import java.util.List;

/**
 * BasicRayTracer class inherits from the abstract class RayTracerBase
 */
public class RayTracerBasic extends RayTracerBase {

    /**
     * Constructor for BasicRayTracer receiving a scene object and activating the father constructor
     *
     * @param scene
     */
    public RayTracerBasic(Scene scene) {
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
        if (intersections == null) return _scene.backGroundColor;
        GeoPoint closestPoint = ray.findClosestGeoPoint(intersections);
        return calcColor(closestPoint, ray);
    }

    /**
     * Calculate a color on a point. For that, this method adds the intensity of the ambientLight,
     * the object's color and calcLocalEffects according tp the Phong Reflectance Model
     *
     * @param intersection a GeoPoint
     * @param ray          of type Ray
     * @return the point's color of type Color
     */
    private Color calcColor(GeoPoint intersection, Ray ray) {
        Color emissionColor = intersection.geometry.getEmission();
        Color basicColor = emissionColor.add(_scene.ambientLight.getIntensity());
        return basicColor.add(calcLocalEffects(intersection, ray));
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
                Color lightIntensity = lightSource.getIntensity(intersection.point);
                color = color.add(calcDiffusive(kd, l, n, lightIntensity),
                        calcSpecular(ks, l, n, v, nShininess, lightIntensity));
            }
        }
        return color;

    }

    /**
     * Method calcSpecular to calculate the specular light
     * @param ks specular coefficient
     * @param l vector from the light's position Pl to the intersection point
     * @param n geometry's normal
     * @param v ray's direction vector
     * @param nShininess the object’s shininess
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
     * @param kd diffuse coefficient
     * @param l vector from the light's position Pl to the intersection point
     * @param n geometry's normal
     * @param lightIntensity light source's intensity
     * @return an object of type Color for diffusion light
     */
    private Color calcDiffusive(double kd, Vector l, Vector n, Color lightIntensity) {
        double ln = Math.abs(l.dotProduct(n));
        return lightIntensity.scale(kd * ln);
    }

}
