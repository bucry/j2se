package com.xcomm.mina;

import java.io.NotSerializableException;
import java.io.Serializable;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

public class MinaDataEncoder extends ProtocolEncoderAdapter{

	@Override
	public void encode(IoSession session, Object message, ProtocolEncoderOutput out)
			throws Exception {
		if (!(message instanceof Serializable)) {
	        throw new NotSerializableException();
	    }

	    IoBuffer buf = IoBuffer.allocate(64);
	    buf.setAutoExpand(true);
	    buf.putObject(message);
	    buf.flip();
	    out.write(buf);
	}

}
