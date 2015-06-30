package com.xcomm.mina.file.util;


import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.apache.mina.filter.codec.demux.MessageEncoder;


/**
 * 客户端传送文件编码
 * 作者：Leonidas 
 * piaobomengxiang@163.com
 * 时间：2013-9-14
 * 版本：1.0
 * 描述：客户端通过Mina框架上传文件
 * 文件通过流化后传送
 * 该类用于编码
 */

public class SendFromBaseMessageEncoder implements MessageEncoder<SendFromBaseMessage> {

	/**
	 * 基本信息编码
	 * */
	public void encode(IoSession session, SendFromBaseMessage message,ProtocolEncoderOutput outPut) throws Exception {
		// TODO Auto-generated method stub
		IoBuffer buffer = IoBuffer.allocate(1024*1024*50); 
		buffer.putInt(message.getDataType());
		SendFromFileBean bean = (SendFromFileBean) message.getData();
		byte[] byteStr = bean.getFileName().getBytes(SendFromBeanUtil.charset);
		buffer.putInt(byteStr.length);
		buffer.putInt(bean.getFileSize());
		buffer.put(byteStr);
		buffer.put(bean.getFileContent());
		buffer.flip();
		outPut.write(buffer);
		System.out.println("编码完成！");
	}

}
