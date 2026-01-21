package com.wombatsw.raytracing.model;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class TripletTest {
    private static final int RAND_ITERATIONS = 100;

    @Test
    public void testCopy() {
        Triplet v1 = new Triplet(1, 2, 3);

        Triplet result = v1.copy();
        assertEquals(v1, result);
        assertNotSame(v1, result);
    }

    @Test
    public void testAverage() {
        Triplet v1 = new Triplet(1,2,-1);
        Triplet v2 = new Triplet(2,3,3);
        Triplet v3 = new Triplet(0,4,-2);

        Triplet result = Triplet.average(Arrays.asList(v1, v2, v3));
        assertEquals(1, result.getX());
        assertEquals(3, result.getY());
        assertEquals(0, result.getZ());
    }
    @Test
    public void testRandom() {
        // Since this is random, run multiple tests
        for (int i = 0; i < RAND_ITERATIONS; i++) {
            Triplet v1 = Triplet.random(1, 2);
            assertTrue(v1.lenSquared() >= 3);
            assertTrue(v1.lenSquared() <= 12);
        }
    }

    @Test
    public void randomUnitVector() {
        // Since this is random, run multiple tests
        for (int i = 0; i < RAND_ITERATIONS; i++) {
            Triplet v1 = Triplet.randomUnitVector();

            double lenMinusOne = Math.abs(v1.lenSquared() - 1);
            assertTrue(lenMinusOne < 1e-10);
        }
    }

    @Test
    public void randomOnHemisphere() {
        Triplet n = new Triplet(1, 0, 0);
        // Since this is random, run multiple tests
        for (int i = 0; i < RAND_ITERATIONS; i++) {
            Triplet v1 = Triplet.randomOnHemisphere(n);

            double lenMinusOne = Math.abs(v1.lenSquared() - 1);
            assertTrue(lenMinusOne < 1e-10);
            assertTrue(v1.getX() >= 0);
        }
    }

    @Test
    public void testAdd() {
        Triplet v1 = createVector(1, 2, 3);
        Triplet v2 = createVector(-1, 0, 5);

        Triplet result = v1.add(v2);
        assertVectorEquals(0, 2, 8, result);
    }

    @Test
    public void testSub() {
        Triplet v1 = createVector(1, 2, 3);
        Triplet v2 = createVector(-1, 0, 5);

        Triplet result = v1.sub(v2);
        assertVectorEquals(2, 2, -2, result);
    }

    @Test
    public void testMulScalar() {
        Triplet v1 = createVector(1, 2, 3);

        Triplet result = v1.mul(-2);
        assertVectorEquals(-2, -4, -6, result);
    }

    @Test
    public void testMulVector() {
        Triplet v1 = createVector(1, 2, 3);
        Triplet v2 = createVector(-1, 0, 5);

        Triplet result = v1.mul(v2);
        assertVectorEquals(-1, -0, 15, result);
    }

    @Test
    public void testDiv() {
        Triplet v1 = createVector(1, 2, 3);

        Triplet result = v1.div(-2);
        assertVectorEquals(-0.5, -1, -1.5, result);
    }

    @Test
    public void testNegate() {
        Triplet v1 = createVector(1, 2, 3);

        Triplet result = v1.negate();
        assertVectorEquals(-1, -2, -3, result);
    }

    @Test
    public void testAddScaledOneVector() {
        Triplet v1 = createVector(1, 2, 3);
        Triplet v2 = createVector(-1, 1, -2);
        Triplet v2orig = v2.copy();

        Triplet result = v1.addScaled(v2, 3);
        assertVectorEquals(-2, 5, -3, result);
        // Verify this didn't change
        assertEquals(v2orig, v2);
    }

    @Test
    public void testAddScaledTwoVectors() {
        Triplet v1 = createVector(1, 2, 3);
        Triplet v2 = createVector(-1, 1, -2);
        Triplet v3 = createVector(2, 2, 2);
        Triplet v2orig = v2.copy();
        Triplet v3orig = v3.copy();

        Triplet result = v1.addScaled(v2, 3, v3, 2);
        assertVectorEquals(2, 9, 1, result);
        // Verify these didn't change
        assertEquals(v2orig, v2);
        assertEquals(v3orig, v3);
    }

    @Test
    public void testNormalize() {
        Triplet v1 = createVector(1, 2, 3);

        Triplet result = v1.normalize();
        double lenMinusOne = Math.abs(result.lenSquared() - 1);
        assertTrue(lenMinusOne < 1e-20);
    }

    @Test
    public void testReflect() {
        Triplet v1 = createVector(1, 2, 3);
        Triplet n = createVector(0, 1, 0); // Needs to be a unit vector

        Triplet result = v1.reflect(n);
        assertVectorEquals(1, -2, 3, result);
    }

    @Test
    public void testRefract() {
        Triplet v1 = createVector(2, 2, 1/1.5);
        Triplet n = createVector(0, 1, 0); // Needs to be a unit vector

        // cosTheta = -v1.n = -2
        // Rperp = ([2,2,1/1.5] - 2*[0,-1,0]) * 1.5 = [3,0,1] (len = 10)
        // Rparallel = -sqrt(|1 - 10|) * [0,1,0] = [0,-3,0]

        Triplet result = v1.refract(n, 1.5);
        assertVectorEquals(3, -3, 1, result);
    }

    @Test
    public void testLerp() {
        Triplet v1 = createVector(1, 2, 3);
        Triplet v2 = createVector(2, 3, 4);

        Triplet result1 = v1.copy().lerp(v2, 0);
        assertVectorEquals(1, 2, 3, result1);

        Triplet result2 = v1.copy().lerp(v2, 0.5);
        assertVectorEquals(1.5, 2.5, 3.5, result2);

        Triplet result3 = v1.copy().lerp(v2, 1);
        assertVectorEquals(2, 3, 4, result3);
    }

    @Test
    public void testDot() {
        Triplet v1 = createVector(1, 0, 1);
        Triplet v2 = createVector(0, 1, 0);
        Triplet v3 = createVector(1, 1, 2);

        double result1 = v1.copy().dot(v2);
        assertEquals(0, result1);

        double result2 = v1.copy().dot(v3);
        assertEquals(3, result2);
    }

    @Test
    public void testCross() {
        Triplet v1 = createVector(1, 0, 0);
        Triplet v2 = createVector(0, 1, 0);

        Triplet result = v1.cross(v2);
        assertVectorEquals(0, 0, 1, result);
    }

    @Test
    public void testLenSquared() {
        Triplet v1 = createVector(2, 3, 4);

        double lenSquared = v1.lenSquared();
        assertEquals(29, lenSquared);
    }

    @Test
    public void testLen() {
        Triplet v1 = createVector(1, 2, 2);

        double len = v1.len();
        assertEquals(3, len);
    }

    @Test
    public void testNearZero() {
        Triplet v1 = createVector(1e-100, 1e-100, 1e-100);

        assertTrue(v1.nearZero());
    }

    @Test
    public void testImmutable() {
        Triplet v1 = new Triplet(1, 2, 3);
        Triplet v2 = new Triplet(1, 2, 3);
        v1.setMutable(false);

        v2.add(v1);
        assertThrows(IllegalStateException.class, () -> v1.add(v2));
    }

    private Triplet createVector(final double x, final double y, final double z) {
        // The type does not really matter, so use Vector3
        return new Triplet(x, y, z);
    }

    private void assertVectorEquals(final double expectedX, final double expectedY, final double expectedZ,
                                    final Triplet actual) {
        assertEquals(expectedX, actual.getX());
        assertEquals(expectedY, actual.getY());
        assertEquals(expectedZ, actual.getZ());
    }
}
