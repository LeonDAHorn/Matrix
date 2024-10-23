package org.cghorn.rendering;

import org.cghorn.math.vectorsandpoints.Point;
import org.cghorn.objects.Material;
import org.cghorn.objects.Object;
import org.cghorn.objects.Triangle;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Screen {

    public JFrame jFrame;

    public Scene scene;

    public RenderPanel jPanel;

    public Point Camerapoint = new Point(0, 0, -100);

    Dimension d = new Dimension(1280, 720);

    public BufferedImage img1 = new BufferedImage(d.width, d.height, BufferedImage.TYPE_INT_RGB);
    public BufferedImage img2 = new BufferedImage(d.width, d.height, BufferedImage.TYPE_INT_RGB);

    double area(Point a, Point b, Point c){
        return Math.abs((a.x*(b.y-c.y) + b.x*(c.y-a.y)+
                c.x*(a.y- b.y))/2.0);
    }
    boolean isInside(Point a, Point b, Point c, Point p) {
        /* Calculate area of triangle ABC */
        double A = area(a, b, c);

        /* Calculate area of triangle PBC */
        double A1 = area(p, b, c);

        /* Calculate area of triangle PAC */
        double A2 = area(a, p, c);

        /* Calculate area of triangle PAB */
        double A3 = area(a, b, p);

        /* Check if sum of A1, A2 and A3 is same as A */
        return (A == A1 + A2 + A3);
    }
    public void render(){
        Random r = new Random();
        //o.rotate("y", 10);
        o.move(30, 0, 0);
        o.rotate("y", 10);
        //o.rotate("x", 10);
        //o.rotate("z", 10);
        int mul = r.nextBoolean() ? -1 : 1;
        //o.move(r.nextInt(10)*mul, 0, 0);
        //o.assignMaterial(new Material(r.nextInt(255), r.nextInt(255), r.nextInt(255)));
        Point pP;
        Color color;
        for (int x = 0; x < img1.getWidth(); x++) {
            for (int y = 0; y < img1.getHeight(); y++) {
                pP = new Point(x, y, 0);
                for(Triangle t : scene.triangles){
                    if(isInside(t.points[0], t.points[1], t.points[2], pP)){
                        color = new Color(t.material.r, t.material.g, t.material.b);
                        img1.setRGB(x, y, color.getRGB());
                    } else {
                        //color = new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255));
                        color = Color.BLACK;
                        img1.setRGB(x, y, color.getRGB());
                    }
                }
            }
        }
    }

    public void swap(){
        img2 = img1;
        jPanel.setImage(img2);
        render();
    }

    public Object o = new Object(new Material(77, 120, 0), new Triangle(new Point(500, 500, 0), new Point(500, 300, 0), new Point(200, 500, 0)));
    //public Object o2 = new Object(new Material(100, 100, 0), new Triangle(new Point(800, 500, 0), new Point(300, 300, 0), new Point(400, 500, 0)));

    public Screen(){
        scene = new Scene(o);
        jFrame = new JFrame("3D Engine");
        jPanel = new RenderPanel(img2);
        jPanel.setSize(d);
        jFrame.add(jPanel);
        jFrame.setSize(d);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setVisible(true);
    }

}
