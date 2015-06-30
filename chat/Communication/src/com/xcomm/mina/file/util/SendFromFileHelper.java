package com.xcomm.mina.file.util;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * 客户端传送文件辅助类
 * 作者：Leonidas 
 * piaobomengxiang@163.com
 * 时间：2013-9-14
 * 版本：1.0
 * 描述：客户端通过Mina框架上传文件
 * 文件通过流化后传送
 * 该类用于取得文件数组
 */

public class SendFromFileHelper {
	
	/**
	 * 直接根据文件路径返回byte数组
	 * */
	@SuppressWarnings("resource")
	public byte[] getContent(String filePath) throws IOException {  
        File file = new File(filePath);  
  
        long fileSize = file.length();  
        if (fileSize > Integer.MAX_VALUE) {  
            System.out.println("file too big...");  
            return null;  
        }  
  
        FileInputStream fi = new FileInputStream(file);  
  
        byte[] buffer = new byte[(int) fileSize];  
  
        int offset = 0;  
  
        int numRead = 0;  
  
        while (offset < buffer.length  
        		
        && (numRead = fi.read(buffer, offset, buffer.length - offset)) >= 0) {  
        	
            offset += numRead;  
        }  
        // 确保所有数据均被读取   
        if (offset != buffer.length) {  
  
            throw new IOException("Could not completely read file "  
                    + file.getName());  
  
        }  
        fi.close();  
        return buffer;  
    }  
	/**
	 * 根据文件返回byte数组
	 * */
	@SuppressWarnings("resource")
	public byte[] getContent(File file) throws IOException {  
	        long fileSize = file.length();  
	        if (fileSize > Integer.MAX_VALUE) {  
	            System.out.println("file too big...");  
	            return null;  
	        }  
	  
	        FileInputStream fi = new FileInputStream(file);  
	  
	        byte[] buffer = new byte[(int) fileSize];  
	  
	        int offset = 0;  
	  
	        int numRead = 0;  
	  
	        while (offset < buffer.length  
	        		
	        && (numRead = fi.read(buffer, offset, buffer.length - offset)) >= 0) {  
	        	
	            offset += numRead;  
	        }  
	        // 确保所有数据均被读取   
	        if (offset != buffer.length) {  
	  
	            throw new IOException("Could not completely read file "  
	                    + file.getName());  
	  
	        }  
	        fi.close();  
	        return buffer;  
	}
}
