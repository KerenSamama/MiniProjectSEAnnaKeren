package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;


/**
 * RayTracerBase is an abstract class who is useful to scan a ray.
 */
public abstract class RayTracerBase {

    protected final Scene _scene; // a field of Scene


    /**
     * Constructor for RayTracerBase who receives an object of a scene
     * @param scene of type Scene
     */
    public RayTracerBase(Scene scene) {
        _scene = scene;
    }

    /**
     * Function traceRay receives a ray and returns the color on the closest point intersection with the start of the ray.
     * @param ray of type Ray
     * @return new Color Object
     */
    public abstract Color traceRay(Ray ray);
}
