package com.wombatsw.raytracing.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ColorTest {
    @Test
    public void testCopy() {
        Color c1 = new Color(1, 2, 3);

        Color result = c1.copy();
        assertEquals(c1, result);
        assertNotSame(c1, result);
    }

    @Test
    public void testAverage() {
        Color c1 = new Color(1,2,-1);
        Color c2 = new Color(2,3,3);
        Color c3 = new Color(0,4,-2);

        Color result = Color.average(c1, c2, c3);
        assertEquals(1, result.getX());
        assertEquals(3, result.getY());
        assertEquals(0, result.getZ());
    }

    @Test
    public void writeColor() {
        Color c1 = new Color(1, 0.25, 0);
        byte[] data = new byte[6];

        c1.writeColor(data, 3);
        assertEquals(0, data[0]);
        assertEquals(0, data[1]);
        assertEquals(0, data[2]);
        assertEquals((byte) 255, data[3]);
        assertEquals((byte) (255/2), data[4]); // value is due to gamma correction
        assertEquals((byte) 0, data[5]);
    }
}