package com.xcomm.mina.file.util;

/**
 * 客户端传送文件信息类
 * 作者：Leonidas 
 * piaobomengxiang@163.com
 * 时间：2013-9-14
 * 版本：1.0
 * 描述：客户端通过Mina框架上传文件
 * 文件通过流化后传送
 * 该类保存文件信息
 */
public class SendFromBaseMessage {
  private int dataType;		//作为业务判断依据
  private Object data;		//存储业务数据
  
  public SendFromBaseMessage(int dataType,Object data){
	  this.dataType = dataType;
	  this.data = data;
  }
  public SendFromBaseMessage(){
	  
  }
public int getDataType() {
	return dataType;
}
public void setDataType(int dataType) {
	this.dataType = dataType;
}
public Object getData() {
	return data;
}
public void setData(Object data) {
	this.data = data;
}
  
  
  
}
