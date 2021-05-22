package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;

/**
 * Sphere class is the basic class representing a sphere in 3D cartesian coordinate system
 */
public class Sphere extends Geometry {

    /**
     * sphere center
     */
    Point3D _center;

    /**
     * sphere radius
     */
    double _radius;

    /**
     * Constructor for Sphere class receiving a Point3D value for the center and a double value for the radius
     * @param center of type Point3D
     * @param radius of type double value
     */
    public Sphere(Point3D center, double radius) {
        if (radius == 0d) {
            throw new IllegalArgumentException("Sphere radius cannot be 0");
        }
        _center = center;
        _radius = radius;
    }

    /**
     * Function get
     *
     * @return _center
     */
    public Point3D getCenter() {
        return _center;
    }

    /**
     * Function get
     *
     * @return _radius
     */
    public double getRadius() {
        return _radius;
    }

    /**
     * Implementation of getNormal from Geometry
     * @param point of type Point3D
     * @return Vector : the normal Vector at this point
     */
    @Override
    public Vector getNormal(Point3D point) {
        if (point.equals(_center)) {
            throw new IllegalArgumentException("Point cannot be equals to the center of the sphere to calculate the normal");
        }
        Vector O_P = point.subtract(_center);
        return O_P.normalize();
    }



    /**
     * This function helps to find the intersections between a ray and a sphere.
     * @param ray of type Ray
     * @return A list of intersection points of type GeoPoint or null if there are no intersections
     */
    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray) {
        Point3D p0 = ray.getP0(); // beginning point of the ray
        Vector v = ray.getDir();  // vector direction of the ray
        Point3D O = _center;      // center of the sphere

        Vector u;
        try {
            u = O.subtract(p0); // vector from p0 to the center
        } catch (IllegalArgumentException e) {  // if p0=center, the vector u is the vector ZERO so exception
            return List.of(new GeoPoint(this, ray.getPoint(_radius))); // t= radius of the sphere
        }
        double tm = v.dotProduct(u); // tm is the projection of u on v
        double d = Math.sqrt(u.lengthSquared() - tm * tm); // calculate the side d on the triangle d-u-tm

        if (d >= _radius) { // there are no intersections
            return null;
        }

        double th = Math.sqrt(_radius * _radius - d * d); // calculate the side th on the triangle r-d-th
        double t1 = tm - th; // the distance between p0 and the first intersection point
        double t2 = tm + th;  // the distance between p0 and the second intersection point

        if (t1 > 0 && t2 > 0) { // there are two intersections points
            return (List.of(new GeoPoint(this, ray.getPoint(t1)), new GeoPoint(this, ray.getPoint(t2))));
        }

        if (t1 > 0) { // there are only one intersection point
            return (List.of(new GeoPoint(this, ray.getPoint(t1))));
        }

        if (t2 > 0) { // // there are only one intersection point
            return (List.of(new GeoPoint(this, ray.getPoint(t2))));
        }

        return null;

    }


    @Override
    public String toString() {
        return "Sphere{" +
                "_center=" + _center +
                ", _radius=" + _radius +
                '}';
    }
}
