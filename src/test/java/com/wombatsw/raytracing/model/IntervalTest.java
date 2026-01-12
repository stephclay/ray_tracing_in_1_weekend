package com.wombatsw.raytracing.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IntervalTest {
    @Test
    public void testIntervalUnion() {
        Interval i1 = new Interval(-2, 1);
        Interval i2 = new Interval(-1, 2);
        Interval i3 = new Interval(3, 4);

        assertEquals(new Interval(-2, 2), new Interval(i1, i2));
        assertEquals(new Interval(-2, 2), new Interval(i2, i1));
        assertEquals(new Interval(-2, 4), new Interval(i1, i3));
    }
    @Test
    public void testCreateOrdered() {
        assertEquals(new Interval(-1, 1), Interval.createOrdered(-1, 1));
        assertEquals(new Interval(-1, 1), Interval.createOrdered(1, -1));
    }

    @Test
    public void testSize() {
        assertEquals(1, new Interval(1, 2).size());
        assertEquals(0, new Interval(1, 1).size());
        assertEquals(-1, new Interval(1, 0).size());
    }

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

    @Test
    public void testExpand() {
        Interval interval = new Interval(-1, 1);

        Interval result = interval.expand(0.5);
        assertEquals(-1.25, result.min());
        assertEquals(1.25, result.max());
    }
}