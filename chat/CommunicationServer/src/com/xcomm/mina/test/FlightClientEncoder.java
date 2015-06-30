package com.xcomm.mina.test;


import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;



/**
 * @function : 
 * @author   :jy
 * @company  :万里网
 * @date     :2011-8-7
 */
public class FlightClientEncoder extends ProtocolEncoderAdapter {
	private final Charset charset;
	
	public FlightClientEncoder(){
		this.charset = Charset.forName("UTF-8");
	}

	@Override
	public void encode(IoSession session, Object message, ProtocolEncoderOutput out) throws Exception {
		Flight flight = (Flight)message;
		
		IoBuffer buf = IoBuffer.allocate(100).setAutoExpand(true);
		
		CharsetEncoder ce = charset.newEncoder();
		
		buf.putString("Flight Search 1.0" + '\n', ce);
		
		buf.putString("startcty:" + flight.getStartCity() + '\n', ce);
		
		buf.putString("endcity:" + flight.getEndCity() + '\n', ce);
		
		buf.putString("flightway:" + flight.getFlightway() + '\n', ce);
		
		buf.putString("date:" + flight.getDate() + '\n', ce);
		
		buf.flip();
		
		out.write(buf);
	}

}
