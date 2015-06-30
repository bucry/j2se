package MarioGame;

public class EnemyDead extends Thread{
	Enemy en ;
	public EnemyDead(Enemy en){
		this.en = en;
	}
	public void run(){
		// 将死亡时的图片改成被压扁的图片
		en.showImage = StaticValue.allTriangleImage.get(2);
		en.imageType = 2;

		try {//休眠一段时间再移除
			Thread.sleep(300);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
		en.bg.getAllEnemy().remove(en);//将队列中移除
//		en.bg.getRemovedEnemy().add(this);
	}

}
