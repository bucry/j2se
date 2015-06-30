package com.bfchuan.send.mail;


/** 
* 测试发送邮件
* 作者:Leonidas
* piaobomengxiang@163.com
* 时间:2010-2-4
* 版本:1.0
* 描述:测试发送邮件 
* 如果你使用的发送邮箱是设置邮箱安全验证,如谷歌邮箱
* 则需要在
* 设置邮箱安全验证,如谷歌邮箱，
* 则需要在SendMailSenderInfo.java中设置
* p.put("mail.smtp.starttls.enable","true");
* 否则会报错：
* com.sun.mail.smtp.SMTPSendFailedException: 
* 530 5.7.0 Must issue a STARTTLS command first. h2sm9729708pbj.38 - gsmtp
* 
*/ 
public class Test {
	
	
	public static void main(String[] args){
        //这个类主要是设置邮件
		sendQQmail();
	}

	//发往QQ邮箱
	public static void sendQQmail(){
		 SendMailSenderInfo mailInfo = new SendMailSenderInfo(); 
		  mailInfo.setMailServerHost("smtp.qq.com"); 
		  mailInfo.setMailServerPort("25"); 
		  mailInfo.setValidate(true); 
		  mailInfo.setUserName("1242237807@qq.com"); //名称
		  mailInfo.setPassword("bfc389779");//您的邮箱密码 
		  
		  mailInfo.setFromAddress("1242237807@qq.com"); //哪个邮箱发
		  mailInfo.setToAddress("499199747@qq.com"); //发到哪儿
		  mailInfo.setSubject("设置邮箱标题"); 
		  mailInfo.setContent("设置邮箱内容"); 
	        //这个类主要来发送邮件
		  SendSimpleMailSender sms = new SendSimpleMailSender();
	         sms.sendTextMail(mailInfo);//发送文体格式 
	         sms.sendHtmlMail(mailInfo);//发送html格式
	}
	
	//发往网易邮箱
	public static void sendWy(){
		 SendMailSenderInfo mailInfo = new SendMailSenderInfo(); 
		  mailInfo.setMailServerHost("smtp.163.com"); 
		  mailInfo.setMailServerPort("25"); 
		  mailInfo.setValidate(true); 
		  mailInfo.setUserName("piaobomengxiang@163.com"); 
		  mailInfo.setPassword("bfc38977951");//您的邮箱密码 
		  mailInfo.setFromAddress("piaobomengxiang@163.com"); 
		  
		  
		  mailInfo.setToAddress("499199747@qq.com"); 
		  mailInfo.setSubject("设置邮箱标题"); 
		  mailInfo.setContent("设置邮箱内容"); 
	        //这个类主要来发送邮件
		  SendSimpleMailSender sms = new SendSimpleMailSender();
	         sms.sendTextMail(mailInfo);//发送文体格式 
	         sms.sendHtmlMail(mailInfo);//发送html格式
	}

	
	//发往HOTMAIL邮箱
	public static void hotmail(){
		 SendMailSenderInfo mailInfo = new SendMailSenderInfo(); 
		  mailInfo.setMailServerHost("smtp.live.com"); 
		  mailInfo.setMailServerPort("25"); 
		  mailInfo.setValidate(true); 
		  mailInfo.setUserName("piaobomengxiang@hotmail.com"); 
		  mailInfo.setPassword("bfc38977951");//您的邮箱密码 
		  mailInfo.setFromAddress("piaobomengxiang@hotmail.com"); 
		  
		  
		  mailInfo.setToAddress("499199747@qq.com"); 
		  mailInfo.setSubject("设置邮箱标题"); 
		  mailInfo.setContent("设置邮箱内容"); 
	        //这个类主要来发送邮件
		  SendSimpleMailSender sms = new SendSimpleMailSender();
	         sms.sendTextMail(mailInfo);//发送文体格式 
	         sms.sendHtmlMail(mailInfo);//发送html格式
	}
	
	//发往GMAIL邮箱
	public static void sendGmail(){
		 SendMailSenderInfo mailInfo = new SendMailSenderInfo(); 
		  mailInfo.setMailServerHost("smtp.gmail.com"); 
		  mailInfo.setMailServerPort("25"); 
		  mailInfo.setValidate(true); 
		  mailInfo.setUserName("piaobomengxiang@gmail.com"); 
		  mailInfo.setPassword("bfc38977951");//您的邮箱密码 
		  mailInfo.setFromAddress("piaobomengxiang@gmail.com"); 
		  
		  
		  mailInfo.setToAddress("499199747@qq.com"); 
		  mailInfo.setSubject("设置邮箱标题"); 
		  mailInfo.setContent("设置邮箱内容"); 
	        //这个类主要来发送邮件
		  SendSimpleMailSender sms = new SendSimpleMailSender();
	         sms.sendTextMail(mailInfo);//发送文体格式 
	         sms.sendHtmlMail(mailInfo);//发送html格式
	}
}
