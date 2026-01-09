package com.wombatsw.raytracing.model;

import com.wombatsw.raytracing.material.Material;
import lombok.Getter;

import java.util.function.Function;

/**
 * Data representing a ray intersection
 */
@Getter
public class Intersection {
    /**
     * The inbound ray
     */
    private final Ray ray;
    /**
     * The intersection point
     */
    private final Point3 p;
    /**
     * The intersection normal unit vector (can be pointing inwards)
     */
    private final Vector3 n;
    /**
     * The material
     */
    private final Material material;
    /**
     * The location of the intersection on the intersecting ray
     */
    private final double t;
    /**
     * Whether the intersection was from the inside our outside
     */
    private final boolean frontFace;

    /**
     * Intersection constructor
     *
     * @param ray             The intersecting ray
     * @param t               The location of the intersection on the ray
     * @param material        The material at the intersection point
     * @param outwardNormalFn The function to create the normal for the outside face of the intersection point.
     *                        Must result in a unit vector
     */
    public Intersection(final Ray ray, final double t, final Material material,
                        final Function<Point3, Vector3> outwardNormalFn) {
        this.ray = ray;
        p = ray.at(t);
        this.t = t;
        this.material = material;

        n = outwardNormalFn.apply(p);

        frontFace = ray.direction().dot(n) < 0;
        if (!frontFace) {
            n.negate();
        }

        n.setImmutable();
        p.setImmutable();
    }
}
