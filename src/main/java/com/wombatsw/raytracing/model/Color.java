package com.wombatsw.raytracing.model;

import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * A mutable color represented by an RGB triplet
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Color extends Triplet<Color> {
    private static final Interval RANGE = new Interval(0, 0.999999);

    public Color(final double x, final double y, final double z) {
        super(new Tuple(x, y, z));
    }

    Color(final Tuple tuple) {
        super(tuple);
    }

    @Override
    Color create(Tuple t) {
        return new Color(t);
    }

    /**
     * Average the provided color values
     *
     * @param samples The list of colors to average
     * @return The averaged color
     */
    public static Color average(Color... samples) {
        Color color = new Color(0, 0, 0);
        for (Color sample : samples) {
            color.add(sample);
        }
        color.div(samples.length);
        return color;
    }

    /**
     * Write this color to the provided byte array at the given offset.
     *
     * @param imageData The byte array
     * @param offset    The offset
     */
    public void writeColor(final byte[] imageData, final int offset) {
        double r = gamma(getX());
        double g = gamma(getY());
        double b = gamma(getZ());

        byte ir = (byte) (255.999 * RANGE.clamp(r));
        byte ig = (byte) (255.999 * RANGE.clamp(g));
        byte ib = (byte) (255.999 * RANGE.clamp(b));

        imageData[offset] = ir;
        imageData[offset + 1] = ig;
        imageData[offset + 2] = ib;
    }

    /**
     * Compute the gamma for the provided value
     *
     * @param x The value
     * @return The value with gamma applied
     */
    private double gamma(double x) {
        if (x > 0) {
            return Math.sqrt(x);
        }
        return 0;
    }
}
