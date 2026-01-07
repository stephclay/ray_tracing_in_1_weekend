package com.wombatsw.raytracing.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TupleTest {
    @Test
    public void testGetValue() {
        Tuple tuple = new Tuple(1, 2);

        assertEquals(1, tuple.getValue(0));
        assertEquals(2, tuple.getValue(1));
    }

    @Test
    public void testAdd() {
        Tuple tuple = new Tuple(1, 2);

        Tuple result = tuple.add(new Tuple(6, 5));
        assertEquals(new Tuple(7, 7), result);
    }

    @Test
    public void testSub() {
        Tuple tuple = new Tuple(1, 2);

        Tuple result = tuple.sub(new Tuple(6, 5));
        assertEquals(new Tuple(-5, -3), result);
    }

    @Test
    public void tesMulScalar() {
        Tuple tuple = new Tuple(1, 2);

        Tuple result = tuple.mul(-2);
        assertEquals(new Tuple(-2, -4), result);
    }

    @Test
    public void testMulTuple() {
        Tuple tuple = new Tuple(-1, 2);

        Tuple result = tuple.mul(new Tuple(6, 5));
        assertEquals(new Tuple(-6, 10), result);
    }

    @Test
    public void testTranslate() {
        Tuple tuple = new Tuple(1, 2);

        Tuple result = tuple.translate(new Tuple(-1, -1), 3);
        assertEquals(new Tuple(-2, -1), result);
    }

    @Test
    public void testLerp() {
        Tuple t1 = new Tuple(1, 2);
        Tuple t2 = new Tuple(3, 4);

        Tuple result1 = t1.copy().lerp(t2, 0);
        assertEquals(new Tuple(1, 2), result1);

        Tuple result2 = t1.copy().lerp(t2, 0.25);
        assertEquals(new Tuple(1.5, 2.5), result2);

        Tuple result3 = t1.copy().lerp(t2, 1);
        assertEquals(new Tuple(3, 4), result3);
    }
}