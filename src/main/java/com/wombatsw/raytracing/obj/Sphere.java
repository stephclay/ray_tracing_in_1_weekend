package com.wombatsw.raytracing.obj;

import com.wombatsw.raytracing.material.Material;
import com.wombatsw.raytracing.model.BoundingBox;
import com.wombatsw.raytracing.model.Intersection;
import com.wombatsw.raytracing.model.Interval;
import com.wombatsw.raytracing.model.Ray;
import com.wombatsw.raytracing.model.Triplet;
import lombok.Getter;
import lombok.ToString;
import org.jspecify.annotations.NonNull;

/**
 * A sphere
 */
@Getter
@ToString(callSuper = true)
public class Sphere extends AbstractObj {
    private final Ray centerPath;
    private final double radius;

    /**
     * Stationary sphere
     *
     * @param center   Center of sphere
     * @param radius   Radius of sphere
     * @param material Material sphere is made of
     */
    public Sphere(final Triplet center, final double radius, final Material material) {
        super(material, createBoundingBox(center, center, Math.max(0, radius)));

        this.centerPath = new Ray(center, Triplet.newZeroVector());
        this.radius = Math.max(0, radius);
    }

    /**
     * Moving sphere
     *
     * @param center1  Center of sphere at t = 0
     * @param center2  Center of sphere at t = 1
     * @param radius   Radius of sphere
     * @param material Material sphere is made of
     */
    public Sphere(final Triplet center1, final Triplet center2, final double radius, final Material material) {
        super(material, createBoundingBox(center1, center2, Math.max(0, radius)));

        // TODO: Extract movement into a separate class which would reference this one (similar to Transform)
        this.centerPath = new Ray(center1, new Triplet(center2, center1));
        this.radius = Math.max(0, radius);
    }

    @Override
    public Intersection intersect(final Ray ray, final Interval tRange) {
        Triplet curCenter = centerPath.at(ray.time());
        Triplet oc = curCenter.copy().sub(ray.origin());
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

    /**
     * Get the intersection for a given t value
     *
     * @param curCenter The current center of the sphere
     * @param ray       The ray
     * @param t         the location on the ray
     * @return The {@link Intersection}
     */
    private @NonNull Intersection getIntersection(final Triplet curCenter, final Ray ray, final double t) {
        Triplet p = ray.at(t);
        Triplet n = new Triplet(p, curCenter).div(radius);

        return new Intersection(ray, t, p, n, getU(n), getV(n), getMaterial());
    }

    /**
     * Get the phi coordinate of the provided unit vector
     *
     * @param v The unit vector
     * @return the PHI coordinate normalized to the range 0-1
     */
    private double getU(final Triplet v) {
        double phi = Math.atan2(-v.getZ(), v.getX()) + Math.PI;
        return phi / (2.0 * Math.PI);
    }

    /**
     * Get the theta coordinate of the provided unit vector
     *
     * @param v The unit vector
     * @return the theta coordinate normalized to the range 0-1
     */
    private double getV(final Triplet v) {
        double theta = Math.acos(-v.getY());
        return theta / Math.PI;
    }

    /**
     * Create the bounding box for this object
     *
     * @param center1 The center of the Sphere at t=0, or just center if not moving
     * @param center2 The center of the Sphere at t=1, or same as center1 if not moving
     * @param radius  The radius of the sphere
     * @return The {@link BoundingBox}
     */
    private static @NonNull BoundingBox createBoundingBox(Triplet center1, Triplet center2, double radius) {
        Triplet rVec = new Triplet(radius, radius, radius);
        BoundingBox bbox1 = new BoundingBox(center1.copy().sub(rVec), center1.copy().add(rVec));
        BoundingBox bbox2 = new BoundingBox(center2.copy().sub(rVec), center2.copy().add(rVec));
        return new BoundingBox(bbox1, bbox2);
    }
}
