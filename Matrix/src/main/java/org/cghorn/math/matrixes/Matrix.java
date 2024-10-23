package org.cghorn.math.matrixes;

import org.cghorn.math.vectorsandpoints.Point;
import org.cghorn.math.vectorsandpoints.Vector;

/**
 * 3D Matrix class using homogenous coordinates.
 * 4x4 Matrix
 */
public class Matrix {

    int col;
    int row;
    double[][] matrix;

    MatrixType type = MatrixType.MATRIX;

    /**
     * Matrix types to describe their different functions.
     * The default is MATRIX.
     */
    enum MatrixType {

        TRANSLATION_MATRIX,
        SCALE_MATRIX,
        ROTATION_MATRIX,
        VECTOR,
        POINT,
        MATRIX
    }

    /**
     * Generic Matrix.
     * Allows the creation of an empty Matrix with all values at 0.
     * @param row
     * @param col
     */
    public Matrix(int row, int col){
        this.col = col;
        this.row = row;
        matrix = new double[row][col];
        if(col == 1 || row == 1){
            type = MatrixType.VECTOR;
        }
    }

    /**
     * Matrix which can take in multiple double values
     * as long as the sum of all values is equal to row*col.
     * @param row
     * @param col
     * @param values
     */
    public Matrix(int row, int col, double... values){
        this.col = col;
        this.row = row;
        matrix = new double[row][col];
        int r = 0;
        int c = 0;
        for(double f : values){
            matrix[r][c] = f;
            c++;
            if(c > col-1){
                c = 0;
                r++;
            }
        }
        if(col == 1 || row == 1){
            if(col == 1){
                if(matrix[row-1][0] == 0f) type = MatrixType.VECTOR;
                if(matrix[row-1][0] == 1f) type = MatrixType.POINT;
            }
            if(row == 1){
                if(matrix[0][col-1] == 0f) type = MatrixType.VECTOR;
                if(matrix[0][col-1] == 1f) type = MatrixType.POINT;
            }
        }
    }

    /**
     * Multiplicative Scalar. Equivalent to M*S(n).
     * @param s
     * @return A new Matrix multiplied by s
     */
    public Matrix scalarMult(double s){
        for(int r = 0; r < this.row; r++){
            for(int c = 0; c < this.col; c++){
                matrix[r][c] = matrix[r][c]*s;
            }
        }
        return this;
    }

    /**
     * Reciprocal Scalar. Equivalent to M/S(n).
     * @param s
     * @return A new Matrix divided by s
     */
    public Matrix scalarDiv(double s){
        return scalarMult(1d/s);
    }

    /**
     * Matrix Multiplication. Equivalent to M*M.
     * @param m
     * @return A new Matrix multiplied by m
     */
    public Matrix multiply(Matrix m){
        Matrix newMatrix = new Matrix(this.row, m.col);
        if(this.col != m.row){
            return newMatrix;
        } else {
            for(int i = 0; i < newMatrix.row; i++){
                for(int j = 0; j < newMatrix.col; j++){
                    double sum = 0d;
                    for (int k = 0; k < this.col; k++){
                        sum += this.matrix[i][k]*m.matrix[k][j];
                    }
                    newMatrix.matrix[i][j] = sum;
                }
            }
        }
        return newMatrix;
    }

    /**
     * Matrix addition. Equivalent to M+M.
     * @param m
     * @return A new Matrix + m
     */
    public Matrix add(Matrix m){
        if(this.row != m.row || this.col != m.col){
            return this;
        }
        Matrix newMatrix = new Matrix(this.row, this.col);
        for(int r = 0; r < this.row; r++){
            for(int c = 0; c < this.col; c++){
                newMatrix.matrix[r][c] = this.matrix[r][c]+m.matrix[r][c];
            }
        }
        return newMatrix;
    }

    /**
     * Matrix subtraction. Equivalent to M-M.
     * @param m
     * @return A new Matrix - m
     */
    public Matrix subtract(Matrix m){
        if(this.row != m.row || this.col != m.col){
            return this;
        }
        Matrix newMatrix = new Matrix(this.row, this.col);
        for(int r = 0; r < this.row; r++){
            for(int c = 0; c < this.col; c++){
                newMatrix.matrix[r][c] = this.matrix[r][c]-m.matrix[r][c];
            }
        }
        return newMatrix;
    }

    /**
     * The transpose of a Matrix. Equivalent to M^t.
     * @return A new transposed Matrix
     */
    public Matrix transpose(){
        Matrix newMatrix = new Matrix(this.col, this.row);
        for(int r = 0; r < newMatrix.row; r++){
            for(int c = 0; c < newMatrix.col; c++){
                newMatrix.matrix[r][c] = this.matrix[c][r];
            }
        }
        return newMatrix;
    }

