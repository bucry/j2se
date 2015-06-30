package com.xcomm.mina.test;


import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderAdapter;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;


/**
 * @function : 
 * @author   :jy
 * @company  :万里网
 * @date     :2011-8-7
 */
public class FlightDecoder extends CumulativeProtocolDecoder {

	@Override
	protected boolean doDecode(IoSession session, IoBuffer in, ProtocolDecoderOutput out) throws Exception {
		IoBuffer buf = IoBuffer.allocate(100).setAutoExpand(true);
		CharsetDecoder cd = Charset.forName("UTF-8").newDecoder();
		
		int ColumnNumber = 0;
		String status="",startCity="",endCity="",flightway="",date="";
		
		int TextLineNumber = 1;
		
		Flight flight = new Flight();

		/**
		 * FlightSearch 1.0 \n
		 * startcity:BJS \n
		 * endcity:PEK \n
		 * flightway:1 \n
		 * date:2011-08-10 \n
		 */
		while(in.hasRemaining()){
			byte b = in.get();
			buf.put(b);
			if(b == 10 && TextLineNumber <= 5){
				ColumnNumber++;
				if(TextLineNumber == 1){
					buf.flip();
					status = buf.getString(ColumnNumber, cd);
				}
				
				if(TextLineNumber == 2){
					buf.flip();
					startCity = buf.getString(ColumnNumber, cd).split(":")[1];
					startCity = startCity.substring(0, startCity.length()-1);
					flight.setStartCity(startCity);
				}
				
				if(TextLineNumber == 3){
					buf.flip();
					endCity = buf.getString(ColumnNumber, cd).split(":")[1];
					endCity = endCity.substring(0, endCity.length()-1);
					flight.setEndCity(endCity);
				}
				
				if(TextLineNumber == 4){
					buf.flip();
					flightway = buf.getString(ColumnNumber, cd).split(":")[1];
					flightway = flightway.substring(0, flightway.length()-1);
					flight.setFlightway(flightway);
				}
				
				if(TextLineNumber == 5){
					buf.flip();
					date = buf.getString(ColumnNumber, cd).split(":")[1];
					date = date.substring(0, date.length()-1);
					flight.setDate(date);
					break;
				}
				
				ColumnNumber = 0;
				buf.clear();
				TextLineNumber++;
			}else{
				ColumnNumber++;
			}
		}
		out.write(flight);
		return false;
	}

}
