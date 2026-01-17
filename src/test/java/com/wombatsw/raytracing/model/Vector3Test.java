package com.wombatsw.raytracing.model;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

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
    public void testAverage() {
        Vector3 v1 = new Vector3(1,2,-1);
        Vector3 v2 = new Vector3(2,3,3);
        Vector3 v3 = new Vector3(0,4,-2);

        Vector3 result = Vector3.average(Arrays.asList(v1, v2, v3));
        assertEquals(1, result.getX());
        assertEquals(3, result.getY());
        assertEquals(0, result.getZ());
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

    @Test
    public void testAdd() {
        Vector3 v1 = createVector(1, 2, 3);
        Vector3 v2 = createVector(-1, 0, 5);

        Vector3 result = v1.add(v2);
        assertVectorEquals(0, 2, 8, result);
    }

    @Test
    public void testSub() {
        Vector3 v1 = createVector(1, 2, 3);
        Vector3 v2 = createVector(-1, 0, 5);

        Vector3 result = v1.sub(v2);
        assertVectorEquals(2, 2, -2, result);
    }

    @Test
    public void testMulScalar() {
        Vector3 v1 = createVector(1, 2, 3);

        Vector3 result = v1.mul(-2);
        assertVectorEquals(-2, -4, -6, result);
    }

    @Test
    public void testMulVector() {
        Vector3 v1 = createVector(1, 2, 3);
        Vector3 v2 = createVector(-1, 0, 5);

        Vector3 result = v1.mul(v2);
        assertVectorEquals(-1, -0, 15, result);
    }

    @Test
    public void testDiv() {
        Vector3 v1 = createVector(1, 2, 3);

        Vector3 result = v1.div(-2);
        assertVectorEquals(-0.5, -1, -1.5, result);
    }

    @Test
    public void testNegate() {
        Vector3 v1 = createVector(1, 2, 3);

        Vector3 result = v1.negate();
        assertVectorEquals(-1, -2, -3, result);
    }

    @Test
    public void testAddScaledOneVector() {
        Vector3 v1 = createVector(1, 2, 3);
        Vector3 v2 = createVector(-1, 1, -2);
        Vector3 v2orig = v2.copy();

        Vector3 result = v1.addScaled(v2, 3);
        assertVectorEquals(-2, 5, -3, result);
        // Verify this didn't change
        assertEquals(v2orig, v2);
    }

    @Test
    public void testAddScaledTwoVectors() {
        Vector3 v1 = createVector(1, 2, 3);
        Vector3 v2 = createVector(-1, 1, -2);
        Vector3 v3 = createVector(2, 2, 2);
        Vector3 v2orig = v2.copy();
        Vector3 v3orig = v3.copy();

        Vector3 result = v1.addScaled(v2, 3, v3, 2);
        assertVectorEquals(2, 9, 1, result);
        // Verify these didn't change
        assertEquals(v2orig, v2);
        assertEquals(v3orig, v3);
    }

    @Test
    public void testNormalize() {
        Vector3 v1 = createVector(1, 2, 3);

        Vector3 result = v1.normalize();
        double lenMinusOne = Math.abs(result.lenSquared() - 1);
        assertTrue(lenMinusOne < 1e-20);
    }

    @Test
    public void testReflect() {
        Vector3 v1 = createVector(1, 2, 3);
        Vector3 n = createVector(0, 1, 0); // Needs to be a unit vector

        Vector3 result = v1.reflect(n);
        assertVectorEquals(1, -2, 3, result);
    }

    @Test
    public void testRefract() {
        Vector3 v1 = createVector(2, 2, 1/1.5);
        Vector3 n = createVector(0, 1, 0); // Needs to be a unit vector

        // cosTheta = -v1.n = -2
        // Rperp = ([2,2,1/1.5] - 2*[0,-1,0]) * 1.5 = [3,0,1] (len = 10)
        // Rparallel = -sqrt(|1 - 10|) * [0,1,0] = [0,-3,0]

        Vector3 result = v1.refract(n, 1.5);
        assertVectorEquals(3, -3, 1, result);
    }

    @Test
    public void testTranslate() {
        Vector3 v1 = createVector(1, 2, 3);
        Vector3 v2 = createVector(-1, 0, 5);

        Vector3 result = v1.translate(v2, 2);
        assertVectorEquals(-1, 2, 13, result);
    }

    @Test
    public void testLerp() {
        Vector3 v1 = createVector(1, 2, 3);
        Vector3 v2 = createVector(2, 3, 4);

        Vector3 result1 = v1.copy().lerp(v2, 0);
        assertVectorEquals(1, 2, 3, result1);

        Vector3 result2 = v1.copy().lerp(v2, 0.5);
        assertVectorEquals(1.5, 2.5, 3.5, result2);

        Vector3 result3 = v1.copy().lerp(v2, 1);
        assertVectorEquals(2, 3, 4, result3);
    }

    @Test
    public void testDot() {
        Vector3 v1 = createVector(1, 0, 1);
        Vector3 v2 = createVector(0, 1, 0);
        Vector3 v3 = createVector(1, 1, 2);

        double result1 = v1.copy().dot(v2);
        assertEquals(0, result1);

        double result2 = v1.copy().dot(v3);
        assertEquals(3, result2);
    }

    @Test
    public void testCross() {
        Vector3 v1 = createVector(1, 0, 0);
        Vector3 v2 = createVector(0, 1, 0);

        Vector3 result = v1.cross(v2);
        assertVectorEquals(0, 0, 1, result);
    }

    @Test
    public void testLenSquared() {
        Vector3 v1 = createVector(2, 3, 4);

        double lenSquared = v1.lenSquared();
        assertEquals(29, lenSquared);
    }

    @Test
    public void testLen() {
        Vector3 v1 = createVector(1, 2, 2);

        double len = v1.len();
        assertEquals(3, len);
    }

    @Test
    public void testNearZero() {
        Vector3 v1 = createVector(1e-100, 1e-100, 1e-100);

        assertTrue(v1.nearZero());
    }

    private Vector3 createVector(final double x, final double y, final double z) {
        // The type does not really matter, so use Vector3
        return new Vector3(x, y, z);
    }

    private void assertVectorEquals(final double expectedX, final double expectedY, final double expectedZ,
                                    final Vector3 actual) {
        assertEquals(expectedX, actual.getX());
        assertEquals(expectedY, actual.getY());
        assertEquals(expectedZ, actual.getZ());
    }
}
