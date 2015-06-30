package com.zhuxian.util;


import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class TimerTest extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    private static JButton button;
    private static TimerTest TimerTest;

	public static void main(String[] args) {
		TimerTest = new TimerTest();
		button = new JButton("按我");
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
			   TimeDialog d = new TimeDialog();
			   int result = d.showDialog(TimerTest, "对方想要和你语音是否接受?", 10);// TimerTest是程序主窗口类，弹出的对话框10秒后消失
				System.out.println("===result: "+result);
			}
		});
		
		button.setBounds(2, 5, 80,20);
		TimerTest.getContentPane().setLayout(null);
		TimerTest.getContentPane().add(button);
		TimerTest.setSize(new Dimension(400,200));
		TimerTest.setLocation(500,200);
		TimerTest.setVisible(true);
		TimerTest.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

}
