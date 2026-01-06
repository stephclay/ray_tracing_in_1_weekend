package com.wombatsw.raytracing;

import com.wombatsw.raytracing.engine.Camera;
import com.wombatsw.raytracing.model.Point3;
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
        camera.setSamplesPerPixel(100);
        camera.setMaxDepth(50);

        ObjectList world = new ObjectList()
                .add(new Sphere(new Point3(0, 0, -1), 0.5))
                .add(new Sphere(new Point3(0, -100.5, -1), 100));

        byte[] imageData = camera.render(world);

        ImageWriter writer = new PPMImageWriter();
        writer.write(new File("test.ppm"), camera.getImageWidth(), camera.getImageHeight(), imageData);
    }
}
