package com.wombatsw.raytracing.model;

import com.wombatsw.raytracing.engine.MathUtils;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Collection;

/**
 * A mutable 3D Vector
 */
@EqualsAndHashCode
@ToString(callSuper = true)
public class Vector3 {
    private static final double EPSILON = 1e-8;

    @Getter
    private final Tuple tuple;

    public Vector3(final double x, final double y, final double z) {
        this(new Tuple(x, y, z));
    }

    Vector3(final Tuple tuple) {
        this.tuple = new Tuple(tuple);
    }

    /**
     * Create a vector pointing from the provided tail to the provided head
     *
     * @param head The tip or head of the vector
     * @param tail The start or tail of the vector
     */
    public Vector3(final Vector3 head, final Vector3 tail) {
        this(head.copy().sub(tail).getTuple());
    }

    /**
     * Create a new zero-length vector
     */
    public static Vector3 newZeroVector() {
        return new Vector3(0, 0, 0);
    }

    /**
     * Create a new vector based on scaling the provided vector
     *
     * @param v     The reference vector
     * @param scale The scale factor
     * @return The new {@link Vector3}
     */
    public static Vector3 newScaled(final Vector3 v, final double scale) {
        return newZeroVector().addScaled(v, scale);
    }

    /**
     * Average the provided {@link Vector3} values
     *
     * @param samples The list of {@link Vector3} to average
     * @return The averaged {@link Vector3}
     */
    public static Vector3 average(final Collection<Vector3> samples) {
        Vector3 v = new Vector3(0, 0, 0);
        for (Vector3 sample : samples) {
            v.add(sample);
        }
        v.div(samples.size());
        return v;
    }

    /**
     * Generate a random Vector3
     *
     * @return The new {@link Vector3}
     */
    public static Vector3 random() {
        return Vector3.random(0, 1);
    }

    /**
     * Generate a random Vector3 with the provided min and max values for each vector component
     *
     * @param min Min component value
     * @param max Max component value
     * @return The new {@link Vector3}
     */
    public static Vector3 random(final double min, final double max) {
        return new Vector3(MathUtils.randomDouble(min, max),
                MathUtils.randomDouble(min, max),
                MathUtils.randomDouble(min, max));
    }

    /**
     * Generate a random unit vector
     *
     * @return The new {@link Vector3}
     */
    public static Vector3 randomUnitVector() {
        while (true) {
            Vector3 v = random(-1, 1);
            double lenSq = v.lenSquared();
            if (lenSq > 1e-160 && lenSq <= 1.0) {
                return v.div(Math.sqrt(lenSq));
            }
        }
    }

    /**
     * Generate a random unit Vector3 within 90 degrees of the provided normal
     *
     * @param normal The normal vector
     * @return The new {@link Vector3}
     */
    public static Vector3 randomOnHemisphere(final Vector3 normal) {
        Vector3 v = randomUnitVector();
        if (v.dot(normal) < 0) { // other hemisphere
            v.negate();
        }
        return v;
    }

    /**
     * Generate a random value within a unit disc. The disc is in the x/y plane with z = 0
     *
     * @return The new {@link Vector3}
     */
    public static Vector3 randomInUnitDisc() {
        while (true) {
            Vector3 v = new Vector3(MathUtils.randomDouble(-1, 1), MathUtils.randomDouble(-1, 1), 0);
            if (v.lenSquared() < 1) {
                return v;
            }
        }
    }

    /**
     * Set the vector to be immutable
     *
     * @return This vector
     */
    public Vector3 setImmutable() {
        this.tuple.setMutable(false);
        return this;
    }

    public Vector3 copy() {
        // Tuple data is copied in the constructor
        return new Vector3(tuple);
    }

    /**
     * @return the first component
     */
    public double getX() {
        return getValue(0);
    }

    /**
     * @return the second component
     */
    public double getY() {
        return getValue(1);
    }

    /**
     * @return the third component
     */
    public double getZ() {
        return getValue(2);
    }

    /**
     * Get the component by index
     *
     * @param index The index to access
     * @return The value
     */
    public double getValue(final int index) {
        return tuple.getValue(index);
    }

    /**
     * Add the provided vector into this one. This is a mutating operation
     *
     * @param v The value to add
     * @return This vector
     */
    public Vector3 add(final Vector3 v) {
        tuple.add(v.tuple);
        return this;
    }

    /**
     * Subtract this vector from the provided one. This is a mutating operation
     *
     * @param v The value to subtract
     * @return This vector
     */
    public Vector3 sub(final Vector3 v) {
        tuple.sub(v.tuple);
        return this;
    }

