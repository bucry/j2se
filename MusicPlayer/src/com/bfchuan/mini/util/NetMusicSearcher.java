package com.bfchuan.mini.util;

import java.util.ArrayList;

import javax.swing.JRadioButton;

import netmp3.search.MusicInfo;
import netmp3.search.SearchMp3;

import com.bfchuan.mini.ui.guicomps.net.MusicListTable;
import com.bfchuan.mini.ui.guicomps.net.NetMusicPanel;

public class NetMusicSearcher {

	private static NetMusicSearcher snm;
	private NetMusicPanel nmpl = NetMusicPanel.getInstance();
	private SearchMp3 sm;
	private ArrayList<MusicInfo> musicList = nmpl.getMusicList();

	private NetMusicSearcher() {
	}
	
	public static NetMusicSearcher getInstance() {
		if (snm == null) {
			snm = new NetMusicSearcher();
		}
		return snm;
	}

	public void stopSearch() {
		sm.setSearching(false);
	}

	public void search() {
		sm = new SearchMp3();
		sm.setTableModel(MusicListTable.getInstance().getTableModel());
		sm.setArrayList(musicList);
		sm.setSongName(nmpl.getSearchField().getText());
		sm.setSearchBtn(nmpl.getSearchBtn());
		sm.setResultLabel(nmpl.getResultLabel());
		sm.setCancelBtn(nmpl.getCancelBtn());
		JRadioButton[] select = nmpl.getSearchConditions();
		for (int i = select.length - 1; i >= 0; i--) {
			if (select[i].isSelected()) {
				sm.setSearchWay((select[i].getText()));
				break;
			}
		}
		sm.search();
	}

}
