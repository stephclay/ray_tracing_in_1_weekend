package com.wombatsw.raytracing.texture;

import com.wombatsw.raytracing.model.Interval;
import com.wombatsw.raytracing.model.Triplet;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Image texture from a provided image file
 */
public class ImageTexture implements Texture {
    private static final Interval BOUNDS = new Interval(0, 1);

    private final BufferedImage image;
    private final int width;
    private final int height;

    public ImageTexture(final String filename) {
        try {
            File file = new File(filename);
            if (file.exists()) {
                image = ImageIO.read(file);
            } else {
                throw new IllegalArgumentException("Couldn't find image file: " + filename);
            }
            width = image.getWidth();
            height = image.getHeight();
        } catch (IOException e) {
            throw new IllegalArgumentException("Couldn't read image file: " + filename, e);
        }
    }

    @Override
    public Triplet value(final double u, final double v, final Triplet p) {
        int x = (int) (BOUNDS.clamp(u) * width);
        int y = (int) ((1 - BOUNDS.clamp(v)) * height);
        int color = image.getRGB(x, y);

        double r = getColorComponent(color, 16);
        double g = getColorComponent(color, 8);
        double b = getColorComponent(color, 0);
        return new Triplet(r, g, b);
    }

    /**
     * Extract a color component from an RGB integer
     *
     * @param value The RGB value
     * @param shift The shift needed for a specific color component
     * @return The color component value in the range 0.0 - 0.1
     */
    private double getColorComponent(final int value, int shift) {
        return ((value >> shift) & 0xff) / 255.0;
    }
}
