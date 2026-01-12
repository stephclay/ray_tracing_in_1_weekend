package com.wombatsw.raytracing.engine;

import com.wombatsw.raytracing.model.Color;
import com.wombatsw.raytracing.model.Intersection;
import com.wombatsw.raytracing.model.Interval;
import com.wombatsw.raytracing.model.Point3;
import com.wombatsw.raytracing.model.Ray;
import com.wombatsw.raytracing.model.ScatterData;
import com.wombatsw.raytracing.model.Vector3;
import com.wombatsw.raytracing.obj.AbstractObj;
import lombok.Setter;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

public class Renderer {
    private final static Color BLUE = new Color(0.5, 0.7, 1).setImmutable();

    /**
     * The max depth of reflection
     */
    @Setter
    private int maxDepth = 10;

    private AntiAlias antiAlias = new AntiAlias(2, 2);

    /**
     * Set the antialiasing to a random pattern
     *
     * @param randomSamples The number of samples
     */
    public void setAntialiasRandom(final int randomSamples) {
        this.antiAlias = new AntiAlias(randomSamples);
    }

    /**
     * Set the antialiasing to a grid pattern
     *
     * @param xSamples The number of samples along the width
     * @param ySamples The number of samples along the height
     */
    public void setAntialiasGrid(final int xSamples, final int ySamples) {
        this.antiAlias = new AntiAlias(xSamples, ySamples);
    }

    /**
     * Render the scene for the provided world data
     *
     * @param world  The world data
     * @param camera The {@link Camera}
     * @return The image raster of size "width x height x 3" as a row-major ordering of RGB triplets
     */
    public byte[] render(final AbstractObj world, final Camera camera) {
        camera.initialize();
        antiAlias.initialize(camera.getViewport());

        ExecutorService executor = Executors.newWorkStealingPool();
        final int width = camera.getImageWidth();
        final int height = camera.getImageHeight();

        long start = System.currentTimeMillis();
        byte[] imageData = new byte[width * height * 3];
        for (int y = 0; y < height; y++) {
            ProgressInfo.displayProgress(start, y, height);

            // Mix the rows up to give a better estimation of time remaining. The first few rows
            // frequently run faster due to ray hitting the "sky", throwing off the estimation.
            int yAlt = (y * 65537) % height;
            Color[] row = renderRow(executor, world, camera, yAlt);

            int rowIndex = yAlt * width * 3;
            for (int x = 0; x < width; x++) {
                row[x].writeColor(imageData, rowIndex + x * 3);
            }
        }

        ProgressInfo.displayCompletionMessage(start);
        return imageData;
    }

    /**
     * Render a row of the image
     *
     * @param executor The executor service
     * @param world    The world data
     * @param camera   The {@link Camera}
     * @param y        The row to render
     */
    private Color[] renderRow(final ExecutorService executor, final AbstractObj world, final Camera camera, final int y) {
        Color[] row = new Color[camera.getImageWidth()];

        for (int x = 0; x < row.length; x++) {
            row[x] = getPixelColor(executor, world, camera, x, y);
        }

        return row;
    }

    /**
     * Get the pixel color for the specified raster location
     *
     * @param executor The executor service. Use {@code nulll} to execute synchronously
     * @param world    The world data
     * @param camera   The {@link Camera}
     * @param x        The raster x coordinate
     * @param y        The raster y coordinate
     * @return The color of the pixel
     */
    private Color getPixelColor(final ExecutorService executor, final AbstractObj world, final Camera camera,
                                final int x, final int y) {
        List<Point3> viewportPoints = antiAlias.getSamplingPoints(x, y);

        List<Color> samples = viewportPoints.stream()
                .parallel()
                .map(camera::getRayForPoint)
                .map(ray -> getColorAsync(executor, world, ray))
                .map(CompletableFuture::join)
                .toList();

        return Color.average(samples);
    }

    /**
     * Asynchronously get the color for the given ray
     *
     * @param executor The executor service. Use {@code nulll} to execute synchronously
     * @param world    The world data
     * @param ray      The ray corresponding to the color
     * @return A {@link CompletableFuture} returning a {@link Color}
     */
    private CompletableFuture<Color> getColorAsync(final ExecutorService executor, final AbstractObj world,
                                                   final Ray ray) {
        Supplier<Color> supplier = () -> getRayColor(ray, maxDepth, world);
        return executor == null ?
                CompletableFuture.completedFuture(supplier.get()) :
                CompletableFuture.supplyAsync(supplier, executor);
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
