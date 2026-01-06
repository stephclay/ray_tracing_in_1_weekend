package com.wombatsw.raytracing.obj;

import com.wombatsw.raytracing.material.Material;
import com.wombatsw.raytracing.model.Intersection;
import com.wombatsw.raytracing.model.Interval;
import com.wombatsw.raytracing.model.Point3;
import com.wombatsw.raytracing.model.Ray;
import org.jspecify.annotations.NonNull;

/**
 * A sphere
 */
public class Sphere extends AbstractObj {
    private final Point3 center;
    private final double radius;

    public Sphere(final Point3 center, final double radius, final Material material) {
        super(material);
        this.center = center;
        this.radius = Math.max(0, radius);
    }

    @Override
    public Intersection intersect(final Ray ray, final Interval tRange) {
        Point3 oc = center.sub(ray.origin());
        double a = ray.direction().lenSquared();
        double h = ray.direction().dot(oc);
        double c = oc.lenSquared() - radius * radius;
        double discriminant = h * h - a * c;
        if (discriminant < 0) {
            return null;
        }

        double sqrtD = Math.sqrt(discriminant);

        // Note: T1 is smaller than T2, which makes it closer
        // tp the ray's origin when tMin is non-negative.
        double t1 = (h - sqrtD) / a;
        if (tRange.surrounds(t1)) {
            return getIntersection(ray, t1);
        }

        double t2 = (h + sqrtD) / a;
        if (tRange.surrounds(t2)) {
            return getIntersection(ray, t2);
        }

        return null;
    }

    /**
     * Get the intersection for a given t value
     *
     * @param ray The ray
     * @param t   the location on the ray
     * @return The {@link Intersection}
     */
    private @NonNull Intersection getIntersection(final Ray ray, final double t) {
        return new Intersection(ray, t, getMaterial(), p -> p.vectorFrom(center).div(radius));
    }
}
