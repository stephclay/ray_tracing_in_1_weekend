package com.wombatsw.raytracing.model;

import com.wombatsw.raytracing.material.Material;

import java.util.function.Function;

/**
 * Data representing a ray intersection
 */
public class Intersection {
    private final Ray ray;
    private final Point3 p;
    private final Vector3 n;
    private final Material material;
    private final double t;
    private final boolean frontFace;

    /**
     * Intersection constructor
     *
     * @param ray             The intersecting ray
     * @param t               The location of the intersection on the ray
     * @param material        The material at the intersection point
     * @param outwardNormalFn The normal for the outside face of the intersection point
     */
    public Intersection(final Ray ray, final double t, final Material material,
                        final Function<Point3, Vector3> outwardNormalFn) {
        this.ray = ray;
        p = ray.at(t);
        this.t = t;
        this.material = material;

        Vector3 outwardNormal = outwardNormalFn.apply(p);

        frontFace = ray.direction().dot(outwardNormal) < 0;
        n = frontFace ? outwardNormal : outwardNormal.negate();
    }

    /**
     * @return The inbound ray
     */
    public Ray getRay() {
        return ray;
    }

    /**
     * @return The intersection point
     */
    public Point3 getP() {
        return p;
    }

    /**
     * @return The intersection normal (can be pointing inwards)
     */
    public Vector3 getN() {
        return n;
    }

    /**
     * @return The location of the intersection on the intersecting ray
     */
    public double getT() {
        return t;
    }

    /**
     * @return The material
     */
    public Material getMaterial() {
        return material;
    }

    /**
     * @return Whether the intersection was from the inside our outside
     */
    public boolean isFrontFace() {
        return frontFace;
    }
}
