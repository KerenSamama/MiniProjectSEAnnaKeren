package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

import static primitives.Util.alignZero;

/**
 * PointLight class represents a light source of type point. This class inherits from Light class
 * and implements the interface LightSource
 */
public class PointLight extends  Light implements LightSource{

    // _position is a point of type Point3D who represents the light's position
    private final Point3D _position;
    // coefficients of attenuation of  PointLight
    private double _Kc = 1d;
    private double _Kl = 0d;
    private double _Kq = 0d;


    /**
     * Constructor for PointLight who sends the field intensity to the father's constructor
     * @param intensity of type Color : light's intensity
     * @param position of type Point3D : light's position
     */
    public PointLight(Color intensity, Point3D position) {
        super(intensity);
        _position = position;
    }

    /**
     * Function getIntensity returns the point's color taking into account the attenuation
     * @param p a point of type Point3D
     * @return an object of type Color
     */
    @Override
    public Color getIntensity(Point3D p) {
        double d = alignZero(_position.distance(p));
        double attenuation = 1d/(_Kc +_Kl*d+ _Kq *d*d);
        return _intensity.scale(attenuation);
    }


    /**
     * Function getL returns the vector from the light's position Pl to the Point3D p
     * @param p a point of type Point3D
     * @return an object of type Vector
     */
    @Override
    public Vector getL(Point3D p) {
        return p.subtract(_position).normalized();
    }

    @Override
    public double getDistance(Point3D point) {
        return _position.distance(point);
    }


    /**
     * Setter Method for _Kc
     * @param kc
     * @return the PointLight object itself for chaining calls
     */
    public PointLight setKc(double kc) {
        _Kc = kc;
        return this;
    }

    /**
     * Setter Method for _Kl
     * @param kl
     * @return the PointLight object itself for chaining calls
     */
    public PointLight setKl(double kl) {
        _Kl = kl;
        return this;
    }

    /**
     * Setter Method  for _Kq
     * @param kq
     * @return the PointLight object itself for chaining calls
     */
    public PointLight setKq(double kq) {
        _Kq = kq;
        return this;
    }
}
