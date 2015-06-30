package com.bfchuan.controller;

import java.io.IOException;
import java.util.*;


import com.bfchuan.entities.Task;
import com.bfchuan.listener.DownloaderListener;
import com.bfchuan.util.Global;
import com.bfchuan.util.TaskUtil;

/**
 * 下载器的总控制器<br>
 * 控制正在下载、已完成、垃圾箱中的所有任务，下达指令执行相应的下载操作；<br>
 * 该下载器的工作机制是：MainController控制TaskController，然后TaskController控制Task
 *  @author Administrator
 *
 */
public class MainController implements DownloaderListener{
	
	/**
	 * 正在下载视图常量
	 */
	public static final int RUNNING_VIEW = 1;
	/**
	 * 已完成视图常量
	 */
	public static final int COMPLETE_VIEW = 2;
	/**
	 * 垃圾箱视图常量
	 */
	public static final int GARBAGEBIN_VIEW = 3;
	/**
	 * 控制器当前控制的视图，缺省为“正在下载”
	 */
	private int frameStatus = 1;
	
	/**
	 * 总控制器需要管理的全部任务
	 */
	Set<TaskController> taskControllerSet = new HashSet<TaskController>();
	/**
	 * 一个任务管理器对应一个触发线程，用Map保存,这是未完成任务的map
	 */
	Map<TaskController,Thread> ctrlThdMap = new HashMap<TaskController,Thread>();
	
	/**
	 * 已完成的任务的map
	 */
	Map<TaskController,Thread> completedMap = new HashMap<TaskController,Thread>();
	
	/**
	 * 垃圾箱中的对象map
	 */
	Map<TaskController,Thread> garbageBinMap = new HashMap<TaskController,Thread>();
	
	/**
	 * 被选择的任务控制器，当前支持每次选一个任务
	 */
    TaskController selectTaskController;
	
	public MainController(){
		this.init();
	}
	
	/**
	 * 初始化之前的任务
	 */
	public void init(){
		/*正在下载的任务*/
		ctrlThdMap = TaskUtil.loadTaskControllersFromFile(Global.RUNNING_TASK_PATH);
		/*已经完成的任务*/
		completedMap =  TaskUtil.loadTaskControllersFromFile(Global.COMPLETE_TASK_PATH);
		/*垃圾箱*/
		garbageBinMap = TaskUtil.loadTaskControllersFromFile(Global.GARBAGE_TASK_PATH);
		
	}
	
	public void exit() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * 新建任务，弹出对话框填信息
	 */
	public void newTask() {
		//this.frame.newTask();
	}

	/**
	 * 根据传来的任务信息新建任务
	 * @param task
	 */
	public void newTask(Task task){
		
		/*根据任务获取任务控制器*/
		TaskController taskController = new TaskController(task);
		
		/*设置任务控制器的初始状态*/
		taskController.setTaskControllerStatus(TaskController.TASKCONTROLLER_RUNNING);
		
		/*将任务控制器加入到总控制器的任务控制器池中*/
		this.taskControllerSet.add(taskController);
		
		/*开始下载*/
		this.startTask(taskController);
		
		/*保存任务对象*/
		//TaskUtil.saveTasktoFile(task);
		/*保存任务管理器对象*/
		//TaskUtil.saveTaskControllerToFile(tc);
	}
	
	/**
	 * 根据传来的任务管理器启动对应任务
	 */
	public void startTask(TaskController taskController) {
		Thread thread = new Thread(taskController);
		
		thread.start();
		
		/*加入map中*/
		ctrlThdMap.put(taskController,thread);
		
		System.out.println("---------------"+ctrlThdMap.get(taskController));
		
//		/*在table中显示*/
//		this.frame.showTable(ctrlThdMap);
		
//		/* 启动内线程 */
//		this.startInnerThread();
	}

	
	
	/**
	 * 从正在下载的任务中检查是否有任务下载完成,返回完成的任务Set
	 */
	public Set<TaskController> checkRunningToComplete() {
		Set<TaskController> completeSet = new HashSet<TaskController>();
		Set<TaskController> taskControllerSet = ctrlThdMap.keySet();
		for(Iterator<TaskController> it = taskControllerSet.iterator();it.hasNext();){
			TaskController taskController = it.next();
			Task task = taskController.getTask();
			if(task.getStatus() == Task.STATE_COMPLETED){
//				JOptionPane.showMessageDialog(this.frame, task.getFileName() + " 下载完成,用时 "
//						+ task.getTakesTime()/1000 + " 秒！", "完成",
//						JOptionPane.INFORMATION_MESSAGE);
				/*加入到已完成*/
				this.completedMap.put(taskController, this.ctrlThdMap.get(taskController));
				
				/*移出下载中*/
				this.ctrlThdMap.remove(taskController);
				
				/*添加到完成set中，供返回*/
				completeSet.add(taskController);
			}
		}	
		return completeSet;
	}
	
