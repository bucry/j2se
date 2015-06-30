package com.bfchuan.view;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import com.bfchuan.controller.CentralController;
import com.bfchuan.controller.MainController;
import com.bfchuan.controller.TaskController;
import com.bfchuan.entities.Task;

/**
 * 动态菜单
 * @author Administrator
 *
 */
public class DownloaderPopupMenu extends JPopupMenu{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 中央控制器
	 */
	CentralController centralController;
	/**
	 * 任务控制器
	 */
	TaskController taskController;
	
	JPopupMenu popmenu4Table;
	JPopupMenu popmenu4Garbage;
	
	JMenuItem itemNewTask;
	JMenuItem itemPauseTask;
	JMenuItem itemContinueTask;
	JMenuItem itemReDownload; 
	JMenuItem itemDeleteTask;
	JMenuItem itemOpenTask;
	JMenuItem itemOpenFolder;
	JMenuItem itemAddThread;
	JMenuItem itemSubThread;
	
	JMenuItem itemDeleteTask4G;
	JMenuItem itemResumeFromGarbage;
	JMenuItem itemOpenFolder4G;
	JMenuItem itemClearGarbageBin;
	
	/**
	 * 构造器
	 */
	public DownloaderPopupMenu(){
		init();
	}
	
	/**
	 * 初始化popupMenu
	 */
	public void init(){
		itemNewTask = new JMenuItem("新建任务");

		itemPauseTask = new JMenuItem("暂停任务");

		itemContinueTask = new JMenuItem("开始任务");
		
		itemReDownload = new JMenuItem("重新下载");
		
		itemOpenTask = new JMenuItem("打开任务");
		
		itemOpenFolder = new JMenuItem("打开文件夹");

		itemDeleteTask = new JMenuItem("删除任务");

		itemAddThread = new JMenuItem("线程+");

		itemSubThread = new JMenuItem("线程-");

		popmenu4Table = new JPopupMenu();
		
		popmenu4Table.add(itemNewTask);
		popmenu4Table.add(itemPauseTask);
		popmenu4Table.add(itemContinueTask);
		popmenu4Table.add(itemReDownload);
		popmenu4Table.add(itemDeleteTask);
		popmenu4Table.add(itemOpenTask);
		popmenu4Table.add(itemOpenFolder);
		popmenu4Table.add(itemAddThread);
		popmenu4Table.add(itemSubThread);
		
		itemResumeFromGarbage = new JMenuItem("还原任务");
		itemDeleteTask4G = new JMenuItem("删除任务");
		itemOpenFolder4G = new JMenuItem("打开文件夹");
		itemClearGarbageBin = new JMenuItem("清空垃圾箱");
		
		popmenu4Garbage = new JPopupMenu();
		
		popmenu4Garbage.add(itemResumeFromGarbage);
		popmenu4Garbage.add(itemDeleteTask4G);
		popmenu4Garbage.add(itemOpenFolder4G);
		popmenu4Garbage.add(itemClearGarbageBin);
		
		/*添加监听*/
		this.addListeners();
	}
	
