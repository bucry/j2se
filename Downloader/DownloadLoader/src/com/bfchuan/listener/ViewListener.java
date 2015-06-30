package com.bfchuan.listener;

import com.bfchuan.controller.TaskController;

public interface ViewListener {
	/**
	 * 启动下载器时的初始化
	 */
	public void startInit();
	/**
	 * 显示“关于”的内容
	 */
	public void showAboatMsg();
	
	/**
	 * 显示“功能介绍”的内容
	 */
	public void showIntroduce();
	
	/**
	 * 显示该任务控制器管理的任务的信息
	 * @param taskController
	 */
	public void showTaskMsg(TaskController taskController);
	
}
