package com.bfchuan.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * 关于myDownloader的对话框
 * @author Administrator
 *
 */
public class DownloaderAboutDialog extends JDialog{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 此对话框依附的Frame
	 */
	MainFrame frame;
	
	/**
	 * 构造器，参数是该对话框依附的组件
	 * @param frame
	 */
	public DownloaderAboutDialog(MainFrame frame){
		super(frame,"版本信息");
		this.frame = frame;
		init();
	}
	
	/**
	 * 初始化
	 */
	public void init(){
		/*获取该对话框的panel容器*/
		JPanel dialogPanel = (JPanel) this.getContentPane();
		Label labelLogo = new Label();
		labelLogo.setText("LOGO");
		
		JPanel center = new JPanel(new GridLayout(3,1,0,5));
		center.add(new JLabel("版本信息 ：迅杭4.0"));
		center.add(new JLabel("作者     ：Leonidas"));
		center.add(new JLabel("时间     ：2010年2月"));
	//	center.setPreferredSize(new Dimension(20,20));
		
		JPanel south = new JPanel();
		JButton btn = new JButton("确定");
		btn.addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent arg0) {
						cancel();
					}
				}
				
		); 
		south.add(btn);
		
		dialogPanel.add(labelLogo,BorderLayout.CENTER);
		dialogPanel.add(center,BorderLayout.NORTH);
		dialogPanel.add(south,BorderLayout.SOUTH);
		
		/*设置对话框的其他属性*/
		this.setSize(300, 250);// 设置大小
		this.setResizable(false); // 不可调整大小
		this.setLocationRelativeTo(this);// 设置此窗口相对于指定组件的位置
		dialogPanel.setBackground(new Color(203, 223, 245));
		
		this.setVisible(true);
	}
	
	/**
	 * 取消，对话框消失
	 */
	public void cancel(){
		this.dialogDispose();
	}
	
	 /**
	  * dialog消失
	  */
	public void dialogDispose(){
		/*dialog消失*/
		this.dispose();
	}
}
