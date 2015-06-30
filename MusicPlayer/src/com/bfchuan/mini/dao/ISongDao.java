package com.bfchuan.mini.dao;

import java.util.Vector;

import com.bfchuan.mini.entity.Song;

public interface ISongDao {

	/**
	 * 保存歌曲列表
	 */
	public abstract void saveSongList();

	/**
	 * 保存本地曲库列表
	 */
	public abstract void saveLocalMusicList();

	/**
	 * 返回所有歌曲列表
	 */
	public abstract Vector<Song> getAllSongList();

	/**
	 * 得到本地曲库的目录列表
	 */
	public abstract Vector<String> getLocalMusicFolderList();

	/**
	 * 增加歌曲到播放列表中
	 */
	public abstract void addSong(Song song);

	/**
	 * 添加文件夹到本地曲库中
	 */
	public abstract void addFolderToLocalMusic(String folder);

	/**
	 * 清空播放列表
	 */
	public abstract void removeAllSong();

	/**
	 * 清空本地曲库
	 */
	public abstract void removeAllLocalMusic();

	/**
	 * 删除指定的本地曲库目录
	 */
	public abstract void removeLocalMusicFolder(String folder);

	/**
	 * 删除指定下标的歌曲
	 */
	public abstract void removeSongByIndex(int index);

}