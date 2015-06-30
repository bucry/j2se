package com.xcomm.mina;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

public class MinaSendDataEntityFromClientHandler extends IoHandlerAdapter{


	@Override
	public void sessionOpened(IoSession session) throws Exception {
		System.out.println("client session open...");
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		System.out.println("client session close...");
	}

	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		if (message instanceof byte[]) {
			byte[] buff = (byte[]) message;
			System.out.println("serverdata: "+new String(buff));
		} 
		session.close(true);
	}

	@Override
	public void exceptionCaught(IoSession session, Throwable cause)
			throws Exception {
		System.out.println("exception");
		session.close(true);
		super.exceptionCaught(session, cause);
	}

	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
		System.out.println("send message success...");
	}

}
