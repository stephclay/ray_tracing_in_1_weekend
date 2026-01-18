package com.wombatsw.raytracing.material;

import com.wombatsw.raytracing.model.Vector3;
import com.wombatsw.raytracing.texture.SolidColor;
import com.wombatsw.raytracing.texture.Texture;

/**
 * A diffuse light material
 */
public class DiffuseLight extends Material {
    private final Texture texture;

    public DiffuseLight(final Texture texture) {
        this.texture = texture;
    }

    public DiffuseLight(final Vector3 color) {
        this(new SolidColor(color));
    }

    @Override
    public Vector3 emitted(double u, double v, Vector3 p) {
        return texture.value(u, v, p);
    }
}
