package com.bfchuan.mini.ui.guicomps.local;

import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 * 正在下载的table的容器
 * 作者:Loenidas
 * 时间:2012-5-10
 * piaobomengxiang@163.com
 * 版本:v1.0
 *
 */
@SuppressWarnings("serial")
public class DownloadingTablePanel extends JPanel {

    private static DownloadingTablePanel ditpnl;
    private JTable dimTable = DownloadingTable.getInstance().getTable();
    private JScrollPane tabpnl = new JScrollPane(dimTable);

    private DownloadingTablePanel() {
        setLayout(null);
        tabpnl.getViewport().setBackground(Color.white);
        add(tabpnl);
        
        new DownloadingListener();//增加Table的监听
    }

    public static DownloadingTablePanel getInstance() {
    	if (ditpnl == null) {
    		ditpnl = new DownloadingTablePanel();
    	}
        return ditpnl;
    }

    public JScrollPane getScrollPane() {
        return tabpnl;
    }
}
