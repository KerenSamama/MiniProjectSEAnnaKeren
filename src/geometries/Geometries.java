package geometries;
import primitives.Point3D;
import primitives.Ray;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


/**
 * Geometries class is the class representing a collection of geometric bodies.
 * Geometries class inherits from Intersectable class according to composite design template.
 */

public class Geometries extends Intersectable {


    private List<Intersectable> _intersectables = null; // list of geometric bodies
    private  List<Geometries> _geos=new ArrayList<>();


    /**
     * Default constructor for Geometries class who initializes with an empty list
     */

    public Geometries() {

        this.box._maxX = Double.NEGATIVE_INFINITY;
        this.box._minX = Double.POSITIVE_INFINITY;
        this.box._maxY= Double.NEGATIVE_INFINITY;
        this.box._minY = Double.POSITIVE_INFINITY;
        this.box._maxZ = Double.NEGATIVE_INFINITY;
        this.box._minZ = Double.POSITIVE_INFINITY;
        _intersectables = new LinkedList<Intersectable>();

    }

    /**
     * Geometries constructor who receives :
     * @param geometries list of objects who implement the interface Intersectable
     */
    public Geometries(Intersectable... geometries) {
        _intersectables = new LinkedList<Intersectable>();
        add(geometries);

        /*  for(int i = 0 ; i< geometries.length ; i++)
        {
            _intersectables.add(i, geometries[i]);
        }*/

    }


    /**
     * Function to add geometric bodies to the collection _intersectables;
     * @param geometries list of objects who implement the interface Intersectable
     */
    public void add(Intersectable... geometries) {
      // Collections.addAll(_intersectables, geometries);

       /* for(int i =0; i<geometries.length ; i++)
        {
            _intersectables.add(_intersectables.size() + i ,geometries[i]);
        }*/

        for (Intersectable geo : geometries) {
            _intersectables.add(geo);
            if (geo.box._maxX > this.box._maxX)
                this.box._maxX = geo.box._maxX;
            if (geo.box._minX< this.box._minX)
                this.box._minX = geo.box._minX;
            if (geo.box._maxY > this.box._maxY)
                this.box._maxY = geo.box._maxY;
            if (geo.box._minY < this.box._minY)
                this.box._minY = geo.box._minY;
            if (geo.box._maxZ > this.box._maxZ)
                this.box._maxZ = geo.box._maxZ;
            if (geo.box._minZ < this.box._minZ)
                this.box._minZ = geo.box._minZ;
        }

    }


    /**
     * This function returns a list of intersection points
     * that includes intersection points with the geometric bodies in the collection.
     * If there is no intercept in any body, the action will return null.
     * @param ray : the function findIntersections receives a ray
     * @return a list of intersection points of type GeoPoint with this ray.
     */
    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {
        List<GeoPoint> result = null;
        for (Intersectable geo : _intersectables) {
            List<GeoPoint> geoPoints = geo.findGeoIntersections(ray,maxDistance);  // geopoints is temporary intersections points with one geometric body
            if (geoPoints != null) {
                if (result == null) {
                    result = new LinkedList<>();
                }
                result.addAll(geoPoints);
            }
        }
        return result;
    }

    public List<GeoPoint> treeGeometries(Ray ray,double max){
        if(this._geos.size()==0)
        {
            return this.findGeoIntersections(ray,max);
        }
        else {
            if (this.isIntersectionWithBox(ray)) {

                for (Geometries g : _geos) {
                    g.treeGeometries(ray, max);
                }
            }
        }
        return null;
    }


}
