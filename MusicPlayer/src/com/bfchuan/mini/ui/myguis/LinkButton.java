package com.bfchuan.mini.ui.myguis;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.plaf.basic.BasicButtonUI;

/**
 * 此类为更改颜色的按钮类
 * 作者:Loenidas
 * 时间:2012-5-10
 * piaobomengxiang@163.com
 * 版本:v1.0
 *
 */
@SuppressWarnings("serial")
public class LinkButton extends JButton {

    private boolean lineSign = false;
    private Color defaultColor = Color.blue;

    public LinkButton(String str) {
        setText(str);
        setFocusable(false);
        setForeground(defaultColor.darker());
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setUI(new BasicButtonUI());
        setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        setOpaque(false);
        addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                lineSign = true;
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                lineSign = false;
                repaint();
            }
        });
    }

    public void setDefaultColor(Color defaultColor) {
        this.defaultColor = defaultColor;
        setForeground(defaultColor.darker());
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);//反锯齿
        if (lineSign == true) {
            g.setColor(defaultColor.darker());
            g.drawLine(0, getHeight() - 3, getWidth(), getHeight() - 3);
        }
    }
    
}
