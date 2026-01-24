package com.wombatsw.raytracing.scene;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.wombatsw.raytracing.model.Triplet;
import com.wombatsw.raytracing.scene.dto.SceneDTO;
import com.wombatsw.raytracing.scene.ref.TripletDeserializer;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * Read YAML scene description files
 */
public class SceneReader {
    public Scene read() {
        Reader in = new InputStreamReader(System.in);
        return read(in);
    }


    public Scene read(File sceneFile) {
        try (Reader in = new FileReader(sceneFile)) {
            return read(in);
        } catch (IOException e) {
            throw new IllegalArgumentException("Cannot read scene file", e);
        }
    }

    public Scene read(final Reader in) {
        try {
            ObjectMapper mapper = new YAMLMapper();

            SimpleModule module = new SimpleModule();
            module.addDeserializer(Triplet.class, new TripletDeserializer());
            mapper.registerModule(module);

            SceneDTO sceneDTO = mapper.readValue(in, SceneDTO.class);
            return sceneDTO.toScene();
        } catch (IOException e) {
            throw new IllegalArgumentException("Cannot read scene file", e);
        }
    }

    private static FileReader getFileReader(File file) {
        try {
            return new FileReader(file);
        } catch (IOException e) {
            throw new IllegalArgumentException("Cannot read scene file: " + file, e);
        }
    }
}
