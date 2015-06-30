package com.bfchuan.mini.ui.myguis;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;

/**
 * 图片按钮
 * 作者:Loenidas
 * 时间:2012-5-10
 * piaobomengxiang@163.com
 * 版本:v1.0
 *
 */
@SuppressWarnings("serial")
public class ImageButton extends JButton {

	public ImageButton(Icon icon) {
		this.setIcon(icon);
		this.setBorderPainted(false);
		this.setCursor(new Cursor(Cursor.HAND_CURSOR));
		this.setBorder(BorderFactory.createLineBorder(Color.lightGray, 1));
		this.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseEntered(MouseEvent e) {
				setBorderPainted(true);
				ImageButton.this.repaint();
			}

			@Override
			public void mouseExited(MouseEvent e) {
				setBorderPainted(false);
				ImageButton.this.repaint();
			}
		});
	}

}
