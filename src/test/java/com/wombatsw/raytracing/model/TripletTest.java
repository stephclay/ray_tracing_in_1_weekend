package com.wombatsw.raytracing.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TripletTest {

    @Test
    public void testAdd() {
        Triplet<?> t1 = createTriplet(1, 2, 3);
        Triplet<?> t2 = createTriplet(-1, 0, 5);

        Triplet<?> result = t1.add(t2);
        assertVectorEquals(0, 2, 8, result);
    }

    @Test
    public void testSub() {
        Triplet<?> t1 = createTriplet(1, 2, 3);
        Triplet<?> t2 = createTriplet(-1, 0, 5);

        Triplet<?> result = t1.sub(t2);
        assertVectorEquals(2, 2, -2, result);
    }

    @Test
    public void testMulScalar() {
        Triplet<?> t1 = createTriplet(1, 2, 3);

        Triplet<?> result = t1.mul(-2);
        assertVectorEquals(-2, -4, -6, result);
    }

    @Test
    public void testMulVector() {
        Triplet<?> t1 = createTriplet(1, 2, 3);
        Triplet<?> t2 = createTriplet(-1, 0, 5);

        Triplet<?> result = t1.mul(t2);
        assertVectorEquals(-1, -0, 15, result);
    }

    @Test
    public void testDiv() {
        Triplet<?> t1 = createTriplet(1, 2, 3);

        Triplet<?> result = t1.div(-2);
        assertVectorEquals(-0.5, -1, -1.5, result);
    }

    @Test
    public void testNegate() {
        Triplet<?> t1 = createTriplet(1, 2, 3);

        Triplet<?> result = t1.negate();
        assertVectorEquals(-1, -2, -3, result);
    }

    @Test
    public void testAddScaledOneVector() {
        Triplet<?> t1 = createTriplet(1, 2, 3);
        Triplet<?> t2 = createTriplet(-1, 1, -2);
        Triplet<?> t2orig = t2.copy();

        Triplet<?> result = t1.addScaled(t2, 3);
        assertVectorEquals(-2, 5, -3, result);
        // Verify this didn't change
        assertEquals(t2orig, t2);
    }

    @Test
    public void testAddScaledTwoVectors() {
        Triplet<?> t1 = createTriplet(1, 2, 3);
        Triplet<?> t2 = createTriplet(-1, 1, -2);
        Triplet<?> t3 = createTriplet(2, 2, 2);
        Triplet<?> t2orig = t2.copy();
        Triplet<?> t3orig = t3.copy();

        Triplet<?> result = t1.addScaled(t2, 3, t3, 2);
        assertVectorEquals(2, 9, 1, result);
        // Verify these didn't change
        assertEquals(t2orig, t2);
        assertEquals(t3orig, t3);
    }

    @Test
    public void testNormalize() {
        Triplet<?> t1 = createTriplet(1, 2, 3);

        Triplet<?> result = t1.normalize();
        double lenMinusOne = Math.abs(result.lenSquared() - 1);
        assertTrue(lenMinusOne < 1e-20);
    }

    @Test
    public void testReflect() {
        Triplet<?> t1 = createTriplet(1, 2, 3);
        Triplet<?> n = createTriplet(0, 1, 0); // Needs to be a unit vector

        Triplet<?> result = t1.reflect(n);
        assertVectorEquals(1, -2, 3, result);
    }

    @Test
    public void testRefract() {
        Triplet<?> t1 = createTriplet(2, 2, 1/1.5);
        Triplet<?> n = createTriplet(0, 1, 0); // Needs to be a unit vector

        // cosTheta = -t1.n = -2
        // Rperp = ([2,2,1/1.5] - 2*[0,-1,0]) * 1.5 = [3,0,1] (len = 10)
        // Rparallel = -sqrt(|1 - 10|) * [0,1,0] = [0,-3,0]

        Triplet<?> result = t1.refract(n, 1.5);
        assertVectorEquals(3, -3, 1, result);
    }

    @Test
    public void testTranslate() {
        Triplet<?> t1 = createTriplet(1, 2, 3);
        Triplet<?> t2 = createTriplet(-1, 0, 5);

        Triplet<?> result = t1.translate(t2, 2);
        assertVectorEquals(-1, 2, 13, result);
    }

    @Test
    public void testLerp() {
        Triplet<?> t1 = createTriplet(1, 2, 3);
        Triplet<?> t2 = createTriplet(2, 3, 4);

        Triplet<?> result1 = t1.copy().lerp(t2, 0);
        assertVectorEquals(1, 2, 3, result1);

        Triplet<?> result2 = t1.copy().lerp(t2, 0.5);
        assertVectorEquals(1.5, 2.5, 3.5, result2);

        Triplet<?> result3 = t1.copy().lerp(t2, 1);
        assertVectorEquals(2, 3, 4, result3);
    }

    @Test
    public void testDot() {
        Triplet<?> t1 = createTriplet(1, 0, 1);
        Triplet<?> t2 = createTriplet(0, 1, 0);
        Triplet<?> t3 = createTriplet(1, 1, 2);

        double result1 = t1.copy().dot(t2);
        assertEquals(0, result1);

        double result2 = t1.copy().dot(t3);
        assertEquals(3, result2);
    }

    @Test
    public void testCross() {
        Triplet<?> t1 = createTriplet(1, 0, 0);
        Triplet<?> t2 = createTriplet(0, 1, 0);

        Triplet<?> result = t1.cross(t2);
        assertVectorEquals(0, 0, 1, result);
    }

    @Test
    public void testLenSquared() {
        Triplet<?> t1 = createTriplet(2, 3, 4);

        double lenSquared = t1.lenSquared();
        assertEquals(29, lenSquared);
    }

    @Test
    public void testLen() {
        Triplet<?> t1 = createTriplet(1, 2, 2);

        double len = t1.len();
        assertEquals(3, len);
    }

    @Test
    public void testNearZero() {
        Triplet<?> t1 = createTriplet(1e-100, 1e-100, 1e-100);

        assertTrue(t1.nearZero());
    }

    private Triplet<?> createTriplet(final double x, final double y, final double z) {
        // The type does not really matter, so use Vector3
        return new Vector3(x, y, z);
    }

    private void assertVectorEquals(final double expectedX, final double expectedY, final double expectedZ,
                                    final Triplet<?> actual) {
        assertEquals(expectedX, actual.getX());
        assertEquals(expectedY, actual.getY());
        assertEquals(expectedZ, actual.getZ());
    }
}
