package com.xcomm.test;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

public class MinaSendDataEntityFromServerProtocolFactory implements ProtocolCodecFactory{

	private ProtocolEncoder encoder;
	private ProtocolDecoder decoder;

	public MinaSendDataEntityFromServerProtocolFactory() {
		encoder = new MinaSendDataEntityFromServerEncoder();
		decoder = new MinaSendDataEntityFromServerDecoder();
	}

	public ProtocolEncoder getEncoder(IoSession ioSession) throws Exception {
		return encoder;
	}

	public ProtocolDecoder getDecoder(IoSession ioSession) throws Exception {
		return decoder;
	}
}