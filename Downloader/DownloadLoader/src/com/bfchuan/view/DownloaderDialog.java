package com.bfchuan.view;


import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.bfchuan.controller.CentralController;
import com.bfchuan.entities.Task;
import com.bfchuan.util.Global;

/**
 * 新建任务对话框
 * @author Administrator
 *
 */
public class DownloaderDialog extends JDialog{
	
	private static final long serialVersionUID = 1L;
	/**
	 * 此对话框依附的Frame
	 */
	MainFrame frame;
	/**
	 * 中央控制器
	 */
	CentralController centralController;
	
	private DefaultComboBoxModel urlModel;
	private DefaultComboBoxModel locationModel;
	private JComboBox urlBox;
	private JComboBox locationBox;
	private JButton btnSelect;
	private JTextField txtFileName;
	private JTextField threadField;
	private JFileChooser chooser;
	private JButton btnOk;
	private JButton btnCancel;
	
	public DownloaderDialog(MainFrame frame){
		super(frame,"新建任务窗口");
		this.frame = frame;
		init();
	}
	
	/**
	 * 初始化
	 */
	public void init(){
		
		/*获取该对话框的panel容器*/
		JPanel dialogPanel = (JPanel) this.getContentPane();
		
		/*设置对话框的布局管理器，将对话框分为内容和按钮两部分*/
		dialogPanel.setLayout(new BorderLayout(10, 10));// 水平间距，垂直间距
		
		/*设置内容模块管理器*/
		JPanel panelContent = new JPanel(new BorderLayout(10, 10));
		
		/*创建局部的布局管理器*/
		GridLayout gridLayout = new GridLayout(4, 1, 5, 5);// 行，列，水平间距，垂直间距
		
		/*创建内容模块的西边，即提示标签*/
		JPanel contentWest = new JPanel(gridLayout);
		contentWest.add(new JLabel("下载地址： "));// 下载地址：
		contentWest.add(new JLabel("保存路径： "));// 保存路径
		contentWest.add(new JLabel("文件名："));
		contentWest.add(new JLabel("线程数： "));
		
		/*url和保存地址框使用combox的形式，先建模型*/
		urlModel = new DefaultComboBoxModel();
		locationModel = new DefaultComboBoxModel();
		/*默认为空*/
		urlModel.addElement("");// 将指定组件添加到此类表的末尾
		locationModel.addElement("");
		
		/*根据模型重建url框*/
		urlBox = new JComboBox(urlModel);// 输入url的框
		urlBox.setEditable(true);// 字段可编辑
		
		/*因为保存地址框需要有“浏览”按钮，所以为了布局得再建一个panel和布局管理器管理布局*/
		JPanel locationPanel = new JPanel(new BorderLayout(5, 5));
	    locationBox = new JComboBox(locationModel);// 创建一个 JComboBox，其项取自现有的ComboBoxModel 中
		locationBox.setEditable(true);// 可编辑
		btnSelect = new JButton("浏览...");// 浏览
		locationPanel.add(locationBox, "Center");
		locationPanel.add(btnSelect, "East");
		
		/* 保存名字框和线程数框*/
		txtFileName = new JTextField();
		threadField = new JTextField();// 放线程数
		
		/*同理，将标签对应的框放到panel中*/
		JPanel center = new JPanel(gridLayout);
		center.add(urlBox);
		center.add(locationPanel);
		center.add(txtFileName);
		center.add(threadField);
		
		/*完成内容模块的组装*/
		panelContent.add(contentWest, "West");
		panelContent.add(center, "Center");
		panelContent.setBorder(BorderFactory.createEmptyBorder(20, 10, 0, 10));// 创建一个占用空间但没有绘制的空边框，指定了顶线、底线、左边框线和右边框线的宽度
		
		/*对话框南边的“确定”和“取消”按钮*/
		JPanel southPanel = new JPanel(new FlowLayout(2));// 流布局,2为右对齐
		btnOk = new JButton("确定");// 确定
	    btnCancel = new JButton("取消");// 取消
		southPanel.add(btnOk);
		southPanel.add(btnCancel);
		southPanel.setBorder(BorderFactory.createEtchedBorder(1));// 创建一个具有“浮雕化”外观效果的边框，将组件的当前背景色用于突出显示和阴影显示
		
		/*组装dialog，内容和按钮模块*/
		dialogPanel.add(panelContent, "Center");
		dialogPanel.add(southPanel, "South");
		
		/*设置对话框的其他属性*/
		this.setSize(500, 220);// 设置大小
		this.setResizable(false); // 不可调整大小
		this.setLocationRelativeTo(this);// 设置此窗口相对于指定组件的位置
		
		/*添加监听*/
		this.addListeners();
		
		/*初始化值并显示*/
		txtFileName.setText("");
		threadField.setText("5");// 默认为线程数
		locationModel.setSelectedItem("");// 两个box默认值都为空
		urlModel.setSelectedItem("");
		this.setVisible(true);
	}
	
