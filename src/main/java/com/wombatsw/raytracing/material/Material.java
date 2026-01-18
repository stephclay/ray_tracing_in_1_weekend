package com.wombatsw.raytracing.material;

import com.wombatsw.raytracing.engine.ColorUtils;
import com.wombatsw.raytracing.model.Intersection;
import com.wombatsw.raytracing.model.ScatterData;
import com.wombatsw.raytracing.model.Vector3;

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
    public ScatterData scatter(final Intersection intersection) {
        return null;
    }

    /**
     * Get the color is emitted by this surface. Returns black for none.
     *
     * @param u The horizontal coordinate
     * @param v The vertical coordinate
     * @param p The location of the intersection
     * @return The emitted light color, or black for none
     */
    public Vector3 emitted(final double u, final double v, final Vector3 p) {
        return ColorUtils.black();
    }
}
