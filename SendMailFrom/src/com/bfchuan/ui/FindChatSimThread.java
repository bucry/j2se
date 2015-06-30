package com.bfchuan.ui;

/**
 * 线程�?
 * 作�?：Leonidas
 * piaobomengxiang@163.com
 * 时间�?013-9-13
 * 版本�?.0
 * 描述：该类用于查�?���?
 */
public class FindChatSimThread extends Thread{
		private int current;//进度栏的当前�?
		private int target;//进度栏的�?���?

		public FindChatSimThread(int t){
		current=0;
		target=t;
		}

		public int getTarget(){
		return target;
		}

		public int getCurrent(){
		return current;
		}

		public void run(){//线程�?
		try{
		while (current<target && !interrupted()){//如果进度栏的当前值小于目标�?并且线程没有被中�?
		sleep(10);
		current++;
		if(current == 700)
		{
		sleep(3000);
		}
		else if(current == 730)
		{
		sleep(1000);
		}
		}
		}catch (InterruptedException e){}
		}
}
