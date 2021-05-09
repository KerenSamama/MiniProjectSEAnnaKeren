package elements;

import primitives.Color;

public class AmbientLight {
    private final Color _intensity;

    public AmbientLight(){
        _intensity=Color.BLACK;
    }
    public AmbientLight(Color Ia, double Ka)  {
        _intensity = Ia.scale(Ka);
    }

    public Color getIntensity() {
        return _intensity;
    }
}
