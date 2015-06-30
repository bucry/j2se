package com.bfchuan.view;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;


import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import com.bfchuan.controller.CentralController;
import com.bfchuan.controller.TaskController;
import com.bfchuan.entities.Task;
import com.bfchuan.entities.TaskShowBean;
import com.bfchuan.util.TaskUtil;

/**
 * 主窗口中的任务table
 * @author Administrator
 *
 */
public class DownloaderTable extends JTable{
	
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 中央控制器
	 */
	CentralController centralController;
	/**
	 * table模型
	 */
    AbstractTableModel tableModel;
    /**
     * 右键菜单
     */
    DownloaderPopupMenu popupMenu;
	
//	/**
//	 * table正在显示的task
//	 */
//	Map<TaskController, Thread> ctrlThdMap;
    
    /**
     * table正在显示的任务组，放到set中
     */
    Set<TaskController> taskControllerSet;

	/**
	 * 有参构造器，通过制定的AbstractTableModel创建table
	 * @param tableModel
	 */
	public DownloaderTable(AbstractTableModel tableModel){
		super(tableModel);
		this.tableModel = tableModel;
		init();
	}
	
	/**
	 * 初始化
	 */
	private void init(){
		//this.setBackground(new Color(250, 250, 250));
		this.getTableHeader().setReorderingAllowed(false);
		this.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		this.addListeners();
		
		/*进度条*/
		this.getColumn("进度").setCellRenderer(new ProgressRenderer());
	}
	
	/**
	 * 添加监听
	 */
	private void addListeners(){
		this.addMouseListener(new MouseAdapter() { // table的鼠标监听
					public void mouseClicked(MouseEvent e) {
						if (e.getClickCount() == 1) { //鼠标左键
							int index = ((JTable) e.getSource()).rowAtPoint(e.getPoint());
							if (index >= 0&& ((JTable) e.getSource()).isRowSelected(index)){
								tableSelected();
							}
						}
						if (e.getButton() == 3) { //鼠标右键
							System.out.println("right_table");
							int row = rowAtPoint(e.getPoint());
							requestFocus();
							changeSelection(row, 1, false, false);
							tableSelected();
							if(popupMenu != null){
								popupMenu.show(DownloaderTable.this, e.getX(), e.getY());
							}	
						}
					}

				});
	}
	
//	@Override
//	public void valueChanged(ListSelectionEvent e) {
//		System.out.println("table选择改变");
//		this.tableSelected();
//	}

	/**
	 * table被选择了，执行相应操作
	 */
	public void tableSelected(){
		if(taskControllerSet == null || taskControllerSet.size() == 0){//map为空
			this.centralController.changeTableSelect(null);
			return;
		}
		int selectedRow = this.getSelectedRow();
		if(selectedRow == -1){//没有任何行被选择
			return;  //不做任何操作
		}else{
			this.centralController.changeTableSelect(matchRowToTaskController(selectedRow));
		}
	}

	/**
	 * 将选定行匹配到对应的taskController，根据URL是否相等
	 */
	public TaskController matchRowToTaskController(int selectedRow){
		int urlColumn = 8;//url对应的列
		String selectedRowUrl = (String) this.getValueAt(selectedRow, urlColumn);
		if(selectedRowUrl != null && this.taskControllerSet != null){ //选中的该行步为空行
			for(Iterator<TaskController> it = this.taskControllerSet.iterator();it.hasNext();){
				TaskController taskController = it.next();
				Task task = taskController.getTask();
				if(task.getSourceUrl().equals(selectedRowUrl)){ //找到相等的url
					return taskController;
				}
			}
		}
		return null;//未找到相等的url，即选择该行是不对应任何任务的空行
	}
	
	/**
	 * 显示table，根据传来的Map,为了支持旧版本，暂时保留该方法
	 * @param ctrlThdMap
	 */
	public void showTable(Map<TaskController, Thread> ctrlThdMap) {
		Set<TaskController> controllerSet =  ctrlThdMap.keySet();
		//this.clearTable();  //清空table
		this.showTable(controllerSet);
	}
	
	/**
	 * 根据传来的任务set显示任务
	 * @param taskController
	 */
	public void showTable(Set<TaskController> taskControllerSet){
		this.taskControllerSet = taskControllerSet;
		this.clearTable();  //清空table
		int row = 0;
		for(Iterator<TaskController> it = taskControllerSet.iterator();it.hasNext();row++){
			TaskController tc = it.next();
			Task task = tc.getTask();
			TaskShowBean tsb = TaskUtil.TaskToShowTask(task);
			tableModel.setValueAt(tsb.getStatus(), row, 0);   //（内容、行、列）
			tableModel.setValueAt(tsb.getFileName(),row, 1);
			tableModel.setValueAt(tsb.getFileSize(), row, 2);
			//tableModel.setValueAt(tsb.getProgressRate(), row, 3);
			tableModel.setValueAt(Math.round(task.getProgressRate()*100), row, 3);
			tableModel.setValueAt(tsb.getSpeed(), row, 4);
			tableModel.setValueAt(tsb.getBeginTime(), row, 5);
			tableModel.setValueAt(tsb.getTakesTime(), row, 6);
			tableModel.setValueAt(tsb.getSavePath(), row, 7);
			tableModel.setValueAt(tsb.getUrl(), row, 8);
			tableModel.setValueAt(tsb.getThreadSum(), row, 9);
		}
	}
	
//	/**
//	 * 显示table，自动检测视图情况，并作出相应显示
//	 */
//	public void showTable(){
//		System.out.println("showTable");
//		if(contrller.getFrameStatus() == MainController.RUNNING_VIEW){ //下载中
//			this.ctrlThdMap = contrller.getCtrlThdMap();
//			this.showTable(ctrlThdMap);
//		}else if(contrller.getFrameStatus() == MainController.COMPLETE_VIEW){  //已完成
//			this.ctrlThdMap = contrller.getCompletedMap();
//			this.showTable(ctrlThdMap);
//		}else if(contrller.getFrameStatus() == MainController.GARBAGEBIN_VIEW){//垃圾箱
//			this.ctrlThdMap = contrller.getGarbageBinMap();
//			this.showTable(ctrlThdMap);
//		}
//		
//	}
	
	/**
	 * 清空table
	 */
	public void clearTable(){
		 for(int row = 0;row < tableModel.getRowCount();row++){
			 for(int col = 0; col < tableModel.getColumnCount();col++){
				 tableModel.setValueAt(null, row, col);
			 }
		 }
	}
	
	/**
	 * 添加中央控制器
	 * @param centralController
	 */
	public void addCentralContoller(CentralController centralController){
		if(this.centralController == null){
			this.centralController = centralController;
		}
	}
	
	/**
	 * 添加右键菜单
	 */
	public void addPopupMenu(DownloaderPopupMenu popupMenu){
		this.popupMenu = popupMenu;
	}
}
