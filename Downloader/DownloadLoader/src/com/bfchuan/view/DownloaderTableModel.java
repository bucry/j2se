package com.bfchuan.view;

import javax.swing.table.AbstractTableModel;

/**
 * table的模型
 * @author Administrator
 *
 */
public class DownloaderTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	final String[] columnNames = {"状态","文件名","大小","进度","速度","开始时间","用时","保存路径","URL","线程数"};
	final Object[][] data = new Object[40][10];
	
	/**
	 * 获得总列数
	 */
	public int getColumnCount() {	
		return columnNames.length;
	}

	/**
	 * 获得列名
	 */
	public String getColumnName(int columnIndex) {	
		return columnNames[columnIndex];
	}

	/**
	 * 获得行数
	 */
	public int getRowCount() {	
		return data.length;
	}

	/**
	 * 取相应单元格的值
	 */
	public Object getValueAt(int rowIndex, int columnIndex) {	
		return data[rowIndex][columnIndex];
	}

	/**
	 * 返回单元格是否可编辑
	 */
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	/**
	 * 为单元格设定值
	 */
	public void setValueAt(Object value, int rowIndex, int columnIndex) {
		//System.out.println("table_setValue");
		data[rowIndex][columnIndex] = value;
		fireTableCellUpdated(rowIndex,columnIndex);
	}
}
