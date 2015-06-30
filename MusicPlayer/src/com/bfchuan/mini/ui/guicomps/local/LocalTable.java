package com.bfchuan.mini.ui.guicomps.local;

import java.awt.Cursor;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;


import com.bfchuan.mini.bo.ConfigBo;
import com.bfchuan.mini.bo.SongBo;
import com.bfchuan.mini.ui.myguis.MyTableModel;

/**
 * 作者:Loenidas
 * 时间:2012-5-10
 * piaobomengxiang@163.com
 * 版本:v1.0
 *
 */
public class LocalTable {

	private static LocalTable mlt = new LocalTable();
	private Object[] attributes = { "播放", "歌名", "歌手", "专辑", "大小", "本地路径" };
	private DefaultTableModel myModel = new MyTableModel();
	private JTable musicTable = new JTable(myModel);

	private LocalTable() {
		musicTable.getTableHeader().setReorderingAllowed(false);
		musicTable.setRowHeight(20);
		myModel.setColumnIdentifiers(attributes);
		musicTable.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		initialize();
		initLocalSong();
	}

	private void initialize() {
		// 设置各个列宽度
		TableColumn tc = musicTable.getColumn(attributes[0]);
		tc.setPreferredWidth(50);
		tc = musicTable.getColumn(attributes[1]);
		tc.setPreferredWidth(150);
		tc = musicTable.getColumn(attributes[2]);
		tc.setPreferredWidth(100);
		tc = musicTable.getColumn(attributes[3]);
		tc.setPreferredWidth(100);
		tc = musicTable.getColumn(attributes[4]);
		tc.setPreferredWidth(60);
		tc = musicTable.getColumn(attributes[5]);
		tc.setPreferredWidth(300);
	}

	/**
	 * 初始化所有本地曲库
	 */
	public void initLocalSong() {
		// 删除DefaultTableModel中所有歌曲
    	int length = myModel.getRowCount();
    	if (length != 0) {
    		for (int i = length - 1; i >= 0; i--) {
    			myModel.removeRow(i);
    		}
    	}
		//先加载下载目录下的所有音乐文件
		SongBo.getInstance().loadAllSongAndUpdateTableModel(ConfigBo.getInstance().getNetMusicDownloadFolder(), this.getTableModel(), 2);
		// 加载别的文件夹中的音乐文件
		Vector<String> folderList = SongBo.getInstance().getLocalMusicFolderList();
		for (int i = 0; i < folderList.size(); i++) {
			SongBo.getInstance().loadAllSongAndUpdateTableModel(folderList.get(i), this.getTableModel(), 2);
		}
	}

	public static LocalTable getInstance() {
		return mlt;
	}

	public JTable getTable() {
		return musicTable;
	}

	public DefaultTableModel getTableModel() {
		return myModel;
	}
}
