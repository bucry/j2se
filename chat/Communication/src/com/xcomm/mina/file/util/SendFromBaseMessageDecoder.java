package com.xcomm.mina.file.util;


import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.AttributeKey;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.apache.mina.filter.codec.demux.MessageDecoder;
import org.apache.mina.filter.codec.demux.MessageDecoderResult;


/**
 * 客户端传送文件解码类
 * 作者：Leonidas 
 * piaobomengxiang@163.com
 * 时间：2013-9-14
 * 版本：1.0
 * 描述：客户端通过Mina框架上传文件
 * 文件通过流化后传送
 * 该类用于解码
 */

public class SendFromBaseMessageDecoder  implements MessageDecoder {
	private AttributeKey CONTEXT = new AttributeKey(getClass(), "context");
	/**
	 * 是否适合解码
	 * */
	public MessageDecoderResult decodable(IoSession session, IoBuffer in) {
		// TODO Auto-generated method stub
		Context context = (Context) session.getAttribute(CONTEXT);
		if(context == null){
			context = new Context();
			context.dataType = in.getInt();
			if(context.dataType == SendFromBeanUtil.UPLOAD_FILE){
				context.strLength = in.getInt();
				context.byteStr = new byte[context.strLength];
				context.fileSize = in.getInt();
				context.byteFile = new byte[context.fileSize];
				session.setAttribute(CONTEXT, context);
				return MessageDecoderResult.OK;
			}else{
				return MessageDecoderResult.NOT_OK;
			}
		}else{
			if(context.dataType == SendFromBeanUtil.UPLOAD_FILE){
				return MessageDecoderResult.OK;
			}else{
				return MessageDecoderResult.NOT_OK;
			}
		}
	}

	/**
	 * 数据解码
	 * */
	public MessageDecoderResult decode(IoSession session, IoBuffer in,
			ProtocolDecoderOutput outPut) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("开始解码：");
		Context context = (Context) session.getAttribute(CONTEXT);
		if(!context.init){
			context.init = true;
			in.getInt();
			in.getInt();
			in.getInt();
		}
		byte[] byteFile = context.byteFile;
		int count = context.count;
		while(in.hasRemaining()){
			byte b = in.get();
			if(!context.isReadName){
				context.byteStr[count] = b;
				if(count == context.strLength-1){
					context.fileName = new String(context.byteStr,SendFromBeanUtil.charset);
					System.out.println(context.fileName);
					count = -1;
					context.isReadName = true;
				}
			}
			if(context.isReadName && count != -1){
				byteFile[count] = b;
			}
		//	byteFile[count] = b;
			count++;
		}
		context.count = count;
		System.out.println("count:"+count);
		System.out.println("context.fileSize:"+context.fileSize);
		session.setAttribute(CONTEXT, context);
		if(context.count == context.fileSize){
			SendFromBaseMessage message = new SendFromBaseMessage();
			message.setDataType(context.dataType);
			SendFromFileBean bean = new SendFromFileBean();
			bean.setFileName(context.fileName);
			bean.setFileSize(context.fileSize);
			bean.setFileContent(context.byteFile);
			message.setData(bean);
			outPut.write(message);
			context.reset();
		}
		return MessageDecoderResult.OK;
	}

	/**
	 * 
	 * */
	public void finishDecode(IoSession session, ProtocolDecoderOutput outPut)
			throws Exception {
		// TODO Auto-generated method stub
		System.out.println("end:::::::::::::::::");
	}
	private class Context{
		public int dataType;
		public byte[] byteFile;
		public int count;
		public int strLength;
		public boolean isReadName;
		public int fileSize;
		public byte[] byteStr;
		public String fileName;
		public boolean init = false;
		
		public void reset(){
			dataType = 0;
			byteFile = null;
			count = 0;
			strLength = 0;
			isReadName = false;
			fileSize = 0;
			byteStr = null;
			fileName = null;
			
		}
	}


}
