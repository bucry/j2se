package com.xomm.comm;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.tree.DefaultMutableTreeNode;

import org.jvnet.substance.skin.SubstanceGreenMagicLookAndFeel;

import com.xcomm.entity.TbUserTable;
import com.xcomm.treeView.BaseTreeUi;
import com.xcomm.treeView.FindCreateTreePen;


/**
 * 聊天主界面
 * 作者：Leonidas 
 * piaobomengxiang@163.com
 * 时间：2013-9-13
 * 版本：1.0
 * 描述：聊天主界面
 */

public class FindChatFrameCont implements ActionListener,MouseListener{

	static {
		JFrame.setDefaultLookAndFeelDecorated(true);
		JDialog.setDefaultLookAndFeelDecorated(true);
		try {
			UIManager.setLookAndFeel(new SubstanceGreenMagicLookAndFeel());
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
	}
	
	
	public static JPopupMenu popupMenu1 = new JPopupMenu(); 
	private JMenuItem menuItem1 = new JMenuItem();
	private JMenuItem menuItem2 = new JMenuItem();
	private JMenuItem menuItem3 = new JMenuItem(); 
	private JMenuItem menuItem4 = new JMenuItem();
	private JMenuItem menuItem5 = new JMenuItem();
	private JMenuItem menuItem6 = new JMenuItem();
	
	
	public static JPopupMenu moveMyFrinds = new JPopupMenu(); 
	private JMenuItem myFrinds = new JMenuItem();
	private JMenuItem commFrinds = new JMenuItem();
	private JMenuItem noTdings = new JMenuItem(); 
	private JMenuItem ress = new JMenuItem();
	
	
	
	
	//定义一个用于格式化日期的格式器
	//private DateFormat formatter = DateFormat.getDateTimeInstance(); 
	public  static  JFrame jframeFriendList;
	private JPanel jpanelFrendList;

	private JButton jbtnOnlineFriendList, jbtnMyGroupChat;

	private JList<Object> jlistOnlineFriendList;
	//private DefaultListModel<Object> defaultListModel;
	private JScrollPane jscrollPaneOnlineFriendList;
	private JScrollPane jscrollPaneOnlineFriendListJTree;

	// 设置树
	private JTree jtree;
	// 设置节点(此节点为跟节点)
	private DefaultMutableTreeNode dmtnRoot = new DefaultMutableTreeNode("中国好友");

	private DefaultMutableTreeNode dmtnOnlineUsersRoot = new DefaultMutableTreeNode(
			"我的好友");

	private DefaultMutableTreeNode dmtnMyGroup = new DefaultMutableTreeNode(
			"我的群");

	private DefaultMutableTreeNode dmtnUnknownUsers = new DefaultMutableTreeNode(
			"陌生人");

	private DefaultMutableTreeNode dmtnBlackName = new DefaultMutableTreeNode(
			"黑名单");

	// 设置节点(此节点为好友列表节点)
	private DefaultMutableTreeNode dmtnLeaf;

	private DefaultMutableTreeNode dmtnGroup = new DefaultMutableTreeNode("我的第一个群");
	private DefaultMutableTreeNode dmtnGroup01 = new DefaultMutableTreeNode("我的第二个群");
	private DefaultMutableTreeNode dmtnGroup02 = new DefaultMutableTreeNode("我的第三个群");

	public FindChatFrameCont() {
		initView();
		acctionListener();
		setOnlineList();
	}
	
	public static void main(String[] args) {
		new FindChatFrameCont();
	}

	// 视图
	@SuppressWarnings("deprecation")
	public void initView() {
		
		jframeFriendList = new JFrame();
		
		ImageIcon icon = new ImageIcon("ico/all.gif");
		menuItem1.setLabel("发送及时信息");
		menuItem1.addActionListener(this); 
		menuItem1.setIcon(icon);
		menuItem2.setLabel("发送电子邮件");
		menuItem2.addActionListener(this); 
		menuItem2.setIcon(icon);
		menuItem3.setLabel("查看资料");
		menuItem3.addActionListener(this); 
		menuItem3.setIcon(icon);
		menuItem4.setLabel("修改备注名称");
		menuItem5.addActionListener(this); 
		menuItem5.addMouseListener(this);
		menuItem4.setIcon(icon);
		menuItem5.setLabel("移动好友至");
		menuItem5.addActionListener(this); 
		menuItem5.setIcon(icon);
		menuItem6.setLabel("进入TT空间");
		menuItem6.addActionListener(this); 
		menuItem6.setIcon(icon);
		popupMenu1.add(menuItem1);
		popupMenu1.add(menuItem2);
		popupMenu1.add(menuItem3);
		popupMenu1.add(menuItem4);
		popupMenu1.add(menuItem5);
		popupMenu1.add(menuItem6);
		//在jframeFriendList中加入popupMenu1
		jframeFriendList.add(popupMenu1); 
		
		
		myFrinds.setLabel("我的好友");
		myFrinds.addActionListener(this); 
		myFrinds.setIcon(icon);
		
		commFrinds.setLabel("企业好友");
		commFrinds.addActionListener(this); 
		commFrinds.setIcon(icon);
		
		noTdings.setLabel("陌生人");
		noTdings.addActionListener(this); 
		noTdings.setIcon(icon);
		
		ress.setLabel("黑名单");
		ress.addActionListener(this); 
		ress.setIcon(icon);
		moveMyFrinds.add(myFrinds);
		moveMyFrinds.add(commFrinds);
		moveMyFrinds.add(noTdings);
		moveMyFrinds.add(ress);
		jframeFriendList.add(moveMyFrinds); 
		
		jpanelFrendList = new JPanel();
		//defaultListModel = new DefaultListModel<Object>();
		// jlistOnlineFriendList = new JList(defaultListModel);
		jlistOnlineFriendList = new JList<Object>();

		jbtnOnlineFriendList = new JButton("在线用户");
		jbtnOnlineFriendList.setEnabled(false);
		jbtnMyGroupChat = new JButton("TT开发设计");
		jbtnMyGroupChat.setEnabled(false);
		jpanelFrendList.setLayout(null);
		// 列表显示
		// dmtnRoot.add(dmtnLeaf);
		jtree = new JTree(dmtnRoot);
		jtree.setUI(new BaseTreeUi());
		// 设置根节点是否显示
		jtree.setRootVisible(true);
		jtree.putClientProperty("JTree.lineStyle", "None");// 清除线
		jtree.setShowsRootHandles(true);// 设置图标
		dmtnRoot.add(dmtnOnlineUsersRoot);
		dmtnRoot.add(dmtnMyGroup);
		dmtnRoot.add(dmtnUnknownUsers);
		dmtnRoot.add(dmtnBlackName);
		dmtnMyGroup.add(dmtnGroup);
		dmtnMyGroup.add(dmtnGroup01);
		dmtnMyGroup.add(dmtnGroup02);
		
		//jscrollPaneOnlineFriendListJTree = new JScrollPane(jtree);
		jscrollPaneOnlineFriendListJTree = new JScrollPane(new FindCreateTreePen().getTree());
		jscrollPaneOnlineFriendList = new JScrollPane(jlistOnlineFriendList);
		// jscrollPaneOnlineFriendList.getViewport()
		// .setView(jlistOnlineFriendList);
		jbtnOnlineFriendList.setBounds(0, 0, 250, 30);
		jscrollPaneOnlineFriendList.setBounds(0, 30, 250, 570);
		jscrollPaneOnlineFriendListJTree.setBounds(0, 30, 250, 570);
		jbtnMyGroupChat.setBounds(0, 595, 250, 30);

		jpanelFrendList.add(jbtnOnlineFriendList);
		//此处显示QQ
		jpanelFrendList.add(jscrollPaneOnlineFriendListJTree);
		// jpanelFrendList.add(jscrollPaneOnlineFriendList);
		
		jpanelFrendList.add(jbtnMyGroupChat);
		jframeFriendList.add(jpanelFrendList);
		jframeFriendList.setTitle("TT2013");
		jframeFriendList.setSize(250, 650);
		jframeFriendList.setResizable(false);
		jframeFriendList.setLocationRelativeTo(null);
		jframeFriendList
				.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframeFriendList.setVisible(true);
		//修改LOGOs
		Toolkit tk=Toolkit.getDefaultToolkit();
		Image image=tk.createImage("/home/java/Work/Communication/icon/icon.gif");
		jframeFriendList.setIconImage(image);
	}

	// 触发事件
	public void acctionListener() {
		// 选择好友聊天
		jtree.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// super.mouseClicked(e);
				int count = jtree.getRowForLocation(e.getX(), e.getY());
				if (e.getClickCount() == 1)
					System.out.println(count);
				if (count != -1) {
					String strFriendUsername = jtree
							.getLastSelectedPathComponent().toString();
					if (e.getClickCount() == 2 && count != 0
							&& !strFriendUsername.equals("陌生人")
							&& !strFriendUsername.equals("黑名单")
							&& !strFriendUsername.equals("我的群")) {
						if (jtree.isRowSelected(jtree.getRowForLocation(
								e.getX(), e.getY()))) {
							TbUserTable user = new TbUserTable();
							user.setChatFrame(new FindChatFrameExchangeDialog(null , user));
							user.getChatFrame().setVisible(true);
							System.out.println("你双击了：" + strFriendUsername);
						}
					}
				}
			}
		});

		// 关闭事件
		jframeFriendList.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				// super.windowClosing(e);
				System.exit(0);
			}
		});
	}

	// 设置在线列表
	public void setOnlineList() {
		for (int i = 0; i < 5; i++) {
			String onlineUsers = "我的第" + i + "位好友";
			dmtnLeaf = new DefaultMutableTreeNode(onlineUsers);
			dmtnOnlineUsersRoot.add(dmtnLeaf);
		}
		jtree.updateUI();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if(obj == menuItem1){
			//菜单1的事件监听
				TbUserTable user = new TbUserTable();
				user.setName("Leonidas");
				user.setNickname("Leonidas");
				user.setChatFrame(new FindChatFrameExchangeDialog(jframeFriendList , user));
				user.getChatFrame().setVisible(true);
		}
		
	}


	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		Object obj = e.getSource();
		if(obj == menuItem5){
			//moveMyFrinds.show(popupMenu1, e.getX(), e.getY());
		}
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}

