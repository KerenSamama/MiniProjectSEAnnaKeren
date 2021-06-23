package renderer;


import elements.*;
import geometries.*;
import org.junit.jupiter.api.Test;
import primitives.*;
import scene.Scene;

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
                .setAmbientLight(new AmbientLight(new Color(255, 255, 255), 0.1)) //
                .setBackground(new Color(0, 0, 0));


        // (double _kD, double _kS, int _nShininess, double _kT, double _kR)

        scene2.geometries.add(new Sphere(20, new Point3D(125, -36, 50))// violet
                .setEmission(new Color(108, 2, 119))
                .setMaterial(new Material().setKd(0.3).setKs(0.2).setShininess(5).setKt(0).setKr(0.5)));

        scene2.geometries.add(new Sphere(20, new Point3D(125, 100, 50))// BLUE
                .setEmission(new Color(15, 5, 107))
                .setMaterial(new Material().setKd(0.25).setKs(0.3).setShininess(5).setKt(0.22).setKr(0)));

        scene2.geometries.add(new Sphere(25, new Point3D(0, -130, -90))// milieu en bas
                .setEmission(new Color(0, 128, 128))
                .setMaterial(new Material().setKd(0.25).setKs(0.3).setShininess(5).setKt(0).setKr(0.4)));


        scene2.geometries.add(new Sphere(25, new Point3D(0, -130, +90))//vert dessus
                .setEmission(new Color(0, 128, 85)) //0 128 85
                .setMaterial(new Material().setKd(0.25).setKs(0.3).setShininess(5).setKt(0).setKr(0.4)));

        scene2.geometries.add(new Sphere(50, new Point3D(-63, +30, -200))//BIG BLUE
                .setEmission(new Color(231, 62, 1))
                .setMaterial(new Material().setKd(0.3).setKs(0.2).setShininess(5).setKt(0).setKr(0.1)));


        scene2.geometries.add(new Sphere(50, new Point3D(48, 30, -200))//
                .setEmission(new Color(212, 175, 55))
                .setMaterial(new Material().setKd(0.3).setKs(0.2).setShininess(5).setKt(0).setKr(0.1)));

        scene2.geometries.add(new Sphere(20, new Point3D(-180, 90, -70))// red
                .setEmission(new Color(255, 0, 0))
                .setMaterial(new Material().setKd(0.3).setKs(0.2).setShininess(5).setKr(0.4)));

        scene2.geometries.add(new Sphere(20, new Point3D(-180, -36, -70))// GREEN
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



        ImageWriter imageWriter = new ImageWriter("ray tracing 4", 600, 600);

        Render render = new Render() //
                .setImageWriter(imageWriter) //
                .setCamera(camera2) //
                .setRayTracer(new BasicRayTracer(scene2).set_rayDistance(1).set_numOfRays(10)); //.set_numOfRays(5).setRadius(40).set_rayDistance(5));

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
                .setRayTracer(new BasicRayTracer(_scene));
        render.renderImage();
        render.writeToImage();


    }

}
