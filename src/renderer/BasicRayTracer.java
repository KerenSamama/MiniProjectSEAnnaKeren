package renderer;
import static geometries.Intersectable.GeoPoint;
import static java.lang.System.out;
import static primitives.Util.alignZero;
import elements.LightSource;
import primitives.*;
import scene.Scene;
import java.util.LinkedList;
import java.util.List;

/**
 * BasicRayTracer class inherits from the abstract class RayTracerBase
 */
public class BasicRayTracer extends RayTracerBase {
    /**
     * Constants for the base case of recursion
     */
    private static final double INITIAL_K = 1.0;
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final double MIN_CALC_COLOR_K = 0.001;

    /**
     * Parameters for ray tracing- glossy surface and diffuse glass - they are in class BasicRayTracer
     * because this class takes care of ray tracing.
     */
    private int _numOfRays=0;
    private double _rayDistance=0;

    private boolean _bvhTree;
    public BasicRayTracer set_bvhTree(boolean sign)
    {
        this._scene.getGeometries()._setBoundingBox=sign;
        return this;

    }


    /**
     * Set the distance between the intersection point and the circle
     * @param _rayDistance of type double
     */
    public BasicRayTracer set_rayDistance(double _rayDistance) {
        if (_rayDistance < 0)
            throw new IllegalArgumentException("Distance cannot be negative");
        this._rayDistance = _rayDistance;
        return this;
    }
    /**
     * Get the distance we want between the intersection point and the circle
     * @return double for distance
     */

    public double get_rayDistance() {
        return _rayDistance;
    }


    /**
     * Set the number of rays that will be part of the beam
     * @param _numOfRays of type int :  amount of rays that will be part of the beam
     */
    public BasicRayTracer set_numOfRays(int _numOfRays) {
        if (_numOfRays < 0)
            throw new IllegalArgumentException("Number of rays cannot be negative");
        this._numOfRays = _numOfRays;
        return this;
    }
    /**
     * Get number of rays of the beam
     * @return number of rays that will be part of the beam
     */
    public int get_numOfRays() {
        return _numOfRays;
    }


    /**
     * Constructor for BasicRayTracer receiving a scene object
     * and activating the father constructor
     * @param scene of type Scene
     */
    public BasicRayTracer(Scene scene) {
        super(scene);
    }

    /**
     * Function traceRay calculates the list of intersection points between a ray and the 3D model.
     * If there are, calculates the closest point to the
     * head of the ray and returns the color on this point by calling the function calcColor.
     * * If there are no intersections, returns the background color of the scene.
     * @param ray of type Ray : a ray from the Camera
     * @return Color object : the color at closest point
     */
    @Override
    public Color traceRay(Ray ray) {
        List<GeoPoint> intersections = _scene.geometries.findGeoIntersections(ray);
        if (intersections != null) {
            GeoPoint closestPoint = ray.findClosestGeoPoint(intersections);
            return calcColor(closestPoint, ray);
        }
        return _scene.backGroundColor;

    }


    /**
     * Calculate a color on a point. For that, this method adds the intensity of the ambientLight,
     * and calls a helper recursion function calcColor and also sends the parameters of the reccurcia.
     * @param closestPoint of type GeoPoint
     * @param ray of type Ray : a ray from the camera to the object
     * @return the point's color intensity of type Color
     */
    private Color calcColor(GeoPoint closestPoint, Ray ray) {
        return calcColor(closestPoint, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K)
                .add(_scene.ambientLight.getIntensity());
    }

    /**
     * To calculate a color on a point, this method adds the object's emission color
     * and adds the local effects according to the Phong Reflectance Model by calling the function calcLocalEffects.
     * This functions adds also the effects of the secondary rays by calling the function
     * calcGlobalEffects who is a recursive function. Before calling calcGlobalEffects,
     * we check if we haven't reached to recursion stop conditions
     * @param intersection of type GeoPoint : the closest intersection point with the head of the ray
     * @param ray of type Ray : a ray from the camera
     * @param level the recursion level
     * @param k double - helps with recursion
     * @return Color object from the effects of the secondary rays
     */
    private Color calcColor(GeoPoint intersection, Ray ray, int level, double k) {
        Color color = intersection._geometry.getEmission();
        color = color.add(calcLocalEffects(intersection, ray, k));
        return 1 == level ? color : color.add(calcGlobalEffects(intersection, ray, level, k));
    }


