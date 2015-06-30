package com.xcomm.mina.test;

import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

/**
 * @function : 
 * @author   :jy
 * @company  :万里网
 * @date     :2011-8-7
 */
public class FlightEncoder extends ProtocolEncoderAdapter {
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
