package geometries;

import primitives.Point3D;

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


}
