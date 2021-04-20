package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class SphereTest {

    /**
     * Test method for {@link Sphere#getNormal(Point3D)}
     */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TCO1 EP : Any Point on Sphere, there is a simple single test here
        Point3D o=new Point3D(0,4,2);
        Point3D p=new Point3D(0,2,2);
        Sphere s1= new Sphere(o,2);
        Vector expectedNormal= (p.subtract(o)).normalize();
        Vector normal=s1.getNormal(p);


    }

        /**
         * Test method for {@link geometries.Sphere#findIntersections(primitives.Ray)}.
         */
        @Test
        public void testFindIntersections() {
            Sphere sphere = new Sphere(new Point3D(1, 0, 0), 1d);

            // ============ Equivalence Partitions Tests ==============

            // TC01: Ray's line is outside the sphere (0 points)
            assertNull(sphere.findIntersections(new Ray(new Point3D(-1, 0, 0), new Vector(1, 1, 0))),"Ray's line out of sphere");

            // TC02: Ray starts before and crosses the sphere (2 points)
            Point3D p1 = new Point3D(0.0651530771650466, 0.355051025721682, 0);
            Point3D p2 = new Point3D(1.53484692283495, 0.844948974278318, 0);
            List<Point3D> result = sphere.findIntersections(new Ray(new Point3D(-1, 0, 0),
                    new Vector(3, 1, 0)));
            assertEquals(2, result.size(),"Wrong number of points");
            if (result.get(0).getX() > result.get(1).getX())
                result = List.of(result.get(1), result.get(0));
            assertEquals(List.of(p1, p2), result,"Ray crosses sphere");

            // TC03: Ray starts inside the sphere (1 point)
            assertEquals(List.of(p2),sphere.findIntersections(new Ray(new Point3D(0.5, 0.5, 0), new Vector(3, 1, 0))),
                    "Ray starts from inside sphere");

            // TC04: Ray starts after the sphere (0 points)
            assertNull(sphere.findIntersections(new Ray(new Point3D(2, 1, 0), new Vector(3, 1, 0))),
                    "Sphere behind Ray");



            // =============== Boundary Values Tests ==================

            // **** Group: Ray's line crosses the sphere (but not the center)
            // TC11: Ray starts at sphere and goes inside (1 points)
            assertEquals( List.of(new Point3D(2, 0, 0)),
                    sphere.findIntersections(new Ray(new Point3D(1, -1, 0), new Vector(1, 1, 0))),
                    "Ray starts at sphere and goes inside");

            // TC12: Ray starts at sphere and goes outside (0 points)
            assertNull(sphere.findIntersections(new Ray(new Point3D(2, 0, 0), new Vector(3, 0, 0))),
                    "Ray starts at sphere and goes outside");


            // **** Group: Ray's line goes through the center
            // TC13: Ray starts before the sphere (2 points)
            result = sphere.findIntersections(new Ray(new Point3D(1, -2, 0), new Vector(0, 1, 0)));

            assertEquals( 2, result.size(),"Wrong number of points");
            if (result.get(0).getY() > result.get(1).getY()) {
                result = List.of(result.get(1), result.get(0));
            }
            assertEquals(List.of(new Point3D(1, -1, 0), new Point3D(1, 1, 0)), result,
                    "Ray's line through the center, Ray crosses sphere");


            // TC14: Ray starts at sphere and goes inside (1 points)
            result = sphere.findIntersections(new Ray(new Point3D(1, -1, 0), new Vector(0, 1, 0)));
            assertEquals( List.of(new Point3D(1, 1, 0)),result,"Ray starts at sphere and crosses sphere");


            // TC15: Ray starts inside (1 points)
            assertEquals( List.of(new Point3D(2, 0, 0)),
                    sphere.findIntersections(new Ray(new Point3D(0.5, 0, 0), new Vector(2, 0, 0))),
                    "Ray starts inside sphere and crosses sphere");

            // TC16: Ray starts at the center (1 points)
            assertEquals( List.of(new Point3D(1, 1, 0)), sphere.findIntersections(new Ray(new Point3D(1, 0, 0), new Vector(0, 1, 0))),
                    "Ray starts at the center and crosses sphere");


            // TC17: Ray starts at sphere and goes outside (0 points)
            assertNull(sphere.findIntersections(new Ray(new Point3D(1, -1, 0), new Vector(0, -1, 0))),
                    "Line starts at sphere and goes outside ");

            // TC18: Ray starts after sphere (0 points)
            assertNull(sphere.findIntersections(new Ray(new Point3D(1, 1.5, 0), new Vector(0, 1, 0))),
                    "Ray's line goes through the center, ray outside sphere");


            // **** Group: Ray's line is tangent to the sphere (all tests 0 points)
            // TC19: Ray starts before the tangent point
            assertNull(sphere.findIntersections(new Ray(new Point3D(0, 1, 0), new Vector(1, 0, 0))),
                    "Tangent line, ray before sphere");

            // TC20: Ray starts at the tangent point
            assertNull(sphere.findIntersections(new Ray(new Point3D(1, 1, 0), new Vector(1, 0, 0))),
                    "Tangent line, ray starts at the tangent point");
            // TC21: Ray starts after the tangent point
            assertNull(sphere.findIntersections(new Ray(new Point3D(2, 1, 0), new Vector(1, 0, 0))),
                    "Tangent line, ray after sphere");

            // **** Group: Special cases
            // TC19: Ray's line is outside, ray is orthogonal to ray start to sphere's center line
            assertNull(sphere.findIntersections(new Ray(new Point3D(-1, 0, 0), new Vector(0, 0, 1))),
                    "Ray's line is outside, Ray orthogonal to ray start to sphere's center line");

        }

    }
