package com.wombatsw.raytracing.model;

import com.wombatsw.raytracing.material.Material;
import lombok.Getter;

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
    private final Vector3 p;
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
     * The horizontal coordinate on the texture map
     */
    private final double u;
    /**
     * The vertical coordinate on the texture map
     */
    private final double v;
    /**
     * Whether the intersection was from the inside our outside
     */
    private final boolean frontFace;

    /**
     * Intersection constructor
     *
     * @param ray      The intersecting ray
     * @param t        The location of the intersection on the ray
     * @param p        The location of the intersection in world coordinates
     * @param n        The surface normal at the point of intersection
     * @param u        The U mapping coordinate
     * @param v        The V mapping coordinate
     * @param material The material at the intersection point
     */
    public Intersection(final Ray ray, final double t, final Vector3 p, final Vector3 n,
                        final double u, final double v, final Material material) {
        this.ray = ray;
        this.t = t;
        this.p = p;
        this.n = n;
        this.u = u;
        this.v = v;
        this.material = material;

        frontFace = ray.direction().dot(n) < 0;
        if (!frontFace) {
            n.negate();
        }

        n.setImmutable();
        p.setImmutable();
    }

    /**
     * Get the color is emitted by the surface at this intersection point. Returns black for none.
     *
     * @return The emitted light color, or black for none
     */
    public Vector3 emitted() {
        return material.emitted(u, v, p);
    }
}
