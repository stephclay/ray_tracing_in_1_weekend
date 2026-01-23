package com.wombatsw.raytracing.obj;

import com.wombatsw.raytracing.model.BoundingBox;
import com.wombatsw.raytracing.model.Intersection;
import com.wombatsw.raytracing.model.Interval;
import com.wombatsw.raytracing.model.Ray;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A list of scene objects
 */
@Getter
@ToString(callSuper = true)
public class ObjectList extends AbstractObj {
    private final List<AbstractObj> list;

    public ObjectList(AbstractObj... objs) {
        this(List.of(objs));
    }

    public ObjectList(List<AbstractObj> objs) {
        super(null, computeBoundingBox(objs));

        list = Collections.unmodifiableList(new ArrayList<>(objs));
    }

    @Override
    public Intersection intersect(final Ray ray, final Interval tRange) {
        Intersection returnVal = null;
        double closestT = tRange.max();

        for (AbstractObj obj : list) {
            Intersection intersection = obj.intersect(ray, new Interval(tRange.min(), closestT));
            if (intersection != null) {
                closestT = intersection.getT();
                returnVal = intersection;
            }
        }
        return returnVal;
    }

    /**
     * Compute the bounding box for the given set of objects
     *
     * @param objs The set of objects
     * @return The {@link BoundingBox}
     */
    private static BoundingBox computeBoundingBox(List<AbstractObj> objs) {
        BoundingBox boundingBox = new BoundingBox();
        for (AbstractObj obj : objs) {
            boundingBox = new BoundingBox(boundingBox, obj.getBoundingBox());
        }
        return boundingBox;
    }
}