	/**
	 * 为个菜单项添加监听
	 */
	public void addListeners(){
		//新建任务
		this.itemNewTask.addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent arg0) {
						centralController.newTask();
					}
				}
		);
		//开始任务
		this.itemContinueTask.addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent arg0) {
						if(taskController.getTask().getStatus() == Task.STATE_PAUSED){ //任务正暂停
							centralController.restartTask(taskController);
						}else{// 否则
							centralController.startTask(taskController);
						}
					}
				}
		);
		
		//重新下载
		this.itemReDownload.addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent arg0) {
						/*只有失败的任务允许重新下载*/
						if(taskController.getTask().getStatus() == Task.STATE_FAILED){ 
							centralController.reDownload(taskController);
						}
					}
				}
		);
		
		//暂停任务
		this.itemPauseTask.addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent arg0) {
						//taskController.pauseTask();
						centralController.pauseTask(taskController);
					}
				}
		);
		
		
		//删除任务
		this.itemDeleteTask.addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent arg0) {
						centralController.deleteTask(taskController);
					}
				}
		);
		
		//删除任务4G
		this.itemDeleteTask4G.addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent arg0) {
						centralController.deleteTask(taskController);
					}
				}
		);
		
		//打开文件
		this.itemOpenTask.addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent arg0) {
						if(taskController != null){
							try {
								centralController.openFile(taskController);
							} catch (IOException e) {
								//这里怎么捕获异常？
								System.out.println("打开文件异常！");
							}
						}
					}
				}
		);
		
		//打开文件夹
		this.itemOpenFolder.addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent arg0) {
						if(taskController != null){
							try {
								centralController.openFolder(taskController);
							} catch (IOException e) {
								//这里怎么捕获异常？
								System.out.println("打开文件异常！");
							}
						}
					}
				}
		);
		
		//打开文件夹4G
		this.itemOpenFolder4G.addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent arg0) {
						if(taskController != null){
							try {
								centralController.openFolder(taskController);
							} catch (IOException e) {
								//这里怎么捕获异常？
								System.out.println("打开文件异常！");
							}
						}
					}
				}
		);
		
		//线程+
		this.itemAddThread.addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent arg0) {
						centralController.addThread(taskController);
					}
				}
		);
		
		//线程-
		this.itemSubThread.addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent arg0) {
						centralController.subThread(taskController);
					}
				}
		);
		
		//还原任务
		this.itemResumeFromGarbage.addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent arg0) {
						centralController.resumeTaskFromGarbage(taskController);
					}
				}
		);
		
		//删除任务
		this.itemClearGarbageBin.addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent arg0) {
						centralController.clearGarbageBin();
					}
				}
		);
	}
	
	
	/**
	 * 覆写父类方法，用于对不同popupMenu的调用
	 */
	@Override
	public void show(Component component, int x, int y) {
			this.setItemStatus();
			
			if(this.centralController.getViewStatus() != MainController.GARBAGEBIN_VIEW){
				this.popmenu4Table.show(component, x, y);
			}else{
				this.popmenu4Garbage.show(component, x, y);
			}
	}

	/**
	 * 设置各菜单的状态
	 */
	public void setItemStatus(){
		if(taskController == null){
			this.itemNewTask.setEnabled(true);
			this.itemPauseTask.setEnabled(false);
			this.itemContinueTask.setEnabled(false);
			this.itemReDownload.setEnabled(false);
			this.itemOpenTask.setEnabled(false);
			this.itemOpenFolder.setEnabled(false);
			this.itemOpenFolder4G.setEnabled(false);
			this.itemDeleteTask.setEnabled(false);
			this.itemDeleteTask4G.setEnabled(false);
			this.itemAddThread.setEnabled(false);
			this.itemSubThread.setEnabled(false);
			this.itemResumeFromGarbage.setEnabled(false);
		}else{
			Task task = taskController.getTask();
			if(task.getStatus() == Task.STATE_NEW || task.getStatus() == Task.STATE_RUNNING){
				this.itemNewTask.setEnabled(true);
				this.itemPauseTask.setEnabled(true);
				this.itemContinueTask.setEnabled(false);
				this.itemReDownload.setEnabled(false);
				this.itemOpenTask.setEnabled(false);
				this.itemOpenFolder.setEnabled(true);
				this.itemOpenFolder4G.setEnabled(true);
				this.itemDeleteTask.setEnabled(true);
				this.itemDeleteTask4G.setEnabled(true);
				this.itemAddThread.setEnabled(true);
				this.itemSubThread.setEnabled(true);
				this.itemResumeFromGarbage.setEnabled(true);
				return;
			}
			if(task.getStatus() == Task.STATE_PAUSED){
				this.itemNewTask.setEnabled(true);
				this.itemPauseTask.setEnabled(false);
				this.itemContinueTask.setEnabled(true);
				this.itemReDownload.setEnabled(false);
				this.itemOpenTask.setEnabled(false);
				this.itemOpenFolder.setEnabled(true);
				this.itemOpenFolder4G.setEnabled(true);
				this.itemDeleteTask.setEnabled(true);
				this.itemDeleteTask4G.setEnabled(true);
				this.itemAddThread.setEnabled(false);
				this.itemSubThread.setEnabled(false);
				this.itemResumeFromGarbage.setEnabled(true);
				return;
			}
			if(task.getStatus() == Task.STATE_COMPLETED){
				this.itemNewTask.setEnabled(true);
				this.itemPauseTask.setEnabled(false);
				this.itemContinueTask.setEnabled(false);
				this.itemReDownload.setEnabled(false);
				this.itemOpenTask.setEnabled(true);
				this.itemOpenFolder.setEnabled(true);
				this.itemOpenFolder4G.setEnabled(true);
				this.itemDeleteTask.setEnabled(true);
				this.itemDeleteTask4G.setEnabled(true);
				this.itemAddThread.setEnabled(false);
				this.itemSubThread.setEnabled(false);
				this.itemResumeFromGarbage.setEnabled(true);
				return;
			}
			if(task.getStatus() == Task.STATE_FAILED){
				this.itemNewTask.setEnabled(true);
				this.itemPauseTask.setEnabled(false);
				this.itemContinueTask.setEnabled(false);
				this.itemReDownload.setEnabled(true);
				this.itemOpenTask.setEnabled(false);
				this.itemOpenFolder.setEnabled(true);
				this.itemOpenFolder4G.setEnabled(true);
				this.itemDeleteTask.setEnabled(false);
				this.itemDeleteTask4G.setEnabled(false);
				this.itemAddThread.setEnabled(false);
				this.itemSubThread.setEnabled(false);
				this.itemResumeFromGarbage.setEnabled(false);
			}
			
		}
		
	}
	
	/**
	 * 添加任务控制器，popupMenu在一个时刻只能对应一个控制器,所以设置了控制器后，原控制器会被覆盖
	 * @param taskController
	 */
	public void setTaskController(TaskController taskController){
		this.taskController = taskController;
		/*show*/
		//this.show();
	}
	
	/**
	 * 添加中央控制器
	 * @param centralController
	 */
	public void addCentralContoller(CentralController centralController){
		if(this.centralController == null){
			this.centralController = centralController;
		}
	}
}
