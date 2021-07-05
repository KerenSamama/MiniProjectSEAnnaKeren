package elements;

import geometries.Plane;
import geometries.Sphere;
import geometries.Triangle;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;


/**
 * CameraIntegrationTest is for integration tests between creating rays from a camera and calculating number of intersections of the ray with geometric bodies
 * The class  will have  test functions for integration against sphere, plane, and triangle
 */

public class CameraIntegrationTest {

        Camera cam1 = (new Camera(Point3D.ZERO, new Vector(0.0D, 0.0D, -1.0D),
                new Vector(0.0D, 1.0D, 0.0D))).setDistance(1.0D).setViewPlaneSize(3.0D, 3.0D);
        Camera cam2 = new Camera(new Point3D(0.0D, 0.0D, 0.5D), new Vector(0.0D, 0.0D, -1.0D),
                new Vector(0.0D, 1.0D, 0.0D)).setDistance(1.0D).setViewPlaneSize(3.0D, 3.0D);;


    @Test
    public void constructRayThroughPixelWithSphere1() {
        Sphere sph = new Sphere(1.0D, new Point3D(0.0D, 0.0D, -3.0D));
        int count = 0;


        for(int i = 0; i < 3; ++i) {
            for(int j = 0; j < 3; ++j) {
                Ray ray = this.cam1.constructRayThroughPixel(3, 3, j, i);
                List<Point3D> results = sph.findIntersections(ray);
                if (results != null) {
                    count += results.size();
                }
            }
        }

        Assertions.assertEquals(2, count, "ERROR, wrong number of intersections with sphere1");

    }

    @Test
    public void cameraRaySphereIntegrationTest2() {
        Sphere sphere = new Sphere(2.5D, new Point3D(0.0D, 0.0D, -2.5D));
        List<Point3D> intersections = new ArrayList();
        int count = 0;

        for(int i = 0; i < 3; ++i) {
            for(int j = 0; j < 3; ++j) {
                intersections = sphere.findIntersections(cam2.constructRayThroughPixel(3, 3, j, i));
                if (intersections != null) {
                    count += ((List)intersections).size();
                }
            }
        }

        if (intersections != null) {
            Assertions.assertEquals(18, count, "ERROR, wrong number of intersections with sphere2");
        }

    }

    @Test
    public void cameraRaySphereIntegrationTest3() {
        Sphere sphere = new Sphere(2.0D, new Point3D(0.0D, 0.0D, -2.0D));
        List<Point3D> intersections = new ArrayList();
        int count = 0;

        for(int i = 0; i < 3; ++i) {
            for(int j = 0; j < 3; ++j) {
                intersections = sphere.findIntersections(cam2.constructRayThroughPixel(3, 3, j, i));
                if (intersections != null) {
                    count += ((List)intersections).size();
                }
            }
        }

        if (intersections != null) {
            Assertions.assertEquals(10, count, "ERROR, wrong number of intersections with sphere3");
        }

    }

    @Test
    public void cameraRaySphereIntegrationTest4() {
        Camera cam = (new Camera(new Point3D(2.0D, -2.0D, -2.0D), new Vector(0.0D, 0.0D, -1.0D), new Vector(0.0D, 1.0D, 0.0D)))
                .setDistance(1.0D).setViewPlaneSize(3.0D, 3.0D);
        Sphere sphere = new Sphere(4.0D, new Point3D(2.0D, -2.0D, -2.0D));
        List<Point3D> intersections = new ArrayList();
        int count = 0;

        for(int i = 0; i < 3; ++i) {
            for(int j = 0; j < 3; ++j) {
                intersections = sphere.findIntersections(cam.constructRayThroughPixel(3, 3, j, i));
                if (intersections != null) {
                    count += ((List)intersections).size();
                }
            }
        }

        if (intersections != null) {
            Assertions.assertEquals(9, count, "ERROR, wrong number of intersections with sphere4");
        }

    }

