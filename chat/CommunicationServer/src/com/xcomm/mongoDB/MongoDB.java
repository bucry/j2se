package com.xcomm.mongoDB;

import java.net.UnknownHostException;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import com.mongodb.MongoException;


public class MongoDB <T extends java.io.Serializable>{
	
	public Class<?> clazz;
	// 连接本地数据库  
	public static Mongo m= null;
	// 创建名为new_test_db的数据库  
	public static DB db = null; 
	// 创建一个叫做"new_test_col"的集合  
	public static DBCollection collection = null; 
	// 初始化一个基本DB对象，最终插入数据库的就是这个DB对象  
	public static BasicDBObject obj = null; 
	
	
	public MongoDB(){
			try{
				m = new Mongo();  
				db = m.getDB("TT");
				collection = db.getCollection("TbUserTable");
				obj = new BasicDBObject();
			}catch (UnknownHostException e){
				     
			}catch (MongoException e1){
		}
	}
}
