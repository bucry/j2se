package com.bfchuan.mini.ui.guicomps.local;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


import com.bfchuan.mini.bo.FileBo;
import com.bfchuan.mini.bo.MusicBo;
import com.bfchuan.mini.bo.SongBo;
import com.bfchuan.mini.entity.Song;
import com.bfchuan.mini.util.ID3Info;

/**
 * 本地曲库操作监听
 * 作者:Loenidas
 * 时间:2012-5-10
 * piaobomengxiang@163.com
 * 版本:v1.0
 *
 */
public class LocalListener extends MouseAdapter implements ActionListener {

	private JTable ltable = LocalTable.getInstance().getTable();
	private int[] index = new int[1];

	public LocalListener() {
		ltable.addMouseListener(this);
		LocalPPMenu.getInstance().addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (index.length == 0) {
			return;
		}
		DefaultTableModel dtm = LocalTable.getInstance().getTableModel();
		String action = e.getActionCommand();
		if ("打开文件夹".equals(action)) {
			int col = dtm.findColumn("本地路径");
			String path = (String) dtm.getValueAt(index[0], col);
			File file = new File(path).getParentFile();
			FileBo.getInstance().openFolder(file.toString());
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
				LocalPPMenu.getInstance().getPopupMenu().show(ltable, e.getX(), e.getY());
			}
		} else if (e.getClickCount() == 2) {// 开始播放歌曲
			DefaultTableModel dtm = LocalTable.getInstance().getTableModel();
			index = ltable.getSelectedRows();
			int col = dtm.findColumn("本地路径");
			String path = (String) dtm.getValueAt(index[0], col);
			Song song = ID3Info.getInstance().parseSong(path);
			SongBo.getInstance().addSong(song);
			MusicBo.getInstance().play(song);
		} else if (e.getClickCount() == 1) {
			index = ltable.getSelectedRows();
		}
	}

}
