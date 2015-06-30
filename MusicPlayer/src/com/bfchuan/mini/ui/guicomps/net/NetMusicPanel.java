package com.bfchuan.mini.ui.guicomps.net;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import netmp3.search.MusicInfo;

import com.bfchuan.mini.ui.myguis.ImageButton;
import com.bfchuan.mini.ui.myguis.ImageLabel;
import com.bfchuan.mini.ui.myguis.LinkButton;
import com.bfchuan.mini.util.ImageTool;
import com.bfchuan.mini.util.NetMusicSearcher;

/**
 * 网络曲库界面
 * 作者:Loenidas
 * 时间:2012-5-10
 * piaobomengxiang@163.com
 * 版本:v1.0
 *
 */
@SuppressWarnings("serial")
public class NetMusicPanel extends JPanel implements ActionListener {

    private static NetMusicPanel nmpnl;
    private static ImageTool imgTool = ImageTool.getInstance();
    private JTextField searchField;
    private ImageButton searchBtn;
    private LinkButton cancelBtn;
    private ImageLabel musicLabel;
    private JRadioButton searchConditions[] = new JRadioButton[2];
    private ButtonGroup bgroup = new ButtonGroup();
    private JScrollPane musicListPnl = new JScrollPane();
    private JLabel resultLabel = new JLabel("", SwingConstants.RIGHT);
    private JTable musicListTable = MusicListTable.getInstance().getTable();
    private ArrayList<MusicInfo> musicList = new ArrayList<MusicInfo>();

    private NetMusicPanel() {
    	searchField = new JTextField();
        searchField.setFont(new Font("宋体", 15, 15));
        
        searchBtn = new ImageButton(imgTool.getIcon("images/search.jpg"));
        musicLabel = new ImageLabel(imgTool.getImage("images/musicIcon.png"));
        
        cancelBtn = new LinkButton("结束搜索");
        cancelBtn.setForeground(Color.red);
        cancelBtn.setDefaultColor(Color.red);
        cancelBtn.addActionListener(this);
        cancelBtn.setVisible(false);
        
        add(musicLabel);
        add(searchField);
        add(searchBtn);
        add(cancelBtn);
        searchBtn.setToolTipText("搜索歌曲");
        searchBtn.addActionListener(this);

        searchConditions[0] = new JRadioButton("按歌名");
        searchConditions[1] = new JRadioButton("按歌手");
        searchConditions[0].setSelected(true);
        bgroup.add(searchConditions[0]);
        bgroup.add(searchConditions[1]);
        add(searchConditions[0]);
        add(searchConditions[1]);

        musicListPnl.setBackground(Color.white);

        musicListPnl.setBorder(BorderFactory.createLineBorder(Color.lightGray, 1));
        musicListPnl.setViewportView(musicListTable);
        musicListPnl.getViewport().setBackground(Color.white);
        add(musicListPnl);

        add(resultLabel);
        
        this.setBorder(BorderFactory.createLineBorder(Color.lightGray, 1));
        
        new MusicListListener();//给网络歌曲列表增加监听器
    }

    public static NetMusicPanel getInstance() {
    	if (nmpnl == null) {
    		nmpnl = new NetMusicPanel();
    	}
        return nmpnl;
    }

    public ArrayList<MusicInfo> getMusicList() {
        return musicList;
    }

    public JRadioButton[] getSearchConditions() {
        return searchConditions;
    }

    public JTextField getSearchField() {
        return searchField;
    }

    public JButton getSearchBtn() {
        return searchBtn;
    }
    
    public JButton getCancelBtn() {
        return cancelBtn;
    }

    public JLabel getResultLabel() {
        return resultLabel;
    }

    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Rectangle clip = g.getClipBounds();
        Graphics2D g2d = (Graphics2D) g;
        //渐变效果
        GradientPaint background = new GradientPaint(2f, getHeight() - 59, Color.white, 2f, getHeight() - 1, new Color(255, 240, 255));
        g2d.setPaint(background);
        g2d.fillRect(clip.x, clip.y, clip.width, clip.height);
        musicLabel.setBounds(10, 5, 128, 128);
        searchField.setBounds(160, 40, getWidth() - 300, 25);
        cancelBtn.setBounds(getWidth() - 220, 104, 60, 25);
        resultLabel.setBounds(getWidth() - 350, 100, 128, 30);
        searchBtn.setBounds(getWidth() - 120, 20, 104, 104);
        searchConditions[0].setBounds(170, 70, 65, 25);
        searchConditions[1].setBounds(240, 70, 65, 25);
        musicListPnl.setBounds(10, 135, getWidth() - 20, getHeight() - 140);
        musicListPnl.setPreferredSize(new Dimension(getWidth() - 20, getHeight() - 140));
        musicListPnl.updateUI();
        //System.out.println("网络Panel重绘...");
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == searchBtn) {
			NetMusicSearcher.getInstance().search();
		} else if (e.getSource() == cancelBtn) {
			NetMusicSearcher.getInstance().stopSearch();
		}
	}
}
