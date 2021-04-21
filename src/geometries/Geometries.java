package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Geometries implements Intersectable {

    private List<Intersectable> _intersectables;

    public Geometries(){
        _intersectables = new ArrayList<Intersectable>();  // run time of insert and remove is quick because we can use an index
    }

    public Geometries(Intersectable... geometries) {
        _intersectables = new ArrayList<Intersectable>();

        for(int i = 0 ; i< geometries.length ; i++)
        {
            _intersectables.add(i, geometries[i]);
        }

        //add(geometries);
    }

    private void add(Intersectable... geometries) {

        for(int i =0; i<geometries.length ; i++)
        {
            _intersectables.add(_intersectables.size() + i ,geometries[i]);
        }

        //Collections.addAll(_intersectables, geometries);
    }

    @Override
    public List<Point3D> findIntersections(Ray ray) {
        List<Point3D> result = null;
        for (Intersectable geo : _intersectables) {
            List<Point3D> geoPoints = geo.findIntersections(ray);
            if (geoPoints != null) {
                if (result == null) {
                    result = new LinkedList<>();
                }
                result.addAll(geoPoints);
            }
        }
        return result;
    }
}
