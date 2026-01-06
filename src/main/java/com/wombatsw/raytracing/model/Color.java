package com.wombatsw.raytracing.model;

/**
 * A Color represented by an RGB triplet
 */
public class Color extends Triplet<Color> {
    private static final Interval RANGE = new Interval(0, 0.999999);

    public Color(final double x, final double y, final double z) {
        super(x, y, z);
    }

    @Override
    public Color create(final double x, final double y, final double z) {
        return new Color(x, y, z);
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
