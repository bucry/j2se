package com.bfchuan.mini.ui.myguis;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

/**
 * 带有背景颜色的JPanel
 * 作者:Loenidas
 * 时间:2012-5-10
 * piaobomengxiang@163.com
 * 版本:v1.0
 *
 */
@SuppressWarnings("serial")
public class ColorPanel extends JPanel {

	private Color color;
	
	public ColorPanel(Color color) {
		this.color = color;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(color);
		g.fillRect(0, 0, getWidth(), getHeight());
	}
	
}
