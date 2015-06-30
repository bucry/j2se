package com.xcomm.mina;


import java.net.InetSocketAddress;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.SocketAcceptor;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import com.xcomm.Encoding.TbUserTableServerCodecFactory;
import com.xcomm.action.TbUserTableInfAction;
import com.xcomm.entity.TbUserTable;

public class MinaServer {
	
	public void init() throws Exception{
		SocketAcceptor acceptor = new NioSocketAcceptor(Runtime.getRuntime().availableProcessors() + 1);
		//设置解析器
		DefaultIoFilterChainBuilder chain = acceptor.getFilterChain();
		//chain.addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory()));
		chain.addLast("codec", new ProtocolCodecFilter(new TbUserTableServerCodecFactory()));
		acceptor.setHandler(new BexnHandler());
		acceptor.bind(new InetSocketAddress(8080));
		
	}
	public MinaServer() throws Exception {
		init();
	}
	
	public static void main(String[] args) throws Exception {
		new MinaServer();
		System.out.println("服务已经启动:8080");
	}
}

class BexnHandler extends IoHandlerAdapter{
	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {
		System.out.println("服务器接收到数据:" + message);
		//byte[] getDataFromClient = (byte[]) message;
		//System.out.println("服务器接收到数据:" + getDataFromClient);
		//分析数据--解密
		//分析数据,反序列化
		//MinaAnalyse.analyseData(MinaAnalyse.decryptData(getDataFromClient, "tbUserKey"));
		//MinaAnalyse.analyseData(getDataFromClient);
		TbUserTable t = (TbUserTable) message;
		System.out.println("用户TTID:" + t.getTtId());
		System.out.println("用户密码:" + t.getPassWd());
		
		super.messageReceived(session, message);
		
		if(new TbUserTableInfAction().isLogin(t)){
			session.write("SUCCESS");
		}else{
			session.write("FAIL");
		}
		
		//session.closeOnFlush();
	}
	
	@Override
	public void exceptionCaught(IoSession session, Throwable cause)
			throws Exception {
		if (session.isConnected()) {
			session.close();
		}
	}

	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
		session.close();
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		super.sessionClosed(session); 
		System.out.println("sessionClosed");
	}

	@Override
	public void sessionCreated(IoSession session) throws Exception {
		session.getConfig().setIdleTime(IdleStatus.BOTH_IDLE, 30000);
	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus status)
			throws Exception {
		session.close();
	}

	@Override
	public void sessionOpened(IoSession session) throws Exception {
		super.sessionOpened(session);
	}
}
