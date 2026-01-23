package com.wombatsw.raytracing.scene;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.wombatsw.raytracing.model.Triplet;
import com.wombatsw.raytracing.scene.dto.SceneDTO;
import com.wombatsw.raytracing.scene.ref.TripletDeserializer;
import lombok.Getter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Read YAML scene description files
 */
public class SceneReader {
    @Getter
    private final Scene scene;

    public SceneReader(final File file) {
        ObjectMapper mapper = new YAMLMapper();

        SimpleModule module = new SimpleModule();
        module.addDeserializer(Triplet.class, new TripletDeserializer());
        mapper.registerModule(module);

        try (InputStream in = new FileInputStream(file)) {
            SceneDTO sceneDTO = mapper.readValue(in, SceneDTO.class);
            scene = sceneDTO.resolve(new ResolveContext());
        } catch (IOException e) {
            throw new IllegalArgumentException("Cannot read scene file " + file, e);
        }
    }
}
