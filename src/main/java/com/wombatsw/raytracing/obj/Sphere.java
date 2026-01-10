package com.wombatsw.raytracing.obj;

import com.wombatsw.raytracing.material.Material;
import com.wombatsw.raytracing.model.Intersection;
import com.wombatsw.raytracing.model.Interval;
import com.wombatsw.raytracing.model.Point3;
import com.wombatsw.raytracing.model.Ray;
import com.wombatsw.raytracing.model.Vector3;
import org.jspecify.annotations.NonNull;

/**
 * A sphere
 */
public class Sphere extends AbstractObj {
    private final Ray center;
    private final double radius;

    /**
     * Stationary sphere
     *
     * @param center   Center of sphere
     * @param radius   Radius of sphere
     * @param material Material sphere is made of
     */
    public Sphere(final Point3 center, final double radius, final Material material) {
        super(material);
        this.center = new Ray(center, Vector3.newZeroVector());
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
    public Sphere(final Point3 center1, final Point3 center2, final double radius, final Material material) {
        super(material);
        this.center = new Ray(center1, new Vector3(center2, center1));
        this.radius = Math.max(0, radius);
    }

    @Override
    public Intersection intersect(final Ray ray, final Interval tRange) {
        Point3 curCenter = center.at(ray.time());
        Point3 oc = curCenter.copy().sub(ray.origin());
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
    private @NonNull Intersection getIntersection(final Point3 curCenter, final Ray ray, final double t) {
        return new Intersection(ray, t, getMaterial(), p -> new Vector3(p, curCenter).div(radius));
    }
}
