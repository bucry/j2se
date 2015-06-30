package com.bfchuan.mini.ui.guicomps.net;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import com.bfchuan.mini.ui.myguis.MyTableModel;

/**
 * 作者:Loenidas
 * 时间:2012-5-10
 * piaobomengxiang@163.com
 * 版本:v1.0
 *
 */
public class MusicListTable {

    private static MusicListTable mlt;
    private Object[] attributes = {"歌名", "歌手", "专辑", "大小", "速度", "试听", "添加", "下载"};
    private DefaultTableModel myModel = new MyTableModel();
    private JTable musicTable = new JTable(myModel);

    private MusicListTable() {
        musicTable.getTableHeader().setReorderingAllowed(false);
        musicTable.setRowHeight(20);
        myModel.setColumnIdentifiers(attributes);
        initialize();
    }

    public void initialize() {
        //设置各个列宽度
        TableColumn tc = musicTable.getColumn(attributes[0]);
        tc.setPreferredWidth(150);
        tc = musicTable.getColumn(attributes[1]);
        tc.setPreferredWidth(100);
        tc = musicTable.getColumn(attributes[2]);
        tc.setPreferredWidth(150);
        tc = musicTable.getColumn(attributes[3]);
        tc.setPreferredWidth(70);
        tc = musicTable.getColumn(attributes[4]);
        tc.setPreferredWidth(40);
        tc = musicTable.getColumn(attributes[5]);
        tc.setPreferredWidth(60);
        tc = musicTable.getColumn(attributes[6]);
        tc.setPreferredWidth(60);
        tc = musicTable.getColumn(attributes[7]);
        tc.setPreferredWidth(60);
    }

    public static MusicListTable getInstance() {
    	if (mlt == null) {
    		mlt = new MusicListTable();
    	}
        return mlt;
    }

    public JTable getTable() {
        return musicTable;
    }

    public DefaultTableModel getTableModel() {
        return myModel;
    }
}
