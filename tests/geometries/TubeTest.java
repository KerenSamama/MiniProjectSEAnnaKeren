package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Unit tests for primitives.Tube class
 * @author Anna Keren
 */
class TubeTest {

    @Test
    void testGetNormal() {

        // ============ Equivalence Partitions Tests ==============
        Point3D p0= new Point3D(0,0,10);
        Point3D p= new Point3D(0,10,30);
        Point3D pE= new Point3D(0,10,10);
        Vector dir = new Vector(0,0,1);


        Ray ray= new Ray(p0,dir);
        Tube tube = new Tube(ray,10);
        double t= dir.dotProduct(p.subtract(p0));
        Point3D o= p0.add(dir.scale(t));
        Vector expectedNormal=(p.subtract(o)).normalize();
        Vector normal = tube.getNormal(p);

        // TC01: Test that Normal is is proper
         assertEquals(normal, expectedNormal, "ERROR: Not the same : getNormal() wrong result");

        // =============== Boundary Values Tests ==================

        double t2= dir.dotProduct(pE.subtract(p0));
        Vector expectedNormal2=(pE.subtract(p0)).normalize();
        Vector normal2 = tube.getNormal(pE);

        // TC11: Test that normal is proper
        assertEquals(normal2, expectedNormal2, "ERROR: Not the same : getNormal() wrong result");

    }
}

