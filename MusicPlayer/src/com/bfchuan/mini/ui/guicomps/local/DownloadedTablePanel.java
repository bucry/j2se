package com.bfchuan.mini.ui.guicomps.local;

import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 * 已经下载的Table的容器
 * 作者:Loenidas
 * 时间:2012-5-10
 * piaobomengxiang@163.com
 * 版本:v1.0
 *
 */
@SuppressWarnings("serial")
public class DownloadedTablePanel extends JPanel {

    private static DownloadedTablePanel detpnl;
    private JTable demTable;
    private JScrollPane tabpnl;
    
    private DownloadedTablePanel() {
    	demTable = DownloadedTable.getInstance().getTable();
    	tabpnl = new JScrollPane(demTable);
        setLayout(null);
        tabpnl.getViewport().setBackground(Color.white);
        tabpnl.setBounds(0, 0, getWidth(), getHeight());
        add(tabpnl);
        
        new DownloadedListener();
    }

    public static DownloadedTablePanel getInstance() {
    	if (detpnl == null) {
    		detpnl = new DownloadedTablePanel();
    	}
        return detpnl;
    }

    public JScrollPane getScrollPane() {
        return tabpnl;
    }

}
