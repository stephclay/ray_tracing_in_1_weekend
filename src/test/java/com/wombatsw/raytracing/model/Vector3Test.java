package com.wombatsw.raytracing.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Vector3Test {
    private static final int RAND_ITERATIONS = 100;

    @Test
    void testNearZero() {
        Vector3 v1 = new Vector3(1e-20, 0, 0);

        assertTrue(v1.nearZero());
    }

    @Test
    void testRandom() {
        // Since this is random, run multiple tests
        for (int i = 0; i < RAND_ITERATIONS; i++) {
            Vector3 v1 = Vector3.random(1, 2);
            assertTrue(v1.lenSquared() >= 3);
            assertTrue(v1.lenSquared() <= 12);
        }
    }

    @Test
    void randomUnitVector() {
        // Since this is random, run multiple tests
        for (int i = 0; i < RAND_ITERATIONS; i++) {
            Vector3 v1 = Vector3.randomUnitVector();

            double lenMinusOne = Math.abs(v1.lenSquared() - 1);
            assertTrue(lenMinusOne < 1e-10);
        }
    }

    @Test
    void randomOnHemisphere() {
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
