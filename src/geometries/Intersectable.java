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
        public Geometry geometry;
        public Point3D point;

        /**
         * Constructor for GeoPoint class receiving two parameters :
         * @param geometry of type Geometry
         * @param point of type Point3D
         */
        public GeoPoint(Geometry geometry, Point3D point) {
            this.geometry = geometry;
            this.point = point;
        }

        /**
         * Equals method check that the comparison is on the same geometric body and the identical points
         */
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            GeoPoint geoPoint = (GeoPoint) o;
            return geometry.equals(geoPoint.geometry) && point.equals(geoPoint.point);
        }

    }

    /**
     *
     * @param ray the method findGeoIntersections receives a ray
     * @return List<GeoPoint> :  returns a list of intersections points of type GeoPoint with this ray
     */
     List<GeoPoint> findGeoIntersections(Ray ray);

    /**
     * She calls the function findGeoIntersections that returns a list of GeoPoints and recovers the field point of each GeoPoint
     * to create a list of intersection points of type Point3D
     */
    default List<Point3D> findIntersections(Ray ray) {
        var geoList = findGeoIntersections(ray);
        return geoList == null ? null
                : geoList.stream()
                .map(gp -> gp.point)
                .collect(Collectors.toList());
    }

}
