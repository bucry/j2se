package MarioGame;

import java.util.ArrayList;

public class Bullet implements Runnable{
	//子弹的属性
	//位置
	public int x,y;
	//速度
	public int xv=50;
	public int yv=10;
	public int size =20;
//	private ArrayList<Bullet> bullet;
	private BackGround bg;//保存子弹所在的场景，用来去除所有的障碍物和敌人
private Mario mario ;
	
	public void setBg(BackGround bg) {
		this.bg = bg;
	
	}
	

	
	//构造方法
	public Bullet(int x,int y,Mario mario){//传进mario是为了，让子弹碰到边的时候，将子弹从队列中移除
		this.x=x;
		this.y=y;
		this.mario =mario ;
	
//		t.start();
	}
	public   void setXv(int xv){
		this.xv=xv;
	}
	public void setYv(int yv){
		this.yv=yv;
	}
	public void removed(){
		
	}
	public void run(){
		while (true) {
			x+=xv;
			y+=yv;
			
			if(y>=540){
//				System.out.println("反弹了");    
//				yv=-yv;
				mario.getBullet().remove(this);
			}if(y<=0){
//				yv=-yv;
				mario.getBullet().remove(this);
			}
			if(x>=900){//碰边将其移除

				mario.getBullet().remove(this);

			}if(x<=0){//碰边将其移除
				
				mario.getBullet().remove(this);
			}

			for (int i = 0; i < this.bg.getAllEnemy().size(); i++) {// 取出所有的敌人
				Enemy en = this.bg.getAllEnemy().get(i);
				if (en.getX() + 50 > this.x && en.getX() - 50 < this.x
						&& (en.getY() + 50 > this.y && en.getY() - 50 < this.y)) {// 碰到敌人的判断,左右
					 en.dead();
					 mario.getBullet().remove(this);

				}
				if (en.getY() == this.y + 60
						&& (en.getX() + 60 > this.x && en.getX() - 60 < this.x)) {
					if (en.getType() == 1) {// 踩到蘑菇
						
					

						en.dead();
						mario.getBullet().remove(this);
					} else if (en.getType() == 2) {// 踩到食人花
					
						
						 en.dead();
						 mario.getBullet().remove(this);
						 
					}
				}
			}
			
			for (int i = 0; i < this.bg.getAllObstruction().size(); i++) {
				Obstruction ob = this.bg.getAllObstruction().get(i);
//				System.out.println(ob.getX()+"障碍物位置");
//				System.out.println(this.x+"子弹位置");
				// 不许向左向右的判断，
				if (ob.getX() == this.x + 60//不能向右
						&& (ob.getY() + 45 > this.y && ob.getY() - 45 < this.y)) {// 判断在空中碰到东西不能向左向右的情况
					System.out.println("碰撞了障碍物");
				
					if (ob.getType() == 0) {// 处理砖块
						// 将砖块从场景中移除
						mario.getBullet().remove(this);
						this.bg.getAllObstruction().remove(ob);
						// 将被移除的砖块保存到一个集合之中
						this.bg.getRemovedObstruction().add(ob);
						
					}
					if (ob.getType() == 4) {// 处理问号
						mario.getBullet().remove(this);
						ob.setType(2); // 将问号变为铁块
						ob.setImage();
						
					}if (ob.getType() == 6) {
						mario.getBullet().remove(this);
											
					}
					if (ob.getType() == 7) {
						mario.getBullet().remove(this);
											
					}
					if (ob.getType() == 8) {
						mario.getBullet().remove(this);
											
					}
					if (ob.getType() == 9) {
						mario.getBullet().remove(this);
											
					}
					else if(ob.getType() == 3){
						mario.getBullet().remove(this);
						ob.setType(2); 
						ob.setImage();
					}
					
				}
				if (ob.getX() == this.x - 60//不能向左
						&& (ob.getY() + 45 > this.y && ob.getY() - 45 < this.y)) {
					System.out.println("碰撞了障碍物");

					if (ob.getType() == 0) {// 处理砖块
						// 将砖块从场景中移除
						System.out.println("移除砖块");
						mario.getBullet().remove(this);
						this.bg.getAllObstruction().remove(ob);
						// 将被移除的砖块保存到一个集合之中
						this.bg.getRemovedObstruction().add(ob);
						
					}
					if (ob.getType() == 4) {// 处理问号
						mario.getBullet().remove(this);
						System.out.println("改变问号");
						ob.setType(2); // 将问号变为铁块
						ob.setImage();
						
					}
					if (ob.getType() == 6) {
						mario.getBullet().remove(this);
											
					}
					if (ob.getType() == 7) {
						mario.getBullet().remove(this);
											
					}
					if (ob.getType() == 8) {
						mario.getBullet().remove(this);
											
					}
					if (ob.getType() == 9) {
						mario.getBullet().remove(this);
											
					}
					else if(ob.getType() == 3){
						mario.getBullet().remove(this);
						ob.setType(2); 
						ob.setImage();
					}
				}
				
				// 判断子弹是否在跳跃中顶到了一个障碍物，是否在一个障碍物的下方
			
//				if (ob.getY() == this.y - 60
//						&& (ob.getX() + 60 > this.x && ob.getX() - 60 < this.x)) {
//					mario.getBullet().remove(this);
//					
//					if (ob.getType() == 0) {// 处理砖块
//						// 将砖块从场景中移除
//						this.bg.getAllObstruction().remove(ob);
//						// 将被移除的砖块保存到一个集合之中
//						this.bg.getRemovedObstruction().add(ob);
//						
//					}
//					if (ob.getType() == 4) {// 处理问号
//						ob.setType(2); // 将问号变为铁块
//						ob.setImage();
//						
//					}else if(ob.getType() == 3){
//						ob.setType(2); 
//						ob.setImage();
//					}
//						
//				
//				}

			}
			
			
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {

				e.printStackTrace();
			}
		}
	}

}
