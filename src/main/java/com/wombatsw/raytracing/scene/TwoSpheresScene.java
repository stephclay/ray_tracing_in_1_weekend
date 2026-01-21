package com.wombatsw.raytracing.scene;

import com.wombatsw.raytracing.engine.Camera;
import com.wombatsw.raytracing.material.Lambertian;
import com.wombatsw.raytracing.material.Material;
import com.wombatsw.raytracing.model.Triplet;
import com.wombatsw.raytracing.obj.ObjectList;
import com.wombatsw.raytracing.obj.Sphere;
import org.springframework.stereotype.Component;

@Component
public class TwoSpheresScene implements Scene {
    @Override
    public Camera getCamera() {
        Camera camera = new Camera();
        camera.setBackground(new Triplet(0.70, 0.80, 1.00));

        return camera;
    }

    @Override
    public ObjectList getWorld() {
        Material matLeft = new Lambertian(new Triplet(0, 0, 1));
        Material matRight = new Lambertian(new Triplet(1, 0, 0));
        double r = Math.cos(Math.PI / 4.0);

        return new ObjectList(
                new Sphere(new Triplet(-r, 0, -1), r, matLeft),
                new Sphere(new Triplet(r, 0, -1), r, matRight)
        );
    }
}
