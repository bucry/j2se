package com.zhuxian.util;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import com.zhuxian.ui.Login;

public class ContentPanel extends JPanel {


	private static final long serialVersionUID = -2920623990692263553L;

	public ContentPanel() {
		this.setLayout(null);
	}

	protected void paintComponent(Graphics g) {
		setOpaque(true);
		super.paintComponent(g);
		BufferedImage bgImage = null;
		BufferedImage leftPerson = null;
		BufferedImage rightPerson = null;
		try {
			bgImage = ImageIO.read(new File("images/bg1.png"));
			leftPerson  = ImageIO.read(new File("images/left1.png"));
			rightPerson = ImageIO.read(new File("images/right1.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 使用双缓冲技术，防止g的反复调用
		BufferedImage image = new BufferedImage(800, 600,
		BufferedImage.TYPE_3BYTE_BGR);// 创建缓冲图片
		Graphics g2 = image.getGraphics();// 从缓冲图片上面获取画布
		g2.drawImage(bgImage, 0, 0, this);// 将图片画在窗体上面
		g2.drawImage(leftPerson, 0, 375, this);// 将图片画在窗体上面
		//g2.drawImage(rightPerson, 379, 150, mainPanel);// 将图片画在窗体上面
		g2.drawImage(rightPerson, 500, 270, this);// 将图片画在窗体上面
		// 把缓冲图片绘制到真正的窗体中
		g.drawImage(image, 0, 0, Login.loginFrame);
	}
}
