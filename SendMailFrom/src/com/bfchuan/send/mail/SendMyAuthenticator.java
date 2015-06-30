package com.bfchuan.send.mail;
import javax.mail.*;
  


/** 
* 发送邮件需要使用的基本信息 
* 作者:Leonidas
* 时间:2010-2-4
* 版本:1.0
* 描述:发送邮件需要使用的基本信息 
* piaobomengxiang@163.com
*/ 
public class SendMyAuthenticator extends Authenticator{
	String userName=null;
	String password=null;
	 
	public SendMyAuthenticator(){
	}
	public SendMyAuthenticator(String username, String password) { 
		this.userName = username; 
		this.password = password; 
	} 
	@Override
	protected PasswordAuthentication getPasswordAuthentication(){
		return new PasswordAuthentication(userName, password);
	}
}
 
