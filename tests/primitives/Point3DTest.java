package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

class Point3DTest {

    @Test
    void testAdd() {
        Point3D p1 = new Point3D(1, 2, 3);
        Vector v=new Vector(-1, -2, -3);
        assertEquals(p1.add(v),Point3D.ZERO,"ERROR: Point + Vector does not work correctly");
    }

    @Test
    void testSubtract() {
        Point3D p1 = new Point3D(1, 2, 3);
        Point3D p2 = new Point3D(2, 3, 4);
        assertEquals((p2.subtract(p1)),new Vector(1,1,1),"ERROR: Point - Point does not work correctly");

    }

    @Test
    void testTestEquals() { //A DEMANDE
        Point3D p1 = new Point3D(1, 2, 3);

    }
}