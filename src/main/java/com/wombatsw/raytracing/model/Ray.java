package com.wombatsw.raytracing.model;

/**
 * A ray consisting of an origin and direction
 */
public record Ray(Point3 origin, Vector3 direction, double time) {
    public Ray {
        origin.setImmutable();
        direction.setImmutable();
    }

    public Ray(final Point3 origin, final Vector3 direction) {
        this(origin, direction, 0);
    }

    /**
     * Get the point at the given location on the ray. 1 would be the tip of the direction vector relative to the point
     * of origin
     *
     * @param t The location along the ray
     * @return The point corresponding to that location
     */
    public Point3 at(final double t) {
        return origin.copy().translate(direction, t);
    }
}
