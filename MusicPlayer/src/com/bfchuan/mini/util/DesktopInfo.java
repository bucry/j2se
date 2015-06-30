package com.bfchuan.mini.util;

import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Toolkit;

/**
 * 桌面信息类
 * 作者:Loenidas
 * 时间:2012-5-10
 * piaobomengxiang@163.com
 * 版本:v1.0
 *
 */
public class DesktopInfo {

	/**
	 * 得到屏幕的最大化尺寸
	 * 
	 * @return
	 */
	public static Rectangle getMaxSize() {
		/*Dimension scrSize = Toolkit.getDefaultToolkit().getScreenSize();
		Insets scrInsets = Toolkit.getDefaultToolkit().getScreenInsets(
				GraphicsEnvironment.getLocalGraphicsEnvironment()
						.getDefaultScreenDevice().getDefaultConfiguration());
		return new Rectangle(scrInsets.left, scrInsets.top, scrSize.width
				- scrInsets.left - scrInsets.right, scrSize.height
				- scrInsets.top - scrInsets.bottom);*/
		return GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
	}

	/**
	 * 得到最大化Dimension
	 * bug：在Linux可能不管用，如：Ubuntu10.04
	 * 
	 * @return
	 */
	public static Dimension getFullDimension() {
		Dimension scrSize = Toolkit.getDefaultToolkit().getScreenSize();
		return scrSize;
	}

	/**
	 * 得到系统窗体要显示的Dimension
	 * 
	 * @return
	 */
	public static Dimension getDimension() {
		Dimension rect = getFullDimension();
		double x = 890 / 1280.0;
		double y = 590 / 800.0;
		int width = (int) (rect.width * x);
		int height = (int) (rect.height * y);
		Dimension dim = new Dimension(width, height);
		return dim;
	}

}
