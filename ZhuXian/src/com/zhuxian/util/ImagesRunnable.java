package com.zhuxian.util;

import java.awt.image.BufferedImage;

public class ImagesRunnable implements Runnable{
	private Thread t = null;
	private static int i = 0;
	// 当前显示的人物图片
	public static BufferedImage showImage;
	
	public ImagesRunnable(){
		new StaticImagesValue();
		showImage = StaticImagesValue.allMarioImage.get(0);
		t = new Thread(this);
		t.start();
	}

	@Override
	public void run() {
		try {// 休眠时间
			for(;i<29;i++){
				if(i > 27)
					i = 0;
				System.out.println(i);
				showImage = StaticImagesValue.allMarioImage.get(0);
				Thread.sleep(50);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}

	public BufferedImage getShowImage() {
		return showImage;
	}

	public void setShowImage(BufferedImage showImage1) {
		showImage = showImage1;
	}


}
