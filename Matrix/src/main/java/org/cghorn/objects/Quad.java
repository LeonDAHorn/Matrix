package org.cghorn.objects;

import org.cghorn.math.vectorsandpoints.Point;

public class Quad {

    public Point[] points = new Point[6];

    public Material material;

    public Quad(Triangle tr1, Triangle tr2){
        int i = 0;
        for(Point p : tr1.points){
            points[i] = p;
            i++;
        }
        for(Point p : tr2.points){
            points[i] = p;
            i++;
        }
    }

    public Quad assignTriangles(Triangle tr1, Triangle tr2){
        int i = 0;
        for(Point p : tr1.points){
            points[i] = p;
            i++;
        }
        for(Point p : tr2.points){
            points[i] = p;
            i++;
        }
        return this;
    }

    public void assignMaterial(Material material){
        this.material = material;
    }

}
