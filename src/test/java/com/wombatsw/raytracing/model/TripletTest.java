package com.wombatsw.raytracing.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TripletTest {

    @Test
    void testAdd() {
        Vector3 v1 = new Vector3(1, 2, 3);
        Vector3 v2 = new Vector3(-1, 0, 5);

        Vector3 result = v1.add(v2);
        assertVectorEquals(0, 2, 8, result);
    }

    @Test
    void testSub() {
        Vector3 v1 = new Vector3(1, 2, 3);
        Vector3 v2 = new Vector3(-1, 0, 5);

        Vector3 result = v1.sub(v2);
        assertVectorEquals(2, 2, -2, result);
    }

    @Test
    void testMulScalar() {
        Vector3 v1 = new Vector3(1, 2, 3);

        Vector3 result = v1.mul(-2);
        assertVectorEquals(-2, -4, -6, result);
    }

    @Test
    void testMulVector() {
        Vector3 v1 = new Vector3(1, 2, 3);
        Vector3 v2 = new Vector3(-1, 0, 5);

        Vector3 result = v1.mul(v2);
        assertVectorEquals(-1, -0, 15, result);
    }

    @Test
    void testDiv() {
        Vector3 v1 = new Vector3(1, 2, 3);

        Vector3 result = v1.div(-2);
        assertVectorEquals(-0.5, -1, -1.5, result);
    }

    @Test
    void testNegate() {
        Vector3 v1 = new Vector3(1, 2, 3);

        Vector3 result = v1.negate();
        assertVectorEquals(-1, -2, -3, result);
    }

    @Test
    void testNormalize() {
        Vector3 v1 = new Vector3(1, 2, 3);

        Vector3 result = v1.normalize();
        double lenMinusOne = Math.abs(result.lenSquared() - 1);
        assertTrue(lenMinusOne < 1e-20);
    }

    @Test
    void testReflect() {
        Vector3 v1 = new Vector3(1, 2, 3);
        Vector3 n = new Vector3(0, 1, 0); // Needs to be a unit vector

        Vector3 result = v1.reflect(n);
        assertVectorEquals(1, -2, 3, result);
    }

    @Test
    void testTranslate() {
        Vector3 v1 = new Vector3(1, 2, 3);
        Vector3 v2 = new Vector3(-1, 0, 5);

        Vector3 result = v1.translate(v2, 2);
        assertVectorEquals(-1, 2, 13, result);
    }

    @Test
    void testLerp() {
        Vector3 v1 = new Vector3(1, 2, 3);
        Vector3 v2 = new Vector3(2, 3, 4);

        Vector3 result1 = v1.copy().lerp(v2, 0);
        assertVectorEquals(1, 2, 3, result1);

        Vector3 result2 = v1.copy().lerp(v2, 0.5);
        assertVectorEquals(1.5, 2.5, 3.5, result2);

        Vector3 result3 = v1.copy().lerp(v2, 1);
        assertVectorEquals(2, 3, 4, result3);
    }

    @Test
    void testDot() {
        Vector3 v1 = new Vector3(1, 0, 1);
        Vector3 v2 = new Vector3(0, 1, 0);
        Vector3 v3 = new Vector3(1, 1, 2);

        double result1 = v1.copy().dot(v2);
        assertEquals(0,  result1);

        double result2 = v1.copy().dot(v3);
        assertEquals(3,  result2);
    }

    @Test
    void testCross() {
        Vector3 v1 = new Vector3(1, 0, 0);
        Vector3 v2 = new Vector3(0, 1, 0);

        Vector3 result = v1.cross(v2);
        assertVectorEquals(0, 0, 1, result);
    }

    @Test
    void testLenSquared() {
        Vector3 v1 = new Vector3(2, 3, 4);

        double lenSquared = v1.lenSquared();
        assertEquals(29, lenSquared);
    }

    @Test
    void testLen() {
        Vector3 v1 = new Vector3(1, 2, 2);

        double len = v1.len();
        assertEquals(3, len);
    }

    @Test
    void testNearZero() {
        Vector3 v1 = new Vector3(1e-100, 1e-100, 1e-100);

        assertTrue(v1.nearZero());
    }

    public void assertVectorEquals(final double expectedX,  final double expectedY, final double expectedZ,
                                   final Vector3 actual) {
        assertEquals(expectedX, actual.getX());
        assertEquals(expectedY, actual.getY());
        assertEquals(expectedZ, actual.getZ());
    }
}
