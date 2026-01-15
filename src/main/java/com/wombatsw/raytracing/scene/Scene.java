package com.wombatsw.raytracing.scene;

import com.wombatsw.raytracing.engine.Camera;
import com.wombatsw.raytracing.obj.ObjectList;

public interface Scene {
    /**
     * Set up the camera
     *
     * @return The camera
     */
    Camera getCamera();

    /**
     * Get the world data
     *
     * @return The list of objects in the scene
     */
    ObjectList getWorld();
}
