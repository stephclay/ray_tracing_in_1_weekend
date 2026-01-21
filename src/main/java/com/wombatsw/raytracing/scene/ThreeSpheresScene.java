package com.wombatsw.raytracing.scene;

import com.wombatsw.raytracing.engine.Camera;
import com.wombatsw.raytracing.engine.Viewport;
import com.wombatsw.raytracing.material.Dielectric;
import com.wombatsw.raytracing.material.Lambertian;
import com.wombatsw.raytracing.material.Material;
import com.wombatsw.raytracing.material.Metal;
import com.wombatsw.raytracing.model.Triplet;
import com.wombatsw.raytracing.obj.ObjectList;
import com.wombatsw.raytracing.obj.Sphere;
import org.springframework.stereotype.Component;

@Component
public class ThreeSpheresScene implements Scene {
    @Override
    public Camera getCamera() {
        Camera camera = new Camera();
        camera.setCameraCenter(new Triplet(-2, 2, 1));
        camera.setBackground(new Triplet(0.70, 0.80, 1.00));

        Viewport viewport = camera.getViewport();
        viewport.setFieldOfView(20);
        viewport.setViewportCenter(new Triplet(0, 0, -1));
        viewport.setViewUp(new Triplet(0, 1, 0));

        return camera;
    }

    @Override
    public ObjectList getWorld() {
        Material matGround = new Lambertian(new Triplet(0.8, 0.8, 0.0)); // yellow
        Material matCenter = new Lambertian(new Triplet(0.1, 0.2, 0.5)); // blue
        Material matLeft = new Dielectric(1.5); // transparent
        Material matBubble = new Dielectric(1.0 / 1.5); // bubble
        Material matRight = new Metal(new Triplet(0.8, 0.6, 0.2), 1.0); // slightly red-ish yellow

        return new ObjectList(
                new Sphere(new Triplet(0, -100.5, -1), 100, matGround),
                new Sphere(new Triplet(0, 0, -1.2), 0.5, matCenter),
                new Sphere(new Triplet(-1, 0, -1), 0.5, matLeft),
                new Sphere(new Triplet(-1, 0, -1), 0.4, matBubble),
                new Sphere(new Triplet(1, 0, -1), 0.5, matRight)
        );
    }
}
