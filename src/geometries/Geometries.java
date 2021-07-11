package geometries;
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


    /**
     * Default constructor for Geometries class who initializes with an empty list
     */
    public Geometries() {

        _intersectables = new LinkedList<Intersectable>();
        setBoundingBox(); // in order to create the bounding box to all of the geometries in the list


    }


    /**
     * Geometries constructor who receives :
     * @param intersectables list of objects who implement the interface Intersectable
     */
    public Geometries(Intersectable... intersectables) {
        _intersectables = new LinkedList<Intersectable>();
        add(intersectables);

        setBoundingBox(); // in order to create the bounding box to all of the geometries in the list

    }


    /**
     * Function to add geometric bodies to the collection _intersectables and sets the bounding boxes is required;
     * @param geometries list of objects who implement the interface Intersectable
     */
    public void add(Intersectable... geometries) {
        for (Intersectable geo : geometries) {
            _intersectables.add(geo);
        }
        setBoundingBox();
         // in order to create the bounding box to all of the geometries in the list
    }




   /**
     * a function that every intersectable geometry- should have in order to create
     * a bounding box
     * Set the bounding boxes between shapes - if there is a bigger max value of smaller min value for one of the coordinates than what we already have we will switch it
     */
    public void setBoundingBox()
    {
        double xMinLimit = this.box._minX; // going in the negative direction of x
        double xMaxLimit = this.box._maxX; // going in the positive direction of x

        double yMinLimit = this.box._minY; // going in the negative direction of y
        double yMaxLimit = this.box._maxY; // going in the positive direction of y

        double zMinLimit = this.box._minZ; // going in the negative direction of z
        double zMaxLimit = this.box._maxZ; // going in the positive direction of z

        for(Object geometry : _intersectables)
        {
            if(geometry instanceof Geometry)
            {
                Geometry geo = ((Geometry)geometry);

                // setting x limits--------------------------
                if(geo.box._minX < xMinLimit)
                    xMinLimit = geo.box._minX;

                if(xMaxLimit < geo.box._maxZ)
                    xMaxLimit = geo.box._maxZ;

                // setting y limits-------------------------
                if(geo.box._minY< yMinLimit)
                    yMinLimit = geo.box._minY;

                if(yMaxLimit < geo.box._maxY)
                    yMaxLimit = geo.box._maxY;

                // setting z limits -----------------------
                if(geo.box._minZ < zMinLimit)
                    zMinLimit = geo.box._minZ;

                if(zMaxLimit < geo.box._maxZ)
                    zMaxLimit = geo.box._maxZ;
                //------------------------------------------
            }

            else
            {
                Geometries geo = ((Geometries)geometry); // in case there's an object of type Geometries- because of the hierarchy

                // setting x limits--------------------------
                if(geo.box._minX< xMinLimit)
                    xMinLimit = geo.box._minX;

                if(xMaxLimit < geo.box._maxZ)
                    xMaxLimit = geo.box._maxZ;

                // setting y limits-------------------------
                if(geo.box._minY< yMinLimit)
                    yMinLimit = geo.box._minY;

                if(yMaxLimit < geo.box._maxY)
                    yMaxLimit = geo.box._maxY;

                // setting z limits -----------------------
                if(geo.box._minZ < zMinLimit)
                    zMinLimit = geo.box._minZ;

                if(zMaxLimit < geo.box._maxZ)
                    zMaxLimit = geo.box._maxZ;
                //------------------------------------------

            }
        }

        this.box._maxX = xMaxLimit;
        this.box._minX = xMinLimit;
        this.box._maxY = yMaxLimit;
        this.box._minY = yMinLimit;
        this.box._maxZ = zMaxLimit;
        this.box._minZ =zMinLimit;


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
        if(_setBoundingBox==true && !isIntersectionWithBox(ray))// if the ray does not intersect the bounding box
        {
            return  null; // we will not calculate the intersection points with the geometries
        }
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




}
