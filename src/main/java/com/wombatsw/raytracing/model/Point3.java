package com.wombatsw.raytracing.model;

import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * A mutable 3D Point
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Point3 extends Triplet<Point3> {
    public Point3(final double x, final double y, final double z) {
        super(new Tuple(x, y, z));
    }

    Point3(final Tuple tuple) {
        super(tuple);
    }

    @Override
    Point3 create(Tuple t) {
        return new Point3(t);
    }
}
