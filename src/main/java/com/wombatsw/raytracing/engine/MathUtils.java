package com.wombatsw.raytracing.engine;

import java.security.SecureRandom;
import java.util.Random;

/**
 * Math utilities
 */
public class MathUtils {
    private final static Random RANDOM = new SecureRandom();

    /**
     * @return A random number in the range [0, 1)
     */
    public static double randomDouble() {
        return RANDOM.nextDouble();
    }

    /**
     * @return A random number in the range [min, max)
     */
    public static double randomDouble(final double min, final double max) {
        return min + (max - min) * RANDOM.nextDouble();
    }

    /**
     * Linear interpolation from start to end by value a
     *
     * @param a     The distance from start to end as a ratio from 0 to 1
     * @param start The starting value
     * @param end   The ending value
     * @return The interpolated value
     */
    public static double lerp(final double a, final double start, final double end) {
        return (1.0 - a) * start + (a * end);
    }

}
