package com.wombatsw.raytracing.model;

import com.wombatsw.raytracing.engine.MathUtils;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Value;

import java.util.Arrays;

/**
 * A mutable tuple with vector operation methods. Operations can be chained
 */
@Value
class Tuple {
    @Getter(AccessLevel.NONE)
    double[] values;

    Tuple(final double... values) {
        this.values = Arrays.copyOf(values, values.length);
    }

    Tuple(final Tuple tuple) {
        this(tuple.values);
    }

    /**
     * Get the value at the specified index
     *
     * @param index The index to access
     * @return The value
     * @throws ArrayIndexOutOfBoundsException if the index is invalid
     */
    public double getValue(final int index) {
        return values[index];
    }

    /**
     * Copy this tuple
     *
     * @return The copy
     */
    public Tuple copy() {
        return new Tuple(this);
    }

    /**
     * Add the provided tuple to this one
     *
     * @param v The value to add
     * @return This tuple
     */
    public Tuple add(final Tuple v) {
        assertSize(v);

        for (int i = 0; i < values.length; i++) {
            values[i] += v.values[i];
        }
        return this;
    }

    /**
     * Subtract the provided tuple from this one
     *
     * @param v The value to subtract
     * @return This tuple
     */
    public Tuple sub(final Tuple v) {
        assertSize(v);

        for (int i = 0; i < values.length; i++) {
            values[i] -= v.values[i];
        }
        return this;
    }

    /**
     * Multiply the provided tuple into this one
     *
     * @param v The value to multiply by
     * @return This tuple
     */
    public Tuple mul(final Tuple v) {
        assertSize(v);

        for (int i = 0; i < values.length; i++) {
            values[i] *= v.values[i];
        }
        return this;
    }

    /**
     * Multiply this tuple by the provided scalar
     *
     * @param t The value to multiply by
     * @return This tuple
     */
    public Tuple mul(final double t) {
        for (int i = 0; i < values.length; i++) {
            values[i] *= t;
        }
        return this;
    }

    /**
     * Translate this tuple in the given direction according to the given scale
     *
     * @param dir The direction of translation
     * @param t   The scaling factor for the given direction
     * @return This tuple
     */
    public Tuple translate(final Tuple dir, final double t) {
        assertSize(dir);

        for (int i = 0; i < values.length; i++) {
            values[i] += dir.values[i] * t;
        }

        return this;
    }

    /**
     * Perform linear interpolation between this tuple and the provided one
     *
     * @param end The other tuple
     * @param a   The scaling value in the range [0, 1]
     * @return This tuple
     */
    public Tuple lerp(final Tuple end, final double a) {
        assertSize(end);

        for (int i = 0; i < values.length; i++) {
            values[i] = MathUtils.lerp(a, values[i], end.values[i]);
        }

        return this;
    }

    /**
     * Make sure that the provided tuple has the same number of elements as this one
     *
     * @param v The {@link Tuple} to validate
     */
    private void assertSize(Tuple v) {
        if (values.length != v.values.length) {
            throw new IllegalArgumentException(
                    String.format("Tuples must have the same length (%d != %d)", values.length, v.values.length));
        }
    }
}
