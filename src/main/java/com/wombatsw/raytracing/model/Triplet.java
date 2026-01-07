package com.wombatsw.raytracing.model;

import lombok.AccessLevel;
import lombok.Getter;

/**
 * An abstract type for points, vectors, and colors. Most methods mutate the triplet, which is done to limit object
 * creation and improve performance.
 *
 * @param <T> The concrete type that extends this abstract class.
 */
public abstract class Triplet<T extends Triplet<T>> {
    private static final double EPSILON = 1e-8;

    @Getter(AccessLevel.PACKAGE)
    private final Tuple tuple;

    Triplet(final Tuple t) {
        tuple = new Tuple(t);
    }

    /**
     * Create a new instance of the implementing class.
     *
     * @param t The triplet values
     * @return The instantiated object
     */
    abstract T create(final Tuple t);

    public T copy() {
        return create(tuple.copy());
    }

    /**
     * @return the first component
     */
    public double getX() {
        return tuple.getValue(0);
    }

    /**
     * @return the second component
     */
    public double getY() {
        return tuple.getValue(1);
    }

    /**
     * @return the third component
     */
    public double getZ() {
        return tuple.getValue(2);
    }

    /**
     * Add the provided triplet into this one. This is a mutating operation
     *
     * @param v The value to add
     * @return This triplet
     */
    public T add(final Triplet<?> v) {
        tuple.add(v.tuple);
        return cast();
    }

    /**
     * Subtract this triplet from the provided one. This is a mutating operation
     *
     * @param v The value to subtract
     * @return This triplet
     */
    public T sub(final Triplet<?> v) {
        tuple.sub(v.tuple);
        return cast();
    }

    /**
     * Multiply this triplet by a scalar value. This is a mutating operation
     *
     * @param t The value to multiply
     * @return This triplet
     */
    public T mul(final double t) {
        tuple.mul(t);
        return cast();
    }

    public T mul(final Triplet<?> v) {
        tuple.mul(v.tuple);
        return cast();
    }

    /**
     * Divide this vector by a scalar value. This is a mutating operation
     *
     * @param t The value to divide
     * @return This triplet
     */
    public T div(final double t) {
        return mul(1 / t);
    }

    /**
     * Negate this triplet. This is a mutating operation
     *
     * @return This triplet
     */
    public T negate() {
        return mul(-1);
    }

    /**
     * Normalize this triplet to a unit value. This is a mutating operation
     *
     * @return This triplet
     */
    public T normalize() {
        return div(len());
    }

    /**
     * Reflect this vector relative to the given surface normal. This is a mutating operation
     *
     * @param n The surface normal, which must be a unit vector
     * @return This triplet
     */
    public T reflect(final Triplet<?> n) {
        double scale = 2.0 * dot(n);
        Tuple t = n.tuple.copy().mul(scale);
        tuple.sub(t);
        return cast();
    }

    /**
     * Translate this triplet in the given direction according to the given scale. This is a mutating operation
     *
     * @param dir The direction of translation
     * @param t   The scaling factor for the given direction
     * @return This triplet
     */
    public T translate(final Triplet<?> dir, final double t) {
        tuple.translate(dir.tuple, t);
        return cast();
    }

    /**
     * Perform linear interpolation between this triplet and the provided one
     *
     * @param end The other triplet. This is a mutating operation
     * @param a   The scaling value in the range [0, 1]
     * @return This triplet
     */
    public T lerp(final Triplet<?> end, final double a) {
        tuple.lerp(end.tuple, a);
        return cast();
    }

    /**
     * Take the dot product of this and the provided triplet
     *
     * @param v The other triplet
     * @return The dot product
     */
    public double dot(final Triplet<?> v) {
        return getX() * v.getX() + getY() * v.getY() + getZ() * v.getZ();
    }

    /**
     * Take the cross product of this and the provided triplet
     *
     * @param v The other triplet
     * @return A new triplet with the result
     */
    public T cross(final Triplet<?> v) {
        return create(new Tuple(
                getY() * v.getZ() - getZ() * v.getY(),
                getZ() * v.getX() - getX() * v.getZ(),
                getX() * v.getY() - getY() * v.getX()));
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

    /**
     * Check if the triplet value is near zero for all components
     *
     * @return Whether the triplet is near zero
     */
    public boolean nearZero() {
        return Math.abs(getX()) < EPSILON &&
                Math.abs(getY()) < EPSILON &&
                Math.abs(getZ()) < EPSILON;
    }

    @SuppressWarnings("unchecked")
    private T cast() {
        return (T) this;
    }
}
