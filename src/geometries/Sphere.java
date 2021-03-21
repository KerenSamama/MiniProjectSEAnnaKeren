package geometries;

import primitives.Point3D;
import primitives.Vector;

/**
 * Sphere class is the basic class representing a sphere in 3D Cartesian coordinate
 * system
 */
public class Sphere implements Geometry{

    /**
     * sphere center
     */
    Point3D _center;

    /**
     * sphere radius
     */
    double _radius;

    /**
     * constructor for Sphere receiving a point center and a double value for radius
     * @param center Point3D value for _center
     * @param radius double value for _radius
     */
    public Sphere(Point3D center, double radius) {
        if(radius==0d){
            throw new IllegalArgumentException("Sphere radius cannot be 0");
        }
        _center = center;
        _radius = radius;
    }

    /**
     * function get
     * @return _center
     */
    public Point3D getCenter() {
        return _center;
    }

    /**
     * function get
     * @return _radius
     */
    public double getRadius() {
        return _radius;
    }

    @Override
    public String toString() {
        return "Sphere{" +
                "_center=" + _center +
                ", _radius=" + _radius +
                '}';
    }

    @Override
    public Vector getNormal(Point3D point) {

        if(point.equals(_center))
        {
            throw new IllegalArgumentException("Point cannot be equals to the center of the sphere");
        }
        Vector O_P=point.subtract(_center);
        return O_P.normalize();
    }
}
