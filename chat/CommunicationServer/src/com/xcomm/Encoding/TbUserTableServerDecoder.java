package com.xcomm.Encoding;


import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

import com.xcomm.entity.TbUserTable;


/**
 * 服务端解码器
 * 作者：Leonidas 
 * piaobomengxiang@163.com
 * 时间：2013-9-13
 * 版本：1.0
 * 描述：服务端解码器，将字节流按照固定编码方式实现编码
 * 必须自定义实现，默认编码方式客户端与服务器不同
 * 会导致解码错误，无法实现流传送
 */
public class TbUserTableServerDecoder extends CumulativeProtocolDecoder {

	@Override
	protected boolean doDecode(IoSession session, IoBuffer in, ProtocolDecoderOutput out) throws Exception {
		
		
		System.out.println("服务器解码开始开始--------");
		
		
		IoBuffer buf = IoBuffer.allocate(100).setAutoExpand(true);
		CharsetDecoder cd = Charset.forName("UTF-8").newDecoder();
		
		int ColumnNumber = 0;
		String status="",startCity="",endCity="",flightway="",date="";
		
		int TextLineNumber = 1;
		
		TbUserTable tbUserTable = new TbUserTable();

		while(in.hasRemaining()){
			byte b = in.get();
			buf.put(b);
			if(b == 10 && TextLineNumber <= 5){
				ColumnNumber++;
				if(TextLineNumber == 1){
					buf.flip();
					status = buf.getString(ColumnNumber, cd);
					tbUserTable.setTtId(status);
				}
				
				if(TextLineNumber == 2){
					buf.flip();
					startCity = buf.getString(ColumnNumber, cd);
					/*startCity = buf.getString(ColumnNumber, cd).split(":")[1];
					startCity = startCity.substring(0, startCity.length()-1);*/
					tbUserTable.setPassWd(startCity);
				}
				
				if(TextLineNumber == 3){
					buf.flip();
					endCity = buf.getString(ColumnNumber, cd).split(":")[1];
					endCity = endCity.substring(0, endCity.length()-1);
					System.out.println(endCity);
				}
				
				if(TextLineNumber == 4){
					buf.flip();
					flightway = buf.getString(ColumnNumber, cd).split(":")[1];
					flightway = flightway.substring(0, flightway.length()-1);
					System.out.println(flightway);
				}
				
				if(TextLineNumber == 5){
					buf.flip();
					date = buf.getString(ColumnNumber, cd).split(":")[1];
					date = date.substring(0, date.length()-1);
					System.out.println(date);
					break;
				}
				
				ColumnNumber = 0;
				buf.clear();
				TextLineNumber++;
			}else{
				ColumnNumber++;
			}
		}
		
		System.out.println("服务器解码结束--------");
		out.write(tbUserTable);
		return false;
	}

}
