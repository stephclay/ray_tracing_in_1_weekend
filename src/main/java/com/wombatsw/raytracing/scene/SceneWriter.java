package com.wombatsw.raytracing.scene;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.wombatsw.raytracing.scene.dto.SceneDTO;
import com.wombatsw.raytracing.scene.ref.RefSerializer;
import lombok.Getter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Read YAML scene description files
 */
public class SceneWriter {
    @Getter
    private final SceneDTO sceneDTO;

    public SceneWriter(final Scene scene) {
        this.sceneDTO = new SceneDTO(scene);
    }

    public void write() {
        try {
            write(System.out);
        } catch (IOException e) {
            throw new IllegalArgumentException("Cannot write scene file", e);
        }
    }

    public void write(final File file) {
        try (OutputStream out = new FileOutputStream(file)) {
            write(out);
        } catch (IOException e) {
            throw new IllegalArgumentException("Cannot write to scene file " + file, e);
        }
    }

    private void write(final OutputStream out) throws IOException {
        YAMLMapper mapper = new YAMLMapper();
        mapper.enable(YAMLGenerator.Feature.MINIMIZE_QUOTES);
        mapper.disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER);
        mapper.disable(YAMLGenerator.Feature.USE_NATIVE_TYPE_ID);
        mapper.disable(JsonGenerator.Feature.AUTO_CLOSE_TARGET);

        SimpleModule module = new SimpleModule();
        module.addSerializer(new RefSerializer());
        mapper.registerModule(module);

        mapper.writer().writeValue(out, sceneDTO);
    }
}
