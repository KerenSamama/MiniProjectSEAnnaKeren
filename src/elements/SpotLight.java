package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

import static primitives.Util.alignZero;

/**
 * SpotLight class represents a light source of type spot. This class inherits from PointLight class
 */
public class SpotLight extends PointLight{

    // _direction is a vector who represents the light's direction
    private final Vector _direction;

    /**
     * Constructor for PointLight who sends the field intensity and position to the father's constructor
     * @param intensity of type Color : light's intensity
     * @param position of type Point3D : light's position
     * @param direction of type Vector : light's direction
     */
    protected SpotLight(Color intensity, Point3D position, Vector direction) {
        super(intensity, position);
        _direction = direction.normalized();
    }

    /**
     * Function getIntensity returns the point's color taking into account the attenuation and the light's direction
     * @param p a point of type Point3D
     * @return an object of type Color
     */
    @Override
    public Color getIntensity(Point3D p) {
        double cosTetha = _direction.dotProduct(getL(p));
        Color intensity= super.getIntensity(p);
        return intensity.scale(alignZero(Math.max(0,cosTetha)));

    }

}
