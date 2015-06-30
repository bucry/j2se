package MarioGame;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class BackGround {
	private BufferedImage bgImage = null;// 当前的背景
	private int sort;// 当前的场景，定义有三幅场景，场景顺序
	private boolean flag;// 如果flag为true，则为最后一个场景

	// 定义构造方法，传入参数，初始化背景图片
	private List <Enemy> allEnemy = new ArrayList<Enemy>();// 全部的敌人队列
	private List<Obstruction> allObstruction = new ArrayList<Obstruction>();// 全部的障碍物队列
	private List <Enemy>removedEnemy = new ArrayList<Enemy>();// 被消灭的敌人队列
	private List<Obstruction> removedObstruction = new ArrayList<Obstruction>();// 被消灭的障碍物队列
	private List <Bullet> allBullet = new ArrayList<Bullet>();// 全部的子弹队列
	public BackGround(int sort, boolean flag) {

		this.sort = sort;
		this.flag = flag;
		if (flag) {// 如果是
			bgImage = StaticValue.endImage;
		} else {
			bgImage = StaticValue.bgImage;// 游戏场景
		}
		
		if(sort==1){
			for(int i=0;i<15;i++){//将障碍类中的地板对象加到本类中的障碍物队列中来
//				if(i!=4&i!=5){
					Obstruction ob =new Obstruction(i*60,540,9);
					ob.setInAir(false);
				this.allObstruction.add(ob);
				
//				}
			}
//			this.allObstruction.add(new Obstruction(420,480,4));
			this.allObstruction.add(new Obstruction(120,360,4));
			this.allObstruction.add(new Obstruction(300,360,0));
			this.allObstruction.add(new Obstruction(360,360,4));
			this.allObstruction.add(new Obstruction(420,360,0));
			this.allObstruction.add(new Obstruction(480,360,4));
			this.allObstruction.add(new Obstruction(540,360,0)); 
			this.allObstruction.add(new Obstruction(420,180,4));
			this.allObstruction.add(new Obstruction(660,540,6));
			this.allObstruction.add(new Obstruction(720,540,5));
			this.allObstruction.add(new Obstruction(660,480,8));
			this.allObstruction.add(new Obstruction(720,480,7));
			
			this.allObstruction.add(new Obstruction(660,120,4));
			
			this.allObstruction.add(new Obstruction(660,300,3));//看不见的色块
			
			//加入敌人
			
			this.allEnemy.add(new Enemy(600,480,true,1,this));//同时将当前场景传经了敌人的属性
		
			this.allEnemy.add(new Enemy(690,540,true,2,420,540,this));
		
//			this.allEnemy.add(new Enemy(60,540,true,2,420,540,this));
//			this.allEnemy.add(new Enemy(120,540,true,2,420,540,this));
//			this.allEnemy.add(new Enemy(180,540,true,2,240,540,this));
//			this.allEnemy.add(new Enemy(240,540,true,2,420,540,this));
//			this.allEnemy.add(new Enemy(300,540,true,2,240,540,this));
//			this.allEnemy.add(new Enemy(360,540,true,2,420,540,this));
			
		}
		if(sort==2){//第二个场景
			for(int i=0;i<15;i++){//将障碍类中的地板对象加到本类中的障碍物队列中来
//				if(i!=10&i!=11){
					
					Obstruction ob =new Obstruction(i*60,540,9);
					ob.setInAir(false);
					this.allObstruction.add(ob);
//					}
			}
			this.allObstruction.add(new Obstruction(60,540,6));//第一根水管
			this.allObstruction.add(new Obstruction(120,540,5));
			this.allObstruction.add(new Obstruction(60,480,6));
			this.allObstruction.add(new Obstruction(120,480,5));
			this.allObstruction.add(new Obstruction(60,420,8));
			this.allObstruction.add(new Obstruction(120,420,7));
			
			this.allObstruction.add(new Obstruction(240,540,6));//第二根水管
			this.allObstruction.add(new Obstruction(300,540,5));
			this.allObstruction.add(new Obstruction(240,480,6));
			this.allObstruction.add(new Obstruction(300,480,5));
			this.allObstruction.add(new Obstruction(240,420,6));
			this.allObstruction.add(new Obstruction(300,420,5));
			this.allObstruction.add(new Obstruction(240,360,8));
			this.allObstruction.add(new Obstruction(300,360,7));
			this.allEnemy.add(new Enemy(600,480,true,1,this));
		}if(sort==3){
			for(int i=0;i<15;i++){//将障碍类中的地板对象加到本类中的障碍物队列中来
				Obstruction ob =new Obstruction(i*60,540,9);
				ob.setInAir(false);
				this.allObstruction.add(ob);
			}
			this.allObstruction.add(new Obstruction(240,540,6));//第二根水管
			this.allObstruction.add(new Obstruction(300,540,5));
			this.allObstruction.add(new Obstruction(240,480,6));
			this.allObstruction.add(new Obstruction(300,480,5));
			this.allObstruction.add(new Obstruction(240,420,6));
			this.allObstruction.add(new Obstruction(300,420,5));
			this.allObstruction.add(new Obstruction(240,360,8));
			this.allObstruction.add(new Obstruction(300,360,7));
			
			this.allEnemy.add(new Enemy(240,480,true,1,this));
			this.allEnemy.add(new Enemy(240,480,true,1,this));
			this.allEnemy.add(new Enemy(300,480,true,1,this));
			this.allEnemy.add(new Enemy(540,480,true,1,this));
			this.allEnemy.add(new Enemy(540,480,true,1,this));
			this.allEnemy.add(new Enemy(600,480,true,1,this));
			this.allEnemy.add(new Enemy(600,480,true,1,this));
			this.allEnemy.add(new Enemy(120,480,true,1,this));
		}if(sort==4){
			for(int i=0;i<15;i++){//将障碍类中的地板对象加到本类中的障碍物队列中来
				Obstruction ob =new Obstruction(i*60,540,9);
				ob.setInAir(false);
				this.allObstruction.add(ob);
			}
			this.allEnemy.add(new Enemy(240,480,true,1,this));
			this.allEnemy.add(new Enemy(540,480,true,1,this));
			this.allEnemy.add(new Enemy(120,480,true,1,this));
		}
	}
//一些 get方法*****************************************************************
	// 获取当前背景图片的方法，
	public BufferedImage getBgImage() {
		return bgImage;
	}
	//取得所有障碍物的方法
	public List<Obstruction> getAllObstruction() {
		return allObstruction;
	}
	public List<Enemy> getAllEnemy() {
		return allEnemy;
	}

	public List<Enemy> getRemovedEnemy() {
		return removedEnemy;
	}
	//获得被移除的障碍物的方法
	public List<Obstruction> getRemovedObstruction() {
		return removedObstruction;
	}
	
	public int getSort() {
		return sort;
	}
	
	//一些 get方法*****************************************************************
	
	
	// 重置方法，将所有的障碍物和敌人返回到原有坐标，并将其状态也修改
	public void reset() {

	}
}
