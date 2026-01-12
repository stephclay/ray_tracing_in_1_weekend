package com.wombatsw.raytracing.material;

import com.wombatsw.raytracing.model.Intersection;
import com.wombatsw.raytracing.model.ScatterData;

/**
 * Base class for materials
 */
public abstract class Material {
    /**
     * Scatter the inbound ray
     *
     * @param intersection The intersection data
     * @return The {@link ScatterData} or {@code null} if the ray was absorbed
     */
    public abstract ScatterData scatter(final Intersection intersection);
}
