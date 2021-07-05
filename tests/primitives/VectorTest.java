package primitives;

import org.junit.jupiter.api.Test;

import static java.lang.System.out;
import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

/**
 * Unit tests for primitives.Vector class
 *
 * @author Anna & Keren
 */

class VectorTest {


    /**
     * Test method for {@link primitives.Vector#scale(double)}
     */
    @Test
    void testScale() {
        Vector v1 = new Vector(1, 2, 3);
        double d = 2.5;
        Vector v2 = new Vector(2.5, 5, 7.5);
        Vector v3 = v1.scale(d);
        assertEquals(v2, v3, "ERROR : scale() wrong result");
    }

    /**
     * Test method for {@link primitives.Vector#crossProduct(primitives.Vector)}.
     */
    @Test
    void testCrossProduct() {

        Vector v1 = new Vector(1, 2, 3);

        // ============ Equivalence Partitions Tests ==============
        Vector v2 = new Vector(0, 3, -2);
        Vector vr = v1.crossProduct(v2);

        // TC01: Test that length of cross-product is proper (orthogonal vectors taken
        // for simplicity)
        assertEquals(v1.length() * v2.length(), vr.length(), 0.00001, "crossProduct() wrong result length");

        // TC02: Test cross-product result orthogonality to its operands
        assertTrue(isZero(vr.dotProduct(v1)), "crossProduct() result is not orthogonal to 1st operand");
        assertTrue(isZero(vr.dotProduct(v2)), "crossProduct() result is not orthogonal to 2nd operand");

        // =============== Boundary Values Tests ==================

        // TC11: test zero vector from cross-product of co-lined vectors
        Vector v3 = new Vector(-2, -4, -6);
        assertThrows(
                IllegalArgumentException.class,
                () -> v1.crossProduct(v3), "crossProduct() for parallel vectors does not throw an exception");


    }

    /**
     * Test method for {@link primitives.Vector#dotProduct(primitives.Vector)}
     */
    @Test
    void testDotProduct() {
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(-2, -4, -6);
        Vector v3 = new Vector(0, 3, -2);
        assertTrue(isZero(v1.dotProduct(v3)), "ERROR: dotProduct() for orthogonal vectors is not zero");
        assertTrue(isZero(v1.dotProduct(v2) + 28), "ERROR: dotProduct() wrong value");

    }

    /**
     * Test method for {@link primitives.Vector#add(primitives.Vector)}
     */
    @Test
    void testAdd() {
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(0, 3, -2);
        Vector va = v1.add(v2);
        Vector v3 = new Vector(1, 5, 1);

        assertEquals(v3, va, "ERROR: Add() wrong result ");
    }

    /**
     * Test method for {@link primitives.Vector#subtract(primitives.Vector)}
     */
    @Test
    void testSubtract() {
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(0, 3, -2);
        Vector vs = v1.subtract(v2);
        Vector v3 = new Vector(1, -1, 5);

        assertEquals(v3, vs, "ERROR: Substract() wrong result ");
    }

    /**
     * Test method for {@link Vector#lengthSquared()}
     */
    @Test
    void testLengthSquared() {
        Vector v1 = new Vector(1, 2, 3);
        assertTrue(isZero(v1.lengthSquared() - 14), "ERROR: lengthSquared() wrong value");
    }

    /**
     * Test method for {@link Vector#length()}
     */
    @Test
    void testLength() {
        Vector v1 = new Vector(0, 3, 4);
        assertTrue(isZero(v1.length() - 5), "ERROR: length() wrong value");
    }

    /**
     * Test method for {@link Vector#normalize()}
     */
    @Test
    void testNormalize() {
        Vector v = new Vector(1, 2, 3);
        Vector vCopy = new Vector(v.getHead());
        Vector vCopyNormalize = vCopy.normalize();

        assertEquals(vCopy, vCopyNormalize, "ERROR: normalize() function creates a new vector");
        assertTrue(isZero(vCopyNormalize.length() - 1), "ERROR: normalize() result is not a unit vector");
    }

    /**
     * Test method for {@link Vector#normalized()}
     */
    @Test
    void testNormalized() {
        Vector v = new Vector(1, 2, 3);
        Vector u = v.normalized();

        assertNotEquals(u, v, "ERROR: normalizated() function does not create a new vector");
        assertTrue(isZero(u.length() - 1), "ERROR: normalized() result is not a unit vector");

    }

}


