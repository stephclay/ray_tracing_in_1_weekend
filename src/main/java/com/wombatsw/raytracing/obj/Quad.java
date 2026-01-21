package com.wombatsw.raytracing.obj;

import com.wombatsw.raytracing.model.BoundingBox;
import com.wombatsw.raytracing.material.Material;
import com.wombatsw.raytracing.model.Intersection;
import com.wombatsw.raytracing.model.Interval;
import com.wombatsw.raytracing.model.Ray;
import com.wombatsw.raytracing.model.Triplet;

import static com.wombatsw.raytracing.Constants.EPSILON;
import static com.wombatsw.raytracing.Constants.UNIT_INTERVAL;

/**
 * A quadrilateral
 */
public class Quad extends AbstractObj {
    private final Triplet q;
    private final Triplet u;
    private final Triplet v;
    private final Triplet n;
    private final Triplet w;
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
    public Quad(final Triplet q, final Triplet u, final Triplet v, final Material material) {
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

    /**
     * Create a box aligned with world coordinates and opposite vertexes at the indicated points
     *
     * @param a        The first vertex
     * @param b        The second vertex
     * @param material The material for the box
     * @return The new box
     */
    public static AbstractObj createBox(final Triplet a, final Triplet b, final Material material) {
        double minZ = Math.min(a.getZ(), b.getZ());
        double minX = Math.min(a.getX(), b.getX());
        double minY = Math.min(a.getY(), b.getY());
        double maxX = Math.max(a.getX(), b.getX());
        double maxY = Math.max(a.getY(), b.getY());
        double maxZ = Math.max(a.getZ(), b.getZ());

        Triplet dx = new Triplet(maxX - minX, 0, 0);
        Triplet dy = new Triplet(0, maxY - minY, 0);
        Triplet dz = new Triplet(0, 0, maxZ - minZ);
        Triplet negDx = dx.copy().negate();
        Triplet negDz = dz.copy().negate();

        return new ObjectList(
                new Quad(new Triplet(minX, minY, maxZ), dx, dy, material), // Front
                new Quad(new Triplet(maxX, minY, maxZ), negDz, dy, material), // Right
                new Quad(new Triplet(maxX, minY, minZ), negDx, dy, material), // Back
                new Quad(new Triplet(minX, minY, minZ), dz, dy, material), // Left
                new Quad(new Triplet(minX, maxY, maxZ), dx, negDz, material), // Top
                new Quad(new Triplet(minX, minY, minZ), dx, dz, material)); // Bottom
    }

    @Override
    public Intersection intersect(final Ray ray, final Interval tRange) {
        double nd = n.dot(ray.direction());

        // If the ray is parallel to the plane, then no intersection
        if (Math.abs(nd) < EPSILON) {
            return null;
        }

        // Plane intersection is out of range
        double t = (d - ray.origin().dot(n)) / nd;
        if (!tRange.contains(t)) {
            return null;
        }

        Triplet p = ray.at(t);
        Triplet planarHit = p.copy().sub(q);
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
