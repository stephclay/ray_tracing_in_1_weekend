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
}
