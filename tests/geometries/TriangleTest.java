package geometries;

import primitives.Point3D;
import primitives.*;
import static org.junit.jupiter.api.Assertions.*;
/**
 * Unit tests for primitives.Triangle class
 * @author Anna Keren
 */

class TriangleTest {

    /**
     * Test method for {@link primitives.Vector#crossProduct(primitives.Vector)}.
     */

    public void testGetNormal()
    {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test that Normal is is proper

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


}