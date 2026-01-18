package com.wombatsw.raytracing;

import com.wombatsw.raytracing.engine.Camera;
import com.wombatsw.raytracing.engine.Renderer;
import com.wombatsw.raytracing.obj.AbstractObj;
import com.wombatsw.raytracing.obj.BVHNode;
import com.wombatsw.raytracing.output.ImageWriter;
import com.wombatsw.raytracing.output.PPMImageWriter;
import com.wombatsw.raytracing.scene.RandomSpheresScene;
import com.wombatsw.raytracing.scene.Scene;
import com.wombatsw.raytracing.scene.SceneSelector;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * Ray Tracer command line application
 */
@Component
public class RayTracerApp implements CommandLineRunner {
    private final SceneSelector sceneSelector;

    @Autowired
    public RayTracerApp(SceneSelector sceneSelector) {
        this.sceneSelector = sceneSelector;
    }

    @Override
    public void run(final String @NonNull ... args) throws Exception {

        String selection = args.length > 0 ? args[0] : "QuadsScene";
        Scene scene = sceneSelector.getScene(selection);

        AbstractObj world = new BVHNode(scene.getWorld());

        Camera camera = scene.getCamera();
        camera.setAspectRatio(16.0 / 9.0);
        camera.setImageWidth(400);

        Renderer renderer = new Renderer();
        renderer.setAntialiasRandom(40);
        renderer.setMaxDepth(20);

        byte[] imageData = renderer.render(world, camera);

        ImageWriter writer = new PPMImageWriter();
        writer.write(new File("test.ppm"), camera.getImageWidth(), camera.getImageHeight(), imageData);
    }
}
