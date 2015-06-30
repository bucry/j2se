package com.xcomm.mina.util;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;
import org.apache.mina.core.buffer.IoBuffer;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class LoUtils {

	private static Properties p;

	private static Properties getProperties() {
		if (p != null) {
			return p;
		} else {
			InputStream in;
			try {
				in = new BufferedInputStream(new FileInputStream(
						"encrypt.properties"));
				p = new Properties();
				p.load(in);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return p;
		}
	}

	
	//byte����ת�ַ�
	public static String formatBytes(byte p_arrCommand[]) {
		return formatBytes(p_arrCommand, p_arrCommand.length, "");
	}
	public static String formatBytes(byte p_arrCommand[], int p_intLength,
			String p_strSeparator) {
		StringBuffer sbResult = new StringBuffer();
		for (int intIndex = 0; intIndex < p_intLength; intIndex++) {
			int intValue = p_arrCommand[intIndex];
			if (intValue < 0)
				intValue += 256;
			String strHexString = Integer.toHexString(intValue);
			if (strHexString.length() == 1) {
				sbResult.append("0");
				sbResult.append(p_strSeparator);
			}
			sbResult.append(strHexString);
		}

		return sbResult.toString();
	}
	

	public static double strToDouble(String str) {
		return Double.parseDouble(str);
	}

	//ȡ�������ļ�����
	public static String getProperty(String pro) {
		Properties p = getProperties();
		return p.getProperty(pro.toUpperCase());
	}
	
	public static String addZeros(String str, int len){
		int length = str.length();
		if(length < len){
			for (int i = 0; i < len - length; i++) {
				str = "0" + str;
			}
		}
		return str;
	}
	public static IoBuffer byteToIoBuffer(byte[] buff) {
		IoBuffer ioBuffer = IoBuffer.allocate(buff.length);
		ioBuffer.put(buff, 0, buff.length);
		ioBuffer.flip();
		return ioBuffer;
	}

	public static byte[] ioBufferToByte(Object message) {
		IoBuffer ioBuffer = (IoBuffer) message;
		byte[] b = new byte[ioBuffer.limit()];
		ioBuffer.get(b);
		return b;
	}

	public static IoBuffer strToIoBuffer(String str) {
		byte bt[] = LoUtils.StrToBytes(str);
		return byteToIoBuffer(bt);
	}
	public static byte[] StrToBytes(String str) {
		if (str == null)
			return null;
		if (str.length() / 2 < 1)
			return null;
		byte data[] = new byte[str.length() / 2];
		for (int i = 0; i < str.length() / 2; i++) {
			int intValue = Integer
					.parseInt(str.substring(2 * i, 2 * i + 2), 16);
			if (intValue > 127)
				intValue -= 256;
			data[i] = (byte) intValue;
		}
		return data;
	}


	// �ַ�ת��ΪASCII��
	public static byte[] ch_znToAscii(String ch_znString) {
		char[] chars = ch_znString.toCharArray();
		byte[] result = new byte[chars.length];
		for (int i = 0; i < chars.length; i++) {
			result[i] = (byte) chars[i];
		}
		return result;
	}
	

	//asciiת����
	public static String asciiToCh_zn(String bytes) {
		bytes = bytes.toUpperCase();
		String hexString = "0123456789ABCDEF";
		ByteArrayOutputStream baos = new ByteArrayOutputStream(
				bytes.length() / 2);
		// ��ÿ2λ16����������װ��һ���ֽ�
		for (int i = 0; i < bytes.length(); i += 2)
			baos.write((hexString.indexOf(bytes.charAt(i)) << 4 | hexString
					.indexOf(bytes.charAt(i + 1))));
		return new String(baos.toByteArray());
	}

	

	public static byte[] toByteArray(String str) {
		byte[] buff = null;
		try {
			if (null == str || str.trim().equals("")) {
				buff = new byte[0];
				return buff;
			} else {
				buff = str.getBytes("UTF-8");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return buff;
	}

	public static byte[] encoderSHA(byte[] buff) {
		// ��ʼ��MessageDigest,SHA��SHA-1�ļ��
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		// ִ��ժҪ����
		return md.digest(buff);
		// return new HexBinaryAdapter().marshal(digest);
	}

	//SHA����  base64����
	public static String encoderBASE64(String string) {
		byte[] buff = toByteArray(string);
		buff = encoderSHA(buff);
		return (new BASE64Encoder()).encode(buff);
	}

	// �� BASE64 ������ַ� s ���н���
	public static String decoderBASE64(String s) {
		if (s == null)
			return null;
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			byte[] b = decoder.decodeBuffer(s);
			return new String(b);
		} catch (Exception e) {
			return null;
		}
	}
	
	//��ʽ��double���  ---12.50
	public static String formatDouble(double d){
		java.text.DecimalFormat df=new java.text.DecimalFormat("#,##0.00"); //������ʾ��ʽ
		return df.format(d);
	}

	public static String addLength(String string){
		int length = string.length();
		if(length < 10)
			return "0"+Integer.toString(length)+string;
		else
			return Integer.toString(length)+string;
	}

}
