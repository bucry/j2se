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
public class FlightClientCodecFactory implements ProtocolCodecFactory {
	private final ProtocolEncoder encoder = new FlightClientEncoder();
	private final ProtocolDecoder decoder = new FlightClientDecoder();
	
	/* (non-Javadoc)
	 * @see org.apache.mina.filter.codec.ProtocolCodecFactory#getDecoder(org.apache.mina.core.session.IoSession)
	 */
	@Override
	public ProtocolDecoder getDecoder(IoSession arg0) throws Exception {
		return decoder;
	}

	/* (non-Javadoc)
	 * @see org.apache.mina.filter.codec.ProtocolCodecFactory#getEncoder(org.apache.mina.core.session.IoSession)
	 */
	@Override
	public ProtocolEncoder getEncoder(IoSession arg0) throws Exception {
		return encoder;
	}

}
