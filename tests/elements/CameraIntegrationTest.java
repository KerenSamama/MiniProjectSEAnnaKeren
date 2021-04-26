package elements;

import geometries.Plane;
import geometries.Sphere;
import geometries.Triangle;
import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CameraIntegrationTest {


    Camera cam1 = new Camera(Point3D.ZERO, new Vector(0, 0, 1), new Vector(0, -1, 0)).setDistance(1).setViewPlaneSize(3,3);


    Camera cam2 = new Camera(new Point3D(0, 0, -0.5), new Vector(0, 0, 1), new Vector(0, -1, 0));


    /**
     * Sphere TC:1
     * a function that checks if we can find intersection with sphere1
     */
    @Test
    public void constructRayThroughPixelWithSphere1() {

        Sphere sph = new Sphere(new Point3D(0, 0, 3), 1);


        List<Point3D> results;

        int count = 0;
        int Nx = 3;
        int Ny = 3;

        System.out.println("sphere1: ");

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {

                Ray ray = cam1.constructRayThroughPixel(3, 3, j, i);
                results = sph.findIntersections(ray);

                if (results != null)
                    count += results.size();
            }
        }


        assertEquals(2, count, "ERROR, wrong number of intersections with sphere");
        System.out.println("count: " + count);
    }
}
