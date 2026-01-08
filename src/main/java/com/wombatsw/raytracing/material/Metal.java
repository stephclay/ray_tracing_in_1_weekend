package com.wombatsw.raytracing.material;

import com.wombatsw.raytracing.model.Color;
import com.wombatsw.raytracing.model.Intersection;
import com.wombatsw.raytracing.model.Ray;
import com.wombatsw.raytracing.model.ScatterData;
import com.wombatsw.raytracing.model.Vector3;

/**
 * A metal material
 */
public class Metal extends Material {
    private final Color albedo;
    private final double fuzz;

    public Metal(final Color albedo, final double fuzz) {
        this.albedo = albedo;
        this.fuzz = Math.min(1, fuzz);
    }

    @Override
    public ScatterData scatter(final Intersection intersection) {
        Vector3 rayDir = intersection.getRay().direction().copy();
        Vector3 reflected = rayDir.reflect(intersection.getN())
                .normalize();
        if (fuzz > 0) {
            reflected.add(Vector3.randomUnitVector().mul(fuzz));
        }
        if (reflected.dot(intersection.getN()) < 0) {
            return null;
        }

        Ray scattered = new Ray(intersection.getP(), reflected);
        return new ScatterData(scattered, albedo);
    }
}
