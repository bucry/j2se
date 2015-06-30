package com.bfchuan.mini.ui.guicomps;

import javax.swing.JLabel;
import javax.swing.JSlider;


import com.bfchuan.mini.bo.MusicBo;
import com.bfchuan.mini.util.FormatUtils;

/**
 * 时间进度条更新线程
 * 作者:Loenidas
 * 时间:2012-5-10
 * piaobomengxiang@163.com
 * 版本:v1.0
 *
 */
public class TimeSliderUpdate extends Thread {

	private JSlider slider;// 滑动条
	private JLabel timeLabel;// 时间标签
	private boolean run = true;
	private boolean pause;// 是否暂停
	private String totalTimeStr;// 时间字符串
		
	public TimeSliderUpdate() {
		slider = BottomPanel.getInstance().getTimeSlider();
		timeLabel = BottomPanel.getInstance().getTimeLabel();
		this.setPriority(Thread.MAX_PRIORITY);
	}
	
	public void finish() {
		this.run = false;
	}
	
	public void pauseUpdate() {
		this.pause = true;
	}
	
	public void resumeUpdate() {
		this.pause = false;
	}
	
	@Override
	public void run() {
		MusicBo musicBo = MusicBo.getInstance();
		double pre;
		totalTimeStr = FormatUtils.formatTime(musicBo.getPlayerTotalTime());
		while (run) {
			try {
				Thread.sleep(100);
			} catch (Exception e) {
			}
			if (!pause) {
				// 更新时间进度条
				//System.out.println(musicBo.getCurrentSong().getSongName());
				pre = (musicBo.getPlayerCurTime() * 1.0) / musicBo.getPlayerTotalTime();
				if (pre >= 1) {// 表明已经结束
					slider.setValue(0);
					//break;
				}
				slider.setValue((int)(slider.getMaximum() * pre));
				// 更新显示的时间
				timeLabel.setText(FormatUtils.formatTime(musicBo.getPlayerCurTime()) + 
						"|" + totalTimeStr);
			}
		}
	}

}
