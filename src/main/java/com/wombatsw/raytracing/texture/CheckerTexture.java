package com.wombatsw.raytracing.texture;

import com.wombatsw.raytracing.model.Triplet;

/**
 * A checker texture based on world coordinates
 */
public class CheckerTexture implements Texture {
    private final double invScale;
    private final Texture even;
    private final Texture odd;

    public CheckerTexture(final double scale, final Texture even, final Texture odd) {
        invScale = 1.0 / scale;
        this.even = even;
        this.odd = odd;
    }

    public CheckerTexture(final double scale, final Triplet even, final Triplet odd) {
        this(scale, new SolidColor(even), new SolidColor(odd));
    }

    @Override
    public Triplet value(final double u, final double v, final Triplet p) {
        int xInt = (int) Math.floor(invScale * p.getX());
        int yInt = (int) Math.floor(invScale * p.getY());
        int zInt = (int) Math.floor(invScale * p.getZ());
        boolean isEven = (xInt + yInt + zInt) % 2 == 0;

        return isEven ? even.value(u, v, p) : odd.value(u, v, p);
    }
}
