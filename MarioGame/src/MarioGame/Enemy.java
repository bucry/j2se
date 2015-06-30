package MarioGame;

import java.awt.image.BufferedImage;



/**
 * 
 * 敌人类   bfc
 * 
 * */
public class Enemy implements Runnable {

	private int x;
	private int y;
	private int yv = 0;
	private int startX;// 启示的坐标
	private int startY;
	private int type;// 类型
	public BufferedImage showImage;// 图片跟着类型走
	private boolean isLeftOrUP = true;
	private MyFrame my;
	// 移动的范围
	private int upMax = 0;
	private int downMax = 0;

	private Thread t = new Thread(this);
	// 定义场景对象来取得所有的障碍物
	public BackGround bg;

	// 定义图片状态
	public int imageType = 0;

	// public void setBg(BackGround bg) {//将当前场景加到敌人类属性中的方法
	// this.bg = bg;
	// }

	// 构造方法
	// 不同的敌人有不同的构造方法
	public Enemy(int x, int y, boolean isLeft, int type, BackGround bg) {// 三角蘑菇(普通敌人)
		this.x = x;
		this.y = y;
		this.startX = x;
		this.startY = y;
		this.isLeftOrUP = isLeft;
		this.type = type;
		this.bg = bg;
		if (type == 1) {
			this.showImage = StaticValue.allTriangleImage.get(0);// 取蘑菇的第一张图片
			if (x == 0) {

			}
		}
		t.start();
	}

	public Enemy(int x, int y, boolean isUp, int type, int upMax, int downMax,
			BackGround bg) {// 食人花
		this.x = x;
		this.y = y;
		this.startX = x;
		this.startY = y;
		this.isLeftOrUP = isUp;
		this.type = type;
		this.upMax = upMax;
		this.downMax = downMax;
		this.bg = bg;
		if (type == 2) {
			this.showImage = StaticValue.allFlowerImage.get(0);// 取食人花的第一张图片
		}
		t.start();
	}

	// 在run方法中去处理对象的移动
	public void run() {

		while (true) {
			
			boolean canLeft = true;
			boolean canRight = true;
			boolean onLand = false;
			
			
			
			
			if (type == 1) {// 定义蘑菇的移动方法
				if (this.isLeftOrUP) {                  //向左
					this.x -= 2;// 定义速度，比mario稍慢
				} else {                                //向右
					this.x += 2;
				}
				if (imageType == 0) {//行进中换图
//					System.out.println(imageType);
					imageType = 1;
				} else if(imageType == 1){
					imageType = 0;
				}


				for(int i=0;i<this.bg.getAllObstruction().size();i++){
					Obstruction ob =this.bg.getAllObstruction().get(i);
					if(ob.getX()==this.x+60 && (ob.getY()+50>this.y && ob.getY()-50<this.y)){
						canRight=false;
					}
					if(ob.getX()==this.x-60 && (ob.getY()+50>this.y && ob.getY()-50<this.y)){
						canLeft=false;
					}
					if (ob.getInAir() == false) {// 取地板
						

							// 如果敌人没有站在障碍物上就掉落
							if (ob.getY() == this.y + 60
									&& ((ob.getX() + 60 > this.x && ob.getX() - 60 < this.x))) {
								onLand = true;
//								break;
							}
						}
				}
				if (onLand) {// 如果不在地面,特别注意此处不能将判断onLand的方法和改变Y坐标的句子写在一起，因为要将障碍物队列遍历完找到一个使OnLand为false的才执行改变Y坐标的句子
					yv = 0;
				} else if(!onLand){
					yv = 10;
					y += yv;
				}
				
//				System.out.println("当前行进状态"+this.isLeftOrUP);
				if (this.isLeftOrUP && !canLeft || this.x == 0) {// 左行碰到障碍物向右走，右行碰到障碍物向左走，碰边反向
					
					this.isLeftOrUP = false;
//					System.out.println("碰到障碍物了"+this.isLeftOrUP);
				} else if (!this.isLeftOrUP && !canRight || this.x == 840) {
					
					this.isLeftOrUP = true;
				}
				this.showImage = StaticValue.allTriangleImage.get(imageType);
			}
			if (type == 2) {// 定义食人花的移动方法
				if (this.isLeftOrUP) {
					this.y -= 2;
				} else {

					this.y += 4;
				}
				if (imageType == 0) {
					imageType = 1;
				} else {
					imageType = 0;
				}
				if (this.isLeftOrUP && this.y == this.upMax) {
					this.isLeftOrUP = false;
				}
				if (!this.isLeftOrUP && this.y == this.downMax) {
					this.isLeftOrUP = true;
				}
				this.showImage = StaticValue.allFlowerImage.get(imageType);
			}

			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {

				e.printStackTrace();
			}
		}

	}

	public void reset() {

	}

	public void dead() {// 敌人死亡的方法
		// 将死亡时的图片改成被压扁的图片
		this.showImage = StaticValue.allTriangleImage.get(2);
		imageType = 2;
		this.bg.getAllEnemy().remove(this);//将队列中移除
		this.bg.getRemovedEnemy().add(this);
	}

	// public void setImage(){
	// showImage=StaticValue.allTriangleImage.get(type);
	// }
	public int getType() {
		return type;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public BufferedImage getShowImage() {
		return showImage;
	}
}
