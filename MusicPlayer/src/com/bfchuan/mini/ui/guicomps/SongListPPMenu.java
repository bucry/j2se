package com.bfchuan.mini.ui.guicomps;

import java.awt.Color;
import java.awt.event.ActionListener;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

/**
 * 弹出菜单类
 * 作者:Loenidas
 * 时间:2012-5-10
 * piaobomengxiang@163.com
 * 版本:v1.0
 *
 */
public class SongListPPMenu {

	private JMenuItem playItem = new JMenuItem("播放");
	private JMenuItem deleteItem = new JMenuItem("删除");
	private JMenuItem deleteAllItem = new JMenuItem("清空播放列表");
	private JMenuItem addSongItem = new JMenuItem("添加本地音乐文件");
	private JMenuItem addSongFolderItem = new JMenuItem("添加本地音乐文件夹");
	private JPopupMenu ppIsSelect = new JPopupMenu();
	private JPopupMenu ppNoSelect = new JPopupMenu();
	private static SongListPPMenu pmenu;

	private SongListPPMenu() {
	}

	public static SongListPPMenu getInstance() {
		if (pmenu == null) {
			pmenu = new SongListPPMenu();
		}
		return pmenu;
	}

	/**
	 * 初始化选中JList是弹出的菜单
	 */
	public void initIsSelectPopupMenu() {
		ppIsSelect.removeAll();
		ppIsSelect.add(playItem);
		ppIsSelect.addSeparator();
		ppIsSelect.add(deleteItem);
		ppIsSelect.addSeparator();
		ppIsSelect.add(deleteAllItem);
		ppIsSelect.addSeparator();
		ppIsSelect.add(addSongItem);
		ppIsSelect.addSeparator();
		ppIsSelect.add(addSongFolderItem);
		ppIsSelect.pack();
	}

	/**
	 * 初始化未选中JList是弹出的菜单
	 */
	public void initNoSelectPopupMenu() {
		ppNoSelect.removeAll();
		ppNoSelect.add(addSongItem);
		ppNoSelect.addSeparator();
		ppNoSelect.add(addSongFolderItem);
		ppNoSelect.addSeparator();
		ppNoSelect.add(deleteAllItem);
		ppIsSelect.pack();
	}

	/**
	 * 设置弹出菜单的背景颜色
	 * 
	 * @param newColor
	 */
	public void setBackgroundColor(Color newColor) {
		playItem.setBackground(newColor);
		deleteItem.setBackground(newColor);
		deleteAllItem.setBackground(newColor);
		addSongItem.setBackground(newColor);
		addSongFolderItem.setBackground(newColor);
	}

	/**
	 * 增加弹出菜单的监听
	 * 
	 * @param listener
	 */
	public void addActionListener(ActionListener listener) {
		playItem.addActionListener(listener);
		deleteItem.addActionListener(listener);
		deleteAllItem.addActionListener(listener);
		addSongItem.addActionListener(listener);
		addSongFolderItem.addActionListener(listener);
	}

	/**
	 * 返回弹出菜单
	 * @param select
	 * @return
	 */
	public JPopupMenu getMyPopupMenu(boolean select) {
		if (select) {
			initIsSelectPopupMenu();
			return ppIsSelect;
		}
		initNoSelectPopupMenu();
		return ppNoSelect;
	}

}
