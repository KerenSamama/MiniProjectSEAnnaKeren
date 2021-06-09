package renderer;

import static geometries.Intersectable.GeoPoint;
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
    private static final double INITIAL_K = 1.0;
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final double MIN_CALC_COLOR_K = 0.001;

    /**
     * parameters for ray tracing- glossy surface and diffuse glass - they are in class BasicRayTracer
     * because this class takes care of ray tracing
     */
    private int _numOfRays;
    private double _radius;
    private double _rayDistance;
    /**


    /**
     * Constructor for BasicRayTracer receiving a scene object and activating the father constructor
     *
     * @param scene
     */
    public BasicRayTracer(Scene scene) {
        super(scene);
    }




    /**
     * Gets the distance we want between the ray point and the circle
     * @return double - distance
     */
    public double get_rayDistance() {
        return _rayDistance;
    }

    /**
     * sets the distance between the ray point and the circle
     * @param _rayDistance
     */
    public void set_rayDistance(double _rayDistance) {
        if (_rayDistance < 0)
            throw new IllegalArgumentException("Distance cannot be negative");
        this._rayDistance = _rayDistance;
    }

    /**
     * get number of rays function
     * @return number of rays that will be part of the beam
     */
    public int get_numOfRays() {
        return _numOfRays;
    }

    /**
     * sets the number of rays that will be part of the beam
     * @param _numOfRays int - amount of rays that will be part of the beam
     */
    public void set_numOfRays(int _numOfRays) {
        if (_numOfRays < 1)
            throw new IllegalArgumentException("There has to be at least one ray");
        this._numOfRays = _numOfRays;
    }

    /**
     * returns the size of the radius
     * @return double - Radius
     */
    public double getRadius() {
        return _radius;
    }

    /**
     * sets the value or the radius for the circle to create a beam
     * @param radius double - the radius of the circle we are using to create a beam
     */
    public void setRadius(double radius) {
        if (radius < 0)
            throw new IllegalArgumentException("radius can't be negative");
        this._radius = radius;
    }



    /**
     * Function traceRay finds the intersection points between a ray and the 3D model. If there are, calculates the closest point to the
     * start of the ray and returns the color on this point. If there are no intersections, returns the background color of the scene.
     *
     * @param ray
     * @return Color
     */
    @Override
    public Color traceRay(Ray ray) {
        List<GeoPoint> intersections = _scene.geometries.findGeoIntersections(ray);
        if (intersections != null) {
            GeoPoint closestPoint = ray.findClosestGeoPoint(intersections);
            return calcColor(closestPoint, ray);
        }
        return _scene.backGroundColor;
       // return _scene.backGroundColor;
    }


    /**
     private Color calcColor(GeoPoint intersection, Ray ray) {
     Color emissionColor = intersection.geometry.getEmission();
     Color basicColor = emissionColor.add(_scene.ambientLight.getIntensity());
     return basicColor.add(calcLocalEffects(intersection, ray));
     }
     **/

    /**
     * Calculate a color on a point. For that, this method adds the intensity of the ambientLight,
     * the object's color and calcLocalEffects according tp the Phong Reflectance Model
     *
     * @param closestPoint a GeoPoint
     * @param ray          of type Ray
     * @return the point's color of type Color
     */

    private Color calcColor(GeoPoint closestPoint, Ray ray) {
        return calcColor(closestPoint, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K)
                .add(_scene.ambientLight.getIntensity());
    }

    /**
     * Calculate a color on a point. For that, this method adds the intensity of the ambientLight,
     * the object's color and calcLocalEffects according tp the Phong Reflectance Model
     * RECURSIVE because global effect call calcolor
     *
     * @param intersection
     * @param ray
     * @param level
     * @param k
     * @return
     */
    private Color calcColor(GeoPoint intersection, Ray ray, int level, double k) {
        Color color = intersection._geometry.getEmission();
        color = color.add(calcLocalEffects(intersection, ray, k));
        return 1 == level ? color : color.add(calcGlobalEffects(intersection, ray.getDir(), level, k));

    }

    /**
     * @param geoPoint
     * @param v
     * @param level
     * @param k
     * @return
     */
    private Color calcGlobalEffects(GeoPoint geoPoint, Vector v, int level, double k) { // v= direction ray
        Color color = Color.BLACK;
        Color colorReflection = Color.BLACK;
        Vector n = geoPoint._geometry.getNormal(geoPoint._point);
        Material material = geoPoint._geometry.getMaterial();
        double kkr = k * material.Kr;
        List<Ray>beam=new LinkedList<>();

        if (kkr > MIN_CALC_COLOR_K) {
            Ray reflectedRay = constructReflectedRay(geoPoint, v);
            if(_numOfRays==0 || _rayDistance<0){
                beam.add(reflectedRay);
            }
            else{
                beam= reflectedRay.createBeamOfRays(geoPoint._geometry.getNormal(geoPoint._point),this.get_rayDistance(),this._numOfRays);
            }
            for(Ray r : beam) // r = reflectedRay
            {
                colorReflection = colorReflection.add(calcGlobalEffects(r, level, material.Kr, kkr));
            }

            color = color.add(colorReflection.reduce(beam.size()));

        }

        double kkt = k * material.Kt;
        if (kkt > MIN_CALC_COLOR_K) {
            color = color.add(calcGlobalEffects(constructRefractedRay(geoPoint, v), level, material.Kt, kkt));

        }
        return color;
    }

    private Color calcGlobalEffects(Ray ray, int level, double kx, double kkx) {
        GeoPoint gp = ray.findClosestGeoPoint(_scene.geometries.findGeoIntersections(ray));
        return (gp == null ? Color.BLACK.add(_scene.backGroundColor).scale(kx) : calcColor(gp, ray, level - 1, kkx)).scale(kkx);
    }



    /**
     * Method constructRefractedRay for calculate the RefractedRay
     *
     * @param geoPoint
     * @param v
     * @return
     */
    private Ray constructRefractedRay(GeoPoint geoPoint, Vector v) {
        Vector n = geoPoint._geometry.getNormal(geoPoint._point);
        return new Ray(geoPoint._point, v, n);

    }

    /**
     * Method constructReflectedRay for calculate the reflectedRay
     *
     * @param geoPoint
     * @param v
     * @return
     */
    private Ray constructReflectedRay(GeoPoint geoPoint, Vector v) {
        Vector n = geoPoint._geometry.getNormal(geoPoint._point);
        Vector r = v.subtract(n.scale(2 * v.dotProduct(n))).normalize();
        return new Ray(geoPoint._point, r, n);
    }

    // ray : rayon depuis camera vers le point GeoPoint intersection
    /**
     * Method calcLocalEffects for adding diffusion/specular calculation
     *
     * @param intersection
     * @param ray
     * @return
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
     * @param light
     * @param l
     * @param n
     * @param geopoint
     * @return
     */

    /*private boolean unshaded(LightSource lightSource, GeoPoint geoPoint) {
        Point3D point = geoPoint._point;
        Vector n = geoPoint._geometry.getNormal(point);
        //Ray from point toward light direction offset by delta
        Ray lightRay = new Ray(point, lightSource, n, DELTA);
       //
        List<GeoPoint> intersection = _scene.geometries
                .findGeoIntersections(lightRay, lightSource.getDistance(point));
        return intersection == null;
    }*/
    // @Deprecated
    private boolean unshaded(LightSource light, Vector l, Vector n, GeoPoint geopoint) {
        Vector lightDirection = l.scale(-1); // from point to light source
        Ray lightRay = new Ray(geopoint._point, lightDirection, n); // refactored ray head move
        List<GeoPoint> intersections = _scene.geometries.findGeoIntersections(lightRay, light.getDistance(geopoint._point));
        if (intersections == null) return true;
        double lightDistance = light.getDistance(geopoint._point);
        for (GeoPoint gp : intersections) {
            if (alignZero(gp._point.distance(geopoint._point) - lightDistance) <= 0 &&
                    gp._geometry.getMaterial().Kt == 0)
                return false;
        }
        return true;
    }
