package com.xcomm.mina.file;


import java.net.InetSocketAddress;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import com.xcomm.mina.file.util.SendFromMathProtocolCodecFactory;

/**
 * 客户端传送文件
 * 作者：Leonidas 
 * piaobomengxiang@163.com
 * 时间：2013-9-14
 * 版本：1.0
 * 描述：客户端M通过Mina框架上传文件
 * 文件通过流化后传送
 */

public class SendFromClient {
	
	public IoSession creatClient(){
		IoConnector connector=new NioSocketConnector(); 
		connector.setConnectTimeoutMillis(30000); 
		connector.getFilterChain().addLast("codec", 
		new ProtocolCodecFilter(new SendFromMathProtocolCodecFactory(false)));
		connector.setHandler(new SendFromClientHandler());
		ConnectFuture future = connector.connect(new InetSocketAddress("127.0.0.1", 9123)); 
		// 等待是否连接成功，相当于是转异步执行为同步执行。 
		future.awaitUninterruptibly(); 
		// 连接成功后获取会话对象。 如果没有上面的等待， 由于connect()方法是异步的， session可能会无法获取。 
		IoSession session = null;
		try{
			session = future.getSession();
		}catch(Exception e){
			e.printStackTrace();
		}
		return session;
	}
	public static void main(String[] args) {
		SendFromClient client = new SendFromClient();
		client.creatClient();
	}
}