    /**
     * This function takes account of global effects of interactions
     * between objects such as reflections and refractions of light.
     * This helps to be able to model transparent objects (with various
     * opacity levels) and reflecting surfaces such as mirrors.
     * @param geoPoint of type GeoPoint : the closest intersection point with the head of the ray from the camera
     * @param ray of type Ray : a ray from the camera
     * @param level the recursion level
     * @param k double - helps with recursion
     * @return color object from
     */
    private Color calcGlobalEffects(GeoPoint geoPoint, Ray ray, int level, double k) {
        Color color = Color.BLACK;
        Color ReflectedColor = Color.BLACK;
        Color RefractedColor = Color.BLACK;
        Vector n = geoPoint._geometry.getNormal(geoPoint._point);
        Material material = geoPoint._geometry.getMaterial();
        List<Ray>beam1=new LinkedList<>(); // for beam of reflected ray
        List<Ray>beam2=new LinkedList<>(); // for beam of refracted ray

        double kkr = k * material.Kr;
        if (kkr > MIN_CALC_COLOR_K) { //if the reflection is bigger than the minimum of calc color

            Ray reflectionRay = constructReflectedRay(geoPoint,ray.getDir());
            //color = calcGlobalEffects(reflectionRay, level, material.Kr, kkr);
            if(this._numOfRays==0  || this._rayDistance<=0){
                beam1.add(reflectionRay);
            }
            else {
                beam1= reflectionRay.createBeamOfRays(geoPoint._geometry.getNormal(geoPoint._point), this.get_rayDistance(),this.get_numOfRays());
            }
            for(Ray r : beam1) // r = reflectedRay
            {
                ReflectedColor = ReflectedColor.add(calcGlobalEffects(r, level, material.Kr, kkr)); // //calls the recursion to find the rest of the color
            }
            if(beam1.size()>0) {
                color = color.add(ReflectedColor.reduce(beam1.size()));
            }
        }

        double kkt = k * material.Kt;
        if (kkt > MIN_CALC_COLOR_K) { //if the refraction is bigger than the minimum of calc color
            Ray refractionRay = constructRefractedRay(geoPoint,ray.getDir());
            //color = color.add(calcGlobalEffects(refractionRay, level, material.Kt, kkt));
            if(this._numOfRays==0 ||this._rayDistance<=0)
                beam2.add(refractionRay);
            else{
                beam2= refractionRay.createBeamOfRays(geoPoint._geometry.getNormal(geoPoint._point), this.get_rayDistance(),this.get_numOfRays());
            }
            for(Ray r : beam2) // r = refractedRay
            {
                RefractedColor = RefractedColor.add(calcGlobalEffects(r, level, material.Kt,kkt)); // //calls the recursion to find the rest of the color
            }

            if(beam2.size()>0) {
                color = color.add(RefractedColor.reduce(beam2.size()));
            }
        }

        return color;

    }


        private Color calcGlobalEffects(Ray ray, int level, double kx, double kkx) {
        GeoPoint gp = ray.findClosestGeoPoint(_scene.geometries.findGeoIntersections(ray));
        //return (gp == null ? Color.BLACK.add(_scene.backGroundColor).scale(kx) : calcColor(gp, ray, level - 1, kkx)).scale(kkx);
        return  ((gp == null ? _scene.backGroundColor : calcColor(gp, ray, level-1, kkx)).scale(kkx));

    }



    /**
     * Method constructRefractedRay for calculate the RefractedRay
     * @param geoPoint of type GeoPoint from where we want to create a refracted ray
     * @param v of type Vector who is the vector direction of the ray from the camera
     * @return a new Ray who is refracted ray
     */
    private Ray constructRefractedRay(GeoPoint geoPoint, Vector v) {
        Vector n = geoPoint._geometry.getNormal(geoPoint._point);
        return new Ray(geoPoint._point, v, n);

    }

    /**
     * Method constructReflectedRay to generate secondary ray as a reflection ray.
     * @param geoPoint of type GeoPoint from where we want to create a reflected ray
     * @param v of type Vector who is the vector direction of the ray from the camera
     * @return a new Ray who is reflected ray
     */
    private Ray constructReflectedRay(GeoPoint geoPoint, Vector v) {
        Vector n = geoPoint._geometry.getNormal(geoPoint._point);
        Vector r = v.subtract(n.scale(2 * v.dotProduct(n))).normalize();
        return new Ray(geoPoint._point, r, n);
    }


