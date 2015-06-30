package com.zhuxian.ui;


import java.awt.Container;
import java.awt.Font;
import java.io.File;
import java.util.EmptyStackException;
import java.util.Iterator;
import java.util.Stack;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;

public class ImplementsDemo {
    public static void main(String[] args) throws EmptyStackException{
        JFrame f=new JFrame("Welcome To Earth!");
        Container cont=f.getContentPane();
        String picPath="c:"+File.separator+"Users"+File.separator+"Sunboy"+File.separator+"desktop"+File.separator+"课表.png";
        Icon ico=new ImageIcon(picPath);
        JPanel pan=new JPanel();
        JLabel lab=new JLabel(ico);
        pan.add(lab);
        JScrollPane scr1=new JScrollPane(pan, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        cont.add(scr1);
        f.setSize(1280,720);
        f.setLocation(300, 200);
        f.setVisible(true);
        
    }
    
}