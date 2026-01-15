package com.wombatsw.raytracing;

import com.wombatsw.raytracing.engine.Camera;
import com.wombatsw.raytracing.engine.Renderer;
import com.wombatsw.raytracing.engine.Viewport;
import com.wombatsw.raytracing.model.Point3;
import com.wombatsw.raytracing.model.Vector3;
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
        Viewport viewport = new Viewport();
        viewport.setFieldOfView(20);
        viewport.setViewportCenter(new Point3(0, 0, 0));
        viewport.setViewUp(new Vector3(0, 1, 0));

        Camera camera = new Camera(viewport);
        camera.setAspectRatio(16.0 / 9.0);
        camera.setImageWidth(600);
        camera.setCameraCenter(new Point3(13, 2, 3));
        camera.setDefocusAngle(0.6);
        camera.setFocusDistance(10.0);

        Renderer renderer = new Renderer();
        renderer.setAntialiasRandom(40);
        renderer.setMaxDepth(20);

        String selection = args.length > 0 ? args[0] : RandomSpheresScene.class.getSimpleName();
        Scene scene = sceneSelector.getScene(selection);
        AbstractObj world = new BVHNode(scene.getWorld());

        byte[] imageData = renderer.render(world, camera);

        ImageWriter writer = new PPMImageWriter();
        writer.write(new File("test.ppm"), camera.getImageWidth(), camera.getImageHeight(), imageData);
    }
}
