package com.wombatsw.raytracing.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Point3Test {
    // Point3 doesn't have any non-constructor methods, so just run a basic test to exercise the create method

    @Test
    public void testCopy() {
        Point3 p1 = new Point3(1, 2, 3);

        Point3 result = p1.copy();
        assertEquals(p1, result);
        assertNotSame(p1, result);
    }
}