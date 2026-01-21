package com.wombatsw.raytracing.model;

import static com.wombatsw.raytracing.Constants.EPSILON;
import static java.lang.Double.NEGATIVE_INFINITY;
import static java.lang.Double.POSITIVE_INFINITY;

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
    public BoundingBox(final Vector3 a, final Vector3 b) {
        this.x = padToMin(Interval.createOrdered(a.getX(), b.getX()));
        this.y = padToMin(Interval.createOrdered(a.getY(), b.getY()));
        this.z = padToMin(Interval.createOrdered(a.getZ(), b.getZ()));
    }

    /**
     * Union of 2 bounding boxes, including any space between the intervals
     *
     * @param a First bounding box
     * @param b Second bounding box
     */
    public BoundingBox(final BoundingBox a, final BoundingBox b) {
        x = padToMin(new Interval(a.x, b.x));
        y = padToMin(new Interval(a.y, b.y));
        z = padToMin(new Interval(a.z, b.z));
    }

    /**
     * Create a new BoundingBox that fits the transformation of the provided bounding box
     *
     * @param orig   The original {@link BoundingBox}
     * @param affine The transformation matrix
     */
    public BoundingBox(final BoundingBox orig, final Affine affine) {
        double[] min = new double[]{POSITIVE_INFINITY, POSITIVE_INFINITY, POSITIVE_INFINITY};
        double[] max = new double[]{NEGATIVE_INFINITY, NEGATIVE_INFINITY, NEGATIVE_INFINITY};

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                for (int k = 0; k < 2; k++) {
                    double x = i == 0 ? orig.x.min() : orig.x.max();
                    double y = i == 0 ? orig.y.min() : orig.y.max();
                    double z = i == 0 ? orig.z.min() : orig.z.max();

                    Vector3 p = affine.apply(new Vector3(x, y, z), true);

                    for (int c = 0; c < 3; c++) {
                        min[c] = Math.min(min[c], p.getValue(c));
                        max[c] = Math.max(max[c], p.getValue(c));
                    }
                }
            }
        }

        this.x = new Interval(min[0], max[0]);
        this.y = new Interval(min[1], max[1]);
        this.z = new Interval(min[2], max[2]);
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
        Vector3 origin = ray.origin();
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

    private Interval padToMin(final Interval interval) {
        return interval.size() < EPSILON ? interval.expand(EPSILON) : interval;
    }
}
