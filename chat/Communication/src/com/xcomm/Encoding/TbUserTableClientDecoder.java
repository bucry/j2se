package com.xcomm.Encoding;


import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

/**
 * 客户端解码器
 * 作者：Leonidas 
 * piaobomengxiang@163.com
 * 时间：2013-9-14
 * 版本：1.0
 * 描述：客户端解码器，将字节流按照固定编码方式实现编码
 * 必须自定义实现，默认编码方式客户端与服务器不同
 * 会导致解码错误，无法实现流传送
 */
public class TbUserTableClientDecoder extends CumulativeProtocolDecoder {

	/* (non-Javadoc)
	 * @see org.apache.mina.filter.codec.ProtocolDecoder#decode(org.apache.mina.core.session.IoSession, org.apache.mina.core.buffer.IoBuffer, org.apache.mina.filter.codec.ProtocolDecoderOutput)
	 */
	@Override
	protected boolean doDecode(IoSession session, IoBuffer in, ProtocolDecoderOutput out) throws Exception {
		CharsetDecoder cd = Charset.forName("UTF-8").newDecoder();
		IoBuffer buf = IoBuffer.allocate(100).setAutoExpand(true);
		
		while(in.hasRemaining()){
			buf.put(in.get());
		}
		buf.flip();
		out.write(buf.getString(cd));
		return false;
	}

}
