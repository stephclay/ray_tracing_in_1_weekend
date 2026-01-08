package com.wombatsw.raytracing;

import com.wombatsw.raytracing.engine.AntiAlias;
import com.wombatsw.raytracing.engine.Camera;
import com.wombatsw.raytracing.engine.MathUtils;
import com.wombatsw.raytracing.material.Dielectric;
import com.wombatsw.raytracing.material.Lambertian;
import com.wombatsw.raytracing.material.Material;
import com.wombatsw.raytracing.material.Metal;
import com.wombatsw.raytracing.model.Color;
import com.wombatsw.raytracing.model.Point3;
import com.wombatsw.raytracing.model.Vector3;
import com.wombatsw.raytracing.obj.AbstractObj;
import com.wombatsw.raytracing.obj.ObjectList;
import com.wombatsw.raytracing.obj.Sphere;
import com.wombatsw.raytracing.output.ImageWriter;
import com.wombatsw.raytracing.output.PPMImageWriter;
import org.jspecify.annotations.NonNull;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * Ray Tracer command line application
 */
@Component
public class RayTracerApp implements CommandLineRunner {
    @Override
    public void run(final String @NonNull ... args) throws Exception {
        Camera camera = new Camera();
        camera.setAspectRatio(16.0 / 9.0);
        camera.setImageWidth(1200);
        camera.setAntiAlias(new AntiAlias(500));
        camera.setMaxDepth(50);

        camera.setFieldOfView(20);
        camera.setCameraCenter(new Point3(13, 2, 3));
        camera.setViewportCenter(new Point3(0, 0, 0));
        camera.setViewUp(new Vector3(0, 1, 0));

        camera.setDefocusAngle(0.6);
        camera.setFocusDistance(10.0);

        AbstractObj world = selectWorld(args);

        byte[] imageData = camera.render(world);

        ImageWriter writer = new PPMImageWriter();
        writer.write(new File("test.ppm"), camera.getImageWidth(), camera.getImageHeight(), imageData);
    }

    private AbstractObj selectWorld(final String... args) {
        String selection = args.length > 0 ? args[0] : "world4";

        return switch (selection) {
            case "world1" -> getWorld1();
            case "world2" -> getWorld2();
            case "world3" -> getWorld3();
            case "world4" -> getWorld4();
            default -> throw new IllegalArgumentException("Unknown argument: " + selection);
        };
    }

    /**
     * Create the world data
     *
     * @return The world data
     */
    private AbstractObj getWorld1() {
        Material matGrey = new Lambertian(new Color(0.5, 0.5, 0.5));

        return new ObjectList(
                new Sphere(new Point3(0, 0, -1), 0.5, matGrey),
                new Sphere(new Point3(0, -100.5, -1), 100, matGrey)
        );
    }

    /**
     * Create the world data
     *
     * @return The world data
     */
    private AbstractObj getWorld2() {
        Material matGround = new Lambertian(new Color(0.8, 0.8, 0.0)); // yellow
        Material matCenter = new Lambertian(new Color(0.1, 0.2, 0.5)); // blue
        Material matLeft = new Dielectric(1.5); // transparent
        Material matBubble = new Dielectric(1.0 / 1.5); // bubble
        Material matRight = new Metal(new Color(0.8, 0.6, 0.2), 1.0); // slightly red-ish yellow

        return new ObjectList(
                new Sphere(new Point3(0, -100.5, -1), 100, matGround),
                new Sphere(new Point3(0, 0, -1.2), 0.5, matCenter),
                new Sphere(new Point3(-1, 0, -1), 0.5, matLeft),
                new Sphere(new Point3(-1, 0, -1), 0.4, matBubble),
                new Sphere(new Point3(1, 0, -1), 0.5, matRight)
        );
    }

    /**
     * Create the world data
     *
     * @return The world data
     */
    private AbstractObj getWorld3() {
        Material matLeft = new Lambertian(new Color(0, 0, 1));
        Material matRight = new Lambertian(new Color(1, 0, 0));
        double r = Math.cos(Math.PI / 4.0);

        return new ObjectList(
                new Sphere(new Point3(-r, 0, -1), r, matLeft),
                new Sphere(new Point3(r, 0, -1), r, matRight)
        );
    }

    /**
     * Create the world data
     *
     * @return The world data
     */
    private AbstractObj getWorld4() {
        ObjectList world = new ObjectList();

        Material matGround = new Lambertian(new Color(0.5, 0.5, 0.5));
        world.add(new Sphere(new Point3(0, -1000, 0), 1000, matGround));

        for (int a = -11; a < 11; a++) {
            for (int b = -11; b < 11; b++) {
                double selection = MathUtils.randomDouble();
                Point3 center = new Point3(a + 0.9 * MathUtils.randomDouble(),
                        0.2, b + 0.9 * MathUtils.randomDouble());

                if (center.copy().sub(new Point3(4, 0.2, 0)).len() < 0.9) {
                    continue;
                }
                Material material;
                if (selection < 0.8) {
                    // diffuse
                    Color albedo = Color.random().mul(Color.random());
                    material = new Lambertian(albedo);
                }
                else if (selection < 0.95) {
                    // metal
                    Color albedo = Color.random(0.5, 1);
                    double fuzz = MathUtils.randomDouble(0, 0.5);
                    material = new Metal(albedo, fuzz);
                }
                else {
                    // glass
                    material = new Dielectric(1.5);
                }
                world.add(new Sphere(center, 0.2, material));
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
