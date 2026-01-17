package com.wombatsw.raytracing.texture;

import com.wombatsw.raytracing.model.Vector3;

public class SolidColor implements Texture {
    private Vector3 color;

    public SolidColor(final Vector3 color) {
        this.color = color;
    }

    SolidColor(final double red, final double green, final double blue) {
        this(new Vector3(red, green, blue));
    }

    @Override
    public Vector3 value(final double u, final double v, final Vector3 p) {
        return color;
    }
}
