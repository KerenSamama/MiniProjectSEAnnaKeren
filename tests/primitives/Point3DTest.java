package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

/**
 * Unit tests for primitives.Point3D class
 * @author Anna & Keren
 */
class Point3DTest {

    /**
     * Test method for {@link primitives.Point3D#add(primitives.Vector)} 
     */
    @Test
    void testAdd() {
        Point3D p1 = new Point3D(1, 2, 3);
        Vector v=new Vector(-1, -2, -3);
        assertEquals(p1.add(v),Point3D.ZERO,"ERROR: Add() for Point + Vector does not work correctly");
    }

    /**
     * Test method for {@link primitives.Point3D#subtract(Point3D)} 
     */
    @Test
    void testSubtract() {
        Point3D p1 = new Point3D(1, 2, 3);
        Point3D p2 = new Point3D(2, 3, 4);
        assertEquals((p2.subtract(p1)),new Vector(1,1,1),"ERROR:  Substract() Point - Point does not work correctly");

    }

    /**
     * Test method for {@link primitives.Point3D#equals(Object)} 
     */
    @Test
    void testTestEquals() {
        Point3D p1 = new Point3D(1, 2, 3);

    }
}