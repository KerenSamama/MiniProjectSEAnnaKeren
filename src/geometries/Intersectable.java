package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.List;
import java.util.stream.Collectors;


/**
 *  Intersectable is an abstract class for all geometries that are able to intersect with a ray.
 */
public abstract class Intersectable{


    /**
     * _setBoundingBox is a parameter for BVH feature
     * It is true if we want to use a bounding box for improvement
     */
    public boolean _setBoundingBox = false;

    /**
     * Setter function for _setBoundingBox
     * @param setBoundingBox of type boolean
     */
    public  void setBoundingBox(boolean setBoundingBox) {
        _setBoundingBox = setBoundingBox;
    }

    /**
     * A static internal helper class to create a Box. A box is represented by a minimal point and a maximal point.
     */
    public static class Box {
        //makes them the opposite of what they should be so we can build boxes by checking if there is a bigger max or smaller min
        public double _maxX = Double.NEGATIVE_INFINITY;
        public double _minX = Double.POSITIVE_INFINITY;
        public double _maxY = Double.NEGATIVE_INFINITY;
        public double _minY = Double.POSITIVE_INFINITY;
        public double _maxZ = Double.NEGATIVE_INFINITY;
        public double _minZ = Double.POSITIVE_INFINITY;
    }

    // For each intersectable shape, it calls to the default constructor to build a box
    protected Box box = new Box();




    /**
     * GeoPoint class is a static internal auxiliary class
     * (as a fully passive data structure - PDS)
     * This class gives us an information on a point but also on the geometry to which belongs this point
     */
    public static class GeoPoint {
        public Geometry _geometry;
        public Point3D _point;

        /**
         * Constructor for GeoPoint class receiving two parameters :
         * @param geometry of type Geometry
         * @param point of type Point3D
         */
        public GeoPoint(Geometry geometry, Point3D point) {
            this._geometry = geometry;
            this._point = point;
        }

        /**
         * Equals method check that the comparison is on the same geometric body and the identical points
         */
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            GeoPoint geoPoint = (GeoPoint) o;
            return _geometry.equals(geoPoint._geometry) && _point.equals(geoPoint._point);
        }

    }

    /**
     * She calls the function findGeoIntersections(Ray ray, double maxDistance)
     * and sends as maxDistance the value : Double.POSITIVE_INFINITY
     * @param ray the method findGeoIntersections receives a ray
     * @return List<GeoPoint> :  returns a list of intersections points of type GeoPoint with this ray
     */
    public List<GeoPoint> findGeoIntersections(Ray ray){
            return  findGeoIntersections(ray,Double.POSITIVE_INFINITY);

    }

    /**
     * The function receive a Ray and a maximum distance where we want to find the intersection points
     * @param ray of type Ray
     * @param maxDistance : maximum distance where we found intersection points
     * @return a list of intersections points of type GeoPoint that are located before this maxDistance
     */
    abstract List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance);

    /**
     * a function that every intersectable geometry- should have in order to create
     * a bounding box
     */
    abstract public void setBoundingBox();

    /**
     * She calls the function findGeoIntersections that returns a list of GeoPoints and recovers the field point of each GeoPoint
     * to create a list of intersection points of type Point3D
     */
    public List<Point3D> findIntersections(Ray ray) {
        var geoList = findGeoIntersections(ray);
        return geoList == null ? null
                : geoList.stream()
                .map(gp -> gp._point)
                .collect(Collectors.toList());
    }


    /**
     * This function checks if the ray intersects the box around a shape.
     * @param ray of type Ray
     * @return true if there is an intersection, otherwise return false.
     */
    public boolean isIntersectionWithBox(Ray ray){

        Point3D start = ray.getP0();

        double start_X = start.getX();
        double start_Y = start.getY();
        double start_Z = start.getZ();

        Point3D direction = ray.getDir().getHead();

        double direction_X = direction.getX();
        double direction_Y = direction.getY();
        double direction_Z = direction.getZ();

        double max_t_for_X;
        double min_t_for_X;

        //If the direction_X is negative then the _min_X give the maximal value
        if (direction_X < 0) {
            max_t_for_X = (box._minX - start_X) / direction_X;
            // Check if the Intersectable is behind the camera
            if (max_t_for_X <= 0) return false;
            min_t_for_X = (box._maxX - start_X) / direction_X;
        }
        else if (direction_X > 0) {
            max_t_for_X = (box._maxX - start_X) / direction_X;
            if (max_t_for_X <= 0) return false;
            min_t_for_X = (box._minX - start_X) / direction_X;
        }
        else {
            if (start_X >= box._maxX || start_X <= box._minX)
                return false;
            else{
                max_t_for_X = Double.POSITIVE_INFINITY;
                min_t_for_X = Double.NEGATIVE_INFINITY;
            }
        }

        double max_t_for_Y;
        double min_t_for_Y;

        if (direction_Y < 0) {
            max_t_for_Y = (box._minY - start_Y) / direction_Y;
            if (max_t_for_Y <= 0) return false;
            min_t_for_Y = (box._maxY - start_Y) / direction_Y;
        }
        else if (direction_Y > 0) {
            max_t_for_Y = (box._maxY - start_Y) / direction_Y;
            if (max_t_for_Y <= 0) return false;
            min_t_for_Y = (box._minY - start_Y) / direction_Y;
        }
        else {
            if (start_Y >= box._maxY || start_Y <= box._minY)
                return false;
            else{
                max_t_for_Y = Double.POSITIVE_INFINITY;
                min_t_for_Y = Double.NEGATIVE_INFINITY;
            }
        }

        //Check the maximal and the minimal value for t
        double temp_max = Math.min(max_t_for_Y,max_t_for_X);
        double temp_min = Math.max(min_t_for_Y,min_t_for_X);
        temp_min = Math.max(temp_min,0);

        if (temp_max < temp_min) return false;

        double max_t_for_Z;
        double min_t_for_Z;

        if (direction_Z < 0) {
            max_t_for_Z = (box._minZ - start_Z) / direction_Z;
            if (max_t_for_Z <= 0) return false;
            min_t_for_Z = (box._maxZ - start_Z) / direction_Z;
        }
        else if (direction_Z > 0) {
            max_t_for_Z = (box._maxZ - start_Z) / direction_Z;
            if (max_t_for_Z <= 0) return false;
            min_t_for_Z = (box._minZ - start_Z) / direction_Z;
        }
        else {
            if (start_Z >= box._maxZ || start_Z <= box._minZ)
                return false;
            else{
                max_t_for_Z = Double.POSITIVE_INFINITY;
                min_t_for_Z = Double.NEGATIVE_INFINITY;
            }
        }

        temp_max = Math.min(max_t_for_Z,temp_max);
        temp_min = Math.max(min_t_for_Z,temp_min);

        if (temp_max < temp_min) return false;

        return true;
    }

}

