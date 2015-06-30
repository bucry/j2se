package com.bfchuan.runnable;

import java.util.concurrent.CountDownLatch;

public class A extends Thread{
	private CountDownLatch threadsSignal;

	public A(CountDownLatch threadsSignal) {
		this.threadsSignal = threadsSignal;
	}
	
	@Override
	public void run() {
		System.out.println(Thread.currentThread().getName() + "开始...");
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		threadsSignal.countDown();
		System.out.println(Thread.currentThread().getName() + "结束. 还有"+ threadsSignal.getCount() + " 个线程");
	}

	
	public static void main(String[] args) throws InterruptedException {
	    long tStart = System.currentTimeMillis();
	    CountDownLatch threadSignal = new CountDownLatch(10);
	    for (int ii = 0; ii < 10; ii++) {
	    	new Thread(new A(threadSignal)).start();
	    }
	    threadSignal.await();
	    
	    System.out.println(Thread.currentThread().getName() + "结束.");
	    long tEnd = System.currentTimeMillis();
	    System.out.println("总共用时:"+ (tEnd - tStart) + "millions");
	    System.out.println("close pool....");
	    
	}
}

