package com.wombatsw.raytracing.texture;

import com.wombatsw.raytracing.model.Color;
import com.wombatsw.raytracing.model.Point3;

public class SolidColor implements Texture {
    private Color color;

    public SolidColor(final Color color) {
        this.color = color;
    }

    SolidColor(final double red, final double green, final double blue) {
        this(new Color(red, green, blue));
    }

    @Override
    public Color value(final double u, final double v, final Point3 p) {
        return color;
    }
}
