package com.zhuxian.comm;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JButton;

public class ImagesJButton extends JButton {

    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2D = (Graphics2D)g;
        g2D.setStroke(new BasicStroke(5.0f));
        g2D.drawRoundRect(5, 5, this.getWidth()-10,this.getHeight()-10, 10, 10);
    }
}
