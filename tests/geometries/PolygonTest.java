package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


    public class PolygonTest {

        /**
         * Test method for
         * {@link geometries.Polygon#Polygon(primitives.Point3D, primitives.Point3D, primitives.Point3D, primitives.Point3D)}.
         */
        @Test
        public void testConstructor() {
            // ============ Equivalence Partitions Tests ==============

            // TC01: Correct concave quadrangular with vertices in correct order
            try {
                new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0),
                        new Point3D(0, 1, 0), new Point3D(-1, 1, 1));
            } catch (IllegalArgumentException e) {
                fail("Failed constructing a correct polygon");
            }

            // TC02: Wrong vertices order
            try {
                new Polygon(new Point3D(0, 0, 1), new Point3D(0, 1, 0),
                        new Point3D(1, 0, 0), new Point3D(-1, 1, 1));
                fail("Constructed a polygon with wrong order of vertices");
            } catch (IllegalArgumentException e) {}

            // TC03: Not in the same plane
            try {
                new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0),
                        new Point3D(0, 1, 0), new Point3D(0, 2, 2));
                fail("Constructed a polygon with vertices that are not in the same plane");
            } catch (IllegalArgumentException e) {}

            // TC04: Concave quadrangular
            try {
                new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0),
                        new Point3D(0, 1, 0), new Point3D(0.5, 0.25, 0.5));
                fail("Constructed a concave polygon");
            } catch (IllegalArgumentException e) {}

            // =============== Boundary Values Tests ==================

            // TC10: Vertex on a side of a quadrangular
            try {
                new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0),
                        new Point3D(0, 1, 0), new Point3D(0, 0.5, 0.5));
                fail("Constructed a polygon with vertix on a side");
            } catch (IllegalArgumentException e) {}

            // TC11: Last point = first point
            try {
                new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0),
                        new Point3D(0, 1, 0), new Point3D(0, 0, 1));
                fail("Constructed a polygon with vertice on a side");
            } catch (IllegalArgumentException e) {}

            // TC12: Colocated points
            try {
                new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0),
                        new Point3D(0, 1, 0), new Point3D(0, 1, 0));
                fail("Constructed a polygon with vertice on a side");
            } catch (IllegalArgumentException e) {}

        }

        /**
         * Test method for {@link geometries.Polygon#getNormal(primitives.Point3D)}.
         */
        @Test
        public void testGetNormal() {
            // ============ Equivalence Partitions Tests ==============
            // TC01: There is a simple single test here
            Polygon pl = new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0), new Point3D(0, 1, 0),
                    new Point3D(-1, 1, 1));
            double sqrt3 = Math.sqrt(1d / 3);
            assertEquals( new Vector(sqrt3, sqrt3, sqrt3), pl.getNormal(new Point3D(0, 0, 1)),"Bad normal to triangle");
        }

        /**
         * Test method for {@link geometries.Polygon#findIntersections(primitives.Ray)}.
         */
        @Test
        void testFindIntersections() {

            Point3D a = new Point3D(0, 0, 1);
            Point3D b = new Point3D(0, 1, 0);
            Point3D c = new Point3D(1, 0, 0);

            Polygon pl = new Polygon(a , b, c);

            // ============ Equivalence Partitions Tests ==============

            // TC01: Inside Polygon

            Ray ray1 = new Ray(new Point3D(0.5, 0.5,  0.5), new Vector(-1, -1, -1));
            List intersectionPoints1 = pl.findIntersections(ray1);
            assertEquals(1,intersectionPoints1.size(),"ERROR, there must be an intersection point");
            Point3D expectedPoint = new Point3D(0.3333333333333333,0.3333333333333333,0.3333333333333333);
            Point3D actualPoint = (pl.findIntersections(ray1)).get(0);
            assertEquals(expectedPoint,actualPoint,"Don't found the real intersection point");


            // TC02: Outside against edge
            Ray ray2 = new Ray(new Point3D(1, 1,  0), new Vector(2, 0, -1));
            List intersectionPoints2 =  pl.findIntersections(ray2);
            assertNull(intersectionPoints2,"ERROR, there must not be any intersection point");


            // TC03:Outside against vertex
            Ray ray3 = new Ray(new Point3D(0, 2,  0), new Vector(0, 0, -1));
            List intersectionPoints3 = pl.findIntersections(ray3);
            assertNull(intersectionPoints3,"ERROR, there must not be any intersection point");


            // =============== Boundary Values Tests ==================


            // TC11: On edge ok
            Ray ray4 = new Ray(new Point3D(1, 0,  0), new Vector(0, 1, 0));
            List intersectionPoints4 = pl.findIntersections(ray4);
            assertNull(intersectionPoints4,"ERROR, there must not be any intersection point");


            // TC12: In vertex
            Ray ray5 = new Ray(new Point3D(1, 1,  0), new Vector(-1, -1, 0));
            List intersectionPoints5 = pl.findIntersections(ray5);
            assertNull(intersectionPoints5,"ERROR, there must not be any intersection point");


            // TC13: On edge's continuation
            Ray ray6 = new Ray(new Point3D(-0.5, 2,  0), new Vector(-1, 0, 0));
            List intersectionPoints6 = pl.findIntersections(ray6);
            assertNull(intersectionPoints6,"ERROR, there must not be any intersection point");

        }

    }
