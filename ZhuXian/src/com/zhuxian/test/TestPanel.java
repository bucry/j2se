package com.zhuxian.test;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import com.zhuxian.ui.Login;

public class TestPanel extends JPanel {


	private static final long serialVersionUID = -2920623990692263553L;

	public TestPanel() {
		this.setLayout(null);
	}

	protected void paintComponent(Graphics g) {
		setOpaque(true);
		super.paintComponent(g);
		BufferedImage bgImage = null;
		try {
			bgImage = ImageIO.read(new File("images/bg1.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 使用双缓冲技术，防止g的反复调用
		BufferedImage image = new BufferedImage(800, 600,
		BufferedImage.TYPE_3BYTE_BGR);// 创建缓冲图片
		Graphics g2 = image.getGraphics();// 从缓冲图片上面获取画布
		g2.drawImage(bgImage, 0, 0, this);// 将图片画在窗体上面
		// 把缓冲图片绘制到真正的窗体中
		g.drawImage(image, 0, 0, Login.loginFrame);
	}
}
