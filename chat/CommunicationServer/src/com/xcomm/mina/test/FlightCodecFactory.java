package com.xcomm.mina.test;


import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

/**
 * @function : 
 * @author   :jy
 * @company  :万里网
 * @date     :2011-8-7
 */
public class FlightCodecFactory implements ProtocolCodecFactory {
	private final ProtocolEncoder encoder = new FlightEncoder();
	private final ProtocolDecoder decoder = new FlightDecoder();

	@Override
	public ProtocolDecoder getDecoder(IoSession session) throws Exception {
		return decoder;
	}

	@Override
	public ProtocolEncoder getEncoder(IoSession session) throws Exception {
		return encoder;
	}

}
