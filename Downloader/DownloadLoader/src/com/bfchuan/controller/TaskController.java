package com.bfchuan.controller;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.bfchuan.downloader.PieceManager;
import com.bfchuan.downloader.PieceThread;
import com.bfchuan.entities.Task;
import com.bfchuan.listener.TaskListener;
import com.bfchuan.util.Global;
import com.bfchuan.util.TaskUtil;

/**
 * 分任务的控制器
 * @author Administrator
 *
 */
public class TaskController implements Runnable, TaskListener,Serializable {
	
	private static final long serialVersionUID = 1L;
	/**
	 * 任务控制器“正在下载”
	 */
	public static final int TASKCONTROLLER_RUNNING = 1;
	/**
	 * 任务控制器"已完成"
	 */
	public static final int TASKCONTROLLER_COMPLETE = 2;
	/**
	 * 任务控制器“垃圾箱”
	 */
	public static final int TASKCONTROLLER_GARBAGE = 3;
	
	/**
	 * 任务控制器的状态，初始状态为running
	 */
	private int TaskControllerStatus = TASKCONTROLLER_RUNNING;
	
	/**
	 * 该控制器要控制的任务
	 */
	 Task task;
	 /**
	  * 管理任务分块的分块管理器
	  */
	 PieceManager pm;
	 /**
	  * 分块下载线程
	  */
	 List<PieceThread> pieceThreadList = new ArrayList<PieceThread>();
	 
	 private long startTime;
	 private long takedTime;  // 之前耗时
	 private long takesTime;   //总耗时 = 当前时间 - startTime + 之前耗时
	
	
	/**
	 * 有参构造器，传入对应的Task，一个Task对应一个TaskController
	 * @param task
	 */
	public TaskController(Task task){
		this.task = task;
	}

	/**
	 * 覆写run方法，开始下载
	 */
	public void run() {
		/*统计时间初始化*/
		takedTime = task.getTakesTime();
		startTime = new Date().getTime();
		
		/*获取分块管理器*/
		if(task.getStatus() == Task.STATE_NEW){ // 若该任务是新建的，则需要获取相应信息，否则可以直接启动线程
			try {
				/*获得文件大小*/
				long fileSize = TaskUtil.getFileSize(task.getSourceUrl());
				task.setFileSize(fileSize);
				/*获得文件后缀*/
				String fileName = TaskUtil.getRealFileName(task.getSourceUrl());
				String postfix = TaskUtil.getFilePostfix(fileName);
				task.setFileName(task.getFileName() + "." +postfix);
			} catch (IOException e) {
				System.out.println("获取文件名、文件大小出现异常！");
				e.printStackTrace();
			}
			pm = new PieceManager(task);
			//task.setPm(pm);//将分块管理器装入task中，供续传时候取出
		}else{		//任务不是新建的，则其之前已经有了分块管理器
			/*获取之前的分块管理器*/
			//pm = task.getPm();
			
			/*清空之前的分块线程池*/
			pieceThreadList.clear();
		}
		
		/*启动线程，开始分块下载*/
		for(int i=0; i < task.getThreadAmount();i++){
			PieceThread pieceThread = new PieceThread(pm);
			pieceThread.start();
			pieceThreadList.add(pieceThread); //将该线程添加到线程池中
			task.setStatus(Task.STATE_RUNNING);//将任务的状态修改为”运行中“
		}
		
		//这里需不需要保存？需要斟酌……
		/*保存任务管理器对象*/
		//TaskUtil.saveTaskControllerToFile(this);
		
		/*启动内线程，监控下载状态*/
		new InnerThread().start();
	}
	
	/**
	 * 内线程,用于即时统计时间，下载进度，下载速度等信息<br>
	 * 并判断任务是否完成或失败
	 * @author Administrator
	 *
	 */
	private class InnerThread extends Thread implements Serializable {

