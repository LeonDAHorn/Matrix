package org.cghorn.objects;

import org.cghorn.math.vectorsandpoints.Point;

public class Triangle {

    public Point[] points = new Point[3];

    public Material material;

    public Triangle(Point pointA, Point pointB, Point pointC){
        points[0] = pointA;
        points[1] = pointB;
        points[2] = pointC;
    }

    public Triangle assignPoints(Point pointA, Point pointB, Point pointC){
        points[0] = pointA;
        points[1] = pointB;
        points[2] = pointC;
        return this;
    }

    public void assignMaterial(Material material){
        this.material = material;
    }

}
