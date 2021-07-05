package renderer;


import elements.*;
import geometries.*;
import geometries.Polygon;
import org.junit.jupiter.api.Test;
import primitives.*;
import primitives.Color;
import scene.Scene;

import java.awt.*;

/**
 * Test rendering a basic image
 *
 * @author Dan
 */
public class RenderTests {


    private Camera camera = new Camera(Point3D.ZERO, new Vector(0, 0, -1), new Vector(0, 1, 0)) //
            .setDistance(100) //
            .setViewPlaneSize(500, 500);


    //AJOUT ESSAI POUR LE DERNIER
    private Scene _scene = new Scene("Test scene") //
            .setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));
    private Camera camera1 = new Camera(new Point3D(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
            .setViewPlaneSize(150, 150) //
            .setDistance(1000);

    private Camera camera2 = new Camera(new Point3D(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
            .setViewPlaneSize(600, 600) //
            .setDistance(1000); // 200,200 pour 1000,1000
     // Nx Ny 500,500 pour 1000,1000

    private Camera camera3 = new Camera(new Point3D(0, -1500, 600), new Vector(0, 1, 0), new Vector(0, 0, 1)) //
            .setViewPlaneSize(500, 500) //
            .setDistance(700);



    /**
     * Produce a scene with basic 3D model and render it into a png image with a
     * grid
     */
    @Test
    public void basicRenderTwoColorTest() {
        Scene scene = new Scene("Test scene")//
                .setAmbientLight(new AmbientLight(new Color(255, 191, 191), 1)) //
                .setBackground(new Color(75, 127, 90));

        scene.geometries.add(new Sphere(50, new Point3D(0, 0, -100)),
                new Triangle(new Point3D(-100, 0, -100), new Point3D(0, 100, -100), new Point3D(-100, 100, -100)), // up
                // left
                new Triangle(new Point3D(100, 0, -100), new Point3D(0, 100, -100), new Point3D(100, 100, -100)), // up
                // right
                new Triangle(new Point3D(-100, 0, -100), new Point3D(0, -100, -100), new Point3D(-100, -100, -100)), // down
                // left
                new Triangle(new Point3D(100, 0, -100), new Point3D(0, -100, -100), new Point3D(100, -100, -100))); // down
        // right

        ImageWriter imageWriter = new ImageWriter("base render test", 1000, 1000);
        Render render = new Render() //
                .setImageWriter(imageWriter) //
                .setCamera(camera) //
                .setRayTracer(new BasicRayTracer(scene));

        render.renderImage();
        render.printGrid(100, new Color(java.awt.Color.YELLOW));
        render.writeToImage();
    }

    /**
     * Test for XML based scene - for bonus
     */
     /*   @Test
    public void basicRenderXml() {
        Scene scene = new Scene("XML Test scene");
        // enter XML file name and parse from XML file into scene object
        // ...

        ImageWriter imageWriter = new ImageWriter("xml render test", 1000, 1000);
        Render render = new Render() //
                .setImageWriter(imageWriter) //
                .setCamera(camera) //
                .setRayTracer(new BasicRayTracer(scene));

        render.renderImage();
        render.printGrid(100, new Color(java.awt.Color.YELLOW));
        render.writeToImage();
    }*/

    // For stage 6 - please disregard in stage 5

    /**
     * Produce a scene with basic 3D model - including individual lights of the bodies
     * and render it into a png image with a grid
     */

    @Test
    public void basicRenderMultiColorTest() {
        Scene scene = new Scene("Test scene")//
                .setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.2)); //

        scene.geometries.add(new Sphere(50, new Point3D(0, 0, -100)) //
                        .setEmission(new Color(java.awt.Color.CYAN)), //
                new Triangle(new Point3D(-100, 0, -100), new Point3D(0, 100, -100), new Point3D(-100, 100, -100)) // up left
                        .setEmission(new Color(java.awt.Color.GREEN)),
                new Triangle(new Point3D(100, 0, -100), new Point3D(0, 100, -100), new Point3D(100, 100, -100)), // up right
                new Triangle(new Point3D(-100, 0, -100), new Point3D(0, -100, -100), new Point3D(-100, -100, -100)) // down left
                        .setEmission(new Color(java.awt.Color.RED)),
                new Triangle(new Point3D(100, 0, -100), new Point3D(0, -100, -100), new Point3D(100, -100, -100)) // down right
                        .setEmission(new Color(java.awt.Color.BLUE)));

        ImageWriter imageWriter = new ImageWriter("color render test", 1000, 1000);
        Render render = new Render() //
                .setImageWriter(imageWriter) //
                .setCamera(camera) //
                .setRayTracer(new BasicRayTracer(scene));

        render.renderImage();
        render.printGrid(100, new Color(java.awt.Color.WHITE));
        render.writeToImage();
    }




    @Test
    public void rayTracingTest2() {

        Scene scene2 = new Scene("Test scene")//
                .setAmbientLight(new AmbientLight(new Color(255, 255, 255), 0.1)) //
                .setBackground(new Color(0, 0, 0));


        // (double _kD, double _kS, int _nShininess, double _kT, double _kR)

        scene2.geometries.add(new Sphere(20, new Point3D(75, -120, +75))// GOLD
                .setEmission(new Color(212, 175, 55))
                .setMaterial(new Material().setKd(0.3).setKs(0.2).setShininess(5).setKt(0).setKr(0.5)));

        scene2.geometries.add(new Sphere(15, new Point3D(160, -165, -100))// BLUE
                .setEmission(new Color(53, 187, 202))
                .setMaterial(new Material().setKd(0.25).setKs(0.3).setShininess(5)));

        scene2.geometries.add(new Sphere(25, new Point3D(0, -130, -90))// RED
                .setEmission(new Color(255, 99, 71))
                .setMaterial(new Material().setKd(0.25).setKs(0.3).setShininess(5).setKr(0.5)));


        scene2.geometries.add(new Sphere(25, new Point3D(0, -130, +60))//ORANGE
                .setEmission(new Color(255, 164, 71))
                .setMaterial(new Material().setKd(0.25).setKs(0.3).setShininess(5).setKr(0.5)));

        scene2.geometries.add(new Sphere(50, new Point3D(-75, +50, -80))//BIG BLUE
                .setEmission(new Color(0, 128, 85))
                .setMaterial(new Material().setKd(0.3).setKs(0.2).setShininess(5).setKr(0.1)));


        scene2.geometries.add(new Sphere(50, new Point3D(75, 50, -80))// BIG TEAL
                .setEmission(new Color(0, 128, 128))
                .setMaterial(new Material().setKd(0.3).setKs(0.2).setShininess(5).setKr(0.1)));

        scene2.geometries.add(new Sphere(20, new Point3D(-75, -120, 75))// SILVER
                .setEmission(new Color(170, 169, 173))
                .setMaterial(new Material().setKd(0.3).setKs(0.4).setShininess(5).setKr(0.5)));

        scene2.geometries.add(new Sphere(15, new Point3D(-160, -165, -100))// GREEN
                .setEmission(new Color(53, 202, 93))
                .setMaterial(new Material().setKd(0.3).setKs(0.4).setShininess(5).setKt(0.25).setKr(0)));


        scene2.lights.add(new DirectionalLight(new Color(240, 240, 240), new Vector(0, -1, 0)));
        scene2.lights.add(new PointLight(new Color(230, 230, 250), new Point3D(-160, -165, -100)));
        scene2.lights.add(new PointLight(new Color(230, 230, 250), new Point3D(160, -165, -100)));
        scene2.lights.add(new SpotLight(new Color(130,100,130), new Point3D(0, -30, 50), new Vector(0, 1, 0)) //
                .setKl(4E-5).setKq(2E-7));


        ImageWriter imageWriter = new ImageWriter("ray tracing 2", 500, 500);
        Render render = new Render() //
                .setImageWriter(imageWriter) //
                .setCamera(camera2) //
                .setRayTracer(new BasicRayTracer(scene2)); //.set_numOfRays(5).setRadius(40).set_rayDistance(5));

        render.renderImage();
        render.writeToImage();

    }

//    @Test
//    public void rayTracingTest3() {
//
//        Scene scene2 = new Scene("Test scene")//
//                .setAmbientLight(new AmbientLight(new Color(255, 255, 255), 0.1)) //
//                .setBackground(new Color(0, 0, 0));
//
//
//        // (double _kD, double _kS, int _nShininess, double _kT, double _kR)
//
//        scene2.geometries.add(new Sphere(20, new Point3D(75, -120, +75))// GOLD
//                .setEmission(new Color(212, 175, 55))
//                .setMaterial(new Material().setKd(0.3).setKs(0.4).setShininess(5).setKt(0).setKr(0.75)));
//
//        scene2.geometries.add(new Sphere(15, new Point3D(160, -165, -100))// BLUE
//                .setEmission(new Color(53, 187, 202))
//                .setMaterial(new Material().setKd(0.25).setKs(0.3).setShininess(5).setKt(0.5).setKr(0.6)));
//
//        scene2.geometries.add(new Sphere(25, new Point3D(0, -130, -90))// RED
//                .setEmission(new Color(255, 99, 71))
//                .setMaterial(new Material().setKd(0.25).setKs(0.3).setShininess(100).setKt(0.5).setKr(0.5)));
//
//
//        scene2.geometries.add(new Sphere(25, new Point3D(0, -130, +60))//ORANGE
//                .setEmission(new Color(255, 164, 71))
//                .setMaterial(new Material().setKd(0.25).setKs(0.3).setShininess(100).setKt(0.5).setKr(0.5)));
//
//        scene2.geometries.add(new Sphere(50, new Point3D(-75, +50, -80))//BIG BLUE
//                .setEmission(new Color(0, 128, 85))
//                .setMaterial(new Material().setKd(0.4).setKs(0.35).setShininess(5).setKt(0.2).setKr(0.8)));
//
//
//        scene2.geometries.add(new Sphere(50, new Point3D(75, 50, -80))//
//                .setEmission(new Color(0, 128, 128))
//                .setMaterial(new Material().setKd(0.4).setKs(0.35).setShininess(5).setKt(0.2).setKr(0.8)));
//
//        scene2.geometries.add(new Sphere(20, new Point3D(-75, -120, 75))// SILVER
//                .setEmission(new Color(170, 169, 173))
//                .setMaterial(new Material().setKd(0.3).setKs(0.4).setShininess(5).setKr(0.75)));
//
//        scene2.geometries.add(new Sphere(15, new Point3D(-160, -165, -100))// GREEN
//                .setEmission(new Color(53, 202, 93))
//                .setMaterial(new Material().setKd(0.3).setKs(0.4).setShininess(5).setKt(0.5).setKr(0.6)));
//
//        scene2.geometries.add(new Polygon(new Point3D(200,200,-200),new Point3D(200,-200,-200),
//                new Point3D(-200,-200,-200),new Point3D(-200,200,-200))
//                .setEmission(new Color(0, 0, 0)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(5).setKt(0).setKr(0.5)));
//
//
//
//        scene2.lights.add(new DirectionalLight(new Color(300, 255, 255), new Vector(0, -1, 0)));
//        scene2.lights.add(new PointLight(new Color(230, 230, 250), new Point3D(-160, -165, -100)));
//        scene2.lights.add(new PointLight(new Color(230, 230, 250), new Point3D(160, -165, -100)));
//        scene2.lights.add(new SpotLight(new Color(130,100,130), new Point3D(0, -30, 50), new Vector(0, 1, 0)) //
//        .setKl(4E-5).setKq(2E-7));
//
//
//        ImageWriter imageWriter = new ImageWriter("ray tracing 3", 500, 500);
//        Render render = new Render() //
//                .setImageWriter(imageWriter) //
//                .setCamera(camera2) //
//                .setRayTracer(new BasicRayTracer(scene2)); //.set_numOfRays(5).setRadius(40).set_rayDistance(5));
//
//        render.renderImage();
//        render.writeToImage();
//
//    }

    @Test
    public void rayTracingTest4() {

        Scene scene2 = new Scene("Test scene")//
                .setAmbientLight(new AmbientLight(new Color(255, 255, 255), 0.15)) //
                .setBackground(new Color(0, 0, 0));


        // (double _kD, double _kS, int _nShininess, double _kT, double _kR)

        scene2.geometries.add(new Sphere(20, new Point3D(125, -36, 50))// violet
                .setEmission(new Color(108, 2, 119))
                .setMaterial(new Material().setKd(0.3).setKs(0.2).setShininess(5).setKt(0).setKr(0)));

        scene2.geometries.add(new Sphere(20, new Point3D(125, 100, 50))// orange
                .setEmission(new Color(231, 62, 1))
                .setMaterial(new Material().setKd(0.25).setKs(0.3).setShininess(5).setKt(0.22).setKr(0)));

        scene2.geometries.add(new Sphere(25, new Point3D(0, -130, -90))// bleu milieu en bas
                .setEmission(new Color(0, 128, 128))
                .setMaterial(new Material().setKd(0.25).setKs(0.3).setShininess(5).setKt(0).setKr(0.3)));


        scene2.geometries.add(new Sphere(25, new Point3D(0, -130, +90))//vert milieu dessus
                .setEmission(new Color(0, 128, 85)) //0 128 85
                .setMaterial(new Material().setKd(0.25).setKs(0.3).setShininess(5).setKt(0).setKr(0.3)));

        scene2.geometries.add(new Sphere(50, new Point3D(-63, +30, -200))//BIG BLUE
                .setEmission(new Color(15, 5, 107))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(5).setKt(0).setKr(0.5)));


        scene2.geometries.add(new Sphere(50, new Point3D(48, 30, -200))// BIG JAUNE
                .setEmission(new Color(212, 175, 55))
                .setMaterial(new Material().setKd(0.3).setKs(0.2).setShininess(5).setKt(0).setKr(0.1)));

        scene2.geometries.add(new Sphere(20, new Point3D(-180, 90, -70))// red
                .setEmission(new Color(255, 0, 0))
                .setMaterial(new Material().setKd(0.3).setKs(0.2).setShininess(5).setKr(0)));

        scene2.geometries.add(new Sphere(20, new Point3D(-180, -36, -70))// GREEN a gauche
                .setEmission(new Color(34, 80, 30))
                .setMaterial(new Material().setKd(0.3).setKs(0.4).setShininess(5).setKt(0.22).setKr(0)));

        /*scene2.geometries.add(new Plane(new Point3D(0,0,-210),new Vector(0,0,1))
                .setEmission(new Color(20, 20, 20)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(5).setKt(0).setKr(0.5)));
*/
      //en haut a droite
        scene2.geometries.add(new Polygon(new Point3D(250,250,-250),new Point3D(187.5,250,-250),
             new Point3D(187.5,187.5,-250),new Point3D(250,187.5,-250))
              .setEmission(new Color(0, 0, 0)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(5).setKt(0).setKr(0.5)));

        scene2.geometries.add(new Polygon(new Point3D(187.5,250,-250),new Point3D(125,250,-250),
                new Point3D(125,187.5,-250),new Point3D(187.5,187.5,-250))
                .setEmission(new Color(96, 96, 96)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(5).setKt(0).setKr(0.5)));

        scene2.geometries.add(new Polygon(new Point3D(125,250,-250),new Point3D(62.5,250,-250),
                new Point3D(62.5,187.5,-250),new Point3D(125,187.5,-250))
                .setEmission(new Color(0, 0, 0)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(5).setKt(0).setKr(0.5)));

        scene2.geometries.add(new Polygon(new Point3D(62.5,250,-250),new Point3D(0,250,-250),
                new Point3D(0,187.5,-250),new Point3D(62.5,187.5,-250))
                .setEmission(new Color(96, 96, 96)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(5).setKt(0).setKr(0.5)));

        scene2.geometries.add(new Polygon(new Point3D(0,250,-250),new Point3D(-62.5,250,-250),
                new Point3D(-62.5,187.5,-250),new Point3D(0,187.5,-250))
                .setEmission(new Color(0, 0, 0)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(5).setKt(0).setKr(0.5)));

        scene2.geometries.add(new Polygon(new Point3D(-62.5,250,-250),new Point3D(-125,250,-250),
                new Point3D(-125,187.5,-250),new Point3D(-62.5,187.5,-250))
                .setEmission(new Color(96, 96, 96)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(5).setKt(0).setKr(0.5)));

        scene2.geometries.add(new Polygon(new Point3D(-125,250,-250),new Point3D(-187.5,250,-250),
                new Point3D(-187.5,187.5,-250),new Point3D(-125,187.5,-250))
                .setEmission(new Color(0, 0, 0)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(5).setKt(0).setKr(0.5)));

        scene2.geometries.add(new Polygon(new Point3D(-187.5,250,-250),new Point3D(-250,250,-250),
                new Point3D(-250,187.5,-250),new Point3D(-187.5,187.5,-250))
                .setEmission(new Color(96, 96, 96)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(5).setKt(0).setKr(0.5)));
 //second line
        scene2.geometries.add(new Polygon(new Point3D(250,187.5,-250),new Point3D(187.5,187.5,-250),
                new Point3D(187.5,125,-250),new Point3D(250,125,-250))
                .setEmission(new Color(96, 96, 96)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(5).setKt(0).setKr(0.5)));
        scene2.geometries.add(new Polygon(new Point3D(187.5,187.5,-250),new Point3D(125,187.5,-250),
                new Point3D(125,125,-250),new Point3D(187.5,125,-250))
                .setEmission(new Color(0, 0, 0)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(5).setKt(0).setKr(0.5)));

        scene2.geometries.add(new Polygon(new Point3D(125,187.5,-250),new Point3D(62.5,187.5,-250),
                new Point3D(62.5,125,-250),new Point3D(125,125,-250))
                .setEmission(new Color(96, 96, 96)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(5).setKt(0).setKr(0.5)));

        scene2.geometries.add(new Polygon(new Point3D(62.5,187.5,-250),new Point3D(0,187.5,-250),
                new Point3D(0,125,-250),new Point3D(62.5,125,-250))
                .setEmission(new Color(0, 0, 0)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(5).setKt(0).setKr(0.5)));

        scene2.geometries.add(new Polygon(new Point3D(0,187.5,-250),new Point3D(-62.5,187.5,-250),
                new Point3D(-62.5,125,-250),new Point3D(0,125,-250))
                .setEmission(new Color(96, 96, 96)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(5).setKt(0).setKr(0.5)));
        scene2.geometries.add(new Polygon(new Point3D(-62.5,187.5,-250),new Point3D(-125,187.5,-250),
                new Point3D(-125,125,-250),new Point3D(-62.5,125,-250))
                .setEmission(new Color(0, 0, 0)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(5).setKt(0).setKr(0.5)));

        scene2.geometries.add(new Polygon(new Point3D(-125,187.5,-250),new Point3D(-187.5,187.5,-250),
                new Point3D(-187.5,125,-250),new Point3D(-125,125,-250))
                .setEmission(new Color(96, 96, 96)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(5).setKt(0).setKr(0.5)));
        scene2.geometries.add(new Polygon(new Point3D(-187.5,187.5,-250),new Point3D(-250,187.5,-250),
                new Point3D(-250,125,-250),new Point3D(-187.5,125,-250))
                .setEmission(new Color(0, 0, 0)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(5).setKt(0).setKr(0.5)));

        //ligne 3

        scene2.geometries.add(new Polygon(new Point3D(250,125,-250),new Point3D(187.5,125,-250),
                new Point3D(187.5,62.5,-250),new Point3D(250,62.5,-250))
                .setEmission(new Color(0, 0, 0)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(5).setKt(0).setKr(0.5)));

        scene2.geometries.add(new Polygon(new Point3D(187.5,125,-250),new Point3D(125,125,-250),
                new Point3D(125,62.5,-250),new Point3D(187.5,62.5,-250))
                .setEmission(new Color(96, 96, 96)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(5).setKt(0).setKr(0.5)));


        scene2.geometries.add(new Polygon(new Point3D(125,125,-250),new Point3D(62.5,125,-250),
                new Point3D(62.5,62.5,-250),new Point3D(125,62.5,-250))
                .setEmission(new Color(0, 0, 0)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(5).setKt(0).setKr(0.5)));

        scene2.geometries.add(new Polygon(new Point3D(62.5,125,-250),new Point3D(0,125,-250),
                new Point3D(0,62.5,-250),new Point3D(62.5,62.5,-250))
                .setEmission(new Color(96, 96, 96)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(5).setKt(0).setKr(0.5)));

        scene2.geometries.add(new Polygon(new Point3D(0,125,-250),new Point3D(-62.5,125,-250),
                new Point3D(-62.5,62.5,-250),new Point3D(0,62.5,-250))
                .setEmission(new Color(0, 0, 0)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(5).setKt(0).setKr(0.5)));

        scene2.geometries.add(new Polygon(new Point3D(-62.5,125,-250),new Point3D(-125,125,-250),
                new Point3D(-125,62.5,-250),new Point3D(-62.5,62.5,-250))
                .setEmission(new Color(96, 96, 96)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(5).setKt(0).setKr(0.5)));

        scene2.geometries.add(new Polygon(new Point3D(-125,125,-250),new Point3D(-187.5,125,-250),
                new Point3D(-187.5,62.5,-250),new Point3D(-125,62.5,-250))
                .setEmission(new Color(0, 0, 0)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(5).setKt(0).setKr(0.5)));

        scene2.geometries.add(new Polygon(new Point3D(-187.5,125,-250),new Point3D(-250,125,-250),
                new Point3D(-250,62.5,-250),new Point3D(-187.5,62.5,-250))
                .setEmission(new Color(96, 96, 96)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(5).setKt(0).setKr(0.5)));

        //ligne 4
       scene2.geometries.add(new Polygon(new Point3D(250,62.5,-250),new Point3D(187.5,62.5,-250),
                new Point3D(187.5,0,-250),new Point3D(250,0,-250))
                .setEmission(new Color(96, 96, 96)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(5).setKt(0).setKr(0.5)));


        scene2.geometries.add(new Polygon(new Point3D(187.5,62.5,-250),new Point3D(125,62.5,-250),
                new Point3D(125,0,-250),new Point3D(187.5,0,-250))
                .setEmission(new Color(0, 0, 0)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(5).setKt(0).setKr(0.5)));

        scene2.geometries.add(new Polygon(new Point3D(125,62.5,-250),new Point3D(62.5,62.5,-250),
                new Point3D(62.5,0,-250),new Point3D(125,0,-250))
                .setEmission(new Color(96, 96, 96)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(5).setKt(0).setKr(0.5)));


        scene2.geometries.add(new Polygon(new Point3D(62.5,62.5,-250),new Point3D(0,62.5,-250),
                new Point3D(0,0,-250),new Point3D(62.5,0,-250))
                .setEmission(new Color(0, 0, 0)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(5).setKt(0).setKr(0.5)));

        scene2.geometries.add(new Polygon(new Point3D(0,62.5,-250),new Point3D(-62.5,62.5,-250),
                new Point3D(-62.5,0,-250),new Point3D(0,0,-250))
                .setEmission(new Color(96, 96, 96)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(5).setKt(0).setKr(0.5)));

        scene2.geometries.add(new Polygon(new Point3D(-62.5,62.5,-250),new Point3D(-125,62.5,-250),
                new Point3D(-125,0,-250),new Point3D(-62.5,0,-250))
                .setEmission(new Color(0, 0, 0)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(5).setKt(0).setKr(0.5)));


        scene2.geometries.add(new Polygon(new Point3D(-125,62.5,-250),new Point3D(-187.5,62.5,-250),
                new Point3D(-187.5,0,-250),new Point3D(-125,0,-250))
                .setEmission(new Color(96, 96, 96)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(5).setKt(0).setKr(0.5)));

        scene2.geometries.add(new Polygon(new Point3D(-187.5,62.5,-250),new Point3D(-250,62.5,-250),
                new Point3D(-250,0,-250),new Point3D(-187.5,0,-250))
                .setEmission(new Color(0, 0, 0)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(5).setKt(0).setKr(0.5)));


//ligne 5

        scene2.geometries.add(new Polygon(new Point3D(250,0,-250),new Point3D(187.5,0,-250),
                new Point3D(187.5,-62.5,-250),new Point3D(250,-62.5,-250))
                .setEmission(new Color(0, 0, 0)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(5).setKt(0).setKr(0.5)));

        scene2.geometries.add(new Polygon(new Point3D(187.5,0,-250),new Point3D(125,0,-250),
                new Point3D(125,-62.5,-250),new Point3D(187.5,-62.5,-250))
                .setEmission(new Color(96, 96, 96)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(5).setKt(0).setKr(0.5)));

        scene2.geometries.add(new Polygon(new Point3D(125,0,-250),new Point3D(62.5,0,-250),
                new Point3D(62.5,-62.5,-250),new Point3D(125,-62.5,-250))
                .setEmission(new Color(0, 0, 0)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(5).setKt(0).setKr(0.5)));

        scene2.geometries.add(new Polygon(new Point3D(62.5,0,-250),new Point3D(0,0,-250),
                new Point3D(0,-62.5,-250),new Point3D(62.5,-62.5,-250))
                .setEmission(new Color(96, 96, 96)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(5).setKt(0).setKr(0.5)));

        scene2.geometries.add(new Polygon(new Point3D(0,0,-250),new Point3D(-62.5,0,-250),
                new Point3D(-62.5,-62.5,-250),new Point3D(0,-62.5,-250))
                .setEmission(new Color(0, 0, 0)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(5).setKt(0).setKr(0.5)));

        scene2.geometries.add(new Polygon(new Point3D(-62.5,0,-250),new Point3D(-125,0,-250),
                new Point3D(-125,-62.5,-250),new Point3D(-62.5,-62.5,-250))
                .setEmission(new Color(96, 96, 96)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(5).setKt(0).setKr(0.5)));

        scene2.geometries.add(new Polygon(new Point3D(-125,0,-250),new Point3D(-187.5,0,-250),
                new Point3D(-187.5,-62.5,-250),new Point3D(-125,-62.5,-250))
                .setEmission(new Color(0, 0, 0)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(5).setKt(0).setKr(0.5)));

        scene2.geometries.add(new Polygon(new Point3D(-187.5,0,-250),new Point3D(-250,0,-250),
                new Point3D(-250,-62.5,-250),new Point3D(-187.5,-62.5,-250))
                .setEmission(new Color(96, 96, 96)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(5).setKt(0).setKr(0.5)));

    //ligne 6

        scene2.geometries.add(new Polygon(new Point3D(250,-62.5,-250),new Point3D(187.5,-62.5,-250),
                new Point3D(187.5,-125,-250),new Point3D(250,-125,-250))
                .setEmission(new Color(96, 96, 96)).setMaterial(new Material().setKd(0.1).setKs(0.1).setShininess(5).setKt(0).setKr(0.7)));

        scene2.geometries.add(new Polygon(new Point3D(187.5,-62.5,-250),new Point3D(125,-62.5,-250),
                new Point3D(125,-125,-250),new Point3D(187.5,-125,-250))
                .setEmission(new Color(0, 0, 0)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(5).setKt(0).setKr(0.5)));

        scene2.geometries.add(new Polygon(new Point3D(125,-62.5,-250),new Point3D(62.5,-62.5,-250),
                new Point3D(62.5,-125,-250),new Point3D(125,-125,-250))
                .setEmission(new Color(96, 96, 96)).setMaterial(new Material().setKd(0.1).setKs(0.1).setShininess(5).setKt(0).setKr(0.7)));


        scene2.geometries.add(new Polygon(new Point3D(62.5,-62.5,-250),new Point3D(0,-62.5,-250),
                new Point3D(0,-125,-250),new Point3D(62.5,-125,-250))
                .setEmission(new Color(0, 0, 0)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(5).setKt(0).setKr(0.5)));


        scene2.geometries.add(new Polygon(new Point3D(0,-62.5,-250),new Point3D(-62.5,-62.5,-250),
                new Point3D(-62.5,-125,-250),new Point3D(0,-125,-250))
                .setEmission(new Color(96, 96, 96)).setMaterial(new Material().setKd(0.1).setKs(0.1).setShininess(5).setKt(0).setKr(0.7)));

        scene2.geometries.add(new Polygon(new Point3D(-62.5,-62.5,-250),new Point3D(-125,-62.5,-250),
                new Point3D(-125,-125,-250),new Point3D(-62.5,-125,-250))
                .setEmission(new Color(0, 0, 0)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(5).setKt(0).setKr(0.5)));

        scene2.geometries.add(new Polygon(new Point3D(-125,-62.5,-250),new Point3D(-187.5,-62.5,-250),
                new Point3D(-187.5,-125,-250),new Point3D(-125,-125,-250))
                .setEmission(new Color(96, 96, 96)).setMaterial(new Material().setKd(0.1).setKs(0.1).setShininess(5).setKt(0).setKr(0.7)));

        scene2.geometries.add(new Polygon(new Point3D(-187.5,-62.5,-250),new Point3D(-250,-62.5,-250),
                new Point3D(-250,-125,-250),new Point3D(-187.5,-125,-250))
                .setEmission(new Color(0, 0, 0)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(5).setKt(0).setKr(0.5)));


        //ligne 7
        scene2.geometries.add(new Polygon(new Point3D(250,-125,-250),new Point3D(187.5,-125,-250),
                new Point3D(187.5,-187.5,-250),new Point3D(250,-187.5,-250))
                .setEmission(new Color(0, 0, 0)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(5).setKt(0).setKr(0.5)));

        scene2.geometries.add(new Polygon(new Point3D(187.5,-125,-250),new Point3D(125,-125,-250),
                new Point3D(125,-187.5,-250),new Point3D(187.5,-187.5,-250))
                .setEmission(new Color(96, 96, 96)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(5).setKt(0).setKr(0.5)));

        scene2.geometries.add(new Polygon(new Point3D(125,-125,-250),new Point3D(62.5,-125,-250),
                new Point3D(62.5,-187.5,-250),new Point3D(125,-187.5,-250))
                .setEmission(new Color(0, 0, 0)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(5).setKt(0).setKr(0.5)));

        scene2.geometries.add(new Polygon(new Point3D(62.5,-125,-250),new Point3D(0,-125,-250),
                new Point3D(0,-187.5,-250),new Point3D(62.5,-187.5,-250))
                .setEmission(new Color(96, 96, 96)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(5).setKt(0).setKr(0.5)));

        scene2.geometries.add(new Polygon(new Point3D(0,-125,-250),new Point3D(-62.5,-125,-250),
                new Point3D(-62.5,-187.5,-250),new Point3D(0,-187.5,-250))
                .setEmission(new Color(0, 0, 0)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(5).setKt(0).setKr(0.5)));

        scene2.geometries.add(new Polygon(new Point3D(-62.5,-125,-250),new Point3D(-125,-125,-250),
                new Point3D(-125,-187.5,-250),new Point3D(-62.5,-187.5,-250))
                .setEmission(new Color(96, 96, 96)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(5).setKt(0).setKr(0.5)));

        scene2.geometries.add(new Polygon(new Point3D(-125,-125,-250),new Point3D(-187.5,-125,-250),
                new Point3D(-187.5,-187.5,-250),new Point3D(-125,-187.5,-250))
                .setEmission(new Color(0, 0, 0)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(5).setKt(0).setKr(0.5)));

        scene2.geometries.add(new Polygon(new Point3D(-187.5,-125,-250),new Point3D(-250,-125,-250),
                new Point3D(-250,-187.5,-250),new Point3D(-187.5,-187.5,-250))
                .setEmission(new Color(96, 96, 96)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(5).setKt(0).setKr(0.5)));

//ligne8

        scene2.geometries.add(new Polygon(new Point3D(250,-187.5,-250),new Point3D(187.5,-187.5,-250),
                new Point3D(187.5,-250,-250),new Point3D(250,-250,-250))
                .setEmission(new Color(96, 96, 96)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(5).setKt(0).setKr(0.5)));

        scene2.geometries.add(new Polygon(new Point3D(187.5,-187.5,-250),new Point3D(125,-187.5,-250),
                new Point3D(125,-250,-250),new Point3D(187.5,-250,-250))
                .setEmission(new Color(0, 0, 0)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(5).setKt(0).setKr(0.5)));

        scene2.geometries.add(new Polygon(new Point3D(125,-187.5,-250),new Point3D(62.5,-187.5,-250),
                new Point3D(62.5,-250,-250),new Point3D(125,-250,-250))
                .setEmission(new Color(96, 96, 96)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(5).setKt(0).setKr(0.5)));

        scene2.geometries.add(new Polygon(new Point3D(62.5,-187.5,-250),new Point3D(0,-187.5,-250),
                new Point3D(0,-250,-250),new Point3D(62.5,-250,-250))
                .setEmission(new Color(0, 0, 0)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(5).setKt(0).setKr(0.5)));


        scene2.geometries.add(new Polygon(new Point3D(0,-187.5,-250),new Point3D(-62.5,-187.5,-250),
                new Point3D(-62.5,-250,-250),new Point3D(0,-250,-250))
                .setEmission(new Color(96, 96, 96)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(5).setKt(0).setKr(0.5)));


        scene2.geometries.add(new Polygon(new Point3D(-62.5,-187.5,-250),new Point3D(-125,-187.5,-250),
                new Point3D(-125,-250,-250),new Point3D(-62.5,-250,-250))
                .setEmission(new Color(0, 0, 0)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(5).setKt(0).setKr(0.5)));

        scene2.geometries.add(new Polygon(new Point3D(-125,-187.5,-250),new Point3D(-187.5,-187.5,-250),
                new Point3D(-187.5,-250,-250),new Point3D(-125,-250,-250))
                .setEmission(new Color(96, 96, 96)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(5).setKt(0).setKr(0.5)));


        scene2.geometries.add(new Polygon(new Point3D(-187.5,-187.5,-250),new Point3D(-250,-187.5,-250),
                new Point3D(-250,-250,-250),new Point3D(-187.5,-250,-250))
                .setEmission(new Color(0, 0, 0)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(5).setKt(0).setKr(0.5)));


































/*
        scene2.lights.add(new DirectionalLight(new Color(240, 240, 240), new Vector(0, -1, 0)));
        scene2.lights.add(new PointLight(new Color(230, 230, 250), new Point3D(-160, -165, -100)));
        scene2.lights.add(new PointLight(new Color(230, 230, 250), new Point3D(160, -165, -100)));
        scene2.lights.add(new SpotLight(new Color(130,100,130), new Point3D(0, -30, 50), new Vector(0, 1, 0)) //
                .setKl(4E-5).setKq(2E-7));*/




        scene2.lights.add(new DirectionalLight(new Color(240, 240, 240), new Vector(0, -1, 0)));
        scene2.lights.add(new PointLight(new Color(170, 170, 170), new Point3D(-50, -50, -50)));
        scene2.lights.add(new PointLight(new Color(170, 170, 170), new Point3D(50, -50, -50)));
        scene2.lights.add(new SpotLight(new Color(255,255,255), new Point3D(0, -30, -100), new Vector(0, 1, 0)) //
                .setKl(4E-5).setKq(2E-7));



        ImageWriter imageWriter = new ImageWriter("ray tracing 4", 1000, 1000);

        Render render = new Render() //
                .setImageWriter(imageWriter) //
                .setCamera(camera2) //
                .setMultithreading(3)
                .setRayTracer(new BasicRayTracer(scene2).set_rayDistance(1).set_numOfRays(1)); //.set_numOfRays(5).setRadius(40).set_rayDistance(5));

        render.renderImage();
        render.writeToImage();

    }




















    @Test

    public void rayTracingTest5() {
/*
        Geometry triangle1 = new Triangle( //
                new Point3D(-150, -150, -150), new Point3D(150, -150, -150), new Point3D(75, 75, -150));
        Geometry triangle2 = new Triangle( //
                new Point3D(-150, -150, -150), new Point3D(-150, 75, -50), new Point3D(75, 75, -150));*/
        /*Geometry triangle3 = new Triangle( //
                new Point3D(-150, -150, -150), new Point3D(-107, 67, -37), new Point3D(75, 75, -150));
*/
        /*Geometry triangle3 = new Triangle( //
                new Point3D(-175, -150, 0), new Point3D(-150, -150, -150), new Point3D(-70, 70, -50));
*/
        // Geometry carre1 = new Polygon()
        _scene.geometries.add( //
                new Triangle(new Point3D(1500, -1500, -1500), new Point3D(-1500, 1500, -1500),
                        new Point3D(670, 670, 3000)) //
                        .setEmission(new Color(20, 20, 20)) //
                        .setMaterial(new Material().setKr(1)));
              /*  new Triangle(new Point3D(1500, -1500, -1500), new Point3D(-1500, 1500, -1500),
                        new Point3D(-1500, -1500, -2000)) //
                        .setEmission(new Color(20, 20, 20)) //
                        .setMaterial(new Material().setKr(0.5)));*/
       /* scene2.geometries.add(triangle1.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(300)),
                triangle2.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(300)));*/
                _scene.lights.add(new DirectionalLight(new Color(java.awt.Color.WHITE), new Vector(-2, -2, -1)));
        _scene.lights.add(new PointLight(new Color(500, 500, 250), new Point3D(20, -10, -130)) //
                .setKl(0.0001).setKq(0.00025));
        _scene.lights.add(new SpotLight(new Color(500, 250, 250), new Point3D(30, -10, -130), new Vector(-2, -2, -1)) //
                .setKl(0.0003).setKq(0.000007));

        _scene.geometries.add(new Sphere(25, new Point3D(0, -130, +60))//
                .setEmission(new Color(255, 164, 71))
                .setMaterial(new Material().setKd(0.25).setKs(0.3).setShininess(5).setKr(1)));

        _scene.geometries.add(new Sphere(50, new Point3D(-75, +50, -80))//
                .setEmission(new Color(0, 128, 85))
                .setMaterial(new Material().setKd(0.3).setKs(0.2).setShininess(5).setKr(1)));


        _scene.geometries.add(new Sphere(50, new Point3D(75, 50, -80))//
                .setEmission(new Color(0, 128, 128))
                .setMaterial(new Material().setKd(0.3).setKs(0.2).setShininess(5).setKr(1)));

        ImageWriter imageWriter = new ImageWriter("rayTracing5", 500, 500);
        Render render = new Render()//
                .setImageWriter(imageWriter) //
                .setCamera(camera2) //
                .setMultithreading(4)
                .setRayTracer(new BasicRayTracer(_scene));
        render.renderImage();
        render.writeToImage();


    }

    @Test
    public void FinalProject() {


        Scene scene3 = new Scene("Test scene")//
                .setAmbientLight(new AmbientLight(new Color(15, 5, 107), 0.15)) //
                .setBackground(new Color(15, 5, 107));

        scene3.geometries.add(
                new Polygon(new Point3D(-118,-650,425),new Point3D(118,-650,425),
                        new Point3D(400,-650,0),new Point3D(-400,-650,0))
                        .setEmission(new Color(255, 0, 0)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(5).setKt(0).setKr(0.5)));


        //pieds
        scene3.geometries.add(

                new Polygon(new Point3D(-170,0,400),new Point3D(-160,0,400),
                        new Point3D(-160,0,300),new Point3D(-170,0,300))
                        .setEmission(new Color(96,0,0)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(5).setKt(0).setKr(0)));

        scene3.geometries.add(

                new Polygon(new Point3D(-140,0,430),new Point3D(-130,0,430),
                        new Point3D(-130,0,300),new Point3D(-140,0,300))
                        .setEmission(new Color(96,0,0)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(5).setKt(0).setKr(0)));


        scene3.geometries.add(

                new Polygon(new Point3D(130,0,430),new Point3D(140,0,430),
                        new Point3D(140,0,300),new Point3D(130,0,300))
                        .setEmission(new Color(96,0,0)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(5).setKt(0).setKr(0)));

        scene3.geometries.add(

                new Polygon(new Point3D(160,0,400),new Point3D(170,0,400),
                        new Point3D(170,0,300),new Point3D(160,0,300))
                        .setEmission(new Color(96,0,0)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(5).setKt(0).setKr(0)));

        //banc
        scene3.geometries.add(

                new Polygon(new Point3D(-140,0,430),new Point3D(140,0,430),
                        new Point3D(170,0,400),new Point3D(-170,0,400))
                        .setEmission(new Color(96, 0, 0)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(5).setKt(0).setKr(0)));

        scene3.geometries.add(

                new Polygon(new Point3D(-140,0,500),new Point3D(140,0,500),
                        new Point3D(140,0,430),new Point3D(-140,0,430))
                        .setEmission(new Color(96, 32, 0)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(5).setKt(0).setKr(0)));

        //corps + tete

        scene3.geometries.add(new Sphere(70, new Point3D(0, -50, 460))// violet
                .setEmission(new Color(200, 200, 200))
                .setMaterial(new Material().setKd(0.3).setKs(0.2).setShininess(5).setKt(0.3).setKr(0)));

        scene3.geometries.add(new Sphere(85, new Point3D(0, -50, 600))// violet
                .setEmission(new Color(200, 200, 200))
                .setMaterial(new Material().setKd(0.3).setKs(0.2).setShininess(5).setKt(0.3).setKr(0)));

        //pieds

        scene3.geometries.add(new Sphere(20, new Point3D(-40, -25, 400))// violet
                .setEmission(new Color(200, 200, 200))
                .setMaterial(new Material().setKd(0.3).setKs(0.2).setShininess(5).setKt(0.3).setKr(0)));

        scene3.geometries.add(new Sphere(20, new Point3D(40, -25, 400))// violet
                .setEmission(new Color(200, 200, 200))
                .setMaterial(new Material().setKd(0.3).setKs(0.2).setShininess(5).setKt(0.3).setKr(0)));

        // doigts de pieds

        scene3.geometries.add(new Sphere(5, new Point3D(-50, -100, 418))// violet
                .setEmission(new Color(0, 0, 0))
                .setMaterial(new Material().setKd(0.3).setKs(0.2).setShininess(5).setKt(0.3).setKr(0)));

        scene3.geometries.add(new Sphere(5, new Point3D(-40, -100, 420))// violet
                .setEmission(new Color(0, 0, 0))
                .setMaterial(new Material().setKd(0.3).setKs(0.2).setShininess(5).setKt(0.3).setKr(0)));

        scene3.geometries.add(new Sphere(5, new Point3D(-30, -100, 418))// violet
                .setEmission(new Color(0, 0, 0))
                .setMaterial(new Material().setKd(0.3).setKs(0.2).setShininess(5).setKt(0.3).setKr(0)));

        scene3.geometries.add(new Sphere(5, new Point3D(-56, -100, 410))// violet
                .setEmission(new Color(0, 0, 0))
                .setMaterial(new Material().setKd(0.3).setKs(0.2).setShininess(5).setKt(0.3).setKr(0)));

        //droit

        scene3.geometries.add(new Sphere(5, new Point3D(50, -100, 418))// violet
                .setEmission(new Color(0, 0, 0))
                .setMaterial(new Material().setKd(0.3).setKs(0.2).setShininess(5).setKt(0.3).setKr(0)));

        scene3.geometries.add(new Sphere(5, new Point3D(40, -100, 420))// violet
                .setEmission(new Color(0, 0, 0))
                .setMaterial(new Material().setKd(0.3).setKs(0.2).setShininess(5).setKt(0.3).setKr(0)));

        scene3.geometries.add(new Sphere(5, new Point3D(30, -100, 418))// violet
                .setEmission(new Color(0, 0, 0))
                .setMaterial(new Material().setKd(0.3).setKs(0.2).setShininess(5).setKt(0.3).setKr(0)));

        scene3.geometries.add(new Sphere(5, new Point3D(56, -100, 410))// violet
                .setEmission(new Color(0, 0, 0))
                .setMaterial(new Material().setKd(0.3).setKs(0.2).setShininess(5).setKt(0.3).setKr(0)));

//ventre

        scene3.geometries.add(new Sphere(45, new Point3D(0, -100, 468))// violet
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.3).setKs(0.2).setShininess(5).setKt(0.3).setKr(0)));

        //queue

        scene3.geometries.add(new Polygon(new Point3D(60,-100,440),new Point3D(150,-100,490),
                new Point3D(150,-100,475),new Point3D(56,-100,430))
                .setEmission(new Color(200, 200, 200)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(5).setKt(0.3).setKr(0)));

        scene3.geometries.add(new Sphere(7, new Point3D(150, -100, 482.5))// violet
                .setEmission(new Color(200, 200, 200))
                .setMaterial(new Material().setKd(0.3).setKs(0.2).setShininess(5).setKt(0.3).setKr(0)));
        //oreilles

        scene3.geometries.add(new Polygon(new Point3D(-70,-100,644),new Point3D(-75,-100,680),
                new Point3D(-45,-100,668))
                .setEmission(new Color(200, 200, 200)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(5).setKt(0.3).setKr(0)));



        scene3.geometries.add(new Polygon(new Point3D(70,-100,644),new Point3D(75,-100,680),
                new Point3D(45,-100,668))
                .setEmission(new Color(200, 200, 200)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(5).setKt(0.3).setKr(0)));

        scene3.geometries.add(new Polygon(new Point3D(-65,-100,650),new Point3D(-69,-110,670),
                new Point3D(-50,-100,664))
                .setEmission(new Color(200, 150, 150)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(5).setKt(0.3).setKr(0)));

        scene3.geometries.add(new Polygon(new Point3D(65,-100,650),new Point3D(69,-110,670),
                new Point3D(50,-100,664))
                .setEmission(new Color(200, 150, 150)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(5).setKt(0.3).setKr(0)));

        //nez
        scene3.geometries.add(new Polygon(new Point3D(10,-200,600),new Point3D(0,-200,590),
                new Point3D(-10,-200,600))
                .setEmission(new Color(00, 00, 0)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(5).setKt(0.3).setKr(0)));

        scene3.geometries.add(new Polygon(new Point3D(-0.5,-200,590),new Point3D(0.5,-200,590),
                new Point3D(0.5,-200,570),new Point3D(-0.5,-200,570))
                .setEmission(new Color(00, 0, 0)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(5).setKt(0.3).setKr(0)));
        //bouche
        scene3.geometries.add(new Polygon(new Point3D(10,-200,560),new Point3D(0,-200,575),
                new Point3D(-10,-200,560))
                .setEmission(new Color(0, 00, 0)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(5).setKt(0.3).setKr(0)));


        //yeux

        scene3.geometries.add(new Sphere(10, new Point3D(-30, -200, 620))// violet
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.3).setKs(0.2).setShininess(5).setKt(0.3).setKr(0)));

        scene3.geometries.add(new Sphere(10, new Point3D(30, -200, 620))// violet
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.3).setKs(0.2).setShininess(5).setKt(0.3).setKr(0)));

        //bleu
        scene3.geometries.add(new Sphere(5, new Point3D(-27, -250, 615))// violet
                .setEmission(new Color(00, 00, 255))
                .setMaterial(new Material().setKd(0.3).setKs(0.2).setShininess(5).setKt(0.3).setKr(0)));

        scene3.geometries.add(new Sphere(5, new Point3D(27, -250, 615))// violet
                .setEmission(new Color(0, 00, 255))
                .setMaterial(new Material().setKd(0.3).setKs(0.2).setShininess(5).setKt(0.3).setKr(0)));

        //noeud

        scene3.geometries.add(new Sphere(15, new Point3D(0, -200, 680))// violet
                .setEmission(new Color(200, 10, 150))
                .setMaterial(new Material().setKd(0.3).setKs(0.2).setShininess(5).setKt(0.3).setKr(0)));

        scene3.geometries.add(new Polygon(new Point3D(-5,-200,680),new Point3D(-40,-200,700),
                new Point3D(-40,-200,660))
                .setEmission(new Color(200, 10, 150)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(5).setKt(0.3).setKr(0)));

        scene3.geometries.add(new Polygon(new Point3D(5,-200,680),new Point3D(40,-200,700),
                new Point3D(40,-200,660))
                .setEmission(new Color(200, 10, 150)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(5).setKt(0.3).setKr(0)));

        // moustaches gauche
        scene3.geometries.add(new Polygon(new Point3D(-10,-250,582),new Point3D(-100,-250,585),
                new Point3D(-100,-250,580))
                .setEmission(new Color(00, 0, 0)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(5).setKt(0.3).setKr(0)));

        scene3.geometries.add(new Polygon(new Point3D(-10,-250,582),new Point3D(-100,-250,565),
                new Point3D(-100,-250,560))
                .setEmission(new Color(00, 0, 0)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(5).setKt(0.3).setKr(0)));

        scene3.geometries.add(new Polygon(new Point3D(-10,-250,582),new Point3D(-100,-250,545),
                new Point3D(-100,-250,540))
                .setEmission(new Color(00, 0, 0)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(5).setKt(0.3).setKr(0)));

        //moustache droite
        scene3.geometries.add(new Polygon(new Point3D(10,-250,582),new Point3D(100,-250,585),
                new Point3D(100,-250,580))
                .setEmission(new Color(00, 0, 0)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(5).setKt(0.3).setKr(0)));

        scene3.geometries.add(new Polygon(new Point3D(10,-250,582),new Point3D(100,-250,565),
                new Point3D(100,-250,560))
                .setEmission(new Color(00, 0, 0)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(5).setKt(0.3).setKr(0)));

        scene3.geometries.add(new Polygon(new Point3D(10,-250,582),new Point3D(100,-250,545),
                new Point3D(100,-250,540))
                .setEmission(new Color(00, 0, 0)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(5).setKt(0.3).setKr(0)));


        //boule cristale

        /*scene3.geometries.add(new Sphere(400, new Point3D(0, 400, 600))// violet
                .setEmission(new Color(00255, 00255, 00255))
                .setMaterial(new Material().setKd(0.1).setKs(0.1).setShininess(5).setKt(0).setKr(0.2)));*/

        scene3.geometries.add(new Sphere(300, new Point3D(0, -200, 590))
                .setEmission(new Color(40, 40, 40))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(5).setKt(0.6).setKr(0)));


        //---------------------------------ETOILES KEREN--------------------------------------------------------------------


        scene3.geometries.add(new Sphere(1, new Point3D(450, 20, 525))
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0.1)));


        scene3.geometries.add(new Sphere(1, new Point3D(196, 20, 610))
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0.1)));

        scene3.geometries.add(new Sphere(1, new Point3D(20, 20, 625))
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0.1)));



        scene3.geometries.add(new Sphere(2, new Point3D(484, 20, 746))
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0.1)));
        scene3.geometries.add(new Sphere(2, new Point3D(394, 20, 773))
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0.1)));




        scene3.geometries.add(new Sphere(1, new Point3D(274, 20, 846))
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0.1)));
        scene3.geometries.add(new Sphere(2, new Point3D(474, 20, 846))
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0.1)));

        scene3.geometries.add(new Sphere(1, new Point3D(159, -15, 895))
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0.1)));





        scene3.geometries.add(new Sphere(1, new Point3D(345, 20, 900))
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0.1)));

        scene3.geometries.add(new Sphere(2, new Point3D(450, 20, 925))
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0.1)));

        scene3.geometries.add(new Sphere(1, new Point3D(400, 10, 950))
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0.1)));


        scene3.geometries.add(new Sphere(1, new Point3D(360, 20, 1057))
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0.1)));
        scene3.geometries.add(new Sphere(2, new Point3D(460, 20, 1057))
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0.1)));
//------------------------------------------------

        scene3.geometries.add(new Sphere(2, new Point3D(-300, 20, 505))
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0.1)));

        scene3.geometries.add(new Sphere(5, new Point3D(-104, 20, 510))
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0.1)));

        scene3.geometries.add(new Sphere(2, new Point3D(-350, 20, 510))
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0.1)));

        scene3.geometries.add(new Sphere(5, new Point3D(-450, 20, 525))
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0.1)));

        scene3.geometries.add(new Sphere(1, new Point3D(-314, 20, 525))
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0.1)));

        scene3.geometries.add(new Sphere(3, new Point3D(-80, 20, 527))
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0.1)));

        scene3.geometries.add(new Sphere(3, new Point3D(-380, 20, 527))
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0.1)));

        scene3.geometries.add(new Sphere(3, new Point3D(-385, 20, 595))
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0.1)));




        scene3.geometries.add(new Sphere(1, new Point3D(-500, 20, 600))
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0.1)));

        scene3.geometries.add(new Sphere(1, new Point3D(-196, 20, 610))
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0.1)));
        scene3.geometries.add(new Sphere(5, new Point3D(-316, 20, 610))
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0.1)));
        scene3.geometries.add(new Sphere(1, new Point3D(-20, 20, 625))
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0.1)));
        scene3.geometries.add(new Sphere(2, new Point3D(-320, 20, 625))
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0.1)));
        scene3.geometries.add(new Sphere(3, new Point3D(-414, 20, 685))
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0.1)));



        scene3.geometries.add(new Sphere(2, new Point3D(-500, 20, 700))
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0.1)));

        scene3.geometries.add(new Sphere(2, new Point3D(-457, 20, 703))
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0.1)));

        scene3.geometries.add(new Sphere(2, new Point3D(-300, 20, 710))
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0.1)));

        scene3.geometries.add(new Sphere(2, new Point3D(-350, 20, 710))
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0.1)));

        scene3.geometries.add(new Sphere(2, new Point3D(19, 20, 710))
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0.1)));

        scene3.geometries.add(new Sphere(2, new Point3D(-329, 20, 710))
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0.1)));

        scene3.geometries.add(new Sphere(1, new Point3D(-190, 20, 725))
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0.1)));
        scene3.geometries.add(new Sphere(2, new Point3D(-150, 20, 725))
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0.1)));
        scene3.geometries.add(new Sphere(2, new Point3D(-177, 20, 725))
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0.1)));

        scene3.geometries.add(new Sphere(5, new Point3D(-330, 20, 725))
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0.1)));

        scene3.geometries.add(new Sphere(2, new Point3D(-30, 20, 725))
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0.1)));
        scene3.geometries.add(new Sphere(2, new Point3D(-484, 20, 746))
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0.1)));
        scene3.geometries.add(new Sphere(3, new Point3D(-394, 20, 773))
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0.1)));






        scene3.geometries.add(new Sphere(2, new Point3D(68, 20, 800))
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0.1)));
        scene3.geometries.add(new Sphere(2, new Point3D(-500, 20, 800))
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0.1)));

        scene3.geometries.add(new Sphere(2, new Point3D(-383, 20, 810))
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0.1)));

        scene3.geometries.add(new Sphere(1, new Point3D(-143, 20, 810))
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0.1)));
        scene3.geometries.add(new Sphere(3, new Point3D(-300, 20, 810))
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0.1)));

        scene3.geometries.add(new Sphere(6, new Point3D(-183, 20, 825))
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0.1)));
        scene3.geometries.add(new Sphere(2, new Point3D(-100, 20, 825))
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0.1)));

        scene3.geometries.add(new Sphere(2, new Point3D(-340, 20, 825))
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0.1)));
        scene3.geometries.add(new Sphere(5, new Point3D(-450, 20, 825))
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0.1)));

        scene3.geometries.add(new Sphere(2, new Point3D(-22, 20, 826))
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0.1)));
        scene3.geometries.add(new Sphere(1, new Point3D(-274, 20, 846))
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0.1)));
        scene3.geometries.add(new Sphere(3, new Point3D(-474, 20, 846))
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0.1)));

        scene3.geometries.add(new Sphere(1, new Point3D(-159, -15, 895))
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0.1)));
        scene3.geometries.add(new Sphere(2, new Point3D(-032, 20, 887))
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0.1)));
        scene3.geometries.add(new Sphere(2, new Point3D(-157, 20, 899))
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0.1)));

        scene3.geometries.add(new Sphere(2, new Point3D(-57, 20, 899))
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0.1)));





        scene3.geometries.add(new Sphere(1, new Point3D(-345, 20, 900))
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0.1)));

        scene3.geometries.add(new Sphere(3, new Point3D(-258, 20, 910))
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0.1)));

        scene3.geometries.add(new Sphere(2, new Point3D(-9, 20, 910))
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0.1)));
        scene3.geometries.add(new Sphere(1, new Point3D(-139, 20, 910))
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0.1)));

        scene3.geometries.add(new Sphere(4, new Point3D(-199, 20, 910))
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0.1)));

        scene3.geometries.add(new Sphere(1, new Point3D(-319, 20, 910))
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0.1)));
        scene3.geometries.add(new Sphere(3, new Point3D(-455, 15, 915))
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0.1)));
        scene3.geometries.add(new Sphere(2, new Point3D(-450, 20, 925))
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0.1)));

        scene3.geometries.add(new Sphere(2, new Point3D(-357, 20, 925))
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0.1)));
        scene3.geometries.add(new Sphere(1, new Point3D(-300, 0, 930))
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0.1)));

        scene3.geometries.add(new Sphere(5, new Point3D(-400, 10, 950))
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0.1)));
        scene3.geometries.add(new Sphere(2, new Point3D(-204, 20, 954))
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0.1)));






        scene3.geometries.add(new Sphere(2, new Point3D(-500, 20, 1000))
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0.1)));
        scene3.geometries.add(new Sphere(2, new Point3D(-572, 20, 1006))
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0.1)));
        scene3.geometries.add(new Sphere(2, new Point3D(-585, 20, 1000))
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0.1)));
        scene3.geometries.add(new Sphere(2, new Point3D(-372, 20, 1006))
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0.1)));

        scene3.geometries.add(new Sphere(1, new Point3D(-275, 20, 1010))
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0.1)));

        scene3.geometries.add(new Sphere(3, new Point3D(-389, 20, 1010))
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0.1)));
        scene3.geometries.add(new Sphere(1, new Point3D(-191, 20, 1017))
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0.1)));
        scene3.geometries.add(new Sphere(2, new Point3D(-491, 20, 1017))
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0.1)));

        scene3.geometries.add(new Sphere(3, new Point3D(-165, 20, 1025))
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0.1)));
        scene3.geometries.add(new Sphere(1, new Point3D(-159, 20, 1025))
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0.1)));

        scene3.geometries.add(new Sphere(1, new Point3D(-379, 20, 1025))
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0.1)));

        scene3.geometries.add(new Sphere(5, new Point3D(-440, 20, 1100))
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0.1)));
        scene3.geometries.add(new Sphere(2, new Point3D(-297, 20, 1057))
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0.1)));
        scene3.geometries.add(new Sphere(2, new Point3D(-129, 20, 1060))
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0.1)));
        scene3.geometries.add(new Sphere(2, new Point3D(-135, 20, 1110))
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0.1)));
        scene3.geometries.add(new Sphere(5, new Point3D(-40, 20, 1000))
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0.1)));

        scene3.geometries.add(new Sphere(3, new Point3D(-40, 20, 1095))
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0.1)));
        scene3.geometries.add(new Sphere(2, new Point3D(-169, 20, 1135))
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0.1)));

        scene3.geometries.add(new Sphere(5, new Point3D(-165, 20, 1145))
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0.1)));

        scene3.geometries.add(new Sphere(4, new Point3D(-500, 20, 1140))
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0.1)));

        scene3.geometries.add(new Sphere(3, new Point3D(-25, 20, 1140))
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0.1)));

        // indication : au dessus de 1150 pas besoin






