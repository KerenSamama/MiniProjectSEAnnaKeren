package elements;

import geometries.Plane;
import geometries.Sphere;
import geometries.Triangle;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

public class CameraIntegrationTest {
    Camera cam1;
    Camera cam2;

    public CameraIntegrationTest() {
        this.cam1 = (new Camera(Point3D.ZERO, new Vector(0.0D, 0.0D, 1.0D), new Vector(0.0D, -1.0D, 0.0D))).setDistance(1.0D).setViewPlaneSize(3.0D, 3.0D);
        this.cam2 = new Camera(new Point3D(0.0D, 0.0D, -0.5D), new Vector(0.0D, 0.0D, 1.0D), new Vector(0.0D, -1.0D, 0.0D));
    }

    @Test
    void constructRayThroughPixelWithSphere1() {
        Sphere sph = new Sphere(new Point3D(0.0D, 0.0D, 3.0D), 1.0D);
        int count = 0;
        System.out.println("sphere1: ");

        for(int i = 0; i < 3; ++i) {
            for(int j = 0; j < 3; ++j) {
                Ray ray = this.cam1.constructRayThroughPixel(3, 3, j, i);
                List<Point3D> results = sph.findIntersections(ray);
                if (results != null) {
                    count += results == null ? 0 : results.size();
                }
            }
        }

        Assertions.assertEquals(2, count, "ERROR, wrong number of intersections with sphere");
        System.out.println("count:" + count);
    }

    public void cameraRaySphereIntegrationTest2() {
        Camera cam = (new Camera(new Point3D(0.0D, 0.0D, -0.5D), new Vector(0.0D, 0.0D, 1.0D), new Vector(0.0D, -1.0D, 0.0D))).setDistance(1.0D).setViewPlaneSize(3.0D, 3.0D);
        Sphere sphere = new Sphere(new Point3D(0.0D, 0.0D, 2.5D), 2.5D);
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
            Assertions.assertEquals(18, count, "wronge intersection");
        }

    }

    @Test
    public void cameraRaySphereIntegrationTest3() {
        Camera cam = (new Camera(new Point3D(0.0D, 0.0D, -0.5D), new Vector(0.0D, 0.0D, 1.0D), new Vector(0.0D, -1.0D, 0.0D))).setDistance(1.0D).setViewPlaneSize(3.0D, 3.0D);
        Sphere sphere = new Sphere(new Point3D(0.0D, 0.0D, 2.0D), 2.0D);
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
            Assertions.assertEquals(10, count, "wronge intersection");
        }

    }

    @Test
    public void cameraRaySphereIntegrationTest4() {
        Camera cam = (new Camera(new Point3D(2.0D, 2.0D, 2.0D), new Vector(0.0D, 0.0D, 1.0D), new Vector(0.0D, -1.0D, 0.0D))).setDistance(1.0D).setViewPlaneSize(3.0D, 3.0D);
        Sphere sphere = new Sphere(new Point3D(2.0D, 2.0D, 2.0D), 4.0D);
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
            Assertions.assertEquals(9, count, "wronge intersection");
        }

    }

    @Test
    public void cameraRaySphereIntegrationTest5() {
        Camera cam = (new Camera(Point3D.ZERO, new Vector(0.0D, 0.0D, 1.0D), new Vector(0.0D, -1.0D, 0.0D))).setDistance(1.0D).setViewPlaneSize(3.0D, 3.0D);
        Sphere sphere = new Sphere(new Point3D(0.0D, 0.0D, -1.0D), 0.5D);
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
            Assertions.assertEquals(0, count, "wronge intersection");
        }

    }

    public void constructRayThroughPixelWithPlane1() {
        Plane plane = new Plane(new Point3D(0.0D, 0.0D, 1.25D), new Vector(0.0D, 0.0D, 1.0D));
        int count = 0;
        int Nx = 3;
        int Ny = 3;

        for(int i = 0; i < Ny; ++i) {
            for(int j = 0; j < Nx; ++j) {
                Ray ray = this.cam1.constructRayThroughPixel(Nx, Ny, j, i);
                List<Point3D> results = plane.findIntersections(ray);
                if (results != null) {
                    count += results.size();
                }
            }
        }

        Assertions.assertEquals(9, count, "ERROR, wrong number of intersections with plane");
        System.out.println("count: " + count);
    }

    @Test
    public void constructRayThroughPixelWithPlane2() {
        Plane plane = new Plane(new Point3D(0.0D, 0.0D, 9.0D), new Vector(0.0D, -0.5D, 1.0D));
        int count = 0;
        int Nx = 3;
        int Ny = 3;

        for(int i = 0; i < Ny; ++i) {
            for(int j = 0; j < Nx; ++j) {
                Ray ray = this.cam1.constructRayThroughPixel(Nx, Ny, j, i);
                List<Point3D> results = plane.findIntersections(ray);
                if (results != null) {
                    count += results.size();
                }
            }
        }

        Assertions.assertEquals(9, count, "ERROR, wrong number of intersections with plane");
        System.out.println("count: " + count);
    }

    @Test
    public void constructRayThroughPixelWithPlane3() {
        Plane plane = new Plane(new Point3D(0.0D, 0.0D, 8.0D), new Vector(0.0D, -3.0D, 1.0D));
        int count = 0;
        int Nx = 3;
        int Ny = 3;

        for(int i = 0; i < Ny; ++i) {
            for(int j = 0; j < Nx; ++j) {
                Ray ray = this.cam1.constructRayThroughPixel(Nx, Ny, j, i);
                List<Point3D> results = plane.findIntersections(ray);
                if (results != null) {
                    count += results.size();
                }
            }
        }

        Assertions.assertEquals(6, count, "ERROR, wrong number of intersections with plane");
        System.out.println("count: " + count);
    }

    public void constructRayThroughPixelWithTriangle1() {
        Triangle triangle = new Triangle(new Point3D(0.0D, -1.0D, 2.0D), new Point3D(1.0D, 1.0D, 2.0D), new Point3D(-1.0D, 1.0D, 2.0D));
        Camera cam1 = (new Camera(Point3D.ZERO, new Vector(0.0D, 0.0D, 1.0D), new Vector(0.0D, -1.0D, 0.0D))).setDistance(1.0D).setViewPlaneSize(3.0D, 3.0D);
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

        Assertions.assertEquals(1, count, "ERROR, wrong number of intersections with triangle");
        System.out.println("count: " + count);
    }

    @Test
    public void constructRayThroughPixelWithTriangle2() {
        Triangle triangle = new Triangle(new Point3D(1.0D, 1.0D, 2.0D), new Point3D(-1.0D, 1.0D, 2.0D), new Point3D(0.0D, -20.0D, 2.0D));
        Camera cam1 = (new Camera(Point3D.ZERO, new Vector(0.0D, 0.0D, 1.0D), new Vector(0.0D, -1.0D, 0.0D))).setDistance(1.0D).setViewPlaneSize(3.0D, 3.0D);
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

        Assertions.assertEquals(2, count, "ERROR, wrong number of intersections with triangle");
        System.out.println("count: " + count);
    }
}