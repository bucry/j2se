package com.xcomm.mina.file.util;


import java.nio.charset.Charset;


/**
 * 客户端传送文件常量类
 * 作者：Leonidas 
 * piaobomengxiang@163.com
 * 时间：2013-9-14
 * 版本：1.0
 * 描述：客户端通过Mina框架上传文件
 * 文件通过流化后传送
 * 该类用于存储常量
 */
public class SendFromBeanUtil {
	/**
	 * 字符串编码
	 * */
  public final static  Charset charset = Charset.forName("utf-8");
  public final static int UPLOAD_FILE = 1;	//传送文件
}
