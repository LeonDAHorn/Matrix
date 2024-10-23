package org.cghorn.math.objects;

import org.cghorn.math.vectorsandpoints.Point;

public class Line {

    public Point p1, p2;

    Function function;

    public Line(Point p1, Point p2){
        this.p1 = p1;
        this.p2 = p2;
        function = new Function(p1, p2);
    }

    public Point getPoint(double x){
        return new Point(x, function.functionY(x), function.functionZ(x));
    }

    private class Function{

        Point p1, p2;
        double my, mz, by, bz;
        public Function(Point p1, Point p2){
            this.p1 = p1;
            this.p2 = p2;
            my = ((p1.y-p2.y)/(p1.x-p2.x));
            mz = ((p1.z-p2.z)/(p1.x-p2.x));
            by = (p1.x*p2.y-p2.x*p1.y)/(p1.x-p2.x);
            bz = (p1.x*p2.z-p2.x*p1.z)/(p1.x-p2.x);
        }
        public double functionY(double x){
            double r = (my*x)+by;
            return r == -0d ? 0d : r;
        }

        public double functionZ(double x){
            double r = (mz*x)+bz;
            return r == -0d ? 0d : r;
        }

    }



}
