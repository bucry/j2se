package com.bfchuan.view;

import java.awt.Color;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JProgressBar;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * 进度条渲染
 * @author Administrator
 *
 */
public class ProgressRenderer extends DefaultTableCellRenderer {


	private static final long serialVersionUID = 1L;

	/**
	 * 构造一个进度条
	 */
	public ProgressRenderer() {
		super();
	}
	
	/**
	 * 覆写父类方法
	 */
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		if(table.getValueAt(row, column) != null){
			JProgressBar progressBar = new JProgressBar();
			progressBar.setMinimum(0);
			progressBar.setMaximum(100);
			progressBar.setValue(0);
			progressBar.setBackground(Color.white);
			progressBar.setBorder(BorderFactory.createEmptyBorder());
			progressBar.setForeground(new Color(110, 92, 255));
			progressBar.setStringPainted(true);
			if (value != null) {
				Integer progressValue = (Integer) value;
				progressBar.setValue(progressValue);
				if (isSelected) {
					progressBar.setBackground(new Color(206, 207, 255));
				} else {
					progressBar.setBackground(Color.white);
				}
			}
			progressBar
					.setToolTipText(String.valueOf(progressBar.getValue()) + "%");
			return progressBar;
		}
	
			return null;
	}
}
