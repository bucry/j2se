package com.bfchuan.mini.dao;

import java.util.ArrayList;

import netlrc.search.LRC;

public interface ILrcDao {
	
	/**
	 * 返回所有网络歌词的列表
	 */
	public abstract ArrayList<LRC> getNetLrcList();

	public abstract void setNetLrcList(ArrayList<LRC> netLrcList);

	/**
	 * 保存歌词到本地文件中
	 * @param localPath 是对应歌曲的本地路径
	 * @param lrcString 歌词内容
	 */
	public abstract void saveLrcToFile(String localPath, String lrcString);

}