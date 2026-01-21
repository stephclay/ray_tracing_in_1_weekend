package com.wombatsw.raytracing.scene;

import com.wombatsw.raytracing.engine.Camera;
import com.wombatsw.raytracing.engine.ColorUtils;
import com.wombatsw.raytracing.engine.Viewport;
import com.wombatsw.raytracing.material.DiffuseLight;
import com.wombatsw.raytracing.material.Lambertian;
import com.wombatsw.raytracing.material.Material;
import com.wombatsw.raytracing.model.Affine;
import com.wombatsw.raytracing.model.Vector3;
import com.wombatsw.raytracing.obj.AbstractObj;
import com.wombatsw.raytracing.obj.ObjectList;
import com.wombatsw.raytracing.obj.Quad;
import com.wombatsw.raytracing.obj.Transform;
import org.springframework.stereotype.Component;

@Component
public class CornellBoxScene implements Scene {
    @Override
    public Camera getCamera() {
        Camera camera = new Camera();
        camera.setCameraCenter(new Vector3(278, 278, -800));
        camera.setBackground(ColorUtils.black());

        Viewport viewport = camera.getViewport();
        viewport.setFieldOfView(40);
        viewport.setViewportCenter(new Vector3(278, 278, 0));
        viewport.setViewUp(new Vector3(0, 1, 0));

        return camera;
    }

    @Override
    public ObjectList getWorld() {

        Material red = new Lambertian(new Vector3(.65, .05, .05));
        Material white = new Lambertian(new Vector3(.73, .73, .73));
        Material green = new Lambertian(new Vector3(.12, .45, .15));
        Material light = new DiffuseLight(new Vector3(15, 15, 15));

        Vector3 lightQ = new Vector3(343, 554, 332);
        Vector3 lightU = new Vector3(-130, 0, 0);
        Vector3 lightV = new Vector3(0, 0, -105);

        Vector3 vx = new Vector3(555, 0, 0);
        Vector3 vy = new Vector3(0, 555, 0);
        Vector3 vz = new Vector3(0, 0, 555);
        Vector3 v0 = new Vector3(0, 0, 0);

        Vector3 vq = new Vector3(555, 555, 555);
        Vector3 vu = new Vector3(-555, 0, 0);
        Vector3 vv = new Vector3(0, 0, -555);

        AbstractObj box1 = Quad.createBox(new Vector3(0, 0, 0), new Vector3(165, 330, 165), white);
        Affine affine1 = new Affine()
                .rotateY(15)
                .translate(new Vector3(265, 0, 295));

        AbstractObj box2 = Quad.createBox(new Vector3(0, 0, 0), new Vector3(165, 165, 165), white);
        Affine affine2 = new Affine()
                .rotateY(-18)
                .translate(new Vector3(130, 0, 65));

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
