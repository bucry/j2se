package MarioGame;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

/***
 * 
 * 存放所有图片的类
 * 
 * */
public class StaticValue {
	// 建立存放所有图片的队列或者图片对象，初始化将写在方法中
	public static List<BufferedImage> allMarioImage = new ArrayList<BufferedImage>();// 马里奥的图片，10
	public static BufferedImage startImage = null;// 开始的图片
	public static BufferedImage endImage = null;// 结束的图片
	public static BufferedImage bgImage = null;// 场景的图片
	public static List<BufferedImage> allFlowerImage = new ArrayList<BufferedImage>();// 食人花的图片，2
	public static List<BufferedImage> allTriangleImage = new ArrayList<BufferedImage>();// 土豆怪的图片,3
	public static List<BufferedImage> allTurtleImage = new ArrayList<BufferedImage>();// 乌龟的图片,5
	public static List<BufferedImage> allObstructionImage = new ArrayList<BufferedImage>();// 所有障碍物的图片,12
	public static BufferedImage marioDeadImage = null;// 马里奥死亡的图片
	public static BufferedImage bulleticon = null;// 子弹的图片
	//public static String imagePath = System.getProperty("user.dir") + "/";// 路径
	
	public static String imagePath = "images/";// 路径

	// System.getProperty("user.dir")+"//bin/";
	// 将图片全部初始化
	
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
//背景图片初始化
		try {

			startImage = ImageIO.read(new File(imagePath + "start.jpg"));
			bgImage = ImageIO.read(new File(imagePath + "firststage.jpg"));
	
//			System.out.println(System.getProperty(imagePath )+ "firststage.jpg");
			endImage = ImageIO.read(new File(imagePath + "firststageend.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		//初始化所有的怪物图片
		for(int i=1;i<=5;i++){
			try{
			if(i<=2){
					allFlowerImage.add(ImageIO.read(new File(imagePath+"flower"+i+".gif")));
					}
			if(i<=3){
				allTriangleImage.add(ImageIO.read(new File(imagePath+"triangle"+i+".gif")));
			}
			allTurtleImage.add(ImageIO.read(new File(imagePath+"Turtle"+i+".gif")));
			}				
			catch(IOException e)
			{
				e.printStackTrace();
			}
		}
		//初始化所有的障碍物图片
		for(int i=1;i<=12;i++)
		{
		try{
			allObstructionImage.add(ImageIO.read(new File(imagePath+"ob"+i+".gif")));
			}
		catch(IOException e)
			{
				e.printStackTrace();
			}
		}
		//初始化Mario死亡的图片，子弹图片
		try{
			marioDeadImage=ImageIO.read(new File(imagePath+"over.gif"));
			bulleticon=ImageIO.read(new File(imagePath+"bullet.png"));
			}
		catch(IOException e)
			{
				e.printStackTrace();
			}
		
	}

}