		private static final long serialVersionUID = 1L;
		public void run() {
			while(task.getStatus() == Task.STATE_RUNNING){
				System.out.println(new File(pm.getTask().getFilePathName()).length());
				
				/*计算耗费的时间*/
				takesTime = System.currentTimeMillis() - startTime + takedTime;
				task.setTakesTime(takesTime);
				
				/*计算下载进度*/
				long loadSize = new File(pm.getTask().getFilePathName()).length();
				task.setLoadSize(loadSize);
				float progressRate = (float)loadSize/task.getFileSize();
				task.setProgressRate(progressRate);
				
				/*计算下载速度*/
				float averageSpeet = (float)task.getLoadSize()/task.getTakesTime();
				task.setAverageSpeet(averageSpeet);
				
				/*判定任务是否完成*/
				if(isTaskComplete()){
					taskComplete();
					System.out.println("下载完成！");
				}	
				
				/*判断任务是否失败*/
				if(isTaskFailure()){
					taskFailed();
				}
				
				try {
					sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
	}

	/**
	 * 线程+1
	 */
	public void addThread() {
		System.out.println("addThead");
		int currentThread = this.task.getThreadAmount();
		/*线程数超出最大*/
		if(currentThread >= Global.MAX_THREADS){
			return;
		}
		this.task.setThreadAmount(currentThread + 1);
		PieceThread pieceThread = new PieceThread(pm);
		pieceThread.start();
		pieceThreadList.add(pieceThread); //将该线程添加到线程池中
	}


	/**
	 * 线程-1
	 */
	public void subThread() {
		System.out.println("subThread");
		int currentThread = this.task.getThreadAmount();
		
		/*线程数已不能再减*/
		if(currentThread <= 1){
			return;
		}
		
		/*活动线程数只剩最后一个，不能再减*/
		if(getAliveThreadSum() == 1){
			return;
		}
		
		this.task.setThreadAmount(currentThread - 1);
		
		/*删除最后一个*/
		int size = pieceThreadList.size();
		PieceThread pt = pieceThreadList.get(size-1);
		
		/*删除即是让其中断*/
		pt.stopMe();
		
		/*移出线程池*/
		pieceThreadList.remove(pt);
	}

	/**
	 * 获得当前下载分块的活动线程数
	 * @return
	 */
	public int getAliveThreadSum(){
		int count_isAlive = 0;
		for(Iterator<PieceThread> it = pieceThreadList.iterator();it.hasNext();){
			PieceThread pieceThread = it.next();
			if(pieceThread.isAlive()){
				count_isAlive ++;
			}
		}
		return count_isAlive;
	}
	
	public void deleteTask() {
		// TODO Auto-generated method stub

	}

	/**
	 * 暂停任务
	 */
	public void pauseTask() {
		
		/*将该任务设为暂停状态*/
		this.getTask().setStatus(Task.STATE_PAUSED);
		
		/*停止所有正在下载的分块线程*/
		for(Iterator<PieceThread> it = pieceThreadList.iterator();it.hasNext();){
			PieceThread pieceThread = it.next();
			pieceThread.stopMe(); //调用线程自身的暂停方法
		}
		
		/*保存该控制器对象*/
		TaskUtil.saveTaskControllerToFile(this);
	}

	 /**
	  * 重新开始任务
	  */
	public void restartTask() {
		// TODO Auto-generated method stub
	}

	/**
	 * 开始任务
	 */
	public void startTask() {

	}

	/**
	 * 任务完成
	 */
	public void taskComplete() {
		/*将任务状态设置为完成*/
		task.setStatus(Task.STATE_COMPLETED);
		
		/*设置任务完成时间*/
		task.setEndTime(new Date().getTime());
		
		/*删除该任务在“正在下载”文件夹中的文件*/
		String file = Global.RUNNING_TASK_PATH + Global.FILE_SEPARATOR + task.getFileName() + "." + Global.TASK_OBJECT_POSTFIX;
		boolean b = TaskUtil.deleteFile(file);
		if(b){
			System.out.println("删除" + file +"成功！");
		}else{
			System.out.println("删除" + file +"失败！");
		}
		
		/*将自己的状态转为完成*/
		this.setTaskControllerStatus(TASKCONTROLLER_COMPLETE);
		
		/*保存已完成的对象成文件*/
		//String fileComplete = Global.COMPLETE_TASK_PATH + "/" + task.getFileName() + "." + Global.TASK_OBJECT_POSTFIX;
		TaskUtil.saveTaskControllerToFile(this);
	}
	
	/**
	 * 任务失败
	 */
	public void taskFailed() {
		task.setStatus(Task.STATE_FAILED);
		/*保存失败对象*/
		TaskUtil.saveTaskControllerToFile(this);
	}
	
	/**
	 * 获取当前控制的任务
	 * @return
	 */
	public Task getTask() {
		return task;
	}

	/**
	 * 判断该任务是否下载完成
	 */
	public boolean isTaskComplete() {
		if(pm.getPiecesPoolStatus() == PieceManager.PIECES_COMPLETE){
			for(Iterator<PieceThread> it = pieceThreadList.iterator();it.hasNext();){
				PieceThread pieceThread = it.next();
				if(pieceThread.isAlive()){
					System.out.println("--------------alive");
					return false;
				}
			}
			return true; // 当分块池全部完成，且分块线程全部结束时，判定任务下载完成
		}
		return false;
	}

	/**
	 * 判断任务是否失败
	 */
	public boolean isTaskFailure() { //分块线程中的所有线程全部失败时，判定任务失败
		int count_failed = 0;
		int count_isAlive = 0;
		for(Iterator<PieceThread> it = pieceThreadList.iterator();it.hasNext();){
			PieceThread pieceThread = it.next();
			if(pieceThread.isFailed()){
				count_failed ++;
			}
			if(pieceThread.isAlive()){
				count_isAlive ++;
			}
		}
		//若任务处于运行中，而全部分块线程失败或全部线程都已经结束，则判定任务失败
		if(this.getTaskControllerStatus() == TASKCONTROLLER_RUNNING 
				&& (count_failed == this.getTask().getThreadAmount() 
						|| count_isAlive == 0)){
			return true;
		}
		return false;
	}
	
	//---------------get、set方法-------------------
	
	/**
	 * 设置控制器控制的任务
	 */
	public void setTask(Task task) {
		this.task = task;
	}
	/**
	 * 获取当前控制器的状态
	 * @return
	 */
	public int getTaskControllerStatus() {
		return TaskControllerStatus;
	}
	/**
	 * 设置当前控制器的状态
	 * @param taskControllerStatus
	 */
	public void setTaskControllerStatus(int taskControllerStatus) {
		TaskControllerStatus = taskControllerStatus;
	}
	
	/**
	 * 获取与控制器对应的分块管理者
	 * @return
	 */
	public PieceManager getPm() {
		return pm;
	}
}
