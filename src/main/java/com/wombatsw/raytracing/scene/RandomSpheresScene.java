package com.wombatsw.raytracing.scene;

import com.wombatsw.raytracing.engine.MathUtils;
import com.wombatsw.raytracing.material.Dielectric;
import com.wombatsw.raytracing.material.Lambertian;
import com.wombatsw.raytracing.material.Material;
import com.wombatsw.raytracing.material.Metal;
import com.wombatsw.raytracing.model.Color;
import com.wombatsw.raytracing.model.Point3;
import com.wombatsw.raytracing.obj.ObjectList;
import com.wombatsw.raytracing.obj.Sphere;
import com.wombatsw.raytracing.texture.CheckerTexture;
import org.springframework.stereotype.Component;

@Component
public class RandomSpheresScene implements Scene {
    @Override
    public ObjectList getWorld() {
        ObjectList world = new ObjectList();

        CheckerTexture checkered = new CheckerTexture(0.32, new Color(.2, .3, .1),
                new Color(.9, .9, .9));
        Material matGround = new Lambertian(checkered);
        world.add(new Sphere(new Point3(0, -1000, 0), 1000, matGround));

        for (int a = -11; a < 11; a++) {
            for (int b = -11; b < 11; b++) {
                double selection = MathUtils.randomDouble();
                Point3 center = new Point3(a + 0.9 * MathUtils.randomDouble(),
                        0.2, b + 0.9 * MathUtils.randomDouble());

                if (center.copy().sub(new Point3(4, 0.2, 0)).len() < 0.9) {
                    continue;
                }
                if (selection < 0.8) {
                    // diffuse
                    Color albedo = Color.random().mul(Color.random());
                    Material material = new Lambertian(albedo);
                    Point3 center2 = new Point3(0, MathUtils.randomDouble(0, 0.5), 0).add(center);
                    world.add(new Sphere(center, center2, 0.2, material));
                } else if (selection < 0.95) {
                    // metal
                    Color albedo = Color.random(0.5, 1);
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
        world.add(new Sphere(new Point3(0, 1, 0), 1.0, material1));

        Material material2 = new Lambertian(new Color(0.4, 0.2, 0.1));
        world.add(new Sphere(new Point3(-4, 1, 0), 1.0, material2));

        Material material3 = new Metal(new Color(0.7, 0.6, 0.5), 0.0);
        world.add(new Sphere(new Point3(4, 1, 0), 1.0, material3));

        return world;
    }
}
