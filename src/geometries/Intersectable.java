package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.List;


// tioud de type javadoc qui explique ce que linterface fait
public interface Intersectable {
   List<Point3D> findIntersections(Ray ray);
}
