package com.bfchuan.mini.ui.guicomps;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 * 此类中间部分左边的容器类
 * 作者:Loenidas
 * 时间:2012-5-10
 * piaobomengxiang@163.com
 * 版本:v1.0
 *
 */
@SuppressWarnings("serial")
public class PlayPanel extends JPanel {

    private static PlayPanel ppnl;
    private MyMenuBar myJmb = MyMenuBar.getInstance();
    private LrcLabel lrcLabel = LrcLabel.getInstance();

    private PlayPanel() {
        setLayout(null);
        add(myJmb);
        add(lrcLabel);
    }

    public MyMenuBar getMenuBar() {
        return myJmb;
    }

    public static PlayPanel getInstance() {
    	if (ppnl == null) {
    		ppnl = new PlayPanel();
    	}
        return ppnl;
    }

    @Override
    public void paintComponent(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(0, 0, getWidth(), getHeight());
        myJmb.setBounds(0, 0, getWidth(), 20);
        lrcLabel.setBounds(0, 20, getWidth(), getHeight() - 40);
    }
}
