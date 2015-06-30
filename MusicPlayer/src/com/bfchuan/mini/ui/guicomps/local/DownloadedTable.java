package com.bfchuan.mini.ui.guicomps.local;

import java.awt.Cursor;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;


import com.bfchuan.mini.bo.ConfigBo;
import com.bfchuan.mini.bo.SongBo;
import com.bfchuan.mini.ui.myguis.MyTableModel;

/**
 * 已经下载的Table
 * 作者:Loenidas
 * 时间:2012-5-10
 * piaobomengxiang@163.com
 * 版本:v1.0
 *
 */
public class DownloadedTable {

    private static DownloadedTable mlt = new DownloadedTable();
    private Object[] attributes = {"播放", "歌名", "歌手", "专辑", "格式", "大小", "下载时间", "本地路径"};
    private DefaultTableModel myModel = new MyTableModel();
    private JTable musicTable = new JTable(myModel);

    private DownloadedTable() {
        musicTable.getTableHeader().setReorderingAllowed(false);
        musicTable.setRowHeight(20);
        myModel.setColumnIdentifiers(attributes);
        musicTable.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        //musicTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);//显示水平滚动条
        initialize();
        initSong();
    }

    private void initialize() {
        //设置各个列宽度
        TableColumn tc = musicTable.getColumn(attributes[0]);
        tc.setPreferredWidth(50);
        tc = musicTable.getColumn(attributes[1]);
        tc.setPreferredWidth(150);
        tc = musicTable.getColumn(attributes[2]);
        tc.setPreferredWidth(100);
        tc = musicTable.getColumn(attributes[3]);
        tc.setPreferredWidth(100);
        tc = musicTable.getColumn(attributes[4]);
        tc.setPreferredWidth(40);
        tc = musicTable.getColumn(attributes[5]);
        tc.setPreferredWidth(60);
        tc = musicTable.getColumn(attributes[6]);
        tc.setPreferredWidth(100);
        tc = musicTable.getColumn(attributes[7]);
        tc.setPreferredWidth(200);
    }
    
    /**
     * 初始化下载文件夹下的所有歌曲
     */
    private void initSong() {
    	SongBo.getInstance().loadAllSongAndUpdateTableModel(ConfigBo.getInstance().getNetMusicDownloadFolder(), this.getTableModel(), 1);
    }

    public static DownloadedTable getInstance() {
        return mlt;
    }

    public JTable getTable() {
        return musicTable;
    }

    public DefaultTableModel getTableModel() {
        return myModel;
    }
}
