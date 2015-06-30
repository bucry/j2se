package com.bfchuan.listener;

/**
 * 对应到具体任务的监听
 * @author Administrator
 *
 */
public interface TaskListener {
	
	/**
	 * 线程+1
	 */
	public void addThread();
	/**
	 * 线程-1
	 */
	public void subThread();
	/**
	 * 任务完成
	 */
	public void taskComplete();
	/**
	 * 任务暂停
	 */
	public void pauseTask();
	 /**
	  * 开始任务
	  */
	public void startTask();
	/**
	 * 重新开始任务
	 */
	public void restartTask();
	/**
	 * 删除任务
	 */
	public void deleteTask();
	/**
	 * 任务失败
	 */
	public void taskFailed();
	/**
	 * 判断任务是否失败
	 * @return
	 */
	public boolean isTaskFailure();
	/**
	 * 判断任务是否完成
	 * @return
	 */
	public boolean isTaskComplete();
	
}
