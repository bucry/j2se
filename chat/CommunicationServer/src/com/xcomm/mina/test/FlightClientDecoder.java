package com.xcomm.mina.test;


import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

/**
 * @function : 
 * @author   :jy
 * @company  :万里网
 * @date     :2011-8-7
 */
public class FlightClientDecoder extends CumulativeProtocolDecoder {

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
