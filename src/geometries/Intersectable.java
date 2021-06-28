package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.List;
import java.util.stream.Collectors;


/**
 * Interface Intersectable
 */
public abstract class Intersectable{

   // public boolean setBoxes = false;
    public static boolean _setBoundingBoxes = false;
    public static void set_BoundingBoxes(boolean _actBoundingBox) {
        Intersectable._setBoundingBoxes = _actBoundingBox;
    }

    public class Box{
        public double _maxX = Double.POSITIVE_INFINITY;
        public double _minX = Double.NEGATIVE_INFINITY;
        public double _maxY = Double.POSITIVE_INFINITY;
        public double _minY = Double.NEGATIVE_INFINITY;
        public double _maxZ = Double.POSITIVE_INFINITY;
        public double _minZ = Double.NEGATIVE_INFINITY;
    }

    protected Box box = new Box();

    public Box getBox() {
        return box;
    }

    public void setMaxX(double max_X) {
        this.box._maxX = max_X;
    }

    public void setMinX(double min_X) {
        this.box._minX = min_X;
    }

    public void setMaxY(double max_Y) {
        this.box._maxY = max_Y;
    }

    public void setMinY(double min_Y) {
        this.box._minY = min_Y;
    }

    public void setMaxZ(double max_Z) {
        this.box._maxZ = max_Z;
    }

    public void setMinZ(double min_Z) {
        this.box._minZ = min_Z;
    }


    /**
     * GeoPoint class is a static internal auxiliary class
     * (as a fully passive data structure - PDS)
     * This class gives us an information on a point but also on the class to which belongs this point
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
        if (!_setBoundingBoxes || isIntersectionWithBox(ray)) {
            return  findGeoIntersections(ray,Double.POSITIVE_INFINITY);
        }
        return null;
     }

    /**

     * @param ray of type Ray
     * @param maxDistance : maximum distance where we found intersection points
     * @return a list of intersections points of type GeoPoint that are located before this maxDistance
     */
    abstract List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance);

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
            // Check if the Intersectble is behind the camera
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

