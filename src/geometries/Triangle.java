package geometries;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;
import java.util.List;
import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * Triangle class is the basic class representing a triangle
 * Triangle class inherits from Polygon class
 * @author Anna and Keren
 */
public class Triangle extends Polygon {

    /**
     * Constructor for Triangle receiving three points and activates the father constructor
     * @param p1 Point3D value for point p1
     * @param p2 Point3D value for point p2
     * @param p3 Point3D value for point p3
     */
    public Triangle(Point3D p1, Point3D p2, Point3D p3) {
        super(p1,p2,p3);
    }




    /**
     * This function helps to find the intersection between a ray and a triangle.
     * Fist, we check intersection between the ray and the plane. Then, we check if the intersection point (if they are) is
     * inside the triangle
     * @param ray
     * @return a list of intersection points of type GeoPoint or null if there are no intersections
     */

    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray) {
        List<GeoPoint> intersections = _plane.findGeoIntersections(ray);
        if (intersections == null) {
            return null;
        }
        Point3D p0 = ray.getP0();  // beginning point of the ray
        Vector v = ray.getDir();   // direction vector of the ray

        Vector v1 = _vertices.get(0).subtract(p0); // vector from p0 to the first vertice
        Vector v2 = _vertices.get(1).subtract(p0); // vector from p0 to the second vertice
        Vector v3 = _vertices.get(2).subtract(p0); // vector from p0 to the third vertice

        Vector n1 = (v1.crossProduct(v2)).normalize();
        Vector n2= (v2.crossProduct(v3)).normalize();
        Vector n3= (v3.crossProduct(v1)).normalize();

        //The point is inside if all 𝒗 ∙ ni have the same sign , if one or more are 0.0 – no intersection
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

        return ((s1 > 0 && s2 > 0 && s3 > 0) || (s1 < 0 && s2 < 0 && s3 < 0)) ?
                List.of(new GeoPoint(this,intersections.get(0).point)) : null;

        }

    }

