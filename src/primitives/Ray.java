package primitives;
import elements.LightSource;

import static geometries.Intersectable.GeoPoint;
import static primitives.Util.alignZero;
import static primitives.Util.random;

import java.util.LinkedList;
import java.util.List;

/**
 * Class Ray is the basic class representing a ray of Euclidean geometry in Cartesian
 * 3-Dimensional coordinate system.
 * Ray is defined by the set of points on a straight that are on one relative side
 * to a given point who is the starting point of the ray.
 */
public class Ray {
    final Point3D _p0; // starting point of the ray
    final Vector _dir; // directional vector of the ray
    private static final double DELTA = 0.1;

    /**
     * Primary constructor for Ray receiving a point and a vector
     * @param p0
     * @param dir
     */
    public Ray(Point3D p0, Vector dir) {
        _p0 = p0;
        _dir = dir.normalized();
    }

    /**
     * Special Ray moove a little of origin
     * @param point
     * @param lightSource
     * @param n
     * @param delta
     */
    public Ray(Point3D point, LightSource lightSource, Vector n, double delta) {
       Vector l= lightSource.getL(point).scale(-1);
        Vector teta=n.scale(n.dotProduct(l)>0? delta : -delta);
        _p0=point.add(teta);
        _dir=l;
    }

    public Ray(Point3D point, Vector direction, Vector n) {
        Vector teta=n.scale(n.dotProduct(direction)>0? DELTA : -DELTA);
        _p0=point.add(teta);
        _dir=direction.normalized();
    }


    /**
     * Getters
     */
    public Point3D getP0() {
        return _p0;
    }

    public Vector getDir() {
        return _dir;
    }

    /**
     * Refactoring : to calculate a point on a ray
     * @param t : the distance from the beginning point p0
     * @return : the point who is on the ray with a distance t from p0
     */
    public Point3D getPoint(double t) {
        return _p0.add(_dir.scale(t));
    }


    /**
     * Find the most closest point of type Point3D to the starting point of the ray
     * @param intersections a list of Point3D
     * @return a point of type Point3D who is the closest to _p0
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


    /**
     * Find the most closest point of type GeoPoint to the starting point of the ray
     * @param intersections a list of GeoPoint
     * @return a point of type GeoPoint who is the closest to _p0
     */
    public GeoPoint findClosestGeoPoint(List<GeoPoint> intersections){
        GeoPoint result = null;
        if (intersections == null) {
            return null;
        }
        double distance = Double.MAX_VALUE;
        for (GeoPoint geo : intersections) {
            double newDist = _p0.distance(geo._point);
            if (newDist < distance) {
                distance = newDist;
                result = geo;
            }
        }
        return result;
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
     * creates a beam of rays(in a list of rays)
     *
     * @param n        Vector - normal vector where the rays start
     * @param distance double - the distance between the  point and the circle we are creating to find the beam
     * @param num      int - the number of rays that will be in the beam
     * @return list that includes all the rays that make up the beam
     */
    public List<Ray> createBeamOfRays(Vector n, double distance, int num) {
        List<Ray> beam = new LinkedList<Ray>();
        beam.add(this);//the original ray that calls the function - there has to be at least one beam
        if (num == 1)//if no additional rays were requested here  there is nothing else to do in this function
            return beam;
        Vector w = this.getDir().normalToVector();//finds a vector that is normal to the direction on the ray
        Vector v = this.getDir().crossProduct(w).normalize();//the cross product between the normal and the direction

        Point3D center = this.getPoint(distance);//the center of our circle is the distance requested from p0
        Point3D randomP = Point3D.ZERO;
        double xRandom, yRandom, random;
        double nDotDirection = alignZero(n.dotProduct(this.getDir()));
        double r = Math.abs(Math.tan(Math.acos(w.dotProduct(v))));
        for (int i = 1; i < num; i++)//starts from 1 because there has to be at least one ray(the original)and we already dealt with it
        {
            xRandom = random(-1, 1);//random number [-1,1)
            yRandom = Math.sqrt(1 - Math.pow(xRandom, 2));
            random = random(-r, r);//random number[-r,r)
            if (xRandom != 0)//vector cannot be scaled with zero
                randomP = center.add(w.scale(random));
            if (yRandom != 0)//vector cannot be scaled with zero
                randomP = center.add(v.scale(random));
            Vector t = randomP.subtract(this.getP0());//vector between the random point and the start of the original ray
            double normalDotT = alignZero(n.dotProduct(t));
            if (nDotDirection * normalDotT > 0)//if they are both positive or both negative then we need to create a ray with the original p0 and t
                beam.add(new Ray(this.getP0(), t));
        }
        return beam;
    }

}
