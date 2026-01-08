package com.wombatsw.raytracing.material;

import com.wombatsw.raytracing.engine.MathUtils;
import com.wombatsw.raytracing.model.Color;
import com.wombatsw.raytracing.model.Intersection;
import com.wombatsw.raytracing.model.Ray;
import com.wombatsw.raytracing.model.ScatterData;
import com.wombatsw.raytracing.model.Vector3;

/**
 * A dielectric (transparent) material
 */
public class Dielectric extends Material {
    private final double refractionIndex;

    /**
     * Create a dielectric (transparent) material
     *
     * @param refractionIndex The refractive index in vacuum, or the ratio of the refractive index over the
     *                        refractive index of the enclosing media
     */
    public Dielectric(final double refractionIndex) {
        this.refractionIndex = refractionIndex;
    }

    @Override
    public ScatterData scatter(final Intersection intersection) {
        double ri = intersection.isFrontFace() ? 1.0 / refractionIndex : refractionIndex;

        Vector3 unitDir = intersection.getRay().direction().copy().normalize();
        double cosTheta = Math.min(1.0, -unitDir.dot(intersection.getN()));
        double sinTheta = Math.sqrt(1.0 - cosTheta * cosTheta);
        boolean canRefract = ri * sinTheta <= 1.0;

        Vector3 rayDir;
        if (canRefract && reflectance(cosTheta, ri) < MathUtils.randomDouble()) {
            rayDir = unitDir.refract(intersection.getN(), ri);
        } else {
            rayDir = unitDir.reflect(intersection.getN());
        }
//        if (ri * sinTheta > 1.0) {
//            rayDir = unitDir.reflect(intersection.getN());
//        } else {
//            rayDir = unitDir.refract(intersection.getN(), ri);
//        }

        Ray scattered = new Ray(intersection.getP(), rayDir);
        return new ScatterData(scattered, Color.white());
    }

    /**
     * Calculate the reflectance using the Schlick Approximation
     *
     * @param cosTheta The angle from the normal
     * @param refractionIndex The refraction index
     * @return The reflectance
     */
    private double reflectance(final double cosTheta, double refractionIndex) {
        // Use Schlick's approximation for reflectance.
        double r = (1 - refractionIndex) / (1 + refractionIndex);
        double rSq = r * r;
        return rSq + (1 - rSq) * Math.pow((1 - cosTheta), 5);
    }
}