    @Test
    public void cameraRaySphereIntegrationTest5() {
        Sphere sphere = new Sphere(0.5D, new Point3D(0.0D, 0.0D, 1.0D));
        List<Point3D> intersections = new ArrayList();
        int count = 0;

        for(int i = 0; i < 3; ++i) {
            for(int j = 0; j < 3; ++j) {
                intersections = sphere.findIntersections(cam1.constructRayThroughPixel(3, 3, j, i));
                if (intersections != null) {
                    count += ((List)intersections).size();
                }
            }
        }

        if (intersections != null) {
            Assertions.assertEquals(0, count, "ERROR, wrong number of intersections with sphere5");
        }

    }

    @Test
    public void constructRayThroughPixelWithPlane1() {
        Plane plane = new Plane(new Point3D(0.0D, 0.0D, -1.25D), new Vector(0.0D, 0.0D, -1.0D));
        int count = 0;
        int Nx = 3;
        int Ny = 3;

        for(int i = 0; i < Ny; ++i) {
            for(int j = 0; j < Nx; ++j) {
                Ray ray = cam1.constructRayThroughPixel(Nx, Ny, j, i);
                List<Point3D> results = plane.findIntersections(ray);
                if (results != null) {
                    count += results.size();
                }
            }
        }

        Assertions.assertEquals(9, count, "ERROR, wrong number of intersections with plane1");

    }

    @Test
    public void constructRayThroughPixelWithPlane2() {
        Plane plane = new Plane(new Point3D(0.0D, 0.0D, -9.0D), new Vector(0.0D, 0.5D, -1.0D));
        int count = 0;
        int Nx = 3;
        int Ny = 3;

        for(int i = 0; i < Ny; ++i) {
            for(int j = 0; j < Nx; ++j) {
                Ray ray = cam1.constructRayThroughPixel(Nx, Ny, j, i);
                List<Point3D> results = plane.findIntersections(ray);
                if (results != null) {
                    count += results.size();
                }
            }
        }

        Assertions.assertEquals(9, count, "ERROR, wrong number of intersections with plane2");

    }

    @Test
    public void constructRayThroughPixelWithPlane3() {
        Plane plane = new Plane(new Point3D(0.0D, 0.0D, -8.0D), new Vector(0.0D, 3.0D, -1.0D));
        int count = 0;
        int Nx = 3;
        int Ny = 3;

        for(int i = 0; i < Ny; ++i) {
            for(int j = 0; j < Nx; ++j) {
                Ray ray = cam1.constructRayThroughPixel(Nx, Ny, j, i);
                List<Point3D> results = plane.findIntersections(ray);
                if (results != null) {
                    count += results.size();
                }
            }
        }

        Assertions.assertEquals(6, count, "ERROR, wrong number of intersections with plane3");

    }

    @Test
    public void constructRayThroughPixelWithTriangle1() {
        Triangle triangle = new Triangle(new Point3D(0.0D, 1.0D, -2.0D), new Point3D(1.0D, -1.0D, -2.0D), new Point3D(-1.0D, -1.0D, -2.0D));
        int count = 0;
        int Nx = 3;
        int Ny = 3;

        for(int i = 0; i < Ny; ++i) {
            for(int j = 0; j < Nx; ++j) {
                Ray ray = cam1.constructRayThroughPixel(Nx, Ny, j, i);
                List<Point3D> results = triangle.findIntersections(ray);
                if (results != null) {
                    count += results.size();
                }
            }
        }

        Assertions.assertEquals(1, count, "ERROR, wrong number of intersections with triangle1");

    }

    @Test
    public void constructRayThroughPixelWithTriangle2() {
        Triangle triangle = new Triangle(new Point3D(1.0D, -1.0D, -2.0D), new Point3D(-1.0D, -1.0D, -2.0D), new Point3D(0.0D, 20.0D, -2.0D));
        int count = 0;
        int Nx = 3;
        int Ny = 3;

        for(int i = 0; i < Ny; ++i) {
            for(int j = 0; j < Nx; ++j) {
                Ray ray = cam1.constructRayThroughPixel(Nx, Ny, j, i);
                List<Point3D> results = triangle.findIntersections(ray);
                if (results != null) {
                    count += results.size();
                }
            }
        }

        Assertions.assertEquals(2, count, "ERROR, wrong number of intersections with triangle2");

    }
}