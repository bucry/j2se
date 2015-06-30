package com.zhuxian.util;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import com.zhuxian.ui.Login;

public class LoginGameTalk extends JDialog implements ActionListener {
	
	
	private static final long serialVersionUID = 666823128374745817L;
	
	JFrame mainFrame;
	JButton okButton;
	javax.swing.Timer myTimer;
	int Counter = 0;

	public LoginGameTalk(JFrame mainFrame) {
		super(mainFrame, "关于本游戏的说明", true); // true 代表为有模式对话框
		this.mainFrame = Login.loginFrame;
		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new BorderLayout());
		JLabel imageLabel = new JLabel(new ImageIcon("images/iconSmall.png"));
		contentPanel.add(imageLabel, BorderLayout.WEST);

		JPanel authorInfoPane = new JPanel();
		authorInfoPane.setLayout(new GridLayout(1, 1));
		JTextArea aboutContent = new JTextArea(
				"诛仙诛仙诛仙诛仙诛仙诛仙诛仙诛仙诛仙诛仙诛仙诛仙诛仙诛仙诛\n诛仙诛仙诛仙诛仙。\n诛仙诛仙诛仙诛仙诛仙\n");
		aboutContent.enable(false);
		authorInfoPane.add(aboutContent);
		contentPanel.add(authorInfoPane, BorderLayout.NORTH);

		JPanel sysInfoPane = new JPanel();
		sysInfoPane.setLayout(new GridLayout(5, 1));
		sysInfoPane.setBorder(BorderFactory.createLoweredBevelBorder());
		contentPanel.add(sysInfoPane, BorderLayout.CENTER);
		JLabel userName = new JLabel("本机的用户名为："
				+ System.getProperty("user.name"));
		JLabel osName = new JLabel("本机的操作系统是：" + System.getProperty("os.name"));
		JLabel javaVersion = new JLabel("本机中所安装的Java SDK的版本号是："
				+ System.getProperty("java.version"));
		JLabel totalMemory = new JLabel("本机中Java虚拟机所可能使用的总内存数："
				+ Runtime.getRuntime().totalMemory() + "字节数");
		JLabel freeMemory=new JLabel("本机中Java虚拟机所剩余的内存数" + Runtime.getRuntime().freeMemory()+"字节数" ); 

		sysInfoPane.add(userName);
		sysInfoPane.add(osName);
		sysInfoPane.add(javaVersion);
		sysInfoPane.add(totalMemory);
		sysInfoPane.add(freeMemory);

		JPanel OKPane = new JPanel();
		okButton = new JButton("确定（O）", new ImageIcon(".\\images\\ok.gif"));
		okButton.setMnemonic('O'); // 设置快捷键为"Alt + O"
		/* 以下代码是设置案钮的Rollover图象 */
		Icon rollover = new ImageIcon(".\\images\\exit.gif");
		Icon general = new ImageIcon(".\\images\\ok.gif");
		Icon press = new ImageIcon(".\\images\\help.gif");

		okButton.setRolloverEnabled(true);
		okButton.setIcon(general); // 设置离开时的图象
		okButton.setRolloverIcon(rollover); // 设置在按纽上时的图象
		okButton.setPressedIcon(press); // 设置在按下按纽时的图象
		this.getRootPane().setDefaultButton(okButton); // 设置该按钮为该对话框的默认的按钮?.

		okButton.addActionListener(this);
		OKPane.add(okButton);
		contentPanel.add("South", OKPane);

		setContentPane(contentPanel);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation((Login.loginFrame.getWidth() - this.getSize().width) / 2,
				(Login.loginFrame.getHeight() - this.getSize().height) / 2);
		this.setResizable(false);
		myTimer = new javax.swing.Timer(1000, this);
		myTimer.start();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==okButton)
		{
			dispose();
		}else{
			Counter++ ;
			this.setTitle("当前的定时器的值为:"+Counter+"秒");
		}
	}
}