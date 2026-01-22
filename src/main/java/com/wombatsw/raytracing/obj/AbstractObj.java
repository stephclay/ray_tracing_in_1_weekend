package com.wombatsw.raytracing.obj;

import com.wombatsw.raytracing.material.Material;
import com.wombatsw.raytracing.model.BoundingBox;
import com.wombatsw.raytracing.model.Intersection;
import com.wombatsw.raytracing.model.Interval;
import com.wombatsw.raytracing.model.Ray;
import lombok.Getter;

/**
 * Base class for all scene objects
 */
public abstract class AbstractObj {
    @Getter
    private final Material material;

    AbstractObj(final Material material) {
        this.material = material;
    }

    /**
     * Check if the given ray intersects this object
     *
     * @param ray    The ray to check
     * @param tRange The interval on the ray that is allowed for intersections
     * @return The {@link Intersection} data or {@code null} if no intersection exists, or it was outside the interval
     */
    public abstract Intersection intersect(final Ray ray, final Interval tRange);

    /**
     * Get the bounding box for this object
     *
     * @return The bounding box for this object
     */
    public abstract BoundingBox getBoundingBox();
}
