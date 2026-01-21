package com.wombatsw.raytracing.scene;

import com.wombatsw.raytracing.engine.Camera;
import com.wombatsw.raytracing.engine.Viewport;
import com.wombatsw.raytracing.material.Lambertian;
import com.wombatsw.raytracing.model.Triplet;
import com.wombatsw.raytracing.obj.ObjectList;
import com.wombatsw.raytracing.obj.Sphere;
import com.wombatsw.raytracing.texture.NoiseTexture;
import org.springframework.stereotype.Component;

@Component
public class PerlinSpheresScene implements Scene {
    @Override
    public Camera getCamera() {
        Camera camera = new Camera();
        camera.setCameraCenter(new Triplet(13, 2, 3));
        camera.setDefocusAngle(0);
        camera.setBackground(new Triplet(0.70, 0.80, 1.00));

        Viewport viewport = camera.getViewport();
        viewport.setFieldOfView(20);
        viewport.setViewportCenter(new Triplet(0, 0, 0));
        viewport.setViewUp(new Triplet(0, 1, 0));

        return camera;
    }

    @Override
    public ObjectList getWorld() {
        ObjectList world = new ObjectList();

        NoiseTexture perTexture = new NoiseTexture(4);

        world.add(new Sphere(new Triplet(0, -1000, 0), 1000, new Lambertian(perTexture)));
        world.add(new Sphere(new Triplet(0, 2, 0), 2, new Lambertian(perTexture)));

        return world;
    }
}