//----------------------------------------------------------------------ETOILES ANNA----------------------------------------------------------------------------------------------------------------------------



        scene3.geometries.add(new Sphere(5, new Point3D(350, 0, 450))
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0.1)));

        scene3.geometries.add(new Sphere(3, new Point3D(330, 0, 470))
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0.1)));




        scene3.geometries.add(new Sphere(3, new Point3D(330, 0, 500))
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0.4)));
        scene3.geometries.add(new Sphere(3, new Point3D(200, 0, 500))
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0.4)));

        scene3.geometries.add(new Sphere(3, new Point3D(500, 0, 500))
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0.4)));

        scene3.geometries.add(new Sphere(3, new Point3D(460, 0, 583))
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0.4)));
        scene3.geometries.add(new Sphere(3, new Point3D(220, 0, 590))
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0.1)));





        scene3.geometries.add(new Sphere(3, new Point3D(520, 0, 600))
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0.4)));

        scene3.geometries.add(new Sphere(5, new Point3D(300, 0, 600))
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0.4)));
        scene3.geometries.add(new Sphere(3, new Point3D(300, 0, 600))
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0.1)));
        scene3.geometries.add(new Sphere(3, new Point3D(400, 0, 630))
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0.4)));
        scene3.geometries.add(new Sphere(1, new Point3D(500, 0, 650))
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0.4)));

        scene3.geometries.add(new Sphere(3, new Point3D(350, 0, 650))
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0.4)));
        scene3.geometries.add(new Sphere(3, new Point3D(180, 0, 650))
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0.1)));
        scene3.geometries.add(new Sphere(5, new Point3D(150, 0, 670))
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0.1)));









        scene3.geometries.add(new Sphere(3, new Point3D(220, 0, 700))
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0.1)));
        scene3.geometries.add(new Sphere(3, new Point3D(260, 0, 730))
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0.1)));
        scene3.geometries.add(new Sphere(3, new Point3D(90, 0, 750))
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0.4)));

        scene3.geometries.add(new Sphere(5, new Point3D(400, 0, 750))
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0.4)));
        scene3.geometries.add(new Sphere(5, new Point3D(220, 0, 760))
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0.1)));

        scene3.geometries.add(new Sphere(5, new Point3D(520, 0, 770))
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0.4)));





        scene3.geometries.add(new Sphere(3, new Point3D(420, 0, 800))
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0.1)));
        scene3.geometries.add(new Sphere(3, new Point3D(100, 0, 800))
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0.1)));

        scene3.geometries.add(new Sphere(5, new Point3D(40, 0, 800))
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0.1)));
        scene3.geometries.add(new Sphere(5, new Point3D(400, 0, 850))
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0.1)));
        scene3.geometries.add(new Sphere(3, new Point3D(300, 0, 860))
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0.1)));




        scene3.geometries.add(new Sphere(5, new Point3D(250, 0, 900))
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0.4)));
        scene3.geometries.add(new Sphere(3, new Point3D(200, 0, 900))
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0.4)));
        scene3.geometries.add(new Sphere(3, new Point3D(55, 0, 900))
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0.1)));

        scene3.geometries.add(new Sphere(3, new Point3D(520, 0, 900))
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0.4)));
        scene3.geometries.add(new Sphere(3, new Point3D(460, 0, 920))
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0.4)));

        scene3.geometries.add(new Sphere(3, new Point3D(370, 0, 930))
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0.4)));

        scene3.geometries.add(new Sphere(3, new Point3D(35, 0, 930))
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0.1)));

        scene3.geometries.add(new Sphere(3, new Point3D(100, 0, 940))
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0.1)));

        scene3.geometries.add(new Sphere(3, new Point3D(180, 0, 980))
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0.1)));
        scene3.geometries.add(new Sphere(3, new Point3D(260, 0, 980))
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0.1)));
        scene3.geometries.add(new Sphere(1, new Point3D(100, 0, 990))
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0.1)));







        scene3.geometries.add(new Sphere(3, new Point3D(400, 0, 1000))
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0.1)));

        scene3.geometries.add(new Sphere(3, new Point3D(250, 0, 1000))
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0.1)));

        scene3.geometries.add(new Sphere(1, new Point3D(75, 0, 1000))
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0.4)));
        scene3.geometries.add(new Sphere(3, new Point3D(200, 0, 1000))
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0.1)));

        scene3.geometries.add(new Sphere(3, new Point3D(190, 0, 1015))
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0.1)));
        scene3.geometries.add(new Sphere(3, new Point3D(30, 0, 1025))
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0.1)));

        scene3.geometries.add(new Sphere(1, new Point3D(520, 0, 1025))
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0.4)));
        scene3.geometries.add(new Sphere(1, new Point3D(-520, 0, 1050))
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0.4)));

        scene3.geometries.add(new Sphere(3, new Point3D(-520, 0, 1050))
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0.4)));


        scene3.geometries.add(new Sphere(3, new Point3D(-300, 0, 1050))
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0.4)));
        scene3.geometries.add(new Sphere(1, new Point3D(520, 0, 1050))
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0.4)));

        scene3.geometries.add(new Sphere(3, new Point3D(520, 0, 1050))
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0.4)));

        scene3.geometries.add(new Sphere(3, new Point3D(300, 0, 1050))
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0.4)));


        scene3.geometries.add(new Sphere(5, new Point3D(85, 0, 1050))
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0.4)));
        scene3.geometries.add(new Sphere(3, new Point3D(200, 0, 1060))
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0.1)));
        scene3.geometries.add(new Sphere(1, new Point3D(300, 0, 1070))
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0.1)));
        scene3.geometries.add(new Sphere(1, new Point3D(20, 0, 1075))
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0.1)));

        scene3.geometries.add(new Sphere(3, new Point3D(430, 0, 1100))
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0.1)));
        scene3.geometries.add(new Sphere(3, new Point3D(75, 0, 1100))
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0.4)));

        scene3.geometries.add(new Sphere(3, new Point3D(120, 0, 1100))
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0.4)));
        scene3.geometries.add(new Sphere(5, new Point3D(300, 0, 1130))
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0.4)));

        scene3.geometries.add(new Sphere(5, new Point3D(-300, 0, 1130))
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0.4)));

        scene3.geometries.add(new Sphere(3, new Point3D(250, 0, 1150))
                .setEmission(new Color(255, 255, 255))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0.4)));















