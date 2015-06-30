package com.bfchuan.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

import com.bfchuan.entities.FilePiece;

/**
 * 分块显示List的渲染器，根据传来的value状态分别显示不同的颜色块
 * @author Administrator
 *
 */
public class PieceListCellRenderer implements ListCellRenderer{

	/**
	 * 覆写父类方法，设置值
	 */
	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {
		
		JPanel panel = new JPanel();
		Dimension size = new Dimension(20,20);
		
		if (value != null) {
			FilePiece piece = (FilePiece) value;
			if (piece.getStatus() == FilePiece.PIECE_COMPLETE) { //完成
				JPanel panelCompleted = new JPanel();
				panelCompleted.setBackground(Color.blue);
				panelCompleted.setPreferredSize(size);
				panel.add(panelCompleted);
			} else if(piece.getStatus() == FilePiece.PIECE_BUSY){//下载中
				JPanel panelBusy = new JPanel();
				panelBusy.setBackground(Color.green);
				panelBusy.setPreferredSize(size);
				panel.add(panelBusy);
			} else { //其他
				JPanel panelLeisure = new JPanel();
				panelLeisure.setBackground(Color.gray);
				panelLeisure.setPreferredSize(size);
				panel.add(panelLeisure);
			}
		}
		return panel;
	}
	
}
