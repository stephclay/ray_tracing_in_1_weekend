package com.wombatsw.raytracing.obj;

import com.wombatsw.raytracing.material.Material;
import com.wombatsw.raytracing.model.BoundingBox;
import com.wombatsw.raytracing.model.Intersection;
import com.wombatsw.raytracing.model.Interval;
import com.wombatsw.raytracing.model.Ray;
import lombok.Getter;
import lombok.ToString;

/**
 * Base class for all scene objects
 */
@Getter
@ToString
public abstract class AbstractObj {
    private final Material material;
    private final BoundingBox boundingBox;

    AbstractObj(final Material material, final BoundingBox boundingBox) {
        this.material = material;
        this.boundingBox = boundingBox;
    }

    /**
     * Check if the given ray intersects this object
     *
     * @param ray    The ray to check
     * @param tRange The interval on the ray that is allowed for intersections
     * @return The {@link Intersection} data or {@code null} if no intersection exists, or it was outside the interval
     */
    public abstract Intersection intersect(final Ray ray, final Interval tRange);
}
