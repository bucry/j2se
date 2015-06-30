package com.xcomm.Encoding;


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
public class TbUserTableServerCodecFactory implements ProtocolCodecFactory {
	private final ProtocolEncoder encoder = new TbUserTableServerEncoder();
	private final ProtocolDecoder decoder = new TbUserTableServerDecoder();

	@Override
	public ProtocolDecoder getDecoder(IoSession session) throws Exception {
		return decoder;
	}

	@Override
	public ProtocolEncoder getEncoder(IoSession session) throws Exception {
		return encoder;
	}

}
