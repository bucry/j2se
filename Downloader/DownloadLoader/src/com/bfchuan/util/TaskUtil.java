package com.bfchuan.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.bfchuan.controller.TaskController;
import com.bfchuan.entities.Task;
import com.bfchuan.entities.TaskShowBean;


/**
 * 任务的工具类，提供各种静态方法
 * @author Administrator
 *
 */
public class TaskUtil {
	
	/**
	 * 根据url获得文件大小
	 * @param urlStr
	 * @return
	 * @throws IOException
	 */
	public static long getFileSize(String urlStr) throws IOException{
		/*获得URL类*/
		URL  url = new URL(urlStr);
		/*获得连接*/
		URLConnection urlConnection = url.openConnection();// 打开连接
		/*连接*/
		urlConnection.connect();
		System.out.println (urlConnection.getContentLength());
		return urlConnection.getContentLength();
	}
	
	/**
	 * 获取资源文件的原文件名
	 * @param urlStr
	 * @return
	 * @throws IOException
	 */
	public static String getRealFileName(String urlStr) throws IOException{
		/*获得URL类*/
		URL  url = new URL(urlStr);
		/*获得连接*/
		URLConnection urlConnection = url.openConnection();// 打开连接
		/*连接*/
		urlConnection.connect();
		System.out.println(url.getFile());
		return url.getFile();	
	}
	
	/**
	 * 获得传来文件名的后缀
	 * @param fileName
	 */
	public static String getFilePostfix(String fileName){
		String postfix = fileName.split("\\.")[fileName.split("\\.").length - 1];
		System.out.println(postfix);
		return postfix;
	}
	
