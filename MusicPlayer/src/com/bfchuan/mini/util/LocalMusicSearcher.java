package com.bfchuan.mini.util;

import java.io.File;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableModel;

import com.bfchuan.mini.entity.Song;

/**
 * 作者:Loenidas
 * 时间:2012-5-10
 * piaobomengxiang@163.com
 * 版本:v1.0
 *
 */
public class LocalMusicSearcher extends Thread {

    private File[] distFile;
    private String keyword;
    private String[] format = {".wav", ".au", ".mp3"};
    private DefaultTableModel dtm;
    private JButton cancelBtn;
    private JLabel appearInfoLabel;
    private boolean stop;

    public LocalMusicSearcher(File[] distFile, String keyword, DefaultTableModel dtm, 
    		JButton cancelBtn, JLabel appearInfoLabel) {
        this.distFile = distFile;
        this.keyword = keyword;
        this.dtm = dtm;
        this.cancelBtn = cancelBtn;
        this.appearInfoLabel = appearInfoLabel;
    }
    
    public void setStop(boolean stop) {
    	this.stop = stop;
    }

    public void searchFile(File[] distFile) {
        for (int i = 0; i < distFile.length; i++) {
        	if (stop == true) {
        		return;
        	}
            if (distFile[i].isDirectory()) {
                File[] fileList = distFile[i].listFiles();
                if (fileList != null && fileList.length != 0) {
                    searchFile(fileList);
                }
            } else {
            	String filename = distFile[i].getName();
            	appearInfoLabel.setText(distFile[i].toString());
                if (filename.contains(keyword)) {// 包含指定的关键字
                	// 检测是不是指定的format
                	boolean sign = false;
                	for (int j = 0; j < format.length; j++) {
                		if (filename.endsWith(format[j])) {
                			sign = true;
                			break;
                		}
                	}
                	if (sign) {
                		Song song = ID3Info.getInstance().parseSong(distFile[i].toString());
                		if (song != null) {
	                        Object[] obj = {"播放", song.getSongName(), song.getArtist(), song.getAlbum(),
	                            FormatUtils.formatLengthToMB(song.getTotalLength()), song.getLocalPath()};
	                        dtm.addRow(obj);
                		}
                	}
                }
            }
        }
    }

    @Override
    public void run() {
    	// 删除DefaultTableModel中原有的歌曲
    	int length = dtm.getRowCount();
    	if (length != 0) {
    		for (int i = length - 1; i >= 0; i--) {
    			dtm.removeRow(i);
    		}
    	}
    	cancelBtn.setVisible(true);
    	searchFile(distFile);
    	cancelBtn.setVisible(false);
    	appearInfoLabel.setText("找到: " + dtm.getRowCount() + " 首 关于\"" + keyword + "\"的歌曲...");
    }
}
