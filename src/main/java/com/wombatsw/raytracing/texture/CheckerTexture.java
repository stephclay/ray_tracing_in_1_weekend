package com.wombatsw.raytracing.texture;

import com.wombatsw.raytracing.model.Color;
import com.wombatsw.raytracing.model.Vector3;

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

    public CheckerTexture(final double scale, final Color even, final Color odd) {
        this(scale, new SolidColor(even), new SolidColor(odd));
    }

    @Override
    public Color value(final double u, final double v, final Vector3 p) {
        int xInt = (int) Math.floor(invScale * p.getX());
        int yInt = (int) Math.floor(invScale * p.getY());
        int zInt = (int) Math.floor(invScale * p.getZ());
        boolean isEven = (xInt + yInt + zInt) % 2 == 0;

        return isEven ? even.value(u, v, p) : odd.value(u, v, p);
    }
}
