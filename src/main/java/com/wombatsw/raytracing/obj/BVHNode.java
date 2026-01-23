package com.wombatsw.raytracing.obj;

import com.wombatsw.raytracing.model.BoundingBox;
import com.wombatsw.raytracing.model.Intersection;
import com.wombatsw.raytracing.model.Interval;
import com.wombatsw.raytracing.model.Ray;
import lombok.Getter;
import lombok.ToString;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Bounding Volume Hierarchy Node
 */
@Getter
@ToString(callSuper = true)
public class BVHNode extends AbstractObj {
    /**
     * A helper class to sort objects along a given axis
     *
     * @param axis The axis used for comparison
     */
    private record BoxCompare(int axis) implements Comparator<AbstractObj> {
        @Override
        public int compare(AbstractObj o1, AbstractObj o2) {
            Interval i1 = o1.getBoundingBox().axisInterval(axis);
            Interval i2 = o2.getBoundingBox().axisInterval(axis);
            return Double.compare(i1.min(), i2.min());
        }
    }

    private static final BoxCompare[] COMPS = {
            new BoxCompare(0), new BoxCompare(1), new BoxCompare(2)};

    private final AbstractObj left;
    private final AbstractObj right;

    /**
     * Create a Bounding Volume Hierarchy Node from the given list of objects
     *
     * @param objList The object list
     */
    public BVHNode(final ObjectList objList) {
        this(objList.getList().toArray(new AbstractObj[0]), 0, objList.getList().size());
    }

    /**
     * Create a Bounding Volume Hierarchy Node from a subset of the given array of objects
     *
     * @param objects The object array
     * @param start   The start of the subset, inclusive
     * @param end     The end of the subset, exclusive
     */
    private BVHNode(final AbstractObj[] objects, final int start, final int end) {
        super(null, computeBoundingBox(objects, start, end));

        int axis = getBoundingBox().longestAxis();
        int span = end - start;

        if (span == 1) {
            left = right = objects[start];
        } else if (span == 2) {
            left = objects[start];
            right = objects[start + 1];
        } else {
            Arrays.sort(objects, start, end, COMPS[axis]);
            int mid = start + span / 2;
            left = new BVHNode(objects, start, mid);
            right = new BVHNode(objects, mid, end);
        }
    }

    @Override
    public Intersection intersect(Ray ray, Interval tRange) {
        Interval rayInt = getBoundingBox().intersect(ray, tRange);
        if (rayInt == null) {
            return null;
        }

        Intersection returnVal = null;
        double closestT = rayInt.max();
        Intersection intLeft = left.intersect(ray, new Interval(rayInt.min(), closestT));
        if (intLeft != null) {
            closestT = intLeft.getT();
            returnVal = intLeft;
        }
        Intersection intRight = right.intersect(ray, new Interval(rayInt.min(), closestT));
        if (intRight != null) {
            returnVal = intRight;
        }

        return returnVal;
    }

    /**
     * Compute the bounding box for the given set of objects
     *
     * @param objects The set of objects
     * @param start The starting index of the elements to use, inclusive
     * @param end The ending index of the elements to use, exclusive
     * @return The {@link BoundingBox}
     */
    private static BoundingBox computeBoundingBox(AbstractObj[] objects, int start, int end) {
        BoundingBox bbox = new BoundingBox();
        for (int i = start; i < end; i++) {
            bbox = new BoundingBox(bbox, objects[i].getBoundingBox());
        }
        return bbox;
    }
}
