package com.bfchuan.entities;

import java.io.Serializable;

import com.bfchuan.downloader.PieceManager;
import com.bfchuan.util.Global;

/**
 * 任务的实体类
 * @author Administrator
 *
 */
public class Task implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 新建任务
	 */
	public static final int STATE_NEW = 1;
	/**
	 * 正在下载任务
	 */
	public static final int STATE_RUNNING = 2;
	/**
	 * 任务完成
	 */
	public static final int STATE_COMPLETED = 3;
	/**
	 * 任务暂停
	 */
	public static final int STATE_PAUSED = 4;
	/**
	 * 任务失败
	 */
	public static final int  STATE_FAILED = 5;
	/**
	 * 任务的执行状态，取值范围（1、2、3、4、5），分别对应（新建、下载、完成、暂停、失败）五个状态
	 */
	private int status;  
	/**
	 * 用户保存的文件名
	 */
	private String fileName;
	/**
	 * 资源文件的原始文件名
	 */
	private String formerFileName;
	/**
	 * 资源文件的总大小
	 */
	private long fileSize;
	/**
	 * 已下载的大小
	 */
	private long loadSize;
	/**
	 * 文件下载的进度
	 */
	private float progressRate;
	/**
	 * 文件下载的即时速度
	 */
	private float immediateSpeed;
	/**
	 * 文件下载的平均速度
	 */
	private float averageSpeet;
	/**
	 * 下载文件的开始时间
	 */
	private long beginTime;
	/**
	 * 下载文件的结束时间
	 */
	private long endTime;
	/**
	 * 下载文件花费的时间
	 */
	private long takesTime;
	/**
	 * 下载文件的保存路径
	 */
	private String savePath;
	/**
	 * 下载文件的资源URL
	 */
	private String sourceUrl;
	/**
	 * 下载文件的线程数
	 */
	private int threadAmount;
	/**
	 * 文件的分块管理者
	 */
	private PieceManager pm;
	
	//--------------------以下是相应的get、set方法---------------------
	/**
	 * 获取任务状态
	 */
	public int getStatus() {
		return status;
	}
	/**
	 * 设置任务状态
	 * @param status
	 */
	public void setStatus(int status) {
		this.status = status;
	}
	 /**
	  * 获取任务的文件名
	  * @return
	  */
	public String getFileName() {
		return fileName;
	}
	/**
	 * 设置任务的文件名
	 * @param fileName
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	/**
	 * 获取任务的原文件名
	 * @return
	 */
	public String getFormerFileName() {
		return formerFileName;
	}
	/**
	 * 设置任务的原文件名
	 * @param formerFileName
	 */
	public void setFormerFileName(String formerFileName) {
		this.formerFileName = formerFileName;
	}
	/**
	 * 获取任务的文件长度
	 * @return
	 */
	public long getFileSize() {
		return fileSize;
	}
	/**
	 * 设置任务的文件长度
	 * @param fileSize
	 */
	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}
	/**
	 *  获取任务下载进度
	 * @return
	 */
	public float getProgressRate() {
		return progressRate;
	}
	/**
	 * 设置任务下载进度
	 * @param progressRate
	 */
	public void setProgressRate(float progressRate) {
		this.progressRate = progressRate;
	}
	/**
	 * 获得即时下载速度
	 * @return
	 */
	public float getImmediateSpeed() {
		return immediateSpeed;
	}
	/**
	 * 设置即时下载速度
	 * @param immediateSpeed
	 */
	public void setImmediateSpeed(float immediateSpeed) {
		this.immediateSpeed = immediateSpeed;
	}
	/**
	 * 获取下载平均速度
	 * @return
	 */
	public float getAverageSpeet() {
		return averageSpeet;
	}
	/**
	 * 设置任务平均下载速度
	 * @param averageSpeet
	 */
	public void setAverageSpeet(float averageSpeet) {
		this.averageSpeet = averageSpeet;
	}
	/**
	 * 获取任务开始时间
	 * @return
	 */
	public long getBeginTime() {
		return beginTime;
	}
	/**
	 * 设置任务开始时间
	 * @param beginTime
	 */
	public void setBeginTime(long beginTime) {
		this.beginTime = beginTime;
	}
	/**
	 * 获取任务结束时间
	 * @return
	 */
	public long getEndTime() {
		return endTime;
	}
	/**
	 * 设置任务结束时间
	 * @param endTime
	 */
	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}
	/**
	 * 获取任务花去时间
	 * @return
	 */
	public long getTakesTime() {
		return takesTime;
	}
	/**
	 * 设置任务花去时间
	 * @param takesTime
	 */
	public void setTakesTime(long takesTime) {
		this.takesTime = takesTime;
	}
	/**
	 * 获取任务文件保存路径
	 * @return
	 */
	public String getSavePath() {
		return savePath;
	}
	/**
	 * 设置任务文件保存路径
	 * @param savePath
	 */
	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}
	/**
	 * 获取任务的URL
	 * @return
	 */
	public String getSourceUrl() {
		return sourceUrl;
	}
	/**
	 * 设置任务的URL
	 */
	public void setSourceUrl(String sourceUrl) {
		this.sourceUrl = sourceUrl;
	}
	/**
	 * 获取下载该任务的线程总数
	 * @return
	 */
	public int getThreadAmount() {
		return threadAmount;
	}
	/**
	 * 设置下载该任务的线程总数
	 * @param threadAmount
	 */
	public void setThreadAmount(int threadAmount) {
		this.threadAmount = threadAmount;
	}
	/**
	 * 获取分块下载的管理者
	 * @return
	 */
	public PieceManager getPm() {
		return pm;
	}
	/**
	 * 设置分块下载的管理者
	 * @param pm
	 */
	public void setPm(PieceManager pm) {
		this.pm = pm;
	}
	/**
	 * 获取已下载文件的大小
	 * @return
	 */
	public long getLoadSize() {
		return loadSize;
	}
	/**
	 * 设置已下载文件的大小
	 * @param loadSize
	 */
	public void setLoadSize(long loadSize) {
		this.loadSize = loadSize;
	}
	/**
	 * 获得任务文件的完整路径，包括名字
	 * @return
	 */
	public String getFilePathName(){
		return this.savePath + Global.FILE_SEPARATOR + this.fileName;
	}
}
