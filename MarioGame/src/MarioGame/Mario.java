package MarioGame;

import java.awt.image.BufferedImage;
import java.util.ArrayList;



public class Mario implements Runnable {
	// 坐标
	private int x, y;
	// 状态
	private String status;
	// 当前显示的人物图片
	private BufferedImage showImage;
	// 生命数和分数
	private int life;
	private int score;
	// 速度
	public int xv = 0;
	// 定义一个垂直方向的速度属性
	public int yv = 0;
	// 线程用来包装Runnable
	private Thread t = null;

	// 当前移动中的图片索引
	private int moving = 0;
	// 定义一个场景对象，保存Mario所在的场景
	private BackGround bg;
	
	private MyFrame my;
	// 跳跃的上升时间
	private int uptime = 0;
	// 标记mario是否死亡
	private boolean isDead = false;
	private boolean isWin = false;
	   public ArrayList<Bullet> bullet = new ArrayList<Bullet>(); //创建存放小球线程的队列
	
	
	
	
//	private Bullet bu =new Bullet(this.x,this.y);
	// 构造方法

	public Mario(int x, int y) {
		this.x = x;
		this.y = y;
		// 初始化Mario的图片
		this.showImage = StaticValue.allMarioImage.get(0);
		this.life = 3;
		this.score = 0;
		t = new Thread(this);
		t.start();

		this.status = "right--standing";
	}

	// 移动的方法
	public void leftMove() {
		xv = -5;
		if (this.status.indexOf("jumping") != -1) {
			this.status = "left--jumping";
		} else {
			this.status = "left--moving";
		}
	}

	public void rightMove() {
		xv = 5;
		if (this.status.indexOf("jumping") != -1) {
			this.status = "right--jumping";
		} else {
			this.status = "right--moving";
		}
	}

	public void leftStop() {
		xv = 0;
		// 如果当前已经是跳跃状态了，那么就不能再改变状态
		if (this.status.indexOf("jumping") != -1) {
			this.status = "left--jumping";
		} else {
			this.status = "left--standing";
		}
	}

	public void rightStop() {
		xv = 0;
		if (this.status.indexOf("jumping") != -1) {
			this.status = "right--jumping";
		} else {
			this.status = "right--standing";
		}
	}

	public void jump() {// 跳的方法

		if (this.status.indexOf("jumping") == -1) {// 必须要处于非跳跃的状态才能够跳
			if (this.status.indexOf("left") != -1) {// 如果向左
				this.status = "left--jumping";
			} else {
				this.status = "right--jumping";
			}// 跳跃应该分为两个阶段，上升的时候纵坐标减小，下降的时候纵坐标增加
			yv = -15;
			uptime = 13;
		}
	}

	public void down() {// 下落的方法，1.跳跃的第二阶段应该下落2.走出空中的障碍物的范围应该下落
		if (this.status.indexOf("left") != -1) {// 如果向左
			this.status = "left--jumping";// 同样也是在空中，定义为jumping状态
		} else {
			this.status = "right--jumping";
		}
		yv = 15;
	}

	public void dead() {// mario死亡的方法
//		System.out.println("死亡");
		isDead = true;
		this.xv=0;
		this.yv=0;
		this.setX(0);
		this.setY(480);

	}
	
	public void Win() {// mario胜利的方法

		isWin = true;
		this.setX(800 );
		this.xv=0;
//		this.setY(480);

	}
	public void Fall() {// mario胜利的方法

	this.setIsDead(true);
	System.out.println("掉落");
		this.xv=0;
		this.yv=0;
		this.setX(0);
		this.setY(480);


	}
	
	public void shoot(){
		Bullet bu =	new Bullet(this.x ,this.y,this);
//		System.out.println(bu.x);
		bu.setBg(MyFrame.nowBG);//将场景放到bullet类的属性中
		Thread t =new Thread(bu);
		t.start();
		bullet.add(bu);
	
		
		
	}

	// ************************************************所有get方法*********************************************************************//
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	public String  getStatus() {
		return status;
	}
	public BufferedImage getShowImage() {
		return showImage;
	}

	public int getScore() {
		return score;
	}

	public int getLife() {
		return life;
	}

	public boolean getIsDead() {
		return isDead;
	}
	
	public boolean getIsWin() {
		return isWin;
	}
	
	public ArrayList<Bullet>  getBullet(){
		return bullet;
	}

	// ****************************************************所有set方法*****************************************************************//

