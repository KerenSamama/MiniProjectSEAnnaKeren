package primitives;

import static primitives.Point3D.ZERO;

public class Ray {
    final Point3D _p0;
    final Vector _dir;

    /**
     * primary constructor for Ray
     *
     * @param p0;
     * @param  dir;
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

    public Point3D getPoint() {
        return  null;
    }
}
