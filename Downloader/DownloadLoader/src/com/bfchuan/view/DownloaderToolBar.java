package com.bfchuan.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import com.bfchuan.controller.CentralController;
import com.bfchuan.controller.TaskController;
import com.bfchuan.entities.Task;


/**
 * 下载器的工具栏，包括新建任务、开始、暂停任务、删除任务、打开上次任务等按钮
 * @author Administrator
 *
 */
public class DownloaderToolBar extends JToolBar{

	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 中央控制器
	 */
	CentralController centralController;
	/**
	 * button正指示的任务
	 */
	TaskController taskController = null;
	private JButton btnNewTask;
	private JButton btnBeginTask;
	private JButton btnPauseTask;
	private JButton btnDeleteTask;
	private JButton btnOpenPreviousTask;
	private JButton btnSubThread;
	private JButton btnAddThread;
	
	/**
	 * 下载器toolBar的构造方法
	 */
	public DownloaderToolBar(){
		init();
	}
	
	 /**
	  * 初始化
	  */
	public void init(){
		this.buildTaskBtn();
		this.buildThreadBtn();
		this.addListeners();
		
		/*初始化状态*/
		this.showBtn();
	}
	
	/**
	 * 生成关于任务的按钮
	 */
	public void buildTaskBtn(){
		
		//设置toolBar的属性
		this.setLayout(new BorderLayout());
		this.setRollover(true);						// 鼠标放在上面是，button突起，出现边框
		this.setBackground(new Color(203, 223, 245));

		//用于放置任务button的penel，这样便于布局
		JPanel panel4TaskBtn = new JPanel();
		panel4TaskBtn.setLayout(new GridLayout(1, 5, 10, 10));
		panel4TaskBtn.setBackground(new Color(189, 209, 232));

		// 新建任务
		ImageIcon iconNewTask = new ImageIcon("images/imgNewTask.jpg");
		btnNewTask = new JButton(iconNewTask);
		btnNewTask.setBorder(BorderFactory.createLineBorder(new Color(90, 160,232)));
		btnNewTask.setToolTipText("点击此按钮开始新建任务");
		
		//开始任务
		ImageIcon iconContinueTask = new ImageIcon("images/imgContinueTask.jpg");
		btnBeginTask = new JButton(iconContinueTask);
		btnBeginTask.setBorder(BorderFactory.createLineBorder(new Color(90, 160,232)));
		//btnBeginTask.setEnabled(false);
		btnBeginTask.setToolTipText("点击此按钮续传已暂停了的任务");
		
		//暂停任务
		ImageIcon iconPauseTask = new ImageIcon("images/imgPauseTask.jpg");
		btnPauseTask = new JButton(iconPauseTask);
		btnPauseTask.setBorder(BorderFactory.createLineBorder(new Color(90, 160,232)));
		//btnPauseTask.setEnabled(false);
		btnPauseTask.setToolTipText("点击此按钮暂停正在下载的任务");
	
		//删除任务
		ImageIcon iconDeleteTask = new ImageIcon("images/imgDeleteTask.JPG");
		btnDeleteTask = new JButton(iconDeleteTask);
		btnDeleteTask.setBorder(BorderFactory.createLineBorder(new Color(90, 160,232)));
		btnDeleteTask.setActionCommand("删除任务");// 设置按钮的动作命令
		//btnDeleteTask.setEnabled(false);
		btnDeleteTask.setToolTipText("点击此按钮删除任务信息或下载文件");

		//以前任务
		ImageIcon iconPreviousTask = new ImageIcon("images/imgPreviousTask.jpg");
		btnOpenPreviousTask = new JButton(iconPreviousTask);
		btnOpenPreviousTask.setBorder(BorderFactory.createLineBorder(new Color(90, 160,232)));
		//btnOpenPreviousTask.setEnabled(true);
		btnOpenPreviousTask.setToolTipText("点击此按钮打开文件");
		
		//将各个任务button放置到panel上
		panel4TaskBtn.add(btnNewTask);
		panel4TaskBtn.add(btnBeginTask);
		panel4TaskBtn.add(btnPauseTask);
		panel4TaskBtn.add(btnDeleteTask);
		panel4TaskBtn.add(btnOpenPreviousTask);
		
		//将panel放置到toolBar上
		this.add(panel4TaskBtn, "West");
	}
	
	/**
	 * 生成线程+，-的按钮
	 */
	private void buildThreadBtn(){
		JPanel panel4ThreadBtn = new JPanel();
		panel4ThreadBtn.setLayout(new GridLayout(1, 2, 10, 5));
		panel4ThreadBtn.setBackground(new Color(189, 209, 232));

		btnAddThread = new JButton(" 线程+ ");
		btnAddThread.setBorder(BorderFactory.createEtchedBorder());
		btnAddThread.setActionCommand("线程+");// 设置按钮的动作命令
		btnAddThread.setToolTipText("点击此按钮使当前线程数+1");
		//btnAddThread.setEnabled(false);

		btnSubThread = new JButton(" 线程- ");
		btnSubThread.setBorder(BorderFactory.createEtchedBorder());
		btnSubThread.setActionCommand("线程-");// 设置按钮的动作命令
		btnSubThread.setToolTipText("点击此按钮使当前线程数-1");
		//btnSubThread.setEnabled(false);

		panel4ThreadBtn.add(btnAddThread);
		panel4ThreadBtn.add(btnSubThread);
		this.add(panel4ThreadBtn, "East");
	}
	
