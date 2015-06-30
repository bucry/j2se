package com.bfchuan.mini.dao.impl;

import java.io.File;
import java.util.Vector;


import com.bfchuan.mini.dao.ISongDao;
import com.bfchuan.mini.entity.Song;

/**
 * 歌曲操作的dao层实现类
 * 作者:Loenidas
 * 时间:2012-5-10
 * piaobomengxiang@163.com
 * 版本:v1.0
 *
 */
public class SongDao extends BaseDao implements ISongDao {

	private Vector<Song> songList;// 歌曲列表
	private Vector<String> localFolderList;// 增加的本地曲库文件夹
	
	@SuppressWarnings("unchecked")
	public SongDao() {
		Object obj = readObjectFromFile("resource" + File.separator + "miniSongList.pl");
		if (obj == null) {
			songList = new Vector<Song>();
		} else {
			songList = (Vector<Song>)obj;
		}
		obj = readObjectFromFile("resource" + File.separator + "miniLocalMusic.lm");
		if (obj == null) {
			localFolderList = new Vector<String>();
		} else {
			localFolderList = (Vector<String>)obj;
		}
	}
	
	/**
	 * 保存歌曲列表
	 */
	public void saveSongList() {
		writeObjectToFile("resource" + File.separator + "miniSongList.pl", songList);
	}
	
	/**
	 * 保存本地曲库列表
	 */
	public void saveLocalMusicList() {
		writeObjectToFile("resource" + File.separator + "miniLocalMusic.lm", localFolderList);
	}
	
	/**
	 * 返回所有歌曲列表
	 */
	public Vector<Song> getAllSongList() {
		return this.songList;
	}
	
	/**
	 * 得到本地曲库的目录列表
	 */
	public Vector<String> getLocalMusicFolderList() {
		return this.localFolderList;
	}

	/**
	 * 增加歌曲到播放列表中
	 */
	public void addSong(Song song) {
		songList.add(song);
	}
	
	/**
	 * 添加文件夹到本地曲库中
	 */
	public void addFolderToLocalMusic(String folder) {
		localFolderList.add(folder);
	}

	/**
	 * 清空播放列表
	 */
	public void removeAllSong() {
		songList.removeAllElements();
	}
	
	/**
	 * 清空本地曲库
	 */
	public void removeAllLocalMusic() {
		localFolderList.removeAllElements();
	}
	
	/**
	 * 删除指定的本地曲库目录
	 */
	public void removeLocalMusicFolder(String folder) {
		localFolderList.remove(folder);
	}

	/**
	 * 删除指定下标的歌曲
	 */
	public void removeSongByIndex(int index) {
		songList.remove(index);
	}
	
}
