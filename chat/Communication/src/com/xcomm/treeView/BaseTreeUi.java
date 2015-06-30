package com.xcomm.treeView;

import java.awt.Graphics;

import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicTreeUI;

public class BaseTreeUi extends BasicTreeUI {

	// 实现去除JTree的垂直线和水平线
	@Override
	protected void paintVerticalLine(Graphics g, JComponent c, int x, int top,
			int bottom) {
	}

	@Override
	protected void paintHorizontalLine(Graphics g, JComponent c, int y,
			int left, int right) {
	}

	// 实现父节点与子节点对齐
	@Override
	public void setLeftChildIndent(int newAmount) {

	}

	@Override
	public void setRightChildIndent(int newAmount) {

	}

}
