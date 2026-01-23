package com.wombatsw.raytracing.material;

import com.wombatsw.raytracing.model.Intersection;
import com.wombatsw.raytracing.model.Ray;
import com.wombatsw.raytracing.model.ScatterData;
import com.wombatsw.raytracing.model.Triplet;
import lombok.Getter;
import lombok.ToString;

/**
 * A metal material
 */
@Getter
@ToString(callSuper = true)
public class Metal extends Material {
    private final Triplet albedo;
    private final double fuzz;

    public Metal(final Triplet albedo, final double fuzz) {
        this.albedo = albedo;
        this.fuzz = Math.min(1, fuzz);
    }

    @Override
    public ScatterData scatter(final Intersection intersection) {
        Triplet reflected = intersection.getRay().direction().copy()
                .reflect(intersection.getN())
                .normalize();
        if (fuzz > 0) {
            reflected.add(Triplet.randomUnitVector().mul(fuzz));
        }
        if (reflected.dot(intersection.getN()) < 0) {
            return null;
        }

        Ray scattered = new Ray(intersection.getP(), reflected, intersection.getRay().time());
        return new ScatterData(scattered, albedo);
    }
}