    /**
     * Multiply this vector by a scalar value. This is a mutating operation
     *
     * @param t The value to multiply
     * @return This vector
     */
    public Vector3 mul(final double t) {
        tuple.mul(t);
        return this;
    }

    /**
     * Multiply this vector by the provided one. This is a mutating operation
     *
     * @param v The vector to multiply into this one
     * @return This vector
     */
    public Vector3 mul(final Vector3 v) {
        tuple.mul(v.tuple);
        return this;
    }

    /**
     * Divide this vector by a scalar value. This is a mutating operation
     *
     * @param t The value to divide
     * @return This vector
     */
    public Vector3 div(final double t) {
        return mul(1 / t);
    }

    /**
     * Negate this vector. This is a mutating operation
     *
     * @return This vector
     */
    public Vector3 negate() {
        return mul(-1);
    }

    /**
     * Add the provided vector to this one after scaling it. This is a mutating operation
     *
     * @param v     The value to add
     * @param scale The scaling factor
     * @return This vector
     */
    public Vector3 addScaled(final Vector3 v, final double scale) {
        tuple.addScaled(v.tuple, scale);
        return this;
    }

    /**
     * Add the provided vectors to this one after scaling them. This is a mutating operation
     *
     * @param u      The first value to add
     * @param uScale The scaling factor for u
     * @param v      The second value to add
     * @param vScale The scaling factor for v
     * @return This vector
     */
    public Vector3 addScaled(final Vector3 u, final double uScale, final Vector3 v, final double vScale) {
        return addScaled(u, uScale).addScaled(v, vScale);
    }

    /**
     * Normalize this vector to a unit value. This is a mutating operation
     *
     * @return This vector
     */
    public Vector3 normalize() {
        return div(len());
    }

    /**
     * Reflect this vector relative to the given surface normal. This is a mutating operation
     *
     * @param n The surface normal, which must be a unit vector
     * @return This vector
     */
    public Vector3 reflect(final Vector3 n) {
        // V - 2.0 * V.N
        addScaled(n, -2.0 * dot(n));
        return this;
    }

    /**
     * Refract this vector relative to the given normal and refraction ratio
     *
     * @param n        The surface normal, which must be a unit vector
     * @param etaRatio Ratio of refractive indices as eta/eta prime
     * @return This vector
     */
    public Vector3 refract(final Vector3 n, final double etaRatio) {
        // Compute the perpendicular component
        // Rperp = (Rorig + cosTheta * N) * etaRatio
        double cosTheta = Math.min(1.0, -dot(n));
        addScaled(n, cosTheta).mul(etaRatio);
        // This vector now contains the perpendicular component of the result

        // Compute the parallel component and add to perpendicular one
        // Rparallel = -sqrt(|1 - Rperp.Rperp|) * N
        double scale = Math.sqrt(Math.abs(1.0 - dot(this)));
        return addScaled(n, -scale);
    }

    /**
     * Translate this vector in the given direction according to the given scale. This is a mutating operation
     *
     * @param dir The direction of translation
     * @param t   The scaling factor for the given direction
     * @return This vector
     */
    public Vector3 translate(final Vector3 dir, final double t) {
        tuple.translate(dir.tuple, t);
        return this;
    }

    /**
     * Perform linear interpolation between this vector and the provided one
     *
     * @param end The other vector. This is a mutating operation
     * @param a   The scaling value in the range [0, 1]
     * @return This vector
     */
    public Vector3 lerp(final Vector3 end, final double a) {
        tuple.lerp(end.tuple, a);
        return this;
    }

    /**
     * Take the dot product of this and the provided vector
     *
     * @param v The other vector
     * @return The dot product
     */
    public double dot(final Vector3 v) {
        return getX() * v.getX() + getY() * v.getY() + getZ() * v.getZ();
    }

    /**
     * Take the cross product of this and the provided vector
     *
     * @param v The other vector
     * @return A new vector with the result
     */
    public Vector3 cross(final Vector3 v) {
        return new Vector3(new Tuple(
                getY() * v.getZ() - getZ() * v.getY(),
                getZ() * v.getX() - getX() * v.getZ(),
                getX() * v.getY() - getY() * v.getX()));
    }

    /**
     * Get the length of this vector squared
     *
     * @return The length of this vector squared
     */
    public double lenSquared() {
        return dot(this);
    }

    /**
     * Get the length of this vector
     *
     * @return The length of this vector
     */
    public double len() {
        return Math.sqrt(lenSquared());
    }

    /**
     * Check if the vector value is near zero for all components
     *
     * @return Whether the vector is near zero
     */
    public boolean nearZero() {
        return Math.abs(getX()) < EPSILON &&
                Math.abs(getY()) < EPSILON &&
                Math.abs(getZ()) < EPSILON;
    }
}
