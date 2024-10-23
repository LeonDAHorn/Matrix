package org.cghorn.math.vectorsandpoints;

import org.cghorn.math.matrixes.Matrix;
import org.cghorn.math.matrixes.Rotationmatrix;
import org.cghorn.math.matrixes.Scalematrix;
import org.cghorn.math.matrixes.Translationmatrix;
import org.cghorn.math.objects.Line;

public class Point {

    public double x, y, z;

    public Point(double x, double y, double z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Matrix convertToMatrixPoint(){
        return new Matrix(4, 1, x, y, z, 1);
    }

    public Point multiplyBy(Matrix m){
        return convertToMatrixPoint().multiply(m).toPoint();
    }

    public Point add(Point p){
        return convertToMatrixPoint().add(p.convertToMatrixPoint()).toPoint();
    }

    public Point subtract(Point p){
        return convertToMatrixPoint().subtract(p.convertToMatrixPoint()).toPoint();
    }

    public Point rotate(String axis, double degrees){
        return new Rotationmatrix(axis, degrees).multiply(convertToMatrixPoint()).toPoint();
    }

    public Point rotateAround(Point p, String axis, double degrees){
        Point newPoint;
        newPoint = subtract(p); //set the point p to origin
        newPoint = newPoint.rotate(axis, degrees); //rotate the point
        newPoint = newPoint.add(p); //move the point back
        return newPoint;
    }

    //RodriguesFormel
    public Point rotateAround(Line l, double degrees){
        double rads = Math.toRadians(degrees);
        Point newPoint = new Point(x, y, z);
        Point lpoint1 = l.p1;
        Point lpoint2 = l.p2;
        lpoint2 = lpoint2.subtract(lpoint1);
        Point resetPoint = lpoint1;
        lpoint1 = lpoint1.subtract(lpoint1);
        newPoint = newPoint.subtract(resetPoint);
        Vector unitVector = new Vector().unitVector(lpoint1, lpoint2);
        Vector scale = newPoint.convertToMatrixPoint().scalarMult(Math.cos(rads)).toVector();
        Vector cross = unitVector.cross(newPoint.convertToMatrixPoint().toVector());
        double dot = unitVector.dot(newPoint.convertToMatrixPoint().toVector());
        Vector crossScale = cross.convertToMatrixVector().scalarMult(Math.sin(rads)).toVector();
        Vector unitDot = unitVector.convertToMatrixVector().scalarMult(dot).toVector();
        Vector unitDotScale = unitDot.convertToMatrixVector().scalarMult(1 - Math.cos(rads)).toVector();
        Vector n = scale.add(crossScale).add(unitDotScale);
        newPoint = n.convertToMatrixVector().toPoint();
        newPoint = newPoint.add(resetPoint);
        return newPoint;
    }

    public Point translate(double x, double y, double z){
        return new Translationmatrix(x, y, z).multiply(convertToMatrixPoint()).toPoint();
    }

    public Point scale(double x, double y, double z){
        return new Scalematrix(x, y, z).multiply(convertToMatrixPoint()).toPoint();
    }

    public void show(){
        System.out.println("P("+x+", "+y+", "+z+")");
    }
}
