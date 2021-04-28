package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * Plane class is the basic class representing a plane in 3D Cartesian coordinate
 * system
 */
public class Plane implements Geometry{

    /**
     *  reference point of the plane
     */
    final Point3D _q0;

    /**
     * normal vector of the plane
     */
    final Vector _normal;

    /**
     * constructor for Plane receiving three points
     * @param p1 value for p1 Point3D
     * @param p2 value for p2 Point3D
     * @param p3 value for p3 Point3D
     */
    public Plane(Point3D p1, Point3D p2, Point3D p3) {
        _q0 = p1;
        Vector v1=p2.subtract(p1);// vector from p1 towards p2
        Vector v2=p3.subtract(p1);// vector from p1 towards p3
        Vector n= v1.crossProduct(v2);
        _normal = n.normalize();//

    }

    /**
     * constructor for Plane receiving one point and the normal vector
     * @param point reference point of the plane
     * @param normal vector
     */
    public Plane(Point3D point, Vector normal) {
        _q0 = point;
        _normal = normal;
    }


    /**
     * function get
     * @return _point
     */
    public Point3D getQ0() {
        return _q0;
    }

    /**
     * function get
     * @deprecated  use {@link Plane#getNormal(Point3D)} with null parameter.
     * @return _normal
     */
    @Deprecated
    public Vector getNormal() {
        return _normal;
    }

    /**
     * implementation of Geometry getNormal
     * @param point
     * @return
     */
    @Override
    public Vector getNormal(Point3D point) {
        return _normal;
    }

    @Override
    public String toString() {
        return "Plane{" +
                "_point=" + _q0 +
                ", _normal=" + _normal +
                '}';
    }

    /**
     * This function helps to find the intersection between a ray and a plane.
     * @param ray : the function findIntersections receives a ray and
     * @return a list of intersection points with the ray or null if they are no intersections.
     */
    @Override
    public List<Point3D> findIntersections(Ray ray) {

        Point3D p0 = ray.getP0();  // beginning point of the ray
        Vector v = ray.getDir();   // direction vector of the ray
        Point3D q0 = _q0;          // reference point of the plane
        Vector n= _normal;         // normal vector of the plane

        Vector p0q0;    // Vector from p0 to q0
        try {
            p0q0 = q0.subtract(p0);
        } catch (IllegalArgumentException e) {
            return null;  // ray starts at the reference point of the plane q0 - no intersections
        }

        double nv = n.dotProduct(v);
        if (isZero(nv)) { // ray is parallel to the plane - no intersections
            return null;
        }

        double t = alignZero(n.dotProduct(p0q0) / nv);

        return t <= 0 ? null : List.of(ray.getPoint(t)); // we take only t>0
    }
}


