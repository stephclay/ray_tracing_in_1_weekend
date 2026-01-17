package com.wombatsw.raytracing.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IntersectionTest {
    private static final Vector3 Y_UNIT = new Vector3(0,1,0);

    @Test
    public void testIntersectionOutside() {
        Vector3 origin = new Vector3(0, 2, 0);
        Vector3 dir = new Vector3(1, -1, 0);
        Ray ray = new Ray(origin, dir);
        double t = 2.0;

        Intersection intersection = new Intersection(ray, t, ray.at(t), Y_UNIT.copy(), 0, 0, null);

        assertEquals(Y_UNIT, intersection.getN());
        assertEquals(new Vector3(2, 0, 0), intersection.getP());
        assertTrue(intersection.isFrontFace());
    }

    @Test
    public void testIntersectionInside() {
        Vector3 origin = new Vector3(0, -3, 0);
        Vector3 dir = new Vector3(1, 1, 0);
        Ray ray = new Ray(origin, dir);
        double t = 3.0;

        Intersection intersection = new Intersection(ray, t, ray.at(t), Y_UNIT.copy(), 0, 0, null);

        assertEquals(Y_UNIT.copy().negate(), intersection.getN());
        assertEquals(new Vector3(3, 0, 0), intersection.getP());
        assertFalse(intersection.isFrontFace());
    }
}