package com.xcomm.Serializable;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;

import com.xcomm.entity.TbUserTable;


/**
 * 实现实体序列化
 * 作者：Leonidas 
 * piaobomengxiang@163.com
 * 时间：2013-9-13
 * 版本：1.0
 * 描述：将实体对象序列化，使用mina框架将其在网络传输
 */

public class FindChatEntitySerializable {

	
    /**
     *将对象序列化成流
     *@paramo
     *@throwsException
     */
	public static byte[] getByteWriteObject(Object o){
		byte[] dataForServer = null;
		// 序列化使用的输出流
		ObjectOutputStream OOS = null;
		// 序列化后数据流给ByteArrayOutputStream 来保存。
		// ByteArrayOutputStream 可转成字符串或字节数组
		ByteArrayOutputStream BAOS = new ByteArrayOutputStream();
		try {
			OOS = new ObjectOutputStream(BAOS);
			OOS.writeObject(o);
			dataForServer = BAOS.toByteArray();
			String StrMySerializer = dataForServer.toString();
			System.out.println("序列化:" + StrMySerializer);
			OOS.close();
			return dataForServer;
		} catch (IOException e) {
			e.printStackTrace();
			return dataForServer;
		} catch (Exception ex) {
			System.out.println("序列化时产生错误 ");
			return dataForServer;
		}

}
	
	
	 /**
     *将流反序列化成对象
     *@paramo
     *@throwsException
     */
	public static TbUserTable getByteWriteObjectFromServer(byte[] strMySerializer){
		StringBuffer sb = new StringBuffer("");
		for(int i =0;i<strMySerializer.length;i++){
			sb.append(strMySerializer[i]);
		}
		
		System.out.println("======================");
		System.out.println(strMySerializer);
		System.out.println(sb.toString());
		TbUserTable tbUserTable = null;
		String name = null;
		try {
			name = new String(strMySerializer,"UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		System.out.println(name);
		byte[] a = name.getBytes();
		System.out.println(a);
		// ByteArrayInputStream 可接收一个字节数组 "byte[] "。供反序列化做参数
		ByteArrayInputStream BAIS = null;
		// 反序列化使用的输入流
		ObjectInputStream OIS = null;
		try {
			//反序列化
			BAIS = new ByteArrayInputStream(a);
			OIS = new ObjectInputStream(BAIS);
			tbUserTable = (TbUserTable) (OIS.readObject());
			System.out.println("反序列化:" + tbUserTable.getPassWd());
			OIS.close();
			return tbUserTable;
		} catch (IOException e) {
			e.printStackTrace();
			return tbUserTable;
		} catch (Exception ex) {
			System.out.println("序列化时产生错误 ");
			return tbUserTable;
		}

}
	
	
    /**
     *将对象序列化到磁盘文件中
     *@paramo
     *@throwsException
     */

    public static void writeObject(Object o,String path) throws Exception{

       File f=new File(path);//"/opt/user.tmp"

       if(f.exists()){

           f.delete();

       }

       FileOutputStream os=new FileOutputStream(f);

       //ObjectOutputStream 核心类

       ObjectOutputStream oos=new ObjectOutputStream(os);

       oos.writeObject(o);

       oos.close();

       os.close();

    }

   

    /**

     *反序列化,将磁盘文件转化为对象

     *@paramf

     *@return

     *@throwsException

     */

    public static TbUserTable readObject(File f) throws Exception{

       InputStream is=new FileInputStream(f);

       //ObjectOutputStream 核心类

       @SuppressWarnings("resource")
       ObjectInputStream ois=new ObjectInputStream(is);
       return (TbUserTable)ois.readObject();

    }

   

   

    public static void main(String[] args) throws Exception{

      

       /*****************将对象序列化***************/

      


    	TbUserTable user=new TbUserTable();

       user.setId(0);

       user.setNickname("Test");

       FindChatEntitySerializable.writeObject(user, "/opt/user.tmp");


      

       /*****************将对象序反列化***************/

      

    	//TbUserTable user=ChatEntitySerializable.readObject(new File("/opt/user.tmp"));

       System.out.println(user);

    }

}