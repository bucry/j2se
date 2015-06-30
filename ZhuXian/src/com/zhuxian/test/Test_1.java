package com.zhuxian.test;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class Test_1 extends JFrame {
	JLabel label;
	Move move;

	int x = 10, y = 10;

	public Test_1() {
		setBounds(100, 100, 300, 200);
		setLayout(null);
		label = new JLabel("Welcome to MyPage");
		label.setBounds(x, y, 150, 50);
		add(label);
		move = new Move();
		move.thread.start();
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public static void main(String[] args) {
		new Test_1();
	}

	class Move implements Runnable {
		Thread thread;

		public Move() {
			thread = new Thread(this);
		}

		public void run() {
			while (true) {
				x = x + 5;
				if (x > 150)
					x = 10;
				label.setLocation(x, y);
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
	}

}