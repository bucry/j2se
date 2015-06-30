package com.bfchuan.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.JOptionPane;

//import org.apache.log4j.Logger;

import com.bfchuan.entities.Task;
import com.bfchuan.view.MainFrame;

/**
 * 中介控制器：在下载器中充当“顾问角色”，继承自抽象类CentralController<br>
 * 			从下载器界面中进行的所有操作都会先经过该中介控制器的引导<br>
 * 			是主控制器和主窗口的中介者
 * @author Administrator
 *
 */
public class MediaController extends CentralController{
	
	/**
	 * 主控制器
	 */
	MainController mainController;
	/**
	 * 主窗口
	 */
	MainFrame mainFrame;
	
	/**
	 * 内线程，用于定时监控、刷新
	 */
	InnerThread innerThread;
	
//	/*日志记录*/
//	static Logger logger = Logger.getLogger(MediaController.class);
	
	public MediaController(MainController mainController,MainFrame mainFrame){
		this.mainController = mainController;
		this.mainFrame = mainFrame;
	}
	
	/**
	 * 启动下载器时的初始化
	 */
	public void startInit(){
		try{
			this.refreshAll();
		}catch(Exception e){
			System.out.println("初始化出错！");
		}
	}
	
	/**
	 * 开始新建任务，弹出对话框，获取任务信息
	 */
	public void newTask() {
		System.out.println("newTask()");
		this.mainFrame.newTask();		
	}

	/**
	 * 根据传来的任务，交给主控制器新建任务，该task传来的信息并不包括文件大小和文件名后缀
	 */
	public void newTask(Task task) {	
		
		/*校验task不合格*/
		if(!checkTask(task)){
			return;
		}
		
		/*交给主控制器新建任务*/
		this.mainController.newTask(task);
		
		/*界面上显示,刷新table*/
		this.showTable();
		
		/*启动内线程监控*/
		this.startInnerThread();
	}
	
	/**
	 * 检查新建的任务，若存在*同名任务*或同url任务则返回true
	 * @param task
	 * @return
	 */
	public boolean checkTask(Task newTask){
		/*先检查下载中的*/
		Map<TaskController,Thread> ctrlThdMap = this.mainController.getCtrlThdMap();
		switch (checkTaskInMap(newTask,ctrlThdMap)) {
			case 1 : JOptionPane.showMessageDialog(this.mainFrame, "已存在同URL任务在下载列表中！", "新建任务信息",0);
					 return false;
			case 2 : JOptionPane.showMessageDialog(this.mainFrame, "已存在同文件名的任务在下载列表中！", "新建任务信息",0);
					 return false;
		}
		/*已完成列表*/
		ctrlThdMap = this.mainController.getCompletedMap();
		switch (checkTaskInMap(newTask,ctrlThdMap)) {
			case 1 : JOptionPane.showMessageDialog(this.mainFrame, "已存在同URL任务在已完成列表中！", "新建任务信息",0);
					 return false;
			case 2 : JOptionPane.showMessageDialog(this.mainFrame, "已存在同文件名的任务在已完成列表中！", "新建任务信息",0);
					 return false;
		}			 
		/*垃圾箱列表*/
		ctrlThdMap = this.mainController.getGarbageBinMap();
		switch (checkTaskInMap(newTask,ctrlThdMap)) {
			case 1 : JOptionPane.showMessageDialog(this.mainFrame, "已存在同URL任务在垃圾箱列表中！", "新建任务信息",0);
					 return false;
			case 2 : JOptionPane.showMessageDialog(this.mainFrame, "已存在同文件名的任务在垃圾箱列表中！", "新建任务信息",0);
					 return false;
		}
		return true;
	}
	
	/**
	 * 检验任务的名字和url是否存在在该任务Map中<br>
	 * 返回 1 表示：有相同url
	 * 返回 2 表示：有相同文件名
	 * 返回 0 表示：无相同
	 * @param newTask
	 * @param ctrlThdMap
	 * @return
	 */
	public int checkTaskInMap(Task newTask,Map<TaskController,Thread> ctrlThdMap){
		if(ctrlThdMap != null && ctrlThdMap.size() != 0){
			Set<TaskController> taskControllerSet = ctrlThdMap.keySet();
			for(Iterator<TaskController> it = taskControllerSet.iterator();it.hasNext();){
				TaskController taskController = it.next();
				Task task = taskController.getTask();
				if(newTask.getSourceUrl().equals(task.getSourceUrl())){
					return 1;
				}
				if(newTask.getFileName().equals(task.getFileName())){
					return 2;
				}
			}
		}
		return 0;
	}
	
	/**
	 * 添加任务到垃圾箱
	 */
	public void addTaskToGarbage(TaskController taskController) {
		
	}

	/**
	 * 线程+1
	 */
	public void addThread(TaskController taskController) {
		this.mainController.addThread(taskController);
		
		this.showTable();
		this.showMsgArea();
	}
	
	/**
	 * 线程-1
	 */
	public void subThread(TaskController taskController) {
		this.mainController.subThread(taskController);
		
		this.showTable();
		this.showMsgArea();
	}

	/**
	 * 清空垃圾箱
	 */
	public void clearGarbageBin() {
		this.mainController.clearGarbageBin();
		this.showTable();
	}

	/**
	 * 删除指定任务控制器管理的任务
	 */
	public void deleteTask(TaskController taskController) {
		this.mainController.deleteTask(taskController);
		
		this.showTable();
		
	}

