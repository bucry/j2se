package com.zhuxian.test;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.jvnet.substance.skin.SubstanceGreenMagicLookAndFeel;

import com.zhuxian.util.FindTaskLogin;
import com.zhuxian.util.MusicPlay;

public class Login1 implements Runnable,ActionListener{
	
   static {
	   
		JFrame.setDefaultLookAndFeelDecorated(true);
		JDialog.setDefaultLookAndFeelDecorated(true);
		try {
			UIManager.setLookAndFeel(new SubstanceGreenMagicLookAndFeel());
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
   }
   
   private static Login1 login = null;
   public static JFrame loginFrame = new JFrame();
   private static  JPanel mainPanel = new TestPanel();
   private static  JPanel loginPanel = new JPanel();
	
	
	public static Login1 getInstance(){
		if(login == null){
			login = new Login1();
		}
		return login;
	}
	
	private Login1(){
		loginFrame.setLayout(null);
		//修改LOGO
		Toolkit tk=Toolkit.getDefaultToolkit();
		Image image=tk.createImage("images/iconSmall.png");
		loginFrame.setIconImage(image);
		
		mainPanel.setBounds(0, 0, 800, 600);
		
		loginPanel.setBounds(300, 20, 250, 300);
		loginPanel.setOpaque(false);
		//loginPanel.setBorder(BorderFactory.createTitledBorder("分组框")); //设置面板边框，实现分组框的效果，此句代码为关键代码
		//loginPanel.setBorder(BorderFactory.createLineBorder(Color.green));//设置面板边框颜色
		
		JLabel guiliDie = new JLabel("");
		guiliDie.setIcon(new ImageIcon("GifImages/573828.gif"));
		guiliDie.setBounds(0, 0, 500, 545);
		
		mainPanel.add(guiliDie);
		
		mainPanel.add(loginPanel);
		
		
		loginFrame.add(mainPanel);
		loginFrame.setSize(800, 600);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		loginFrame.setLocation((screen.width - loginFrame.getSize().width) / 2,
				(screen.height - loginFrame.getSize().height) / 2);
		loginFrame.setVisible(true);
		loginFrame.setResizable(false);
		loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public static void main(String args[]){
		new Login1();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		
	}

}
