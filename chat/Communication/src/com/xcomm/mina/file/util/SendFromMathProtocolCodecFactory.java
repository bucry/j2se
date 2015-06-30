package com.xcomm.mina.file.util;


import org.apache.mina.filter.codec.demux.DemuxingProtocolCodecFactory;

/**
 * 客户端传送文件编码/解码工厂类
 * 作者：Leonidas 
 * piaobomengxiang@163.com
 * 时间：2013-9-14
 * 版本：1.0
 * 描述：客户端通过Mina框架上传文件
 * 文件通过流化后传送
 * 该类用于提供编码/解码类实例
 */
public class SendFromMathProtocolCodecFactory extends DemuxingProtocolCodecFactory{
	
	public SendFromMathProtocolCodecFactory(boolean server){
		if(server){
			super.addMessageDecoder(SendFromBaseMessageDecoder.class);
		}else{
			super.addMessageEncoder(SendFromBaseMessage.class, SendFromBaseMessageEncoder.class);
		}
	}
}
