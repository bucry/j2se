package com.bfchuan.mini.ui.myguis;

import java.awt.Cursor;
import java.awt.Graphics;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import com.bfchuan.mini.util.ImageTool;

/**
 * 选择的主题标签
 * 作者:Loenidas
 * 时间:2012-5-10
 * piaobomengxiang@163.com
 * 版本:v1.0
 *
 */
@SuppressWarnings("serial")
public class SelectedLabel extends JLabel {

    private ImageIcon labIcon;
    private ImageIcon yesIcon;

    public SelectedLabel(ImageIcon ico) {
        this.labIcon = ico;
        this.setCursor(new Cursor(Cursor.HAND_CURSOR));
        yesIcon = ImageTool.getInstance().getIcon("images/yes.png");
    }

    public void setLabIcon(ImageIcon labIcon) {
        this.labIcon = labIcon;
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(labIcon.getImage(), 0, 0, getWidth(), getHeight(), null);
        g.drawImage(yesIcon.getImage(), getWidth() - 28, 5, 25, 25, null);
    }
}
