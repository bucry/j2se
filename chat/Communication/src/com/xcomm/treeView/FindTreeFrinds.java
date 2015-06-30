package com.xcomm.treeView;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreePath;

import org.jvnet.substance.skin.SubstanceGreenMagicLookAndFeel;

public class FindTreeFrinds {
	
	static {
		JFrame.setDefaultLookAndFeelDecorated(true);
		JDialog.setDefaultLookAndFeelDecorated(true);
		try {
			UIManager.setLookAndFeel(new SubstanceGreenMagicLookAndFeel());
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
	}
	JFrame frame;

	public FindTreeFrinds() {
		

		frame = new JFrame("树");

		IconNode root1 = new IconNode(new ImageIcon("images/01.png"), "高中同学");
		IconNode root2 = new IconNode(new ImageIcon("images/02.png"), "初中同学");

		root1.add(new IconNode(new ImageIcon("images/01.png"), "雅君"));
		root1.add(new IconNode(new ImageIcon("01.png"), "伟旭"));
		root1.add(new IconNode(new ImageIcon("2.png"), "宜群"));
		root2.add(new IconNode(new ImageIcon("2.png"), "彬强"));
		root2.add(new IconNode(new ImageIcon("1.png"), "小强"));

		IconNode Root = new IconNode(null, null);// 定义根节点
		Root.add(root1);// 定义二级节点
		Root.add(root2);// 定义二级节点

		final JTree tree = new JTree(Root);// 定义树
		tree.setCellRenderer(new FindIconNodeRenderer()); // 设置单元格描述
		tree.setEditable(false); // 设置树是否可编辑
		tree.setRootVisible(false);// 设置树的根节点是否可视
		tree.setToggleClickCount(1);// 设置单击几次展开数节点

		DefaultTreeCellRenderer cellRenderer = (DefaultTreeCellRenderer) tree
				.getCellRenderer();// 获取该树的Renderer
		cellRenderer.setClosedIcon(new ImageIcon("2.gif"));// 关闭打开图标
		cellRenderer.setOpenIcon(new ImageIcon("2.gif"));// 设置展开图标

		// 测试事件
		tree.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2)// 双击节点
				{
					TreePath path = tree.getSelectionPath();// 获取选中节点路径
					IconNode node = (IconNode) path.getLastPathComponent();// 通过路径将指针指向该节点
					if (node.isLeaf())// 如果该节点是叶子节点
					{
						// DefaultTreeModel
						// model=(DefaultTreeModel)tree.getModel();//获取该树的模型
						// model.removeNodeFromParent(node);//从本树删除该节点
						node.setIcon(new ImageIcon("3.png"));// 修改该节点的图片
						node.setText("双击");// 修改该节点的文本
						tree.repaint();// 重绘更新树
						System.out.println(node.getText());
					} else// 不是叶子节点
					{
					}

				}
			}
		});

		JScrollPane sp = new JScrollPane(tree);
		frame.getContentPane().add(sp, BorderLayout.CENTER);
		frame.setVisible(true);
		frame.setTitle("TT2013");
		frame.setSize(250, 650);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		new FindTreeFrinds();
	}
}
