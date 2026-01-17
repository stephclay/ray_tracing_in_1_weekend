package com.wombatsw.raytracing.engine;

import com.wombatsw.raytracing.model.Vector3;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ColorUtilsTest {
    @Test
    public void testColors() {
        assertEquals(3, ColorUtils.white().lenSquared());
        assertEquals(0, ColorUtils.black().lenSquared());
    }

    @Test
    public void writeColor() {
        Vector3 c1 = new Vector3(1, 0.25, 0);
        byte[] data = new byte[6];

        ColorUtils.writeColor(c1, data, 3);
        assertEquals(0, data[0]);
        assertEquals(0, data[1]);
        assertEquals(0, data[2]);
        assertEquals((byte) 255, data[3]);
        assertEquals((byte) (255/2), data[4]); // value is due to gamma correction
        assertEquals((byte) 0, data[5]);
    }
}