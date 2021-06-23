package geometries;

import primitives.Color;
import primitives.Material;
import primitives.Point3D;
import primitives.Vector;


/**
 * Abstract class Geometry for any geometric body
 * who implements the interface Intersectable
 */
public abstract class Geometry extends Intersectable {
    protected Color _emission = Color.BLACK; // field for representing geometric body's color
    private Material _material = new Material(); // field for representing geometric body's material

    /**
     * Function Getter
     * @return _emission
     */
    public Color getEmission() {
        return _emission;
    }

    /**
     * Setter method as in Builder design template
     * @param emission of type Color : geometric body's color
     * @return the Geometry object itself for chaining calls
     */
    public Geometry  setEmission(Color emission){
        _emission=emission;
        return this;
    }

    public Material getMaterial() {
        return _material;
    }

    /**
     * Setter method as in Builder design template
     * @param material of type Material : geometric body's material
     * @return the Geometry object itself for chaining calls
     */
    public Geometry setMaterial(Material material) {
        _material = material;
        return this;
    }

    /**
     * Function getNormal who receives a Point3D
     * and returns the normal vector to the geometric shape at this point
     * @param point of type Point3D
     * @return Vector
     */
    public abstract Vector getNormal(Point3D point);
}
