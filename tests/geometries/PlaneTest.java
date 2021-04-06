package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Vector;

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

}




