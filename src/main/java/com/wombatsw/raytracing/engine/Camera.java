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
    private final static Color WHITE = new Color(1, 1, 1);
    private final static Color BLACK = new Color(0, 0, 0);
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
     * The number of samples per pixel
     */
    @Setter
    private int samplesPerPixel = 10;
    /**
     * The max depth of reflection
     */
    @Setter
    private int maxDepth = 10;

    private Vector3 pixelDU;
    private Vector3 pixelDV;

    private Point3 cameraCenter;
    private Point3 pixelOrigin;

    /**
     * Render the scene for the provided world data
     *
     * @param world The world data
     * @return The image raster of size "width x height x 3" as a row-major ordering of RGB triplets
     */
    public byte[] render(final AbstractObj world) {
        initialize();

        byte[] imageData = new byte[imageWidth * imageHeight * 3];
        for (int y = 0; y < imageHeight; y++) {
            System.out.printf("\rRemaining: %d", imageHeight - y);
            System.out.flush();
            for (int x = 0; x < imageWidth; x++) {
                Color[] samples = new Color[samplesPerPixel];
                for (int i = 0; i < samplesPerPixel; i++) {
                    Ray ray = getRay(x, y);
                    samples[i] = getRayColor(ray, maxDepth, world);
                }

                int index = (y * imageWidth + x) * 3;
                Color.average(samples).writeColor(imageData, index);
            }
        }

        System.out.println("\rDone");
        return imageData;
    }

    /**
     * Initialize the camera for the current settings.
     */
    private void initialize() {
        WHITE.setMutable(false);
        BLACK.setMutable(false);
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
        pixelDU = viewportU.copy().div(imageWidth);
        pixelDV = viewportV.copy().div(imageHeight);
        pixelDU.setMutable(false);
        pixelDV.setMutable(false);

        // Calculate the upper left pixel
        Point3 viewportUpperLeft = cameraCenter
                .copy()
                .sub(new Vector3(0, 0, focalLength))
                .sub(viewportU.div(2))
                .sub(viewportV.div(2));
        Vector3 pixelOffset = pixelDU.copy().add(pixelDV).div(2);
        pixelOrigin = viewportUpperLeft.add(pixelOffset);
        pixelOrigin.setMutable(false);
    }

    /**
     * Get the ray for the provided x and y values
     *
     * @param x The x value of the resulting image
     * @param y The y value of the resulting image
     * @return The ray for the x and y location
     */
    private Ray getRay(final int x, final int y) {
        Vector3 offset = new Vector3(MathUtils.randomDouble() - 0.5,
                MathUtils.randomDouble() - 0.5, 0);

        Point3 pixelCenter = pixelOrigin
                .copy()
                .add(pixelDU.copy().mul(x + offset.getX()))
                .add(pixelDV.copy().mul(y + offset.getY()));
        Vector3 rayDirection = new Vector3(pixelCenter, cameraCenter);
        rayDirection.setMutable(false);
        return new Ray(cameraCenter, rayDirection);
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
            return BLACK.copy();
        }

        Intersection intersect = world.intersect(ray, new Interval(0.001, Double.POSITIVE_INFINITY));
        if (intersect != null) {
            ScatterData scatterData = intersect.getMaterial().scatter(intersect);
            if (scatterData != null) {
                Color scatterColor = getRayColor(scatterData.ray(), depth - 1, world);
                return scatterColor.mul(scatterData.attenuation());
            }
            return BLACK.copy();
        }

        Vector3 unitDir = ray.direction().copy().normalize();
        double a = 0.5 * (unitDir.getY() + 1.0);

        return WHITE.copy().lerp(BLUE, a);
    }
}
