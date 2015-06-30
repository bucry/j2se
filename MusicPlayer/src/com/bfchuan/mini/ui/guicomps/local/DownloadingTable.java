package com.bfchuan.mini.ui.guicomps.local;

import java.awt.Cursor;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;


import com.bfchuan.mini.bo.DownloadBo;
import com.bfchuan.mini.entity.TaskModel;
import com.bfchuan.mini.ui.myguis.MyTableModel;

/**
 * 正在下载的Table
 * 作者:Loenidas
 * 时间:2012-5-10
 * piaobomengxiang@163.com
 * 版本:v1.0
 *
 */
public class DownloadingTable {

    private static DownloadingTable mlt = new DownloadingTable();
    private Object[] attributes = {"状态", "歌曲名称", "歌手", "专辑", "格式", "大小", "进度"};
    private DefaultTableModel myModel = new MyTableModel();
    private JTable musicTable = new JTable(myModel);

    private DownloadingTable() {
        musicTable.getTableHeader().setReorderingAllowed(false);
        musicTable.setRowHeight(20);
        myModel.setColumnIdentifiers(attributes);
        musicTable.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        initialize();
        initAllTask();
    }

    private void initialize() {
        //设置各个列宽度
        TableColumn tc = musicTable.getColumn(attributes[0]);
        tc.setPreferredWidth(100);
        tc = musicTable.getColumn(attributes[1]);
        tc.setPreferredWidth(150);
        tc = musicTable.getColumn(attributes[2]);
        tc.setPreferredWidth(100);
        tc = musicTable.getColumn(attributes[3]);
        tc.setPreferredWidth(100);
        tc = musicTable.getColumn(attributes[4]);
        tc.setPreferredWidth(60);
        tc = musicTable.getColumn(attributes[5]);
        tc.setPreferredWidth(60);
        tc = musicTable.getColumn(attributes[6]);
        tc.setPreferredWidth(60);
    }
    
    /**
     * 初始化所有未完成的下载任务
     */
    private void initAllTask() {
    	Vector<TaskModel> tasks = DownloadBo.getInstance().getAllTaskList();
		if (tasks.size() == 0) {
			return;
		}
		TaskModel tm;
		int progress = 0;
		for (int i = 0; i < tasks.size(); i++) {
			tm = tasks.elementAt(i);
			progress = (int)((tm.getCurPos() * 100) / tm.getTotalSize());
			Object[] obj = { "停止下载", tm.getSongName(), tm.getArtist(),
					tm.getAlbum(), ".mp3", tm.getMszie(), progress + "%" };
			myModel.addRow(obj);
		}
    }

    public static DownloadingTable getInstance() {
        return mlt;
    }

    public JTable getTable() {
        return musicTable;
    }

    public DefaultTableModel getTableModel() {
        return myModel;
    }
}
