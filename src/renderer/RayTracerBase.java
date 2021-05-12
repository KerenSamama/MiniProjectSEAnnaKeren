package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;


/**
 * RayTracerBase class is useful to scan a ray.
 */
public abstract class RayTracerBase {

    protected final Scene _scene;


    /**
     * Constructor for RayTracerBase who receives an object of a scene
     * @param scene
     */
    public RayTracerBase(Scene scene) {
        _scene = scene;
    }

    /**
     * Function traceRay receives a ray and returns the color on the closest point intersection with the start of the ray.
     * @param ray
     * @return new Color Object
     */
    public abstract Color traceRay(Ray ray);
}
