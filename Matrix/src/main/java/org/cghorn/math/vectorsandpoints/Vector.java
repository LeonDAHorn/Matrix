package org.cghorn.math.vectorsandpoints;

import org.cghorn.math.matrixes.Matrix;
import org.cghorn.math.matrixes.Rotationmatrix;

public class Vector {

    public double x, y, z;

    public Vector(double x, double y, double z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector(){
        this.x = 0;
        this.y = 0;
        this.z = 0;
    }

    public Matrix convertToMatrixVector(){
        return new Matrix(4, 1, x, y, z, 0);
    }

    public Vector multiplyBy(Matrix m){
        return this.convertToMatrixVector().multiply(m).toVector();
    }

    public Vector add(Vector v){
        return convertToMatrixVector().add(v.convertToMatrixVector()).toVector();
    }

    public Vector subtract(Vector v){
        return convertToMatrixVector().subtract(v.convertToMatrixVector()).toVector();
    }

    public double dot(Vector v){
        return convertToMatrixVector().transpose().multiply(v.convertToMatrixVector()).get(1, 1);
    }

    public Vector cross(Vector v){
        double nX = (y * v.z) - (z * v.y);
        double nY = (z * v.x) - (x * v.z);
        double nZ = (x * v.y) - (y * v.x);
        return new Vector(nX, nY, nZ);
    }

    public Vector unitVector(Point p1, Point p2){
        Vector vector;
        vector = p2.subtract(p1).convertToMatrixPoint().scalarDiv(p1.subtract(p2).convertToMatrixPoint().toVector().magnitude()).toVector();
        return vector;
    }

    public Vector unitVector() {
        return new Vector(x/magnitude(), y/magnitude(), z/magnitude());
    }

    public double magnitude(){
        return Math.sqrt((x*x)+(y*y)+(z*z));
    }

    public double angleDegrees(Vector v){
        return Math.toDegrees(Math.acos(dot(v)/(magnitude()*v.magnitude())));
    }

    public double angleRadians(Vector v){
        return Math.acos(dot(v)/(magnitude()*v.magnitude()));
    }

    public Vector rotate(String axis, double degrees){
        return new Rotationmatrix(axis, degrees).multiply(convertToMatrixVector()).toVector();
    }

    public Vector rotateAround(Point p, String axis, double degrees){
        Vector newVector;
        newVector = subtract(p.convertToMatrixPoint().toVector());
        newVector = newVector.rotate(axis, degrees);
        newVector = newVector.add(p.convertToMatrixPoint().toVector());
        return newVector;
    }

    public void show(){
        System.out.println("V("+x+", "+y+", "+z+")");
    }
}
