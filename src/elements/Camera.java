package elements;

import primitives.Point3D;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

import static primitives.Util.isZero;

public class Camera {
    private final Point3D _p0;
    private final Vector _vTo;
    private final Vector _vUp;
    private final Vector _vRight;
    private double _width;
    private double _height;
    private double _distance;


    public Camera(Point3D p0, Vector vTo, Vector vUp) {
        if (!isZero(vUp.dotProduct(vTo))) {
            throw new IllegalArgumentException("vTo and vUp are not orthogonal");

        }

        _p0 = p0;
        _vTo = vTo.normalized();
        _vUp = vUp.normalized();
        _vRight = _vTo.crossProduct(_vUp);
    }

    public Point3D getP0() {
        return _p0;
    }

    public Vector getvTo() {
        return _vTo;
    }

    public Vector getvUp() {
        return _vUp;
    }

    public Vector getvRight() {
        return _vRight;
    }

    public double getWidth() {
        return _width;
    }

    public double getHeight() {
        return _height;
    }

    public double getDistance() {
        return _distance;
    }

// setter using methode chaining

    /**
     * @param width
     * @param height
     * @return "this": camera current instance
     */
    public Camera setViewPlaneSize(double width, double height) {

        _width = width;
        _height = height;
        return this;
    }

     public Camera setDistance(double distance) {

     _distance = distance;
     return this;
     }

    /**
     *
     * @param nX
     * @param nY
     * @param j
     * @param i
     * @return
     */

    public Ray constructRayThroughPixel(int nX, int nY,int j,int i) {
        if(isZero(_distance)){
            throw new IllegalArgumentException("distance cannot be 0");
        }
        Point3D Pc=_p0.add(_vTo.scale(_distance));
        double Ry=_height/nY;
        double Rx=_width/nX;
        Point3D Pij=Pc;
        double Xj=((j - nX/2d)*Rx + Rx/2d);
        double Yi=((i - nY/2d)*Ry + Ry/2d);

        Point3D finalPoint = Point3D.ZERO;
        Vector Right = new Vector(_vRight.getHead());
        Vector Up = new Vector(_vUp.getHead());



        if(!Util.isZero(Xj) && !Util.isZero(Yi))
        {
            if((Right.scale(Xj)).equals(Up.scale(Yi))) {
                finalPoint = Pc;
            }
            else
            {
                Right = Right.scale(Xj);
                Up = Up.scale(Yi);
                Vector res = Right.substract(Up);
                finalPoint = Pc.add(res);
            }
        }
        else {
            if (Util.isZero(Xj) && !Util.isZero(Yi)) {
                Yi = Yi * (-1);
                Up = Up.scale(Yi);
                finalPoint = Pc.add(Up);
            }
            if (!Util.isZero(Xj) && Util.isZero(Yi)) {
                Right = Right.scale(Xj);
                finalPoint = Pc;
                finalPoint = finalPoint.add(Right);
            }
            if ((Util.isZero(Xj) && Util.isZero(Yi))) {
                finalPoint = Pc;
            }
        }

        Vector v = finalPoint.subtract(_p0);//the ray's vector
        v.normalize();

        Ray ray = new Ray(_p0, v);
        return ray;


    }


}
