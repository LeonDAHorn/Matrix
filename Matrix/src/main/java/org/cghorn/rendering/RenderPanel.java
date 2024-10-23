package org.cghorn.rendering;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class RenderPanel extends JPanel {

    BufferedImage img;

    public RenderPanel(BufferedImage img){
        this.img = img;
    }

    public void setImage(BufferedImage img){
        this.img = img;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2 = (Graphics2D) g;
        int w = img.getWidth();
        int h = img.getHeight();
        g2.drawImage(img, 0, 0, w, h, null);
    }
}
