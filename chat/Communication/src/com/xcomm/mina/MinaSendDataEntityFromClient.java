package com.xcomm.mina;

import java.net.InetSocketAddress;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

public class MinaSendDataEntityFromClient {
	
	ConnectFuture cf = null;
	NioSocketConnector connector = null;
	
	public void sendMessage(String address, int port, Object message) {
		if (cf == null) {
			getconnect(address, port);
		}
		// 等待连接创建完成
		cf.awaitUninterruptibly();
		IoSession session = cf.getSession();
		// 发送消息
		session.write(message);
		// session.write("quit");
		// 等待连接断开
		session.getCloseFuture().awaitUninterruptibly();
		// 释放连接
		connector.dispose();
	}

	public void getconnect(String address, int port) {
		// 创建客户端连接器.
		connector = new NioSocketConnector();
		// 设置日志记录器
		connector.getFilterChain().addLast("logger", new LoggingFilter());
		// 加载解  编码工厂
		connector.getFilterChain().addLast("addmoney",new ProtocolCodecFilter(new MinaSendDataEntityFromClientProtocolFactory()));

		// 设置连接超时检查时间
		connector.setConnectTimeoutCheckInterval(300);
		// 设置事件处理器
		connector.setHandler(new MinaSendDataEntityFromClientHandler());
		// 建立连接
		cf = connector.connect(new InetSocketAddress(address, port));

		// connector.getSessionConfig().setUseReadOperation(true);
		System.out.println("Client Start....");
	}


}
