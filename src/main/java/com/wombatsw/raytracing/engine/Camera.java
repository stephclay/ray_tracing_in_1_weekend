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
    /**
     * The field of view
     */
    @Setter
    private double fieldOfView = 90;
    /**
     * The location of the camera
     */
    @Setter
    private Point3 cameraCenter = new Point3(0, 0, 0);
    /**
     * The center of the viewport, which is where the camera is looking
     */
    @Setter
    private Point3 viewportCenter = new Point3(0, 0, -1);
    /**
     * The camera-relative Up direction
     */
    @Setter
    private Vector3 viewUp = new Vector3(0, 1, 0);
    /**
     * The variation angle of rays through a pixel
     */
    @Setter
    private double defocusAngle = 0;
    /**
     * Distance from the camera to the place of perfect focus
     */
    @Setter
    private double focusDistance = 10;

    private Vector3 defocusDiskU;
    private Vector3 defocusDiskV;

    /**
     * Initialize the camera for the current settings.
     */
    private void initialize() {
        BLUE.setMutable(false);
        cameraCenter.setMutable(false);

        imageHeight = Math.max(1, (int) (imageWidth / aspectRatio));


        // Viewport dimensions
        Vector3 cameraDirection = new Vector3(cameraCenter, viewportCenter);
        double h = Math.tan(Math.toRadians(fieldOfView) / 2);
        double viewportHeight = 2.0 * h * focusDistance;
        double viewportWidth = viewportHeight * (double) imageWidth / (double) imageHeight;

        // Calculate basis vectors for the camera coordinate frame
        Vector3 w = cameraDirection.normalize();
        Vector3 u = viewUp.cross(w).normalize();
        Vector3 v = w.cross(u).normalize();

        // Calculate vectors across the horizontal and vertical viewport edges
        Vector3 viewportU = u.copy().mul(viewportWidth);
        Vector3 viewportV = v.copy().mul(-viewportHeight);

        // Calculate the horizontal and vertical deltas from pixel to pixel
        Vector3 pixelDU = viewportU.copy().div(imageWidth);
        Vector3 pixelDV = viewportV.copy().div(imageHeight);
        pixelDU.setMutable(false);
        pixelDV.setMutable(false);

        // Calculate the upper left pixel
        Point3 viewportUpperLeft = cameraCenter
                .copy()
                .sub(w.copy().mul(focusDistance))
                .sub(viewportU.div(2))
                .sub(viewportV.div(2));
        Vector3 pixelOffset = pixelDU.copy().add(pixelDV).div(2);
        Point3 pixelOrigin = viewportUpperLeft.add(pixelOffset);
        pixelOrigin.setMutable(false);

        double defocusRadius = focusDistance * Math.tan(Math.toRadians(defocusAngle / 2));
        defocusDiskU = u.copy().mul(defocusRadius);
        defocusDiskV = v.copy().mul(-defocusRadius);

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
            ProgressInfo.displayRemainingRowsMessage(start, y, imageHeight);

            Color[] row = renderRow(world, y);

            int rowIndex = y * imageWidth * 3;
            for (int x = 0; x < imageWidth; x++) {
                row[x].writeColor(imageData, rowIndex + x * 3);
            }
        }

        ProgressInfo.displayCompletionMessage(start);
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
            Ray ray = defocus(viewportPoints[i]);
            samples[i] = getRayColor(ray, maxDepth, world);
        }

        return Color.average(samples);
    }

    /**
     * Defocus the image by moving the origin of the ray by a random amount. Will use camera center as-is if defocus
     * is not enabled (defocusAngle <= 0)
     *
     * @param samplePoint The point on the viewport
     * @return The defocused ray
     */
    private Ray defocus(Point3 samplePoint) {
        Point3 rayOrigin;
        if (defocusAngle <= 0) {
            rayOrigin = cameraCenter;
        } else {
            Vector3 v = Vector3.randomInUnitDisk();
            rayOrigin = cameraCenter.copy()
                    .add(defocusDiskU.copy().mul(v.getX()))
                    .add(defocusDiskV.copy().mul(v.getY()));
            rayOrigin.setMutable(false);
        }
        Vector3 rayDir = new Vector3(samplePoint, rayOrigin);
        rayDir.setMutable(false);

        return new Ray(rayOrigin, rayDir);
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
