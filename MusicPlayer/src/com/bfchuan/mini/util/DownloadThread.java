package com.bfchuan.mini.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;


import com.bfchuan.mini.bo.ConfigBo;
import com.bfchuan.mini.bo.DownloadBo;
import com.bfchuan.mini.entity.TaskModel;

public class DownloadThread extends Thread {

	private TaskModel task;
	private File dFile;
	private boolean run = true;
	private long originalPos;
	
	public DownloadThread(TaskModel task) {
		this.task = task;
		try {
			File parent = new File(ConfigBo.getInstance().getNetMusicDownloadFolder());//音乐下载的存放文件夹
			if (!parent.exists()) {
				parent.mkdirs();
			}
			dFile = new File(parent, task.getSongName() + ".mp3");
			if (!dFile.exists()) {
				dFile.createNewFile();
			}
			originalPos = dFile.length();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setRun(boolean run) {
		this.run = run;
	}
	
	private void writeFile(File file, InputStream ins) {
		OutputStream bos = null;
		try {
			bos = new FileOutputStream(file, true);
			int bytesRead = 0;
			int c = 0;
			byte[] buffer = new byte[1024];
			while ((bytesRead = ins.read(buffer, 0, 1024)) != -1 && run) {
				bos.write(buffer, 0, bytesRead);
				c += bytesRead;
				//更新任务进度...
				task.setCurPos(c + originalPos);
				DownloadBo.getInstance().updateTask(task);
			}
		} catch (Exception e) {
		} finally {
			try {
				bos.flush();
				bos.close();
			} catch (Exception e) { 
			} finally {
				try {
					ins.close();
				} catch (Exception e) {
				}
			}
		}
		//下载完成
		if (file.length() == task.getTotalSize()) {
			DownloadBo.getInstance().removeTask(this);
		}
	}
	
	public TaskModel getTaskModel() {
		return this.task;
	}

	@Override
	public void run() {
		try {
	    	URL url = task.getURL();
	    	URLConnection con = url.openConnection();
	    	con.connect();
	    	task.setTotalSize(con.getContentLength());
	    	if (originalPos == task.getTotalSize()) {//如果这个文件已经下载完成，则不用再次下载
	    		DownloadBo.getInstance().removeTask(this);
	    		return;
	    	}
	    	HttpURLConnection hcon = (HttpURLConnection)url.openConnection();
	    	if (originalPos != 0) {//说明任务还没完成
	    		hcon.setRequestProperty("Range", "bytes=" + originalPos + "-");
	    	}
	    	BufferedInputStream bis = new BufferedInputStream(hcon.getInputStream());
	    	writeFile(dFile, bis);
	    	hcon.disconnect();
		} catch (Exception e) {
			//这里网络断开，应该做相应处理
			e.printStackTrace();
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof DownloadThread)) {
			return false;
		}
		DownloadThread dt = (DownloadThread)obj;
		if (dt.getTaskModel().equals(this.getTaskModel())) {
			return true;
		}
		return false;
	}
	
}
