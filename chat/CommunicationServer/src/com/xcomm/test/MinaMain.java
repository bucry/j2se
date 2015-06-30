package com.xcomm.test;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

public class MinaMain {

	private static final int PORT = 8901;// 定义监听端口

	public MinaMain() {
		// 创建服务端监控线程
		IoAcceptor acceptor = new NioSocketAcceptor(Runtime.getRuntime()
				.availableProcessors() + 1);
		 acceptor.getFilterChain().addLast("logger", new LoggingFilter());
		// 设置编码过滤器 Charset.forName("UTF-8")
		// acceptor.getFilterChain().addLast("codec",
		// new ProtocolCodecFilter(new TextLineCodecFactory()));

		acceptor.getFilterChain().addLast("addmoney ",
				new ProtocolCodecFilter(new MinaSendDataEntityFromServerProtocolFactory()));// 加载解 /// 编码工厂
		// acceptor.getFilterChain().addLast("threadPool",
		// new ExecutorFilter(Executors.newCachedThreadPool()));

		// 设置读取数据的缓冲区大小
		acceptor.getSessionConfig().setReadBufferSize(2048);
		// 读写通道10秒内无操作进入空闲状态
		acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);
		// 指定业务逻辑处理器
		acceptor.setHandler(new ServerHanlder());
		// 设置端口号
		acceptor.setDefaultLocalAddress(new InetSocketAddress(PORT));
		// 启动监听线程
		try {
			acceptor.bind();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Server Start....");
	}

	
	public static void main(String[] args) {
		new MinaMain();
	}
}
