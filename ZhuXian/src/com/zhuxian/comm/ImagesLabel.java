package com.zhuxian.comm;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JLabel;

public class ImagesLabel extends JLabel {

	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2D = (Graphics2D) g;
		g2D.setStroke(new BasicStroke(5.0f));
		g2D.drawRoundRect(0, 0, this.getWidth(), this.getHeight(),
				0, 0);
	}

}
