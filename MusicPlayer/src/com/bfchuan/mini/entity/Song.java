package com.bfchuan.mini.entity;

import java.io.File;
import java.io.Serializable;

/**
 * 歌曲类
 * 作者:Loenidas
 * 时间:2012-5-10
 * piaobomengxiang@163.com
 * 版本:v1.0
 *
 */
@SuppressWarnings("serial")
public class Song implements Serializable {

	private String key;// 区别于所有的song
	private String songName; // 歌曲名称
	private String artist; // 演唱者
	private String album; //专辑
	private String year; // 年限
	private String comment; //内容
	private long totalLength; // 总长度
	private int totalTime; //总时长
	private boolean netMusic; // 是否是网络音乐
	private String localPath;//歌曲本地路径
	private String songURL;// 网络歌曲的URL
	private String lrcURL;// 网络歌词的URL
	
	private File parentFile;//这个歌曲的父目录

	public Song() {
	}
	
	public String getSongName() {
		return songName;
	}

	public void setSongName(String songName) {
		this.songName = songName;
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

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public long getTotalLength() {
		return totalLength;
	}

	public void setTotalLength(long totalLength) {
		this.totalLength = totalLength;
	}

	public int getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(int totalTime) {
		this.totalTime = totalTime;
	}

	public boolean isNetMusic() {
		return netMusic;
	}

	public void setNetMusic(boolean netMusic) {
		this.netMusic = netMusic;
	}

	public String getSongURL() {
		return songURL;
	}

	public void setSongURL(String songURL) {
		this.songURL = songURL;
	}
	
	public String getLrcURL() {
		return lrcURL;
	}

	public void setLrcURL(String lrcURL) {
		this.lrcURL = lrcURL;
	}
	
	public String getLocalPath() {
		return localPath;
	}

	public void setLocalPath(String localPath) {
		this.localPath = localPath;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	
	public File getParentFile() {
		if (parentFile == null) {
			parentFile = new File(this.getLocalPath()).getParentFile();
		}
		return parentFile;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Song)) {
			return false;
		}
		Song song = (Song)obj;
		if (song.getKey().equals(this.getKey())) {
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return "歌曲名称" + songName + ", 本地路径: " + localPath;
	}
}
