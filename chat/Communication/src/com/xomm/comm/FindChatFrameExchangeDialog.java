package com.xomm.comm;


import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetSocketAddress;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.xcomm.entity.TbUserTable;

/**
 * 交谈的对话框
 * 作者：Leonidas 
 * piaobomengxiang@163.com
 * 时间：2013-9-13
 * 版本：1.0
 * 描述：该类用于定义交谈的对话框
 */

public class FindChatFrameExchangeDialog extends JDialog
{
	private static final long serialVersionUID = -4027954913094209420L;
	//聊天信息区
	JTextArea msgArea = new JTextArea(12 , 45);
	//聊天输入区
	JTextField chatField = new JTextField(30);
	//发送聊天信息的按钮
	JButton sendBn = new JButton("发送");
	//该交谈窗口对应的用户
	TbUserTable user;
	//构造器，用于初始化交谈对话框的界面
	
	
	public FindChatFrameExchangeDialog(Frame parent , TbUserTable user)
	{
		super(parent , "和" + user.getNickname() + "聊天中" , false);
		this.user = user;
		msgArea.setEditable(false);
		add(new JScrollPane(msgArea));
		JPanel buttom = new JPanel();
		//buttom.add(new JLabel("输入信息："));
		ImageIcon img = new ImageIcon("ico/0001.png");
		JButton expression = new JButton(img);
		buttom.add(expression);
		
		ImageIcon imgFont = new ImageIcon("ico/1111.png");
		JButton titleFont = new JButton(imgFont);
		buttom.add(titleFont);
		
		ImageIcon musicImg = new ImageIcon("ico/1122.png");
		JButton titleMusic = new JButton(musicImg);
		buttom.add(titleMusic);
		
		JButton expression1 = new JButton(img);
		buttom.add(expression1);
		JButton expression2 = new JButton(img);
		buttom.add(expression2);
		JButton expression3 = new JButton(img);
		buttom.add(expression3);
		JButton expression4 = new JButton(img);
		buttom.add(expression4);
		
		
		
		
		
		buttom.add(chatField);
		buttom.add(sendBn);
		
		add(buttom , BorderLayout.SOUTH);
		
		MyActionListener listener = new MyActionListener();
		chatField.addActionListener(listener);
		sendBn.addActionListener(listener);
		pack();
	}

	class MyActionListener implements ActionListener
	{
		public void actionPerformed(ActionEvent evt)
		{
			InetSocketAddress dest = (InetSocketAddress)user.getAddress();
			/*在聊友列表中，所有人项的SocketAddress是null*/
			//这表明是向所有人发送消息
			if (dest == null)
			{
				//LoginFrame.comUtil.broadCast(chatField.getText());
				msgArea.setText("您对大家说："
					+ chatField.getText() + "\n" + msgArea.getText());
			}
			//向私人发送信息
			else
			{
				//获取发送消息的目的
				dest = new InetSocketAddress(dest.getHostName(),
					dest.getPort() + 1);
				//LoginFrame.comUtil.sendSingle(chatField.getText(), dest);
				msgArea.setText("您对" + user.getNickname() +  "说："
					+ chatField.getText() + "\n" + msgArea.getText());

			}
			chatField.setText("");
		}
	}
	//定义向聊天区域添加消息的方法
	public void addString(String msg)
	{
		msgArea.setText(msg + "\n" + msgArea.getText());
	}
}
