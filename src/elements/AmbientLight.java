package elements;

import primitives.Color;

/**
 * AmbientLight class for environmental lighting
 *
 */
public class AmbientLight extends Light {

    /**
     * Default constructor - to generate the intensity in Black Color modifffff
     */
    public AmbientLight(){
        super(Color.BLACK);
    }

    /**
     * AmbientLight Constructor modiffff
     * @param Ia : original light filling according to RGB components
     * @param Ka coefficient of attenuation of light filling
     */
    public AmbientLight(Color Ia, double Ka)  {
        super(Ia.scale(Ka));
    }
}
