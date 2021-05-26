package elements;

import primitives.Color;

/**
 * AmbientLight class for environmental lighting
 *
 */
public class AmbientLight extends Light {

    /**
     * Default constructor who activates the father's constructor to generate the intensity in Black Color
     */
    public AmbientLight(){
        super(Color.BLACK);
    }

    /**
     * AmbientLight Constructor who activates the father's constructor with the final intensity of environmental lighting.
     * @param Ia : original light filling according to RGB components
     * @param Ka coefficient of attenuation of light filling
     */
    public AmbientLight(Color Ia, double Ka)  {
        super(Ia.scale(Ka));
    }
}
