package primitives;

import org.junit.jupiter.api.Test;

import static java.lang.System.out;
import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;
/**
 * Unit tests for primitives.Vector class
 * @author Dan
 */

class VectorTest {

    @Test
    void testScale() {
        Vector v1 = new Vector(1, 2, 3);
        double d = 2.5;
        Vector v2= new Vector(2.5,5,7.5);
        Vector v3=v1.scale(d);
        assertEquals(v2,v3,"ERROR : scale() wrong result");


    }

    @Test
    void testCrossProduct() {
        /**
         * Test method for {@link primitives.Vector#crossProduct(primitives.Vector)}.
         */

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


        /**
         *
         *       try { // test zero vector
         *             v1.crossProduct(v2);
         *             out.println("ERROR: crossProduct() for parallel vectors does not throw an exception");
         *         } catch (Exception e) {}
         *         Vector vr = v1.crossProduct(v3);
         *         if (!isZero(vr.length() - v1.length() * v3.length()))
         *             out.println("ERROR: crossProduct() wrong result length");
         *         if (!isZero(vr.dotProduct(v1)) || !isZero(vr.dotProduct(v3)))
         *             out.println("ERROR: crossProduct() result is not orthogonal to its operands");
         */
        // =============== Boundary Values Tests ==================


        // TC11: test zero vector from cross-product of co-lined vectors
        Vector v3 = new Vector(-2, -4, -6);
        assertThrows(
                IllegalArgumentException.class,
                () -> v1.crossProduct(v3), "crossProduct() for parallel vectors does not throw an exception");
        // try {
        //     v1.crossProduct(v2);
        //     fail("crossProduct() for parallel vectors does not throw an exception");
        // } catch (Exception e) {}

    }

    @Test
    void testDotProduct() {
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(-2, -4, -6);
        Vector v3 = new Vector(0, 3, -2);
        assertTrue(isZero(v1.dotProduct(v3)), "ERROR: dotProduct() for orthogonal vectors is not zero");
        assertTrue(isZero(v1.dotProduct(v2) + 28), "ERROR: dotProduct() wrong value");

        if (!isZero(v1.dotProduct(v2) + 28))
            out.println("ERROR: dotProduct() wrong value");
    }


    @Test
    void testAdd() {


            Vector v1 = new Vector(1, 2, 3);
            Vector v2 = new Vector(0, 3, -2);
            Vector va;
            Vector v3 = new Vector(1, 5, 1);
            va = v1.add(v2);
            assertEquals(v3, va, "ERROR: Add() wrong result ");
    }

    @Test
    void testSubstract() {
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(0, 3, -2);
        Vector vs;
        Vector v3 = new Vector(1, -1, 5);
        vs = v1.substract(v2);
        assertEquals(v3, vs, "ERROR: Substract() wrong result ");
    }




    @Test
    void testLengthSquared() {
        Vector v1 = new Vector(1, 2, 3);



        assertTrue(isZero(v1.lengthSquared() - 14), "ERROR: lengthSquared() wrong value");

    }

    @Test
    void testLength() {

        Vector v1 = new Vector(0, 3, 4);
        assertTrue(isZero(v1.length() - 5), "ERROR: length() wrong value");

    }


    @Test
    void testNormalize() {
        Vector v = new Vector(1, 2, 3);
        Vector vCopy = new Vector(v.getHead());
        Vector vCopyNormalize = vCopy.normalize();

        assertEquals(vCopy, vCopyNormalize, "ERROR: normalize() function creates a new vector");
        assertTrue(isZero(vCopyNormalize.length() - 1), "ERROR: normalize() result is not a unit vector");
    }

    @Test
    void testNormalized() {

        Vector v = new Vector(1, 2, 3);
        Vector u = v.normalized();

        assertNotEquals(u,v,"ERROR: normalizated() function does not create a new vector");

        }



}


