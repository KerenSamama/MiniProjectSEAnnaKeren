package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.*;

/**
 * Tube class is the basic class representing a tube in 3D Cartesian coordinate system
 */
public class Tube extends Geometry {

    Ray _axisRay;
    double _radius;

    /**
     * Primary constructor for Tube class
     * @param axisRay Ray
     * @param radius  double
     */
    public Tube(Ray axisRay, double radius) {
        if (radius == 0d) {
            throw new IllegalArgumentException("Tube radius cannot be 0");
        }
        _axisRay = axisRay;
        _radius = radius;
    }

    /**
     * Function get
     * @return _axisRay
     */
    public Ray getAxisRay() {
        return _axisRay;
    }

    /**
     * Function get
     * @return _radius
     */
    public double getRadius() {
        return _radius;
    }



    /**
     * Implementation of getNormal from Geometry
     * @param p of type Point3D
     * @return Vector : the normal Vector at this point
     */
    @Override
    public Vector getNormal(Point3D p) {
        Point3D P0 = _axisRay.getP0();
        Vector v = _axisRay.getDir();

        Vector P0_P = p.subtract(P0); // Vector from P towards P0

        double t = alignZero(P0_P.dotProduct(v)); // distance between the projection of p on the axis and P0
        if (isZero(t)) {
            return P0_P.normalize();
        }

        Point3D O = P0.add(v.scale(t)); // O represents the center of the section of the tube by an orthogonal plane containing the point P
        Vector O_P = p.subtract(O); // Vector from O towards P

        return O_P.normalize();
    }

    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {
        return null;
    }

    @Override
    public void setBoundingBox() {

    }

    @Override
    public String toString() {
        return "Tube{" +
                "_axisRay=" + _axisRay +
                ", _radius=" + _radius +
                '}';
    }
}
