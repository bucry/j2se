package com.xomm.Login;

import com.xcomm.entity.TbUserTable;
import com.xcomm.mina.MinaSendDataFromClient;

/**
 * 登录数据验证
 * 作者：Leonidas 
 * piaobomengxiang@163.com
 * 时间：2013-9-13
 * 版本：1.0
 * 描述：登录验证
 */
public class LoginCheckData {
	private MinaSendDataFromClient clent = new MinaSendDataFromClient();
	public static String serverMessage = null;
	public boolean isLogin(TbUserTable tbUserInfo){
		
		
		//String data = "123 456";
		//String key = "wang!@#$%";
		try {
			//String data = DesUtil.encrypt(ChatEntitySerializable.getByteWriteObject(tbUserInfo), tbUserInfo.getKey());
			//ChatEntitySerializable.getByteWriteObjectFromServer(ChatEntitySerializable.getByteWriteObject(tbUserInfo));
			//byte[] dateFromClient = ChatEntitySerializable.getByteWriteObject(tbUserInfo);
			clent.sendMessage(tbUserInfo);
			//clent.getSocketConnector().dispose();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		/*if(serverMessage.equals("SUCCESS")){
			return true;
		}else{
			return false;
		}*/
		return true;
	}

}
