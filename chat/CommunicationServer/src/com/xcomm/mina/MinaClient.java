package com.xcomm.mina;


import java.net.ConnectException;
import java.net.InetSocketAddress;

import org.apache.mina.core.RuntimeIoException;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.SocketConnector;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

public class MinaClient {
	public SocketConnector socketConnector;

	/**
	 * 缺省连接超时时间
	 */
	public static final int DEFAULT_CONNECT_TIMEOUT = 5;

	public static final String HOST = "localhost";

	public static final int PORT = 8080;

	public MinaClient() {
		init();
	}

	public void init() {
		socketConnector = new NioSocketConnector();

		// 长连接
		// socketConnector.getSessionConfig().setKeepAlive(true);

		socketConnector.setConnectTimeout(DEFAULT_CONNECT_TIMEOUT);
		//socketConnector.setReaderIdleTime(DEFAULT_CONNECT_TIMEOUT);
		//socketConnector.setWriterIdleTime(DEFAULT_CONNECT_TIMEOUT);
		//socketConnector.setBothIdleTime(DEFAULT_CONNECT_TIMEOUT);

		socketConnector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory()));
		
		ClientIoHandler ioHandler = new ClientIoHandler();
		socketConnector.setHandler(ioHandler);
	}

	public void sendMessage(final String msg) {
		InetSocketAddress addr = new InetSocketAddress(HOST, PORT);
		ConnectFuture cf = socketConnector.connect(addr);
		try {
			cf.awaitUninterruptibly();
			cf.getSession().write(msg);
			System.out.println("发送数据:" + msg);
		} catch (RuntimeIoException e) {
			if (e.getCause() instanceof ConnectException) {
				try {
					if (cf.isConnected()) {
						cf.getSession().close();
					}
				} catch (RuntimeIoException e1) {
				}
			}
		}
	}

	public static void main(String[] args) throws InterruptedException {
		MinaClient clent = new MinaClient();
		for (int i = 0; i < 1; i++) {
			System.err.println(i);
			clent.sendMessage("Hello World " + i);
		}
		clent.getSocketConnector().dispose();
		//System.exit(0);
	}

	public SocketConnector getSocketConnector() {
		return socketConnector;
	}

	public void setSocketConnector(SocketConnector socketConnector) {
		this.socketConnector = socketConnector;
	}
	
	
}

class ClientIoHandler extends IoHandlerAdapter {

	private void releaseSession(IoSession session) throws Exception {
		System.out.println("releaseSession");
		if (session.isConnected()) {
			//session.closeOnFlush();
		}
	}

	@Override
	public void sessionOpened(IoSession session) throws Exception {
		System.out.println("sessionOpened");
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		System.out.println("sessionClosed");
	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
		System.out.println("sessionIdle");
		try {
			releaseSession(session);
		} catch (RuntimeIoException e) {
		}
	}

	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {
		System.out.println("Receive Server message " + message);

		super.messageReceived(session, message);

		releaseSession(session);
	}

	@Override
	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
		System.out.println("exceptionCaught");
		cause.printStackTrace();
		releaseSession(session);
	}

	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
		System.out.println("messageSent");
		super.messageSent(session, message);
	}

}

