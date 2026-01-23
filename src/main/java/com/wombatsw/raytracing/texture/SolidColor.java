package com.wombatsw.raytracing.texture;

import com.wombatsw.raytracing.model.Triplet;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
public class SolidColor extends Texture {
    private final Triplet color;

    public SolidColor(final Triplet color) {
        this.color = color;
    }

    @Override
    public Triplet value(final double u, final double v, final Triplet p) {
        return color;
    }
}
