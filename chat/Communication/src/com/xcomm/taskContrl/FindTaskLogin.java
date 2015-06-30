package com.xcomm.taskContrl;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ProgressMonitor;
import javax.swing.Timer;

import com.xomm.Login.LoginFrame;
import com.xomm.comm.FindChatFrameCont;


/**
 * 登录进度条
 * 作者：Leonidas 
 * piaobomengxiang@163.com
 * 时间：2013-9-14
 * 版本：1.0
 * 描述：登录进度条
 */

public class FindTaskLogin
{
	Timer timer;
	public void init()
	{
		final FindTaskLoginSimulatedTarget target = new FindTaskLoginSimulatedTarget(100);
		//以启动一条线程的方式来执行一个耗时的任务
		final Thread targetThread = new Thread(target);
		targetThread.start();
		//创建进度对话框
		final ProgressMonitor dialog = new ProgressMonitor(null ,
			"正在登录:" , "请稍后：" , 0 , target.getAmount());
		//创建一个计时器
		timer = new Timer(300 , new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				//以任务的当前完成量设置进度对话框的完成比例
				dialog.setProgress(target.getCurrent());
				if(target.getCurrent() >= 100){
					new FindChatFrameCont();
					//停止计时器
					timer.stop();
					//中断任务的执行线程
					targetThread.interrupt();
				}
				
				//如果用户单击了进度对话框的”取消“按钮
				if (dialog.isCanceled())
				{
					//停止计时器
					timer.stop();
					//中断任务的执行线程
					targetThread.interrupt();
					//系统退出
					LoginFrame.getInstance();
				}
			}
		});
		timer.start();
	}
}