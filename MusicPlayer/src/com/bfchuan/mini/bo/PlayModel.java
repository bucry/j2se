package com.bfchuan.mini.bo;

import com.bfchuan.mini.enums.CircleModel;

/**
 * 播放模式控制类：全部循环、单曲循环等
 * 作者:Loenidas
 * 时间:2012-5-10
 * piaobomengxiang@163.com
 * 版本:v1.0
 *
 */
public class PlayModel {

	private static PlayModel playModel;
	private CircleModel cmodel = CircleModel.全部循环;
	
	private PlayModel() {
	}
	
	public static PlayModel getInstance() {
		if (playModel == null) {
			playModel = new PlayModel();
		}
		return playModel; 
	}
	
	public CircleModel getCircleModel() {
		return cmodel;
	}
	
	public void setCircleModel(CircleModel cmodel) {
		this.cmodel = cmodel;
	}
	
}
