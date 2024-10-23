package org.cghorn.math.matrixes;

public class Rotationmatrix extends Matrix{
    public Rotationmatrix(String axis, double degrees) {
        super(4, 4);
        type = MatrixType.ROTATION_MATRIX;
        double cos = (float) Math.cos(Math.toRadians(degrees));
        double sin = (float) Math.sin(Math.toRadians(degrees));
        switch (axis){
            case "x":
                matrix = new double[][]{
                        {1, 0, 0, 0},
                        {0, cos, sin*-1, 0},
                        {0, sin, cos, 0},
                        {0, 0, 0, 1}
                };
                break;
            case "y":
                matrix = new double[][]{
                        {cos, 0, sin, 0},
                        {0, 1, 0, 0},
                        {sin*-1, 0, cos, 0},
                        {0, 0, 0, 1}
                };
                break;
            case "z":
                matrix = new double[][]{
                        {cos, sin*-1, 0, 0},
                        {sin, cos, 0, 0},
                        {0, 0, 1, 0},
                        {0, 0, 0, 1}
                };
                break;
        }
    }
}
