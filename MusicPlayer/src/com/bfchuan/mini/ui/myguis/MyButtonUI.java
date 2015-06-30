package com.bfchuan.mini.ui.myguis;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JComponent;
import javax.swing.plaf.ButtonUI;

/**
 * 此类为自定义Button的UI类
 * 作者:Loenidas
 * 时间:2012-5-10
 * piaobomengxiang@163.com
 * 版本:v1.0
 *
 */
public class MyButtonUI extends ButtonUI {

	@Override
	public void paint(Graphics g, JComponent c) {
		g.setColor(Color.red);
		g.fillRect(0, 0, c.getWidth(), c.getHeight());
		g.dispose();
	}

}
