package org.cghorn.objects;

import org.cghorn.math.vectorsandpoints.Point;

public class TransformNode {

    TransformNode parent;

    Point[] points;

    Point center;

    public TransformNode(TransformNode parent, Point[] points){
        this.parent = parent;
        this.points = points;

        double cx = 0d;
        double cy = 0d;
        double cz = 0d;
        for(Point p : this.points){
            cx += p.x;
            cy += p.y;
            cz += p.z;
        }
        cx = cx/this.points.length;
        cy = cy/this.points.length;
        cz = cz/this.points.length;
        center = new Point(cx, cy, cz);
    }

    public Point[] move(double x, double z, double y){
        Point[] ps = points;
        int index = 0;
        for(Point p : points){
            ps[index] = p.translate(x, y, z);
            index++;
        }
        points = ps;
        return ps;
    }

    public Point[] rotate(String axis, double degrees){
        Point[] ps = points;
        int index = 0;
        for(Point p : points){
            if(parent == null){
                ps[index] = p.rotateAround(center, axis, degrees);
            } else {
                ps[index] = p.rotateAround(parent.center, axis, degrees);
            }

            index++;
        }
        points = ps;
        return ps;
    }

    public Point[] scale(double x, double y, double z){
        Point[] ps = points;
        int index = 0;
        for(Point p : points){
            ps[index] = p.scale(x, y, z);
            index++;
        }
        points = ps;
        return ps;
    }


}
