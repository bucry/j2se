package com.bfchuan.mini.ui.guicomps;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JList;
import javax.swing.JOptionPane;

import com.bfchuan.mini.bo.FileBo;
import com.bfchuan.mini.bo.MusicBo;
import com.bfchuan.mini.bo.SongBo;

/**
 * 歌曲列表操作监听
 * 作者:Loenidas
 * 时间:2012-5-10
 * piaobomengxiang@163.com
 * 版本:v1.0
 *
 */
public class SongListListener extends MouseAdapter implements ActionListener {

	private int playIndex = -1;// 选中的下标
	private RightPanel rightPanel;
	private SongListPPMenu popupMenu;// 弹出菜单
	private JList<?> defaultSongList;// 默认歌曲列表

	public SongListListener() {
		rightPanel = RightPanel.getInstance();
		popupMenu = SongListPPMenu.getInstance();
		defaultSongList = rightPanel.getDefaultSongList();

		defaultSongList.addMouseListener(this);
		popupMenu.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        if ("添加本地音乐文件".equals(action)) {
			String msg = FileBo.getInstance().openMusicFile();
			if (!("".equals(msg))) {
				JOptionPane.showMessageDialog(null, msg);
			}
        } else if ("添加本地音乐文件夹".equals(action)) {
			String msg = FileBo.getInstance().openMusicFolder();
			if (!("".equals(msg))) {
				JOptionPane.showMessageDialog(null, msg);
			}
        } else if ("播放".equals(action)) {
			MusicBo musicBo = MusicBo.getInstance();
			musicBo.play(SongBo.getInstance().getSongByIndex(playIndex));
        } else if ("清空播放列表".equals(action)) {
        	SongBo.getInstance().removeAllSong();
        } else if ("删除".equals(action)) {
        	SongBo.getInstance().removeSongByIndex(playIndex);
        }
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getButton() == 3) {
			int thisIndex = defaultSongList.locationToIndex(e.getPoint());
			if (playIndex != -1 && playIndex == thisIndex
					&& defaultSongList.getCellBounds(playIndex, playIndex).contains(e.getPoint())) {
				popupMenu.getMyPopupMenu(true).show(e.getComponent(), e.getX(), e.getY());
			} else {
				defaultSongList.clearSelection();
				popupMenu.getMyPopupMenu(false).show(e.getComponent(), e.getX(), e.getY());
			}
		} else if (e.getClickCount() == 1) {
			playIndex = defaultSongList.locationToIndex(e.getPoint());
		} else if (e.getClickCount() == 2) {
			playIndex = defaultSongList.locationToIndex(e.getPoint());
			if (playIndex != -1) {
				MusicBo musicBo = MusicBo.getInstance();
				musicBo.play(SongBo.getInstance().getSongByIndex(playIndex));
			}
		}
	}

}
