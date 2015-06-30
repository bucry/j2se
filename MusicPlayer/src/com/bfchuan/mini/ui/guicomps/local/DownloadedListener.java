package com.bfchuan.mini.ui.guicomps.local;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


import com.bfchuan.mini.bo.ConfigBo;
import com.bfchuan.mini.bo.FileBo;
import com.bfchuan.mini.bo.MusicBo;
import com.bfchuan.mini.bo.SongBo;
import com.bfchuan.mini.entity.Song;
import com.bfchuan.mini.util.ID3Info;

/**
 * 下载监听器
 * 作者:Loenidas
 * 时间:2012-5-10
 * piaobomengxiang@163.com
 * 版本:v1.0
 *
 */
public class DownloadedListener extends MouseAdapter implements ActionListener {

	private JTable dtable = DownloadedTable.getInstance().getTable();
	private int[] index = new int[1];

	public DownloadedListener() {
		dtable.addMouseListener(this);
		DownloadedPPMenu.getInstance().addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (index.length == 0) {
			return;
		}
		DefaultTableModel dtm = DownloadedTable.getInstance().getTableModel();
		String action = e.getActionCommand();
		if ("打开文件夹".equals(action)) {
			FileBo.getInstance().openFolder(
					ConfigBo.getInstance().getNetMusicDownloadFolder());
		} else if ("播放".equals(action)) {
			int col = dtm.findColumn("本地路径");
			String path = (String) dtm.getValueAt(index[0], col);
			Song song = ID3Info.getInstance().parseSong(path);
			SongBo.getInstance().addSong(song);
			MusicBo.getInstance().play(song);
		} else if ("添加".equals(action)) {
			SongBo.getInstance().addSong(index, dtm);
		} else if ("删除本地文件".equals(action)) {
			FileBo.getInstance().deleteMusicFileAndUpdateTableModel(index, dtm);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getButton() == 3) {
			if (index.length != 0) {
				DownloadedPPMenu.getInstance().getPopupMenu().show(dtable, e.getX(), e.getY());
			}
		} else if (e.getClickCount() == 2) {// 开始播放音乐
			DefaultTableModel dtm = DownloadedTable.getInstance().getTableModel();
			index = dtable.getSelectedRows();
			int col = dtm.findColumn("本地路径");
			String path = (String) dtm.getValueAt(index[0], col);
			Song song = ID3Info.getInstance().parseSong(path);
			SongBo.getInstance().addSong(song);
			MusicBo.getInstance().play(song);
		} else if (e.getClickCount() == 1) {
			index = dtable.getSelectedRows();
		}
	}

}
