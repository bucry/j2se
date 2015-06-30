package com.bfchuan.downloader;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.bfchuan.entities.FilePiece;
import com.bfchuan.entities.Task;
import com.bfchuan.util.Global;

/**
 * 分块管理者，用于管理任务的分块下载信息
 * @author Administrator
 *
 */
public class PieceManager implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 每个文件缺省时被分为20块
	 */
	public static final int PIECE_AMOUNT = Global.PIECES_SUM;  
	/**
	 * 全部完成
	 */
	public static final int PIECES_COMPLETE = 0;
	/**
	 * 无空闲块，但未完成
	 */
	public static final int PIECES_NO_LEISURE = 1;
	/**
	 * 有空闲
	 */
	public static final int PIECES_LEISURE = 2;
	
	private int piecesPoolStatus = PIECES_LEISURE;
	

	Task task = null;
	
	List<FilePiece> pieces;
	
	/**
	 * 有参构造器，根据Task创造出PieceManager，用于管理该Task的分块信息，一个Task对应一个PieceManager
	 * @param task
	 */
	public PieceManager(Task task){
		this.task = task;
		if(task.getStatus() == Task.STATE_NEW){ //任务是新建的，得先划分文件分块
			pieces = splitFile();
		}else{  //任务不是新建的，之前已经进行过分块，不需要再分
			
		}
	}
	
	/**
	 * 将task的文件分成PIECE_AMOUNT份
	 */
	public List<FilePiece> splitFile(){
		List<FilePiece> pieces = new ArrayList<FilePiece>(PIECE_AMOUNT);
		long fileSize = task.getFileSize();  //获得文件长度
		long startPoint = 0;	//
		long endPoint = 0;
		long pieceLength = fileSize/PIECE_AMOUNT;
		for(int i=0;i < PIECE_AMOUNT; i++){
			startPoint = endPoint;       //当前份的起始点是上一份的结束点
			endPoint = startPoint + pieceLength;//当前份的结束点等于起始点+每份长度
			if(i == PIECE_AMOUNT - 1){ //最后一份
				endPoint = fileSize;   //最后一份的结束点就是文件长度
			}
			FilePiece piece = new FilePiece();
			piece.setStartPoint(startPoint);
			piece.setEndPoint(endPoint);
			piece.setPieceSize(fileSize);
			piece.setStatus(FilePiece.PIECE_LEISURE);   //每个分块的初始状态都为“空闲”
			piece.setPieceName("第" + i + "份");
			pieces.add(piece);   //添加到分块池中
		}
		return pieces;
	}
	
	/**
	 * 分配分块，此方法是线程同步的
	 * @return
	 */
	public synchronized FilePiece assignPiece(){
		System.out.println("assignPiece");
		int flag_complete = 0;
		int flag_leisure = 0;
		for(Iterator<FilePiece> it = pieces.iterator();it.hasNext();){//统计分块的完成数和空闲数
			FilePiece piece = it.next();
			if(piece.getStatus() == FilePiece.PIECE_COMPLETE){//完成
				flag_complete ++;
			}else
			if(piece.getStatus() == FilePiece.PIECE_LEISURE){
				flag_leisure ++;
			}
		}
		if(flag_leisure == 0 && flag_complete != PieceManager.PIECE_AMOUNT){//未下载完，但无空闲
			this.setPiecesPoolStatus(PIECES_NO_LEISURE);
			System.out.println("未下载完，但无空闲");
		}else
		if(flag_complete == PieceManager.PIECE_AMOUNT){//全部下载完
			this.setPiecesPoolStatus(PIECES_COMPLETE);
			System.out.println("全部下载完");
		}else{
			for(Iterator<FilePiece> it = pieces.iterator();it.hasNext();){//统计分块的完成数和空闲数
				FilePiece piece = it.next();
				if(piece.getStatus() == FilePiece.PIECE_LEISURE){
					piece.setStatus(FilePiece.PIECE_BUSY);   //此块被分出，将其设置为忙碌状态
					System.out.println("assignPiece_complete");
					return piece;
				}
			}
		}
		System.out.println("assignPiece_complete_null");
		return null;
	}
	
	
	//----------------get、set-----------------

	/**
	 * 或得当前分块池的状态
	 */
	public int getPiecesPoolStatus() {
		return piecesPoolStatus;
	}

	/**
	 * 设置当前分块池的状态
	 * @param piecesPoolStatus
	 */
	public void setPiecesPoolStatus(int piecesPoolStatus) {
		this.piecesPoolStatus = piecesPoolStatus;
	}
	
	/**
	 * 获得管理者管理的任务
	 * @return
	 */
	public Task getTask() {
		return task;
	}
/**
 * 设置管理者管理的任务
 * @param task
 */
	public void setTask(Task task) {
		this.task = task;
	}

	/**
	 * 获得管理者管理的分块List
	 * @return
	 */
	public List<FilePiece> getPieces() {
		return pieces;
	}
}
