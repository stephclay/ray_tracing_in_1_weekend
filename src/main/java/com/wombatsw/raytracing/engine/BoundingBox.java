package com.wombatsw.raytracing.engine;

import com.wombatsw.raytracing.model.Interval;
import com.wombatsw.raytracing.model.Point3;
import com.wombatsw.raytracing.model.Ray;
import com.wombatsw.raytracing.model.Vector3;

/**
 * An Axis-Aligned Bounding Box
 */
public class BoundingBox {
    private final Interval x, y, z;

    /**
     * Empty bounding box constructor
     */
    public BoundingBox() {
        x = Interval.EMPTY;
        y = Interval.EMPTY;
        z = Interval.EMPTY;
    }

    /**
     * Bounding box from 2 points
     *
     * @param a First point
     * @param b Second point
     */
    public BoundingBox(final Point3 a, final Point3 b) {
        this.x = Interval.createOrdered(a.getX(), b.getX());
        this.y = Interval.createOrdered(a.getY(), b.getY());
        this.z = Interval.createOrdered(a.getZ(), b.getZ());
    }

    /**
     * Union of 2 bounding boxes, including any space between the intervals
     *
     * @param a First bounding box
     * @param b Second bounding box
     */
    public BoundingBox(final BoundingBox a, final BoundingBox b) {
        x = new Interval(a.x, b.x);
        y = new Interval(a.y, b.y);
        z = new Interval(a.z, b.z);
    }

    /**
     * Get the interval for the indicated axis
     *
     * @param n The axis index from 0 to 2
     * @return The {@link Interval} for that axis
     */
    public Interval axisInterval(int n) {
        return switch (n) {
            case 0 -> x;
            case 1 -> y;
            case 2 -> z;
            default -> throw new IllegalStateException("Invalid axis: " + n);
        };
    }

    /**
     * Intersect this bounding box with the given ray and within the given range
     *
     * @param ray    The ray
     * @param tRange The range of points on the ray to consider
     * @return The interval of intersection, or {@code null} if there is no intersection
     */
    public Interval intersect(final Ray ray, final Interval tRange) {
        Point3 origin = ray.origin();
        Vector3 dir = ray.direction();

        double min = tRange.min();
        double max = tRange.max();
        for (int axis = 0; axis < 3; axis++) {
            Interval axisInterval = axisInterval(axis);
            double dirInv = 1.0 / dir.getValue(axis);

            double t0 = (axisInterval.min() - origin.getValue(axis)) * dirInv;
            double t1 = (axisInterval.max() - origin.getValue(axis)) * dirInv;

            if (t0 < t1) {
                min = Math.max(t0, min);
                max = Math.min(t1, max);
            } else {
                min = Math.max(t1, min);
                max = Math.min(t0, max);
            }

            if (max <= min) {
                return null;
            }
        }
        return new Interval(min, max);
    }

    /**
     * Get the axis index of the longest interval
     *
     * @return the axis index of the longest interval
     */
    public int longestAxis() {
        if (x.size() > y.size()) {
            return x.size() > z.size() ? 0 : 2;
        } else {
            return y.size() > z.size() ? 1 : 2;
        }
    }
}
