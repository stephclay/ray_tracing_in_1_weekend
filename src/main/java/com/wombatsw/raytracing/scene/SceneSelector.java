package com.wombatsw.raytracing.scene;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class SceneSelector {
    private final Map<String, Scene> scenesMap;

    @Autowired
    public SceneSelector(final List<Scene> scenes) {
        scenesMap = new HashMap<>();
        for (Scene scene : scenes) {
            scenesMap.put(scene.getClass().getSimpleName(), scene);
        }
    }

    public Scene getScene(final String sceneName) {
        Scene scene = scenesMap.get(sceneName);
        if (scene != null) {
            return scene;
        }

        File sceneFile = new File(sceneName);
        if (!sceneFile.exists()) {
            throw new RuntimeException(String.format("Scene %s not found", sceneName));
        }

        SceneReader reader = new SceneReader(sceneFile);
        return reader.getScene();
    }
}
