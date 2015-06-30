package com.bfchuan.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.bfchuan.util.TaskUtil;

/**
 * 功能介绍对话框
 * @author Administrator
 *
 */
public class DownloaderIntroductionDialog extends JDialog{
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
	public DownloaderIntroductionDialog(MainFrame frame){
		super(frame,"迅杭功能介绍");
		this.frame = frame;
		init();
	}
	
	/**
	 * 初始化
	 */
	public void init(){
		/*获取该对话框的panel容器*/
		JPanel dialogPanel = (JPanel) this.getContentPane();
		Label labelLogo = new Label("功能介绍");
		
		//JPanel center = new JPanel();
		JTextArea textArea = new JTextArea();
		textArea.setText(TaskUtil.readFile("introduction.txt"));
		textArea.setEditable(false);
		JScrollPane jsp = new JScrollPane(textArea);
		
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
		
		dialogPanel.add(labelLogo,BorderLayout.NORTH);
		dialogPanel.add(jsp,BorderLayout.CENTER);
		dialogPanel.add(south,BorderLayout.SOUTH);
		
		/*设置对话框的其他属性*/
		this.setSize(600, 450);// 设置大小
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
