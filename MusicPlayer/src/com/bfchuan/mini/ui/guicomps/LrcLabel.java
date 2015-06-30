package com.bfchuan.mini.ui.guicomps;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.Set;

import javax.swing.JLabel;


import com.bfchuan.mini.bo.ConfigBo;
import com.bfchuan.mini.bo.MusicBo;
import com.bfchuan.mini.entity.LrcSentence;
import com.bfchuan.mini.util.FormatUtils;

/**
 * 歌词显示的类
 * 作者:Loenidas
 * 时间:2012-5-10
 * piaobomengxiang@163.com
 * 版本:v1.0
 *
 */
@SuppressWarnings("serial")
public class LrcLabel extends JLabel {

    private static LrcLabel lrcLabel;
    private ConfigBo configBo;
    private String warnString = "正在载入歌词...";
    private int standOutSign = 0;//突出显示的那一行
    private boolean appearLine = false;
    private int nowTime = 0;
    private boolean adjustLrc = false;
    private ArrayList<LrcSentence> lrcStringList;

    private LrcLabel() {
    	lrcStringList = new ArrayList<LrcSentence>();
        this.setCursor(new Cursor(Cursor.HAND_CURSOR));
        configBo = ConfigBo.getInstance();
    }

    public static LrcLabel getInstance() {
    	if (lrcLabel == null) {
    		lrcLabel = new LrcLabel();
    	}
        return lrcLabel;
    }
    
    public void refresh(Set<LrcSentence> lrcStringSet) {
    	if (lrcStringSet == null || lrcStringSet.size() == 0) {
    		lrcStringList.clear();// 清空
    		warnString = "没有搜索到歌词...";
        	repaint();
    		return;
    	}
    	lrcStringList.clear();
    	lrcStringList.addAll(lrcStringSet);//将Set增加到ArrayList中便于后面处理
    	standOutSign = 0;
    	nowTime = 0;
    	repaint();
    }
    
    /**
     * 调整歌词进度
     * @param decrease是移动的个数
     * @param media为true是调整音乐进度，false时只调整歌词进度
     */
    public void adjustLRC(int decrease, boolean media) {
        if (lrcStringList.size() != 0 && (standOutSign + decrease) < lrcStringList.size() - 1
                && (standOutSign + decrease) >= 0) {
            this.standOutSign = standOutSign + decrease;
            nowTime = lrcStringList.get(this.standOutSign).getTime();
            if (media) {
/*                MusicPlayer.pause();
                SliderUpdateThread.pause();
                MusicPlayer.getPlayer().setMediaTime(new Time(lrcStringList.get(this.standOutSign).getTime() * 1.0));
                MusicPlayer.start();
                SliderUpdateThread.resume();*/
            }
            repaint();
        }
    }

    /**
     * 更新歌词
     * @param time时歌词播放的时间
     */
    public void updateLRC(int time) {
        if (!isAdjustLrc()) {
            this.nowTime = time;
            
            int size = lrcStringList.size();
            for (int i = 0; i < size; i++) {
                if (time >= lrcStringList.get(size - 1).getTime()) {
                    standOutSign = size - 1;
                } else if (time >= lrcStringList.get(i).getTime() && time <= lrcStringList.get(i + 1).getTime()) {
                    standOutSign = i;
                }
            }
            repaint();
        }
    }

    /**
     * 是否显示虚线
     * @return
     */
    public boolean isAppearLine() {
        return appearLine;
    }

    /**
     * 设置虚线显示状态
     * @param appearLine
     */
    public void setAppearLine(boolean appearLine) {
        this.appearLine = appearLine;
        repaint();
    }

    /**
     * 是否在调整歌词
     * @return
     */
    public boolean isAdjustLrc() {
        return adjustLrc;
    }

    /**
     * 设置歌词是否在调整的标记
     * @param adjustLrc
     */
    public void setAdjustLrc(boolean adjustLrc) {
        this.adjustLrc = adjustLrc;
    }

    /**
     * 设置突出显示的那一行
     * @param standOutSign为下标
     */
    public void setStandOutSign(int standOutSign) {
        this.standOutSign = standOutSign;
        repaint();
    }
    
    @Override
    public void paintComponent(Graphics g) {
    	super.paintComponent(g);
        if (configBo.getLrcBgImage() != null) {
            g.drawImage(configBo.getLrcBgImage(), 0, 0, getWidth(), getHeight(), null);
        } else {
            g.setColor(Color.black);
            g.fillRect(0, 0, getWidth(), getHeight());
        }
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);//反锯齿
        g2d.setFont(new Font("宋体", 20, 20));
        int size = lrcStringList.size();
        int centerLine = getHeight() / 2;
        if (appearLine && size != 0) {
            g2d.setStroke(new BasicStroke(1f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, new float[]{6.0f}, 0.0f));
            g.setColor(ConfigBo.getInstance().getLrcFgColor());
            g.drawLine(0, centerLine, getWidth(), centerLine);
            g.drawString(FormatUtils.formatTime(nowTime), 5, centerLine - 1);
        }
        if (size != 0) {
            for (int i = 0; i < size; i++) {
                if (i == standOutSign) {
                    g.setColor(ConfigBo.getInstance().getLrcFgColor());
                } else {
                    g.setColor(ConfigBo.getInstance().getLrcBgColor());
                }
                String str = lrcStringList.get(i).getLrcString();
                int strWidth = g.getFontMetrics().charsWidth(str.toCharArray(), 0, str.length());
                int begin = (getWidth() - strWidth) / 2;
                g.drawString(str, begin, centerLine + 8 + 28 * (i - standOutSign));
            }
        } else {
            if (MusicBo.getInstance().getCurrentSong() != null) {
                int strWidth = g.getFontMetrics().charsWidth(warnString.toCharArray(), 0, warnString.length());
                int begin = (getWidth() - strWidth) / 2;
                g.setColor(ConfigBo.getInstance().getLrcFgColor());
                g.drawString(warnString, begin, centerLine);
            }
        }
    }
}
