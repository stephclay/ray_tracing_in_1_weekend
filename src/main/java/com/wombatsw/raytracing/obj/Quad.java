package com.wombatsw.raytracing.obj;

import com.wombatsw.raytracing.engine.BoundingBox;
import com.wombatsw.raytracing.material.Material;
import com.wombatsw.raytracing.model.Intersection;
import com.wombatsw.raytracing.model.Interval;
import com.wombatsw.raytracing.model.Ray;
import com.wombatsw.raytracing.model.Vector3;

import static com.wombatsw.raytracing.Constants.EPSILON;
import static com.wombatsw.raytracing.Constants.UNIT_INTERVAL;

/**
 * A quadrilateral
 */
public class Quad extends AbstractObj {
    private final Vector3 q;
    private final Vector3 u;
    private final Vector3 v;
    private final Vector3 n;
    private final Vector3 w;
    private final double d;
    private final BoundingBox boundingBox;

    /**
     * Stationary quad
     *
     * @param q        One corner of the quad
     * @param u        First vector to adjacent vertex of q
     * @param v        Second vector to adjacent vertex of q
     * @param material Material sphere is made of
     */
    public Quad(final Vector3 q, final Vector3 u, final Vector3 v, final Material material) {
        super(material);
        this.q = q;
        this.u = u;
        this.v = v;

        n = u.cross(v);
        w = n.copy().div(n.dot(n));
        n.normalize();

        d = n.dot(q);

        n.setImmutable();
        w.setImmutable();
        this.boundingBox = createBoundingBox();
    }

    @Override
    public Intersection intersect(final Ray ray, final Interval tRange) {
        double nd = n.dot(ray.direction());

        // If the ray is parallel to the plane, then no intersection
        if (Math.abs(nd) < EPSILON) {
            return null;
        }

        // Plane interection is out of range
        double t = (d - ray.origin().dot(n)) / nd;
        if (!tRange.contains(t)) {
            return null;
        }

        Vector3 p = ray.at(t);
        Vector3 planarHit = p.copy().sub(q);
        double a = w.dot(planarHit.cross(v));
        double b = w.dot(u.cross(planarHit));

        if (!UNIT_INTERVAL.contains(a) || !UNIT_INTERVAL.contains(b)) {
            return null;
        }

        return new Intersection(ray, t, p, n.copy(), a, b, getMaterial());
    }

    @Override
    public BoundingBox getBoundingBox() {
        return boundingBox;
    }

    /**
     * Create the bounding box for this object
     *
     * @return The {@link BoundingBox}
     */
    private BoundingBox createBoundingBox() {
        BoundingBox bbDiag1 = new BoundingBox(q, q.copy().add(u).add(v));
        BoundingBox bbDiag2 = new BoundingBox(q.copy().add(u), q.copy().add(v));
        return new BoundingBox(bbDiag1, bbDiag2);
    }
}
