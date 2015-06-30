package com.xcomm.server.impl;

import java.util.List;

import com.xcomm.dao.TbUserTableInfoDao;
import com.xcomm.dao.impl.TbUserTableInfoDaoImpl;
import com.xcomm.entity.TbUserTable;
import com.xcomm.server.TbUserTableInfoServer;

public class TbUserTableInfoServerImpl implements TbUserTableInfoServer{
	
	TbUserTableInfoDao tbUserTableInfoDao = new TbUserTableInfoDaoImpl();

	@Override
	public TbUserTable getTbUserInfoGetByTtId(TbUserTable tbUserTable) {
		 System.out.println("================================");
		 System.out.println("Server isLogin");
		 System.out.println(tbUserTable.getTtId());
		 System.out.println("================================");
		
		return tbUserTableInfoDao.getTbUserInfoGetByTtId(tbUserTable);
	}

	@Override
	public void queryNosqlTbale(List<TbUserTable> list) {
		tbUserTableInfoDao.queryNosqlTbale(list);
	}

	@Override
	public void insertNosqlTable(List<TbUserTable> list) {
		tbUserTableInfoDao.insertNosqlTable(list);
	}

}
