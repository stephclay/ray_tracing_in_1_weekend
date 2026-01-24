package com.wombatsw.raytracing.scene.generators;

import com.wombatsw.raytracing.engine.Camera;
import com.wombatsw.raytracing.engine.MathUtils;
import com.wombatsw.raytracing.engine.Viewport;
import com.wombatsw.raytracing.material.Dielectric;
import com.wombatsw.raytracing.material.Lambertian;
import com.wombatsw.raytracing.material.Material;
import com.wombatsw.raytracing.material.Metal;
import com.wombatsw.raytracing.model.Triplet;
import com.wombatsw.raytracing.obj.AbstractObj;
import com.wombatsw.raytracing.obj.ObjectList;
import com.wombatsw.raytracing.obj.Sphere;
import com.wombatsw.raytracing.scene.Scene;
import com.wombatsw.raytracing.texture.CheckerTexture;
import com.wombatsw.raytracing.texture.SolidColor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class RandomSpheresScene implements Scene {
    @Override
    public Camera getCamera() {
        Camera camera = new Camera();
        camera.setDefocusAngle(0.6);
        camera.setFocusDistance(10.0);
        camera.setCameraCenter(new Triplet(13, 2, 3));
        camera.setBackground(new Triplet(0.70, 0.80, 1.00));

        Viewport viewport = camera.getViewport();
        viewport.setFieldOfView(20);
        viewport.setViewportCenter(new Triplet(0, 0, 0));
        viewport.setViewUp(new Triplet(0, 1, 0));

        return camera;
    }

    @Override
    public ObjectList getWorld() {
        ArrayList<AbstractObj> list = new ArrayList<>();

        CheckerTexture checkered = new CheckerTexture(0.32,
                new SolidColor(new Triplet(.2, .3, .1)),
                new SolidColor(new Triplet(.9, .9, .9)));
        Material matGround = new Lambertian(checkered);
        list.add(new Sphere(new Triplet(0, -1000, 0), 1000, matGround));

        for (int a = -11; a < 11; a++) {
            for (int b = -11; b < 11; b++) {
                double selection = MathUtils.randomDouble();
                Triplet center = new Triplet(a + 0.9 * MathUtils.randomDouble(),
                        0.2, b + 0.9 * MathUtils.randomDouble());

                if (center.copy().sub(new Triplet(4, 0.2, 0)).len() < 0.9) {
                    continue;
                }
                if (selection < 0.8) {
                    // diffuse
                    Triplet albedo = Triplet.random().mul(Triplet.random());
                    Material material = new Lambertian(albedo);
                    Triplet center2 = new Triplet(0, MathUtils.randomDouble(0, 0.5), 0).add(center);
                    list.add(new Sphere(center, center2, 0.2, material));
                } else if (selection < 0.95) {
                    // metal
                    Triplet albedo = Triplet.random(0.5, 1);
                    double fuzz = MathUtils.randomDouble(0, 0.5);
                    Material material = new Metal(albedo, fuzz);
                    list.add(new Sphere(center, 0.2, material));
                } else {
                    // glass
                    Material material = new Dielectric(1.5);
                    list.add(new Sphere(center, 0.2, material));
                }
            }
        }

        Material material1 = new Dielectric(1.5);
        list.add(new Sphere(new Triplet(0, 1, 0), 1.0, material1));

        Material material2 = new Lambertian(new Triplet(0.4, 0.2, 0.1));
        list.add(new Sphere(new Triplet(-4, 1, 0), 1.0, material2));

        Material material3 = new Metal(new Triplet(0.7, 0.6, 0.5), 0.0);
        list.add(new Sphere(new Triplet(4, 1, 0), 1.0, material3));

        return new ObjectList(list);
    }
}
