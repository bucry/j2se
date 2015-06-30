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

import com.xcomm.entity.TbUserTable;


/**
 * 实现实体序列化
 * 作者：Leonidas 
 * piaobomengxiang@163.com
 * 时间：2013-9-13
 * 版本：1.0
 * 描述：将实体对象序列化，使用mina框架将其在网络传输
 */
public class MinaSerializable {

	
	
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
			System.out.println("abc:"+dataForServer);
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
	public static TbUserTable getByteWriteObjectFromClient(byte[] strMySerializer){
		TbUserTable tbUserTable = null;
		// 序列化后数据流给ByteArrayOutputStream 来保存。
		// ByteArrayOutputStream 可转成字符串或字节数组
		ByteArrayOutputStream BAOS = new ByteArrayOutputStream();
		// ByteArrayInputStream 可接收一个字节数组 "byte[] "。供反序列化做参数
		ByteArrayInputStream BAIS = null;
		// 反序列化使用的输入流
		ObjectInputStream OIS = null;
		try {
			//反序列化
			BAIS = new ByteArrayInputStream(strMySerializer);
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

       File f=new File(path);

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

       ObjectInputStream ois=new ObjectInputStream(is);
       return (TbUserTable)ois.readObject();

    }

   

   

    public static void main(String[] args) throws Exception{

      

       /*****************将对象序列化***************/

      

    /* 

        TbUserInfo user=new TbUserInfo();

       user.setUserId(1);

       user.setUserName("张艺谋");

       user.setUserSex("男");

       user.setUserAge(50);

       TestSerializable.writeObject(user);

    */

      

       /*****************将对象序反列化***************/

      

    	TbUserTable user=MinaSerializable.readObject(new File("/opt/user.tmp"));

       System.out.println(user);

    }

}