package primitives;
import static geometries.Intersectable.GeoPoint;
import geometries.Plane;
import geometries.Sphere;
import geometries.Triangle;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class RayTest {


    /**
     * Test method for {@link primitives.Ray#getClosestPoint(List)}.
     */
    @Test
    void testGetClosestPoint() {


        Ray ray = new Ray(new Point3D(0, 1, 0), new Vector(0, 1, 0));
        // ============ Equivalence Partitions Tests ==============

        // TC01: The point in the middle of the list is the closest point to the beginning of the ray

        List<Point3D> list1 = new ArrayList<Point3D>();
        Collections.addAll(list1, new Point3D(0, 15, 0), new Point3D(0, 2, 0), new Point3D(0, 7, 0));
        assertEquals(new Point3D(0, 2, 0), ray.getClosestPoint(list1), "Don't find the closest point, it was the middle point !");


        // =============== Boundary Values Tests ==================

        // TC11:  There are no intersections, the list is empty, so the closest point is null,
        List<Point3D> list4 = new ArrayList<Point3D>();
        assertNull(ray.getClosestPoint(list4), "It's an empty list, it must return null!");


        // TC12: The first point of the list is the closest to the beginning of the ray
        List<Point3D> list2 = new ArrayList<Point3D>();
        Collections.addAll(list2, new Point3D(0, 2, 0), new Point3D(0, 15, 0), new Point3D(0, 7, 0));
        assertEquals(new Point3D(0, 2, 0), ray.getClosestPoint(list2), "Don't find the closest point, it was the first point !");

        // TC13: The last point of the list is the closest to the beginning of the ray
        List<Point3D> list3 = new ArrayList<Point3D>();
        Collections.addAll(list3, new Point3D(0, 15, 0), new Point3D(0, 7, 0), new Point3D(0, 2, 0));
        assertEquals(new Point3D(0, 2, 0), ray.getClosestPoint(list3), "Don't find the closest point, it was the last point !");

    }

    /**
     * Test method for {@link primitives.Ray#findClosestGeoPoint(List)}.
     */
    @Test
    void testFindClosestGeoPoint() {
        Triangle tr = new Triangle(new Point3D(1, 2, 0), new Point3D(2, 2, 0),
                new Point3D(1.5, 2, 1));

        Plane plane = new Plane(new Point3D(3, 1, 0), new Point3D(4, 1, 0),
                new Point3D(3.5, 0, 1));

        Sphere sp = new Sphere(1.0, new Point3D(0, -2, 0));

        Ray ray = new Ray(new Point3D(0, 1, 0), new Vector(0, 1, 0));
        // ============ Equivalence Partitions Tests ==============

        // TC01: The point in the middle of the list is the closest point to the beginning of the ray

        List<GeoPoint> list1 = new ArrayList<GeoPoint>();
        Collections.addAll(list1, new GeoPoint(tr, new Point3D(0, 15, 0)), new GeoPoint(plane, new Point3D(0, 2, 0)),
                new GeoPoint(sp, new Point3D(0, 7, 0)));
        assertEquals(new GeoPoint(plane,new Point3D(0, 2, 0)), ray.findClosestGeoPoint(list1),
                "Don't find the closest geoPoint, it was the middle point !");


        // =============== Boundary Values Tests ==================

        // TC11:  There are no intersections, the list is empty, so the closest point is null,
        List<GeoPoint> list4 = new ArrayList<GeoPoint>();
        assertNull(ray.findClosestGeoPoint(list4), "It's an empty list, it must return null!");


        // TC12: The first point of the list is the closest to the beginning of the ray
        List<GeoPoint> list2 = new ArrayList<GeoPoint>();
        Collections.addAll(list2, new GeoPoint(tr, new Point3D(0, 2, 0)), new GeoPoint(plane, new Point3D(0, 15, 0)),
                new GeoPoint(sp, new Point3D(0, 7, 0)));
        assertEquals(new GeoPoint(tr, new Point3D(0, 2, 0)), ray.findClosestGeoPoint(list2),
                "Don't find the closest geoPoint, it was the first point !");

        // TC13: The last point of the list is the closest to the beginning of the ray
        List<GeoPoint> list3 = new ArrayList<GeoPoint>();
        Collections.addAll(list3, new GeoPoint(tr, new Point3D(0, 15, 0)), new GeoPoint(plane, new Point3D(0, 7, 0)),
                new GeoPoint(sp, new Point3D(0, 2, 0)));
        assertEquals(new GeoPoint(sp, new Point3D(0, 2, 0)), ray.findClosestGeoPoint(list3),
                "Don't find the closest geoPoint, it was the last point !");

    }




}