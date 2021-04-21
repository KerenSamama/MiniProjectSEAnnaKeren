package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * Triangle class is the basic class representing a triangle in 3D Cartesian coordinate system
 * Triangle class inherits from Polygon class
 */
public class Triangle extends Polygon {

    /**
     * constructor for Triangle receiving three points and activate the father constructor
     * @param p1 value for point p1
     * @param p2 value for point p1
     * @param p3 value for point p1
     */
    public Triangle(Point3D p1, Point3D p2, Point3D p3) {
        super(p1,p2,p3);
    }

    @Override
    public List<Point3D> findIntersections(Ray ray) {
        List<Point3D> intersections = _plane.findIntersections(ray);
        if (intersections == null) {
            return null;
        }
        Point3D p0 = ray.getP0();
        Vector v = ray.getDir();

        Vector v1 = _vertices.get(0).subtract(p0);
        Vector v2 = _vertices.get(1).subtract(p0);
        Vector v3 = _vertices.get(2).subtract(p0);

        Vector n1 = (v1.crossProduct(v2)).normalize();
        Vector n2= (v2.crossProduct(v3)).normalize();
        Vector n3= (v3.crossProduct(v1)).normalize();

        double s1 = v.dotProduct(n1);
        if (alignZero(s1) ==0.0) {
            return null;
        }
        double s2 = v.dotProduct(n2);
        if (alignZero(s1) ==0.0) {
            return null;
        }
        double s3 = v.dotProduct(n3);
        if (alignZero(s1) ==0.0) {
            return null;
        }

        return ((s1 > 0 && s2 > 0 && s3 > 0) || (s1 < 0 && s2 < 0 && s3 < 0)) ? intersections : null;


        }
    }

