package com.xcomm.analyse;

import java.io.IOException;

import com.xcomm.Serializable.MinaSerializable;
import com.xcomm.encrypt.DesUtil;
import com.xcomm.entity.TbUserTable;

/**
 * 数据分析
 * 作者：Leonidas 
 * piaobomengxiang@163.com
 * 时间：2013-9-13
 * 版本：1.0
 * 描述：数据分析类，接受到Mina数据，根据加密协议或者数据包格式，正确解析出
 * 需要的数据
 */
public class MinaAnalyse {
	private static TbUserTable tbUserTable;
	public static void analyseData(byte[] objectData){
		
		try {
			tbUserTable = MinaSerializable.getByteWriteObjectFromClient(objectData);
			System.out.println("======================\n"+tbUserTable.getPassWd());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	public static String decryptData(String data,String key) throws IOException{
		String newData = null;
		try {
			newData = DesUtil.decrypt(data, key);
			System.out.println("解密后:" + newData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return newData;
	}
}
