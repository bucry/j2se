package com.bfchuan.entities;

import java.io.Serializable;

/**
 * 资源文件分块下载的实体类
 * @author Administrator
 *
 */
public class FilePiece implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 *分块处于空闲状态
	 */
	public static final int PIECE_LEISURE = 0;
	/**
	 * 分块处于忙碌状态
	 */
	public static final int PIECE_BUSY = 1;
	/**
	 * 分块处于完成状态
	 */
	public static final int PIECE_COMPLETE = 2;
	/**
	 * 分块的状态,取值范围（0、1、2），分别对应（空闲、忙碌、完成）
	 */
	private int status;
	/**
	 * 分块的名字
	 */
	private String pieceName;
	/**
	 * 分块的总大小
	 */
	private long pieceSize;
	/**
	 * 分块的开始点
	 */
	private long startPoint;
	/**
	 * 当前下载到的点
	 */
	private long nowPoint;
	/**
	 * 分块的结束点
	 */
	private long endPoint;
	
	//--------------------以下是相应的get、set方法-------------------------
	
	/**
	 * 获取当前块状态
	 */
	public int getStatus() {
		return status;
	}
	/**
	 * 设置当前块的状态
	 * @param status
	 */
	public void setStatus(int status) {
		this.status = status;
	}
	/**
	 * 获取块长度
	 * @return
	 */
	public long getPieceSize() {
		return pieceSize;
	}
	/**
	 * 设置块长度
	 * @param pieceSize
	 */
	public void setPieceSize(long pieceSize) {
		this.pieceSize = pieceSize;
	}
	/**
	 * 获取开始点
	 * @return
	 */
	public long getStartPoint() {
		return startPoint;
	}
	/**
	 * 设置开始点
	 * @param startPoint
	 */
	public void setStartPoint(long startPoint) {
		this.startPoint = startPoint;
	}
	/**
	 * 获得当前点
	 * @return
	 */
	public long getNowPoint() {
		return nowPoint;
	}
	/**
	 * 设置当前点
	 * @param nowPoint
	 */
	public void setNowPoint(long nowPoint) {
		this.nowPoint = nowPoint;
	}
	/**
	 * 获取结束点
	 * @return
	 */
	public long getEndPoint() {
		return endPoint;
	}
	/**
	 * 设置结束点
	 * @param endPoint
	 */
	public void setEndPoint(long endPoint) {
		this.endPoint = endPoint;
	}
	/**
	 * 获取该块的名字
	 * @return
	 */
	public String getPieceName() {
		return pieceName;
	}
	/**
	 * 设置该块的名字
	 * @param pieceName
	 */
	public void setPieceName(String pieceName) {
		this.pieceName = pieceName;
	}
	
}
