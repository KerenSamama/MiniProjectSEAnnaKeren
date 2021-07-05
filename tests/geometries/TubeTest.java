package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Unit tests for primitives.Tube class
 * @author Anna & Keren
 */
class TubeTest {

    /**
     * Test method for {@link geometries.Tube#getNormal(Point3D)}
     */
    @Test
    void testGetNormal() {

        // ============ Equivalence Partitions Tests ==============

        // TC01: Test that Normal is proper
        Point3D p0= new Point3D(0,0,10);
        Point3D p= new Point3D(0,10,30);
        Point3D pE= new Point3D(0,10,10);
        Vector dir = new Vector(0,0,1);


        Ray ray= new Ray(p0,dir);
        Tube tube = new Tube(ray,10);
        double t= dir.dotProduct(p.subtract(p0));
        Point3D o= p0.add(dir.scale(t));
        Vector expectedNormal1=(p.subtract(o)).normalize();
        Vector normal1 = tube.getNormal(p);

         assertEquals(normal1, expectedNormal1, "ERROR: Not the same : getNormal() for Tube wrong result");

        // =============== Boundary Values Tests ==================

        // TC11: When a connection of the point with the head of the ray of the tube axis produces a straight angle with the axis –
        // the point is in front of the head of the ray

        Vector expectedNormal2=(pE.subtract(p0)).normalize();
        Vector normal2 = tube.getNormal(pE);

        assertEquals(normal2, expectedNormal2, "ERROR: Not the same : getNormal() for Tube wrong result");

    }
}

