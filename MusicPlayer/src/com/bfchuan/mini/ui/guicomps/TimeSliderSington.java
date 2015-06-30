package com.bfchuan.mini.ui.guicomps;

/**
 * 时间滑动条单例
 * 作者:Loenidas
 * 时间:2012-5-10
 * piaobomengxiang@163.com
 * 版本:v1.0
 *
 */
public class TimeSliderSington {

	private static TimeSliderSington tss;
	private TimeSliderUpdate tsu;// 进度条更新线程
	
	private TimeSliderSington() {
	}
	
	public static TimeSliderSington getInstance() {
		if (tss == null) {
			tss = new TimeSliderSington();
		}
		return tss;
	}
	
	public void start() {
		tsu = new TimeSliderUpdate();
		tsu.start();
	}
	
	public void pause() {
		tsu.pauseUpdate();
	}
	
	public void resume() {
		tsu.resumeUpdate();
	}
	
	public void stop() {
		if (tsu != null && tsu.isAlive()) {
			tsu.finish();
		}
	}
	
}
