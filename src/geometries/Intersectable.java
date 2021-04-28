package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.List;

/**
 * interface Intersectable with the operation : findIntersections
 */
public interface Intersectable {

   /**
    * @param ray : the function findIntersections receives a ray
    * @return List<Point3D> : return a list of intersections points with this ray.
    */
   List<Point3D> findIntersections(Ray ray);
}
