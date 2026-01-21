package com.wombatsw.raytracing.engine;

import com.wombatsw.raytracing.model.Ray;
import com.wombatsw.raytracing.model.Triplet;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Camera object, which also handles driving the rendering process
 */
@Getter
@ToString
public class Camera {
    /**
     * The image aspect ratio
     */
    @Setter
    private double aspectRatio = 1.0;

    /**
     * The image width
     */
    @Setter
    private int imageWidth = 100;

    /**
     * The background color
     */
    @Setter
    private Triplet background = ColorUtils.black();

    /**
     * The location of the camera
     */
    @Setter
    private Triplet cameraCenter = new Triplet(0, 0, 0);

    /**
     * The variation angle in degrees of rays through a pixel
     */
    @Setter
    private double defocusAngle = 0;

    /**
     * Distance from the camera to the place of perfect focus
     */
    @Setter
    private double focusDistance = 10;

    /**
     * The image height
     */
    private int imageHeight;

    /**
     * The viewport that the forms the camera's visible region
     */
    private final Viewport viewport;

    @Getter(AccessLevel.NONE)
    private Triplet defocusDiskU;

    @Getter(AccessLevel.NONE)
    private Triplet defocusDiskV;

    public Camera() {
        this.viewport = new Viewport();
    }

    /**
     * Initialize the camera for the current settings
     */
    public void initialize() {
        cameraCenter.setImmutable();
        imageHeight = Math.max(1, (int) (imageWidth / aspectRatio));
        viewport.initialize(imageWidth, imageHeight, focusDistance, cameraCenter);

        double defocusRadius = focusDistance * Math.tan(Math.toRadians(defocusAngle / 2));

        defocusDiskU = Triplet.newScaled(viewport.getViewBasisU(), defocusRadius);
        defocusDiskV = Triplet.newScaled(viewport.getViewBasisV(), -defocusRadius);
    }

    /**
     * Defocus the image by moving the origin of the ray by a random amount. Will use camera center as-is if defocus
     * is not enabled (defocusAngle <= 0)
     *
     * @param point The point on the viewport
     * @return The defocused ray
     */
    public Ray getRayForPoint(final Triplet point) {
        Triplet rayOrigin;
        if (defocusAngle <= 0) {
            rayOrigin = cameraCenter;
        } else {
            // Defocus the image by moving the origin of the ray by a random amount
            Triplet v = Triplet.randomInUnitDisc();
            rayOrigin = cameraCenter.copy()
                    .addScaled(defocusDiskU, v.getX(), defocusDiskV, v.getY());
        }
        Triplet rayDir = new Triplet(point, rayOrigin);
        double rayTime = MathUtils.randomDouble();

        return new Ray(rayOrigin, rayDir, rayTime);
    }
}
