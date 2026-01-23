package com.wombatsw.raytracing.scene;

import com.wombatsw.raytracing.engine.Camera;
import com.wombatsw.raytracing.obj.AbstractObj;
import com.wombatsw.raytracing.obj.ObjectList;

import java.util.List;

/**
 * A container type for {@link Scene} data
 *
 * @param camera The {@link Camera}
 * @param world  The list of world objects
 */
public record SceneFile(Camera camera, List<AbstractObj> world) implements Scene {
    @Override
    public Camera getCamera() {
        return camera;
    }

    @Override
    public ObjectList getWorld() {
        return new ObjectList(world);
    }
}
