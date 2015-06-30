package com.zhuxian.frame;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.zhuxian.util.ImageMainPanel;

public class FrameMain {
	
	public static JFrame mainFrame = new JFrame();
	private static  JPanel mainPanel = new ImageMainPanel();
	private static FrameMain frameMain = null;
	
	public static FrameMain getInstance(){
		if(frameMain == null){
			return new FrameMain();
		}else{
			return frameMain;
		}
	}
	
	public FrameMain(){
		mainPanel.setBounds(0, 0, 800, 600);
		Toolkit tk=Toolkit.getDefaultToolkit();
		Image image=tk.createImage("images/iconSmall.png");
		
		JLabel jineng = new JLabel("");
		jineng.setIcon(new ImageIcon("images/jineng.png"));
		jineng.setBounds(19, 520, 762, 65);
		mainPanel.add(jineng);
		
		
		mainFrame.add(mainPanel);
		mainFrame.setIconImage(image);
		mainFrame.setSize(800, 600);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		mainFrame.setLocation((screen.width - mainFrame.getSize().width) / 2,
				(screen.height - mainFrame.getSize().height) / 2);
		mainFrame.setVisible(true);
		mainFrame.setResizable(false);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public static void main(String args[]){
		new FrameMain();
	}

}
