package com.wombatsw.raytracing.texture;

import com.wombatsw.raytracing.model.Triplet;

public class SolidColor implements Texture {
    private Triplet color;

    public SolidColor(final Triplet color) {
        this.color = color;
    }

    SolidColor(final double red, final double green, final double blue) {
        this(new Triplet(red, green, blue));
    }

    @Override
    public Triplet value(final double u, final double v, final Triplet p) {
        return color;
    }
}
