package com.xcomm.dao.impl;

import java.util.List;
import java.util.Set;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.xcomm.dao.TbUserTableInfoDao;
import com.xcomm.entity.TbUserTable;
import com.xcomm.mongoDB.MongoDB;

public class TbUserTableInfoDaoImpl extends MongoDB<TbUserTable> implements TbUserTableInfoDao{

	private static TbUserTable tbUserTableInfo  = new TbUserTable();
	
	public TbUserTableInfoDaoImpl(){
		super();
		clazz = TbUserTable.class;
	}
	
	 public  TbUserTable getTbUserInfoGetByTtId(TbUserTable tbUserTable){
		 System.out.println("================================");
		 System.out.println("Dao isLogin");
		 //System.out.println(tbUserTable.getTtId());
		 System.out.println("================================");
		 BasicDBObject query=new BasicDBObject(); 
		 DBCursor cursor=collection.find();  
		 query.put("ttId", "123456");  
		 cursor=collection.find(query);  
		 while(cursor.hasNext()){  
		     System.out.println(cursor.next());  
		 }  
		 
		 if(cursor.hasNext()){
			 tbUserTableInfo = tbUserTable;
			 return tbUserTableInfo;
		 }else{
			 return null;
		 }
		 
	 }
	 
	 public  void queryNosqlTbale(List<TbUserTable> list){
		 
		 // 获取TT中的集合名称（类似于获取关系数据库中的表）  
		 Set<String> cols = db.getCollectionNames();  
		 // 打印出new_test_db中的集合，这里应当为null  
		 for (String s : cols) {  
		     System.out.println(s);  
		 }  
		 
		 System.out.println("======================================================");
		 //下面我们来遍历集合，find()方法返回的是一个游标(cursor)，这里的概念和关系数据库很相似  
		 DBCursor cursor=collection.find();  
		 //然后我们使用这个游标来遍历集合  
		 while(cursor.hasNext()){  
		     System.out.println(cursor.next());  
		 }  
		 System.out.println("======================================================");
		//下面来看一些略复杂一点的查询技巧，第一个，简单的条件查询，查询ranking为1的记录  
		 BasicDBObject query=new BasicDBObject();  
		 query.put("birthday", 1);  
		 cursor=collection.find(query);  
		 while(cursor.hasNext()){  
		     System.out.println(cursor.next());  
		 }  
		 System.out.println("======================================================");
		 //下面是更复杂的条件查询，查询ranking大于5小于9的记录  
		 query=new BasicDBObject();  
		 query.put("birthday", new BasicDBObject("$gt", 5).append("$lt", 9));  
		 cursor=collection.find(query);  
		 while(cursor.hasNext()){  
		     System.out.println(cursor.next());  
		 }  
	 }
	 
	 
	 public  void insertNosqlTable(List<TbUserTable> list){
		 // 放入几个键值对  
		 obj.put("id", "0");  
		 obj.put("nickname", "Test");  
		 obj.put("passWd", "Test");  
		 //插入对象  
		 collection.insert(obj);  
		 //查看一条记录，findOne()=find().limit(1);  
		 DBObject dbobj=collection.findOne();  
		 //打印出刚才插入的数据  
		 System.out.println(dbobj);  
		 //现在我们来插入9条{ranking:i}的数据  
		 for(int i=0;i<9;i++){  
		     collection.insert(new BasicDBObject().append("birthday", i));  
		 }  
		 //打印集合中的数据总数，这里应当输出10  
		 System.out.println(collection.getCount());  
	 }
	 
}
