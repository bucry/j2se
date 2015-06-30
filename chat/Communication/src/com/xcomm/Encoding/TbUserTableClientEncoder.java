package com.xcomm.Encoding;


import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

import com.xcomm.entity.TbUserTable;



/**
 * 客户端编码器
 * 作者：Leonidas 
 * piaobomengxiang@163.com
 * 时间：2013-9-13
 * 版本：1.0
 * 描述：客户端编码器，将字节流按照固定编码方式实现编码
 * 必须自定义实现，默认编码方式客户端与服务器不同
 * 会导致解码错误，无法实现流传送
 */
public class TbUserTableClientEncoder extends ProtocolEncoderAdapter {
	private final Charset charset;
	
	public TbUserTableClientEncoder(){
		this.charset = Charset.forName("UTF-8");
	}

	@Override
	public void encode(IoSession session, Object message, ProtocolEncoderOutput out) throws Exception {
		
		System.out.println("客户端编码开始--------");
		
		TbUserTable tbUserTable = (TbUserTable)message;
		
		IoBuffer buf = IoBuffer.allocate(100).setAutoExpand(true);
		
		CharsetEncoder ce = charset.newEncoder();
		buf.putString(tbUserTable.getTtId() + "\n", ce);
		buf.putString(tbUserTable.getPassWd() + "\n", ce);
		
		buf.flip();
		
		out.write(buf);
		System.out.println("客户端编码结束--------");
	}

}
