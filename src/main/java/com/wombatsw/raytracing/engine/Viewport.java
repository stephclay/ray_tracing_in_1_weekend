package com.wombatsw.raytracing.engine;

import com.wombatsw.raytracing.model.Point3;
import com.wombatsw.raytracing.model.Vector3;
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
    private Point3 viewportCenter = new Point3(0, 0, -1);

    /**
     * The camera-relative Up direction
     */
    @Setter
    private Vector3 viewUp = new Vector3(0, 1, 0);

    /**
     * Viewport basis vector in the X direction. Points to the right (facing viewport)
     */
    private Vector3 viewBasisU;

    /**
     * Viewport basis vector in the Y direction. Points up
     */
    private Vector3 viewBasisV;

    /**
     * Viewport basis vector in the Z direction. Points towards camera
     */
    private Vector3 viewBasisW;

    /**
     * The distance between pixels in the X direction
     */
    private Vector3 pixelDU;

    /**
     * The distance between pixels in the Y direction
     */
    private Vector3 pixelDV;

    /**
     * The upper left pixel of the viewport
     */
    private Point3 pixelOrigin;

    /**
     * Initialize the camera for the current settings.
     */
    public void initialize(final int imageWidth, final int imageHeight, final double focusDistance, final Point3 cameraCenter) {
        // Viewport dimensions
        double h = Math.tan(Math.toRadians(fieldOfView) / 2);
        double viewportHeight = 2.0 * h * focusDistance;
        double viewportWidth = viewportHeight * (double) imageWidth / (double) imageHeight;

        // Calculate basis vectors for the camera coordinate frame
        Vector3 cameraDirection = new Vector3(cameraCenter, viewportCenter);
        viewBasisW = cameraDirection.normalize().setImmutable();
        viewBasisU = viewUp.cross(viewBasisW).normalize().setImmutable();
        viewBasisV = viewBasisW.cross(viewBasisU).normalize().setImmutable();

        // Calculate vectors across the horizontal and vertical viewport edges
        Vector3 viewportU = Vector3.newScaled(viewBasisU, viewportWidth);
        Vector3 viewportV = Vector3.newScaled(viewBasisV, -viewportHeight);

        // Calculate the horizontal and vertical deltas from pixel to pixel
        pixelDU = Vector3.newScaled(viewportU, 1.0/imageWidth).setImmutable();
        pixelDV = Vector3.newScaled(viewportV, 1.0/imageHeight).setImmutable();

        // Calculate the upper left pixel
        Point3 viewportUpperLeft = cameraCenter
                .copy()
                .addScaled(viewBasisW, -focusDistance)
                .addScaled(viewportU, -0.5)
                .addScaled(viewportV, -0.5);
        Vector3 pixelOffset = Vector3.newScaled(pixelDU, 0.5).addScaled(pixelDV, 0.5);
        pixelOrigin = viewportUpperLeft.add(pixelOffset).setImmutable();
    }
}
