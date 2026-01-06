package com.wombatsw.raytracing.material;

import com.wombatsw.raytracing.model.Color;
import com.wombatsw.raytracing.model.Intersection;
import com.wombatsw.raytracing.model.Ray;
import com.wombatsw.raytracing.model.ScatterData;
import com.wombatsw.raytracing.model.Vector3;

/**
 * A Lambertian (diffuse) material
 */
public class Lambertian extends Material {
    private final Color albedo;

    public Lambertian(final Color albedo) {
        this.albedo = albedo;
    }

    @Override
    public ScatterData scatter(final Intersection intersection) {
        Vector3 scatterDir = intersection.getN().add(Vector3.randomUnitVector());

        Ray scattered = new Ray(intersection.getP(), scatterDir.nearZero() ? intersection.getN() : scatterDir);
        return new ScatterData(scattered, albedo);
    }
}
