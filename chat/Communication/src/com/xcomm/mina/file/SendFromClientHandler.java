package com.xcomm.mina.file;


import java.io.File;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

import com.xcomm.mina.file.util.SendFromBaseMessage;
import com.xcomm.mina.file.util.SendFromBeanUtil;
import com.xcomm.mina.file.util.SendFromFileBean;
import com.xcomm.mina.file.util.SendFromFileHelper;


/**
 * 客户端传送文件辅助类
 * 作者：Leonidas 
 * piaobomengxiang@163.com
 * 时间：2013-9-14
 * 版本：1.0
 * 描述：客户端通过Mina框架上传文件
 * 文件通过流化后传送
 */
public class SendFromClientHandler extends IoHandlerAdapter{
	
	/**
	 * 客户端接收到信息
	 * */
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		// TODO Auto-generated method stub
		super.messageReceived(session, message);
		
	}

	public void sessionOpened(IoSession session) { 
		SendFromBaseMessage baseMessage = new SendFromBaseMessage();
		baseMessage.setDataType(SendFromBeanUtil.UPLOAD_FILE);
		SendFromFileBean bean = new SendFromFileBean();
		File file = new File("e:\\log.log");
		bean.setFileName(file.getName());
		bean.setFileSize((int)file.length());
		try {
			SendFromFileHelper helper =new SendFromFileHelper();
			bean.setFileContent(helper.getContent(file));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		baseMessage.setData(bean);
		session.write(baseMessage); 
	}

	public void sessionCreated(IoSession session) throws Exception {
		// TODO Auto-generated method stub
		super.sessionCreated(session);
	}
	
	
}
