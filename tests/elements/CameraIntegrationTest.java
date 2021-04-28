package elements;

import geometries.Plane;
import geometries.Sphere;
import geometries.Triangle;
import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CameraIntegrationTest {





    Camera cam1 = new Camera(Point3D.ZERO, new Vector(0, 0, 1), new Vector(0, -1, 0)).setDistance(1).setViewPlaneSize(3,3);
    Camera cam2 = new Camera(new Point3D(0, 0, -0.5), new Vector(0, 0, 1), new Vector(0, -1, 0));


    /**
     * Sphere TC:1
     * a function that checks if we can find intersection with sphere1
     */
    @Test
    void constructRayThroughPixelWithSphere1() {

        Sphere sph = new Sphere(new Point3D(0, 0, 3), 1);

       int count=0;
        List<Point3D> results;


        System.out.println("sphere1: ");

        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {


                Ray ray = cam1.constructRayThroughPixel(3, 3, j, i);
                results = sph.findIntersections(ray);

                if (results != null)
                    count += results==null?0: results.size();


            }
        }


        assertEquals(2, count, "ERROR, wrong number of intersections with sphere");
        System.out.println("count:"+ count);
    }




    public void cameraRaySphereIntegrationTest2() {
        Camera cam = new Camera(new Point3D(0, 0, -0.5), new Vector(0, 0, 1), new Vector(0, -1, 0)).setDistance(1).setViewPlaneSize(3,3);
        Sphere sphere = new Sphere( new Point3D(0, 0, 2.5),2.5);
        List<Point3D> intersections=new ArrayList<Point3D>();
        int count = 0;


        for (int i=0;i<3;i++) {
            for (int j = 0; j < 3; j++) {
                intersections = sphere.findIntersections(cam.constructRayThroughPixel(3, 3, j, i));
                if (intersections != null)
                    count+=intersections.size();
            }
        }

        if (intersections != null)
            assertEquals(18,count,"wrong intersection");
    }

    @Test
    public void cameraRaySphereIntegrationTest3() {
        Camera cam = new Camera(new Point3D(0, 0, -0.5), new Vector(0, 0, 1), new Vector(0, -1, 0)).setDistance(1).setViewPlaneSize(3,3);
        Sphere sphere = new Sphere( new Point3D(0, 0, 2),2);
        List<Point3D> intersections=new ArrayList<Point3D>();
        int count = 0;


        for (int i=0;i<3;i++) {
            for (int j = 0; j < 3; j++) {
                intersections = sphere.findIntersections(cam.constructRayThroughPixel(3, 3, j, i));
                if (intersections != null)
                    count+=intersections.size();
            }
        }

        if (intersections != null)
            assertEquals(10,count,"wrong intersection");
    }
    @Test
    public void cameraRaySphereIntegrationTest4() {
        Camera cam = new Camera(new Point3D(2, 2, 2), new Vector(0, 0, 1), new Vector(0, -1, 0)).setDistance(1).setViewPlaneSize(3,3);
        Sphere sphere = new Sphere(new Point3D(2, 2, 2),4);
        List<Point3D> intersections=new ArrayList<Point3D>();
        int count = 0;


        for (int i=0;i<3;i++) {
            for (int j = 0; j < 3; j++) {
                intersections = sphere.findIntersections(cam.constructRayThroughPixel(3, 3, j, i));
                if (intersections != null)
                    count+=intersections.size();
            }
        }

        if (intersections != null)
            assertEquals(9,count,"wrong intersection");
    }
    @Test
    public void cameraRaySphereIntegrationTest5() {
        Camera cam = new Camera(Point3D.ZERO, new Vector(0, 0, 1), new Vector(0, -1, 0)).setDistance(1).setViewPlaneSize(3,3);
        Sphere sphere = new Sphere(new Point3D(0, 0, -1),0.5);
        List<Point3D> intersections=new ArrayList<Point3D>();
        int count = 0;


        for (int i=0;i<3;i++) {
            for (int j = 0; j < 3; j++) {
                intersections = sphere.findIntersections(cam.constructRayThroughPixel(3, 3, j, i));
                if (intersections != null)
                    count+=intersections.size();
            }
        }

        if (intersections != null)
            assertEquals(0,count,"wrong intersection");
    }

    public void constructRayThroughPixelWithPlane1()
    {
        Plane plane =  new Plane(new Point3D(0, 0, 1.25), new Vector(0, 0, 1));

        List<Point3D> results;
        int count = 0;

        int Nx = 3;
        int Ny = 3;

        for (int i = 0; i < Ny; i++)
        {
            for (int j = 0; j < Nx; j ++)
            {
                Ray ray = cam1.constructRayThroughPixel(Nx, Ny, j, i);
                results = plane.findIntersections(ray);

                if (results != null)
                    count += results.size();
            }
        }

        assertEquals(9, count,"ERROR, wrong number of intersections with plane");
        System.out.println("count: "+ count);



    }

    /** Plane TC:2
     * a function that checks if we can find intersection with plane2
     */
    @Test
    public void constructRayThroughPixelWithPlane2()
    {
        Plane plane =  new Plane(new Point3D(0, 0, 9), new Vector(0,-0.5, 1));

        List<Point3D> results;
        int count = 0;

        int Nx = 3;
        int Ny = 3;

        for (int i = 0; i < Ny; ++i)
        {
            for (int j = 0; j < Nx; ++j)
            {
                Ray ray = cam1.constructRayThroughPixel(Nx, Ny, j, i);
                results = plane.findIntersections(ray);
                if (results != null)
                    count += results.size();
            }
        }

        assertEquals(9, count,"ERROR, wrong number of intersections with plane");
        System.out.println("count: "+ count);

    }

    /** Plane TC:3
     * a function that checks if we can find intersection with plane3
     */
    @Test
    public void constructRayThroughPixelWithPlane3()
    {
        Plane plane =  new Plane(new Point3D(0, 0, 8), new Vector(0,-3, 1));

        List<Point3D> results;
        int count = 0;

        int Nx = 3;
        int Ny = 3;

        for (int i = 0; i < Ny; ++i)
        {
            for (int j = 0; j < Nx; ++j)
            {
                Ray ray = cam1.constructRayThroughPixel(Nx, Ny, j, i);
                results = plane.findIntersections(ray);
                if (results != null)
                    count += results.size();
            }
        }

        assertEquals( 6, count,"ERROR, wrong number of intersections with plane");
        System.out.println("count: "+ count);
    }


    public void constructRayThroughPixelWithTriangle1()
    {
        Triangle triangle =  new Triangle(new Point3D(0, -1, 2), new Point3D(1,1, 2), new Point3D(-1,1,2));
        Camera cam1 = new Camera(Point3D.ZERO, new Vector(0, 0, 1), new Vector(0, -1, 0)).setDistance(1).setViewPlaneSize(3,3);

        List<Point3D> results;
        int count = 0;

        int Nx = 3;
        int Ny = 3;

        for (int i = 0; i < Ny; ++i)
        {
            for (int j = 0; j < Nx; ++j)
            {
                Ray ray = cam1.constructRayThroughPixel(Nx, Ny, j, i);
                results = triangle.findIntersections(ray);

                if (results != null)
                    count += results.size();
            }
        }

        assertEquals(1, count,"ERROR, wrong number of intersections with triangle");
        System.out.println("count: "+ count);
    }


    @Test
    public void constructRayThroughPixelWithTriangle2()
    {
        Triangle triangle =  new Triangle(new Point3D(1, 1, 2), new Point3D(-1,1, 2), new Point3D(0,-20,2));
        Camera cam1 = new Camera(Point3D.ZERO, new Vector(0, 0, 1), new Vector(0, -1, 0)).setDistance(1).setViewPlaneSize(3,3);

        List<Point3D> results;
        int count = 0;

        int Nx = 3;
        int Ny = 3;

        for (int i = 0; i < Ny; ++i)
        {
            for (int j = 0; j < Nx; ++j)
            {
                Ray ray = cam1.constructRayThroughPixel(Nx, Ny, j, i);
                results = triangle.findIntersections(ray);
                if (results != null)
                    count += results.size();
            }
        }

        assertEquals(2, count,"ERROR, wrong number of intersections with triangle");
        System.out.println("count: "+ count);
    }



}
