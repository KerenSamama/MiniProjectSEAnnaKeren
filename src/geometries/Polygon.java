package geometries;

import java.util.List;

import primitives.*;

import static primitives.Util.*;

/**
 * Polygon class represents two-dimensional polygon in 3D Cartesian coordinate
 * system
 *
 * @author Dan
 */
public class Polygon extends Geometry {
    /**
     * List of polygon's vertices
     */
    protected List<Point3D> _vertices;
    /**
     * Associated plane in which the polygon lays
     */
    protected Plane _plane;

    /**
     * Polygon constructor based on vertices list. The list must be ordered by edge
     * path. The polygon must be convex.
     *
     * @param vertices list of vertices according to their order by edge path
     * @throws IllegalArgumentException in any case of illegal combination of
     *                                  vertices:
     *                                  <ul>
     *                                  <li>Less than 3 vertices</li>
     *                                  <li>Consequent vertices are in the same
     *                                  point
     *                                  <li>The vertices are not in the same
     *                                  plane</li>
     *                                  <li>The order of vertices is not according
     *                                  to edge path</li>
     *                                  <li>Three consequent vertices lay in the
     *                                  same line (180&#176; angle between two
     *                                  consequent edges)
     *                                  <li>The polygon is concave (not convex)</li>
     *                                  </ul>
     */
    public Polygon(Point3D... vertices) {
        if (vertices.length < 3)
            throw new IllegalArgumentException("A polygon can't have less than 3 vertices");
        _vertices = List.of(vertices);
        // Generate the plane according to the first three vertices and associate the
        // polygon with this plane.
        // The plane holds the invariant normal (orthogonal unit) vector to the polygon
        _plane = new Plane(vertices[0], vertices[1], vertices[2]);
        if (vertices.length == 3)
            return; // no need for more tests for a Triangle

        Vector n = _plane.getNormal(null);
        // Vector n = _plane.getNormal();

        // Subtracting any subsequent points will throw an IllegalArgumentException
        // because of Zero Vector if they are in the same point
        Vector edge1 = vertices[vertices.length - 1].subtract(vertices[vertices.length - 2]);
        Vector edge2 = vertices[0].subtract(vertices[vertices.length - 1]);

        // Cross Product of any subsequent edges will throw an IllegalArgumentException
        // because of Zero Vector if they connect three vertices that lay in the same
        // line.
        // Generate the direction of the polygon according to the angle between last and
        // first edge being less than 180 deg. It is hold by the sign of its dot product
        // with
        // the normal. If all the rest consequent edges will generate the same sign -
        // the
        // polygon is convex ("kamur" in Hebrew).
        boolean positive = edge1.crossProduct(edge2).dotProduct(n) > 0;
        for (int i = 1; i < vertices.length; ++i) {
            // Test that the point is in the same plane as calculated originally
            if (!isZero(vertices[i].subtract(vertices[0]).dotProduct(n)))
                throw new IllegalArgumentException("All vertices of a polygon must lay in the same plane");
            // Test the consequent edges have
            edge1 = edge2;
            edge2 = vertices[i].subtract(vertices[i - 1]);
            if (positive != (edge1.crossProduct(edge2).dotProduct(n) > 0))
                throw new IllegalArgumentException("All vertices must be ordered and the polygon must be convex");

            if (_setBoundingBoxes == true) {
                //starting points
                box._minX = this._vertices.get(0).getX();
                box._maxX = this._vertices.get(0).getX();
                box._minY = this._vertices.get(0).getY();
                box._maxY = this._vertices.get(0).getY();
                box._minZ = this._vertices.get(0).getZ();
                box._maxZ = this._vertices.get(0).getZ();

                for (int j = 1; j < this._vertices.size(); j++) {
                    if (this._vertices.get(j).getX() < box._minX)//checks for min x
                        box._minX = this._vertices.get(j).getX();
                    if (this._vertices.get(j).getY() < box._minY)//checks for min y
                        box._minY = this._vertices.get(j).getY();
                    if (this._vertices.get(j).getZ() < box._minZ)//checks for min z
                        box._minZ = this._vertices.get(j).getZ();
                    if (this._vertices.get(j).getX() > box._maxX)//checks for max x
                        box._maxX = this._vertices.get(j).getX();
                    if (this._vertices.get(j).getY() > box._maxY)//checks for max y
                        box._maxY = this._vertices.get(j).getY();
                    if (this._vertices.get(j).getZ() > box._maxZ)//checks for max z
                        box._maxZ = this._vertices.get(j).getZ();
                }
            }
        }
    }

    /**
     * @return vertices
     */
    public List<Point3D> getVertices() {
        return _vertices;
    }

    /**
     * @return plane
     */
    public Plane getPlane() {
        return _plane;
    }

    @Override
    public String toString() {
        return "Polygon{" +
                "vertices=" + _vertices +
                ", plane=" + _plane +
                '}';
    }

    @Override
    public Vector getNormal(Point3D point) {
        return _plane.getNormal(null);
    }


    /**
     * This function helps to find the intersection between a ray and a polygon.
     * Fist, we check intersection between the ray and the plane. If there is an intersection point,
     * we must update the geometry field from the GeoPoint originally received from the plane
     * to the value "this" related to the polygon
     *
     * @param ray of type Ray
     * @return a list of intersection points of type GeoPoint or null if there are no intersections
     */
    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {
        List<GeoPoint> intersections = _plane.findGeoIntersections(ray, maxDistance);
        if (intersections == null) {
            return null;
        }

        Point3D p0 = ray.getP0(); // head of the ray
        Vector v = ray.getDir(); // directional vector of the ray

        Vector v1 = _vertices.get(1).subtract(p0);
        Vector v2 = _vertices.get(0).subtract(p0);
        double sign = alignZero(v.dotProduct(v1.crossProduct(v2)));
        if (isZero(sign)) {
            return null;
        }

        boolean positive = sign > 0;

        for (int i = _vertices.size() - 1; i > 0; --i) {
            v1 = v2;
            v2 = _vertices.get(i).subtract(p0);
            sign = alignZero(v.dotProduct(v1.crossProduct(v2)));
            if (isZero(sign)) { // if one or more  𝒗 ∙ 𝑵𝒊  are 0.0 – no intersection

                return null;
            }
            if (positive != (sign > 0)) { // The point is inside if all 𝒗 ∙ 𝑵𝒊 have the same sign
                return null;
            }
        }

        return List.of(new GeoPoint(this, intersections.get(0)._point));

    }


}
