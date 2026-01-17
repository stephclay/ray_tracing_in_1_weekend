package com.wombatsw.raytracing.texture;

import com.wombatsw.raytracing.engine.MathUtils;
import com.wombatsw.raytracing.model.Vector3;

/**
 * Perlin noise generator
 */
public class Perlin {
    private final static int POINT_COUNT = 256;
    private final Vector3[] randVec = new Vector3[POINT_COUNT];
    private final int[] permX = new int[POINT_COUNT];
    private final int[] permY = new int[POINT_COUNT];
    private final int[] permZ = new int[POINT_COUNT];

    public Perlin() {
        for (int i = 0; i < POINT_COUNT; i++) {
            randVec[i] = Vector3.random(-1, 1);
        }

        perlinGeneratePerm(permX);
        perlinGeneratePerm(permY);
        perlinGeneratePerm(permZ);
    }

    /**
     * Get a noise value for the given point
     *
     * @param p The point
     * @return The noise value
     */
    public double noise(final Vector3 p, final double scale) {
        double xs = p.getX() * scale;
        double ys = p.getY() * scale;
        double zs = p.getZ() * scale;

        double u = xs - Math.floor(xs);
        double v = ys - Math.floor(ys);
        double w = zs - Math.floor(zs);

        int i = (int) Math.floor(xs);
        int j = (int) Math.floor(ys);
        int k = (int) Math.floor(zs);

        Vector3[][][] c = new Vector3[2][2][2];
        for (int di = 0; di < 2; di++) {
            for (int dj = 0; dj < 2; dj++) {
                for (int dk = 0; dk < 2; dk++) {
                    c[di][dj][dk] = randVec[permX[(i + di) & 0xff] ^
                            permY[(j + dj) & 0xff] ^
                            permZ[(k + dk) & 0xff]];
                }
            }
        }
        return perlinInterp(c, u, v, w);
    }

    /**
     * Create noise in the given array
     *
     * @param perm The array to contain the noise data
     */
    private static void perlinGeneratePerm(int[] perm) {
        for (int i = 0; i < POINT_COUNT; i++) {
            perm[i] = i;
        }

        permute(perm, POINT_COUNT);
    }

    /**
     * Permute the given array values
     *
     * @param perm The array of values
     * @param n    The number of items to permute
     */
    private static void permute(int[] perm, int n) {
        for (int i = n - 1; i > 0; i--) {
            int target = MathUtils.randomInt(0, i);
            int tmp = perm[i];
            perm[i] = perm[target];
            perm[target] = tmp;
        }
    }

    /**
     * Perlin interpolation based on location in space
     *
     * @param c Noise data
     * @param u X-axis
     * @param v Y-axis
     * @param w Z-axis
     * @return The noise output
     */
    private double perlinInterp(Vector3[][][] c, double u, double v, double w) {
        double accum = 0;

        double uu = u * u * (3 - 2 * u);
        double vv = v * v * (3 - 2 * v);
        double ww = w * w * (3 - 2 * w);

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                for (int k = 0; k < 2; k++) {
                    Vector3 weight = new Vector3(u - i, v - j, w - k);
                    accum += c[i][j][k].dot(weight) *
                            (i * uu + (1 - i) * (1 - uu)) *
                            (j * vv + (1 - j) * (1 - vv)) *
                            (k * ww + (1 - k) * (1 - ww));
                }
            }
        }
        return accum;
    }
}
