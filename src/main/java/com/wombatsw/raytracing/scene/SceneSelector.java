package com.wombatsw.raytracing.scene;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class SceneSelector {

    @Autowired
    public SceneSelector(final List<Scene> scenes) {
        scenesMap = new HashMap<>();
        for (Scene scene : scenes) {
            scenesMap.put(scene.getClass().getSimpleName(), scene);
        }
    }

    private Map<String, Scene> scenesMap;

    public Scene getScene(final String sceneName) {
        return scenesMap.getOrDefault(sceneName, scenesMap.get(RandomSpheresScene.class.getSimpleName()));
    }
}