	/**
	 * 选择文件保存路径
	 */
	public void selectFolderToStore() {
		if (chooser == null) {
			chooser = new JFileChooser();
			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);// 设置JFileChooser，只选择目录
		}
		int returnVal = chooser.showSaveDialog(this);// 弹出一个 "Save File"
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			String dir = chooser.getSelectedFile().getAbsolutePath();// 设置选中的文件。如果该文件的父目录不是当前目录，则将当前目录更改为该文件的父目录
			locationModel.addElement(dir);// 在模型的末尾添加项
			locationModel.setSelectedItem(dir);// 设置选择项的值
		}
	}
	
	/**
	 * 为按钮添加监听
	 */
	public void addListeners(){
		/*浏览按钮*/
		this.getBtnSelect().addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent arg0) {
						selectFolderToStore();
					}
				}
				
		); 
		
		/*确定*/
		this.getBtnOk().addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent arg0) {
						newTask();	
					}
				}	
		); 
		
		/*取消*/
		this.getBtnCancel().addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent arg0) {
						cancel();
					}
				}
				
		); 
	}
	
	/**
	 * 新建任务
	 */
	public void newTask(){
			
		/*判断线程数是否输入正确*/
		int threadSum;
		try{
		   threadSum = Integer.parseInt(threadField.getText().trim());// 获得线程数
		   if(threadSum > Global.MAX_THREADS){
			   JOptionPane.showMessageDialog(this, "可设置的最大线程数为"+Global.MAX_THREADS+"！", "Error",0);
			return;
		   }
		   if(threadSum < 1){
			   JOptionPane.showMessageDialog(this, "线程数最小为1！", "Error",0);
			   return; 
		   }
		}catch(Exception e){
			JOptionPane.showMessageDialog(this, "请填写正确的线程数！", "Error",0);
			return;
		} 
		/*获取填入输入框的信息*/
		String urlString = (String) urlBox.getSelectedItem();
		String location = (String) locationBox.getSelectedItem();// 获得保存路径
		String fileName = (String) txtFileName.getText();
		
		if("".equals(urlString) || "".equals(location) || "".equals(fileName)){
			JOptionPane.showMessageDialog(this, "请填写完整信息！", "Error",0);
			return;
		}
		
		/*新建任务*/
		Task task = new Task();
		task.setSourceUrl(urlString);
		task.setSavePath(location);
		task.setFileName(fileName);
		task.setThreadAmount(threadSum);
		task.setStatus(Task.STATE_NEW);// 设置任务为新建
		task.setBeginTime(new Date().getTime());//设置当前时间为任务开始时间
		
		/*调用控制器的新建任务方法*/
		this.centralController.newTask(task);
		
		/*对话框消失*/
		this.dialogDispose();
	}

	/**
	 * 取消，对话框消失
	 */
	public void cancel(){
		this.dialogDispose();
	}
	
	 /**
	  * dialog消失
	  */
	public void dialogDispose(){
		/*将主窗口的dialog赋空*/
		this.frame.setDialog(null);
		/*dialog消失*/
		this.dispose();
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
	
	public JButton getBtnSelect() {
		return btnSelect;
	}

	public JButton getBtnOk() {
		return btnOk;
	}

	public JButton getBtnCancel() {
		return btnCancel;
	}
}
