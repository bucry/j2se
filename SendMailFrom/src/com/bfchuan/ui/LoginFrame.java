package com.bfchuan.ui;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.jvnet.substance.skin.SubstanceGreenMagicLookAndFeel;


/**
 * 登录界面
 * 作者：Leonidas 
 * piaobomengxiang@163.com
 * 时间：2013-9-13
 * 版本：1.0
 * 描述：登录主界面，该登录界面为TT移植过来
 * 仅作为练习，尚未做修改
 * 因为仅为测试。
 * 登录用户名无需输入@以及后面的东西
 */
public class LoginFrame extends JFrame implements ActionListener{
	
	private static LoginFrame loginFrame = null;
	private static final long serialVersionUID = -2302380191880949014L;
	
	static {
		JFrame.setDefaultLookAndFeelDecorated(true);
		JDialog.setDefaultLookAndFeelDecorated(true);
		try {
			UIManager.setLookAndFeel(new SubstanceGreenMagicLookAndFeel());
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	public JComboBox<Object> iconList = new JComboBox<Object>(
			new Integer[]{1, 2, 3, 4, 5 , 6, 7, 8 ,9 ,10});
	
	
	private FindChatSimThread activity;
	private Container contentPane;
	private JProgressBar jpb;
	private JPanel paneTop = new JPanel();
	private JPanel paneMid = new JPanel();
	private JPanel paneBut = new JPanel();
	private JLabel lblName = new JLabel();
	private JLabel lblPwd = new JLabel();
	private JLabel lblApply = new JLabel();
	private JLabel lblForget = new JLabel();
	private JLabel lblModel = new JLabel();
	private Timer activityMonitor;
	private JTextField txtName = new JTextField(15);
	private JPasswordField txtPwd = new JPasswordField(15);

	private JComboBox<String> cmb = new JComboBox<String> ();
	private JCheckBox chk = new JCheckBox();

	private JButton btnKill = new JButton("查杀木马");
	private JButton btnSet = new JButton("设置");
	private JButton btnLogin = new JButton("登录");

	
	public static LoginFrame  getInstance(){
		if(loginFrame == null){
			return new LoginFrame();
		}else{
			return loginFrame;
		}
	}
	
	
	
	public LoginFrame() {
		//修改LOGO
		Toolkit tk=Toolkit.getDefaultToolkit();
		Image image=tk.createImage("/home/java/Work/Communication/icon/icon.gif");
		this.setIconImage(image);
		
		btnKill.addActionListener(this);
		btnLogin.addActionListener(this);
		btnSet.addActionListener(this);
		
		//在此执行检查，根据后缀名或者进程
		//将后缀作为标本上传到服务器进行MD5码检验
		//即是云查杀
		//在此为了简化
		//省略
		activityMonitor = new Timer(100, new ActionListener() {// 每0.5秒执行一次
					public void actionPerformed(ActionEvent e) {// 以下动作将在事件调度线程中运行，十分安全
						int current = activity.getCurrent();// 得到线程的当前进度

						jpb.setValue(current);// 更新进度栏的值

						if (current == activity.getTarget()) {// 如果到达目标值
							JOptionPane.showMessageDialog(null,"您的系统很安全");
							activityMonitor.stop();// 终止定时器
							btnKill.setEnabled(true);// 激活按钮
						}
					}
				});

		jpb = new JProgressBar();
		jpb.setStringPainted(true);
		lblName.setText("邮箱帐号:");
		lblApply.setText("申请邮箱");
		lblPwd.setText("邮箱密码:");
		lblForget.setText("忘记密码 ?");
		lblModel.setText("状态:");

		String[] s1 = { "隐身", "在线", "忙碌" };
		cmb.addItem(s1[0]);
		cmb.addItem(s1[1]);
		cmb.addItem(s1[2]);

		chk.setText("自动登录");
		paneMid.add(lblName);
		paneMid.add(txtName);
		paneMid.add(lblApply);
		paneMid.add(lblPwd);
		paneMid.add(txtPwd);
		paneMid.add(lblForget);
		paneMid.add(chk);
		paneMid.add(lblModel);
		paneMid.add(cmb);
		paneMid.add(jpb);

		paneBut.add(btnKill);
		paneBut.add(btnSet);
		paneBut.add(btnLogin);

		contentPane = this.getContentPane();

		contentPane.add(paneTop, BorderLayout.NORTH);
		contentPane.add(paneMid, BorderLayout.CENTER);
		contentPane.add(paneBut, BorderLayout.SOUTH);

		setTitle("TT邮箱2013登录");
		setSize(330, 200);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((screen.width - getSize().width) / 2,
				(screen.height - getSize().height) / 2);
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public static void main(String args[]) {
		LoginFrame.getInstance();
	}

	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if(obj == btnKill){
				jpb.setVisible(true);
				jpb.setMaximum(1000);// 设置进度栏的最大值
				activity = new FindChatSimThread(1000);
				activity.start();// 启动线程
				activityMonitor.start();// 启动定时器
				btnKill.setEnabled(false);// 禁止按钮
		}
		
		if(obj == btnSet){
			
		}
		if(obj == btnLogin){
			MainFrame.tbUser.setName(txtName.getText().trim());
			MainFrame.tbUser.setPass(txtPwd.getText().trim());
			this.dispose();
			new MainFrame().getMail();;
		}
	}
}
