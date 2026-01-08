package com.wombatsw.raytracing.model;

import com.wombatsw.raytracing.engine.MathUtils;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * A mutable 3D Vector
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Vector3 extends Triplet<Vector3> {
    public Vector3(final double x, final double y, final double z) {
        super(new Tuple(x, y, z));
    }

    Vector3(final Tuple tuple) {
        super(tuple);
    }

    /**
     * Create a vector pointing from the provided tail to the provided head
     *
     * @param head The tip or head of the vector
     * @param tail The start or tail of the vector
     */
    public Vector3(final Point3 head, final Point3 tail) {
        this(head.copy().sub(tail).getTuple());
    }

    @Override
    Vector3 create(Tuple t) {
        return new Vector3(t);
    }

    /**
     * Generate a random Vector3 with the provided min and max values for each vector component
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
                return v.div(Math.sqrt(lenSq));
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
        if (v.dot(normal) < 0) { // other hemisphere
            v.negate();
        }
        return v;
    }

    /**
     * Generate a random value within a unit disc. The disc is in the x/y plane with z = 0
     *
     * @return The new Vector3
     */
    public static Vector3 randomInUnitDisk() {
        while (true) {
            Vector3 v = new Vector3(MathUtils.randomDouble(-1 , 1), MathUtils.randomDouble(-1 , 1), 0);
            if (v.lenSquared() < 1) {
                return v;
            }
        }
    }
}
