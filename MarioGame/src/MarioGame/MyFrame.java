package MarioGame;

import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;


/**
 * 主界面
 * @author bfc
 *
 */
public class MyFrame extends JFrame implements KeyListener, Runnable {
	
	private Thread t = new Thread(this);
	private boolean isStart = false;// 是否已经开始，一开始游戏是不会开始的

	private List<BackGround> allBG = new ArrayList<BackGround>();// 存放背景图片的队列
	public  static BackGround nowBG = null;// 当前背景图片，方法中再进行初始化

	
	public  BackGround getNowBg(){
		return nowBG;
		
	}
	private Mario mario = null;
	private Bullet bullet1 =null ;
	
//	ArrayList<Bullet> bullet=null;   //存放子弹的队列

	public static void main(String args[]) {
		MyFrame ui = new MyFrame();
		ui.initUI();

	}

	
	
	public void initUI() {
		this.setTitle("java3D:白发川");
		this.setSize(900, 600);
		this.addKeyListener(this);
		this.setDefaultCloseOperation(3);
		t.start();
		int width = Toolkit.getDefaultToolkit().getScreenSize().width;
		int height = Toolkit.getDefaultToolkit().getScreenSize().height;
		this.setLocation((width - 900) / 2, (height - 600) / 2);
		StaticValue.init();// 在创建场景之前就初始化全部的图片
		this.setVisible(true);
		// 创建全部的场景
		for (int i = 1; i <= 4; i++) {
			this.allBG.add(new BackGround(i, i == 4 ? true : false));
			// 将BackGround中的图片下标遍历，如果i为第三个，则flag为true，其他情况为false,然后加到当前的allBG队列中来
		}
		// 将第一个场景设置为当前场景
		this.nowBG = this.allBG.get(0);
		this.mario = new Mario(0, 480);//马里奥初始化
//		this.bullet1 =new Bullet(mario.getX(),mario.getY());

		// 将场景放入mario对象的属性中去
		this.mario.setBg(nowBG);// 现在场景中全部的障碍物就可以取得了
//		this.bullet1.setBg(nowBG);//取得全部的敌人
		this.setResizable(false);
		
	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == 39) {// 马里奥向右
			System.out.println("执行了");
			mario.rightMove();
		}
		if (e.getKeyCode() == 37) {// 马里奥向左
			mario.leftMove();
		}
		if (e.getKeyCode() == 38) {// 马里奥跳跃
			mario.jump();
		}
		if (e.getKeyCode() == 32) {// 马里奥发射子弹
			mario.shoot();
		}

	}

	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == 39) {// 马里奥向右
			mario.rightStop();
		}
		if (e.getKeyCode() == 37) {// 马里奥向左
			mario.leftStop();
		}
	}

	public void keyTyped(KeyEvent e) {
		// 不用//

	}

	public void paint(Graphics g) {
//如果走出场景范围时，切换场景
		if(this.mario!=null){
			List<Obstruction> obt =this.nowBG.getAllObstruction();
			//取出所有的障碍物
			for(int i= 0;i<obt.size();i++){
				Obstruction obs =obt.get(i);
//				if(obs.getType()==9){
					if(this.nowBG.getSort()==1){
//						System.out.println(this.nowBG.getSort());
						if(this.mario.getX()>=0){
							System.out.println(this.mario.getX());
							obs.setYv(10);
							System.out.println("");
//							System.out.println(obs.yv);
						}
					}
//				}
			}
//			
			// 使用双缓冲技术，防止g的反复调用
			BufferedImage image = new BufferedImage(900, 600,
					BufferedImage.TYPE_3BYTE_BGR);// 创建缓冲图片
			Graphics g2 = image.getGraphics();// 从缓冲图片上面获取画布
			g2.drawImage(this.nowBG.getBgImage(), 0, 0, this);// 将图片画在窗体上面
			
			
			
			//绘制敌人
			Iterator<Enemy> iter1 = this.nowBG.getAllEnemy().iterator();// 使用迭代器进行遍历
			while (iter1.hasNext()){
				Enemy en =iter1.next();
				g2.drawImage(en.getShowImage(), en.getX(), en.getY(), this);
			}
			// 绘制障碍物
			Iterator<Obstruction> iter = this.nowBG.getAllObstruction().iterator();// 使用迭代器进行遍历
			while (iter.hasNext()) {
				Obstruction ob = iter.next();
				g2.drawImage(ob.getShowImage(), ob.getX(), ob.getY(), this);
			}
			// 将Mario画上去
			g2.drawImage(this.mario.getShowImage(), this.mario.getX(), this.mario
					.getY(), this);

			//画子弹
//			this.bullet=mario.getBullet();
			for(int i=0;i<mario.bullet.size();i++){
				bullet1=mario.bullet.get(i);
//				bullet1.setBg(nowBG);//取得全部的敌人
				if(mario.getStatus().indexOf("left")!=-1){
					bullet1.setXv(-50);
				}if(mario.getStatus().indexOf("right")!=-1){
					bullet1.setXv(50);
				}if(mario.getStatus().indexOf("jump")!=-1){
//					bullet1.setYv(-10);
				}
//				Bullet bullets = bullet.get(i);
//				g2.setColor(Color.red);
//				g2.fillOval( bullets.x-20,  bullets.y-30,  bullets.size, bullets.size);
				g2.drawImage(StaticValue.bulleticon,  bullet1.x, bullet1.y, this);
			}
			
//			//画网格线
//			for(int i=0;i<10;i++){
//				g2.drawLine(0, 60*i, 900, 60*i);
//			}
//			for(int i=0;i<15;i++){
//				g2.drawLine(60*i, 0, 60*i, 600);
//			}
			
			
			// 把缓冲图片绘制到真正的窗体中
			g.drawImage(image, 0, 0, this);
		}
		
		
		
	}

	public void run() {
		while (true) {
			this.repaint();

			
			try {
				if(this.mario!=null){
					if (this.mario.getX() >= 840) {
						this.nowBG = this.allBG.get(this.nowBG.getSort());
						this.mario.setBg(this.nowBG);
						if(this.nowBG.getSort()==3){
							this.mario.setX(75);
							this.mario.setY(0);
						}else{
						this.mario.setX(0);
						}

					}
					if(mario.getY()==600){
//						mario.setIsDead(true);
//						this.mario.setX(0);
//						this.mario.setY(480);
//						this.mario.xv=0;
//						this.mario.yv=0;
						this.mario.Fall();
					}
					if(this.nowBG.getSort()==4){
						if(this.mario.getX()==700){
							this.mario.Win();
							if(this.mario.getIsWin()==true){
							JOptionPane.showMessageDialog(this, "过关了，你真棒！");
							}
						}
					}
					
					if(mario.getIsDead()==true){
						JOptionPane.showMessageDialog(this, "死亡了");
						mario.setIsDead(false);
						
					}
					
				}
				Thread.sleep(25);

				
			
			} catch (InterruptedException e) {

				e.printStackTrace();
			}
		}

	}

}
