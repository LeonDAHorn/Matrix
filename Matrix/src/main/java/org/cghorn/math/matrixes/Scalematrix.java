package org.cghorn.math.matrixes;

public class Scalematrix extends Matrix{
    public Scalematrix(double x, double y, double z) {
        super(4, 4);
        type = MatrixType.SCALE_MATRIX;
        matrix = new double[][]{
                {x, 0, 0, 0},
                {0, y, 0, 0},
                {0, 0, z, 0},
                {0, 0, 0, 1}
        };
    }
}
