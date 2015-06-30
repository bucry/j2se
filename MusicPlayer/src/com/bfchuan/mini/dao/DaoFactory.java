package com.bfchuan.mini.dao;

import com.bfchuan.mini.dao.impl.ConfigDao;
import com.bfchuan.mini.dao.impl.DownloadDao;
import com.bfchuan.mini.dao.impl.LrcDao;
import com.bfchuan.mini.dao.impl.SongDao;

/**
 * 数据库处理层的工厂类
 * 作者:Loenidas
 * 时间:2012-5-10
 * piaobomengxiang@163.com
 * 版本:v1.0
 *
 */
public class DaoFactory {

	private static DaoFactory daoFactory;
	private IConfigDao iConfigDao;
	private IDownloadDao iDownloadDao;
	private ILrcDao iLrcDao;
	private ISongDao iSongDao;
	
	private DaoFactory() {
	}
	
	/**
	 * 返回配置文件处理的dao层单例对象
	 * @return
	 */
	public IConfigDao getConfigDao() {
		if (iConfigDao == null) {
			iConfigDao = new ConfigDao();
		}
		return iConfigDao;
	}
	
	/**
	 * 返回下载操作的dao层单例对象
	 * @return
	 */
	public IDownloadDao getDownloadDao() {
		if (iDownloadDao == null) {
			iDownloadDao = new DownloadDao();
		}
		return iDownloadDao;
	}

	/**
	 * 返回歌词处理的dao层单例对象
	 * @return
	 */
	public ILrcDao getLrcDao() {
		if (iLrcDao == null) {
			iLrcDao = new LrcDao();
		}
		return iLrcDao;
	}
	
	/**
	 * 返回歌曲处理的dao层单例对象
	 * @return
	 */
	public ISongDao getSongDao() {
		if (iSongDao == null) {
			iSongDao = new SongDao();
		}
		return iSongDao;
	}
	
	public static DaoFactory getInstance() {
		if (daoFactory == null) {
			daoFactory = new DaoFactory();
		}
		return daoFactory;
	}
	
}
