package com.wombatsw.raytracing.model;

/**
 * A 3D Point
 */
public class Point3 extends Triplet<Point3> {
    public Point3(final double x, final double y, final double z) {
        super(x, y, z);
    }

    /**
     * Create a vector from the provided point to this one
     *
     * @param initial The start of the vector
     * @return The new Vector3
     */
    public Vector3 vectorFrom(final Point3 initial) {
        Point3 v = sub(initial);
        return new Vector3(v.getX(), v.getY(), v.getZ());
    }

    @Override
    public Point3 create(final double x, final double y, final double z) {
        return new Point3(x, y, z);
    }
}
