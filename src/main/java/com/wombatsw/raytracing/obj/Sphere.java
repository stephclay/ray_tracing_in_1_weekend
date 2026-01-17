package com.wombatsw.raytracing.obj;

import com.wombatsw.raytracing.engine.BoundingBox;
import com.wombatsw.raytracing.material.Material;
import com.wombatsw.raytracing.model.Intersection;
import com.wombatsw.raytracing.model.Interval;
import com.wombatsw.raytracing.model.Ray;
import com.wombatsw.raytracing.model.Vector3;
import org.jspecify.annotations.NonNull;

/**
 * A sphere
 */
public class Sphere extends AbstractObj {
    private final Ray centerPath;
    private final double radius;
    private final BoundingBox boundingBox;

    /**
     * Stationary sphere
     *
     * @param center   Center of sphere
     * @param radius   Radius of sphere
     * @param material Material sphere is made of
     */
    public Sphere(final Vector3 center, final double radius, final Material material) {
        super(material);
        this.centerPath = new Ray(center, Vector3.newZeroVector());
        this.radius = Math.max(0, radius);

        Vector3 rVec = new Vector3(radius, radius, radius);
        boundingBox = new BoundingBox(center.copy().sub(rVec), center.copy().add(rVec));
    }

    /**
     * Moving sphere
     *
     * @param center1  Center of sphere at t = 0
     * @param center2  Center of sphere at t = 1
     * @param radius   Radius of sphere
     * @param material Material sphere is made of
     */
    public Sphere(final Vector3 center1, final Vector3 center2, final double radius, final Material material) {
        super(material);
        this.centerPath = new Ray(center1, new Vector3(center2, center1));
        this.radius = Math.max(0, radius);

        Vector3 rVec = new Vector3(radius, radius, radius);
        BoundingBox bbox1 = new BoundingBox(center1.copy().sub(rVec), center1.copy().add(rVec));
        BoundingBox bbox2 = new BoundingBox(center2.copy().sub(rVec), center2.copy().add(rVec));
        this.boundingBox = new BoundingBox(bbox1, bbox2);
    }

    @Override
    public Intersection intersect(final Ray ray, final Interval tRange) {
        Vector3 curCenter = centerPath.at(ray.time());
        Vector3 oc = curCenter.copy().sub(ray.origin());
        double a = ray.direction().lenSquared();
        double h = ray.direction().dot(oc);
        double c = oc.lenSquared() - radius * radius;
        double discriminant = h * h - a * c;
        if (discriminant < 0) {
            return null;
        }

        double sqrtD = Math.sqrt(discriminant);

        // Note: Root 1 is smaller than Root 2, which makes it closer
        // to the ray's origin when tRange.min() is non-negative.
        double root1 = (h - sqrtD) / a;
        if (tRange.surrounds(root1)) {
            return getIntersection(curCenter, ray, root1);
        }

        double root2 = (h + sqrtD) / a;
        if (tRange.surrounds(root2)) {
            return getIntersection(curCenter, ray, root2);
        }

        return null;
    }

    @Override
    public BoundingBox getBoundingBox() {
        return boundingBox;
    }

    /**
     * Get the intersection for a given t value
     *
     * @param curCenter The current center of the sphere
     * @param ray       The ray
     * @param t         the location on the ray
     * @return The {@link Intersection}
     */
    private @NonNull Intersection getIntersection(final Vector3 curCenter, final Ray ray, final double t) {
        Vector3 p = ray.at(t);
        Vector3 n = new Vector3(p, curCenter).div(radius);

        return new Intersection(ray, t, p, n, getU(n), getV(n), getMaterial());
    }

    /**
     * Get the phi coordinate of the provided unit vector
     *
     * @param v The unit vector
     * @return the PHI coordinate normalized to the range 0-1
     */
    private double getU(final Vector3 v) {
        double phi = Math.atan2(-v.getZ(), v.getX()) + Math.PI;
        return phi / (2.0 * Math.PI);
    }

    /**
     * Get the theta coordinate of the provided unit vector
     *
     * @param v The unit vector
     * @return the theta coordinate normalized to the range 0-1
     */
    private double getV(final Vector3 v) {
        double theta = Math.acos(-v.getY());
        return theta / Math.PI;
    }
}
