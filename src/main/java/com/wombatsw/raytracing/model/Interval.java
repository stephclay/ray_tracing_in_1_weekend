package com.wombatsw.raytracing.model;

/**
 * An interval between 2 scalar values
 */
public class Interval {
    public final static Interval EMPTY = new Interval(Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY);
    public final static Interval UNIVERSE = new Interval(Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);

    private final double min;
    private final double max;

    public Interval(final double min, final double max) {
        this.min = min;
        this.max = max;
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

    /**
     * @return the interval minimum
     */
    public double getMin() {
        return min;
    }

    /**
     * @return the interval maximum
     */
    public double getMax() {
        return max;
    }
}
