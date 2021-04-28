package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlaneTest {

    @Test
        // ============ Equivalence Partitions Tests ==============

        // TCO1 EP : Any Point on the plane, there is a simple single test here
    void testGetNormal() {
        Point3D p1 = new Point3D(0, 1, 1);
        Point3D p2 = new Point3D(1, 0, 1);
        Point3D p3 = new Point3D(1, 1, 0);

        Plane pl1 = new Plane(p1, p2, p3);
        Vector v1 = p2.subtract(p1);
        Vector v2 = p3.subtract(p1);
        Vector cp = v1.crossProduct(v2);
        Vector expectedNormal = cp.normalize();
        Vector normal1 = pl1.getNormal(p1);
        Vector normal2 = pl1.getNormal(null);
        assertEquals(normal1, expectedNormal, "ERROR: Not the same : getNormal() wrong result");
        assertEquals(normal2, expectedNormal, "ERROR: Not the same : getNormal() wrong result");

    }

    @Test
    public void testConstructor() {
        // TC10: First point = Second point
        assertThrows(

                IllegalArgumentException.class,
                () -> new Plane(new Point3D(0, 1, 1), new Point3D(0, 1, 1), new Point3D(1, 1, 1)), "ERROR : first and second point are the same");

        // TC11: Collocated points
        assertThrows(

                IllegalArgumentException.class,
                () -> new Plane(new Point3D(1, 0, 0), new Point3D(2, 0, 0), new Point3D(3, 0, 0)), "ERROR : all of points are on the same line");
    }


    /**
     * Test method for {@link geometries.Plane#findIntersections(primitives.Ray)}.
     */
    @Test
    void testFindIntersections() {

        Plane plane = new Plane(new Point3D(0.5, 0.5, 0), new Vector(-1, -1, 0));

        // ============ Equivalence Partitions Tests ==============

        // TC01: Ray intersects the plane
        Ray ray1 = new Ray(new Point3D(2, 2, 0), new Vector(-1, -2, 0));
        List<Point3D> intersectionPoints1 = plane.findIntersections(ray1);
        assertEquals(1, intersectionPoints1.size(), "ERROR, Ray must intersects the plane");


        // TC02: Ray does not intersect the plane
        Ray ray2 = new Ray(new Point3D(-1, -2, 0), new Vector(-1, -2, 0));
        List<Point3D> intersectionPoints2 = plane.findIntersections(ray2);
        assertNull(intersectionPoints2, "ERROR, Ray does not intersect the plane");


        // =============== Boundary Values Tests ==================

        // **** Group:  Ray is parallel to the plane
        // TC11: The ray included in the plane
        Ray ray3 = new Ray(new Point3D(1, 0, 0), new Vector(0, 0, 2));
        List intersectionPoints3 = plane.findIntersections(ray3);
        assertNull(intersectionPoints3, "ERROR, Ray does not intersect the plane");

        // TC12: The ray not included in the plane
        Ray ray4 = new Ray(new Point3D(-1, 1, 0), new Vector(-2, 2, 0));
        List intersectionPoints4 = plane.findIntersections(ray4);
        assertNull(intersectionPoints4, "ERROR, Ray does not intersect the plane");


        // **** Group:  Ray is orthogonal to the plane

        // TC13: P0 before the plane
        Ray ray7 = new Ray(new Point3D(0.75, 0.75, 0), new Vector(-1, -1, 0));
        List intersectionPoints7 = plane.findIntersections(ray7);
        assertEquals(1, intersectionPoints7.size(), "ERROR, Ray intersect the plane");


        // TC14: P0 in the plane
        Ray ray6 = new Ray(new Point3D(0.5, 0.5, 0), new Vector(-1, -1, 0));
        List intersectionPoints6 = plane.findIntersections(ray6);
        assertNull(intersectionPoints6, "ERROR, Ray does not intersect the plane");


        // TC15: P0 after the plane
        Ray ray5 = new Ray(new Point3D(0.25, 0.25, 0), new Vector(-1, -1, 0));
        List intersectionPoints5 = plane.findIntersections(ray5);
        assertNull(intersectionPoints5, "ERROR, Ray does not intersect the plane");

        // **** Group: Ray is neither orthogonal nor parallel to the plane
        //  TC16: Ray begins at the plane,P0 is in the plane but not the ray
        Ray ray8 = new Ray(new Point3D(1,0 ,0 ), new Vector(1.5, 1, 0));
        List intersectionPoints8 = plane.findIntersections(ray8);
        assertNull(intersectionPoints8, "ERROR, Ray does not intersect the plane");


        // TC17: Ray begins at q0
        Ray ray9 = new Ray(new Point3D(0.5, 0.5, 0), new Vector(1.5, 1, 0));
        List intersectionPoints9 = plane.findIntersections(ray9);
        assertNull(intersectionPoints9, "ERROR, Ray does not intersect the plane");

    }
}




