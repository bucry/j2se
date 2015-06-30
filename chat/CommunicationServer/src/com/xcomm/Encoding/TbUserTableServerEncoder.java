package com.xcomm.Encoding;

import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;


/**
 * 服务端编码器
 * 作者：Leonidas 
 * piaobomengxiang@163.com
 * 时间：2013-9-13
 * 版本：1.0
 * 描述：服务端编码器，将字节流按照固定编码方式实现编码
 * 必须自定义实现，默认编码方式客户端与服务器不同
 * 会导致解码错误，无法实现流传送
 */
public class TbUserTableServerEncoder extends ProtocolEncoderAdapter {
	private final Charset charset = Charset.forName("UTF-8");
	/* 
	 * 服务器端编码无需处理，直接将接收到的数据向下传递
	 */
	@Override
	public void encode(IoSession session, Object message, ProtocolEncoderOutput out) throws Exception {
		IoBuffer buf = IoBuffer.allocate(100).setAutoExpand(true);
		
		CharsetEncoder ce = charset.newEncoder();
		
		buf.putString((String)message, ce);
		
		buf.flip();
		
		out.write(buf);
	}

}
