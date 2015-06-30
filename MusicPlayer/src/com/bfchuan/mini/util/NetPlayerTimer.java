package com.bfchuan.mini.util;
import java.util.Timer;
import java.util.TimerTask;



public class NetPlayerTimer {

	private static NetPlayerTimer npt;
	private CountTask countTask;
	private Timer timer;
	private long time;
	
	private NetPlayerTimer() {
	}
	
	public static NetPlayerTimer getInstance() {
		if (npt == null) {
			npt = new NetPlayerTimer();
		}
		return npt;
	}
	
	public void common() {
		if (timer == null) {
			timer = new Timer();
		}
		if (countTask == null) {
			countTask = new CountTask();
		}	
	}
	
	public void start() {
		common();
		timer.scheduleAtFixedRate(countTask, 0, 100);
	}
	
	public void resume() {
		common();
		countTask.setCount(time);
		timer.scheduleAtFixedRate(countTask, 0, 100);
	}

	public void stop() {
		if (timer != null) {
			time = countTask.getCount();
			timer.cancel();
			timer = null;
			countTask = null;
		}
	}
	
	public int getTime() {
		int t = (int)(time / 1000);
		try {
			t = (int)(countTask.getCount() / 1000);
		} catch (Exception e) {	
		}
		return t;
	}
	
	private class CountTask extends TimerTask  {
		
		private long count;
		
		public void setCount(long count) {
			this.count = count;
		}
		
		public long getCount() {
			return count;
		}
		
		@Override
		public void run() {
			count += 100;
		}
		
	}
	
}
