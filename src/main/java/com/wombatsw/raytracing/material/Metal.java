package com.wombatsw.raytracing.material;

import com.wombatsw.raytracing.model.Intersection;
import com.wombatsw.raytracing.model.Ray;
import com.wombatsw.raytracing.model.ScatterData;
import com.wombatsw.raytracing.model.Vector3;

/**
 * A metal material
 */
public class Metal extends Material {
    private final Vector3 albedo;
    private final double fuzz;

    public Metal(final Vector3 albedo, final double fuzz) {
        this.albedo = albedo;
        this.fuzz = Math.min(1, fuzz);
    }

    @Override
    public ScatterData scatter(final Intersection intersection) {
        Vector3 reflected = intersection.getRay().direction().copy()
                .reflect(intersection.getN())
                .normalize();
        if (fuzz > 0) {
            reflected.add(Vector3.randomUnitVector().mul(fuzz));
        }
        if (reflected.dot(intersection.getN()) < 0) {
            return null;
        }

        Ray scattered = new Ray(intersection.getP(), reflected, intersection.getRay().time());
        return new ScatterData(scattered, albedo);
    }
}
