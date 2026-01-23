package com.wombatsw.raytracing.texture;

import com.wombatsw.raytracing.model.Triplet;
import lombok.Getter;
import lombok.ToString;

/**
 * Noise texture
 */
@ToString(callSuper = true)
public class NoiseTexture extends Texture {
    @Getter
    private final double scale;
    private final Perlin perlin;

    public NoiseTexture(final double scale) {
        this.scale = scale;
        perlin = new Perlin();
    }

    @Override
    public Triplet value(double u, double v, Triplet p) {
        double adj = (1 + Math.sin(scale * p.getZ() + 10 * perlin.turbulence(p, 7)));
        return new Triplet(.5, .5, .5).mul(adj);
    }
}
