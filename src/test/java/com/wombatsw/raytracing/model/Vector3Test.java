package com.wombatsw.raytracing.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Vector3Test {
    private static final int RAND_ITERATIONS = 100;

    @Test
    public void testCopy() {
        Vector3 v1 = new Vector3(1, 2, 3);

        Vector3 result = v1.copy();
        assertEquals(v1, result);
        assertNotSame(v1, result);
    }

    @Test
    public void testRandom() {
        // Since this is random, run multiple tests
        for (int i = 0; i < RAND_ITERATIONS; i++) {
            Vector3 v1 = Vector3.random(1, 2);
            assertTrue(v1.lenSquared() >= 3);
            assertTrue(v1.lenSquared() <= 12);
        }
    }

    @Test
    public void randomUnitVector() {
        // Since this is random, run multiple tests
        for (int i = 0; i < RAND_ITERATIONS; i++) {
            Vector3 v1 = Vector3.randomUnitVector();

            double lenMinusOne = Math.abs(v1.lenSquared() - 1);
            assertTrue(lenMinusOne < 1e-10);
        }
    }

    @Test
    public void randomOnHemisphere() {
        Vector3 n = new Vector3(1, 0, 0);
        // Since this is random, run multiple tests
        for (int i = 0; i < RAND_ITERATIONS; i++) {
            Vector3 v1 = Vector3.randomOnHemisphere(n);

            double lenMinusOne = Math.abs(v1.lenSquared() - 1);
            assertTrue(lenMinusOne < 1e-10);
            assertTrue(v1.getX() >= 0);
        }
    }
}
