package com.bfchuan.mini.ui.guicomps;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;


import com.bfchuan.mini.bo.SongBo;
import com.bfchuan.mini.ui.myguis.MyCellRenderer;
import com.bfchuan.mini.ui.myguis.MyTabbedPaneUI;

import mp3x.ctl.Mp3TVShow;

/**
 * 此类为中间部分右边容器
 * 作者:Loenidas
 * 时间:2012-5-10
 * piaobomengxiang@163.com
 * 版本:v1.0
 *
 */
@SuppressWarnings("serial")
public class RightPanel extends JPanel {

    private JTabbedPane listTabPnl = new JTabbedPane(SwingConstants.TOP);
    @SuppressWarnings({ "unchecked", "rawtypes" })
	private JList<?> defaultSongList = new JList(SongBo.getInstance().getAllSongList());
    private JScrollPane defaultListPnl = new JScrollPane(defaultSongList);
    //private PlayerInfoLabel playInfoLab = PlayerInfoLabel.getInstance();
    private SongListPPMenu popupMenu = SongListPPMenu.getInstance();
    private Mp3TVShow mp3TVShow = new Mp3TVShow();
    private JPanel TVShowPnl = new JPanel();
    private Color bgColor = new Color(255, 240, 255);
    
    private static RightPanel rpnl = new RightPanel();

    private RightPanel() {
        setLayout(null);
        initListPanel();
        //add(playInfoLab);
        add(listTabPnl);
        mp3TVShow.setBackground(Color.black);
        TVShowPnl.setLayout(null);
        TVShowPnl.setBackground(Color.black);
        TVShowPnl.add(mp3TVShow);
        mp3TVShow.setBounds(0, 0, 185, 80);
        add(TVShowPnl);
    }

    public static RightPanel getInstance() {
    	if (rpnl == null) {
    		rpnl = new RightPanel();
    	}
        return rpnl;
    }

    /**
     * 初始化列表容器
     */
	public void initListPanel() {
        listTabPnl.setUI(new MyTabbedPaneUI());
        listTabPnl.setFont(new Font("宋体", 12, 12));
        listTabPnl.setFocusable(false);
        listTabPnl.setForeground(new Color(51, 105, 186));
        listTabPnl.add(defaultListPnl, "播放列表");
        listTabPnl.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        defaultListPnl.setBorder(null);

        defaultSongList.setCellRenderer(new MyCellRenderer());
        defaultSongList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    /**
     * 得到弹出菜单
     * @return
     */
    public SongListPPMenu getPopupMenu() {
        return popupMenu;
    }
    
    /**
     * 得到歌曲列表的组件JList
     * @return
     */
    public JList<?> getDefaultSongList() {
    	return defaultSongList;
    }

    /**
     * 得到TabPnl
     * @return
     */
    public JTabbedPane getListTabPnl() {
        return listTabPnl;
    }

    public Mp3TVShow getMp3TVShow() {
        return mp3TVShow;
    }

    public void updateListUI() {
    	defaultSongList.updateUI();
    }
    
    public void setBgColor(Color bgColor) {
    	this.bgColor = bgColor;
    	repaint();
    }
    
    @Override
    public void paintComponent(Graphics g) {
        Rectangle clip = g.getClipBounds();
        Graphics2D g2d = (Graphics2D) g;//
        //渐变效果
        GradientPaint background = new GradientPaint(2f, getHeight() - 59,
        		bgColor, 2f, getHeight() - 1, Color.white);
        g2d.setPaint(background);
        g2d.fillRect(clip.x, clip.y, clip.width, clip.height);
        g2d.setColor(Color.black);
        g2d.fillRect(10, 10, getWidth() - 20, 80);
        TVShowPnl.setBounds(10, 10, getWidth() - 20, 80);
        listTabPnl.setBounds(0, 100, getWidth() - 1, getHeight() - 100);
    }
    
}
