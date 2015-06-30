package com.xcomm.test;

import java.nio.charset.Charset;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

public class MinaSendDataEntityFromServerEncoder implements ProtocolEncoder {

	@Override
	public void encode(IoSession session, Object message,
			ProtocolEncoderOutput out) throws Exception {
		IoBuffer ioBuffer = null;

		if (message instanceof byte[]) {
			byte[] buff = (byte[]) message;
			ioBuffer = IoBuffer.allocate(buff.length, false);
			ioBuffer.put(buff);
			ioBuffer.flip();
			out.write(ioBuffer);
		} else {
			String string = (String) message;
			ioBuffer = IoBuffer.allocate(string.length(), false);
			ioBuffer.putString(string, Charset.forName("UFT-8").newEncoder());
			ioBuffer.flip();
			out.write(ioBuffer);
		}
	}

	@Override
	public void dispose(IoSession arg0) throws Exception {
	}

}