//
    private double transparency(LightSource light, Vector l, Vector n, GeoPoint geopoint) {
        Vector lightDirection = l.scale(-1); // from point to light source
        Point3D point = geopoint._point;
        Ray lightRay = new Ray(point, lightDirection, n);
        double lightDistance = light.getDistance(point);
        var intersections = _scene.geometries.findGeoIntersections(lightRay, lightDistance);
        if (intersections == null) return 1.0;
        double ktr = 1.0;
        for (GeoPoint gp : intersections) {

            ktr *= gp._geometry.getMaterial().getKt();
            if (ktr < MIN_CALC_COLOR_K) return 0.0;
        }
        return ktr;
    }


    /**
     * Method calcSpecular to calculate the specular light
     *
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
     *
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

    /**
     *  private primitives.Color calcColor(Intersectable.GeoPoint gp,Ray in,int level,double k) {
     *         if (level == 1 || k < MIN_CALC_COLOR_K)
     *             return primitives.Color.BLACK;
     *         primitives.Color color = gp.getGeometry().get_emission();//the geometries emssion light
     *         Vector v = gp.point.subtract(_scene.getCamera().get_p0()).normalize();//subtracts the camera starting point from the geopoint and normalizes the vector
     *
     *         double nDotV =alignZero(  gp.geometry.getNormal(gp.getPoint()).dotProduct(v));//n dot product v
     *         if (nDotV == 0) {
     *             return color;
     *         }
     *         int nShininess = gp.geometry.get_material().getnShininess();//the geopoint's shininess
     *         double kd = gp.geometry.get_material().getKd();//geopoint diffuse
     *         double ks = gp.geometry.get_material().getKs();//geopoint specular
     *         double kr = k * gp.getGeometry().get_material().get_kR();//reflection
     *         double kt = k * gp.getGeometry().get_material().get_kT();//refraction
     *         double transparencyAmount=0;//transparency
     *         for (LightSource lightSource : _scene.getLightSources())//for each light source in the scene's light sources
     *         {
     *             Vector l = lightSource.getL(gp.point);//the lights direction from geopoint
     *             if (alignZero( gp.geometry.getNormal(gp.getPoint()).dotProduct(l)) * alignZero( gp.geometry.getNormal(gp.getPoint()).dotProduct(v)) > 0)//if the dot proudct between the normal and the light direction times the dot product btween the normal and the normal vector between the camera and geopoint
     *             {
     *                 //   if (unshaded(lightSource, l, n, gp))//if the geopoint isnt shaded by the light
     *                 transparencyAmount = transparency(lightSource, l,  gp.geometry.getNormal(gp.getPoint()), gp);
     *                 if (transparencyAmount * k > MIN_CALC_COLOR_K) {
     *                     primitives.Color lightIntensity = lightSource.getIntensity(gp.point).scale(transparencyAmount);//intensity color of the geopoint
     *                     color = color.add(calcDiffusive(kd, l.dotProduct( gp.geometry.getNormal(gp.getPoint())), lightIntensity),
     *                             calcSpecular(ks, l,  gp.geometry.getNormal(gp.getPoint()), l.dotProduct( gp.geometry.getNormal(gp.getPoint())), v, nShininess, lightIntensity))
     *                     ;//adds the specular and diffuse lights to the color
     *                 }
     *             }
     *         }
     *
     *         if (kr > MIN_CALC_COLOR_K)//if the reflection is bigger than the minimum of calc color
     *         {
     *             List<Ray>beam=new LinkedList<>();
     *             if(this._numOfRays==0 ||this._radius<0||this._rayDistance<0)
     *                 beam.add(in);
     *             else
     *                 beam=  in.createBeamOfRays(gp.getGeometry().getNormal(gp.getPoint()),this.getRadius(),this._scene.getDistance(),this.get_numOfRays());
     *           for(Ray r :beam)
     *           {
     *               Ray reflection= constructReflectedRay(gp.getGeometry().getNormal(gp.getPoint()), gp.getPoint(), r);
     *               Intersectable.GeoPoint reflectedGp = findClosestIntersection(reflection);//find the closest point to the reflection ray's p0
     *               if (reflectedGp != null)//if such a point exists
     *               {
     *                   color = color.add(calcColor(reflectedGp, reflection, level - 1, kr).scale(kr));//calls the recursion th find the rest of the color and then scales it with the reflection
     *               }
     *           }
     *
     *
     *         }
     *
     *         if (kt > MIN_CALC_COLOR_K)//if the refraction is bigger than the minimum of calc color
     *         {
     *             List<Ray>beam=new LinkedList<>();
     *             if(this._numOfRays==0 ||this._radius<0||this._rayDistance<0)
     *                 beam.add(in);
     *             else
     *                 beam=  in.createBeamOfRays(gp.getGeometry().getNormal(gp.getPoint()),this.getRadius(),this._scene.getDistance(),this.get_numOfRays());
     *
     *             for(Ray r :beam) {
     *                 Ray refraction = constructRefractedRay(gp.getPoint(), r, gp.getGeometry().getNormal(gp.getPoint()));//constructs a refracted ray
     *                 Intersectable.GeoPoint refractedGp = findClosestIntersection(refraction);//find the closest point to the refracted ray's p0
     *                 if (refractedGp != null)//if such a point exists
     *                 {
     *                     color = color.add(calcColor(refractedGp, refraction, level - 1, kt).scale(kt));//calls the recursion th find the rest of the color and then scales it with the refracted
     *                 }
     *             }
     *         }
     *         return color;
     *     }
     */

}
