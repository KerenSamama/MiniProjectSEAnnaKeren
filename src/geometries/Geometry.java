package geometries;

import primitives.Point3D;
import primitives.Vector;

public interface Geometry extends Intersectable{

    /**
     * Function getNormal who receive a Point3D
     * and returns the normal vector to the geometric shape at this point
     * @param point
     * @return Vector
     */
    Vector getNormal(Point3D point);
}
