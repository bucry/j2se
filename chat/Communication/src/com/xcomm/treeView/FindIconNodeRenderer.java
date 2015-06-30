package com.xcomm.treeView;

import java.awt.Component;
import javax.swing.Icon;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

public class FindIconNodeRenderer extends DefaultTreeCellRenderer// 继承该类
{
	
	private static final long serialVersionUID = 1L;

	// 重写该方法
	public Component getTreeCellRendererComponent(JTree tree, Object value,
			boolean sel, boolean expanded, boolean leaf, int row,
			boolean hasFocus) {
		super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf,
				row, hasFocus); // 调用父类的该方法
		Icon icon = ((IconNode) value).getIcon();// 从节点读取图片
		String txt = ((IconNode) value).getText(); // 从节点读取文本
		setIcon(icon);// 设置图片
		setText(txt);// 设置文本
		return this;
	}
}

// 定义节点类
class IconNode extends DefaultMutableTreeNode {
	
	private static final long serialVersionUID = 6382520196804581222L;
	
	protected Icon icon;
	protected String txt;

	// 只包含文本的节点构造
	public IconNode(String txt) {
		super();
		this.txt = txt;
	}

	// 包含文本和图片的节点构造
	public IconNode(Icon icon, String txt) {
		super();
		this.icon = icon;
		this.txt = txt;
	}

	public void setIcon(Icon icon) {
		this.icon = icon;
	}

	public Icon getIcon() {
		return icon;
	}

	public void setText(String txt) {
		this.txt = txt;
	}

	public String getText() {
		return txt;
	}
}
