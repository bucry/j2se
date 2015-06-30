package com.xcomm.treeView;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreePath;

import com.xcomm.entity.TbUserTable;
import com.xomm.comm.FindChatFrameCont;
import com.xomm.comm.FindChatFrameExchangeDialog;

public class FindCreateTreePen {
	public  JTree tree;
	public static JScrollPane sp;
	
	private List<String> frindGroup = new ArrayList<String>();
	
	public FindCreateTreePen(){
		
		for(int i = 0;i<frindGroup.size();i++){
			//IconNode root1 = new IconNode(new ImageIcon("ico/root.gif"), "我的好友");
		}
		
		
		IconNode root1 = new IconNode(new ImageIcon("ico/root.gif"), "我的好友");
		IconNode root2 = new IconNode(new ImageIcon("ico/root.gif"), "企业好友");
		IconNode root5 = new IconNode(new ImageIcon("ico/root.gif"), "陌生人");
		IconNode root6 = new IconNode(new ImageIcon("ico/root.gif"), "黑名单");

		
		root1.add(new IconNode(new ImageIcon("ico/5.GIF"), "Leonidas"));
		root1.add(new IconNode(new ImageIcon("ico/6.GIF"), "Leonidas"));
		root1.add(new IconNode(new ImageIcon("ico/7.GIF"), "Leonidas"));
		root1.add(new IconNode(new ImageIcon("ico/8.GIF"), "Leonidas"));
		root1.add(new IconNode(new ImageIcon("ico/9.GIF"), "Leonidas"));
		root1.add(new IconNode(new ImageIcon("ico/10.gif"), "Leonidas"));
		
		root2.add(new IconNode(new ImageIcon("ico/1.gif"), "企业好友-企业好友"));
		root2.add(new IconNode(new ImageIcon("ico/2.gif"), "企业好友-企业好友"));
		root2.add(new IconNode(new ImageIcon("ico/3.gif"), "企业好友-企业好友"));
		root2.add(new IconNode(new ImageIcon("ico/4.gif"), "企业好友-企业好友"));
		root2.add(new IconNode(new ImageIcon("images/011.png"), "企业好友-企业好友"));
		root2.add(new IconNode(new ImageIcon("images/02title41*41.png"), "企业好友-企业好友"));
		root2.add(new IconNode(new ImageIcon("images/01title41*41.png"), "企业好友-企业好友"));
		root2.add(new IconNode(new ImageIcon("images/012.png"), "企业好友-企业好友"));
		
		root5.add(new IconNode(new ImageIcon("ico/5.GIF"), "Leonidas"));
		root5.add(new IconNode(new ImageIcon("ico/6.GIF"), "Leonidas"));
		root5.add(new IconNode(new ImageIcon("ico/7.GIF"), "Leonidas"));
		root5.add(new IconNode(new ImageIcon("ico/8.GIF"), "Leonidas"));
		root5.add(new IconNode(new ImageIcon("ico/9.GIF"), "Leonidas"));
		root5.add(new IconNode(new ImageIcon("ico/10.gif"), "Leonidas"));
		
		
		
		root6.add(new IconNode(new ImageIcon("images/011small.PNG"), "Leonidas"));
		root6.add(new IconNode(new ImageIcon("images/012small.PNG"), "Leonidas"));
		root6.add(new IconNode(new ImageIcon("images/small.PNG"), "Leonidas"));
		root6.add(new IconNode(new ImageIcon("images/01titsmall.PNG"), "Leonidas"));
		
		IconNode Root = new IconNode(null, null);// 定义根节点
		Root.add(root1);// 定义二级节点
		Root.add(root2);// 定义二级节点
		Root.add(root5);// 定义二级节点
		Root.add(root6);// 定义二级节点

		tree = new JTree(Root);// 定义树
		tree.setCellRenderer(new FindIconNodeRenderer()); // 设置单元格描述
		tree.setEditable(false); // 设置树是否可编辑
		tree.setRootVisible(true);// 设置树的根节点是否可视
		tree.setToggleClickCount(1);// 设置单击几次展开数节点

		DefaultTreeCellRenderer cellRenderer = (DefaultTreeCellRenderer) tree
				.getCellRenderer();// 获取该树的Renderer
		cellRenderer.setClosedIcon(new ImageIcon("ico/close.gif"));// 关闭打开图标
		cellRenderer.setOpenIcon(new ImageIcon("ico/open.gif"));// 设置展开图标

		
		
		// 测试事件
		tree.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				
				int mods=e.getModifiers();
				//右键
				if(mods == 4){
					FindChatFrameCont.popupMenu1.show(FindChatFrameCont.jframeFriendList, e.getX(), e.getY());
					/*TreePath path = tree.getSelectionPath();// 获取选中节点路径
					IconNode node = (IconNode) path.getLastPathComponent();// 通过路径将指针指向该节点
					if (node.isLeaf())// 如果该节点是叶子节点
					{
						FindChatFrameCont.popupMenu1.show(FindChatFrameCont.jframeFriendList, e.getX(), e.getY());
					}*/
				}
				if (e.getClickCount() == 2)// 双击节点
				{
					TreePath path = tree.getSelectionPath();// 获取选中节点路径
					IconNode node = (IconNode) path.getLastPathComponent();// 通过路径将指针指向该节点
					if (node.isLeaf())// 如果该节点是叶子节点
					{
						TbUserTable user = new TbUserTable();
						user.setName("Leonidas");
						user.setNickname("Leonidas");
						user.setChatFrame(new FindChatFrameExchangeDialog(null , user));
						user.getChatFrame().setVisible(true);
						tree.repaint();// 重绘更新树
						//System.out.println(node.getText());
					} else// 不是叶子节点
					{
					}

				}
			}
		});
		sp = new JScrollPane(tree);
	}

	public JTree getTree() {
		return tree;
	}

	public static JScrollPane getSp() {
		return sp;
	}

}
