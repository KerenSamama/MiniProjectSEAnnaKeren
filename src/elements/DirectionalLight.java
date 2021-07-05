package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * DirectionalLight class represents a light source of type directional. This class inherits from Light class
 * and implements the interface LightSource
 */
public class DirectionalLight extends Light implements LightSource{

    /**
     * _direction is a vector who represents the light's direction
     */
    private final Vector _direction;

    /**
     * Constructor for DirectionalLight who sends the field intensity to the father's constructor
     * @param intensity of type Color : light's intensity
     * @param direction of type Vector : light's direction
     */
    public DirectionalLight(Color intensity, Vector direction) {
        super(intensity);
        _direction = direction.normalized();
    }

    /**
     * * Function getIntensity who returns the point's color
     * @param p a point of type Point3D
     * @return the point's color of type Color
     */
    @Override
    public Color getIntensity(Point3D p) {
        return _intensity;
    }

    /**
     * For DirectionalLight, the method getL returns the light's direction vector
     * @param p a point of type Point3D
     * @return the vector _direction
     */
    @Override
    public Vector getL(Point3D p) {
        return _direction;
    }

    /**
     * Directional light is a light source without position, therefore the function
     * getDistance returns for value Double.POSITIVE_INFINITY
     * @param point of type Point3D
     * @return  value Double.POSITIVE_INFINITY
     */
    @Override
    public double getDistance(Point3D point) {
        return Double.POSITIVE_INFINITY;
    }
}
