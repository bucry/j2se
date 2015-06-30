package com.bfchuan.runnable;

public class AA extends Thread {
	private static int x;

	public synchronized void doThings() {
		int current = x;
		current++;
		x = current;
	}

	public void run() {
		doThings();
	}
}
