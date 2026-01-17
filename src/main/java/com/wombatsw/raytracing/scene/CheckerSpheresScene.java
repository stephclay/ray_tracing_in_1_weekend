package com.wombatsw.raytracing.scene;

import com.wombatsw.raytracing.engine.Camera;
import com.wombatsw.raytracing.engine.Viewport;
import com.wombatsw.raytracing.material.Lambertian;
import com.wombatsw.raytracing.model.Vector3;
import com.wombatsw.raytracing.obj.ObjectList;
import com.wombatsw.raytracing.obj.Sphere;
import com.wombatsw.raytracing.texture.CheckerTexture;
import org.springframework.stereotype.Component;

@Component
public class CheckerSpheresScene implements Scene {
    @Override
    public Camera getCamera() {
        Camera camera = new Camera();
        camera.setCameraCenter(new Vector3(13, 2, 3));
        camera.setDefocusAngle(0);

        Viewport viewport = camera.getViewport();
        viewport.setFieldOfView(20);
        viewport.setViewportCenter(new Vector3(0, 0, 0));
        viewport.setViewUp(new Vector3(0, 1, 0));

        return camera;
    }

    @Override
    public ObjectList getWorld() {
        ObjectList world = new ObjectList();

        CheckerTexture checker = new CheckerTexture(0.32, new Vector3(.2, .3, .1),
                new Vector3(.9, .9, .9));

        world.add(new Sphere(new Vector3(0, -10, 0), 10, new Lambertian(checker)));
        world.add(new Sphere(new Vector3(0, 10, 0), 10, new Lambertian(checker)));

        return world;
    }
}
