package com.wombatsw.raytracing.model;

import com.wombatsw.raytracing.engine.MathUtils;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Arrays;
import java.util.Collection;

import static com.wombatsw.raytracing.Constants.EPSILON;

/**
 * A mutable 3D Vector
 */
@EqualsAndHashCode(exclude = "mutable")
@ToString(callSuper = true)
public class Triplet {
    private final double[] values;

    /**
     * Set the mutability of this tuple. Used to prevent changes on objects that need to be used repeatedly
     */
    @Setter
    @Getter
    private boolean mutable = true;

    public Triplet(final double x, final double y, final double z) {
        values = new double[]{x, y, z};
    }

    private Triplet(final double[] values) {
        this.values = Arrays.copyOf(values, values.length);
    }

    /**
     * Create a vector pointing from the provided tail to the provided head
     *
     * @param head The tip or head of the vector
     * @param tail The start or tail of the vector
     */
    public Triplet(final Triplet head, final Triplet tail) {
        this(head.copy().sub(tail).values);
    }

    /**
     * Create a new zero-length vector
     */
    public static Triplet newZeroVector() {
        return new Triplet(0, 0, 0);
    }

    /**
     * Create a new vector based on scaling the provided vector
     *
     * @param v     The reference vector
     * @param scale The scale factor
     * @return The new {@link Triplet}
     */
    public static Triplet newScaled(final Triplet v, final double scale) {
        return newZeroVector().addScaled(v, scale);
    }

    /**
     * Average the provided {@link Triplet} values
     *
     * @param samples The list of {@link Triplet} to average
     * @return The averaged {@link Triplet}
     */
    public static Triplet average(final Collection<Triplet> samples) {
        Triplet v = new Triplet(0, 0, 0);
        for (Triplet sample : samples) {
            v.add(sample);
        }
        v.div(samples.size());
        return v;
    }

    /**
     * Generate a random Vector3
     *
     * @return The new {@link Triplet}
     */
    public static Triplet random() {
        return Triplet.random(0, 1);
    }

    /**
     * Generate a random Vector3 with the provided min and max values for each vector component
     *
     * @param min Min component value
     * @param max Max component value
     * @return The new {@link Triplet}
     */
    public static Triplet random(final double min, final double max) {
        return new Triplet(MathUtils.randomDouble(min, max),
                MathUtils.randomDouble(min, max),
                MathUtils.randomDouble(min, max));
    }

    /**
     * Generate a random unit vector
     *
     * @return The new {@link Triplet}
     */
    public static Triplet randomUnitVector() {
        while (true) {
            Triplet v = random(-1, 1);
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
     * @return The new {@link Triplet}
     */
    public static Triplet randomOnHemisphere(final Triplet normal) {
        Triplet v = randomUnitVector();
        if (v.dot(normal) < 0) { // other hemisphere
            v.negate();
        }
        return v;
    }

    /**
     * Generate a random value within a unit disc. The disc is in the x/y plane with z = 0
     *
     * @return The new {@link Triplet}
     */
    public static Triplet randomInUnitDisc() {
        while (true) {
            Triplet v = new Triplet(MathUtils.randomDouble(-1, 1), MathUtils.randomDouble(-1, 1), 0);
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
    public Triplet setImmutable() {
        setMutable(false);
        return this;
    }

    public Triplet copy() {
        // Tuple data is copied in the constructor
        return new Triplet(values);
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
        return values[index];
    }

    /**
     * Add the provided vector into this one. This is a mutating operation
     *
     * @param v The value to add
     * @return This vector
     */
    public Triplet add(final Triplet v) {
        assertMutationAllowed();

        for (int i = 0; i < values.length; i++) {
            values[i] += v.values[i];
        }
        return this;
    }

    /**
     * Subtract this vector from the provided one. This is a mutating operation
     *
     * @param v The value to subtract
     * @return This vector
     */
    public Triplet sub(final Triplet v) {
        assertMutationAllowed();

        for (int i = 0; i < values.length; i++) {
            values[i] -= v.values[i];
        }
        return this;
    }

    /**
     * Multiply this vector by a scalar value. This is a mutating operation
     *
     * @param t The value to multiply
     * @return This vector
     */
    public Triplet mul(final double t) {
        assertMutationAllowed();

        for (int i = 0; i < values.length; i++) {
            values[i] *= t;
        }
        return this;
    }

    /**
     * Multiply this vector by the provided one. This is a mutating operation
     *
     * @param v The vector to multiply into this one
     * @return This vector
     */
    public Triplet mul(final Triplet v) {
        assertMutationAllowed();

        for (int i = 0; i < values.length; i++) {
            values[i] *= v.values[i];
        }
        return this;
    }

    /**
     * Divide this vector by a scalar value. This is a mutating operation
     *
     * @param t The value to divide
     * @return This vector
     */
    public Triplet div(final double t) {
        return mul(1 / t);
    }

    /**
     * Negate this vector. This is a mutating operation
     *
     * @return This vector
     */
    public Triplet negate() {
        return mul(-1);
    }

    /**
     * Add the provided vector to this one after scaling it. This is a mutating operation
     *
     * @param v     The value to add
     * @param scale The scaling factor
     * @return This vector
     */
    public Triplet addScaled(final Triplet v, final double scale) {
        assertMutationAllowed();

        for (int i = 0; i < values.length; i++) {
            values[i] += v.values[i] * scale;
        }
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
    public Triplet addScaled(final Triplet u, final double uScale, final Triplet v, final double vScale) {
        return addScaled(u, uScale).addScaled(v, vScale);
    }

    /**
     * Normalize this vector to a unit value. This is a mutating operation
     *
     * @return This vector
     */
    public Triplet normalize() {
        return div(len());
    }

    /**
     * Reflect this vector relative to the given surface normal. This is a mutating operation
     *
     * @param n The surface normal, which must be a unit vector
     * @return This vector
     */
    public Triplet reflect(final Triplet n) {
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
    public Triplet refract(final Triplet n, final double etaRatio) {
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
     * Perform linear interpolation between this vector and the provided one
     *
     * @param end The other vector. This is a mutating operation
     * @param a   The scaling value in the range [0, 1]
     * @return This vector
     */
    public Triplet lerp(final Triplet end, final double a) {
        assertMutationAllowed();

        for (int i = 0; i < values.length; i++) {
            values[i] = MathUtils.lerp(a, values[i], end.values[i]);
        }
        return this;
    }

    /**
     * Take the dot product of this and the provided vector
     *
     * @param v The other vector
     * @return The dot product
     */
    public double dot(final Triplet v) {
        return getX() * v.getX() + getY() * v.getY() + getZ() * v.getZ();
    }

    /**
     * Take the cross product of this and the provided vector
     *
     * @param v The other vector
     * @return A new vector with the result
     */
    public Triplet cross(final Triplet v) {
        return new Triplet(
                getY() * v.getZ() - getZ() * v.getY(),
                getZ() * v.getX() - getX() * v.getZ(),
                getX() * v.getY() - getY() * v.getX());
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

    /**
     * Make sure that this object can be mutated
     */
    private void assertMutationAllowed() {
        if (!mutable) {
            throw new IllegalStateException("This data is not mutable");
        }
    }
}