/*        scene3.geometries.add(new Sphere(20, new Point3D(-40, -25, 400))// violet
                .setEmission(new Color(200, 200, 200))
                .setMaterial(new Material().setKd(0.3).setKs(0.2).setShininess(5).setKt(0.3).setKr(0)));*/



     scene3.lights.add(new SpotLight(new Color(0,0,0), new Point3D(0, -1200, 1150), new Vector(0, 1, -1))); //
//                .setKl(0.0001).setKq(0.000005));

        scene3.lights.add(new PointLight(new Color(100, 139, 154), new Point3D(1000,100, 500)));
        scene3.lights.add(new PointLight(new Color(255,255,255), new Point3D(-1000, 0, 500)));





      /*  scene3.geometries.add(new Polygon(new Point3D(70,-100,644),new Point3D(75,-100,680),
                new Point3D(45,-100,668))
                .setEmission(new Color(200, 200, 200)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(5).setKt(0.3).setKr(0)));
*/
       // scene3.lights.add(new DirectionalLight(new Color(100, 100, 100), new Vector(0, 0, -1)));
        //scene3.lights.add(new PointLight(new Color(170, 170, 170), new Point3D(-50,     150, 100)));
        // scene3.lights.add(new PointLight(new Color(170, 170, 170), new Point3D(50, 150, 100)));
//        scene3.lights.add(new SpotLight(new Color(255,255,255), new Point3D(0,  150, 100), new Vector(0, 1, 0))
//         .setKl(4E-5).setKq(2E-7));

        ImageWriter imageWriter = new ImageWriter("finalProject", 1500, 1500);

        Render render = new Render() //
                .setImageWriter(imageWriter)
                //
                .setCamera(camera3) //
                .setMultithreading(4)
                .setRayTracer(new BasicRayTracer(scene3)); //.set_numOfRays(1).set_rayDistance(1));

        render.renderImage();
        render.writeToImage();

    }
}

