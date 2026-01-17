package com.wombatsw.raytracing.engine;

import com.wombatsw.raytracing.model.Vector3;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Antialiasing driver. Generates sets of points on the viewport for use in antialiasing
 */
public class AntiAlias {
    private final double[] xOffsets;
    private final double[] yOffsets;
    private final int randomSamples;

    private Viewport viewport;
    private List<Vector3> viewportOffsets;

    /**
     * Create an {@link AntiAlias} using a grid
     *
     * @param xSamples The number of samples along the width
     * @param ySamples The number of samples along the height
     */
    public AntiAlias(final int xSamples, final int ySamples) {
        xOffsets = getAxisOffsets(xSamples);
        yOffsets = getAxisOffsets(ySamples);
        randomSamples = 0;
    }

    /**
     * Create an {@link AntiAlias} using random sampling
     *
     * @param randomSamples The number of samples
     */
    public AntiAlias(final int randomSamples) {
        xOffsets = null;
        yOffsets = null;
        this.randomSamples = randomSamples;
    }

    /**
     * Initialize this {@link AntiAlias} instance
     *
     * @param viewport The {@link Viewport}
     */
    public void initialize(final Viewport viewport) {
        this.viewport = viewport;

        if (randomSamples > 0) {
            viewportOffsets = IntStream.range(0, randomSamples)
                    .mapToObj(i -> getOffset(
                            MathUtils.randomDouble() - 0.5,
                            MathUtils.randomDouble() - 0.5))
                    .toList();
        } else {
            viewportOffsets = new ArrayList<>(xOffsets.length * yOffsets.length);
            for (double yOffset : yOffsets) {
                for (double xOffset : xOffsets) {
                    viewportOffsets.add(getOffset(xOffset, yOffset));
                }
            }
        }
    }

    /**
     * Get a set of pixels near the indicated coordinates. The pixels are chosen based on the antialias settings
     *
     * @param x The x coordinate
     * @param y The y coordinate
     * @return The pixel locations in viewport coordinates
     */
    public List<Vector3> getSamplingPoints(final int x, final int y) {
        return viewportOffsets.stream()
                .map(offset -> viewport.getPixelOrigin().copy()
                        .addScaled(viewport.getPixelDU(), x, viewport.getPixelDV(), y)
                        .add(offset)
                        .setImmutable())
                .toList();
    }

    /**
     * Get an array of samples evenly distributed along a unit interval
     *
     * @param numSamples Number of samples
     * @return The array of samples
     */
    private double[] getAxisOffsets(int numSamples) {
        double delta = 1.0 / numSamples;
        double start = -0.5 + delta / 2.0;
        return IntStream.range(0, numSamples)
                .mapToDouble(i -> start + i * delta)
                .toArray();
    }

    /**
     * Get a pixel offset relative to the viewport plane
     *
     * @param xOffset The x offset
     * @param yOffset The y offset
     * @return The offset
     */
    private Vector3 getOffset(final double xOffset, final double yOffset) {
        return Vector3.newZeroVector()
                .addScaled(viewport.getPixelDU(), xOffset, viewport.getPixelDV(), yOffset);
    }
}
