package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * Cylinder class is the basic class representing a cylinder in 3D Cartesian coordinate system
 * Cylinder is a finished tube
 * Cylinder class inherits from tube class
 */
public class Cylinder extends Tube{

    /**
     * cylinder height
     */
    double _height;

    /**
     * Constructor for Cylinder class receiving a Ray and two double values
     * activates the father constructor by sending the ray and radius
     * @param axisRay Ray value for  _axisRay
     * @param radius double value for _radius
     * @param height double value for  _height
     *
     */
    public Cylinder(Ray axisRay, double radius, double height) {
        super(axisRay, radius);
        _height = height;
    }

    /**
     * @return height
     */
    public double getHeight() {
        return _height;
    }

    @Override
    public Vector getNormal(Point3D point) {
        Point3D P0 = _axisRay.getP0();
        Vector v = _axisRay.getDir();

        Vector P0_P = point.subtract(P0); // Vector from P towards P0

        double t = alignZero(P0_P.dotProduct(v)); // distance between the projection of p on the axis and P0
        if (isZero(t)||t==_height) { // The normal at a base will be simply equal to central ray's direction vector
            return v.normalize();
        }
        else
        {
            Point3D O = P0.add(v.scale(t)); // // O represents the center of the section of the cylinder by an orthogonal plane containing the point P
            Vector O_P = point.subtract(O); // // Vector from O towards P
            return O_P.normalize();
        }

    }

    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray) {
        return super.findGeoIntersections(ray);
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
