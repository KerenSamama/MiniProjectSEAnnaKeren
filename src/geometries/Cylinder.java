package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

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
        Point3D P0 = _axisRay.getP0();
        Vector v = _axisRay.getDir();


        Vector P0_P = point.subtract(P0);

        double t = alignZero(P0_P.dotProduct(v));
        if (isZero(t)||t==_height) {
            return v.normalize();
        }
        else
        {
            Point3D O = P0.add(v.scale(t));
            Vector O_P = point.subtract(O);
            return O_P.normalize();
        }

    }

    @Override
    public List<Point3D> findIntersections(Ray ray) {
        return super.findIntersections(ray);
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
