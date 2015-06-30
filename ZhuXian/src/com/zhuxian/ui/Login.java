package com.zhuxian.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.jvnet.substance.skin.SubstanceGreenMagicLookAndFeel;

import com.zhuxian.util.ImageLoginPanel;
import com.zhuxian.util.MusicPlay;

public class Login implements Runnable,ActionListener{
	
   static {
	   
		JFrame.setDefaultLookAndFeelDecorated(true);
		JDialog.setDefaultLookAndFeelDecorated(true);
		try {
			UIManager.setLookAndFeel(new SubstanceGreenMagicLookAndFeel());
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
   }
   
   private static Login login = null;
   public static JFrame loginFrame = new JFrame();
   //private static JButton submit = new JButton("登录");
  // private static JButton canel = new JButton("取消");
  /* private JLabel nameLabel = new JLabel("帐号:");
   private JTextField name = new JTextField(15);
   private JLabel passLabel = new JLabel("密码:");
   private JPasswordField pass = new JPasswordField(15);*/
   private static  JPanel mainPanel = new ImageLoginPanel();
   //private static  JPanel contentPanel = new ContentPanel();
   private static  JPanel loginPanel = new JPanel();
   //private static  JScrollPane jScrollPaneContent;
   private static  JLabel titleJLabel = new JLabel("");
   private static  JLabel contentJLabel = new JLabel("诛仙");
   private static int x = 200, y = 10;
	public static Login getInstance(){
		if(login == null){
			login = new Login();
		}
		return login;
	}
	
	public Login(){
		new MusicPlay().playerCardMusic("music/Theme A.mp3");
		loginFrame.setLayout(null);
		//修改LOGO
		Toolkit tk=Toolkit.getDefaultToolkit();
		Image image=tk.createImage("images/iconSmall.png");
		loginFrame.setIconImage(image);
		new Thread(this).start();
		mainPanel.setBounds(0, 0, 800, 600);
		//mainPanel.setOpaque(false);
		/*nameLabel.setBounds(10, 10, 50, 20);
		name.setBounds(70, 10, 100, 20);
		
		passLabel.setBounds(10, 30, 50, 20);
		pass.setBounds(70, 30, 100, 20);*/
		
	/*	submit.setBounds(70, 50, 50, 20);
		submit.addActionListener(this);
		canel.setBounds(110, 50, 50, 20);*/
		
		loginPanel.setBounds(300, 20, 250, 300);
		loginPanel.setOpaque(false);
		//loginPanel.setBorder(BorderFactory.createTitledBorder("分组框")); //设置面板边框，实现分组框的效果，此句代码为关键代码
		//loginPanel.setBorder(BorderFactory.createLineBorder(Color.green));//设置面板边框颜色
		
		
		/*loginPanel.add(nameLabel);
		loginPanel.add(name);
		loginPanel.add(passLabel);
		loginPanel.add(pass);*/
		//loginPanel.add(submit);
		//loginPanel.add(canel);
		
		
		mainPanel.add(loginPanel);
		
		JLabel guiliDie = new JLabel("");
		guiliDie.setIcon(new ImageIcon("GifImages/gldie.gif"));
		guiliDie.setBounds(288, 250, 167, 200);
		
		titleJLabel.setIcon(new ImageIcon("images/logoTitle.png"));
		titleJLabel.setBounds(10, 10, 636, 698);
		mainPanel.add(titleJLabel);
		
		//jScrollPaneContent = new JScrollPane(contentPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		//contentPanel.setBounds(400, 10, 200, 300);
		//contentPanel.setOpaque(true);
		contentJLabel.setBounds(x, y, 200, 20);
		contentJLabel.setFont(new Font("宋体", Font.BOLD, 20));
		contentJLabel.setBackground(Color.RED);
		loginFrame.add(contentJLabel); //
		
		mainPanel.add(guiliDie);
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
		  SwingUtilities.invokeLater(new Runnable() {                
              @Override
              public void run() {
            	  new Login();               
              }
      });
	}

	@Override
	public void run() {
		while (true) {
				x = x + 1;
			if (x == 350)
				x = 200;
			contentJLabel.setLocation(x, y);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		/*if(obj == submit){
			new FindTaskLogin().init();
			loginFrame.dispose();
		}*/
	}

}
