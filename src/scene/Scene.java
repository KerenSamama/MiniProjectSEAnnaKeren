package scene;

import elements.AmbientLight;
import elements.LightSource;
import geometries.Geometries;
import primitives.Color;
import primitives.Point3D;

import java.util.LinkedList;
import java.util.List;

public class Scene {
    private final String _name; // the name of the scene

    public Color backGroundColor = Color.BLACK; // the color of the background
    public AmbientLight ambientLight = new AmbientLight(new Color(0,0,0),1.d); // the environmental lighting
    public Geometries geometries=null; // 3D model
    public List<LightSource> lights = new LinkedList<>();

    /**
     * Constructor for Scene who receives the name of the scene and who builts an empty collection of bodies for the 3D model
     * @param name for the name of the scene
     */
    public Scene(String name) {
        _name = name;
        geometries= new Geometries();
    }

    /**
     * Update methods for ambientLight
     * @param ambientLight  the environmental lighting
     * @return the Scene object itself for chaining calls
     */
    public Scene setAmbientLight(AmbientLight ambientLight) {
        this.ambientLight=ambientLight;
        return this;
    }

    /**
     * Update methods for backGroundColor
     * @param color the color of the background
     * @return the Scene object itself for chaining calls
     */
    public Scene setBackground(Color color) {
        this.backGroundColor=color;
        return this;
    }

    /**
     * Update methods for geometries
     * @param geometries 3D model
     * @return the Scene object itself for chaining calls
     */
    public Scene setGeometries(Geometries geometries) {
        this.geometries = geometries;
        return this;
    }

    public Scene setLights(List<LightSource> lights){
       this.lights=lights;
       return this;
    }
}
