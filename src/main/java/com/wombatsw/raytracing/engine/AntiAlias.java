package com.wombatsw.raytracing.engine;

import com.wombatsw.raytracing.model.Point3;
import com.wombatsw.raytracing.model.Vector3;

/**
 * Antialiasing driver. Generates sets of points on the viewport for use in antialiasing
 */
public class AntiAlias {
    private final int xSamples;
    private final int ySamples;
    private final int randomSamples;

    private Point3 pixelOrigin;
    private Vector3 pixelDU;
    private Vector3 pixelDV;

    /**
     * Create an {@link AntiAlias} using a grid
     *
     * @param xSamples The number of samples along the width
     * @param ySamples The number of samples along the height
     */
    public AntiAlias(final int xSamples, final int ySamples) {
        this.xSamples = xSamples;
        this.ySamples = ySamples;
        this.randomSamples = 0;
    }

    /**
     * Create an {@link AntiAlias} using random sampling
     *
     * @param randomSamples The number of samples
     */
    public AntiAlias(final int randomSamples) {
        this.xSamples = 0;
        this.ySamples = 0;
        this.randomSamples = randomSamples;
    }

    /**
     * Initialize this {@link AntiAlias} instance
     *
     * @param pixelOrigin The origin of the viewport
     * @param pixelDU The distance between pixels along the width of the viewport
     * @param pixelDV The distance between pixels along the height of the viewport
     */
    public void initialize(final Point3 pixelOrigin, final Vector3 pixelDU, final Vector3 pixelDV) {
        this.pixelOrigin = pixelOrigin;
        this.pixelDU = pixelDU;
        this.pixelDV = pixelDV;
    }

    /**
     * Get a set of pixels near the indicated coordinates. The pixels are chosen based on the antialias settings
     *
     * @param x The x coordinate
     * @param y The y coordinate
     * @return The pixel locations in viewport coordinates
     */
    public Point3[] getPoints(final int x, final int y) {
        if (randomSamples > 0) {
            Point3[] points = new Point3[randomSamples];
            for (int i = 0; i < randomSamples; i++) {
                points[i] = getRandomPoint(x, y);
            }
            return points;
        }

        double dx = 1.0 / xSamples;
        double xStart = x - 0.5 + dx / 2.0;
        double dy = 1.0 / ySamples;
        double yStart = y - 0.5 + dy / 2.0;

        Point3[] points = new Point3[xSamples * ySamples];
        for (int j = 0; j < ySamples; j++) {
            double yOffset = yStart + j * dy;
            for (int i = 0; i < xSamples; i++) {
                double xOffset = xStart + i * dx;
                points[j*xSamples + i] = getPointAtOffset(xOffset, yOffset);
            }
        }
        return points;
    }

    /**
     * Get a random point near the indicated pixel
     *
     * @param x The x coordinate
     * @param y The y coordinate
     * @return The pixel location in viewport coordinates
     */
    private Point3 getRandomPoint(final int x, final int y) {
        return getPointAtOffset(
                x + MathUtils.randomDouble() - 0.5,
                y + MathUtils.randomDouble() - 0.5);
    }

    /**
     * Get a pixel offset from the origin
     *
     * @param xOffset The x offset
     * @param yOffset The y offset
     * @return The pixel location in viewport coordinates
     */
    private Point3 getPointAtOffset(final double xOffset, final double yOffset) {
        Vector3 du = pixelDU.copy().mul(xOffset);
        Vector3 dv = pixelDV.copy().mul(yOffset);
        return pixelOrigin.copy().add(du).add(dv);
    }
}
