package com.bfchuan.view;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

import com.bfchuan.controller.TaskController;
import com.bfchuan.downloader.PieceManager;
import com.bfchuan.entities.FilePiece;
import com.bfchuan.entities.Task;
import com.bfchuan.entities.TaskShowBean;
import com.bfchuan.util.TaskUtil;

/**
 * 信息显示区域
 * @author Administrator
 *
 */
public class DownloaderMsgArea extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTabbedPane tabbedPane;
	private JTextArea msgArea;
	/**
	 * 分模块显示List的模型
	 */
	private DefaultListModel pieceListModel;
	private JList pieceList;
	/**
	 * 该区域所用于显示的任务
	 */
    TaskController taskController;
	
	public DownloaderMsgArea(){
		init();
	}
	
	/**
	 * 初始化
	 */
	public void init(){
		tabbedPane = new JTabbedPane();
		
		/*信息文本区*/
		msgArea = new JTextArea();
		msgArea.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(msgArea);
		
		/*分块显示区*/
		pieceListModel = new DefaultListModel();
		pieceList = new JList(pieceListModel);
		pieceList.setCellRenderer(new PieceListCellRenderer());
		//jsp2.getViewport().setView(list);
		pieceList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		pieceList.setVisibleRowCount(1);
		JScrollPane scrollPane2 = new JScrollPane(pieceList);
		
		tabbedPane.add(scrollPane,"任务信息");
		tabbedPane.add(scrollPane2,"分块下载信息");
		
		this.setLayout(new BorderLayout());
		this.add(tabbedPane,"Center");
	}
	
	/**
	 * 显示任务信息
	 */
	public void showMsg(){
		StringBuilder sb = new StringBuilder();
		if(taskController == null){
			sb.append("欢迎使用迅杭下载器");
		}else{
			Task task = taskController.getTask();
			TaskShowBean tsb = TaskUtil.TaskToShowTask(task);
			sb.append("文件名：" + tsb.getFileName());
			sb.append("\n");
			sb.append("状态：" + tsb.getStatus());
			sb.append("\n");
			sb.append("文件大小：" + tsb.getFileSize());
			sb.append("\n");
			sb.append("进度：" + tsb.getProgressRate());
			sb.append("\n");
			sb.append("速度：" + tsb.getSpeed());
			sb.append("\n");
			sb.append("开始时间：" + tsb.getBeginTime());
			sb.append("\n");
			sb.append("完成时间：" + tsb.getEndTime());
			sb.append("\n");
			sb.append("用时：" + tsb.getTakesTime());
			sb.append("\n");
			sb.append("保存路径：" + tsb.getSavePath());
			sb.append("\n");
			sb.append("资源URL：" + tsb.getUrl());
			sb.append("\n");
			sb.append("下载线程数：" + tsb.getThreadSum());
			sb.append("\n");
		}	
		this.msgArea.setText(sb.toString());
	}
	
	/**
	 * 显示分块下载信息
	 */
	public void showPieceList(){
		
		if(taskController == null){
			pieceListModel.clear();
			return;
		}
		/*获取任务*/
		Task task = taskController.getTask();
		if(task.getStatus() == Task.STATE_COMPLETED || task.getStatus() == Task.STATE_FAILED){
			pieceListModel.clear();
			return;
		}
		/*获取分块管理者*/
		PieceManager pm =  taskController.getPm();
		if(pm == null){
			pieceListModel.clear();
			return;
		}
		/* 获取分块信息 */
		List<FilePiece> pieces = pm.getPieces();
		if(pieces == null){
			pieceListModel.clear();
			return;
		}
		int len = pieces.size();
		for(int i=0;i<len;i++){//循环所有分块
			if(pieceListModel.getSize() < len){//分块显示的初始化尚未完成
				pieceListModel.addElement(pieces.get(i));//添加到list中
			}else {//长度已够，已完成第一次的初始化
				/*对比list块中对应的piece的状态是否改变*/
				//if(pieces.get(i) != (FilePiece)pieceListModel.get(i)){
					pieceListModel.set(i, pieces.get(i));
				//}
				
			}
			
		}
		
	}
	
	/**
	 * 设置任务控制器
	 * @param taskController
	 */
	public void setTaskController(TaskController taskController){
		this.taskController = taskController;
		this.showMsg();
		this.showPieceList();
	}
}
