package com.wombatsw.raytracing.scene;

import com.wombatsw.raytracing.material.Dielectric;
import com.wombatsw.raytracing.material.Lambertian;
import com.wombatsw.raytracing.material.Material;
import com.wombatsw.raytracing.material.Metal;
import com.wombatsw.raytracing.model.Color;
import com.wombatsw.raytracing.model.Point3;
import com.wombatsw.raytracing.obj.ObjectList;
import com.wombatsw.raytracing.obj.Sphere;
import org.springframework.stereotype.Component;

@Component
public class ThreeSpheresScene implements Scene {
    @Override
    public ObjectList getWorld() {
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
}
