package com.wombatsw.raytracing.texture;

import com.wombatsw.raytracing.model.Triplet;

public class SolidColor implements Texture {
    private final Triplet color;

    public SolidColor(final Triplet color) {
        this.color = color;
    }

    @Override
    public Triplet value(final double u, final double v, final Triplet p) {
        return color;
    }
}
