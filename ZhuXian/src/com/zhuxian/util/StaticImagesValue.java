package com.zhuxian.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public class StaticImagesValue {
	
	public static List<BufferedImage> allMarioImage = new ArrayList<BufferedImage>();
	public static String imagePath = "images/animos/";// 路径
	
	
	public StaticImagesValue(){
		System.out.println("初始化动物------");
		init();
	}
	public static void init() {
		// 初始化所有Mario的图片
		for (int i = 1; i <= 10; i++) {
			try {
//				System.out.println(System.getProperty("user.dir") + "\\"+ i + ".gif");
				// 将所有的图片初始化，并作为BufferedImage对象存到队列中去，使用ImageIO的read方法将bufferedImage对象指向图片
				allMarioImage.add(ImageIO.read(new File(imagePath + i + ".png")));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
