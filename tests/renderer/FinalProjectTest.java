package renderer;

import elements.*;
import geometries.*;
import geometries.Polygon;
import org.junit.jupiter.api.Test;
import primitives.*;
import primitives.Color;
import scene.Scene;


public class FinalProjectTest {


    private Camera camera2 = new Camera(new Point3D(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
            .setViewPlaneSize(500, 500) //
            .setDistance(1000); // 200,200 pour 1000,1000  or Nx Ny 500,500 pour 1000,1000


    @Test
    public void finalProject() {

        Scene scene2 = new Scene("Test scene")//
                .setAmbientLight(new AmbientLight(new Color(255, 255, 255), 0.15)) //
                .setBackground(new Color(0, 0, 0));

        Geometries geos1 = new Geometries(new Sphere(20, new Point3D(125, -36, 50))// violet
                .setEmission(new Color(108, 2, 119))
                .setMaterial(new Material().setKd(0.3).setKs(0.2).setShininess(5).setKt(0).setKr(0)),

                new Sphere(20, new Point3D(125, 100, 50))// orange
                        .setEmission(new Color(231, 62, 1))
                        .setMaterial(new Material().setKd(0.25).setKs(0.3).setShininess(5).setKt(0.22).setKr(0)));

        Geometries geos2 = new Geometries(new Sphere(25, new Point3D(0, -130, -90))// bleu milieu en bas
                .setEmission(new Color(0, 128, 128))
                .setMaterial(new Material().setKd(0.25).setKs(0.3).setShininess(5).setKt(0).setKr(0.3)),


                new Sphere(25, new Point3D(0, -130, +90))//vert milieu dessus
                        .setEmission(new Color(0, 128, 85)) //0 128 85
                        .setMaterial(new Material().setKd(0.25).setKs(0.3).setShininess(5).setKt(0).setKr(0.3)));

        Geometries geos3 = new Geometries(new Sphere(50, new Point3D(-63, +30, -200))//BIG BLUE
                .setEmission(new Color(15, 5, 107))
                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(5).setKt(0).setKr(0)),


                new Sphere(50, new Point3D(48, 30, -200))// BIG JAUNE
                        .setEmission(new Color(212, 175, 55))
                        .setMaterial(new Material().setKd(0.3).setKs(0.2).setShininess(5).setKt(0).setKr(0.1)));

        Geometries geos4 = new Geometries(new Sphere(20, new Point3D(-180, 90, -70))// red
                .setEmission(new Color(255, 0, 0))
                .setMaterial(new Material().setKd(0.3).setKs(0.2).setShininess(5).setKr(0)),

                new Sphere(20, new Point3D(-180, -36, -70))// GREEN a gauche
                        .setEmission(new Color(34, 80, 30))
                        .setMaterial(new Material().setKd(0.3).setKs(0.4).setShininess(5).setKt(0.22).setKr(0)));

        //------------------first line from the side right----------------------------------------------

        Geometries geos5 = new Geometries(
                new Polygon(new Point3D(250, 250, -250), new Point3D(187.5, 250, -250),
                        new Point3D(187.5, 187.5, -250), new Point3D(250, 187.5, -250))
                        .setEmission(new Color(0, 0, 0)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(5).setKt(0).setKr(0.5)),

                new Polygon(new Point3D(187.5, 250, -250), new Point3D(125, 250, -250),
                        new Point3D(125, 187.5, -250), new Point3D(187.5, 187.5, -250))
                        .setEmission(new Color(96, 96, 96)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(5).setKt(0).setKr(0.5)),

                new Polygon(new Point3D(125, 250, -250), new Point3D(62.5, 250, -250),
                        new Point3D(62.5, 187.5, -250), new Point3D(125, 187.5, -250))
                        .setEmission(new Color(0, 0, 0)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(5).setKt(0).setKr(0.5)),

                new Polygon(new Point3D(62.5, 250, -250), new Point3D(0, 250, -250),
                        new Point3D(0, 187.5, -250), new Point3D(62.5, 187.5, -250))
                        .setEmission(new Color(96, 96, 96)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(5).setKt(0).setKr(0.5)),

                new Polygon(new Point3D(0, 250, -250), new Point3D(-62.5, 250, -250),
                        new Point3D(-62.5, 187.5, -250), new Point3D(0, 187.5, -250))
                        .setEmission(new Color(0, 0, 0)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(5).setKt(0).setKr(0.5)),

                new Polygon(new Point3D(-62.5, 250, -250), new Point3D(-125, 250, -250),
                        new Point3D(-125, 187.5, -250), new Point3D(-62.5, 187.5, -250))
                        .setEmission(new Color(96, 96, 96)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(5).setKt(0).setKr(0.5)),

                new Polygon(new Point3D(-125, 250, -250), new Point3D(-187.5, 250, -250),
                        new Point3D(-187.5, 187.5, -250), new Point3D(-125, 187.5, -250))
                        .setEmission(new Color(0, 0, 0)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(5).setKt(0).setKr(0.5)),

                new Polygon(new Point3D(-187.5, 250, -250), new Point3D(-250, 250, -250),
                        new Point3D(-250, 187.5, -250), new Point3D(-187.5, 187.5, -250))
                        .setEmission(new Color(96, 96, 96)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(5).setKt(0).setKr(0.5)),

                //------------------------------second line-----------------------------------------
                new Polygon(new Point3D(250, 187.5, -250), new Point3D(187.5, 187.5, -250),
                        new Point3D(187.5, 125, -250), new Point3D(250, 125, -250))
                        .setEmission(new Color(96, 96, 96)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(5).setKt(0).setKr(0.5)),
                new Polygon(new Point3D(187.5, 187.5, -250), new Point3D(125, 187.5, -250),
                        new Point3D(125, 125, -250), new Point3D(187.5, 125, -250))
                        .setEmission(new Color(0, 0, 0)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(5).setKt(0).setKr(0.5)),

                new Polygon(new Point3D(125, 187.5, -250), new Point3D(62.5, 187.5, -250),
                        new Point3D(62.5, 125, -250), new Point3D(125, 125, -250))
                        .setEmission(new Color(96, 96, 96)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(5).setKt(0).setKr(0.5)),

                new Polygon(new Point3D(62.5, 187.5, -250), new Point3D(0, 187.5, -250),
                        new Point3D(0, 125, -250), new Point3D(62.5, 125, -250))
                        .setEmission(new Color(0, 0, 0)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(5).setKt(0).setKr(0.5)),

                new Polygon(new Point3D(0, 187.5, -250), new Point3D(-62.5, 187.5, -250),
                        new Point3D(-62.5, 125, -250), new Point3D(0, 125, -250))
                        .setEmission(new Color(96, 96, 96)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(5).setKt(0).setKr(0.5)),
                new Polygon(new Point3D(-62.5, 187.5, -250), new Point3D(-125, 187.5, -250),
                        new Point3D(-125, 125, -250), new Point3D(-62.5, 125, -250))
                        .setEmission(new Color(0, 0, 0)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(5).setKt(0).setKr(0.5)),

                new Polygon(new Point3D(-125, 187.5, -250), new Point3D(-187.5, 187.5, -250),
                        new Point3D(-187.5, 125, -250), new Point3D(-125, 125, -250))
                        .setEmission(new Color(96, 96, 96)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(5).setKt(0).setKr(0.5)),

                new Polygon(new Point3D(-187.5, 187.5, -250), new Point3D(-250, 187.5, -250),
                        new Point3D(-250, 125, -250), new Point3D(-187.5, 125, -250))
                        .setEmission(new Color(0, 0, 0)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(5).setKt(0).setKr(0.5)),

                //------------------------line 3-----------------------------------------------------

                new Polygon(new Point3D(250, 125, -250), new Point3D(187.5, 125, -250),
                        new Point3D(187.5, 62.5, -250), new Point3D(250, 62.5, -250))
                        .setEmission(new Color(0, 0, 0)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(5).setKt(0).setKr(0.5)),

                new Polygon(new Point3D(187.5, 125, -250), new Point3D(125, 125, -250),
                        new Point3D(125, 62.5, -250), new Point3D(187.5, 62.5, -250))
                        .setEmission(new Color(96, 96, 96)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(5).setKt(0).setKr(0.5)),


                new Polygon(new Point3D(125, 125, -250), new Point3D(62.5, 125, -250),
                        new Point3D(62.5, 62.5, -250), new Point3D(125, 62.5, -250))
                        .setEmission(new Color(0, 0, 0)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(5).setKt(0).setKr(0.5)),

                new Polygon(new Point3D(62.5, 125, -250), new Point3D(0, 125, -250),
                        new Point3D(0, 62.5, -250), new Point3D(62.5, 62.5, -250))
                        .setEmission(new Color(96, 96, 96)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(5).setKt(0).setKr(0.5)),

                new Polygon(new Point3D(0, 125, -250), new Point3D(-62.5, 125, -250),
                        new Point3D(-62.5, 62.5, -250), new Point3D(0, 62.5, -250))
                        .setEmission(new Color(0, 0, 0)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(5).setKt(0).setKr(0.5)),

                new Polygon(new Point3D(-62.5, 125, -250), new Point3D(-125, 125, -250),
                        new Point3D(-125, 62.5, -250), new Point3D(-62.5, 62.5, -250))
                        .setEmission(new Color(96, 96, 96)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(5).setKt(0).setKr(0.5)),

                new Polygon(new Point3D(-125, 125, -250), new Point3D(-187.5, 125, -250),
                        new Point3D(-187.5, 62.5, -250), new Point3D(-125, 62.5, -250))
                        .setEmission(new Color(0, 0, 0)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(5).setKt(0).setKr(0.5)),

                new Polygon(new Point3D(-187.5, 125, -250), new Point3D(-250, 125, -250),
                        new Point3D(-250, 62.5, -250), new Point3D(-187.5, 62.5, -250))
                        .setEmission(new Color(96, 96, 96)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(5).setKt(0).setKr(0.5)),

                //----------------------------------line 4-------------------------------------------
                new Polygon(new Point3D(250, 62.5, -250), new Point3D(187.5, 62.5, -250),
                        new Point3D(187.5, 0, -250), new Point3D(250, 0, -250))
                        .setEmission(new Color(96, 96, 96)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(5).setKt(0).setKr(0.5)),


                new Polygon(new Point3D(187.5, 62.5, -250), new Point3D(125, 62.5, -250),
                        new Point3D(125, 0, -250), new Point3D(187.5, 0, -250))
                        .setEmission(new Color(0, 0, 0)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(5).setKt(0).setKr(0.5)),

                new Polygon(new Point3D(125, 62.5, -250), new Point3D(62.5, 62.5, -250),
                        new Point3D(62.5, 0, -250), new Point3D(125, 0, -250))
                        .setEmission(new Color(96, 96, 96)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(5).setKt(0).setKr(0.5)),


                new Polygon(new Point3D(62.5, 62.5, -250), new Point3D(0, 62.5, -250),
                        new Point3D(0, 0, -250), new Point3D(62.5, 0, -250))
                        .setEmission(new Color(0, 0, 0)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(5).setKt(0).setKr(0.5)),

                new Polygon(new Point3D(0, 62.5, -250), new Point3D(-62.5, 62.5, -250),
                        new Point3D(-62.5, 0, -250), new Point3D(0, 0, -250))
                        .setEmission(new Color(96, 96, 96)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(5).setKt(0).setKr(0.5)),

                new Polygon(new Point3D(-62.5, 62.5, -250), new Point3D(-125, 62.5, -250),
                        new Point3D(-125, 0, -250), new Point3D(-62.5, 0, -250))
                        .setEmission(new Color(0, 0, 0)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(5).setKt(0).setKr(0.5)),


                new Polygon(new Point3D(-125, 62.5, -250), new Point3D(-187.5, 62.5, -250),
                        new Point3D(-187.5, 0, -250), new Point3D(-125, 0, -250))
                        .setEmission(new Color(96, 96, 96)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(5).setKt(0).setKr(0.5)),

                new Polygon(new Point3D(-187.5, 62.5, -250), new Point3D(-250, 62.5, -250),
                        new Point3D(-250, 0, -250), new Point3D(-187.5, 0, -250))
                        .setEmission(new Color(0, 0, 0)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(5).setKt(0).setKr(0.5)),

                //-------------------------------------------line 5-------------------------------------------------

                new Polygon(new Point3D(250, 0, -250), new Point3D(187.5, 0, -250),
                        new Point3D(187.5, -62.5, -250), new Point3D(250, -62.5, -250))
                        .setEmission(new Color(0, 0, 0)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(5).setKt(0).setKr(0.5)),

                new Polygon(new Point3D(187.5, 0, -250), new Point3D(125, 0, -250),
                        new Point3D(125, -62.5, -250), new Point3D(187.5, -62.5, -250))
                        .setEmission(new Color(96, 96, 96)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(5).setKt(0).setKr(0.5)),

                new Polygon(new Point3D(125, 0, -250), new Point3D(62.5, 0, -250),
                        new Point3D(62.5, -62.5, -250), new Point3D(125, -62.5, -250))
                        .setEmission(new Color(0, 0, 0)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(5).setKt(0).setKr(0.5)),

                new Polygon(new Point3D(62.5, 0, -250), new Point3D(0, 0, -250),
                        new Point3D(0, -62.5, -250), new Point3D(62.5, -62.5, -250))
                        .setEmission(new Color(96, 96, 96)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(5).setKt(0).setKr(0.5)),

                new Polygon(new Point3D(0, 0, -250), new Point3D(-62.5, 0, -250),
                        new Point3D(-62.5, -62.5, -250), new Point3D(0, -62.5, -250))
                        .setEmission(new Color(0, 0, 0)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(5).setKt(0).setKr(0.5)),

                new Polygon(new Point3D(-62.5, 0, -250), new Point3D(-125, 0, -250),
                        new Point3D(-125, -62.5, -250), new Point3D(-62.5, -62.5, -250))
                        .setEmission(new Color(96, 96, 96)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(5).setKt(0).setKr(0.5)),

                new Polygon(new Point3D(-125, 0, -250), new Point3D(-187.5, 0, -250),
                        new Point3D(-187.5, -62.5, -250), new Point3D(-125, -62.5, -250))
                        .setEmission(new Color(0, 0, 0)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(5).setKt(0).setKr(0.5)),

                new Polygon(new Point3D(-187.5, 0, -250), new Point3D(-250, 0, -250),
                        new Point3D(-250, -62.5, -250), new Point3D(-187.5, -62.5, -250))
                        .setEmission(new Color(96, 96, 96)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(5).setKt(0).setKr(0.5)),

                //-----------------------------------------------line 6------------------------------------------

                new Polygon(new Point3D(250, -62.5, -250), new Point3D(187.5, -62.5, -250),
                        new Point3D(187.5, -125, -250), new Point3D(250, -125, -250))
                        .setEmission(new Color(96, 96, 96)).setMaterial(new Material().setKd(0.1).setKs(0.1).setShininess(5).setKt(0).setKr(0.7)),

                new Polygon(new Point3D(187.5, -62.5, -250), new Point3D(125, -62.5, -250),
                        new Point3D(125, -125, -250), new Point3D(187.5, -125, -250))
                        .setEmission(new Color(0, 0, 0)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(5).setKt(0).setKr(0.5)),

                new Polygon(new Point3D(125, -62.5, -250), new Point3D(62.5, -62.5, -250),
                        new Point3D(62.5, -125, -250), new Point3D(125, -125, -250))
                        .setEmission(new Color(96, 96, 96)).setMaterial(new Material().setKd(0.1).setKs(0.1).setShininess(5).setKt(0).setKr(0.7)),


                new Polygon(new Point3D(62.5, -62.5, -250), new Point3D(0, -62.5, -250),
                        new Point3D(0, -125, -250), new Point3D(62.5, -125, -250))
                        .setEmission(new Color(0, 0, 0)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(5).setKt(0).setKr(0.5)),


                new Polygon(new Point3D(0, -62.5, -250), new Point3D(-62.5, -62.5, -250),
                        new Point3D(-62.5, -125, -250), new Point3D(0, -125, -250))
                        .setEmission(new Color(96, 96, 96)).setMaterial(new Material().setKd(0.1).setKs(0.1).setShininess(5).setKt(0).setKr(0.7)),

                new Polygon(new Point3D(-62.5, -62.5, -250), new Point3D(-125, -62.5, -250),
                        new Point3D(-125, -125, -250), new Point3D(-62.5, -125, -250))
                        .setEmission(new Color(0, 0, 0)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(5).setKt(0).setKr(0.5)),

                new Polygon(new Point3D(-125, -62.5, -250), new Point3D(-187.5, -62.5, -250),
                        new Point3D(-187.5, -125, -250), new Point3D(-125, -125, -250))
                        .setEmission(new Color(96, 96, 96)).setMaterial(new Material().setKd(0.1).setKs(0.1).setShininess(5).setKt(0).setKr(0.7)),

                new Polygon(new Point3D(-187.5, -62.5, -250), new Point3D(-250, -62.5, -250),
                        new Point3D(-250, -125, -250), new Point3D(-187.5, -125, -250))
                        .setEmission(new Color(0, 0, 0)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(5).setKt(0).setKr(0.5)),


                //----------------------------------------line 7---------------------------------------------------
                new Polygon(new Point3D(250, -125, -250), new Point3D(187.5, -125, -250),
                        new Point3D(187.5, -187.5, -250), new Point3D(250, -187.5, -250))
                        .setEmission(new Color(0, 0, 0)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(5).setKt(0).setKr(0.5)),

                new Polygon(new Point3D(187.5, -125, -250), new Point3D(125, -125, -250),
                        new Point3D(125, -187.5, -250), new Point3D(187.5, -187.5, -250))
                        .setEmission(new Color(96, 96, 96)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(5).setKt(0).setKr(0.5)),

                new Polygon(new Point3D(125, -125, -250), new Point3D(62.5, -125, -250),
                        new Point3D(62.5, -187.5, -250), new Point3D(125, -187.5, -250))
                        .setEmission(new Color(0, 0, 0)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(5).setKt(0).setKr(0.5)),

                new Polygon(new Point3D(62.5, -125, -250), new Point3D(0, -125, -250),
                        new Point3D(0, -187.5, -250), new Point3D(62.5, -187.5, -250))
                        .setEmission(new Color(96, 96, 96)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(5).setKt(0).setKr(0.5)),

                new Polygon(new Point3D(0, -125, -250), new Point3D(-62.5, -125, -250),
                        new Point3D(-62.5, -187.5, -250), new Point3D(0, -187.5, -250))
                        .setEmission(new Color(0, 0, 0)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(5).setKt(0).setKr(0.5)),

                new Polygon(new Point3D(-62.5, -125, -250), new Point3D(-125, -125, -250),
                        new Point3D(-125, -187.5, -250), new Point3D(-62.5, -187.5, -250))
                        .setEmission(new Color(96, 96, 96)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(5).setKt(0).setKr(0.5)),

                new Polygon(new Point3D(-125, -125, -250), new Point3D(-187.5, -125, -250),
                        new Point3D(-187.5, -187.5, -250), new Point3D(-125, -187.5, -250))
                        .setEmission(new Color(0, 0, 0)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(5).setKt(0).setKr(0.5)),

                new Polygon(new Point3D(-187.5, -125, -250), new Point3D(-250, -125, -250),
                        new Point3D(-250, -187.5, -250), new Point3D(-187.5, -187.5, -250))
                        .setEmission(new Color(96, 96, 96)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(5).setKt(0).setKr(0.5)),

                //---------------------------------------------line8----------------------------------------

                new Polygon(new Point3D(250, -187.5, -250), new Point3D(187.5, -187.5, -250),
                        new Point3D(187.5, -250, -250), new Point3D(250, -250, -250))
                        .setEmission(new Color(96, 96, 96)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(5).setKt(0).setKr(0.5)),

                new Polygon(new Point3D(187.5, -187.5, -250), new Point3D(125, -187.5, -250),
                        new Point3D(125, -250, -250), new Point3D(187.5, -250, -250))
                        .setEmission(new Color(0, 0, 0)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(5).setKt(0).setKr(0.5)),

                new Polygon(new Point3D(125, -187.5, -250), new Point3D(62.5, -187.5, -250),
                        new Point3D(62.5, -250, -250), new Point3D(125, -250, -250))
                        .setEmission(new Color(96, 96, 96)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(5).setKt(0).setKr(0.5)),

                new Polygon(new Point3D(62.5, -187.5, -250), new Point3D(0, -187.5, -250),
                        new Point3D(0, -250, -250), new Point3D(62.5, -250, -250))
                        .setEmission(new Color(0, 0, 0)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(5).setKt(0).setKr(0.5)),


                new Polygon(new Point3D(0, -187.5, -250), new Point3D(-62.5, -187.5, -250),
                        new Point3D(-62.5, -250, -250), new Point3D(0, -250, -250))
                        .setEmission(new Color(96, 96, 96)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(5).setKt(0).setKr(0.5)),


                new Polygon(new Point3D(-62.5, -187.5, -250), new Point3D(-125, -187.5, -250),
                        new Point3D(-125, -250, -250), new Point3D(-62.5, -250, -250))
                        .setEmission(new Color(0, 0, 0)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(5).setKt(0).setKr(0.5)),

                new Polygon(new Point3D(-125, -187.5, -250), new Point3D(-187.5, -187.5, -250),
                        new Point3D(-187.5, -250, -250), new Point3D(-125, -250, -250))
                        .setEmission(new Color(96, 96, 96)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(5).setKt(0).setKr(0.5)),


                new Polygon(new Point3D(-187.5, -187.5, -250), new Point3D(-250, -187.5, -250),
                        new Point3D(-250, -250, -250), new Point3D(-187.5, -250, -250))
                        .setEmission(new Color(0, 0, 0)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(5).setKt(0).setKr(0.5)));

        //****************creating Hierarchy*********************
        Geometries geos6 = new Geometries(geos1, geos2, geos3, geos4, geos5);
        scene2.geometries.add(geos6);


        //****************adding the lights*********************
        scene2.lights.add(new DirectionalLight(new Color(240, 240, 240), new Vector(0, -1, 0)));
        scene2.lights.add(new PointLight(new Color(170, 170, 170), new Point3D(-50, -50, -50)));
        scene2.lights.add(new PointLight(new Color(170, 170, 170), new Point3D(50, -50, -50)));
        scene2.lights.add(new SpotLight(new Color(255, 255, 255), new Point3D(0, -30, -100), new Vector(0, 1, 0)) //
                .setKl(4E-5).setKq(2E-7));


        ImageWriter imageWriter = new ImageWriter("final project 1 ray", 1000, 1000);

        Render render = new Render() //
                .setImageWriter(imageWriter) //
                .setCamera(camera2) //
                .setMultithreading(3)
                .setRayTracer(new BasicRayTracer(scene2).set_rayDistance(1).set_numOfRays(1));

        render.renderImage();
        render.writeToImage();

    }


}