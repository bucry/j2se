package com.bfchuan.mini.ui.guicomps.local;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTable;

import com.bfchuan.mini.bo.ConfigBo;
import com.bfchuan.mini.bo.DownloadBo;
import com.bfchuan.mini.bo.FileBo;

/**
 * 正在下载的监听
 * 作者:Loenidas
 * 时间:2012-5-10
 * piaobomengxiang@163.com
 * 版本:v1.0
 *
 */
public class DownloadingListener extends MouseAdapter implements ActionListener {
	
	private JTable table = DownloadingTable.getInstance().getTable();
	private int[] index = new int[1];
	
	public DownloadingListener() {
		table.addMouseListener(this);
		DownloadingPPMenu.getInstance().addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (index.length == 0) {
			return;
		}
        String action = e.getActionCommand();
        if ("打开文件夹".equals(action)) {
        	FileBo.getInstance().openFolder(ConfigBo.getInstance().getNetMusicDownloadFolder());
        } else if ("开始下载".equals(action)) {
        	DownloadBo.getInstance().startTask(index);
        } else if ("暂停下载".equals(action)) {
        	DownloadBo.getInstance().stopTask(index);
        } else if ("重新下载".equals(action)) {
        	DownloadBo.getInstance().reDownloadTask(index);
        } else if ("删除任务".equals(action)) {
        	DownloadBo.getInstance().removeTask(index);
        }
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getButton() == 3) {
			if (index.length != 0) {
				DownloadingPPMenu.getInstance().getPopupMenu().show(table, e.getX(), e.getY());
			}
		} else if (e.getClickCount() == 2) {//开始任务或者停止任务
			index = table.getSelectedRows();
			DownloadBo.getInstance().twoClickCount(index);//双击事件的处理
		} else if (e.getClickCount() == 1) {//开始任务或者停止任务
			index = table.getSelectedRows();
		}
	}

}
