package com.wombatsw.raytracing.engine;

import com.wombatsw.raytracing.model.Color;
import com.wombatsw.raytracing.model.Intersection;
import com.wombatsw.raytracing.model.Interval;
import com.wombatsw.raytracing.model.Point3;
import com.wombatsw.raytracing.model.Ray;
import com.wombatsw.raytracing.model.ScatterData;
import com.wombatsw.raytracing.model.Vector3;
import com.wombatsw.raytracing.obj.AbstractObj;
import lombok.Getter;
import lombok.Setter;

/**
 * Camera object, which also handles driving the rendering process
 */
public class Camera {
    private final static Color BLUE = new Color(0.5, 0.7, 1);

    /**
     * The image aspect ratio
     */
    @Setter
    private double aspectRatio = 1.0;
    /**
     * The image width
     */
    @Getter
    @Setter
    private int imageWidth = 100;
    /**
     * The image height
     */
    @Getter
    private int imageHeight;
    /**
     * The antialiasing method. Default is a 2x2 grid
     */
    @Setter
    private AntiAlias antiAlias = new AntiAlias(2, 2);
    /**
     * The max depth of reflection
     */
    @Setter
    private int maxDepth = 10;

    private Point3 cameraCenter;

    /**
     * Initialize the camera for the current settings.
     */
    private void initialize() {
        BLUE.setMutable(false);

        imageHeight = Math.max(1, (int) (imageWidth / aspectRatio));

        cameraCenter = new Point3(0, 0, 0);
        cameraCenter.setMutable(false);

        // Viewport dimensions
        double focalLength = 1.0;
        double viewportHeight = 2.0;
        double viewportWidth = viewportHeight * (double) imageWidth / (double) imageHeight;

        // Calculate vectors across the horizontal and vertical viewport edges
        Vector3 viewportU = new Vector3(viewportWidth, 0, 0);
        Vector3 viewportV = new Vector3(0, -viewportHeight, 0);

        // Calculate the horizontal and vertical deltas from pixel to pixel
        Vector3 pixelDU = viewportU.copy().div(imageWidth);
        Vector3 pixelDV = viewportV.copy().div(imageHeight);
        pixelDU.setMutable(false);
        pixelDV.setMutable(false);

        // Calculate the upper left pixel
        Point3 viewportUpperLeft = cameraCenter
                .copy()
                .sub(new Vector3(0, 0, focalLength))
                .sub(viewportU.div(2))
                .sub(viewportV.div(2));
        Vector3 pixelOffset = pixelDU.copy().add(pixelDV).div(2);
        Point3 pixelOrigin = viewportUpperLeft.add(pixelOffset);
        pixelOrigin.setMutable(false);

        antiAlias.initialize(pixelOrigin, pixelDU, pixelDV);
    }

    /**
     * Render the scene for the provided world data
     *
     * @param world The world data
     * @return The image raster of size "width x height x 3" as a row-major ordering of RGB triplets
     */
    public byte[] render(final AbstractObj world) {
        initialize();

        long start = System.currentTimeMillis();
        byte[] imageData = new byte[imageWidth * imageHeight * 3];
        for (int y = 0; y < imageHeight; y++) {
            System.out.printf("\rRemaining: %d", imageHeight - y);
            System.out.flush();

            Color[] row = renderRow(world, y);

            int rowIndex = y * imageWidth * 3;
            for (int x = 0; x < imageWidth; x++) {
                row[x].writeColor(imageData, rowIndex + x * 3);
            }
        }

        long millis = System.currentTimeMillis() - start;
        System.out.printf("\rDone in %d seconds\n", millis / 1000);
        return imageData;
    }

    /**
     * Render a row of the image
     *
     * @param world The world data
     * @param y     The row to render
     */
    private Color[] renderRow(final AbstractObj world, final int y) {
        Color[] row = new Color[imageWidth];

        // TODO: Parallelize this
        for (int x = 0; x < imageWidth; x++) {
            row[x] = getPixelColor(world, x, y);
        }

        return row;
    }

    /**
     * Get the pixel color for the specified raster location
     *
     * @param world The world data
     * @param x     The raster x coordinate
     * @param y     The raster y coordinate
     * @return The color of the pixel
     */
    private Color getPixelColor(final AbstractObj world, final int x, final int y) {
        // TODO: May be able to reuse these points if they are returned as relative offsets from the pixel center
        Point3[] viewportPoints = antiAlias.getPoints(x, y);
        Color[] samples = new Color[viewportPoints.length];

        for (int i = 0; i < viewportPoints.length; i++) {
            Vector3 rayDirection = new Vector3(viewportPoints[i], cameraCenter);
            rayDirection.setMutable(false);
            Ray ray = new Ray(cameraCenter, rayDirection);
            samples[i] = getRayColor(ray, maxDepth, world);
        }

        return Color.average(samples);
    }

    /**
     * Get the color for a specific ray. Will always return a new instance
     *
     * @param ray   The ray to check
     * @param depth The depth of reflection
     * @param world The world data
     * @return The color for this ray
     */
    private Color getRayColor(final Ray ray, final int depth, final AbstractObj world) {
        if (depth <= 0) {
            return Color.black();
        }

        Intersection intersect = world.intersect(ray, new Interval(0.001, Double.POSITIVE_INFINITY));
        if (intersect != null) {
            ScatterData scatterData = intersect.getMaterial().scatter(intersect);
            if (scatterData != null) {
                Color scatterColor = getRayColor(scatterData.ray(), depth - 1, world);
                return scatterColor.mul(scatterData.attenuation());
            }
            return Color.black();
        }

        Vector3 unitDir = ray.direction().copy().normalize();
        double a = 0.5 * (unitDir.getY() + 1.0);

        return Color.white().lerp(BLUE, a);
    }
}
