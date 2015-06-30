package com.xcomm.test;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

public class ServerHanlder extends IoHandlerAdapter {

	private MinaSendDataEntityFromServer userService = new MinaSendDataEntityFromServer();
	@Override
	public void sessionOpened(IoSession session) throws Exception {
		System.out.println("session open...");
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		System.out.println("session close...");
	}

	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		if(message instanceof byte[]){
			byte[] buff = (byte[])message;
			userService.login(buff);
			session.write("登陆信息已收到！".getBytes());
		}
		session.close(true);
	}

	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
		System.out.println("send message success...");
	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus status)
			throws Exception {
		System.out.println("server become free...");
	}

	@Override
	public void exceptionCaught(IoSession session, Throwable cause)
			throws Exception {
		session.write("exception！".getBytes());
		System.out.println("server exception");
		session.close(true);
		super.exceptionCaught(session, cause);
	}
}
