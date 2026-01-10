package com.wombatsw.raytracing.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RayTest {

    @Test
    public void testAt() {
        Point3 origin = new Point3(0, 2, 0);
        Vector3 dir = new Vector3(1, -1, 0);
        Ray ray = new Ray(origin, dir);

        Point3 result = ray.at(4);
        assertEquals(new Point3(4, -2, 0), result);
    }

    @Test
    public void testImmutableRay() {
        Point3 origin = new Point3(0, 2, 0);
        Vector3 dir = new Vector3(1, -1, 0);
        Ray ray = new Ray(origin, dir);

        assertThrows(IllegalStateException.class, () -> ray.origin().mul(2));
        assertThrows(IllegalStateException.class, () -> ray.direction().mul(2));
    }
}