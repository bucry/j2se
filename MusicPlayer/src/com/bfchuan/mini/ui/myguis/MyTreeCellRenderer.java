package com.bfchuan.mini.ui.myguis;

import java.awt.Color;
import java.awt.Component;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.tree.TreeCellRenderer;

import com.bfchuan.mini.util.ImageTool;

/**
 * 作者:Loenidas
 * 时间:2012-5-10
 * piaobomengxiang@163.com
 * 版本:v1.0
 *
 */
@SuppressWarnings("serial")
public class MyTreeCellRenderer extends JLabel implements TreeCellRenderer {

	private ImageIcon openFolderIcon;
	private ImageIcon closeFolderIcon;
	private ImageIcon leafIcon;
	private static Color fgColor = new Color(255, 245, 255);

	public MyTreeCellRenderer() {
		ImageTool imgTool = ImageTool.getInstance();
		openFolderIcon = imgTool.getIcon("images/treefolderopened.png");
		closeFolderIcon = imgTool.getIcon("images/treefolderclosed.png");
		leafIcon = imgTool.getIcon("images/music.png");
		setOpaque(true);
		setSize(140, 20);
	}
	
	public static void setForegroundColor(Color fgColor) {
		MyTreeCellRenderer.fgColor = fgColor;
	}

	public Component getTreeCellRendererComponent(JTree tree, Object value,
			boolean selected, boolean expanded, boolean leaf, int row,
			boolean hasFocus) {
		if (selected == true) {
			setBackground(fgColor);
		} else {
			setBackground(Color.white);
		}
		setText(value.toString());
		if (expanded) {
			setIcon(openFolderIcon);
		} else if (!leaf) {
			setIcon(closeFolderIcon);
		} else {
			setIcon(leafIcon);
		}
		return this;
	}
}
