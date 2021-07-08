package primitives;

import static primitives.Point3D.ZERO;
import static primitives.Util.isZero;

/**
 * Class Vector is the basic class representing a vector of Euclidean geometry in Cartesian
 * 3-Dimensional coordinate system.
 * A vector is an object with direction and size and
 * is defined by the end point when the start point is the first of the axes
 */
public class Vector {

    Point3D _head;

    /**
     * Primary constructor for Vector receiving a Point3D
     * @param head of type Point3D
     */
    public Vector(Point3D head) {
        if (ZERO.equals(head)) {
            throw new IllegalArgumentException("Vector head cannot be Point(0,0,0)");
        }
        _head = head;
    }

    /**
     * Constructor for Vector receiving three numbers of type double and calling to the primary constructor
     * @param x double value for creating x coordinate
     * @param y double value for creating y coordinate
     * @param z double value for creating z coordinate
     */
    public Vector(double x, double y, double z) {
        this(new Point3D(x, y, z));
    }



    /**
     * Function get
     * @return _head
     */
    public Point3D getHead() {
        return  new Point3D(
                _head._x._coord,
                _head._y._coord,
                _head._z._coord
                );
    }


    /**
     * Function for product vector with scalar and returns a new vector
     * @param  scalar of type double
     * @return new Vector
     */
    public Vector scale(double scalar) {
        if (scalar == 0d) {
            throw new IllegalArgumentException("Scalar cannot be 0");
        }

        double x = _head._x._coord * scalar;
        double y = _head._y._coord * scalar;
        double z = _head._z._coord * scalar;

        return new Vector(new Point3D(x, y, z));

    }

    /**
     * Function for cross-product mathematics between 2 vectors
     * @param v of type Vector
     * @return new Vector who is perpendicular to the others two vectors
     */
    public Vector crossProduct(Vector v) {

        double u1 = _head._x._coord;
        double u2 = _head._y._coord;
        double u3 = _head._z._coord;

        double v1 = v._head._x._coord;
        double v2 = v._head._y._coord;
        double v3 = v._head._z._coord;

        return new Vector(new Point3D(
                u2 * v3 - u3 * v2,
                u3 * v1 - u1 * v3,
                u1 * v2 - u2 * v1
        ));

    }

    /**
     * Function for dot-product between two vectors and returns a double value
     * @param  v of type Vector
     * @return double value
     */
    public double dotProduct(Vector v) {
        double u1 = _head._x._coord;
        double u2 = _head._y._coord;
        double u3 = _head._z._coord;

        double v1 = v._head._x._coord;
        double v2 = v._head._y._coord;
        double v3 = v._head._z._coord;

        return (u1 * v1 + u2 * v2 + u3 * v3);
    }

    /**
     * Function for add two vectors and returns a new Vector
     * @param v of type Vector
     * @return new Vector
     */
    public Vector add(Vector v) {
        double x = _head._x._coord + v._head._x._coord;
        double y = _head._y._coord + v._head._y._coord;
        double z = _head._z._coord + v._head._z._coord;

        return new Vector(new Point3D(x, y, z));
    }

    /**
     * Function for subtract two vectors and returns a new Vector
     * @param v of type Vector
     * @return new Vector
     */
    public Vector subtract(Vector v) {
        if (v.equals(this)) {
            throw new IllegalArgumentException("Parameter vector cannot be equals to me to subtract");
        }
        double x = _head._x._coord - v._head._x._coord;
        double y = _head._y._coord - v._head._y._coord;
        double z = _head._z._coord - v._head._z._coord;

        return new Vector(new Point3D(x, y, z));

    }

    /**
     * Function to calculate the vector squared length
     * @return double value
     */
    public double lengthSquared() {
        double xx = _head._x._coord * _head._x._coord;
        double yy = _head._y._coord * _head._y._coord;
        double zz = _head._z._coord * _head._z._coord;

        return (xx + yy + zz);
    }

    /**
     * Function to calculate the vector length and using the function lengthSquared
     * @return double value for length
     */
    public double length() {
        return Math.sqrt(lengthSquared());
    }

    /**
     * Function normalize who change the original vector to a unit vector
     * @return this who is the original Vector
     */
    public Vector normalize() {

        double len = length();
        if (len == 0) {
            throw new ArithmeticException("Cannot divide by 0");
        }

        double x = _head._x._coord / len;
        double y = _head._y._coord / len;
        double z = _head._z._coord / len;

        Point3D newHead = new Point3D(x, y, z);
        if (ZERO.equals(newHead)) {
            throw new IllegalArgumentException("Vector head cannot be point(0,0,0)");
        }

        _head = new Point3D(x, y, z);
        return this;
    }

    /**
     * Function normalized who returns a new vector which is a unit vector in the same direction like the original vector
     * @return new Vector
     */
    public Vector normalized() {
        Vector result = new Vector(_head);
        result.normalize();
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector vector = (Vector) o;
        return _head.equals(vector._head);
    }

    @Override
    public String toString() {
        return "Vector{" +
                "_head=" + _head +
                '}';
    }

    /**
     * This function helps us to calculate  a normal vector to the vector that calls the function
     * @return a new vector
     */
    public Vector normalToVector()
    {
        double coordinate;

        if(this.getHead().getX()>0) //finding the smallest coordinate of the vector to replace it with 0
        {
            coordinate = this.getHead().getX();
        }
        else {
            coordinate = -this.getHead().getX();
        }

        if(Math.abs(this.getHead().getY())<coordinate)
        {
            coordinate=1;
            if(this.getHead().getY()>0)
                coordinate=this.getHead().getY();
            else
                coordinate=-this.getHead().getY();
        }
        if(Math.abs(this.getHead().getZ())<coordinate)
        {
            coordinate=2;//last coordinate that we are checking so no need to reassign coordinate

        }
        if(coordinate==0) {//x is the smallest
            return new Vector(0, -this.getHead().getZ(), this.getHead().getY()).normalize();
        }
        if(coordinate==1) {//y is the smallest
            return new Vector(-this.getHead().getZ(), 0, this.getHead().getX()).normalize();
        }
        //z is the smallest
        return new Vector(this.getHead().getY(),-this.getHead().getX(),0).normalize();
    }


}

