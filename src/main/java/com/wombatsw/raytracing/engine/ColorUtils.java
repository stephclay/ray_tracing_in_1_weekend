package com.wombatsw.raytracing.engine;

import com.wombatsw.raytracing.model.Interval;
import com.wombatsw.raytracing.model.Vector3;

/**
 * Utilities for handling color
 */
public class ColorUtils {
    private static final Interval RANGE = new Interval(0, 0.999999);

    /**
     * Write this color to the provided byte array at the given offset.
     *
     * @param color     The color to write
     * @param imageData The byte array
     * @param offset    The offset
     */
    public static void writeColor(final Vector3 color, final byte[] imageData, final int offset) {
        double r = gamma(color.getX());
        double g = gamma(color.getY());
        double b = gamma(color.getZ());

        byte ir = (byte) (255.999 * RANGE.clamp(r));
        byte ig = (byte) (255.999 * RANGE.clamp(g));
        byte ib = (byte) (255.999 * RANGE.clamp(b));

        imageData[offset] = ir;
        imageData[offset + 1] = ig;
        imageData[offset + 2] = ib;
    }

    /**
     * Get a new instance of a white color
     *
     * @return The {@link Vector3} white
     */
    public static Vector3 white() {
        return new Vector3(1, 1, 1);
    }

    /**
     * Get a new instance of a black color
     *
     * @return The {@link Vector3} black
     */
    public static Vector3 black() {
        return new Vector3(0, 0, 0);
    }

    /**
     * Compute the gamma for the provided value
     *
     * @param x The value
     * @return The value with gamma applied
     */
    private static double gamma(double x) {
        if (x > 0) {
            return Math.sqrt(x);
        }
        return 0;
    }

}
