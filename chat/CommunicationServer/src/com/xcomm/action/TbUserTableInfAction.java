package com.xcomm.action;

import com.xcomm.entity.TbUserTable;
import com.xcomm.server.TbUserTableInfoServer;
import com.xcomm.server.impl.TbUserTableInfoServerImpl;

public class TbUserTableInfAction {
	TbUserTableInfoServer tbUserTableInfoServer = new TbUserTableInfoServerImpl();
	
	public boolean isLogin(TbUserTable tbUserTable){
		System.out.println("================================");
		System.out.println("Action isLogin");
		System.out.println(tbUserTable.getTtId());
		System.out.println("================================");
		TbUserTable userInfo = tbUserTableInfoServer.getTbUserInfoGetByTtId(tbUserTable);
		if(userInfo != null){
			return true;
		}else{
			return false;
		}
	}
}
