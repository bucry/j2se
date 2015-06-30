package com.bfchuan.mini.ui.guicomps.local;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;


import com.bfchuan.mini.bo.FileBo;
import com.bfchuan.mini.ui.myguis.ImageButton;
import com.bfchuan.mini.ui.myguis.LinkButton;
import com.bfchuan.mini.util.ImageTool;
import com.bfchuan.mini.util.LocalMusicSearcher;

/**
 * 本地曲库的table容器
 * 作者:Loenidas
 * 时间:2012-5-10
 * piaobomengxiang@163.com
 * 版本:v1.0
 *
 */
@SuppressWarnings("serial")
public class LoaclTablePanel extends JPanel implements ActionListener {

    private static LoaclTablePanel ltpnl;
    private JTable lmTable;
    private JScrollPane tabpnl;
    private JComboBox<String> driveBox = new JComboBox<String>();
    private JTextField searchField = new JTextField();
    private ImageButton searchBtn;
    private ImageButton addSongBtn;
    private LinkButton cancelBtn;// 结束搜索的按钮
    private JLabel appearInfoLab;// 显示信息的标签
    private LocalMusicSearcher slm;// 搜索本地歌曲的线程

    private LoaclTablePanel() {
    	
    	lmTable = LocalTable.getInstance().getTable();
    	tabpnl = new JScrollPane(lmTable);
    	
    	ImageTool imgTool = ImageTool.getInstance(); 
    	searchBtn = new ImageButton(imgTool.getIcon("images/local.png"));
    	addSongBtn = new ImageButton(imgTool.getIcon("images/add.png"));
    	addSongBtn.addActionListener(this);
    	
        setLayout(null);
        tabpnl.getViewport().setBackground(Color.white);
        add(tabpnl);
        searchField.setFont(new Font("宋体", 15, 15));
        this.add(searchField);
        searchBtn.setToolTipText("本地搜索");
        this.add(searchBtn);
        searchBtn.addActionListener(this);
        addSongBtn.setToolTipText("添加本地歌曲");
        this.add(addSongBtn);
        //addSongBtn.setBounds(0, 30, 42, 20);
        
        File[] roots = File.listRoots();//得到所有盘符
        for (int i = 0; i < roots.length; i++) {
            driveBox.addItem(roots[i].toString());
        }
        driveBox.addItem("ALL");
        driveBox.setBounds(0, 5, 60, 20);
        this.add(driveBox);
        
        cancelBtn = new LinkButton("结束搜索");
        cancelBtn.setForeground(Color.red);
        cancelBtn.setDefaultColor(Color.red);
        cancelBtn.addActionListener(this);
        cancelBtn.setVisible(false);
        this.add(cancelBtn);
        
        appearInfoLab = new JLabel();
        this.add(appearInfoLab);
        
        new LocalListener();
    }

    public void addActionListener(ActionListener listener) {
        searchBtn.addActionListener(listener);
        addSongBtn.addActionListener(listener);
    }

    public JTextField getSearchField() {
        return searchField;
    }

    public ImageButton getSearchBtn() {
        return searchBtn;
    }

    public ImageButton getAddSongBtn() {
        return addSongBtn;
    }
    
    public JTable getLmTable() {
        return lmTable;
    }
    
    public JComboBox<String> getDriveBox() {
        return driveBox;
    }

    public static LoaclTablePanel getInstance() {
    	if (ltpnl == null) {
    		ltpnl = new LoaclTablePanel();
    	}
        return ltpnl;
    }

    public JScrollPane getScrollPane() {
        return tabpnl;
    }

    @Override
    public void paintComponent(Graphics g) {
    	super.paintComponent(g);
        driveBox.setBounds(0, 5, 60, 20);
        searchField.setBounds(61, 5, getWidth() - 193, 20);
        searchBtn.setBounds(getWidth() - 132, 5, 64, 20);
        cancelBtn.setBounds(getWidth() - 65, 5, 55, 20);
        addSongBtn.setBounds(0, 30, 42, 20);
        appearInfoLab.setBounds(43, 30, getWidth() - 50, 20);
        //tabpnl.setBounds(0, 55, getWidth(), getHeight());
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == addSongBtn) {
			FileBo.getInstance().addMusicFolderToLocalMusic();
		} else if (e.getSource() == searchBtn) {// 结束搜索
			String distPath = this.getDriveBox().getSelectedItem().toString();
            String keyword = this.getSearchField().getText();
            if (!keyword.trim().equals("")) {
            	File[] disFile = null;
                if (distPath.equals("ALL")) {
                    disFile = File.listRoots();
                } else {
                    disFile = new File[1];
                    disFile[0] = new File(distPath);
                }
                slm = new LocalMusicSearcher(disFile, keyword, LocalTable.getInstance().getTableModel(), 
                		this.cancelBtn, this.appearInfoLab);
                slm.start();
            }
		} else if (e.getSource() == cancelBtn) {
			if (slm != null && slm.isAlive()) {
				slm.setStop(true);
			}
		}
	}
}
