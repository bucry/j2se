package com.qq.frame;

import java.awt.Graphics;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class BgPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void paintComponent(Graphics g) {	
		int x=0,y=0;
		URL imgUrl=getClass().getResource("pics/afternoon.jpg");
		URL textureUrl=getClass().getResource("pics/texture.png");
		ImageIcon img=new ImageIcon(imgUrl);
		ImageIcon txt=new ImageIcon(textureUrl);
		g.drawImage(img.getImage(), x, y,this.getSize().width,this.getSize().height,this);
		g.drawImage(txt.getImage(), x-50, y,this.getSize().width+100,this.getSize().height,this);
	}
}
