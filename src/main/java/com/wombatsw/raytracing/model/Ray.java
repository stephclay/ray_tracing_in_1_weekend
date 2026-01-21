package com.wombatsw.raytracing.model;

/**
 * A ray consisting of an origin and direction
 */
public record Ray(Triplet origin, Triplet direction, double time) {
    public Ray {
        origin.setImmutable();
        direction.setImmutable();
    }

    public Ray(final Triplet origin, final Triplet direction) {
        this(origin, direction, 0);
    }

    /**
     * Get the point at the given location on the ray. 1 would be the tip of the direction vector relative to the point
     * of origin
     *
     * @param t The location along the ray
     * @return The point corresponding to that location
     */
    public Triplet at(final double t) {
        return origin.copy().addScaled(direction, t);
    }
}
