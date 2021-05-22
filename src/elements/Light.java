package elements;

import primitives.Color;

abstract class Light {

    /**
     * _intensity represents the intensity of light
     */
    protected final Color _intensity;

    protected Light(Color intensity) {
        _intensity = intensity;
    }

    /**
     * @return the value of  light intensity
     */
    public Color getIntensity() {
        return _intensity;
    }
}
