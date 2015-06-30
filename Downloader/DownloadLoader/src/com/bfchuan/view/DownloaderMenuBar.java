package com.bfchuan.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import com.bfchuan.controller.CentralController;


/**
 *  下载器的menuBar类
 * @author Administrator
 *
 */
public class DownloaderMenuBar extends JMenuBar{
	
	private static final long serialVersionUID = 1L; 
	/**
	 * 中央控制器
	 */
	CentralController centralController;
	private JMenu menuTask;
	private JMenu menuHelp;
	private JMenuItem menuItemExit;
	private JMenuItem menuItemAbout;
	private JMenuItem menuItemIntoduce;
	private JMenuItem menuItemNewTask;
	/**
	 * 下载器menuBar的构造方法
	 */
	public DownloaderMenuBar(){
		init();
	}
	
	/**
	 * 初始化
	 */
	private void init(){

		/* 第一个菜单项 */
		menuTask = new JMenu("任 务");
		menuItemExit = new JMenuItem("退出程序");
		menuItemNewTask = new JMenuItem("新建任务");
		menuTask.add(menuItemNewTask);
		menuTask.add(menuItemExit);
		this.add(menuTask);
		
		/* 第二个菜单项 */
		menuHelp = new JMenu("帮 助");
		menuItemAbout =  new JMenuItem("关于");
		menuItemIntoduce = new JMenuItem("功能介绍");
		menuHelp.add(menuItemAbout);
		menuHelp.add(menuItemIntoduce);
		this.add(menuHelp);
		
		/*为元素添加监听*/
		this.addListeners();
		
		//设置边框样式
		this.setBorder(BorderFactory.createEtchedBorder(0));
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
	 * 为menuBar中的各个元素添加相应监听
	 */
	private void addListeners(){
		
		/*新建任务的菜单项*/
		this.getMenuItemNewTask().addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent arg0) {
						centralController.newTask();
					}	
				}
		);	
		
		/*退出程序的菜单项*/
		this.getMenuItemExit().addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent arg0) {
						centralController.exit();
					}	
				}
		);	
		
		/*关于菜单项*/
		this.getMenuItemAbout().addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent arg0) {
						centralController.showAboatMsg();
					}	
				}
		);	
		
		/*功能介绍菜单项*/
		this.getMenuItemIntoduce().addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent arg0) {
						centralController.showIntroduce();
					}	
				}
		);
	}


	//-----------以下是相应的get方法-------------------
	
	public JMenuItem getMenuItemExit() {
		return menuItemExit;
	}

	public JMenuItem getMenuItemAbout() {
		return menuItemAbout;
	}


	public JMenuItem getMenuItemIntoduce() {
		return menuItemIntoduce;
	}

	public JMenuItem getMenuItemNewTask() {
		return menuItemNewTask;
	}

}
