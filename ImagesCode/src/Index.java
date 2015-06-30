import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * 图形识别技术
 * @author marker 
 * 时间：2012-4-6
 * */
public class Index {
	public static void main(String[] args) throws IOException {
 
		BufferedImage bi = (BufferedImage)ImageIO.read(new File("/home/vircom/winxp/share/E.PNG"));
		
		//获取图像的宽度和高度
		int width = bi.getWidth();
		int height = bi.getHeight();
		
		//扫描图片
		for(int i=0;i<height;i++){
			for(int j=0;j<width;j++){//行扫描
				int dip = bi.getRGB(j, i);
				if(dip == -1) System.out.print(" ");
				else          System.out.print("♦");
			}
			System.out.println();//换行
		}
 
	}

}
