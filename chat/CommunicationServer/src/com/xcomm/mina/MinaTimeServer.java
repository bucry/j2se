package com.xcomm.mina;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

/**
 * Mina测试案例
 * 作者：Leonidas
 * piaobomengxiang@163.com
 * 时间：2013-9-13
 * 版本：1.0
 * 描述：首先运行MinaTimeServer，启动服务端，
 * 接着在命令行运行“telnet 127.0.0.1 9123”,来登录
 */

public class MinaTimeServer {
	private static final int PORT = 9123;// 定义监听端口

	public static void main(String[] args) throws IOException {
		IoAcceptor acceptor = new NioSocketAcceptor();
		acceptor.getFilterChain().addLast("logger", new LoggingFilter());
		acceptor.getFilterChain().addLast(
				"codec",
				new ProtocolCodecFilter(new TextLineCodecFactory(Charset
						.forName("UTF-8"))));// 指定编码过滤器
		acceptor.setHandler(new MinaTimeServerHandler());// 指定业务逻辑处理器
		acceptor.setDefaultLocalAddress(new InetSocketAddress(PORT));// 设置端口号
		acceptor.bind();// 启动监听
	}
}
