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
//AJOUT ESSAIE
   private Scene scene2 = new Scene("Test scene") //
            .setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));
    private Camera camera1 = new Camera(new Point3D(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
            .setViewPlaneSize(150, 150) //
            .setDistance(1000);
    private Camera camera2 = new Camera(new Point3D(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
            .setViewPlaneSize(1000, 1000) //
            .setDistance(500);

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
//CHANGER AUSSI ICI
  /*  private Camera camera2 = new Camera((new Point3D(0, 0, +1000)), new Vector(0, 0, -1), new Vector(0, +1, 0))
            .setDistance(500) //
            .setViewPlaneSize(200, 200)
            ;*/

    Geometry sphere = new Sphere(50, new Point3D(0, 0, -50)) //
            .setEmission(new Color(java.awt.Color.BLUE)) //
            .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100));

    @Test

    public void rayTracingTest2() {

        Scene scene2 = new Scene("Test scene")//
                .setAmbientLight(new AmbientLight(new Color(255, 191, 191), 0.5)) //
                .setBackground(new Color(100,10,1));


        // (double _kD, double _kS, int _nShininess, double _kT, double _kR)

    /*    Geometry sphere = new Sphere(50, new Point3D(0, 0, -50)) //
                .setEmission(new Color(java.awt.Color.BLUE)) //
                .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100));*/

        scene2.geometries.add(new Sphere(20, new Point3D(75, -120, +75))//
                .setEmission(new Color(212, 175, 55))
                .setMaterial(new Material().setKd(0.3).setKs(0.4).setShininess(5).setKt(0).setKr(0.5)));

        scene2.geometries.add(new Sphere(15, new Point3D(160, -165, -100))//
                .setEmission(new Color(53, 187, 202))
                .setMaterial(new Material().setKd(0.25).setKs(0.3).setShininess(5)));

        scene2.geometries.add(new Sphere(25, new Point3D(0, -130, -90))//
                .setEmission(new Color(255, 99, 71))
                .setMaterial(new Material().setKd(0.25).setKs(0.3).setShininess(5).setKr(1)));


        scene2.geometries.add(new Sphere(25, new Point3D(0, -130, +60))//
                .setEmission(new Color(255, 164, 71))
                .setMaterial(new Material().setKd(0.25).setKs(0.3).setShininess(5).setKr(1)));

        scene2.geometries.add(new Sphere(50,new Point3D(-75, +50, -80))//
                .setEmission(new Color(0, 128, 85))
                .setMaterial(new Material().setKd(0.3).setKs(0.2).setShininess(5).setKr(1)));


        scene2.geometries.add(new Sphere(50, new Point3D(75, 50, -80))//
                .setEmission(new Color(0, 128, 128))
                .setMaterial(new Material().setKd(0.3).setKs(0.2).setShininess(5).setKr(1)));



        scene2.lights.add(new DirectionalLight(new Color(240, 240, 240), new Vector(0, -1, 0)));
       // scene2.lights.add(new PointLight(new Color(230, 230, 250), new Point3D(-160, -165, -100)));
        scene2.lights.add(new PointLight(new Color(230, 230, 250), new Point3D(160, -165, -100)));

        scene2.geometries.add(sphere);//
       // scene2.lights.add(new DirectionalLight(new Color(java.awt.Color.cyan), new Vector(3, 5, -7)));
       // scene2.lights.add(new PointLight(new Color(java.awt.Color.cyan), new Point3D(-15, -20, 80))
              //  .setKl(0.001).setKq(0.0001));
        scene2.lights.add(new SpotLight(new Color(java.awt.Color.cyan), new Point3D(-30, -50, 70), new Vector(3, 5, -7)) //
                .setKl(0.001).setKq(0.001));


//                new SpotLight(new Color(130, 100, 130),new Point3D(0, 30, -50),
//                        new Vector(0,-1,0),1, 4E-5, 2E-7)

        ImageWriter imageWriter = new ImageWriter("ray tracing 2", 1000, 1000);
        Render render = new Render() //
                .setImageWriter(imageWriter) //
                .setCamera(camera2) //
                .setRayTracer(new BasicRayTracer(scene2).set_numOfRays(5).setRadius(40).set_rayDistance(5));

 /*         public void trianglesLightsTest() {



        ImageWriter imageWriter = new ImageWriter("trianglesLightsTest", 500, 500);
        Render render = new Render()//
                .setImageWriter(imageWriter) //
                .setCamera(camera2) //
                .setRayTracer(new BasicRayTracer(scene2));
        render.renderImage();
        render.writeToImage();

    }*/



    }

    @Test

    public void rayTracingTest3() {
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
        scene2.geometries.add( //
                new Triangle(new Point3D(1500, -1500, -1500), new Point3D(-1500, 1500, -1500),
                        new Point3D(670, 670, 3000)) //
                        .setEmission(new Color(20, 20, 20)) //
                        .setMaterial(new Material().setKr(1)),
              /*  new Triangle(new Point3D(1500, -1500, -1500), new Point3D(-1500, 1500, -1500),
                        new Point3D(-1500, -1500, -2000)) //
                        .setEmission(new Color(20, 20, 20)) //
                        .setMaterial(new Material().setKr(0.5)));*/
       /* scene2.geometries.add(triangle1.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(300)),
                triangle2.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(300)));*/

        scene2.lights.add(new DirectionalLight(new Color(java.awt.Color.WHITE), new Vector(-2, -2, -1)));
        scene2.lights.add(new PointLight(new Color(500, 500, 250), new Point3D(20, -10, -130)) //
                .setKl(0.0001).setKq(0.00025));
        scene2.lights.add(new SpotLight(new Color(500, 250, 250), new Point3D(30, -10, -130), new Vector(-2, -2, -1)) //
                .setKl(0.0003).setKq(0.000007));

        scene2.geometries.add(new Sphere(25, new Point3D(0, -130, +60))//
                .setEmission(new Color(255, 164, 71))
                .setMaterial(new Material().setKd(0.25).setKs(0.3).setShininess(5).setKr(1)));

        scene2.geometries.add(new Sphere(50,new Point3D(-75, +50, -80))//
                .setEmission(new Color(0, 128, 85))
                .setMaterial(new Material().setKd(0.3).setKs(0.2).setShininess(5).setKr(1)));


        scene2.geometries.add(new Sphere(50, new Point3D(75, 50, -80))//
                .setEmission(new Color(0, 128, 128))
                .setMaterial(new Material().setKd(0.3).setKs(0.2).setShininess(5).setKr(1)));

        ImageWriter imageWriter = new ImageWriter("rayTracing3", 500, 500);
        Render render = new Render()//
                .setImageWriter(imageWriter) //
                .setCamera(camera2) //
                .setRayTracer(new BasicRayTracer(scene2));
        render.renderImage();
        render.writeToImage();


    }

}
