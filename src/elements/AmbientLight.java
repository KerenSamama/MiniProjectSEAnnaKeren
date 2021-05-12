package elements;

import primitives.Color;

/**
 * AmbientLight class for environmental lighting
 *
 */
public class AmbientLight {

    /**
     * _intensity represents the intensity of light filling
     */
    private final Color _intensity;

    /**
     * Default constructor - to generate the intensity in Black Color
     */
    public AmbientLight(){
        _intensity=Color.BLACK;
    }

    /**
     * AmbientLight Constructor
     * @param Ia : original light filling according to RGB components
     * @param Ka coefficient of attenuation of light filling
     */
    public AmbientLight(Color Ia, double Ka)  {
        _intensity = Ia.scale(Ka);
    }

    /**
     *
     * @return the value of ambient light intensity
     */
    public Color getIntensity() {
        return _intensity;
    }
}
