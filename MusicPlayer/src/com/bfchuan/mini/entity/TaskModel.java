package com.bfchuan.mini.entity;

import java.io.Serializable;
import java.net.URL;

/**
 * 下载任务类
 * 作者:Loenidas
 * 时间:2012-5-10
 * piaobomengxiang@163.com
 * 版本:v1.0
 *
 */
@SuppressWarnings("serial")
public class TaskModel implements Serializable {

	private URL url;// 歌曲链接
	private String songName;// 歌曲名称
	private String artist; // 演唱者
	private String album; //专辑
	private String mszie;// 显示的歌曲大小M
	private long curPos;//当前的下载位置
	private long totalSize;// 歌曲总大小

	public String getSongName() {
		return songName;
	}

	public void setSongName(String songName) {
		this.songName = songName;
	}

	public long getCurPos() {
		return curPos;
	}

	public void setCurPos(long curPos) {
		this.curPos = curPos;
	}

	public long getTotalSize() {
		return totalSize;
	}

	public void setTotalSize(int titolSize) {
		this.totalSize = titolSize;
	}

	public URL getURL() {
		return url;
	}

	public void setURL(URL url) {
		this.url = url;
	}
	
	public void setURL(String urlstr) {
		try {
			this.url = new URL(urlstr);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public String getAlbum() {
		return album;
	}

	public void setAlbum(String album) {
		this.album = album;
	}

	public String getMszie() {
		return mszie;
	}

	public void setMszie(String mszie) {
		this.mszie = mszie;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof TaskModel)) {
			return false;
		}
		TaskModel tm = (TaskModel)obj;
		if (tm.getURL().equals(this.getURL()) && 
				tm.getSongName().equals(this.getSongName())) {
			return true;
		}
		return false;
	}

}
