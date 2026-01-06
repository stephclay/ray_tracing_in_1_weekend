package com.wombatsw.raytracing.obj;

import com.wombatsw.raytracing.model.Intersection;
import com.wombatsw.raytracing.model.Interval;
import com.wombatsw.raytracing.model.Ray;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A list of scene objects
 */
public class ObjectList extends AbstractObj {
    private final ArrayList<AbstractObj> list = new ArrayList<AbstractObj>();

    public ObjectList() {
        super(null);
    }

    public ObjectList(AbstractObj... objs) {
        this();
        add(objs);
    }

    /**
     * Add an object to the list. May be chained
     *
     * @param objs One or more scene objects
     * @return this
     */
    public ObjectList add(AbstractObj... objs) {
        list.addAll(Arrays.asList(objs));
        return this;
    }

    /**
     * Clear the list of scene objects
     */
    public void clear() {
        list.clear();
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
}
