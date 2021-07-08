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
    final Vector _dir; // directional vector of the ray (a unit vector)
    private static final double DELTA = 0.1; // constant double to move the head of a ray for shadows , reflections,etc

    /**
     * Primary constructor for Ray receiving a point and a vector.
     * Ray's constructor ensures that the direction vector is normalized.
     * @param p0 of type Point3D to represent the starting point of the ray
     * @param dir of type Vector to represent the directional vector of the ray
     */
    public Ray(Point3D p0, Vector dir) {
        _p0 = p0;
        _dir = dir.normalized();
    }



    /**
     * Ray constructor to create a new Ray by moving the head of the ray by DELTA over the normal's line
     * @param point of type Point3D
     * @param direction  of type Vector : direction of the ray
     * @param n of type Vector : normal Vector
     */
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
     * This function creates a beam of rays
     * @param normal of type Vector : normal vector to the head of the ray who calls the function
     * @param distance  of type double : the distance between the  point and the circle we are creating to find the beam
     * @param numOfRays of type int : the number of rays that will be in the beam
     * @return a list of all the rays int the beam
     */
    public List<Ray>createBeamOfRays(Vector normal,double distance,int numOfRays)
    {
        List<Ray> beam=new LinkedList<Ray>();
        beam.add(this);//the original ray that calls the function - there has to be at least one ray
        if(numOfRays==1) //if no additional rays were requested here
            return beam;

        Vector w=this.getDir().normalToVector();//finds a vector that is normal to the direction of the ray
        //the cross product between the normal vector w and the direction gives a new normal vector to the direction of the ray
        Vector v=this.getDir().crossProduct(w).normalize();

        Point3D center=this.getPoint(distance);//the center of our circle is the distance requested from p0, the head of the ray that calls the function
        Point3D randomP=Point3D.ZERO;
        double xRandom,yRandom,random;

        double randomRadiusValue = random(3 ,6); // the radius will be in range: 3 < r < 6, and will have double values


        for(int i=1;i<numOfRays;i++)//starts from 1 because there has to be at least one ray(the original)and we already add it to the beam
        {
            xRandom=random(-1,1);//random number [-1,1)
            yRandom=Math.sqrt(1-Math.pow(xRandom,2)); // toujours entre 0 et 1
            random=random(-randomRadiusValue,randomRadiusValue);
            if(xRandom!=0)//vector cannot be scaled with zero
                randomP=center.add(w.scale(xRandom*randomRadiusValue));//
            if(yRandom!=0)//vector cannot be scaled with zero
                randomP=center.add(v.scale(yRandom*randomRadiusValue));//randomRadiusValue

            Vector t= randomP.subtract(this.getP0());//vector from the head of the original ray and the random point

            double normalDotDir=alignZero(normal.dotProduct(this.getDir()));
            double normalDotT=alignZero(normal.dotProduct(t));
            if(normalDotDir*normalDotT>0)//if they are both positive or both negative then we need to create a ray with the original p0 and t
                beam.add(new Ray(this.getP0(),t));
        }
        return beam;
    }

}
