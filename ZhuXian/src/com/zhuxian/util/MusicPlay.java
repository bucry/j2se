package com.zhuxian.util;

import mp3x.ctl.PlayerControl;

public class MusicPlay {
	
	public  PlayerControl  control =new PlayerControl();
	
	public  void playerCardMusic(String path){
		control.openSong(path);
		System.out.println("time:" + control.getTotalTimeSecond());
		System.out.println("size:" + control.getTotalBytes());
		control.play();
	}
	
	public static void main(String args[]){
		new MusicPlay().playerCardMusic("music/Theme A.mp3");
	}

	
	
}
