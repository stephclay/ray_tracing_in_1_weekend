package com.wombatsw.raytracing.model;

/**
 * An interval between 2 scalar values
 */
public record Interval(double min, double max) {
    public final static Interval EMPTY = new Interval(Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY);
    public final static Interval UNIVERSE = new Interval(Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);

    /**
     * Create an interval as a union of 2 intervals
     *
     * @param a The first interval
     * @param b The second interval
     */
    public Interval(final Interval a, final Interval b) {
        this(Math.min(a.min(), b.min()), Math.max(a.max(), b.max()));
    }

    /**
     * Create an Interval with the provided values, reordering them if needed so that min <= max.
     *
     * @param t0 One end of the interval
     * @param t1 The other end of the interval
     * @return The new Interval
     */
    public static Interval createOrdered(final double t0, final double t1) {
        return t0 <= t1 ? new Interval(t0, t1) : new Interval(t1, t0);
    }

    /**
     * Get the size of the interval. Will be negative if the interval is empty (min > max)
     *
     * @return The size of the interval
     */
    public double size() {
        return max - min;
    }

    /**
     * Check of the provide value is within the interval, including endpoints
     *
     * @param x The value to check
     * @return true if the value is in the range [min, max]
     */
    public boolean contains(final double x) {
        return min <= x && x <= max;
    }

    /**
     * Check of the provide value is within the interval, excluding endpoints
     *
     * @param x The value to check
     * @return true if the value is in the range (min, max)
     */
    public boolean surrounds(final double x) {
        return min < x && x < max;
    }

    /**
     * Clamp the value to this interval. If the value is less than min, then min will be returned. If the value is
     * greater than max, then max will be returned. Otherwise, the original value will be returned
     *
     * @param x The value to clamp
     * @return The clamped value
     */
    public double clamp(final double x) {
        return Math.max(min, Math.min(max, x));
    }

    public Interval expand(final double delta) {
        double pad = delta / 2.0;
        return new Interval(min - pad, max + pad);
    }
}
