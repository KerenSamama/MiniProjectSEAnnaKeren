package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * Plane class is the basic class representing a plane in 3D Cartesian coordinate system
 */
public class Plane extends Geometry {

    /**
     * Reference point of the plane
     */
    final Point3D _q0;

    /**
     * Normal vector of the plane
     */
    final Vector _normal;

    /**
     * Constructor for Plane receiving three points and who calculates the normal of the plane
     *
     * @param p1 Point3D value for p1
     * @param p2 Point3D value for p2
     * @param p3 Point3D value for p3
     */
    public Plane(Point3D p1, Point3D p2, Point3D p3) {
        _q0 = p1; // reference point
        Vector v1 = p2.subtract(p1);// vector from p1 towards p2
        Vector v2 = p3.subtract(p1);// vector from p1 towards p3
        Vector n = v1.crossProduct(v2);
        _normal = n.normalize();// normal vector as a unit vector

    }

    /**
     * Constructor for Plane receiving one point and the normal vector
     *
     * @param point  of type Point3D : reference point of the plane
     * @param normal of type Vector : normal vector
     */
    public Plane(Point3D point, Vector normal) {
        _q0 = point;
        _normal = normal;
    }


    /**
     * Function get
     *
     * @return _point
     */
    public Point3D getQ0() {
        return _q0;
    }

    /**
     * Function get
     *
     * @return _normal
     * @deprecated use {@link Plane#getNormal(Point3D)} with null parameter.
     */
    @Deprecated
    public Vector getNormal() {
        return _normal;
    }

    /**
     * Implementation of getNormal from Geometry
     *
     * @param point of type Point3D
     * @return Vector
     */
    @Override
    public Vector getNormal(Point3D point) {
        return _normal;
    }


    /**
     * This function helps to find the intersection between a ray and a plane.
     *
     * @param ray of type Ray
     * @return a list of intersection points of type GeoPoint with the ray or null if they are no intersections.
     */
    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {
        Point3D p0 = ray.getP0();  // beginning point of the ray
        Vector v = ray.getDir();   // direction vector of the ray
        //Point3D q0 = _q0;          // reference point of the plane
        Vector n = _normal;
        // normal vector of the plane
        if (_q0.equals(p0)) {
            return null;
        }
        if (isZero(n.dotProduct(v))) {
            return null;
        }

        double t = n.dotProduct(_q0.subtract(p0)) / n.dotProduct(v);

        if (t > 0 && alignZero(t - maxDistance) <= 0) {
            return List.of(new GeoPoint(this, ray.getPoint(t)));
        }
        return null;




        /*Vector p0q0;    // Vector from p0 to q0
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

        if (t > 0 && alignZero(t - maxDistance) <= 0)
            return List.of(new GeoPoint(this, ray.getPoint(t)));


         return null;
*/
        //return t <= 0 ? null : List.of(new GeoPoint(this, ray.getPoint(t) )); // we take only t>0
    }


    @Override
    public String toString() {
        return "Plane{" +
                "_point=" + _q0 +
                ", _normal=" + _normal +
                '}';
    }

}


