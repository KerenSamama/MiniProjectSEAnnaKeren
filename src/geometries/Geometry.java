package geometries;

import primitives.Color;
import primitives.Material;
import primitives.Point3D;
import primitives.Vector;


/**
 * interface Geometry who implements the interface Intersectable
 * with the operation getNormal
 */
public abstract class Geometry implements Intersectable {
    protected Color _emission = Color.BLACK;
    private Material _material = new Material();

    public Color getEmission() {
        return _emission;
    }

    public Geometry  setEmission(Color emission){
        _emission=emission;
        return this;
    }

    public Material getMaterial() {
        return _material;
    }

    public Geometry setMaterial(Material material) {
        _material = material;
        return this;
    }

    /**
     * Function getNormal who receive a Point3D
     * and returns the normal vector to the geometric shape at this point
     *
     * @param point
     * @return Vector
     */
    public abstract Vector getNormal(Point3D point);
}
