package com.wombatsw.raytracing.scene;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.wombatsw.raytracing.scene.dto.SceneDTO;
import com.wombatsw.raytracing.scene.ref.RefSerializer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

/**
 * Read YAML scene description files
 */
public class SceneWriter {
    public void write(final Scene scene) {
        write(new OutputStreamWriter(System.out), scene);
    }

    public void write(final File file, final Scene scene) {
        try (Writer out = new FileWriter(file)) {
            write(out, scene);
        } catch (IOException e) {
            throw new IllegalArgumentException("Cannot write to scene file " + file, e);
        }
    }

    public void write(final Writer out, final Scene scene) {
        try {
            YAMLMapper mapper = new YAMLMapper();
            mapper.enable(YAMLGenerator.Feature.MINIMIZE_QUOTES);
            mapper.disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER);
            mapper.disable(YAMLGenerator.Feature.USE_NATIVE_TYPE_ID);
            mapper.disable(JsonGenerator.Feature.AUTO_CLOSE_TARGET);

            SimpleModule module = new SimpleModule();
            module.addSerializer(new RefSerializer());
            mapper.registerModule(module);

            mapper.writer().writeValue(out, new SceneDTO(scene));
        } catch (IOException e) {
            throw new IllegalArgumentException("Cannot write scene file", e);
        }
    }
}
