package renderer;
import elements.Camera;
import primitives.Color;
import primitives.Point3D;
import primitives.Ray;
import scene.Scene;

import java.util.List;
import java.util.MissingResourceException;

public class Render {
    private ImageWriter _imageWriter;
    private Scene _scene;
    private Camera _camera;
    private RayTracerBase _rayTracer;

    // chaining methods
    public Render setImageWriter(ImageWriter imageWriter) {
        _imageWriter = imageWriter;
        return this;
    }

     public Render setScene(Scene scene) {
         _scene = scene;
         return this;
     }

    public Render setCamera(Camera camera) {
        _camera = camera;
        return this;
    }

    public Render setRayTracer(BasicRayTracer basicRayTracer) {
        _rayTracer = basicRayTracer;
        return this;
    }

    public void renderImage() { // first exception after implement
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

    public void printGrid(int interval, Color intervalColor) {
        if(_imageWriter.getNx()==0 || _imageWriter.getNy()==0){
            throw new MissingResourceException("The fields of ImageWriter are empty","ImageWriter","Nx,Ny");
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

    public void writeToImage() {
        if(_imageWriter.getNx()==0 || _imageWriter.getNy()==0){
            throw new MissingResourceException("The fields of ImageWriter are empty","ImageWriter","Nx,Ny");
        }
        _imageWriter.writeToImage();
    }
}
