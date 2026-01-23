package com.wombatsw.raytracing.material;

import com.wombatsw.raytracing.model.Triplet;
import com.wombatsw.raytracing.texture.SolidColor;
import com.wombatsw.raytracing.texture.Texture;
import lombok.Getter;
import lombok.ToString;

/**
 * A diffuse light material
 */
@Getter
@ToString(callSuper = true)
public class DiffuseLight extends Material {
    private final Texture texture;

    public DiffuseLight(final Texture texture) {
        this.texture = texture;
    }

    public DiffuseLight(final Triplet color) {
        this(new SolidColor(color));
    }

    @Override
    public Triplet emitted(double u, double v, Triplet p) {
        return texture.value(u, v, p);
    }
}
