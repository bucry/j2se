package com.bfchuan.mini.ui.guicomps;

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
public class LrcLabelPPMenu {

	private static LrcLabelPPMenu pmenu;
	private JMenuItem searchLrc = new JMenuItem("手动搜索歌词");
	private JPopupMenu lrcPP = new JPopupMenu();

	private LrcLabelPPMenu() {
		initPopupMenu();
	}

	public static LrcLabelPPMenu getInstance() {
		if (pmenu == null) {
			pmenu = new LrcLabelPPMenu();
		}
		return pmenu;
	}

	/**
	 * 初始化弹出菜单
	 */
	public void initPopupMenu() {
		lrcPP.add(searchLrc);
		lrcPP.pack();
	}
	
	/**
	 * 设置弹出菜单的背景颜色
	 * 
	 * @param newColor
	 */
	public void setBackgroundColor(Color newColor) {
		searchLrc.setBackground(newColor);
	}

	/**
	 * 增加弹出菜单的监听
	 * 
	 * @param listener
	 */
	public void addActionListener(ActionListener listener) {
		searchLrc.addActionListener(listener);
	}

	/**
	 * 返回弹出菜单
	 * @param select
	 * @return
	 */
	public JPopupMenu getPopupMenu() {
		return this.lrcPP;
	}

}
