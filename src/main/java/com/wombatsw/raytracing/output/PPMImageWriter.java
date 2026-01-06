package com.wombatsw.raytracing.output;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Image writer for PPM color binary (P6) format
 */
public class PPMImageWriter implements ImageWriter {
    @Override
    public void write(File name, int width, int height, byte[] data) throws IOException {
        try (FileOutputStream out = new FileOutputStream(name)) {
            String header = String.format("P6\n%d %d 255\n", width, height);
            out.write(header.getBytes(StandardCharsets.UTF_8));
            out.write(data);
        }
    }
}
