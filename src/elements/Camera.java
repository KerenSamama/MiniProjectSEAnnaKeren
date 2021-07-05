package elements;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;
import static primitives.Util.isZero;

public class Camera {
    private final Point3D _p0; // position of the camera
    private final Vector _vTo; // forward directional vector
    private final Vector _vUp; // up directional vector
    private final Vector _vRight; // right directional vector
    private double _width; // width of the view plane
    private double _height; // height of the view plane
    private double _distance; // distance from the camera to the view plane

    /**
     * Constructor for Camera class who receives two vectors and a position point
     * The constructor checks if the vectors are orthogonal, are unit vectors and creates the right directional vector.
     * @param p0 of type Point3D for position
     * @param vTo of type Vector for forward directional vector
     * @param vUp of type Vector for up directional vector
     */
    public Camera(Point3D p0, Vector vTo, Vector vUp) {
        if (!isZero(vUp.dotProduct(vTo))) {
            throw new IllegalArgumentException("vTo and vUp are not orthogonal");

        }
        _p0 = p0;
        _vTo = vTo.normalized();
        _vUp = vUp.normalized();
        _vRight = _vTo.crossProduct(_vUp);
    }


    // Getters methods
    public Point3D getP0() { return _p0; }

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

    // Setters methods using chaining

    /**
     * @param width of type double : width of the view plane
     * @param height of type double  : height of the view plane
     * @return the Camera object itself for chaining calls
     */
    public Camera setViewPlaneSize(double width, double height) {
        _width = width;
        _height = height;
        return this;
    }

    /**
     * @param distance of type double : distance from the camera to the view plane
     * @return the Camera object itself for chaining calls
     */
    public Camera setDistance(double distance) {
        _distance = distance;
        return this;
    }



    /**
     *
     * @param nX In resolution – Nx represents amount of columns (row width)
     * @param nY In resolution – Ny represents amount of rows (column height)
     * @param j index of column for pixel
     * @param i index of row for pixel
     * @return a Ray
     */
    public Ray constructRayThroughPixel(int nX, int nY,int j,int i) {
        if(isZero(_distance)){
            throw new IllegalArgumentException("distance cannot be 0");
        }
        Point3D Pc=_p0.add(_vTo.scale(_distance));
        double Ry=_height/nY; //height of one pixel
        double Rx=_width/nX;  //width of one pixel

        double Xj=((j - nX/2d)*Rx + Rx/2d);
        double Yi=((i - nY/2d)*Ry + Ry/2d);

        Point3D Pij = Point3D.ZERO;
        Vector Right = new Vector(_vRight.getHead());
        Vector Up = new Vector(_vUp.getHead());

        if(!isZero(Xj) && !isZero(Yi))
        {
            if((Right.scale(Xj)).equals(Up.scale(Yi))) {
                Pij = Pc;
            }
            else
            {
                Right = Right.scale(Xj);
                Up = Up.scale(Yi);
                Vector res = Right.subtract(Up);
                Pij = Pc.add(res);
            }
        }
        else {
            if (isZero(Xj) && !isZero(Yi)) {
                Yi = Yi * (-1);
                Up = Up.scale(Yi);
                Pij = Pc.add(Up);
            }
            if (!isZero(Xj) && isZero(Yi)) {
                Right = Right.scale(Xj);
                Pij = Pc.add(Right);
            }
            if ((isZero(Xj) && isZero(Yi))) {
                Pij = Pc;
            }
        }

        Vector vij = Pij.subtract(_p0); //the ray's vector
        vij.normalize();

        Ray ray = new Ray(_p0, vij);
        return ray;

    }

}