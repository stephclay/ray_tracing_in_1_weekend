package com.wombatsw.raytracing.material;

import com.wombatsw.raytracing.model.Color;
import com.wombatsw.raytracing.model.Intersection;
import com.wombatsw.raytracing.model.Ray;
import com.wombatsw.raytracing.model.ScatterData;
import com.wombatsw.raytracing.model.Vector3;
import com.wombatsw.raytracing.texture.SolidColor;
import com.wombatsw.raytracing.texture.Texture;

/**
 * A Lambertian (diffuse) material
 */
public class Lambertian extends Material {
    private final Texture texture;

    public Lambertian(final Texture texture) {
        this.texture = texture;
    }

    public Lambertian(final Color albedo) {
        this(new SolidColor(albedo.copy().setImmutable()));
    }

    @Override
    public ScatterData scatter(final Intersection intersection) {
        Vector3 scatterDir = Vector3.randomUnitVector().add(intersection.getN());

        Vector3 rayDir = scatterDir.nearZero() ? intersection.getN() : scatterDir;
        Ray scattered = new Ray(intersection.getP(), rayDir, intersection.getRay().time());
        Color attenuation = texture.value(intersection.getU(), intersection.getV(), intersection.getP());
        return new ScatterData(scattered, attenuation);
    }
}
