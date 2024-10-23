package org.cghorn.math.matrixes;

public class Translationmatrix extends Matrix{


    public Translationmatrix(double x, double y, double z) {
        super(4, 4);
        type = MatrixType.TRANSLATION_MATRIX;
        matrix = new double[][]{
                {1, 0, 0, x},
                {0, 1, 0, y},
                {0, 0, 1, z},
                {0, 0, 0, 1}
        };
    }
}
