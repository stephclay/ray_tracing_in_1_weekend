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

    public Metal(final Color albedo) {
        this.albedo = albedo;
    }

    @Override
    public ScatterData scatter(final Intersection intersection) {
        Vector3 rayDir = intersection.getRay().direction();
        Vector3 reflected = rayDir.reflect(intersection.getN());

        Ray scattered = new Ray(intersection.getP(), reflected);
        return new ScatterData(scattered, albedo);
    }
}