	/**
	 * 为toolBar中各个按钮添加监听
	 */
	public void addListeners(){
		//新建任务
		this.getBtnNewTask().addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent arg0) {
						centralController.newTask();
					}
				}
		);
		//开始任务
		this.getBtnBeginTask().addActionListener(
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
		
		//暂停任务
		this.getBtnPauseTask().addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent arg0) {
						//taskController.pauseTask();
						centralController.pauseTask(taskController);
					}
				}
		);
		
		
		//删除任务
		this.getBtnDeleteTask().addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent arg0) {
						centralController.deleteTask(taskController);
					}
				}
		);
		
		//打开文件
		this.getBtnOpenPreviousTask().addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent arg0) {
						if(taskController != null){
							try {
								centralController.openFile(taskController);
							} catch (IOException e) {
								//这里怎么捕获异常？
							}
						}
					}
				}
		);
		
		//线程+
		this.getBtnAddThread().addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent arg0) {
						centralController.addThread(taskController);
					}
				}
		);
		
		//线程-
		this.getBtnSubThread().addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent arg0) {
						centralController.subThread(taskController);
					}
				}
		);
		
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
	
	/**
	 * 添加任务控制器，toolBar在一个时刻只能对应一个控制器,所以设置了控制器后，原控制器会被覆盖
	 * @param taskController
	 */
	public void setTaskController(TaskController taskController){
		this.taskController = taskController;
		this.showBtn();
	}
	
	/**
	 * 根据当前的控制器，相应的显示button的状态
	 */
	public void showBtn(){
		if(taskController == null){ //当控制器为空时
			this.getBtnBeginTask().setEnabled(false);
			this.getBtnPauseTask().setEnabled(false);
			this.getBtnDeleteTask().setEnabled(false);
			this.getBtnAddThread().setEnabled(false);
			this.getBtnSubThread().setEnabled(false);
			this.getBtnOpenPreviousTask().setEnabled(false);
			return;
		}else{
			switch (taskController.getTask().getStatus()){
				case Task.STATE_NEW :     
				case Task.STATE_RUNNING :  //新建和下载中
					this.getBtnBeginTask().setEnabled(false);
					this.getBtnPauseTask().setEnabled(true);
					this.getBtnDeleteTask().setEnabled(true);
					this.getBtnAddThread().setEnabled(true);
					this.getBtnSubThread().setEnabled(true);
					this.getBtnOpenPreviousTask().setEnabled(false);
					break;
				case Task.STATE_PAUSED :   //任务暂停
					this.getBtnBeginTask().setEnabled(true);
					this.getBtnPauseTask().setEnabled(false);
					this.getBtnDeleteTask().setEnabled(true);
					this.getBtnAddThread().setEnabled(false);
					this.getBtnSubThread().setEnabled(false);
					this.getBtnOpenPreviousTask().setEnabled(false);
					break;
				case Task.STATE_FAILED :	//任务失败
					this.getBtnBeginTask().setEnabled(false);
					this.getBtnPauseTask().setEnabled(false);
					this.getBtnDeleteTask().setEnabled(true);
					this.getBtnAddThread().setEnabled(false);
					this.getBtnSubThread().setEnabled(false);
					this.getBtnOpenPreviousTask().setEnabled(false);
					break;
				case Task.STATE_COMPLETED :  //任务完成
					this.getBtnBeginTask().setEnabled(false);
					this.getBtnPauseTask().setEnabled(false);
					this.getBtnDeleteTask().setEnabled(true);
					this.getBtnAddThread().setEnabled(false);
					this.getBtnSubThread().setEnabled(false);
					this.getBtnOpenPreviousTask().setEnabled(true);
					break;
				default: 					 //都不是
					this.getBtnBeginTask().setEnabled(false);
					this.getBtnPauseTask().setEnabled(false);
					this.getBtnDeleteTask().setEnabled(false);
					this.getBtnAddThread().setEnabled(false);
					this.getBtnSubThread().setEnabled(false);
					this.getBtnOpenPreviousTask().setEnabled(false);
					
			}
			
			/*特殊处理，当任务控制器处于垃圾状态时，不能开始任务，得先还原*/
			if(taskController.getTaskControllerStatus() == TaskController.TASKCONTROLLER_GARBAGE){
				this.getBtnBeginTask().setEnabled(false);
			}
		}
		
	}
	
	//-------------  相应按钮的get方法 ---------------
	

	public JButton getBtnNewTask() {
		return btnNewTask;
	}
	public JButton getBtnBeginTask() {
		return btnBeginTask;
	}
	public JButton getBtnPauseTask() {
		return btnPauseTask;
	}
	public JButton getBtnDeleteTask() {
		return btnDeleteTask;
	}
	public JButton getBtnOpenPreviousTask() {
		return btnOpenPreviousTask;
	}
	public JButton getBtnAddThread() {
		return btnAddThread;
	}
	public JButton getBtnSubThread() {
		return btnSubThread;
	}
}
