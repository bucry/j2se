package com.bfchuan.mini.ui.guicomps;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JSlider;

import com.bfchuan.mini.bo.ConfigBo;
import com.bfchuan.mini.bo.MyPlayerControl;

/**
 * 音量滑动条拖动的监听，用来调整音量
 * 作者:Loenidas
 * 时间:2012-5-10
 * piaobomengxiang@163.com
 * 版本:v1.0
 *
 */
public class SoundSliderListener extends MouseAdapter {

	private JSlider slider;
	
	public SoundSliderListener(BottomPanel bmpl) {
		slider = bmpl.getSoundSlider();
		slider.addMouseMotionListener(this);
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		double pre = slider.getValue() * 1.0 / slider.getMaximum();
		MyPlayerControl.getInstance().setVolumnGain(pre);
		ConfigBo.getInstance().setVoiceValue(slider.getValue());
		slider.setToolTipText("音量: " + slider.getValue() + "%");
	}
	
}
