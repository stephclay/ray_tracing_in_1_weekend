package com.wombatsw.raytracing.model;

/**
 * A ray consisting of an origin an direction
 */
public class Ray {
    private final Point3 origin;
    private final Vector3 direction;

    public Ray(final Point3 origin, final Vector3 direction) {
        this.origin = origin;
        this.direction = direction;
    }

    /**
     * Get the point at the given location on the ray. 1 would be the tip of the direction vector relative to the point
     * of origin
     *
     * @param t The location along the ray
     * @return The point corresponding to that location
     */
    public Point3 at(final double t) {
        return origin.add(direction.mul(t));
    }

    /**
     * @return The origin
     */
    public Point3 getOrigin() {
        return origin;
    }

    /**
     * @return The direction
     */
    public Vector3 getDirection() {
        return direction;
    }
}
