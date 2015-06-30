package com.bfchuan.music;

import mp3x.ctl.PlayerControl;

public class TTMusic {
	
	public  PlayerControl  control =new PlayerControl();// ≤•∑≈øÿ÷∆¿‡
	private long songNumber = 0;
	private long songNumberNow = 0;
	
	public  void playerCardMusic(String path){
		
		control.openSong(path);
		songNumber = control.getTotalTimeSecond();
		//System.out.println("time:" + control.getTotalTimeSecond());
		//System.out.println("size:" + control.getTotalBytes());
		control.play();
	}
	
	/*public static void main(String args[]){
		new Music().playerCardMusic("music/shunzi.wav");
	}*/

	public long getSongNumber() {
		return songNumber;
	}

	public void setSongNumber(long songNumber) {
		this.songNumber = songNumber;
	}

	public long getSongNumberNow() {
		return songNumberNow;
	}

	public void setSongNumberNow(long songNumberNow) {
		this.songNumberNow = songNumberNow;
	}

	
	
}
