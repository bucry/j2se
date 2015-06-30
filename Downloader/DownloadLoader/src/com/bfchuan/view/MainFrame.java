package com.bfchuan.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.table.AbstractTableModel;

import com.bfchuan.controller.CentralController;
import com.bfchuan.controller.TaskController;
import com.bfchuan.util.Global;

/**
 * 主窗口类
 * @author Administrator
 *
 */

public class MainFrame extends JFrame{
	
	private static final long serialVersionUID = 1L;
	private static MainFrame mainFrame = null;
//	/*日志记录*/
//	static Logger logger = Logger.getLogger(MainFrame.class);
	/**
	 * 中央控制器
	 */
	 CentralController centralController;
	 DownloaderMenuBar menuBar;
	 DownloaderToolBar toolBar;
	 DownloaderTree tree;
	 AbstractTableModel tablemodel;
	 DownloaderTable table;
	 DownloaderPopupMenu popupMenu;
	 DownloaderMsgArea msgArea;
	 DownloaderFoot foot;
	 DownloaderDialog dialog;
	 DownloaderAboutDialog aboutDialog;
	 DownloaderIntroductionDialog introductionDialog;
	
	 /**
	  * 获取MainFrame的实例，单例模式
	  * @return
	  */
	 public static MainFrame getInstance(){
		 if(mainFrame == null){
			 mainFrame = new MainFrame();
		 }
		 return mainFrame;
	 }
	 
	/**
	 * 构造器，创建mainFrame的同时得给其配套一个mainController
	 */
	private MainFrame(){
		super(Global.VERSION_NAME);
	}
	
	/**
	 * 初始化
	 */
	public void init(){
		
		 /*设置主界面的布局*/
		this.setLayout(new BorderLayout());
		
		 /*添加menuBar*/
		 menuBar = new DownloaderMenuBar();
		 menuBar.addCentralContoller(centralController);
		 this.setJMenuBar(menuBar);
		 
		 /*添加toolBar*/ 
		 toolBar = new DownloaderToolBar();
		 toolBar.addCentralContoller(centralController);
		 // toolBar.addTaskListener(controller);
		 this.add(toolBar,"North");
		
		 /*添加主体*/
		JSplitPane center = buildCenterPane();
		this.add(center,"Center");
		
	     /*添加foot*/
		foot = new DownloaderFoot();
		this.add(foot,"South");
		
		/*设置主窗口的关闭属性，关闭前的动作*/
		//this.setDefaultCloseOperation(3);// 设置用户在此窗体上发起 "close" 时默认执行的操作。
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);   
		this.addWindowListener(new WindowAdapter() {   
		            public void windowClosing(WindowEvent e) {   
		               centralController.exit();
		            }   
		        });  
		 /*设置主窗口其他属性*/
		this.setSize(1000, 700);
		this.setLocation(120, 100);// 将组件移到新位置
		this.setVisible(true);
		
//		/*显示以前的任务*/
//		this.showTable();
	}
	
	/**
	 *  建造主体
	 * @return
	 */
	private JSplitPane buildCenterPane() {
		// 创建一个配置为指定方向且无连续布局的新 JSplitPane,newOrientation -
		// JSplitPane.HORIZONTAL_SPLIT 或 JSplitPane.VERTICAL_SPLIT
		JSplitPane centerPane = new JSplitPane(1);
		centerPane.setResizeWeight(0.2);
		
		/*主体左边，文件树*/
		centerPane.setRightComponent(getRightPane());
		
		/*主体右边，table和消息栏*/
		centerPane.setLeftComponent(getLeftPane());
		
		/*设置主题其他属性*/
		centerPane.setContinuousLayout(false);// 如果分隔条改变位置时组件连续重绘，则为 true
		centerPane.setDividerSize(8);// 设置分隔条的大小。
		centerPane.setOneTouchExpandable(true);// 在分隔条上提供一个 UI 小部件来快速展开/折叠分隔条
		return centerPane;
	}
	
	/**
	 * 建造主体的左边，文件树
	 * @return
	 */
	private JScrollPane getLeftPane() {
		this.tree = new DownloaderTree();
		JScrollPane treeView = new JScrollPane(tree);
		tree.addCentralContoller(centralController);
		return treeView;
	}
	
	/**
	 * 建造主体右边
	 * @return
	 */
	private JPanel getRightPane() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBackground(new Color(240, 245, 250));
		
		/*主体右边包括table和消息框，也是由JSplitPane划分*/
		JSplitPane rightPane = new JSplitPane(0);
		rightPane.setResizeWeight(0.2);
		
		/*table*/
		rightPane.setLeftComponent(getUpPane());
		
		/*消息框*/
		rightPane.setRightComponent(getDownPane());
		rightPane.getRightComponent().setBackground(new Color(248,248,248));
		
		panel.add(rightPane,BorderLayout.CENTER);
		return panel;
	}
	
	/**
	 * 获取主体消息框
	 * @return
	 */
	private JPanel getDownPane() {
		msgArea = new DownloaderMsgArea();
		return msgArea;
	}
	
	/**
	 * 获取主体table
	 * @return
	 */
	private JPanel getUpPane(){
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBackground(new Color(248,248,248));
		
		/*table模型*/
		tablemodel = new DownloaderTableModel();
		
		/*用模型建造table*/
	    table = new DownloaderTable(tablemodel);
	    table.addCentralContoller(centralController);
	    
	    /*为table添加右键菜单*/
	    popupMenu = new DownloaderPopupMenu();
	    popupMenu.addCentralContoller(centralController);
	    table.addPopupMenu(popupMenu);
	    
	    /*装入table*/
		JScrollPane scrollPane = new JScrollPane(table);
		
		scrollPane.setBackground(new Color(248,248,248));
		
		panel.add(scrollPane, BorderLayout.CENTER);
		return panel;
	}
	
	/**
	 * 显示table，根据传来的任务
	 * @param ctrlThdMap
	 */
	public void showTable(Map<TaskController,Thread> ctrlThdMap){
		this.table.showTable(ctrlThdMap);
	}
	
