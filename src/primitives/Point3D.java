package primitives;

import javax.management.openmbean.OpenMBeanOperationInfo;

public class Point3D {

    final Coordinate _x;
    final Coordinate _y;
    final Coordinate _z;

    public final static Point3D ZERO = new Point3D(0d, 0d, 0d);


    /**
     * constructor for Point3D receiving three numbers of type double
     *
     * @param x value for creating x coordinate
     * @param y value for creating y coordinate
     * @param z value for creating z coordinate
     */
    public Point3D(double x, double y, double z) {

        _x = new Coordinate(x);
        _y = new Coordinate(y);
        _z = new Coordinate(z);
    }


    /**
     * Function add Vector to Point3D and return new Point3D
     *
     * @param vector vector
     * @return new Point3D
     */
    public Point3D add(Vector vector) {
        double x = _x._coord + vector._head._x._coord;
        double y = _y._coord + vector._head._y._coord;
        double z = _z._coord + vector._head._z._coord;

        return new Point3D(x, y, z);
    }

    /**
     * Function Substract two points(x,y,z) and return vector
     *
     * @param  point3D Point3D
     * @return new Vector
     */
    public Vector subtract(Point3D point3D) {
        if (point3D.equals(this)) {
            throw new IllegalArgumentException("Cannot create vector to Point(0,0,0)");
        }
        double x = _x._coord - point3D._x._coord;
        double y = _y._coord - point3D._y._coord;
        double z = _z._coord - point3D._z._coord;

        return new Vector(x, y, z);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point3D point3D = (Point3D) o;
        return _x.equals(point3D._x) && _y.equals(point3D._y) && _z.equals(point3D._z);
    }

    /**
     * Function find distance square between 2 points(x,y,z)
     *
     * @param  other Point3D
     * @return distance square
     */
    public double distanceSquare(Point3D other) {
        double xx = (other._x._coord - _x._coord) * (other._x._coord - _x._coord);
        double yy = (other._y._coord - _y._coord) * (other._y._coord - _y._coord);
        double zz = (other._z._coord - _z._coord) * (other._z._coord - _z._coord);

        return (xx + yy + zz);
    }

    /**
     * Function find distance between 2 points(x,y,z)
     *
     * @param  other Point3D
     * @return sqrt of distance square
     */

    public double distance(Point3D other) {
        return Math.sqrt(distanceSquare(other));
    }

    @Override
    public String toString() {
        return "(" + _x + "," + _y + "," + _z + ")";

    }

    public double getX() {
        return _x._coord;
    }

    public double getY() {
        return _y._coord;
    }

    public double getZ() {
        return _z._coord;
    }
}




