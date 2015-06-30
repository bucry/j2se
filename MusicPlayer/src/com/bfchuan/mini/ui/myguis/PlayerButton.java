package com.bfchuan.mini.ui.myguis;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.plaf.basic.BasicButtonUI;

import com.bfchuan.mini.util.ImageTool;

/**
 * 播放按钮类
 * 作者:Loenidas
 * 时间:2012-5-10
 * piaobomengxiang@163.com
 * 版本:v1.0
 *
 */
@SuppressWarnings("serial")
public class PlayerButton extends JButton {

    private String iconPath = "";

    /**
     * 构造方法
     */
    public PlayerButton() {
        init();
    }

    /**
     * 构造方法
     * @param icon是按钮的Icon
     */
    public PlayerButton(Icon icon) {
        super(icon);
        init();
        addMouseListener();
    }

    /**
     * 初始化按钮
     */
    public void init() {
        setUI(new BasicButtonUI());
        setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        setOpaque(false);
    }

    public void addMouseListener() {
    	addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                 setIcon(ImageTool.getInstance().getIcon(iconPath + "_.png"));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                 setIcon(ImageTool.getInstance().getIcon(iconPath + ".png"));
            }
    	});
    }

    /**
     * 设置按钮的图标路径
     * @param iconPath为Icon的路径
     */
    public void setIconPath(String iconPath) {
        if (iconPath.length() < 4) {
            System.out.println("Error");
            return;
        }
        this.iconPath = iconPath.substring(0, iconPath.length() - 4);
    }

}
