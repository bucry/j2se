package MarioGame;

import java.awt.image.BufferedImage;
 /*
  *障碍物类
  * 
  * /
  */




 public class Obstruction implements Runnable {

	private int x, y;    //障碍物的位置
	public    int yv =0;  //y方向的速度
    private Thread t = new Thread(this);   //包装成线程对象
	private int type;           //障碍物的种类，共12种 
	private int startType;           //某一位置初始状态的种类
	private BufferedImage showImage = null;     //显示的图片，方法中再进行初始化
    private boolean inAir =true;
	private BackGround bg;
	// 线程用来包装Runnable
	
	//构造方法用于传入坐标，类型
	
	public  Obstruction(int x,int y,int type){
		this.x=x;
		this.y=y;
		this.type=type;
		setImage();
		t.start();//在创建时启动线程
	}
	
	
	
	
	//重置方法
	public void reset(){
		
		this.type=startType;
		this.setImage();
	}
	//根据图片类型改变显示图片,依据(type)
	public void setImage(){
		showImage=StaticValue.allObstructionImage.get(type);
	}
	
	public BufferedImage getShowImage() {
		return showImage;
	}
	//所有的 get方法*************************************************************************************
	public int getType() {
		return type;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public boolean getInAir() {
		return inAir;
	}
	//结束*************************************************************************************
	
	//所有的 set方法*************************************************************************************
	public void setType( int type) {
		this.type =type ;
	}
	
	public void setYv( int yv) {
		this.yv =yv ;
	}
	public void setInAir(  boolean inAir) {
		this.inAir =inAir ;
	}
	
	//结束*************************************************************************************
	public void run() {
		//写成线程方便操作
		//设置陷阱时需要用到run方法
		System.out.println("陷阱运动了");
		y+=yv;
	}

}