    /**
     * The Inverse of a Matrix using the Gauss-Jordan elimination method.
     * Equivalent to M^-1.
     * Does not work for any non-square Matrix.
     * @return A new inverted Matrix
     */
    public Matrix inverse() {
        if(row != col){
            return this;
        }
        int n = matrix.length;
        Matrix result = new Matrix(row, col);
        Matrix tmp = new Matrix(row, col*2);

        for (int i = 0; i < n; ++i) {
            System.arraycopy(matrix[i], 0, tmp.matrix[i], 0, n);
            tmp.matrix[i][i + n] = 1;
        }

        for (int i = 0; i < n; ++i) {
            double e = tmp.matrix[i][i];
            if (e == 0) {
                for (int j = i + 1; j < n; ++j) {
                    if (tmp.matrix[j][i] != 0) {
                        double[] t = tmp.matrix[i];
                        tmp.matrix[i] = tmp.matrix[j];
                        tmp.matrix[j] = t;
                        break;
                    }
                }
                e = tmp.matrix[i][i];
                if (e == 0) {
                    return this;
                }
            }
            for (int j = 0; j < n * 2; ++j) {
                tmp.matrix[i][j] /= e;
            }
            for (int j = 0; j < n; ++j) {
                if (i != j) {
                    e = tmp.matrix[j][i];
                    for (int k = 0; k < n * 2; ++k) {
                        tmp.matrix[j][k] -= tmp.matrix[i][k] * e;
                    }
                }
            }
        }

        for (int i = 0; i < n; ++i) {
            System.arraycopy(tmp.matrix[i], n, result.matrix[i], 0, n);
        }

        return result;
    }

    /**
     * The classical Adjoint of a Matrix.
     * Does not work for any non-square Matrix.
     * @return A new adjugated Matrix.
     */
    public Matrix adjoint() {
        if(row != col){
            return this;
        }
        int n = matrix.length;
        Matrix adj = new Matrix(n, n);
        if (n == 1) {
            adj.matrix[0][0] = 1;
            return adj;
        }
        int sign;
        Matrix temp = new Matrix(n - 1, n - 1);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                getCofactor(this, temp, i, j, n);
                sign = ((i + j) % 2 == 0) ? 1 : -1;
                adj.matrix[j][i] = (sign) * (determinant(temp, n - 1));
            }
        }
        for (int i = 0; i < adj.row; i++){
            for (int j = 0; j < adj.col; j++){
                if(adj.matrix[i][j] == -0.0d){
                    adj.matrix[i][j] = 0.0d;
                }
            }
        }
        return adj;
    }

    /**
     * Writes the cofactor of a Matrix block into the temp Matrix.
     * @param m
     * @param temp
     * @param p
     * @param q
     * @param n
     */
    public void getCofactor(Matrix m, Matrix temp, int p, int q, int n) {
        int i = 0, j = 0;
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                if (row != p && col != q) {
                    temp.matrix[i][j++] = m.matrix[row][col];
                    if (j == n - 1) {
                        j = 0;
                        i++;
                    }
                }
            }
        }
    }

    /**
     * Gives the determinant of a square Matrix.
     * @param m
     * @param n
     * @return The determinant
     */
    public double determinant(Matrix m, int n) {
        double D = 0;
        if (n == 1)
            return m.matrix[0][0];
        Matrix temp = new Matrix(n, n);
        int sign = 1;
        for (int f = 0; f < n; f++) {
            getCofactor(m, temp, 0, f, n);
            D += sign * m.matrix[0][f] * determinant(temp, n - 1);
            sign = -sign;
        }
        return D;
    }

    /**
     * Transforms a Matrix Object into a Vector Object.
     * @return A new Vector Object
     */
    public Vector toVector(){
        return new Vector(matrix[0][0], matrix[1][0], matrix[2][0]);
    }

    /**
     * Transforms a Matrix Object into a Point Object.
     * @return A new Point Object
     */
    public Point toPoint(){
        return new Point(matrix[0][0], matrix[1][0], matrix[2][0]);
    }

    /**
     * Get the value in a specified row and column of a Matrix.
     * @param r
     * @param c
     * @return A double value stored in a Matrix
     */
    public double get(int r, int c){
        return matrix[r-1][c-1];
    }

    /**
     * Prints this Matrix Object to console.
     */
    public void show(){
        String bracketsfront = "|";
        String bracketsback = bracketsfront;
        System.out.print(this.type.toString().charAt(0));
        for(int r = 0; r < this.row; r++){
            if(r != 0) bracketsfront = " |";
            System.out.print(bracketsfront);
            for(int c = 0; c < this.col; c++){
                String space = " ";
                if(c == this.col-1) space = "";
                System.out.print(this.matrix[r][c]+space);
            }
            System.out.println(bracketsback);
        }
    }

}
