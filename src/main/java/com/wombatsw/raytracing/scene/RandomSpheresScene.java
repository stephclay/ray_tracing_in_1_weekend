package com.wombatsw.raytracing.scene;

import com.wombatsw.raytracing.engine.Camera;
import com.wombatsw.raytracing.engine.MathUtils;
import com.wombatsw.raytracing.engine.Viewport;
import com.wombatsw.raytracing.material.Dielectric;
import com.wombatsw.raytracing.material.Lambertian;
import com.wombatsw.raytracing.material.Material;
import com.wombatsw.raytracing.material.Metal;
import com.wombatsw.raytracing.model.Vector3;
import com.wombatsw.raytracing.obj.ObjectList;
import com.wombatsw.raytracing.obj.Sphere;
import com.wombatsw.raytracing.texture.CheckerTexture;
import org.springframework.stereotype.Component;

@Component
public class RandomSpheresScene implements Scene {
    @Override
    public Camera getCamera() {
        Camera camera = new Camera();
        camera.setDefocusAngle(0.6);
        camera.setFocusDistance(10.0);
        camera.setCameraCenter(new Vector3(13, 2, 3));
        camera.setBackground(new Vector3(0.70, 0.80, 1.00));

        Viewport viewport = camera.getViewport();
        viewport.setFieldOfView(20);
        viewport.setViewportCenter(new Vector3(0, 0, 0));
        viewport.setViewUp(new Vector3(0, 1, 0));

        return camera;
    }

    @Override
    public ObjectList getWorld() {
        ObjectList world = new ObjectList();

        CheckerTexture checkered = new CheckerTexture(0.32, new Vector3(.2, .3, .1),
                new Vector3(.9, .9, .9));
        Material matGround = new Lambertian(checkered);
        world.add(new Sphere(new Vector3(0, -1000, 0), 1000, matGround));

        for (int a = -11; a < 11; a++) {
            for (int b = -11; b < 11; b++) {
                double selection = MathUtils.randomDouble();
                Vector3 center = new Vector3(a + 0.9 * MathUtils.randomDouble(),
                        0.2, b + 0.9 * MathUtils.randomDouble());

                if (center.copy().sub(new Vector3(4, 0.2, 0)).len() < 0.9) {
                    continue;
                }
                if (selection < 0.8) {
                    // diffuse
                    Vector3 albedo = Vector3.random().mul(Vector3.random());
                    Material material = new Lambertian(albedo);
                    Vector3 center2 = new Vector3(0, MathUtils.randomDouble(0, 0.5), 0).add(center);
                    world.add(new Sphere(center, center2, 0.2, material));
                } else if (selection < 0.95) {
                    // metal
                    Vector3 albedo = Vector3.random(0.5, 1);
                    double fuzz = MathUtils.randomDouble(0, 0.5);
                    Material material = new Metal(albedo, fuzz);
                    world.add(new Sphere(center, 0.2, material));
                } else {
                    // glass
                    Material material = new Dielectric(1.5);
                    world.add(new Sphere(center, 0.2, material));
                }
            }
        }

        Material material1 = new Dielectric(1.5);
        world.add(new Sphere(new Vector3(0, 1, 0), 1.0, material1));

        Material material2 = new Lambertian(new Vector3(0.4, 0.2, 0.1));
        world.add(new Sphere(new Vector3(-4, 1, 0), 1.0, material2));

        Material material3 = new Metal(new Vector3(0.7, 0.6, 0.5), 0.0);
        world.add(new Sphere(new Vector3(4, 1, 0), 1.0, material3));

        return world;
    }
}
