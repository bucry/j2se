package com.bfchuan.mini.ui.guicomps;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.bfchuan.mini.bo.MusicBo;

/**
 * 时间进度条的监听器
 * 作者:Loenidas
 * 时间:2012-5-10
 * piaobomengxiang@163.com
 * 版本:v1.0
 *
 */
public class TimeSliderListener extends MouseAdapter implements ChangeListener {

	private JSlider slider;
	
	public TimeSliderListener(BottomPanel bmpl) {
		slider = bmpl.getTimeSlider();
		slider.addChangeListener(this);
		slider.addMouseListener(this);
	}
	
	@Override
	public void stateChanged(ChangeEvent e) {
		int time = MusicBo.getInstance().getPlayerCurTime();
		LrcLabel.getInstance().updateLRC(time);
	}
	
    @Override
    public void mousePressed(MouseEvent e) {
    }

	@Override
	public void mouseReleased(MouseEvent e) {
	}
	
}
