package primitives;


import static primitives.Point3D.ZERO;
import static primitives.Util.isZero;

public class Vector {

    Point3D _head;

    /**
     * primary constructor for Vector receiving one Point3D
     *
     * @param head
     */
    public Vector(Point3D head) {
        if (ZERO.equals(head)) {
            throw new IllegalArgumentException("Vector head cannot be Point(0,0,0)");
        }
        _head = head;
    }

    /**
     * constructor for Vector receiving three numbers of type double
     * @param x
     * @param y
     * @param z
     */
    public Vector(double x, double y, double z) {
        this(new Point3D(x, y, z));
    }


    /**
     * Function get,
     *
     * @return _head
     */
    public Point3D getHead() {
        return  new Point3D(
                _head._x._coord,
                _head._y._coord,
                _head._z._coord
                );
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
     * Function scale vector with scalar
     *
     * @param  scalar double
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
     * Function do cross-product mathematic between 2 vectors
     *
     * @param v Vector v
     * @return new Vector who is perpendicular to the other two vectors
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
     * Function do dot-product between 2 vectors
     *
     * @param  v Vector pour ....
     * @return value double
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
     * Function do add two vectors
     *
     * @param v Vector v
     * @return new Vector
     */
    public Vector add(Vector v) {
        double x = _head._x._coord + v._head._x._coord;
        double y = _head._y._coord + v._head._y._coord;
        double z = _head._z._coord + v._head._z._coord;

        return new Vector(new Point3D(x, y, z));
    }

    /**
     * Function do substract 2 vectors
     *
     * @param v Vector v
     * @return new Vector
     */
    public Vector substract(Vector v) {
        if (v.equals(this)) {
            throw new IllegalArgumentException("Parameter vector cannot be equals to me");
        }
        double x = _head._x._coord - v._head._x._coord;
        double y = _head._y._coord - v._head._y._coord;
        double z = _head._z._coord - v._head._z._coord;

        return new Vector(new Point3D(x, y, z));

    }

    /**
     * Function find the vector squared length
     *
     * @return length squared (double)
     */
    public double lengthSquared() {
        double xx = _head._x._coord * _head._x._coord;
        double yy = _head._y._coord * _head._y._coord;
        double zz = _head._z._coord * _head._z._coord;

        return (xx + yy + zz);
    }

    /**
     * Function return the vector length
     *
     * @return length (double)
     */
    public double length() {
        return Math.sqrt(lengthSquared());
    }

    /**
     * Function normalize : change the original vector to a unit vector
     *
     * @return Vector
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
     * Function normalized : return a new vector which is a unit vector in the same direction
     *
     * @return new Vector
     */
    public Vector normalized() {
        Vector result = new Vector(_head);
        result.normalize();
        return result;
    }


}
