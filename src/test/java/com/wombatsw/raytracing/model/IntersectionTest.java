package com.wombatsw.raytracing.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IntersectionTest {
    private static final Triplet Y_UNIT = new Triplet(0,1,0);

    @Test
    public void testIntersectionOutside() {
        Triplet origin = new Triplet(0, 2, 0);
        Triplet dir = new Triplet(1, -1, 0);
        Ray ray = new Ray(origin, dir);
        double t = 2.0;

        Intersection intersection = new Intersection(ray, t, ray.at(t), Y_UNIT.copy(), 0, 0, null);

        assertEquals(Y_UNIT, intersection.getN());
        assertEquals(new Triplet(2, 0, 0), intersection.getP());
        assertTrue(intersection.isFrontFace());
    }

    @Test
    public void testIntersectionInside() {
        Triplet origin = new Triplet(0, -3, 0);
        Triplet dir = new Triplet(1, 1, 0);
        Ray ray = new Ray(origin, dir);
        double t = 3.0;

        Intersection intersection = new Intersection(ray, t, ray.at(t), Y_UNIT.copy(), 0, 0, null);

        assertEquals(Y_UNIT.copy().negate(), intersection.getN());
        assertEquals(new Triplet(3, 0, 0), intersection.getP());
        assertFalse(intersection.isFrontFace());
    }
}