package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class CylinderTest {

    @Test
    void testGetNormal() {
        Point3D p0= new Point3D(0,0,10);
        Point3D p1= new Point3D(0,5,10);
        Point3D p2= new Point3D(0,5,35);
        Point3D p3= new Point3D(0,5,50);
        Point3D p4= new Point3D(0,0,50);
        Vector dir = new Vector(0,0,1);

        Ray ray= new Ray(p0,dir);
        Cylinder cylinder = new Cylinder(ray,10,40);
       // tc1 ep



        Vector expectedNormal=dir;
        Vector normal = cylinder.getNormal(p1);
        assertEquals(normal, expectedNormal, "ERROR: Not the same : getNormal() wrong result");

        //tc2
        double t2= dir.dotProduct(p2.subtract(p0));
        Point3D o2= p0.add(dir.scale(t2));
        Vector expectedNormal2=(p2.subtract(o2)).normalize();
        Vector normal2 = cylinder.getNormal(p2);
        assertEquals(normal2, expectedNormal2, "ERROR: Not the same : getNormal() wrong result");

        //tc3

        Vector expectedNormal3=dir;
        Vector normal3 = cylinder.getNormal(p3);
        assertEquals(normal3, expectedNormal3, "ERROR: Not the same : getNormal() wrong result");
     // tc4 BDA p4 centre du cercle du haut

        Vector expectedNormal4=dir;
        //
        Vector normal4 = cylinder.getNormal(p4);

        assertEquals(normal4, expectedNormal4, "ERROR: Not the same : getNormal() wrong result");

        // tc5 BDA meme centre que p0
        assertThrows(

                IllegalArgumentException.class,
                () -> cylinder.getNormal(p0), "ERROR : vector 0");

        }
}