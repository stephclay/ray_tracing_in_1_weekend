package com.wombatsw.raytracing.scene.generators;

import com.wombatsw.raytracing.engine.Camera;
import com.wombatsw.raytracing.engine.ColorUtils;
import com.wombatsw.raytracing.engine.Viewport;
import com.wombatsw.raytracing.material.DiffuseLight;
import com.wombatsw.raytracing.material.Lambertian;
import com.wombatsw.raytracing.material.Material;
import com.wombatsw.raytracing.model.Affine;
import com.wombatsw.raytracing.model.Triplet;
import com.wombatsw.raytracing.obj.AbstractObj;
import com.wombatsw.raytracing.obj.ObjectList;
import com.wombatsw.raytracing.obj.Quad;
import com.wombatsw.raytracing.obj.Transform;
import com.wombatsw.raytracing.scene.Scene;
import org.springframework.stereotype.Component;

@Component
public class CornellBoxScene implements Scene {
    @Override
    public Camera getCamera() {
        Camera camera = new Camera();
        camera.setCameraCenter(new Triplet(278, 278, -800));
        camera.setBackground(ColorUtils.black());

        Viewport viewport = camera.getViewport();
        viewport.setFieldOfView(40);
        viewport.setViewportCenter(new Triplet(278, 278, 0));
        viewport.setViewUp(new Triplet(0, 1, 0));

        return camera;
    }

    @Override
    public ObjectList getWorld() {

        Material red = new Lambertian(new Triplet(.65, .05, .05));
        Material white = new Lambertian(new Triplet(.73, .73, .73));
        Material green = new Lambertian(new Triplet(.12, .45, .15));
        Material light = new DiffuseLight(new Triplet(15, 15, 15));

        Triplet lightQ = new Triplet(343, 554, 332);
        Triplet lightU = new Triplet(-130, 0, 0);
        Triplet lightV = new Triplet(0, 0, -105);

        Triplet vx = new Triplet(555, 0, 0);
        Triplet vy = new Triplet(0, 555, 0);
        Triplet vz = new Triplet(0, 0, 555);
        Triplet v0 = new Triplet(0, 0, 0);

        Triplet vq = new Triplet(555, 555, 555);
        Triplet vu = new Triplet(-555, 0, 0);
        Triplet vv = new Triplet(0, 0, -555);

        AbstractObj box1 = Quad.createBox(new Triplet(0, 0, 0), new Triplet(165, 330, 165), white);
        Affine affine1 = new Affine()
                .rotateY(15)
                .translate(new Triplet(265, 0, 295));

        AbstractObj box2 = Quad.createBox(new Triplet(0, 0, 0), new Triplet(165, 165, 165), white);
        Affine affine2 = new Affine()
                .rotateY(-18)
                .translate(new Triplet(130, 0, 65));

        return new ObjectList(
                new Quad(lightQ, lightU, lightV, light),
                new Quad(vx, vy, vz, green),
                new Quad(v0, vy, vz, red),
                new Quad(v0, vx, vz, white),
                new Quad(vq, vu, vv, white),
                new Quad(vz, vx, vy, white),
                new Transform(box1, affine1),
                new Transform(box2, affine2)
        );
    }
}
