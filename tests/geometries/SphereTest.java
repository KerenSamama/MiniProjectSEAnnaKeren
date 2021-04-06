package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class SphereTest {

    // ============ Equivalence Partitions Tests ==============
    // TCO1 EP : Any Point on Sphere, there is a simple single test here
    @Test
    void testGetNormal() {
        Point3D o=new Point3D(0,4,2);
        Point3D p=new Point3D(0,2,2);
        Sphere s1= new Sphere(o,2);
        Vector expectedNormal= (p.subtract(o)).normalize();
        Vector normal=s1.getNormal(p);


    }
}