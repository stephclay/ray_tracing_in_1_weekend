package com.wombatsw.raytracing.obj;

import com.wombatsw.raytracing.engine.BoundingBox;
import com.wombatsw.raytracing.model.Affine;
import com.wombatsw.raytracing.model.Intersection;
import com.wombatsw.raytracing.model.Interval;
import com.wombatsw.raytracing.model.Ray;
import com.wombatsw.raytracing.model.Vector3;

/**
 * An object made from transforming another object.
 */
public class Transform extends AbstractObj {
    private final AbstractObj object;
    private final Affine affine;
    private final BoundingBox boundingBox;

    public Transform(final AbstractObj object, final Affine affine) {
        super(object.getMaterial());
        this.object = object;
        this.affine = affine;
        this.boundingBox = new BoundingBox(object.getBoundingBox(), affine);
    }

    @Override
    public Intersection intersect(final Ray ray, final Interval tRange) {
        // Transform ray to object space
        Vector3 newOrigin = affine.invert(ray.origin(), true);
        Vector3 newDir = affine.invert(ray.direction(), false);

        Ray rotatedRay = new Ray(newOrigin, newDir, ray.time());

        // Determine intersection
        Intersection intersection = object.intersect(rotatedRay, tRange);
        if (intersection == null) {
            return null;
        }

        // Transform intersection from object space to world space
        Vector3 p = affine.apply(intersection.getP(), true);
        Vector3 n = affine.invert(intersection.getN(), false);

        return new Intersection(intersection, p, n);
    }

    @Override
    public BoundingBox getBoundingBox() {
        return boundingBox;
    }
}
