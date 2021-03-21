package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

/**
 * Cylinder class is the basic class representing a cylinder in 3D Cartesian coordinate system
 * Cylinder is a finished tube, cylinder class inherits from tube class
 */
public class Cylinder extends Tube{

    /**
     * cylinder height
     */
    double _height;

    /**
     * Constructor for Cylinder receiving a ray, a value for radius and a value for height and
     * activate the father constructor by sending the ray and radius
     * @param axisRay value for  _axisRay
     * @param radius  value for _radius
     * @param height value for  _height
     *
     */
    public Cylinder(Ray axisRay, double radius, double height) {
        super(axisRay, radius);
        _height = height;
    }

    /**
     *
     * @return height
     */
    public double getHeight() {
        return _height;
    }

    @Override
    public Vector getNormal(Point3D point) {
        return super.getNormal(point);
    }

    @Override
    public String toString() {
        return "Cylinder{" +
                "height=" + _height +
                ", _axisRay=" + _axisRay +
                ", _radius=" + _radius +
                '}';
    }
}
