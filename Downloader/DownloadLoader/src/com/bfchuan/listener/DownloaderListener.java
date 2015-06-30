package com.bfchuan.listener;

import java.io.IOException;

import com.bfchuan.controller.TaskController;
import com.bfchuan.entities.Task;

/**
 * 整个下载器的总监听
 * @author Administrator
 *
 */
public interface DownloaderListener {
	
	/**
	 * 开始新建任务，准备获取任务信息
	 */
	public void newTask();
	
	/**
	 * 根据传来的任务新建任务
	 */
	public void newTask(Task task);
	
	/**
	 * 暂停该任务管理器管理的任务
	 * @param taskController
	 */
	public void pauseTask(TaskController taskController);
	
	/**
	 * 重新开始该任务管理器管理的任务
	 * @param taskController
	 */
	public void restartTask(TaskController taskController);
	
	/**
	 * 重新下载该任务管理器管理的任务
	 * @param taskController
	 */
	public void reDownload(TaskController taskController);
	
	/**
	 * 开始任务管理器管理的对应任务
	 * @param taskController
	 */
	public void startTask(TaskController taskController);
	
	/**
	 * 删除任务
	 * @param taskController
	 */
	public void deleteTask(TaskController taskController);
	
	/**
	 * 打开已完成的文件
	 */
	public void openFile(TaskController taskController) throws IOException;
	
	/**
	 * 打开任务所在的文件夹
	 * @param taskController
	 */
	public void openFolder(TaskController taskController) throws IOException;
	
	/**
	 * 将任务控制器管理的任务移动到垃圾箱
	 */
	public void addTaskToGarbage(TaskController taskController);
	
	/**
	 * 将任务从垃圾箱还原出来
	 * @param taskController
	 */
	public void resumeTaskFromGarbage(TaskController taskController);
	
	/**
	 * 清空垃圾箱
	 */
	public void clearGarbageBin();
	
	/**
	 * 为任务控制器管理的任务增加线程
	 * @param taskController
	 */
	public void addThread(TaskController taskController);
	
	/**
	 * 为任务控制器管理的任务减去线程
	 * @param taskController
	 */
	public void subThread(TaskController taskController);
	
	
	/**
	 * 暂停全部正在下载的任务
	 */
	public void pauseAll();
	
	/**
	 * table行选择改变
	 * @param taskController
	 */
	public void changeTableSelect(TaskController taskController);
	
	/**
	 * 切换视图，分为正在下载、已完成、垃圾箱
	 * @param status
	 */
	public void setViewStatus(int status);
	
	/**
	 * 获得当前视图
	 */
	public int getViewStatus();
	
	/**
	 * 退出下载器
	 */
	public void exit();
	
}
