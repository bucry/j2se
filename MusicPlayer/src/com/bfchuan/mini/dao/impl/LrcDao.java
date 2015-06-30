package com.bfchuan.mini.dao.impl;

import java.io.File;
import java.util.ArrayList;

import netlrc.search.LRC;

import com.bfchuan.mini.dao.ILrcDao;

/**
 * 歌词的数据存储层
 * 作者:Loenidas
 * 时间:2012-5-10
 * piaobomengxiang@163.com
 * 版本:v1.0
 *
 */
public class LrcDao extends BaseDao implements ILrcDao {
	
	//用来存储网络搜索的歌词列表
	private ArrayList<LRC> netLrcList;
	
	public LrcDao() {
	}
	
	/**
	 * 返回所有网络歌词的列表
	 */
	public ArrayList<LRC> getNetLrcList() {
		if (netLrcList == null) {
			netLrcList = new ArrayList<LRC>();
		}
		return netLrcList;
	}
	
	public void setNetLrcList(ArrayList<LRC> netLrcList) {
		this.netLrcList = netLrcList;
	}

	/**
	 * 保存歌词到本地文件中
	 * @param localPath 是对应歌曲的本地路径
	 * @param lrcString 歌词内容
	 */
	public void saveLrcToFile(String localPath, String lrcString) {
		try {
			String lrcPath = localPath.substring(0, localPath.length() - 4) + ".lrc";
			File lrcFile = new File(lrcPath);
			
			//如果歌词文件不存在,就创建歌词文件
			if (!lrcFile.exists()) {
				lrcFile.createNewFile();
			}
			this.writeStringToFile(lrcFile, lrcString);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
