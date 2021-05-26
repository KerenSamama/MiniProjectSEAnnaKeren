package elements;
import primitives.Color;

/**
 * Light Class is an abstract class who represents all type of lights
 */
abstract class Light {

    /**
     * _intensity represents the intensity of light
     */
    protected final Color _intensity;

    /**
     * Constructor for Light class
     * @param intensity of type Color
     */
    protected Light(Color intensity) {
        _intensity = intensity;
    }

    /** Getter Method
     * @return _intensity : the value of  light intensity
     */
    public Color getIntensity() {
        return _intensity;
    }
}
