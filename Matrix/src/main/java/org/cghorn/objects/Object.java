package org.cghorn.objects;

import org.cghorn.math.vectorsandpoints.Point;

public class Object {

    public Triangle[] triangles;

    Point[] points;

    Point position;

    public Material material;

    TransformNode transformNode;

    public Object(Point position, Material material, Triangle... triangles){
        this.triangles = triangles;
        this.material = material;
        for(Triangle t : this.triangles){
            t.assignMaterial(this.material);
        }
        this.position = position;
        this.transformNode = new TransformNode(null, assignPoints());
    }

    public Object(Material material, Triangle... triangles){
        this.triangles = triangles;
        this.material = material;
        for(Triangle t : this.triangles){
            t.assignMaterial(this.material);
        }
        this.transformNode = new TransformNode(null, assignPoints());

        double cx = 0d;
        double cy = 0d;
        double cz = 0d;
        for(Point p : points){
            cx += p.x;
            cy += p.y;
            cz += p.z;
        }
        cx = cx/points.length;
        cy = cy/points.length;
        cz = cz/points.length;
        position = new Point(cx, cy, cz);
    }

    public Point[] assignPoints(){
        Point[] points = new Point[triangles.length*3];
        int index = 0;
        for(Triangle t : triangles){
            for(Point p : t.points){
                points[index] = p;
                index++;
            }
        }
        this.points = points;
        return this.points;
    }

    public Triangle[] convertToTriangles(){
        Triangle[] tr = this.triangles;
        int index = 0;
        int pindex = 0;
        for(Triangle t : tr){
            triangles[index] = t.assignPoints(points[pindex], points[pindex+1], points[pindex+2]);
            index++;
            pindex += 3;
        }
        return triangles;
    }

    public void move(double x, double y, double z){
        points = transformNode.move(x, y, z);
        triangles = convertToTriangles();
    }

    public void rotate(String axis, double degrees){
        points = transformNode.rotate(axis, degrees);
        triangles = convertToTriangles();
    }

    public void scale(double x, double y, double z){
        points = transformNode.scale(x, y, z);
        triangles = convertToTriangles();
    }

    public void assignMaterial(Material material) {
        this.material = material;
        for(Triangle t : this.triangles){
            t.assignMaterial(this.material);
        }
    }
}
