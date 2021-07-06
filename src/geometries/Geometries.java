package geometries;
import primitives.Ray;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


/**
 * Geometries class is the class representing a collection of geometric bodies.
 * Geometries class inherits from Intersectable class according to composite design template.
 */

public class Geometries extends Intersectable {


    private List<Intersectable> _intersectables = null; // list of geometric bodies
    private  List<Geometries> _geometries =new LinkedList<>();


    /**
     * Default constructor for Geometries class who initializes with an empty list
     */
    public Geometries() {


        if(this._setBoundingBox==true) {
          //makes them the opposite of what they should be so we can build boxes by checking if there is a bigger max or smaller min
            this.box._maxX = Double.NEGATIVE_INFINITY;
            this.box._minX = Double.POSITIVE_INFINITY;
            this.box._maxY = Double.NEGATIVE_INFINITY;
            this.box._minY = Double.POSITIVE_INFINITY;
            this.box._maxZ = Double.NEGATIVE_INFINITY;
            this.box._minZ = Double.POSITIVE_INFINITY;
        }
        _intersectables = new LinkedList<Intersectable>();

    }


    /**
     * Geometries constructor who receives :
     * @param intersectables list of objects who implement the interface Intersectable
     */
    public Geometries(Intersectable... intersectables) {
        _intersectables = new LinkedList<Intersectable>();
        add(intersectables);

    }
    public Geometries(Geometries... geometries) {
        add(geometries);
    }



    /**
     * Function to add geometric bodies to the collection _intersectables and sets the bounding boxes is required;
     * @param geometries list of objects who implement the interface Intersectable
     */
    public void add(Intersectable... geometries) {

        for (Intersectable geo : geometries) {
            _intersectables.add(geo);
            //sets the bounding boxes between shapes - if there is a bigger max value of smaller min value for one of the coordinates than what we already have we will switch it
            if (this._setBoundingBox == true) {
                if (geo.box._maxX > this.box._maxX)
                    this.box._maxX = geo.box._maxX;
                if (geo.box._minX < this.box._minX)
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
            //      _geometries.add((Geometries) _intersectables);

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

    /**
     * Recursive function to check for intersections with the bvh tree
     * @param ray : the ray we are checking for an intersection with
     * @return list of intersection points
     */
    public List<GeoPoint> treeGeometries(Ray ray,double max){
        if(this._geometries.size()==0)
        {
            return this.findGeoIntersections(ray,max);
        }
        else {
            if (this.isIntersectionWithBox(ray)) {

                for (Geometries g : _geometries) {
                    g.treeGeometries(ray, max);
                }
            }
        }
        return null;
    }


}
