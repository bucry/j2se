package com.bfchuan.mini.dao;

import java.util.Properties;


/**
 * 作者:Loenidas
 * 时间:2012-5-10
 * piaobomengxiang@163.com
 * 版本:v1.0
 *
 */
public interface IConfigDao {

	/**
	 * 保存配置
	 */
	public abstract boolean saveConfig();

	/**
	 * 得到本系统的属性
	 */
	public abstract Properties getSystemProperties();

	/**
	 * 得到歌词的属性
	 */
	public abstract Properties getLrcProperties();

	/**
	 * 得到系统主题的属性
	 */
	public abstract Properties getThemeProperties();

	/**
	 * 得到歌词背景图片路径
	 */
	public abstract String getLrcbg();

	/**
	 * 得到主题的下标
	 */
	public abstract String getTheme();

	/**
	 * 得到声音的大小
	 */
	public abstract String getVoice();

	/**
	 * 得到网络音乐缓冲的目录
	 */
	public abstract String getNetMusicBufferFolder();

	/**
	 * 得到网络音乐下载的目录
	 */
	public abstract String getNetMusicDownloadFolder();

	/**
	 * 设置歌词背景
	 */
	public abstract void setLrcbg(String lrcbg);

	/**
	 * 设置系统主题
	 */
	public abstract void setTheme(String theme);

	/**
	 * 设置声音
	 */
	public abstract void setVoice(String voice);

	/**
	 * 设置网络音乐缓存的目录
	 */
	public abstract void setNetMusicBufferFolder(String folder);

	/**
	 * 设置网络音乐下载的目录
	 */
	public abstract void setNetMusicDownloadFolder(String folder);

}