package com.wombatsw.raytracing.model;

/**
 * An abstract type for points, vectors, and colors.
 *
 * @param <T> The concrete type that extends this abstract class.
 */
public abstract class Triplet<T extends Triplet<T>> {
    private static final double EPSILON = 1e-8;

    private final double x, y, z;

    public Triplet(final double x, final double y, final double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Create a new instance of the implementing class.
     *
     * @param x The first component of the triplet
     * @param y The second component of the triplet
     * @param z The third component of the triplet
     * @return The instantiated object
     */
    public abstract T create(final double x, final double y, final double z);

    /**
     * @return the first component
     */
    public double getX() {
        return x;
    }

    /**
     * @return the second component
     */
    public double getY() {
        return y;
    }

    /**
     * @return the third component
     */
    public double getZ() {
        return z;
    }

    /**
     * Add this triplet to the provided one
     *
     * @param v The value to add
     * @return A new triplet with the result
     */
    public T add(final Triplet<?> v) {
        return create(x + v.getX(), y + v.getY(), z + v.getZ());
    }

    /**
     * Subtract the provided triplet from this one
     *
     * @param v The value to subtract
     * @return A new triplet with the result
     */
    public T sub(final Triplet<?> v) {
        return create(x - v.getX(), y - v.getY(), z - v.getZ());
    }

    /**
     * Multiply the values of this triplet with the values of the provided triplet
     *
     * @param v The value to multiply
     * @return A new triplet with the result
     */
    public T mul(final Triplet<?> v) {
        return create(x * v.getX(), y * v.getY(), z * v.getZ());
    }

    /**
     * Multiply this triplet ny a scalar value
     *
     * @param t The value to multiply
     * @return A new triplet with the result
     */
    public T mul(final double t) {
        return create(x * t, y * t, z * t);
    }

    /**
     * Divide this triplet ny a scalar value
     *
     * @param t The value to divide
     * @return A new triplet with the result
     */
    public T div(final double t) {
        return mul(1 / t);
    }

    /**
     * Negate this triplet
     *
     * @return A new triplet with the result
     */
    public T negate() {
        return mul(-1);
    }

    /**
     * Take the dot product of this and the provided triplet
     *
     * @param v The other triplet
     * @return The dot product
     */
    public double dot(final Triplet<?> v) {
        return x * v.getX() + y * v.getY() + z * v.getZ();
    }

    /**
     * Take the cross product of this and the provided triplet
     *
     * @param v The other triplet
     * @return A new triplet with the result
     */
    public T cross(final Triplet<?> v) {
        return create(
                y * v.getZ() - z * v.getY(),
                z * v.getX() - x * v.getZ(),
                x * v.getY() - y * v.getX());
    }

    /**
     * Perform linear interpolation between this triplet and the provided one
     *
     * @param end The other triplet
     * @param a   The scaling value in the range [0, 1]
     * @return A new triplet with the result
     */
    public T lerp(final Triplet<?> end, final double a) {
        return mul(1 - a).add(end.mul(a));
    }

    /**
     * Get the length of this triplet squared
     *
     * @return The length of this triplet squared
     */
    public double lenSquared() {
        return dot(this);
    }

    /**
     * Get the length of this triplet
     *
     * @return The length of this triplet
     */
    public double len() {
        return Math.sqrt(lenSquared());
    }

    public boolean nearZero() {
        return Math.abs(x) < EPSILON &&
                Math.abs(y) < EPSILON &&
                Math.abs(z) < EPSILON;
    }
}
