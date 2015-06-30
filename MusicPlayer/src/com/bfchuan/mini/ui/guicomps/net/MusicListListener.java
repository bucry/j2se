package com.bfchuan.mini.ui.guicomps.net;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JTable;

import netmp3.search.MusicInfo;

import com.bfchuan.mini.bo.DownloadBo;
import com.bfchuan.mini.bo.MusicBo;
import com.bfchuan.mini.bo.SongBo;
import com.bfchuan.mini.entity.Song;
import com.bfchuan.mini.entity.TaskModel;
import com.bfchuan.mini.util.ID3Info;

/**
 * 作者:Loenidas
 * 时间:2012-5-10
 * piaobomengxiang@163.com
 * 版本:v1.0
 *
 */
public class MusicListListener extends MouseAdapter {

    private JTable musicListTable = MusicListTable.getInstance().getTable();
    private ArrayList<MusicInfo> musicList;
	
    public MusicListListener() {
        musicListTable.addMouseListener(this);
    }
    
	private void addSingleNetMusic() {
		musicList = NetMusicPanel.getInstance().getMusicList();
        int row = musicListTable.getSelectedRow();
        int col = musicListTable.getSelectedColumn();
        MusicInfo mi = musicList.get(row);
        if ("试听".equals(musicListTable.getColumnName(col))) {
        	Song song = ID3Info.getInstance().parseSong(mi);
            SongBo.getInstance().addSong(song);
            MusicBo.getInstance().play(song);
        } else if ("添加".equals(musicListTable.getColumnName(col))) {
        	Song song = ID3Info.getInstance().parseSong(mi);
            SongBo.getInstance().addSong(song);	
        } else if ("下载".equals(musicListTable.getColumnName(col))) {
        	TaskModel tm = new TaskModel();
        	tm.setSongName(mi.getSongName());
        	tm.setURL(mi.getSongUrl());
        	tm.setArtist(mi.getSinger());
        	tm.setAlbum(mi.getAlbum());
        	tm.setMszie(mi.getSize());
        	DownloadBo.getInstance().addTask(tm);
        } else {
        }
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		addSingleNetMusic();
	}

}
