package com.wombatsw.raytracing.scene;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
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
        URL resource = this.getClass().getResource("/" + sceneName);
        if (resource != null) {
            SceneReader reader = new SceneReader(resource);
            return reader.getScene();
        }
        return scenesMap.getOrDefault(sceneName, scenesMap.get(RandomSpheresScene.class.getSimpleName()));
    }
}