//	/**
//	 * 此方法调用table的showTable方法，会自动识别当前视图，作出相应显示
//	 */
//	public void showTable(){
//		this.table.showTable();
//	}
	
	/**
	 * 新建任务
	 */
	public void newTask(){
		 if(dialog == null || dialog.isVisible() == false){
			 dialog = new DownloaderDialog(this);
			 dialog.addCentralContoller(centralController);
		 }
	}
	
	/**
	 * 根据传来的任务管理器显示按钮的状态
	 * @param taskController
	 */
	public void setButtonStatus(TaskController taskController){
		this.toolBar.setTaskController(taskController);
	}
	
	/**
	 * 显示“关于”
	 */
	public void showAbout(){
		if(this.aboutDialog == null || this.aboutDialog.isVisible() == false){
			aboutDialog = new DownloaderAboutDialog(this);
		}
	}
	
	/**
	 * 显示功能介绍
	 */
	public void showIntroduction(){
		if(this.introductionDialog == null || this.introductionDialog.isVisible() == false){
			introductionDialog = new DownloaderIntroductionDialog(this);
		}
	}
	
	/**
	 * 根据传来的任务管理器显示消息区域的状态
	 * @param taskController
	 */
	public void setMsgAreaStatus(TaskController taskController){
		this.msgArea.setTaskController(taskController);
	}
	
	/**
	 * 根据传来的任务管理器设置给popupMenu
	 * @param taskController
	 */
	public void setPopupMenuStatus(TaskController taskController){
		this.popupMenu.setTaskController(taskController);
	}
	
	/**
	 * 添加中央控制器
	 * @param centralController
	 */
	public void addCentralContoller(CentralController centralController){
		//if(this.centralController == null){
			this.centralController = centralController;
		//}
	}
	
	//-------------------get、set---------------------
	
	/**
	 * 设置弹出对话框
	 */
	public void setDialog(DownloaderDialog dialog) {
		this.dialog = dialog;
	}
	
	/**
	 * 设置信息显示区域
	 * @return
	 */
	public DownloaderMsgArea getMsgArea() {
			return msgArea;
	}
}
