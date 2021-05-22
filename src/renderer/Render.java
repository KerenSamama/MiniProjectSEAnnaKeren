package renderer;
import elements.Camera;
import primitives.Color;
import primitives.Ray;

import java.util.MissingResourceException;

/**
 * Render class to create the color matrix of the image from the scene
 */
public class Render {

    private ImageWriter _imageWriter;
    private Camera _camera;
    private RayTracerBase _rayTracer;


    // Chaining methods

    /**
     * Setter method for imageWriter
     * @param imageWriter
     * @return the Render object itself for chaining calls
     */
    public Render setImageWriter(ImageWriter imageWriter) {
        _imageWriter = imageWriter;
        return this;
    }



    /**
     * Setter method for camera
     * @param camera
     * @return the Render object itself for chaining calls
     */
    public Render setCamera(Camera camera) {
        _camera = camera;
        return this;
    }

    /**
     * Setter method for basicRayTracer
     * @param rayTracerBase
     * @return the Render object itself for chaining calls
     */
    public Render setRayTracer(RayTracerBase rayTracerBase) {
        _rayTracer = rayTracerBase;
        return this;
    }

    /**
     * Function renderImage makes a Loop over all the pixels of the ViewPlane,
     * for each pixel a ray will be built and for each ray we will get color from the rayTracer.
     * The color will be in the appropriate pixel
     */
    public void renderImage() {
        if(_imageWriter == null){
            throw new MissingResourceException("The fields of ImageWriter are empty","Render","_imageWriter");
        }
        int nX = _imageWriter.getNx();
        int nY = _imageWriter.getNy();
        for (int i = 0; i < nY; i++) {
            for (int j = 0; j < nX; j++) {
                Ray ray = _camera.constructRayThroughPixel(nX, nY, j, i);
                Color color = _rayTracer.traceRay(ray);
                _imageWriter.writePixel(j, i, color);
            }
        }
    }

    /**
     * Operation printGrid to create a grid of lines
     * @param interval
     * @param intervalColor
     */
    public void printGrid(int interval, Color intervalColor) {
        if(_imageWriter == null){
            throw new MissingResourceException("The fields of ImageWriter are empty","Render","_imageWriter");
        }

        int nX = _imageWriter.getNx();
        int nY = _imageWriter.getNy();
        for (int i = 0; i < nY; i++) {
            for (int j = 0; j < nX; j++) {
                if (i % interval == 0 || j % interval == 0)
                    _imageWriter.writePixel(j, i, intervalColor);
            }
        }
    }

    /**
     * Function writeToImage activate the function from the imageWriter who produces unoptimized png file of the image
     */
    public void writeToImage() {
        if(_imageWriter==null){
            throw new MissingResourceException("The fields of ImageWriter are empty","Render","_imageWriter");
        }
        _imageWriter.writeToImage();
    }
}