    /**
     * Method calcLocalEffects for adding diffusion/specular calculation
     * @param intersection of type Geopoint : an intersection point between the ray and a geometry
     * @param ray of type Ray : a ray from the camera
     * @return a color object for specular and diffusion light
     */
    private Color calcLocalEffects(GeoPoint intersection, Ray ray, double k) {
        Vector v = ray.getDir();
        Vector n = intersection._geometry.getNormal(intersection._point);
        double nv = alignZero(n.dotProduct(v));
        if (nv == 0) {
            return Color.BLACK;
        }
        Material material = intersection._geometry.getMaterial();
        int nShininess = material.nShininess;
        double kd = material.Kd;
        double ks = material.Ks;
        Color color = Color.BLACK;
        for (LightSource lightSource : _scene.lights) {
            Vector l = lightSource.getL(intersection._point); // from lightSource to point
            double nl = alignZero(n.dotProduct(l));
            if (nl * nv > 0) { // sign(nl) == sing(nv)
                // if (unshaded(lightSource,l,n, intersection)) {
                double ktr = transparency(lightSource, l, n, intersection);
                if (ktr * k > MIN_CALC_COLOR_K) {
                    Color lightIntensity = lightSource.getIntensity(intersection._point).scale(ktr);
                    color = color.add(calcDiffusive(kd, l, n, lightIntensity),
                            calcSpecular(ks, l, n, v, nShininess, lightIntensity));
                }
            }
        }
        return color;

    }


    /**
     * This function helps to calculate the level of transparency
     * @param light of type LightSource
     * @param l of type Vector : Vector from light source to point
     * @param n of type Vector : normal vector at the geometry on the geopoint
     * @param geopoint of type GeoPoint : an intersection point between the geometry and a ray from the camera
     * @return  double number
     */
    private double transparency(LightSource light, Vector l, Vector n, GeoPoint geopoint) {
        Vector lightDirection = l.scale(-1); // from point to light source
        Point3D point = geopoint._point;
        Ray lightRay = new Ray(point, lightDirection, n); // refactored ray head move from delta
        double lightDistance = light.getDistance(point);
        var intersections = _scene.geometries.findGeoIntersections(lightRay); //finds intersections between the scene's geometries and the new ray
        if (intersections == null) return 1.0; //checks if there are no intersections with the light source
        double ktr = 1.0;
        for (GeoPoint gp : intersections) {
            if (alignZero((gp._point.distance(geopoint._point))- lightDistance) <= 0) { // //checks for each point if the distance is negative and the transparency is 0
                ktr *= gp._geometry.getMaterial().getKt(); // multiplies with the geometry's transparency
                if (ktr < MIN_CALC_COLOR_K) return 0.0; // //if the transparency is smaller than the min calc color
            }
        }
        return ktr;
    }



    /**
     * @param light of type LightSource
     * @param l of type Vector : Vector from light source to point
     * @param n of type Vector : normal vector at the geometry on the geopoint
     * @param geopoint of type GeoPoint : an intersection point between the geometry and a ray from the camera
     * @return true if the point is unshaded, else false
     */
    // @Deprecated
    private boolean unshaded(LightSource light, Vector l, Vector n, GeoPoint geopoint) {
        Vector lightDirection = l.scale(-1); // from point to light source
        Ray lightRay = new Ray(geopoint._point, lightDirection, n); // refactored ray head move
        List<GeoPoint> intersections = _scene.geometries.findGeoIntersections(lightRay);
        if (intersections == null) return true;
        double lightDistance = light.getDistance(geopoint._point);
        for (GeoPoint gp : intersections) {
            if (alignZero(gp._point.distance(geopoint._point) - lightDistance) <= 0 &&
                    gp._geometry.getMaterial().Kt == 0)
                return false;
        }
        return true;
    }


    /**
     * Method calcSpecular to calculate the specular light
     * @param ks             specular coefficient
     * @param l              vector from the light's position Pl to the intersection point
     * @param n              geometry's normal
     * @param v              ray's direction vector
     * @param nShininess     the object’s shininess
     * @param lightIntensity light source's intensity
     * @return an object o type Color for specular light
     */
    private Color calcSpecular(double ks, Vector l, Vector n, Vector v, int nShininess, Color lightIntensity) {
        Vector r = l.subtract(n.scale(l.dotProduct(n) * 2));
        double vrMinus = v.scale(-1).dotProduct(r);
        double vrn = Math.pow(Math.max(0,vrMinus),nShininess);
        return lightIntensity.scale(ks * vrn);

    }


    /**
     * Method calcDiffusive to calculate the diffusion light
     * @param kd             diffuse coefficient
     * @param l              vector from the light's position Pl to the intersection point
     * @param n              geometry's normal
     * @param lightIntensity light source's intensity
     * @return an object of type Color for diffusion light
     */
    private Color calcDiffusive(double kd, Vector l, Vector n, Color lightIntensity) {
        double ln = Math.abs(l.dotProduct(n));
        return lightIntensity.scale(kd * ln);
    }



}
