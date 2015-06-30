package com.bfchuan.entities;

/**
 * 显示与table之上的实体类，方便封装显示信息
 * @author Administrator
 *
 */
public class TaskShowBean {
	
	private String status;
	private String fileName;
	private String fileSize;
	private String progressRate;
	private String speed;
	private String beginTime;
	private String endTime;
	private String takesTime;
	private String savePath;
	private String url;
	private String threadSum;
	
	 /**
	  * 获取任务的显示状态
	  * @return
	  */
	public String getStatus() {
		return status;
	}
	/**
	 * 设置任务的显示状态
	 * @param status
	 */
	public void setStatus(String status) {
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
	 *  设置任务的文件名
	 * @param fileName
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	/**
	 * 获取任务的显示大小
	 * @return
	 */
	public String getFileSize() {
		return fileSize;
	}
	/**
	 * 设置任务的显示大小
	 * @param fileSize
	 */
	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}
	/**
	 * 获取任务的下载进度 “%”
	 * @return
	 */
	public String getProgressRate() {
		return progressRate;
	}
	/**
	 * 设置任务的下载进度
	 * @param progressRate
	 */
	public void setProgressRate(String progressRate) {
		this.progressRate = progressRate;
	}
	/**
	 * 获取任务的下载速度
	 * @return
	 */
	public String getSpeed() {
		return speed;
	}
	/**
	 * 设置任务的下载速度
	 * @param speed
	 */
	public void setSpeed(String speed) {
		this.speed = speed;
	}
	/**
	 *  获取任务的开始时间
	 * @return
	 */
	public String getBeginTime() {
		return beginTime;
	}
	/**
	 * 设置任务的开始时间
	 * @param beginTime
	 */
	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}
	/**
	 * 获取任务的下载耗时
	 * @return
	 */
	public String getTakesTime() {
		return takesTime;
	}
	/**
	 * 设置任务的下载耗时
	 * @param takesTime
	 */
	public void setTakesTime(String takesTime) {
		this.takesTime = takesTime;
	}
	/**
	 *  获取任务的保存路径
	 * @return
	 */
	public String getSavePath() {
		return savePath;
	}
	/**
	 * 设置任务的保存路径
	 * @param savePath
	 */
	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}
	/**
	 * 获取任务的URL
	 * @return
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * 设置任务的URL
	 * @param url
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	/**
	 * 获取下载任务的线程总数
	 * @return
	 */
	public String getThreadSum() {
		return threadSum;
	}
	/**
	 * 设置下载任务的线程总数
	 * @param threadSum
	 */
	public void setThreadSum(String threadSum) {
		this.threadSum = threadSum;
	}
	 /**
	  *  获得任务的结束时间
	  * @return
	  */
	public String getEndTime() {
		return endTime;
	}
	/**
	 * 设置热舞的结束时间
	 * @param endTime
	 */
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
}
