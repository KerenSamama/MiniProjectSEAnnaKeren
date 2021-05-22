package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

import static primitives.Util.alignZero;

public class SpotLight extends PointLight{
    private final Vector _direction;

    protected SpotLight(Color intensity, Point3D position, Vector direction) {
        super(intensity, position);
        _direction = direction.normalized();
    }

    @Override
    public Color getIntensity(Point3D p) {
        double cosTetha = _direction.dotProduct(getL(p));
        Color intensity= super.getIntensity(p);
        return intensity.scale(alignZero(Math.max(0,cosTetha)));

    }

}
