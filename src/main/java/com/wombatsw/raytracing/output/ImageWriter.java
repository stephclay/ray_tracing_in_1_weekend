package com.wombatsw.raytracing.output;

import java.io.File;
import java.io.IOException;

/**
 * An interface for writing images
 */
public interface ImageWriter {
    void write(File name, int width, int height, byte[] data) throws IOException;
}
