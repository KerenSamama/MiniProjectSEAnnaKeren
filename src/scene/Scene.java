package scene;

import elements.AmbientLight;
import geometries.Geometries;
import primitives.Color;
import primitives.Point3D;

public class Scene {
    private final String _name;
    public AmbientLight ambientLight;
    public Color backGroundColor;
    public Geometries geometries;


    public Scene(String name) {
        _name = name;
        geometries= new Geometries();
    }

    public Scene setAmbientLight(elements.AmbientLight ambientLight) {
        this.ambientLight=ambientLight;
        return this;
    }

    public Scene setBackground(Color color) {
        this.backGroundColor=color;
        return this;
    }
}