	/**
	 * 保存任务对象到相应文件夹,此方法会自行判断task的状态，以便存放到不同的文件夹
	 * @param task
	 * @param filePath
	 */
	public static void saveTasktoFile(Task task){
		try {
			String fileName = task.getFileName() + "." + Global.TASK_OBJECT_POSTFIX;
			String filePath;
			if(task.getStatus() != Task.STATE_COMPLETED ){//未完成
				filePath = Global.RUNNING_TASK_PATH;
			}else{//完成
				filePath = Global.COMPLETE_TASK_PATH;
			}
			String fullName = filePath + Global.FILE_SEPARATOR + fileName;
			File fileDat = new File(fullName);
			ObjectOutputStream oos = new ObjectOutputStream(
					new FileOutputStream(fileDat));
			oos.writeObject(task);
			oos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 保存任务控制器对象到相应文件夹,此方法会自行判断task的状态，以便存放到不同的文件夹
	 * @param taskController
	 */
	public static void saveTaskControllerToFile(TaskController taskController){
		System.out.println("saveTaskControllerToFile");
		try {
			String fileName = taskController.getTask().getFileName() + "." + Global.TASK_OBJECT_POSTFIX;
			String filePath;
			if(taskController.getTask().getStatus() != Task.STATE_COMPLETED ){//未完成
				filePath = Global.RUNNING_TASK_PATH;
			}else{//完成
				filePath = Global.COMPLETE_TASK_PATH;
			}
			String fullName = filePath + Global.FILE_SEPARATOR + fileName;
			File fileDat = new File(fullName);
			ObjectOutputStream oos = new ObjectOutputStream(
					new FileOutputStream(fileDat));
			oos.writeObject(taskController);
			oos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 将指定路径下的文件反序列化为TaskController，存放到map中
	 */
	public static Map<TaskController, Thread> loadTaskControllersFromFile(String filePath){
		System.out.println("loadTaskControllersFromFile");
		Map<TaskController,Thread> ctrlThdMap = new HashMap<TaskController, Thread>();
		
		/*获取该路径中的所有文件*/
		File previousFiles = new File(filePath);
		String[] previousFilesArray = previousFiles.list();
		
		/*遍历，逐个反序列化*/
		for(String previousFile : previousFilesArray){
			TaskController taskController;
			try {
				taskController = loadTaskController(filePath + Global.FILE_SEPARATOR + previousFile );
			} catch (IOException e) {
				taskController = null;
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				taskController = null;
				e.printStackTrace();
			}
			if(taskController != null){
				ctrlThdMap.put(taskController, null);
			}
		}
		return ctrlThdMap;
	}
	
	/**
	 * 单个文件反序列化为TaskController
	 * @param fullPathName
	 * @return
	 * @throws ClassNotFoundException 
	 * @throws IOException 
	 */
	public static TaskController loadTaskController(String fullPathName) throws IOException, ClassNotFoundException{
		System.out.println("loadTaskController");
		TaskController taskController;
		FileInputStream fis = new FileInputStream(fullPathName);
		ObjectInputStream ois = new ObjectInputStream(fis);
		taskController = (TaskController) ois.readObject();
		
		/*保存在文件中的任务不可能是running，running很有可能是因为非法关闭引起的，所以进行以下处理*/
		if(taskController.getTask().getStatus() == Task.STATE_RUNNING){
			taskController.getTask().setStatus(Task.STATE_PAUSED);
		}
		fis.close();
		ois.close();
		return taskController;
		
	}
	
	/**
	 * 将任务类信息转化为在table上显示的信息，封装到TaskShowBean中
	 * @param task
	 * @return
	 */
	public static TaskShowBean TaskToShowTask(Task task){
		TaskShowBean tsb = new TaskShowBean();
		DecimalFormat   fnum   =   new   DecimalFormat("##0.00");          
		String temp = null; //临时字符串
		/*任务状态*/
		switch (task.getStatus()){
			case Task.STATE_NEW :
				temp = "新建";
				break;
			case Task.STATE_RUNNING:
				temp = "下载中…";
				break;
			case Task.STATE_PAUSED:
				temp = "暂停";
				break;
			case Task.STATE_COMPLETED:
				temp = "完成";
				break;
			case Task.STATE_FAILED:
				temp = "失败";
				break;
		}
		tsb.setStatus(temp);
		
		/*文件名*/
		tsb.setFileName(task.getFileName());
		
		/*文件大小*/
		String fileSize = fileSizeToStr(task.getFileSize());
		String loadSize = fileSizeToStr(task.getLoadSize());
		tsb.setFileSize(fileSize + "/" + loadSize);
		
		/*进度*/
		float progressRate = task.getProgressRate();
		if(progressRate == 1){
			tsb.setProgressRate("100%");
		}else{
			tsb.setProgressRate(fnum.format(progressRate*100) + "%");
		}
		
		/*速度*/
		tsb.setSpeed(fnum.format(task.getAverageSpeet()) + "KB/s");
		
		/*开始时间*/
		SimpleDateFormat sf = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒 ");
		Date sdate = new Date(task.getBeginTime());
		tsb.setBeginTime(sf.format(sdate));
		
		/*结束时间*/
		if(task.getStatus() == Task.STATE_COMPLETED){
			if(task.getEndTime() != 0){
				Date edate = new Date(task.getEndTime());
				tsb.setEndTime(sf.format(edate));
			}
		}else{
			tsb.setEndTime("未完成");
		}
		
		/*用去时间*/
		tsb.setTakesTime(task.getTakesTime()/1000 + "s");
		
		/*保存路径*/
		tsb.setSavePath(task.getSavePath());
		
		/*url*/
		tsb.setUrl(task.getSourceUrl());
		
		/*线程数*/
		tsb.setThreadSum(String.valueOf(task.getThreadAmount()));
		
		return tsb;
	}
	
	/**
	 * 文件大小转换成相应的字符串显示形式
	 * @param fileSize
	 * @return
	 */
	public static String fileSizeToStr(long fileSize){
		DecimalFormat fnum = new DecimalFormat("##0.00");   
		String temp = null;
		if(fileSize/1024 < 1){
			temp = fileSize + "B";
		}else if(fileSize/1024 < 1024){
			temp = fileSize/1024 + "KB";
		}else if(fileSize/(1024*1024) < 1024){
			temp = fnum.format((float)fileSize/(1024*1024)) + "M";
		}else{
			temp = fnum.format((float)fileSize/(1024*1024*1024)) + "G";
		}
		return temp;
	}
	
	/**
	 * 删除文件
	 */
	public static boolean deleteFile(String fullPathName){
		File file = new File(fullPathName);
		if(file.exists()){
			return file.delete();
		}
		return false;
	}
	
	/**
	 * 将源文件移动到指定的路径中
	 * @param srcFile
	 * @param destPath
	 * @return
	 */
	 public static boolean moveFile(String srcFile, String destPath){
	        // File (or directory) to be moved
	        File file = new File(srcFile);
	        
	        // Destination directory
	        File dir = new File(destPath);
	        
	        // Move file to new directory
	        boolean success = file.renameTo(new File(dir, file.getName()));
	        
	        return success;
	    }
	 
	 /**
	  * 判断任务管理器管理的任务是不是在垃圾箱
	  * @param taskController
	  * @return
	  */
	 public static boolean isTaskGarbage(TaskController taskController){
		 boolean b = false;
		 return b;
	 }
	 
	 /**
	  * 清空垃圾箱中的临时文件
	  */
	 public static void deleteAllGarbageFile(){
		 String garbagePath = Global.GARBAGE_TASK_PATH;
		 String[] tempFiles = new File(garbagePath).list();
		 for(String fileName : tempFiles){
			 String fullName = garbagePath + Global.FILE_SEPARATOR + fileName;
			 File file = new File(fullName);
			 file.delete();
		 }
	 }
	 
	 /**
	  * 打开指定路径下的文件
	  * @param fullPathName
	 * @throws IOException 
	  */
	 public static void openFile(String fullPathName) throws IOException{
		
		 Runtime.getRuntime().exec("cmd /c " + fullPathName);
		// Runtime.getRuntime().exec("start " + fullPathName);
	 }
	 
	 /**
	  * 打开指定的文件夹
	  * @param filePath
	  * @throws IOException
	  */
	 public static void openFolder(String filePath) throws IOException{
		 Runtime.getRuntime().exec("explorer.exe /n, "+ filePath );
	 }
	 
	 /**
	  * 读取文本内容，返回字符串
	  * @param fileName
	  * @return
	  */
	 public static String readFile(String fileName){
		 StringBuilder sb = new StringBuilder();
			try {
				FileReader fis = new FileReader(new File(fileName));
				BufferedReader br = new BufferedReader(fis);
				String tempStr;
				try {
					while((tempStr = br.readLine()) != null){
						sb.append(tempStr);
						sb.append("\n");
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			return sb.toString();
	 }
	 
	 public static void main(String[] args){
		 System.out.println(TaskUtil.readFile("introduction.txt"));
		 try {
			openFolder("F:\\home\\");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }
}
