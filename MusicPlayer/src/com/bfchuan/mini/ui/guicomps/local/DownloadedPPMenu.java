package com.bfchuan.mini.ui.guicomps.local;

import java.awt.Color;
import java.awt.event.ActionListener;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

/**
 * 已下载歌曲和本地曲库上的弹出菜单类
 * 作者:Loenidas
 * 时间:2012-5-10
 * piaobomengxiang@163.com
 * 版本:v1.0
 *
 */
public class DownloadedPPMenu {

	private static DownloadedPPMenu pmenu;
	private JMenuItem listenSong = new JMenuItem("播放");
	private JMenuItem addSong = new JMenuItem("添加");
	private JMenuItem openFolder = new JMenuItem("打开文件夹");
	private JMenuItem delSong = new JMenuItem("删除本地文件");
	private JPopupMenu taskPP = new JPopupMenu();

	private DownloadedPPMenu() {
		initPopupMenu();
	}

	public static DownloadedPPMenu getInstance() {
		if (pmenu == null) {
			pmenu = new DownloadedPPMenu();
		}
		return pmenu;
	}

	/**
	 * 初始化弹出菜单
	 */
	public void initPopupMenu() {
		taskPP.add(listenSong);
		taskPP.addSeparator();
		taskPP.add(addSong);
		taskPP.addSeparator();
		taskPP.add(openFolder);
		taskPP.addSeparator();
		taskPP.add(delSong);
		taskPP.pack();
	}
	
	/**
	 * 设置弹出菜单的背景颜色
	 * 
	 * @param newColor
	 */
	public void setBackgroundColor(Color newColor) {
		openFolder.setBackground(newColor);
		listenSong.setBackground(newColor);
		addSong.setBackground(newColor);
		delSong.setBackground(newColor);
	}

	/**
	 * 增加弹出菜单的监听
	 * 
	 * @param listener
	 */
	public void addActionListener(ActionListener listener) {
		openFolder.addActionListener(listener);
		listenSong.addActionListener(listener);
		addSong.addActionListener(listener);
		delSong.addActionListener(listener);
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
