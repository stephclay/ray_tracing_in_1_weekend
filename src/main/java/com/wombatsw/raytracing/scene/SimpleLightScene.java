package com.wombatsw.raytracing.scene;

import com.wombatsw.raytracing.engine.Camera;
import com.wombatsw.raytracing.engine.ColorUtils;
import com.wombatsw.raytracing.engine.Viewport;
import com.wombatsw.raytracing.material.DiffuseLight;
import com.wombatsw.raytracing.material.Lambertian;
import com.wombatsw.raytracing.material.Material;
import com.wombatsw.raytracing.model.Vector3;
import com.wombatsw.raytracing.obj.ObjectList;
import com.wombatsw.raytracing.obj.Quad;
import com.wombatsw.raytracing.obj.Sphere;
import com.wombatsw.raytracing.texture.NoiseTexture;
import com.wombatsw.raytracing.texture.Texture;
import org.springframework.stereotype.Component;

@Component
public class SimpleLightScene implements Scene {
    @Override
    public Camera getCamera() {
        Camera camera = new Camera();
        camera.setCameraCenter(new Vector3(26, 3, 6));
        camera.setBackground(ColorUtils.black());

        Viewport viewport = camera.getViewport();
        viewport.setFieldOfView(20);
        viewport.setViewportCenter(new Vector3(0, 2, 0));
        viewport.setViewUp(new Vector3(0, 1, 0));

        return camera;
    }

    @Override
    public ObjectList getWorld() {
        Texture pertext = new NoiseTexture(4);
        Material diffLight = new DiffuseLight(new Vector3(4, 4, 4));

        return new ObjectList(
                new Sphere(new Vector3(0, -1000, 0), 1000, new Lambertian(pertext)),
                new Sphere(new Vector3(0, 2, 0), 2, new Lambertian(pertext)),
                new Sphere(new Vector3(0, 7, 0), 2, diffLight),
                new Quad(new Vector3(3, 1, -2), new Vector3(2, 0, 0), new Vector3(0, 2, 0),
                        diffLight)
        );
    }
}
