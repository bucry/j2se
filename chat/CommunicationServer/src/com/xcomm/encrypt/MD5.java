package com.xcomm.encrypt;


import java.security.MessageDigest;
import sun.misc.BASE64Encoder;
import com.sun.org.apache.xml.internal.security.utils.Base64;


/**
 * MD5单向散列加密
 * 作者：Leonidas
 * piaobomengxiang@163.com
 * 时间：2011-2-3
 * 版本：1.0
 * 描述：实现将数据DES加密传输
 */
public class MD5 {
	public String EncoderByMd5(String str) {
		String newstr = "";
		try {
			// 确定计算方法
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			@SuppressWarnings("unused")
			BASE64Encoder base64en = new BASE64Encoder();
			// 加密后的字符串
			newstr = Base64.encode(md5.digest(str.getBytes("gb2312")));
		} catch (Exception e) {
			System.out.print(e + "加密错误");
		}
		return newstr;
	}
	
	public static void main(String args[]){
		System.out.println(new MD5().EncoderByMd5("Hello"));
	}
}
