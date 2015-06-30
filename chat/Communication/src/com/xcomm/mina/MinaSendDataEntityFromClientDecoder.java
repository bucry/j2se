package com.xcomm.mina;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

import com.xcomm.mina.util.LoUtils;

public class MinaSendDataEntityFromClientDecoder extends CumulativeProtocolDecoder {

	@Override
	protected boolean doDecode(IoSession session, IoBuffer in,
			ProtocolDecoderOutput out) throws Exception {
		byte[] buff = LoUtils.ioBufferToByte(in);

		out.write(buff);

		return false;
	}

}
