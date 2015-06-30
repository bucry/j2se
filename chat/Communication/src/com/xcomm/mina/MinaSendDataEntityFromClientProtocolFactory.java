package com.xcomm.mina;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

public class MinaSendDataEntityFromClientProtocolFactory implements ProtocolCodecFactory{

	private ProtocolEncoder encoder;
	private ProtocolDecoder decoder;

	public MinaSendDataEntityFromClientProtocolFactory() {
		encoder = new MinaSendDataEntityFromClientEncoder();
		decoder = new MinaSendDataEntityFromClientDecoder();
	}

	public ProtocolEncoder getEncoder(IoSession ioSession) throws Exception {
		return encoder;
	}

	public ProtocolDecoder getDecoder(IoSession ioSession) throws Exception {
		return decoder;
	}
}