package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * LightSource is an interface who represents all light sources
 */
public interface LightSource {

    /**
     * Function getIntensity who returns the point's color
     * @param p a point of type Point3D
     * @return an object of type Color
     */
    public Color getIntensity(Point3D p);


    /**
     * Function getL returns the vector from the light's position Pl to the Point3D p
     * @param p a point of type Point3D
     * @return an object of type Vector
     */
    public Vector getL(Point3D p);

    /**
     * Function to get the distance between the light source and a point
     * @param point of type Point3D
     * @return double value for distance
     */
    double getDistance(Point3D point);
}
