package com.wombatsw.raytracing.model;

import com.wombatsw.raytracing.engine.MathUtils;

/**
 * A 3D Vector
 */
public class Vector3 extends Triplet<Vector3> {
    public Vector3(final double x, final double y, final double z) {
        super(x, y, z);
    }

    /**
     * Normalize this triplet to a unit value
     *
     * @return A new triplet with the result
     */
    public Vector3 normalize() {
        return div(len());
    }

    /**
     * Generate a Vector3 with the provided min and max values for each vector component
     *
     * @param min Min component value
     * @param max Max component value
     * @return The new Vector3
     */
    public static Vector3 random(final double min, final double max) {
        return new Vector3(MathUtils.randomDouble(min, max),
                MathUtils.randomDouble(min, max),
                MathUtils.randomDouble(min, max));
    }

    /**
     * Generate a random unit vector
     *
     * @return The new Vector3
     */
    public static Vector3 randomUnitVector() {
        while (true) {
            Vector3 v = random(-1, 1);
            double lenSq = v.lenSquared();
            if (lenSq > 1e-160 && lenSq <= 1.0) {
                return v.div(lenSq);
            }
        }
    }

    /**
     * Generate a random unit Vector3 within 90 degrees of the provided normal
     *
     * @param normal The normal vector
     * @return The new Vector3
     */
    public static Vector3 randomOnHemisphere(final Vector3 normal) {
        Vector3 v = randomUnitVector();
        if (v.dot(normal) > 0) { // same hemisphere
            return v;
        } else {
            return v.negate();
        }
    }

    @Override
    public Vector3 create(final double x, final double y, final double z) {
        return new Vector3(x, y, z);
    }

    /**
     * Reflect the vector relative to the given surface normal
     *
     * @param n The surface normal
     * @return The reflected vector
     */
    public Vector3 reflect(final Vector3 n) {
        double scale = 2.0 * dot(n);
        return sub(n.mul(scale));
    }
}
