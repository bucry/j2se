package com.xcomm.mina.file.util;


/**
 * 客户端传送文件实体POJI类
 * 作者：Leonidas 
 * piaobomengxiang@163.com
 * 时间：2013-9-14
 * 版本：1.0
 * 描述：客户端通过Mina框架上传文件
 * 文件通过流化后传送
 * 该类用于封装文件类
 * 该类仅仅作为测试使用，非正式使用
 */
public class SendFromFileBean {
 private int fileSize;		//文件大小
 private String fileName;	//文件名称
 private byte[] fileContent;//文件byte数组

 
 
public int getFileSize() {
	return fileSize;
}
public void setFileSize(int fileSize) {
	this.fileSize = fileSize;
}
public String getFileName() {
	return fileName;
}
public void setFileName(String fileName) {
	this.fileName = fileName;
}
public byte[] getFileContent() {
	return fileContent;
}
public void setFileContent(byte[] fileContent) {
	this.fileContent = fileContent;
}
 
 
}
