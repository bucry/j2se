package com.bfchuan.mini.dao;

import java.util.Vector;


import com.bfchuan.mini.entity.TaskModel;
import com.bfchuan.mini.util.DownloadThread;

public interface IDownloadDao {

	/**
	 * 返回所有的下载任务列表
	 */
	public abstract Vector<TaskModel> getAllTaskList();

	/**
	 * 返回所有的下载线程
	 */
	public abstract Vector<DownloadThread> getAllTaskThread();

	/**
	 * 保存下载的任务列表
	 */
	public abstract void saveTaskList();

	/**
	 * 增加下载任务
	 */
	public abstract void addTask(TaskModel tm);

	/**
	 * 移除下载任务
	 */
	public abstract void removeTask(TaskModel tm);

	/**
	 * 增加任务线程
	 */
	public abstract void addTaskThread(DownloadThread tt);

	/**
	 * 删除下载线程
	 */
	public abstract void removeTaskThread(DownloadThread tt);

}