	public void setBg(BackGround bg) {
		this.bg = bg;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setIsDead(boolean isDead) {
		this.isDead = isDead;
	}

	// ****************************************************run方法*****************************************************************//
	public void run() {
		while (true) {
			// 判断Mario是否可以与障碍物横向相碰
			// 使用for循环将障碍物遍历出来
			boolean canLeft = true;
			boolean canRight = true;
			// 定义是否处于障碍物之上的标记
			boolean onLand = false;
			
			for (int i = 0; i < this.bg.getAllObstruction().size(); i++) {
				Obstruction ob = this.bg.getAllObstruction().get(i);
				// 不许向左向右的判断，将判断范围改为50，模糊判断，是跳跃不那么困难死板
				if (ob.getX() == this.x + 60
						&& (ob.getY() + 50 > this.y && ob.getY() - 50 < this.y)) {// 判断在空中碰到东西不能向左向右的情况
					// 即障碍物的Y+60大于mario的Y，或障碍物的Y-60小与mario的Y，
					// 此时mario就不能继续向左向右移动了
					canRight = false;
				}
				if (ob.getX() == this.x - 60
						&& (ob.getY() + 50 > this.y && ob.getY() - 50 < this.y)) {
					canLeft = false;
				}
				// 如果是这个状态表示当前处于一个障碍物的上面,包含空中和地面的障碍物判断
				if (ob.getY() == this.y + 60
						&& (ob.getX() + 60 > this.x && ob.getX() - 60 < this.x)) {
					onLand = true;
				}
				// 判断mario是否在跳跃中顶到了一个障碍物，是否在一个障碍物的下方
				// 此处也改为50 防止下落过程中也进行判断在障碍物下方把不该顶的顶掉了
				if (ob.getY() == this.y - 60
						&& (ob.getX() + 50 > this.x && ob.getX() - 50 < this.x)) {
					if (ob.getType() == 0) {// 处理砖块
						// 将砖块从场景中移除
						this.bg.getAllObstruction().remove(ob);
						// 将被移除的砖块保存到一个集合之中
						this.bg.getRemovedObstruction().add(ob);
					}
					if (ob.getType() == 4) {// 处理问号
						ob.setType(2); // 将问号变为铁块
						ob.setImage();
					}else if(ob.getType() == 3){
						ob.setType(2); 
						ob.setImage();
					}
						
					uptime = 0;
				}

			}
			// ******************************************是否与敌人碰撞的判断***************************************************
			for (int i = 0; i < this.bg.getAllEnemy().size(); i++) {// 取出所有的敌人
				Enemy en = this.bg.getAllEnemy().get(i);
				if (en.getX() + 50 > this.x && en.getX() - 50 < this.x
						&& (en.getY() + 50 > this.y && en.getY() - 50 < this.y)) {// 碰到敌人的判断,左右
					// en.dead();
					System.out.println("死了");
					this.uptime = 15;
					yv = -15;
					try {
						Thread.sleep(100);
						this.dead();//主人物死亡
					} catch (Exception ef) {
						ef.printStackTrace();
					}
					

				}
				if (en.getY() == this.y + 60
						&& (en.getX() + 60 > this.x && en.getX() - 60 < this.x)) {
					if (en.getType() == 1) {// 踩到蘑菇
						if(en.imageType!=2){
						yv = -15;
						this.uptime = 7;//人物上升一段距离
						}
						EnemyDead dead = new EnemyDead(en);
						dead.start();
//						en.dead();   //敌人死亡
					} else if (en.getType() == 2) {// 踩到食人花
						yv = -10;
						this.uptime = 15;
						// en.dead();
						 this.dead();
					}
				}
			}

			// *******************************掉到地板以下的判断****************************************************
			// System.out.println("    ==="+y);
			if (this.y > 600) {

				// this.dead();
			}

			// **********************************************跳跃的判断***********************************************************
			if (onLand && uptime == 0) {// 特别注意此处，如果是初始状态，那么就不会进入else，就不会产生y方向的速度
				// 所以应该加上一个判断，按了跳以后uptime就不会为0了，就会跳到else{}的判断中去。
				if (this.status.indexOf("left") != -1) {// 如果向左
					if (xv != 0) {
						this.status = "left--moving";
					} else {
						this.status = "left--standing";
					}
				} else {
					if (xv != 0) {
						this.status = "right--moving";
					} else {
						this.status = "right--standing";
					}
				}

			} else {// 如果不在地面上,才能进入跳跃的状态
				if (uptime != 0) {// 表示当前是上升的状态时
					uptime--;// 如果为0的时候就停止-，只有按jump进入空中才能进行这一步，否则跳入下一步
				} else {// 或者是直接走下来的
					this.down();// 直接调用down 方法
				}

				y += yv;// 不管是上升过程还是下降过程，这一部分不变
			}

			// *********************************************结束跳跃的判断***********************************************************

			// **********************************************运动图片的处理*********************************************************
			// 改变坐标
			if (canLeft && xv < 0 || canRight && xv > 0) {
				x += xv;
				if (x < 0) {// 是人物不能走到前一个场景去
					x = 0;
				}
			}
			int temp = 0;// 定义一个图片取得的初始索引数
			if (this.status.indexOf("left") != -1) {
				temp += 5;// 如果状态中有向左的话，就将初始索引数+5，因为向右的图片下标为1-5，向左的为6-10，此处没必要写
				// else
			}

			// 判断是否当前为移动
			if (this.status.indexOf("moving") != -1) {
				temp += this.moving;
				moving++;// 注意，此处如果不将moving归零，将会无限制的+下去，就会产生越界的异常
				if (moving == 4) {
					moving = 0;
				}

			}
			if (this.status.indexOf("jumping") != -1) {
				temp += 4;
			}
			this.showImage = StaticValue.allMarioImage.get(temp);// 改变显示图片
			// 要求：迈步的时候需要将这四张图片一个一个的循环，如果状态为moving,人物每运动一下图片就要改变一下

			// *********************************************结束***********************************************************

			//子弹方向***************************************************************
//			if(this.status.indexOf("left")!=-1){
//				bullet1.setXv(-50);
//			}if(this.status.indexOf("right")!=-1){
//				bullet1.setXv(50);
//			}if(this.status.indexOf("jump")!=-1){
//				bullet1.setYv(-10);
//			}
			
			
			
			try {// 休眠时间
				Thread.sleep(25);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}
}
