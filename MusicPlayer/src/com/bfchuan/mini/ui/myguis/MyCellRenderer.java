package com.bfchuan.mini.ui.myguis;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;


import com.bfchuan.mini.bo.SongBo;
import com.bfchuan.mini.entity.Song;

/**
 * 列表的CellRenderer
 * 作者:Loenidas
 * 时间:2012-5-10
 * piaobomengxiang@163.com
 * 版本:v1.0
 *
 */
@SuppressWarnings("serial")
public class MyCellRenderer extends JLabel implements ListCellRenderer<Object> {

	private List<Song> songList = SongBo.getInstance().getAllSongList();
	private static Color bgColor = new Color(255, 220, 255);
	private static Color fgColor = new Color(102, 0, 102);

	public MyCellRenderer() {
		setOpaque(true);
	}

	public static void setBackgroundColor(Color bgColor) {
		MyCellRenderer.bgColor = bgColor;
	}

	public static void setForegroundColor(Color fgColor) {
		MyCellRenderer.fgColor = fgColor;
	}

	public Component getListCellRendererComponent(JList<?> list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {
		this.setFont(new Font(this.getFont().getName(), 20, 15));
		setText(" " + songList.get(index).getSongName());
		Color background;
		Color foreground;

		JList.DropLocation dropLocation = list.getDropLocation();
		if (dropLocation != null && !dropLocation.isInsert()
				&& dropLocation.getIndex() == index) {
			background = bgColor.brighter();
			foreground = fgColor;
		} else if (isSelected) {
			background = bgColor;
			foreground = fgColor;
		} else {
			background = bgColor.brighter();
			foreground = fgColor;
		}
		setBackground(background);
		setForeground(foreground);
		return this;
	}

}
