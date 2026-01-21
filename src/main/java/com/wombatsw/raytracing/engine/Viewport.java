package com.wombatsw.raytracing.engine;

import com.wombatsw.raytracing.model.Triplet;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
public class Viewport {
    /**
     * The field of view
     */
    @Setter
    private double fieldOfView = 90;

    /**
     * The center of the viewport, which is where the camera is looking
     */
    @Setter
    private Triplet viewportCenter = new Triplet(0, 0, -1);

    /**
     * The camera-relative Up direction
     */
    @Setter
    private Triplet viewUp = new Triplet(0, 1, 0);

    /**
     * Viewport basis vector in the X direction. Points to the right (facing viewport)
     */
    private Triplet viewBasisU;

    /**
     * Viewport basis vector in the Y direction. Points up
     */
    private Triplet viewBasisV;

    /**
     * Viewport basis vector in the Z direction. Points towards camera
     */
    private Triplet viewBasisW;

    /**
     * The distance between pixels in the X direction
     */
    private Triplet pixelDU;

    /**
     * The distance between pixels in the Y direction
     */
    private Triplet pixelDV;

    /**
     * The upper left pixel of the viewport
     */
    private Triplet pixelOrigin;

    /**
     * Initialize the camera for the current settings.
     */
    public void initialize(final int imageWidth, final int imageHeight, final double focusDistance,
                           final Triplet cameraCenter) {
        // Viewport dimensions
        double h = Math.tan(Math.toRadians(fieldOfView) / 2);
        double viewportHeight = 2.0 * h * focusDistance;
        double viewportWidth = viewportHeight * (double) imageWidth / (double) imageHeight;

        // Calculate basis vectors for the camera coordinate frame
        Triplet cameraDirection = new Triplet(cameraCenter, viewportCenter);
        viewBasisW = cameraDirection.normalize().setImmutable();
        viewBasisU = viewUp.cross(viewBasisW).normalize().setImmutable();
        viewBasisV = viewBasisW.cross(viewBasisU).normalize().setImmutable();

        // Calculate vectors across the horizontal and vertical viewport edges
        Triplet viewportU = Triplet.newScaled(viewBasisU, viewportWidth);
        Triplet viewportV = Triplet.newScaled(viewBasisV, -viewportHeight);

        // Calculate the horizontal and vertical deltas from pixel to pixel
        pixelDU = Triplet.newScaled(viewportU, 1.0 / imageWidth).setImmutable();
        pixelDV = Triplet.newScaled(viewportV, 1.0 / imageHeight).setImmutable();

        // Calculate the upper left pixel
        Triplet viewportUpperLeft = cameraCenter
                .copy()
                .addScaled(viewBasisW, -focusDistance)
                .addScaled(viewportU, -0.5)
                .addScaled(viewportV, -0.5);
        Triplet pixelOffset = Triplet.newScaled(pixelDU, 0.5).addScaled(pixelDV, 0.5);
        pixelOrigin = viewportUpperLeft.add(pixelOffset).setImmutable();
    }
}
