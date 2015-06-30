package com.bfchuan.mini.ui.myguis;

import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.plaf.basic.BasicButtonUI;


import com.bfchuan.mini.enums.ButtonType;
import com.bfchuan.mini.util.ImageTool;

/**
 * 作者:Loenidas
 * 时间:2012-5-10
 * piaobomengxiang@163.com
 * 版本:v1.0
 *
 */
@SuppressWarnings("serial")
public class WindowsButton extends JButton {

    private Image backImage;
    private boolean isHaveBackImg = false;
    private boolean isSelect = false;

    public WindowsButton() {
    }

    public WindowsButton(ImageIcon icon, ButtonType type) {
    	ImageTool imgTool = ImageTool.getInstance();
        if (type == ButtonType.WINDOWS_BUTTON) {
            backImage = imgTool.getImage("images/windowsBtnBack.png");
        } else if (type == ButtonType.PAGE_BUTTON) {
        	backImage = imgTool.getImage("images/titleBtnBack.png");
        }
        setIcon(icon);
        setUI(new BasicButtonUI());
        setBorderPainted(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setOpaque(false);
        addMouseListener(new MyMouseAdapter());
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (isHaveBackImg || isSelect) {
            g.drawImage(backImage, 0, 0, getWidth(), getHeight(), null);
        }
    }

    public void setSelectState(boolean isSelect) {
        this.isSelect = isSelect;
        repaint();
    }

    private class MyMouseAdapter extends MouseAdapter {

        public MyMouseAdapter() {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            isHaveBackImg = true;
            repaint();
        }

        @Override
        public void mouseExited(MouseEvent e) {
            isHaveBackImg = false;
            repaint();
        }
    }
}
