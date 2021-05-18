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
public class BasicRayTracer extends RayTracerBase {

    /**
     * Constructor for BasicRayTracer receiving a scene object and activating the father constructor
     * @param scene
     */
    public BasicRayTracer(Scene scene) {
        super(scene);
    }


    /**
     * Function traceRay finds the intersection points between a ray and the 3D model. If there are, calculates the closest point to the
     * start of the ray and returns the color on this point. If there are no intersections, returns the background color of the scene.
     * @param ray
     * @return Color
     */
    @Override
    public Color traceRay(Ray ray) {
        List<GeoPoint> intersections = _scene.geometries.findGeoIntersections(ray);
        if (intersections == null) return _scene.backGroundColor;
        GeoPoint closestPoint = ray.getClosestGeoPoint(intersections);
        return calcColor(closestPoint,ray);
    }

    /**
     * Calculate a color on a point
     * @param closestPoint a point3D
     * @param ray
     * @return the intensity of the ambientLight
     */
    private Color calcColor(GeoPoint closestPoint, Ray ray) {
        Color emissionColor = closestPoint.geometry.getEmission();
        Color basicColor = emissionColor.add(_scene.ambientLight.getIntensity());
        return basicColor.add(calcLocalEffects(closestPoint,ray));


    }

    private Color calcLocalEffects(GeoPoint intersection, Ray ray) {
        Vector v = ray.getDir ();
        Vector n = intersection.geometry.getNormal();
        double nv = alignZero(n.dotProduct(v);
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

    private Color calcSpecular(double ks, Vector l, Vector n, Vector v, int nShininess, Color lightIntensity) {
    }

    private Color calcDiffusive(double kd, Vector l, Vector n, Color lightIntensity) {
    }

}
