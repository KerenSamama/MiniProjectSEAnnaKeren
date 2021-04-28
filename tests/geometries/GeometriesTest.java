package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class GeometriesTest {


    /**
     * Test method for {@link geometries.Geometries#findIntersections(primitives.Ray)}.
     */
    @Test
    void testFindIntersections(){

        Triangle tr1 = new Triangle(new Point3D(1, 2, 0), new Point3D(2, 2, 0),
                new Point3D(1.5, 2, 1));

        Plane plane1 = new Plane(new Point3D(3, 1, 0), new Point3D(4, 1, 0),
                new Point3D(3.5, 0, 1));

        Sphere sp = new Sphere(new Point3D(0, -2, 0), 1.0);

        Geometries geo1 = new Geometries(tr1, plane1, sp);

        Geometries geo2 = new Geometries();

        //--------------------------------------------------------

        Triangle tr2 = new Triangle(new Point3D(-0.5, 1, 0), new Point3D(0.5, 1, 0),
                new Point3D(0, 1, 1));

        Plane plane2 = new Plane(new Point3D(-0.5, -0.5, 0), new Point3D(0.5, -0.5, 0),
                new Point3D(0, -0.75, 1));

        Geometries geo3 = new Geometries(tr2, plane2, sp);


        // ============ Equivalence Partitions Tests ==============

        // TC01: Some shapes but not all are cut by the ray
        Ray ray1 = new Ray(new Point3D(2, -2, 0), new Vector(-1, 0, 0));
        List<Point3D> intersectionPoints1 = geo1.findIntersections(ray1);
        assertEquals(2, intersectionPoints1.size(), "ERROR : Only two shapes must be intersect by the ray");



        // =============== Boundary Values Tests ==================


        // TC11: empty geometries collection
        Ray ray2 = new Ray(new Point3D(1, 1, 1), new Vector(1, 0, 0));
        assertNull(geo2.findIntersections(ray2),"ERROR: empty geometries collection, therefore it must return null ");

        //TC11 : None of the geometries shapes is cut by the ray
        Ray ray3 = new Ray(new Point3D(-1, -1, 0), new Vector(-2, -2, 0));
        List<Point3D> intersectionPoints3 = geo1.findIntersections(ray3);
        assertNull(intersectionPoints3,"ERROR, there are no intersection points, therefore it must return null");

        //TC12 : only one shape is cut by the ray
        Ray ray4 = new Ray(new Point3D(1, 0, 0), new Vector(0, 0, 1));
        assertEquals(1,geo1.findIntersections(ray4).size(), "ERROR, there must be only one intersection point");


        //TC13 : all the collection is cut by the ray
        Ray ray5 = new Ray(new Point3D(0, -4, 0), new Vector(0,1,0));
        assertEquals(3,geo3.findIntersections(ray5).size(), "ERROR, there must be three intersection points");


    }

}
