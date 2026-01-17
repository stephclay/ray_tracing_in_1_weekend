package com.wombatsw.raytracing.scene;

import com.wombatsw.raytracing.engine.Camera;
import com.wombatsw.raytracing.material.Lambertian;
import com.wombatsw.raytracing.material.Material;
import com.wombatsw.raytracing.model.Vector3;
import com.wombatsw.raytracing.obj.ObjectList;
import com.wombatsw.raytracing.obj.Sphere;
import org.springframework.stereotype.Component;

@Component
public class TwoSpheresScene implements Scene {
    @Override
    public Camera getCamera() {
        return new Camera();
    }

    @Override
    public ObjectList getWorld() {
        Material matLeft = new Lambertian(new Vector3(0, 0, 1));
        Material matRight = new Lambertian(new Vector3(1, 0, 0));
        double r = Math.cos(Math.PI / 4.0);

        return new ObjectList(
                new Sphere(new Vector3(-r, 0, -1), r, matLeft),
                new Sphere(new Vector3(r, 0, -1), r, matRight)
        );
    }
}
