package com.wombatsw.raytracing.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RayTest {

    @Test
    public void testAt() {
        Triplet origin = new Triplet(0, 2, 0);
        Triplet dir = new Triplet(1, -1, 0);
        Ray ray = new Ray(origin, dir);

        Triplet result = ray.at(4);
        assertEquals(new Triplet(4, -2, 0), result);
    }

    @Test
    public void testImmutableRay() {
        Triplet origin = new Triplet(0, 2, 0);
        Triplet dir = new Triplet(1, -1, 0);
        Ray ray = new Ray(origin, dir);

        assertThrows(IllegalStateException.class, () -> ray.origin().mul(2));
        assertThrows(IllegalStateException.class, () -> ray.direction().mul(2));
    }
}