package com.wombatsw.raytracing.scene;

import com.wombatsw.raytracing.engine.Camera;
import com.wombatsw.raytracing.engine.Viewport;
import com.wombatsw.raytracing.material.Lambertian;
import com.wombatsw.raytracing.material.Material;
import com.wombatsw.raytracing.model.Vector3;
import com.wombatsw.raytracing.obj.ObjectList;
import com.wombatsw.raytracing.obj.Quad;
import org.springframework.stereotype.Component;

@Component
public class QuadsScene implements Scene {
    @Override
    public Camera getCamera() {
        Camera camera = new Camera();
        camera.setCameraCenter(new Vector3(0, 0, 9));
        camera.setDefocusAngle(0);

        Viewport viewport = camera.getViewport();
        viewport.setFieldOfView(80);
        viewport.setViewportCenter(new Vector3(0, 0, 0));
        viewport.setViewUp(new Vector3(0, 1, 0));

        return camera;
    }

    @Override
    public ObjectList getWorld() {
        ObjectList world = new ObjectList();

        // Materials
        Material leftRed     = new Lambertian(new Vector3(1.0, 0.2, 0.2));
        Material backGreen   = new Lambertian(new Vector3(0.2, 1.0, 0.2));
        Material rightBlue   = new Lambertian(new Vector3(0.2, 0.2, 1.0));
        Material upperOrange = new Lambertian(new Vector3(1.0, 0.5, 0.0));
        Material lowerTeal   = new Lambertian(new Vector3(0.2, 0.8, 0.8));

        // Quads
        world.add(new Quad(new Vector3(-3,-2, 5), new Vector3(0, 0,-4), new Vector3(0, 4, 0), leftRed));
        world.add(new Quad(new Vector3(-2,-2, 0), new Vector3(4, 0, 0), new Vector3(0, 4, 0), backGreen));
        world.add(new Quad(new Vector3( 3,-2, 1), new Vector3(0, 0, 4), new Vector3(0, 4, 0), rightBlue));
        world.add(new Quad(new Vector3(-2, 3, 1), new Vector3(4, 0, 0), new Vector3(0, 0, 4), upperOrange));
        world.add(new Quad(new Vector3(-2,-3, 5), new Vector3(4, 0, 0), new Vector3(0, 0,-4), lowerTeal));

        return world;
    }
}
