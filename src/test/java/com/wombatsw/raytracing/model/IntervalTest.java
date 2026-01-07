package com.wombatsw.raytracing.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IntervalTest {

    @Test
    public void testContains() {
        Interval interval = new Interval(-1, 1);

        assertTrue(interval.contains(0));
        assertTrue(interval.contains(-1));
        assertTrue(interval.contains(1));
        assertFalse(interval.contains(-2));
        assertFalse(interval.contains(2));
    }

    @Test
    public void testSurrounds() {
        Interval interval = new Interval(-1, 1);

        assertTrue(interval.surrounds(0));
        assertFalse(interval.surrounds(-1));
        assertFalse(interval.surrounds(1));
        assertFalse(interval.surrounds(-2));
        assertFalse(interval.surrounds(2));
    }

    @Test
    public void testClamp() {
        Interval interval = new Interval(-1, 1);

        assertEquals(-1, interval.clamp(-2));
        assertEquals(-1, interval.clamp(-1));
        assertEquals(0, interval.clamp(0));
        assertEquals(1, interval.clamp(1));
        assertEquals(1, interval.clamp(2));
    }
}