package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.List;
import java.util.stream.Collectors;


/**
 * Interface Intersectable
 */
public interface Intersectable{

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
     default List<GeoPoint> findGeoIntersections(Ray ray){
         return  findGeoIntersections(ray,Double.POSITIVE_INFINITY);
     }





    /**

     * @param ray of type Ray
     * @param maxDistance : maximum distance where we found intersection points
     * @return a list of intersections points of type GeoPoint that are located before this maxDistance
     */
    List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance);

    /**
     * She calls the function findGeoIntersections that returns a list of GeoPoints and recovers the field point of each GeoPoint
     * to create a list of intersection points of type Point3D
     */
    default List<Point3D> findIntersections(Ray ray) {
        var geoList = findGeoIntersections(ray);
        return geoList == null ? null
                : geoList.stream()
                .map(gp -> gp._point)
                .collect(Collectors.toList());
    }

}