	/**
	 * 退出程序
	 */
	public void exit() {	
	  int option = JOptionPane.showConfirmDialog(this.mainFrame, "是否要退出迅杭下载器？",   
                 "系统提示", JOptionPane.YES_NO_OPTION,   
                 JOptionPane.QUESTION_MESSAGE);   
         if (option == JOptionPane.YES_OPTION) {
         	System.out.println(" STOP………… ");
         	/*退出前暂停所有正在运行的任务*/
         	 this.pauseAll();
         	
         	/*记录日志*/
        // 	logger.info(" Lxlei退出！");
         	
             System.exit(0);  
         }  
         System.out.println("我又不想退出了……");
	}

	/**
	 * 打开文件
	 */
	public void openFile(TaskController taskController) {
		try {
			this.mainController.openFile(taskController);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this.mainFrame, "打开文件出错！", "Error",0);
		}
	}

	/**
	 * 打开任务所在的文件夹
	 * @param taskController
	 */
	public void openFolder(TaskController taskController){
		try {
			this.mainController.openFolder(taskController);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this.mainFrame, "打开文件夹出错！", "Error",0);
		}
	}

	/**
	 * 暂停全部任务
	 */
	public void pauseAll() {
		this.mainController.pauseAll();
	}

	public void pauseTask(TaskController taskController) {
		this.mainController.pauseTask(taskController);	
	}

	/**
	 * 重新开始
	 */
	public void restartTask(TaskController taskController) {
		this.mainController.restartTask(taskController);
		
		this.showTable();
		
		this.startInnerThread();
		
	}

	/**
	 * 重新下载
	 */
	public void reDownload(TaskController taskController){
		/*将任务设置为新建*/
		taskController.getTask().setStatus(Task.STATE_NEW);
		
		/*重新开始下载*/
		this.restartTask(taskController);
	}
	
	/**
	 * 从垃圾箱中还原任务
	 */
	public void resumeTaskFromGarbage(TaskController taskController) {
		this.mainController.resumeTaskFromGarbage(taskController);
		this.refreshAll();
	}

	/**
	 * 开始任务
	 */
	public void startTask(TaskController taskController) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * 显示“关于信息”
	 */
	public void showAboatMsg() {
		System.out.println("showAboatMsg");
		this.mainFrame.showAbout();
	}

	/**
	 * 显示“功能介绍”
	 */
	public void showIntroduce() {
		this.mainFrame.showIntroduction();
		
	}

	/**
	 * 显示任务信息
	 */
	public void showTaskMsg(TaskController taskController) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * table的所选行发生了改变
	 */
	public void changeTableSelect(TaskController taskController) {
		/*主控制器动作*/
		this.mainController.changeTableSelect(taskController);
		
		/*界面*/
		this.showButton();
		this.showMsgArea();
		this.mainFrame.setPopupMenuStatus(taskController);
	}

	/**
	 * 设定当前视图，正在下载、已完成、垃圾箱
	 */
	public void setViewStatus(int status) {
		this.mainController.setFrameStatus(status);
		
		this.refreshAll();
	}
	
	/**
	 * 内线程，用于刷新table
	 * @author Administrator
	 *
	 */
	private class InnerThread extends Thread implements Serializable {
		private static final long serialVersionUID = -5653497905844621750L;
		@Override
		public void run() {
			while(true){
				try {
					sleep(1000);    //  每1000ms刷新一次桌面状态
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("主控制器刷");
					refreshAll();
					
					/*检查是否有任务下载完成*/
					checkRunningToComplete();
					
					if(!mainController.isHaveRunningTask()){ //若已经没有正在下载的任务，退出
						refreshAll();
						System.out.println("主控制器停刷");
						break;
					}
			}
		}
	}
	
	/**
	 * 检查是否有下载中的任务已完成，如果有，则弹出对话框信息，并刷新界面显示
	 */
	public void checkRunningToComplete(){
		Set<TaskController> completeSet = this.mainController.checkRunningToComplete();
		if(completeSet.size() != 0){  //有完成的
			for(Iterator<TaskController> it = completeSet.iterator();it.hasNext(); ){
				TaskController taskController = it.next();
				Task task = taskController.getTask();
				JOptionPane.showMessageDialog(this.mainFrame, task.getFileName() + " 下载完成,用时 "
						+ task.getTakesTime()/1000 + " 秒！", "完成",JOptionPane.INFORMATION_MESSAGE);
			}
			/*显示*/
			this.showTable();
		}
	}
	
	/**
	 * 若内线程未开启则开启
	 */
	public void startInnerThread(){
		/*若内线程为空或者已经死去，则创建、并启动*/
		if(innerThread == null || !innerThread.isAlive()){ 
			innerThread = new InnerThread();
			//innerThread.setDaemon(true);  //后台线程
			innerThread.start();
		}
	}
	
	/**
	 * 刷新table
	 */
	public void showTable(){
		Map<TaskController,Thread> ctrlThdMap = this.mainController.getViewMap();
		this.mainFrame.showTable(ctrlThdMap);
	}
	
	/**
	 * 刷新button
	 */
	public void showButton(){
		this.mainFrame.setButtonStatus(this.mainController.getSelectTaskController());
	}
	
	/**
	 * 刷新信息区域
	 */
	 public void showMsgArea(){
		 this.mainFrame.setMsgAreaStatus(this.mainController.getSelectTaskController());
	 }

	 /**
	  * 刷新全部
	  */
	 public void refreshAll(){
		 this.showTable();
		 this.showButton();
		 this.showMsgArea();
	 }

	 /**
	  * 获得当前视图
	  */
	public int getViewStatus() {
		return this.mainController.getFrameStatus();
	}
}
