package com.wombatsw.raytracing.scene;

import com.wombatsw.raytracing.engine.Camera;
import com.wombatsw.raytracing.engine.Viewport;
import com.wombatsw.raytracing.material.Lambertian;
import com.wombatsw.raytracing.material.Material;
import com.wombatsw.raytracing.model.Triplet;
import com.wombatsw.raytracing.obj.ObjectList;
import com.wombatsw.raytracing.obj.Quad;
import org.springframework.stereotype.Component;

@Component
public class QuadsScene implements Scene {
    @Override
    public Camera getCamera() {
        Camera camera = new Camera();
        camera.setCameraCenter(new Triplet(0, 0, 9));
        camera.setDefocusAngle(0);
        camera.setBackground(new Triplet(0.70, 0.80, 1.00));

        Viewport viewport = camera.getViewport();
        viewport.setFieldOfView(80);
        viewport.setViewportCenter(new Triplet(0, 0, 0));
        viewport.setViewUp(new Triplet(0, 1, 0));

        return camera;
    }

    @Override
    public ObjectList getWorld() {
        // Materials
        Material leftRed = new Lambertian(new Triplet(1.0, 0.2, 0.2));
        Material backGreen = new Lambertian(new Triplet(0.2, 1.0, 0.2));
        Material rightBlue = new Lambertian(new Triplet(0.2, 0.2, 1.0));
        Material upperOrange = new Lambertian(new Triplet(1.0, 0.5, 0.0));
        Material lowerTeal = new Lambertian(new Triplet(0.2, 0.8, 0.8));

        // Quads
        return new ObjectList(
                new Quad(new Triplet(-3, -2, 5), new Triplet(0, 0, -4), new Triplet(0, 4, 0), leftRed),
                new Quad(new Triplet(-2, -2, 0), new Triplet(4, 0, 0), new Triplet(0, 4, 0), backGreen),
                new Quad(new Triplet(3, -2, 1), new Triplet(0, 0, 4), new Triplet(0, 4, 0), rightBlue),
                new Quad(new Triplet(-2, 3, 1), new Triplet(4, 0, 0), new Triplet(0, 0, 4), upperOrange),
                new Quad(new Triplet(-2, -3, 5), new Triplet(4, 0, 0), new Triplet(0, 0, -4), lowerTeal));
    }
}
