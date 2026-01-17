package com.wombatsw.raytracing.texture;

import com.wombatsw.raytracing.model.Color;
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
    public Color value(double u, double v, Vector3 p) {
        double colorScale = 0.5 * (1.0 + perlin.noise(p, scale));
        return Color.white().mul(colorScale);
    }
}
