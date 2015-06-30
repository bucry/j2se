package com.xcomm.dao;

import java.util.List;

import com.xcomm.entity.TbUserTable;

public interface TbUserTableInfoDao {
	public  TbUserTable getTbUserInfoGetByTtId(TbUserTable tbUserTable);
	public  void queryNosqlTbale(List<TbUserTable> list);
	public  void insertNosqlTable(List<TbUserTable> list);

}
