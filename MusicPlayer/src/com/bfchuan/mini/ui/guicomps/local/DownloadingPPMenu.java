package com.bfchuan.mini.ui.guicomps.local;

import java.awt.Color;
import java.awt.event.ActionListener;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

/**
 * 下载任务Table上的弹出菜单类
 * 作者:Loenidas
 * 时间:2012-5-10
 * piaobomengxiang@163.com
 * 版本:v1.0
 *
 */
public class DownloadingPPMenu {

	private static DownloadingPPMenu pmenu;
	private JMenuItem openFolder = new JMenuItem("打开文件夹");
	private JMenuItem startDown = new JMenuItem("开始下载");
	private JMenuItem pauseDown = new JMenuItem("暂停下载");
	private JMenuItem reDown = new JMenuItem("重新下载");
	private JMenuItem delTask = new JMenuItem("删除任务");
	private JPopupMenu taskPP = new JPopupMenu();

	private DownloadingPPMenu() {
		initPopupMenu();
	}

	public static DownloadingPPMenu getInstance() {
		if (pmenu == null) {
			pmenu = new DownloadingPPMenu();
		}
		return pmenu;
	}

	/**
	 * 初始化弹出菜单
	 */
	public void initPopupMenu() {
		taskPP.add(openFolder);
		taskPP.addSeparator();
		taskPP.add(startDown);
		taskPP.addSeparator();
		taskPP.add(pauseDown);
		taskPP.addSeparator();
		taskPP.add(reDown);
		taskPP.addSeparator();
		taskPP.add(delTask);
		taskPP.pack();
	}
	
	/**
	 * 设置弹出菜单的背景颜色
	 * 
	 * @param newColor
	 */
	public void setBackgroundColor(Color newColor) {
		openFolder.setBackground(newColor);
		startDown.setBackground(newColor);
		pauseDown.setBackground(newColor);
		reDown.setBackground(newColor);
		delTask.setBackground(newColor);
	}

	/**
	 * 增加弹出菜单的监听
	 * 
	 * @param listener
	 */
	public void addActionListener(ActionListener listener) {
		openFolder.addActionListener(listener);
		startDown.addActionListener(listener);
		pauseDown.addActionListener(listener);
		reDown.addActionListener(listener);
		delTask.addActionListener(listener);
	}

	/**
	 * 返回弹出菜单
	 * @param select
	 * @return
	 */
	public JPopupMenu getPopupMenu() {
		return this.taskPP;
	}

}