	/**
	 * 判读是否有正在运行的任务
	 * @return
	 */
	public boolean isHaveRunningTask(){
		Set<TaskController> taskController = ctrlThdMap.keySet();
		for(Iterator<TaskController> it = taskController.iterator();it.hasNext();){
			Task task = it.next().getTask();
			if(task.getStatus() == Task.STATE_RUNNING || task.getStatus() == Task.STATE_NEW){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * table的选择改变了
	 */
	public void changeTableSelect(TaskController taskController) {
		this.setSelectTaskController(taskController);
	}
	
	/**
	 * 暂停该任务
	 */
	public void pauseTask(TaskController taskController) {
		/*若任务不是下载中状态则返回*/
		if(taskController.getTask().getStatus() != Task.STATE_RUNNING){
			return;
		}
		
	    /*调用任务管理器自己的暂停处理方法*/
	   taskController.pauseTask();
	   
	   /*停止该任务的触发线程*/
	   Thread thread =  this.ctrlThdMap.get(taskController);
	   System.out.println("--------2-------"+ctrlThdMap.get(taskController));
	   thread.interrupt();
	}

	 /**
	  * 重新开始该任务
	  */
	public void restartTask(TaskController taskController) {
		System.out.println("restartTask");
		
		Thread thread = new Thread(taskController);
		
		thread.start();
		
		this.ctrlThdMap.put(taskController,thread);
		
	}
	
	/**
	 * 重新下载任务
	 */
	public void reDownload(TaskController taskController){
		/*将任务设置为新建*/
		taskController.getTask().setStatus(Task.STATE_NEW);
		
		/*重新开始下载*/
		this.restartTask(taskController);
	}
	
	/**
	 * 删除任务到垃圾箱，若任务就在垃圾箱则彻底删除
	 */
	public void deleteTask(TaskController taskController) {
		
		System.out.println("deleteTask");
		/*对象文件的文件名*/
		String tempFileName = taskController.getTask().getFileName() + "." + Global.TASK_OBJECT_POSTFIX;
		
		if(this.frameStatus == GARBAGEBIN_VIEW){// 当前视图是垃圾箱，则将任务彻底删除
			/*从垃圾箱map中移除*/
			this.garbageBinMap.remove(taskController);
			/*删除文件*/
			TaskUtil.deleteFile(Global.GARBAGE_TASK_PATH + Global.FILE_SEPARATOR + tempFileName);
		}else if(this.frameStatus == RUNNING_VIEW){  //否则，将对象文件移动到垃圾箱
			/*任务运行中，得先暂停任务*/
			if(taskController.getTask().getStatus() == Task.STATE_RUNNING){
				pauseTask(taskController);
			}
			
			this.ctrlThdMap.remove(taskController);
			TaskUtil.moveFile(Global.RUNNING_TASK_PATH + Global.FILE_SEPARATOR + tempFileName, Global.GARBAGE_TASK_PATH);
			
			/*添加到垃圾箱map中*/
			this.garbageBinMap.put(taskController, null);
			
			/*该任务控制器状态修改为垃圾*/
			taskController.setTaskControllerStatus(TaskController.TASKCONTROLLER_GARBAGE);
		}else if(this.frameStatus == COMPLETE_VIEW){
			this.completedMap.remove(taskController);
			TaskUtil.moveFile(Global.COMPLETE_TASK_PATH + Global.FILE_SEPARATOR + tempFileName, Global.GARBAGE_TASK_PATH);
			
			/*添加到垃圾箱map中*/
			this.garbageBinMap.put(taskController, null);
			
			/*该任务控制器状态修改为垃圾*/
			taskController.setTaskControllerStatus(TaskController.TASKCONTROLLER_GARBAGE);
		}
		
//		/*刷新table*/
//		this.frame.showTable();
	}
	
	/**
	 * 打开已完成的任务
	 * @throws IOException 
	 */
	public void openFile(TaskController taskController) throws IOException {
		System.out.println("openFile");
		Task task = taskController.getTask();
		if(task.getStatus() == Task.STATE_COMPLETED){ //只有已完成的任务可以打开		
				TaskUtil.openFile(task.getFilePathName());
		}		
		
	}
	

	/**
	 * 打开任务所在的文件夹
	 * @param taskController
	 */
	public void openFolder(TaskController taskController) throws IOException{
		Task task = taskController.getTask();
		System.out.println(task.getSavePath());
		TaskUtil.openFolder(task.getSavePath());
	}
	
	/**
	 * 将任务从垃圾箱还原出来
	 * @param taskController
	 */
	public void resumeTaskFromGarbage(TaskController taskController) {
		System.out.println("resumeTaskFromGarbage");
		Task task = taskController.getTask();
		String tcFilePath = Global.GARBAGE_TASK_PATH + Global.FILE_SEPARATOR + task.getFileName() + "." + Global.TASK_OBJECT_POSTFIX;
		int taskStatus = task.getStatus();
		if( taskStatus == Task.STATE_RUNNING || taskStatus == Task.STATE_NEW || taskStatus == Task.STATE_PAUSED){//运行中和新建状态
			/*移动文件*/
			TaskUtil.moveFile(tcFilePath, Global.RUNNING_TASK_PATH);
			
			/*添加到正在下载map*/
			this.ctrlThdMap.put(taskController, null);
			
			/*移出垃圾箱map*/
			this.garbageBinMap.remove(taskController);
			
			/*设置任务管理器状态*/
			taskController.setTaskControllerStatus(TaskController.TASKCONTROLLER_RUNNING);
			
		}else if(taskStatus == Task.STATE_COMPLETED){//已完成
			TaskUtil.moveFile(tcFilePath, Global.COMPLETE_TASK_PATH);
			
			/*添加到正在下载map*/
			this.completedMap.put(taskController, null);
			
			/*移出垃圾箱map*/
			this.garbageBinMap.remove(taskController);
			
			/*设置任务管理器状态*/
			taskController.setTaskControllerStatus(TaskController.TASKCONTROLLER_COMPLETE);
		}
		
	}
	
	/**
	 * 为任务控制器管理的任务增加线程
	 * @param taskController
	 */
	public void addThread(TaskController taskController) {
		Task task = taskController.getTask();
		int currentThread = task.getThreadAmount();
		if(currentThread >= Global.MAX_THREADS){
			
		}else{
			taskController.addThread();
		}	
//		/*刷*/
//		this.frame.showTable();
	}

	/**
	 * 为任务控制器管理的任务减去线程
	 * @param taskController
	 */
	public void subThread(TaskController taskController) {
		Task task = taskController.getTask();
		int currentThread = task.getThreadAmount();
		if(currentThread <= 1){
			
		}else{
			taskController.subThread();
		}	
//		/*刷*/
//		this.frame.showTable();
	}
	
	/**
	 * 暂停所有正在下载的任务
	 */
	public void pauseAll() {
		Set<TaskController> taskControllerSet = this.ctrlThdMap.keySet();
		/*逐个暂停任务*/
		for(Iterator<TaskController> it = taskControllerSet.iterator();it.hasNext();){
			TaskController taskController = it.next();
			this.pauseTask(taskController);
		}
	}
	
	public void addTaskToGarbage(TaskController taskController) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * 清空垃圾箱
	 */
	public void clearGarbageBin() {
		/*清空临时文件*/
		TaskUtil.deleteAllGarbageFile();
		
		/*清空垃圾箱map*/
		this.garbageBinMap.clear();
		
	}
	
	/**
	 * 获取正在显示的任务map
	 * @return
	 */
	public Map<TaskController,Thread> getViewMap(){
		if(this.frameStatus == MainController.RUNNING_VIEW){
			return ctrlThdMap;
		}else if(this.frameStatus == MainController.COMPLETE_VIEW){
			return completedMap;
		}else {
			return garbageBinMap;
		}
	}
	
	//-------------get、set方法-------------
	
	/**
	 * 获取当前视图状态
	 */
	public int getFrameStatus() {
		return frameStatus;
	}
	
	/**
	 * 设置当前视图状态
	 * @param frameStatus
	 */
	public void setFrameStatus(int frameStatus) {
		this.frameStatus = frameStatus;
		//this.frame.showTable();
	}
	
	/**
	 * 获取当前选择的任务控制器
	 * @return
	 */
	public TaskController getSelectTaskController() {
		return selectTaskController;
	}

	/**
	 * 设置当前选择的任务控制器
	 * @param selectTaskController
	 */
	public void setSelectTaskController(TaskController selectTaskController) {
		this.selectTaskController = selectTaskController;
	}
	/**
	 * 获取全部正在下载的任务
	 * @return
	 */
	public Map<TaskController, Thread> getCtrlThdMap() {
		return ctrlThdMap;
	}
	/**
	 * 获取全部已完成的任务
	 * @return
	 */
	public Map<TaskController, Thread> getCompletedMap() {
		return completedMap;
	}
	/**
	 * 获取全部垃圾箱的任务
	 * @return
	 */
	public Map<TaskController, Thread> getGarbageBinMap() {
		return garbageBinMap;
	}

	/**
	 *  设置当前视图
	 */
	public void setViewStatus(int status) {
		this.frameStatus = status;	
	}

	/**
	 * 获取当前视图
	 */
	public int getViewStatus() {
		return frameStatus;
	}

	
}
