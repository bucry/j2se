package com.xcomm.test;

import com.xcomm.entity.TbUserTable;

public class MinaSendDataEntityFromServer {
	public void login(byte[] buff){
		Loginr loginr = new Loginr();
		int contextLength = Integer.parseInt(LoUtils.formatBytes(new byte[]{buff[19]}));
		int[] FieldLen = loginr.getFieldLen();
		FieldLen[2] = contextLength;
		loginr.setBasicData();
		loginr.setPacket(buff);
		int charAt = 0;
		String context = new String(loginr.GetField("交易内容"));
		System.out.println("交易内容: "+context);
		int uidLength = Integer.parseInt(context.substring(0, charAt+=2));
		String uid = context.substring(charAt,charAt+=uidLength); 
		System.out.println("uid: "+uid);
		
		int pwdLength = Integer.parseInt(context.substring(charAt, charAt+=2));
		String pwd = context.substring(charAt,charAt+=pwdLength); 
		System.out.println("pwd: "+pwd);
	}
	
	public byte[] send(TbUserTable user){
		Logins logins = new Logins();
		byte[] userBytes = user.toBytes();
		int[] FieldLen = logins.getFieldLen();
		FieldLen[2] = userBytes.length;
		logins.setBasicData();
		
		logins.SetField(LoUtils.StrToBytes("570001"), "报文代码");
		logins.SetField(LoUtils.StrToBytes("0001"), "交易类型");
		logins.SetField(userBytes, "交易内容");
		System.out.println(LoUtils.formatBytes(logins.getPacket()));
		
		return logins.getPacket();
	}
}
