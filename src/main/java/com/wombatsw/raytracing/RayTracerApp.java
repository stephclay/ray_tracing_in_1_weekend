package com.wombatsw.raytracing;

import com.wombatsw.raytracing.engine.Camera;
import com.wombatsw.raytracing.material.Lambertian;
import com.wombatsw.raytracing.material.Material;
import com.wombatsw.raytracing.material.Metal;
import com.wombatsw.raytracing.model.Color;
import com.wombatsw.raytracing.model.Point3;
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
        camera.setImageWidth(400);
        camera.setSamplesPerPixel(10);
        camera.setMaxDepth(5);

        AbstractObj world = getWorld();

        byte[] imageData = camera.render(world);

        ImageWriter writer = new PPMImageWriter();
        writer.write(new File("test.ppm"), camera.getImageWidth(), camera.getImageHeight(), imageData);
    }

    /**
     * Create the world data
     *
     * @return The world data
     */
    private AbstractObj getWorld() {
        Material matGround = new Lambertian(new Color(0.8, 0.8, 0.0)); // yellow
        Material matCenter = new Lambertian(new Color(0.1, 0.2, 0.5)); // blue
        Material matLeft = new Metal(new Color(0.8, 0.8, 0.8)); // gray
        Material matRight = new Metal(new Color(0.8, 0.6, 0.2)); // slightly red-ish yellow

        return new ObjectList(
                new Sphere(new Point3(0, -100.5, -1), 100, matGround),
                new Sphere(new Point3(0, 0, -1.2), 0.5, matCenter),
                new Sphere(new Point3(-1, 0, -1), 0.5, matLeft),
                new Sphere(new Point3(1, 0, -1), 0.5, matRight)
        );
    }
}
