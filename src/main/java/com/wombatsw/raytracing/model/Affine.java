package com.wombatsw.raytracing.model;

/**
 * Affine transformation matrix
 */
public class Affine {
    private static final int DIM = 4;

    // Matrix addressed as [row][column]
    private double[][] matrix;
    private double[][] inverse;

    public Affine() {
        matrix = createIdentity();
        inverse = createIdentity();
    }

    /**
     * Apply this Affine matrix to a triplet (a column matrix)
     *
     * @param triplet The point or vector
     * @param isPoint Whether the triplet is a point
     * @return The new triplet
     */
    public Triplet apply(final Triplet triplet, final boolean isPoint) {
        return apply(matrix, triplet, isPoint);
    }

    /**
     * Apply the inverse of this Affine matrix to a triplet (a column matrix)
     *
     * @param triplet The point or vector
     * @param isPoint Whether the triplet is a point
     * @return The new triplet
     */
    public Triplet invert(final Triplet triplet, final boolean isPoint) {
        return apply(inverse, triplet, isPoint);
    }

    /**
     * Add a translation to the Affine matrix
     *
     * @param vector The translation vector
     * @return This Affine matrix
     */
    public Affine translate(final Triplet vector) {
        double[][] translation = createIdentity();
        for (int i = 0; i < DIM - 1; i++) {
            translation[i][DIM - 1] = vector.getValue(i);
        }
        matrix = multiply(translation, matrix);

        for (int i = 0; i < DIM - 1; i++) {
            translation[i][DIM - 1] = -vector.getValue(i);
        }
        inverse = multiply(inverse, translation);
        return this;
    }

    /**
     * Add a rotation around the X axis to the Affine matrix
     *
     * @param angle The angle of rotation in degrees
     * @return This Affine matrix
     */
    public Affine rotateX(final double angle) {
        return rotate(new Triplet(1, 0, 0), angle);
    }

    /**
     * Add a rotation around the Y axis to the Affine matrix
     *
     * @param angle The angle of rotation in degrees
     * @return This Affine matrix
     */
    public Affine rotateY(final double angle) {
        return rotate(new Triplet(0, 1, 0), angle);
    }

    /**
     * Add a rotation around the Z axis to the Affine matrix
     *
     * @param angle The angle of rotation in degrees
     * @return This Affine matrix
     */
    public Affine rotateZ(final double angle) {
        return rotate(new Triplet(0, 0, 1), angle);
    }

    /**
     * Add a rotation to the Affine matrix
     *
     * @param axis  The axis of rotation
     * @param angle The angle of rotation in degrees
     * @return This Affine matrix
     */
    public Affine rotate(final Triplet axis, final double angle) {
        Triplet axisNorm = axis.copy().normalize();

        double[][] rotation = createRotation(axisNorm, angle);
        matrix = multiply(rotation, matrix);

        double[][] invRotation = createRotation(axisNorm, -angle);
        inverse = multiply(inverse, invRotation);
        return this;
    }

    /**
     * Create a rotation matrix along the indicated axis
     *
     * @param axis  The axis of rotation
     * @param angle The angle of rotation in degrees
     * @return The rotation matrix
     */
    private static double[][] createRotation(final Triplet axis, final double angle) {
        double theta = Math.toRadians(angle);
        double[][] result = new double[DIM][DIM];
        double x = axis.getX();
        double y = axis.getY();
        double z = axis.getZ();
        double cosTheta = Math.cos(theta);
        double sinTheta = Math.sin(theta);

        result[0][0] = x * x * (1 - cosTheta) + cosTheta;
        result[0][1] = y * x * (1 - cosTheta) - z * sinTheta;
        result[0][2] = z * x * (1 - cosTheta) + y * sinTheta;

        result[1][0] = x * y * (1 - cosTheta) + z * sinTheta;
        result[1][1] = y * y * (1 - cosTheta) + cosTheta;
        result[1][2] = z * y * (1 - cosTheta) - x * sinTheta;

        result[2][0] = x * z * (1 - cosTheta) - y * sinTheta;
        result[2][1] = y * z * (1 - cosTheta) + x * sinTheta;
        result[2][2] = z * z * (1 - cosTheta) + cosTheta;

        result[3][3] = 1;
        return result;
    }

    /**
     * Apply this matrix to a triplet (a column matrix)
     *
     * @param mat     The matrix
     * @param triplet The point or vector
     * @param isPoint Whether the triplet is a point
     * @return The new triplet
     */
    private Triplet apply(final double[][] mat, final Triplet triplet, final boolean isPoint) {
        double x = 0;
        double y = 0;
        double z = 0;

        for (int i = 0; i < DIM - 1; i++) {
            double val = triplet.getValue(i);
            x += mat[0][i] * val;
            y += mat[1][i] * val;
            z += mat[2][i] * val;
        }

        if (isPoint) {
            x += mat[0][DIM - 1];
            y += mat[1][DIM - 1];
            z += mat[2][DIM - 1];
        }

        return new Triplet(x, y, z);
    }

    /**
     * Multiply matrices
     *
     * @param m1 The first matrix
     * @param m2 The second matrix
     * @return The new matrix
     */
    private static double[][] multiply(double[][] m1, double[][] m2) {
        double[][] result = new double[DIM][DIM];
        for (int i = 0; i < DIM; i++) {
            for (int j = 0; j < DIM; j++) {
                result[i][j] = 0;
                for (int k = 0; k < DIM; k++) {
                    result[i][j] += m1[i][k] * m2[k][j];
                }
            }
        }
        return result;
    }

    /**
     * Create an identity matrix
     *
     * @return The identity matrix
     */
    private static double[][] createIdentity() {
        double[][] matrix = new double[DIM][DIM];
        for (int i = 0; i < DIM; i++) {
            matrix[i][i] = 1;
        }
        return matrix;
    }
}
