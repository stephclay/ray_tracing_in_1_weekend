package com.wombatsw.raytracing.scene;

import com.wombatsw.raytracing.engine.Camera;
import com.wombatsw.raytracing.engine.Viewport;
import com.wombatsw.raytracing.material.Lambertian;
import com.wombatsw.raytracing.material.Material;
import com.wombatsw.raytracing.model.Triplet;
import com.wombatsw.raytracing.obj.ObjectList;
import com.wombatsw.raytracing.obj.Sphere;
import com.wombatsw.raytracing.texture.ImageTexture;
import com.wombatsw.raytracing.texture.Texture;
import org.springframework.stereotype.Component;

@Component
public class EarthScene implements Scene {
    @Override
    public Camera getCamera() {
        Camera camera = new Camera();
        camera.setCameraCenter(new Triplet(0, 0, 12));
        camera.setBackground(new Triplet(0.70, 0.80, 1.00));

        Viewport viewport = camera.getViewport();
        viewport.setFieldOfView(20);
        viewport.setViewportCenter(new Triplet(0, 0, 0));
        viewport.setViewUp(new Triplet(0, 1, 0));

        return camera;
    }

    @Override
    public ObjectList getWorld() {
        Texture earthTexture = new ImageTexture("/earthmap.jpg");
        Material earthSurface = new Lambertian(earthTexture);

        return new ObjectList(
                new Sphere(new Triplet(0, 0, 0), 2, earthSurface)
        );
    }
}
