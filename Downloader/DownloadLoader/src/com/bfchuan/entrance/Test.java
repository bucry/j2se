package com.bfchuan.entrance;

import com.bfchuan.controller.TaskController;
import com.bfchuan.entities.Task;

public class Test {

	public void test1(){
		Task task = new Task();
		task.setSourceUrl("http://file5.top100.cn/200910040830/CE926A956366FF9704D9BA1DF5C94771/Special_153622/%E6%98%A0%E5%B1%B1%E7%BA%A2.mp3");
		task.setFileName("yingshanhong.mp3");
		task.setSavePath("F:/");
		task.setStatus(Task.STATE_NEW);
		task.setThreadAmount(5);
		
		TaskController tc = new TaskController(task);
		new Thread(tc).start();
	}
}
