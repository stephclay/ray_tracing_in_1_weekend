package com.wombatsw.raytracing.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IntersectionTest {
    private static final Vector3 Y_UNIT = new Vector3(0,1,0);

    @Test
    public void testIntersectionOutside() {
        Point3 origin = new Point3(0, 2, 0);
        Vector3 dir = new Vector3(1, -1, 0);
        Ray ray = new Ray(origin, dir);
        double t = 2.0;

        Intersection intersection = new Intersection(ray, t, null, p -> Y_UNIT);

        assertEquals(Y_UNIT, intersection.getN());
        assertEquals(new Point3(2, 0, 0), intersection.getP());
        assertTrue(intersection.isFrontFace());
    }

    @Test
    public void testIntersectionInside() {
        Point3 origin = new Point3(0, -3, 0);
        Vector3 dir = new Vector3(1, 1, 0);
        Ray ray = new Ray(origin, dir);
        double t = 3.0;

        Intersection intersection = new Intersection(ray, t, null, p -> Y_UNIT);

        assertEquals(Y_UNIT, intersection.getN());
        assertEquals(new Point3(3, 0, 0), intersection.getP());
        assertFalse(intersection.isFrontFace());
    }
}