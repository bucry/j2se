package com.bfchuan.mini.ui.guicomps;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


import com.bfchuan.mini.bo.MusicBo;
import com.bfchuan.mini.enums.PlayerState;

/**
 * 歌词区域的弹出菜单监听
 * 作者:Loenidas
 * 时间:2012-5-10
 * piaobomengxiang@163.com
 * 版本:v1.0
 *
 */
public class LrcLabelPPMenuListener extends MouseAdapter implements ActionListener {

	private LrcLabel lrcLabel = LrcLabel.getInstance();
	private LrcLabelPPMenu popupMenu = LrcLabelPPMenu.getInstance();

	public LrcLabelPPMenuListener() {
		lrcLabel.addMouseListener(this);
		popupMenu.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        if ("手动搜索歌词".equals(action)) {
        	if (MusicBo.getInstance().getPlayerState().equals(PlayerState.UNREALIZED)) {
        		return;
        	}
        	LrcSearchDialog.getInstance().setVisible(true);
        }
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getButton() == 3) {// 如果单击右键
        	if (MusicBo.getInstance().getPlayerState().equals(PlayerState.UNREALIZED)) {
        		return;
        	}
			popupMenu.getPopupMenu().show(e.getComponent(), e.getX(), e.getY());
		}
	}

}
