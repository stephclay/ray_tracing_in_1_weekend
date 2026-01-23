package com.wombatsw.raytracing.obj;

import com.wombatsw.raytracing.model.BoundingBox;
import com.wombatsw.raytracing.model.Affine;
import com.wombatsw.raytracing.model.Intersection;
import com.wombatsw.raytracing.model.Interval;
import com.wombatsw.raytracing.model.Ray;
import com.wombatsw.raytracing.model.Triplet;
import lombok.Getter;
import lombok.ToString;

/**
 * An object made from transforming another object.
 */
@Getter
@ToString(callSuper = true)
public class Transform extends AbstractObj {
    private final AbstractObj object;
    private final Affine affine;

    public Transform(final AbstractObj object, final Affine affine) {
        super(object.getMaterial(), new BoundingBox(object.getBoundingBox(), affine));

        this.object = object;
        this.affine = affine;
    }

    @Override
    public Intersection intersect(final Ray ray, final Interval tRange) {
        // Transform ray to object space
        Triplet newOrigin = affine.invert(ray.origin(), true);
        Triplet newDir = affine.invert(ray.direction(), false);

        Ray rotatedRay = new Ray(newOrigin, newDir, ray.time());

        // Determine intersection
        Intersection intersection = object.intersect(rotatedRay, tRange);
        if (intersection == null) {
            return null;
        }

        // Transform intersection from object space to world space
        Triplet p = affine.apply(intersection.getP(), true);
        Triplet n = affine.invert(intersection.getN(), false);

        return new Intersection(intersection, p, n);
    }
}
