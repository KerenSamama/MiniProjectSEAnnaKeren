package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.*;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;


class TriangleTest {


    public void testGetNormal()
    {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here

        Point3D p1=new Point3D(0,1,1);
        Point3D p2=new Point3D(1,0,1);
        Point3D p3=new Point3D(1,1,0);

        Triangle t1= new Triangle(p1,p2,p3);
        Vector v1= p2.subtract(p1);
        Vector v2= p3.subtract(p1);
        Vector cp= v1.crossProduct(v2);
        Vector expectedNormal= cp.normalize();
        Vector normal= t1.getNormal(p1);
        assertEquals(normal, expectedNormal, "ERROR: Not the same : getNormal() wrong result");
    }


    /**
     * Test method for {@link geometries.Triangle#findIntersections(primitives.Ray)}.
     */
    @Test
    void testFindIntersections() {

        Point3D a = new Point3D(0, 0, 1);
        Point3D b = new Point3D(0, 1, 0);
        Point3D c = new Point3D(1, 0, 0);

        Triangle tr = new Triangle(a , b, c);

        // ============ Equivalence Partitions Tests ==============

        // TC01: Inside triangle

        Ray ray1 = new Ray(new Point3D(0.5, 0.5,  0.5), new Vector(-1, -1, -1));
        List intersectionPoints1 = tr.findIntersections(ray1);
        assertEquals(1,intersectionPoints1.size(),"ERROR, there must be an intersection point");
        Point3D expectedPoint = new Point3D(0.3333333333333333,0.3333333333333333,0.3333333333333333);
        Point3D actualPoint = (tr.findIntersections(ray1)).get(0);
        assertEquals(expectedPoint,actualPoint,"Don't found the real intersection point");


        // TC02: Outside against edge
        Ray ray2 = new Ray(new Point3D(1, 1,  0), new Vector(2, 0, -1));
        List intersectionPoints2 =  tr.findIntersections(ray2);
        assertNull(intersectionPoints2,"ERROR, there must not be any intersection point");


        // TC03:Outside against vertex
        Ray ray3 = new Ray(new Point3D(0, 2,  0), new Vector(0, 0, -1));
        List intersectionPoints3 = tr.findIntersections(ray3);
        assertNull(intersectionPoints3,"ERROR, there must not be any intersection point");


        // =============== Boundary Values Tests ==================


        // TC11: On edge ok
        Ray ray4 = new Ray(new Point3D(1, 0,  0), new Vector(0, 1, 0));
        List intersectionPoints4 = tr.findIntersections(ray4);
        assertNull(intersectionPoints4,"ERROR, there must not be any intersection point");



        // TC12: In vertex
        Ray ray5 = new Ray(new Point3D(1, 1,  0), new Vector(-1, -1, 0));
        List intersectionPoints5 = tr.findIntersections(ray5);
        assertNull(intersectionPoints5,"ERROR, there must not be any intersection point");


        // TC13: On edge's continuation
        Ray ray6 = new Ray(new Point3D(-0.5, 2,  0), new Vector(-1, 0, 0));
        List intersectionPoints6 = tr.findIntersections(ray6);
        assertNull(intersectionPoints6,"ERROR, there must not be any intersection point");

    }
}