package com.wombatsw.raytracing.texture;

import com.wombatsw.raytracing.model.Vector3;

/**
 * Noise texture
 */
public class NoiseTexture implements Texture {
    private final double scale;
    private final Perlin perlin;

    public NoiseTexture(final double scale) {
        this.scale = scale;
        perlin = new Perlin();
    }

    @Override
    public Vector3 value(double u, double v, Vector3 p) {
        double adj = (1 + Math.sin(scale * p.getZ() + 10 * perlin.turbulence(p, 7)));
        return new Vector3(.5, .5, .5).mul(adj);
    }
}
