package com.bfchuan.mini.ui.myguis;

import javax.swing.table.DefaultTableModel;

/**
 * 作者:Loenidas
 * 时间:2012-5-10
 * piaobomengxiang@163.com
 * 版本:v1.0
 *
 */
@SuppressWarnings("serial")
public class MyTableModel extends DefaultTableModel {

	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}
}
