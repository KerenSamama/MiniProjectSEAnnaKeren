package renderer;

import primitives.Color;
import primitives.Point3D;
import primitives.Ray;
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
        List<Point3D> intersections = _scene.geometries.findIntersections(ray);
        if (intersections == null) return _scene.backGroundColor;
        Point3D closestPoint = ray.getClosestPoint(intersections);
        return calcColor(closestPoint);
    }

    /**
     * Calculate a color on a point
     * @param closestPoint a point3D
     * @return the intensity of the ambientLight
     */
    private Color calcColor(Point3D closestPoint) {
        return _scene.ambientLight.getIntensity();

    }

}
