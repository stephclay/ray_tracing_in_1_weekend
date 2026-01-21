package com.wombatsw.raytracing.texture;

import com.wombatsw.raytracing.model.Triplet;

public interface Texture {
    /**
     * Get the color value at a specific point on the texture map
     *
     * @param u The horizontal coordinate
     * @param v The vertical coordinate
     * @param p The location of the intersection
     * @return The color at that point
     */
    Triplet value(double u, double v, Triplet p);
}
