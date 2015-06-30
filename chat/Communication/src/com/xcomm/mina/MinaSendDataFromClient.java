package com.xcomm.mina;


import java.net.ConnectException;
import java.net.InetSocketAddress;

import org.apache.mina.core.RuntimeIoException;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.SocketConnector;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import com.xcomm.Encoding.TbUserTableClientCodecFactory;
import com.xcomm.encrypt.DesUtil;
import com.xomm.Login.LoginCheckData;


/**
 * 客户端Mina发送消息框架
 * 作者：Leonidas
 * piaobomengxiang@163.com
 * 时间：2013-9-13
 * 版本：1.0
 * 描述：该类向服务器发送客户端的消息
 * 所有的对象经过序列化
 * 封装成ISO8583数据报之后发送
 * 聊天内容经过DES加密后发送
 */
public class MinaSendDataFromClient {
	public SocketConnector socketConnector;

	/**
	 * 缺省连接超时时间
	 */
	public static final int DEFAULT_CONNECT_TIMEOUT = 5;

	public static final String HOST = "localhost";

	public static final int PORT = 8080;

	public MinaSendDataFromClient() {
		init();
	}

	@SuppressWarnings("deprecation")
	public void init() {
		socketConnector = new NioSocketConnector();

		// 长连接
		// socketConnector.getSessionConfig().setKeepAlive(true);

		socketConnector.setConnectTimeout(DEFAULT_CONNECT_TIMEOUT);
		//socketConnector.setReaderIdleTime(DEFAULT_CONNECT_TIMEOUT);
		//socketConnector.setWriterIdleTime(DEFAULT_CONNECT_TIMEOUT);
		//socketConnector.setBothIdleTime(DEFAULT_CONNECT_TIMEOUT);

//		socketConnector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory()));
		socketConnector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new TbUserTableClientCodecFactory()));
		ClientIoHandler ioHandler = new ClientIoHandler();
		socketConnector.setHandler(ioHandler);
	}

	@SuppressWarnings("deprecation")
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
	
	
	@SuppressWarnings("deprecation")
	public void sendMessage(final byte[] msg) {
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
	
	
	@SuppressWarnings("deprecation")
	public void sendMessage(final Object msg) {
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
		MinaSendDataFromClient clent = new MinaSendDataFromClient();
		String data = "123 456";
		String key = "wang!@#$%";
		String da = null;
		try {
			da = DesUtil.encrypt(data, key);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		for (int i = 0; i < 1; i++) {
			System.err.println(i);
			clent.sendMessage(da);
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
		System.out.println("接收到服务器信息: " + message);
		super.messageReceived(session, message);
		//判断服务器数据
		LoginCheckData.serverMessage = (String)message;
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
		System.out.println("数据发送完毕!");
		super.messageSent(session, message);
	}

}

