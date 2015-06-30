package com.bfchuan.mini.dao.impl;

import java.io.File;
import java.util.Vector;


import com.bfchuan.mini.dao.IDownloadDao;
import com.bfchuan.mini.entity.TaskModel;
import com.bfchuan.mini.util.DownloadThread;

/**
 * 网络歌曲下载的dao层实现类
 * 作者:Loenidas
 * 时间:2012-5-10
 * piaobomengxiang@163.com
 * 版本:v1.0
 *
 */
public class DownloadDao extends BaseDao implements IDownloadDao {

	private Vector<TaskModel> taskList;
	private Vector<DownloadThread> taskThreads;
	

	@SuppressWarnings("unchecked")
	public DownloadDao() {
		taskThreads = new Vector<DownloadThread>();//用来存储用户下载的线程
		Object obj = readObjectFromFile("resource" + File.separator + "task.list");
		if (obj == null) {
			taskList = new Vector<TaskModel>();
		} else {
			taskList = (Vector<TaskModel>)obj;
		}
	}
	
	/**
	 * 返回所有的下载任务列表
	 */
	public Vector<TaskModel> getAllTaskList() {
		return taskList;
	}
	
	/**
	 * 返回所有的下载线程
	 */
	public Vector<DownloadThread> getAllTaskThread() {
		return taskThreads;
	}
	
	/**
	 * 保存下载的任务列表
	 */
	public void saveTaskList() {
		writeObjectToFile("resource" + File.separator + "task.list", taskList);
	}

	/**
	 * 增加下载任务
	 */
	public void addTask(TaskModel tm) {
		taskList.add(tm);
	}

	/**
	 * 移除下载任务
	 */
	public void removeTask(TaskModel tm) {
		taskList.remove(tm);
	}
	
	/**
	 * 增加任务线程
	 */
	public void addTaskThread(DownloadThread tt) {
		taskThreads.add(tt);
	}
	
	/**
	 * 删除下载线程
	 */
	public void removeTaskThread(DownloadThread tt) {
		taskThreads.remove(tt);
	}
	
}
