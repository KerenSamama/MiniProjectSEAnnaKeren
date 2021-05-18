package primitives;

import geometries.Geometry;
import geometries.Intersectable;
import static geometries.Intersectable.GeoPoint;

import java.util.List;

import static primitives.Point3D.ZERO;

public class Ray {
    final Point3D _p0; // starting point of the ray
    final Vector _dir; // directional vector of the ray

    /**
     * primary constructor for Ray
     *
     * @param p0
     * @param dir;
     */
    public Ray(Point3D p0, Vector dir) {
        _p0 = p0;
        _dir = dir.normalized();
    }


    public Point3D getP0() {
        return _p0;
    }

    public Vector getDir() {
        return _dir;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ray ray = (Ray) o;
        return _p0.equals(ray._p0) && _dir.equals(ray._dir);
    }


    @Override
    public String toString() {
        return "Ray{" +
                "_head=" + _p0 +
                ", _vec=" + _dir +
                '}';
    }


    /**
     * Refactoring : to calculate a point on a ray
     *
     * @param t : the distance from the beginning point p0
     * @return : the point who is on the ray with a distance t from p0
     */
    public Point3D getPoint(double t) {
        return _p0.add(_dir.scale(t));
    }


    /**
     * Find the most closest point to the starting point of the ray
     * @param intersections a list of Point3D
     * @return a point who is the closest to _p0
     */
    public Point3D getClosestPoint(List<Point3D> intersections) {
        Point3D result = null;
        if (intersections == null) {
            return null;
        }
        double distance = Double.MAX_VALUE;
        for (Point3D p : intersections) {
            double newDist = _p0.distance(p);
            if (newDist < distance) {
                distance = newDist;
                result = p;
            }
        }
        return result;
    }

    public GeoPoint getClosestGeoPoint(List<GeoPoint> intersections){
        GeoPoint result = null;
        if (intersections == null) {
            return null;
        }
        double distance = Double.MAX_VALUE;
        for (GeoPoint geo : intersections) {
            double newDist = _p0.distance(geo.point);
            if (newDist < distance) {
                distance = newDist;
                result = geo;
            }
        }
        return result;
    }
}
