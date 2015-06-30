package com.xcomm.mina;

import com.xcomm.entity.TbUserTable;


public class Test {
	public static void main(String[] args) {
		MinaSendDataEntityFromClientServer userService = new MinaSendDataEntityFromClientServer();
		TbUserTable t = new TbUserTable();
		t.setTtId("111111");
		t.setPassWd("1222");
		byte[] sendBuff = userService.send(t);
		new MinaSendDataEntityFromClient().sendMessage("127.0.0.1", 8901, sendBuff);
	}

}
