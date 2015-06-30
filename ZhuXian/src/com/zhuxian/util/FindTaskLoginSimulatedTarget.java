package com.zhuxian.util;


/**
 * 登录进度条进度控制
 * 作者：Leonidas 
 * piaobomengxiang@163.com
 * 时间：2013-9-14
 * 版本：1.0
 * 描述：登录进度条进度控制，在此做登录成功与否处理
 */
public class FindTaskLoginSimulatedTarget implements Runnable{


	//任务的当前完成量
	private volatile int current;
	//总任务量
	private int amount;
	
	public FindTaskLoginSimulatedTarget(int amount)
	{  
		current = 0;
		this.amount = amount;
	}

	public int getAmount()
	{  
	  return amount;
	}

	public int getCurrent()
	{  
		return current;
	}
	//run方法代表不断完成任务的过程
	public void run()
	{  

		while (current < amount)
		{ 
			try
			{
				Thread.sleep(50);
			}
			catch(InterruptedException e)
			{

			}
			current++;
		}
	}
}