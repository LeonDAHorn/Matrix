package org.cghorn;

import org.cghorn.rendering.Screen;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        Screen s = new Screen();

        Timer t = new Timer(13, (ev)->{
            s.jPanel.repaint();
            s.jPanel.getToolkit().sync();
            s.jPanel.requestFocus();
            s.swap();
        });
        t.start();
        //new Point(5, 5, 5).rotateAround(new Line(new Point(0, 0, 0), new Point(-2, 3, 2)), 90d).show();
    }
}