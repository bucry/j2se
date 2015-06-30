package com.bfchuan.downloader;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.bfchuan.entities.FileAccess;
import com.bfchuan.entities.FilePiece;

 /**
  * 分块下载类，由分块管理者分配分块任务
  * @author Administrator
  *
  */
public class PieceThread extends Thread implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	PieceManager pm;

	public PieceThread(PieceManager pm) {
		this.pm = pm;
	}

	/**
	 * 当前下载块
	 */
	private FilePiece currentPiece;
	
	/**
	 * 该线程是否停止了，缺省值为false
	 */
	private boolean isStop = false;
	
	/**
	 * 线程是否失败，缺省为false
	 */
	private boolean isFailed = false;
	
	/**
	 * 分块启动，开始下载
	 */
	@Override
	public void run() {
		
		/*循环下载，直到分块池中的分块全部下载完成*/
		while (pm.getPiecesPoolStatus() != PieceManager.PIECES_COMPLETE && !isStop) {
			
			/* 获取该线程要下载的分块 */
			currentPiece = pm.assignPiece();
			
			if(currentPiece == null){//假如分配到的是piece为null，则退出循环
				break;
			}
			
			/* 将该分块的状态设置为“忙碌” */
			currentPiece.setStatus(FilePiece.PIECE_BUSY);
			
			System.out.println(Thread.currentThread() + " -- "+ currentPiece.getPieceName());
		
			try {
				/* 获取URL */
				URL url = new URL(pm.getTask().getSourceUrl());
						
				/*获取随机存储文件*/
				FileAccess fileAccess = new FileAccess(pm.getTask().getSavePath()+"/"+pm.getTask().getFileName());
				
				/*获取http协议的连接*/
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				
				/*通过连接申请文件开始点*/
				conn.setRequestProperty("RANGE", "bytes=" + currentPiece.getStartPoint()+ "-" +currentPiece.getEndPoint());
				
				/*获取文件流*/
				InputStream is = null;
				BufferedInputStream bis = null;
				is = conn.getInputStream();
				bis = new BufferedInputStream(is);
				
				/*开辟字节缓冲区*/
				byte[] buffer = new byte[1024];
				
				/*设置存盘文件的起始点*/
				fileAccess.setPosition(currentPiece.getStartPoint());
				
				int b = -1; //做获取到的资源偏移量标记
				
				/*做该块是否结束的标记*/
				boolean isPieceComplete = false;
				
				/*循环将该分块从连接中写入文件*/
				while((b = bis.read(buffer)) != -1 && !isPieceComplete && !isStop){
					if(currentPiece.getStartPoint() + b > currentPiece.getEndPoint()){ //若偏移量超出了该分块的边界
						b = (int)(currentPiece.getEndPoint() - currentPiece.getStartPoint());
					}
					/*将读到的文件偏移量从缓冲区写入文件*/
					fileAccess.write(buffer, 0, b); 
					
					/*重新设置该块的下载起始点*/
					currentPiece.setStartPoint(currentPiece.getStartPoint() + b);
					
					if(currentPiece.getStartPoint() == currentPiece.getEndPoint()){//若该块的起始点等于结束点，表示该块下载完成
						currentPiece.setStatus(FilePiece.PIECE_COMPLETE);
						System.out.println("----完成"+currentPiece.getPieceName());
						isPieceComplete = true;
					}
				}
				
				/*关闭流及连接*/
				is.close();
				bis.close();
				fileAccess.close();
				conn.disconnect();
				
			} catch (MalformedURLException e) {
				//该线程下载失败，设置该块为空闲
				this.setFailed(true);
				currentPiece.setStatus(FilePiece.PIECE_LEISURE);
				e.printStackTrace();
			} catch (IOException e) {
				this.setFailed(true);
				currentPiece.setStatus(FilePiece.PIECE_LEISURE);
				e.printStackTrace();
			}	
		}
	}

	/**
	 * 暂停
	 */
	public void stopMe(){
		/*设置当前下载块为空闲*/
		if(currentPiece != null){
			this.currentPiece.setStatus(FilePiece.PIECE_LEISURE);
		}
		
		/*设置线程状态为 停止*/
		this.setStop(true);
		
		/*中断该线程*/
		this.interrupt();
	}

	
	//------------------get、set----------------
	/**
	 * 返回分块线程是否停止
	 */
	public boolean isStop() {
		return isStop;
	}

	/**
	 * 设置分块线程的停止状态
	 * @param isStop
	 */
	public void setStop(boolean isStop) {
		this.isStop = isStop;
	}
	

	/**
	 * 返回分块线程是否失败
	 * @return
	 */
	public boolean isFailed() {
		return isFailed;
	}

	/**
	 * 设置分块线程的失败状态
	 * @param isFailed
	 */
	public void setFailed(boolean isFailed) {
		this.isFailed = isFailed;
	}

}
