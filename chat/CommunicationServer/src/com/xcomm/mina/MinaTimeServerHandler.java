package com.xcomm.mina;

import java.util.Date;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

/**
 * Mina测试案例
 * 作者：Leonidas
 * piaobomengxiang@163.com
 * 时间：2013-9-13
 * 版本：1.0
 * 描述：首先运行MinaTimeServer，启动服务端，
 * 接着在命令行运行“telnet 127.0.0.1 9123”,来登录
 */
public class MinaTimeServerHandler extends IoHandlerAdapter {
	@Override
	public void sessionCreated(IoSession session) {
		// 显示客户端的ip和端口
		System.out.println(session.getRemoteAddress().toString());
	}

	@SuppressWarnings("deprecation")
	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		String str = message.toString();
		System.out.println(str);
		if (str.trim().equalsIgnoreCase("quit")) {
			session.close();// 结束会话
			return;
		}
		Date date = new Date();
		session.write(date.toString());// 返回当前时间的字符串
		System.out.println("Message written...");
	}
}