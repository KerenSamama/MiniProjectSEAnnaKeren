package geometries;

import primitives.Point3D;
import primitives.Ray;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


/**
 * Geometries class is the class representing a collection of geometric bodies.
 * Geometries class implements the interface Intersectable.
 */

public class Geometries implements Intersectable {


    private List<Intersectable> _intersectables = null; // list of geometric bodies


    /**
     * default constructor who initializes with an empty list
     */
    public Geometries(){
        _intersectables = new ArrayList<Intersectable>();
        // ArrayList choice because run time of insert and remove is quick because we can use an index
    }


    /**
     * Geometries constructor who receives :
     * @param geometries list of objects who implement the interface Intersectable
     */
    public Geometries(Intersectable... geometries) {
        _intersectables = new ArrayList<Intersectable>();

      /*  for(int i = 0 ; i< geometries.length ; i++)
        {
            _intersectables.add(i, geometries[i]);
        }*/
        add(geometries);
    }


    /**
     * function to add geometric bodies to the collection _intersectables;
     * @param geometries list of objects who implement the interface Intersectable
     */
    public void add(Intersectable... geometries) {


       /* for(int i =0; i<geometries.length ; i++)
        {
            _intersectables.add(_intersectables.size() + i ,geometries[i]);
        }*/
        Collections.addAll(_intersectables, geometries);
    }


    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray) {
        List<GeoPoint> result = null;
        for (Intersectable geo : _intersectables) {
            List<GeoPoint> geoPoints = geo.findGeoIntersections(ray);  // geopoints is temporary intersections points with one geometric body
            if (geoPoints != null) {
                if (result == null) {
                    result = new LinkedList<>();
                }
                result.addAll(geoPoints);
            }
        }
        return result;
    }

    /** on a supprimer la fonction findintersection
     * This function returns a list of intersection points that includes intersection points of all the bodies in the collection.
     * If there is no intercept in any body, the action will return null.
     * @param ray : the function findIntersections receives a ray
     * @return a list of intersection points with this ray.
     */

}
