package com.bfchuan.mini.ui.myguis;

import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JLabel;

/**
 * 此类是图片标签
 * 作者:Loenidas
 * 时间:2012-5-10
 * piaobomengxiang@163.com
 * 版本:v1.0
 *
 */
@SuppressWarnings("serial")
public class ImageLabel extends JLabel {

	private Image image;

	/**
	 * 构造函数
	 * 
	 * @param img是传入的Image
	 */
	public ImageLabel(Image img) {
		this.image = img;
		this.setCursor(new Cursor(Cursor.HAND_CURSOR));
	}

	@Override
	public void paintComponent(Graphics g) {
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		g.dispose();
	}
}
