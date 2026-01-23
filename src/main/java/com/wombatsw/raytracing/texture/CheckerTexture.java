package com.wombatsw.raytracing.texture;

import com.wombatsw.raytracing.model.Triplet;
import lombok.Getter;
import lombok.ToString;

/**
 * A checker texture based on world coordinates
 */
@ToString(callSuper = true)
public class CheckerTexture extends Texture {
    @Getter
    private final double scale;
    @Getter
    private final Texture even;
    @Getter
    private final Texture odd;

    private final double invScale;

    public CheckerTexture(final double scale, final Texture even, final Texture odd) {
        this.scale = scale;
        this.even = even;
        this.odd = odd;

        invScale = 1.0 / scale;
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
