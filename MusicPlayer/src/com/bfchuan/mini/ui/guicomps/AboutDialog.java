package com.bfchuan.mini.ui.guicomps;
import javax.swing.JFrame;
import javax.swing.JLabel;

import com.bfchuan.mini.util.ImageTool;

/**
 * 关于对话框
 * 作者:Loenidas
 * 时间:2012-5-10
 * piaobomengxiang@163.com
 * 版本:v1.0
 *
 */
@SuppressWarnings("serial")
public class AboutDialog extends JFrame {

	private static AboutDialog aboutDialog;
	private JLabel logoLabel;
	
	private AboutDialog() {
		logoLabel = new JLabel(ImageTool.getInstance().getIcon("images/about.jpg"));
		logoLabel.setSize(500, 300);
		
		this.add(logoLabel);
		this.setSize(500, 400);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	public static AboutDialog getInstance() {
		if (aboutDialog == null) {
			aboutDialog = new AboutDialog();
		}
		return aboutDialog;
	}
	
	public static void main(String[] args) {
		AboutDialog.getInstance().setVisible(true);
	}

}
