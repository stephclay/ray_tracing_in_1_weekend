package com.wombatsw.raytracing.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RayTest {

    @Test
    public void testAt() {
        Vector3 origin = new Vector3(0, 2, 0);
        Vector3 dir = new Vector3(1, -1, 0);
        Ray ray = new Ray(origin, dir);

        Vector3 result = ray.at(4);
        assertEquals(new Vector3(4, -2, 0), result);
    }

    @Test
    public void testImmutableRay() {
        Vector3 origin = new Vector3(0, 2, 0);
        Vector3 dir = new Vector3(1, -1, 0);
        Ray ray = new Ray(origin, dir);

        assertThrows(IllegalStateException.class, () -> ray.origin().mul(2));
        assertThrows(IllegalStateException.class, () -> ray.direction().mul(2));
    }
}