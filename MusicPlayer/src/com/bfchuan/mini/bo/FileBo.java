package com.bfchuan.mini.bo;

import java.awt.Desktop;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.table.DefaultTableModel;


import com.bfchuan.mini.entity.Song;
import com.bfchuan.mini.filefilter.MP3FileFilter;
import com.bfchuan.mini.filefilter.WAVFileFilter;
import com.bfchuan.mini.ui.MiniMusic;
import com.bfchuan.mini.util.ID3Info;

/**
 * 文件处理类
 * 作者:Loenidas
 * 时间:2012-5-10
 * piaobomengxiang@163.com
 * 版本:v1.0
 *
 */
public class FileBo {

	private static FileBo fileBo;
	
	private FileBo() {
	}
	
	public static FileBo getInstance() {
		if (fileBo == null) {
			fileBo = new FileBo();
		}
		return fileBo;
	}
	
	/**
	 * 选择音乐文件
	 * @return
	 */
    public String openMusicFile() {
        int selectState = 0;
        File chooserFile = null;
        JFileChooser chooser = new JFileChooser();
        MP3FileFilter mp3FileFilter = new MP3FileFilter();
        WAVFileFilter wavFileFilter = new WAVFileFilter();
        chooser.setCurrentDirectory(new File("."));
        chooser.setDialogTitle("选择音乐文件");
        chooser.setApproveButtonText("确定");
        chooser.setApproveButtonToolTipText("选择音乐文件");
        chooser.addChoosableFileFilter(wavFileFilter);
        chooser.addChoosableFileFilter(mp3FileFilter);
        chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        selectState = chooser.showOpenDialog(MiniMusic.getInstance());
        if (selectState == JFileChooser.APPROVE_OPTION) {
            chooserFile = chooser.getSelectedFile();
            if (chooserFile.toString().toLowerCase().endsWith(".mp3")
                    || chooserFile.toString().toLowerCase().endsWith(".wav")) {
            	Song newSong = ID3Info.getInstance().parseSong(chooserFile.toString());
            	if (newSong == null) {
            		return "文件错误：\n" + chooserFile.toString();
            	}
            	SongBo.getInstance().addSong(newSong);
            }
        }
        return "";
    }
    
    public String openMusicFolder() {
        int selectState = 0;
        StringBuilder sb = new StringBuilder();
        
        File chooserFolder = null;
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File("."));
        chooser.setDialogTitle("选择音乐文件夹");
        chooser.setApproveButtonText("确定");
        chooser.setApproveButtonToolTipText("选择音乐文件夹");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        selectState = chooser.showOpenDialog(MiniMusic.getInstance());
        if (selectState == JFileChooser.APPROVE_OPTION) {
            chooserFolder = chooser.getSelectedFile();
            String songPath[] = chooserFolder.list();
            int length = songPath.length;
            for (int i = 0; i < length; i++) {
                if (songPath[i].toLowerCase().endsWith(".mp3")
                        || songPath[i].toLowerCase().endsWith(".wav")) {
                	Song newSong = ID3Info.getInstance().parseSong(chooserFolder + File.separator + songPath[i]);
                	if (newSong == null) {
                		sb.append(songPath[i]).append("\n");
                	} else {
                		SongBo.getInstance().addSong(newSong);
                	}
                }
            }
        }
        if (sb.length() != 0) {
        	sb.insert(0, "下列音乐文件错误:\n");
        }
        return sb.toString();
    }
    
    /**
     * 选择音乐文件夹
     */
    public void addMusicFolderToLocalMusic() {
        int selectState = 0;
        File chooserFolder = null;
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File("."));
        chooser.setDialogTitle("添加音乐文件夹到本地曲库");
        chooser.setApproveButtonText("确定");
        chooser.setApproveButtonToolTipText("选择音乐文件夹");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        selectState = chooser.showOpenDialog(MiniMusic.getInstance());
        if (selectState == JFileChooser.APPROVE_OPTION) {
            chooserFolder = chooser.getSelectedFile();
            SongBo.getInstance().addFolderToLocalMusic(chooserFolder.toString());
        }
    }
    
    /**
     * 打开指定的目录，JKD1.6后支持
     * @param folder
     */
    public void openFolder(String folder) {
    	try {
			Desktop.getDesktop().open(new File(folder));
		} catch (Exception e) {
		}
    }

    /**
     * 删除指定下标的音乐文件
     * @param index
     * @param dtm
     */
	public void deleteMusicFileAndUpdateTableModel(int[] index, DefaultTableModel dtm) {
		int col = dtm.findColumn("本地路径");
		String path;
		File file;
		for (int i = index.length - 1; i >= 0; i--) {
			path = (String)dtm.getValueAt(index[i], col);
			dtm.removeRow(index[i]);
			file = new File(path);
			file.delete();
		}
	}
    
}
