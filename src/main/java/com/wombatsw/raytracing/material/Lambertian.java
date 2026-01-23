package com.wombatsw.raytracing.material;

import com.wombatsw.raytracing.model.Intersection;
import com.wombatsw.raytracing.model.Ray;
import com.wombatsw.raytracing.model.ScatterData;
import com.wombatsw.raytracing.model.Triplet;
import com.wombatsw.raytracing.texture.SolidColor;
import com.wombatsw.raytracing.texture.Texture;
import lombok.Getter;
import lombok.ToString;

/**
 * A Lambertian (diffuse) material
 */
@Getter
@ToString(callSuper = true)
public class Lambertian extends Material {
    private final Texture texture;

    public Lambertian(final Texture texture) {
        this.texture = texture;
    }

    public Lambertian(final Triplet albedo) {
        this(new SolidColor(albedo.copy().setImmutable()));
    }

    @Override
    public ScatterData scatter(final Intersection intersection) {
        Triplet scatterDir = Triplet.randomUnitVector().add(intersection.getN());

        Triplet rayDir = scatterDir.nearZero() ? intersection.getN() : scatterDir;
        Ray scattered = new Ray(intersection.getP(), rayDir, intersection.getRay().time());
        Triplet attenuation = texture.value(intersection.getU(), intersection.getV(), intersection.getP());
        return new ScatterData(scattered, attenuation);
    }
}
