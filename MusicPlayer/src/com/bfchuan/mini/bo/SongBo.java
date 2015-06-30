package com.bfchuan.mini.bo;

import java.io.File;
import java.util.Random;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;


import com.bfchuan.mini.dao.DaoFactory;
import com.bfchuan.mini.dao.ISongDao;
import com.bfchuan.mini.entity.Song;
import com.bfchuan.mini.enums.CircleModel;
import com.bfchuan.mini.ui.guicomps.RightPanel;
import com.bfchuan.mini.ui.guicomps.local.LocalTable;
import com.bfchuan.mini.util.FormatUtils;
import com.bfchuan.mini.util.ID3Info;
import com.bfchuan.mini.util.SystemDate;

/**
 * 歌曲处理逻辑类
 * 作者:Loenidas
 * 时间:2012-5-10
 * piaobomengxiang@163.com
 * 版本:v1.0
 *
 */
public class SongBo {

	private static SongBo songBo;
	private ISongDao songDao = DaoFactory.getInstance().getSongDao();
	
	private SongBo() {
	}
	
	public static SongBo getInstance() {
		if (songBo == null) {
			songBo = new SongBo();
		}
		return songBo;
	}
	
	/**
	 * 保存歌曲列表
	 */
	public void saveSongList() {
		songDao.saveSongList();
		songDao.saveLocalMusicList();
	}
	
	public Vector<Song> getAllSongList() {
		return songDao.getAllSongList();
	}
	
	public Vector<String> getLocalMusicFolderList() {
		return songDao.getLocalMusicFolderList();
	}
	
	/**
	 * 得到指定下标的Song
	 * @param index
	 * @return
	 */
	public Song getSongByIndex(int index) {
		if (index >=0 && index < getAllSongList().size()) {
			return getAllSongList().get(index);
		}
		return null;
	}
	
	/**
	 * 删除全部歌曲
	 */
	public void removeAllSong() {
		songDao.removeAllSong();
		RightPanel.getInstance().updateListUI();
	}
	
	/**
	 * 删除指定下标的歌曲
	 * @param index
	 */
	public void removeSongByIndex(int index) {
		if (index < getAllSongList().size() && index >= 0) {
			songDao.removeSongByIndex(index);
			RightPanel.getInstance().updateListUI();
		}
	}
	
	/**
	 * 增加歌曲
	 * @param song
	 */
	public void addSong(Song song) {
		songDao.addSong(song);
		RightPanel.getInstance().updateListUI();
	}
	
	/**
	 * 添加指定数组下表的歌曲
	 * @param index
	 * @param dtm
	 */
	public void addSong(int[] index, DefaultTableModel dtm) {
		int col = dtm.findColumn("本地路径");
		String path;
		Song song;
		for (int i = 0; i < index.length; i++) {
			path = (String)dtm.getValueAt(index[i], col);
			song = ID3Info.getInstance().parseSong(path);
			addSong(song);
		}
	}
	
	/**
	 * 添加文件夹到本地曲库
	 * @param folder
	 */
	public void addFolderToLocalMusic(String folder) {
		// 如果就是下载目录，则无需添加
		if (folder.equals(ConfigBo.getInstance().getNetMusicDownloadFolder())) {
			return;
		}
		// 检查本地曲库列表
		Vector<String> folderList = getLocalMusicFolderList();
		for (int i = 0; i < folderList.size(); i++) {
			if (folder.equals(folderList.get(i))) {
				return;
			}
		}
		songDao.addFolderToLocalMusic(folder);
		// 加载这个文件夹下所有的文件
		loadAllSongAndUpdateTableModel(folder, LocalTable.getInstance().getTableModel(), 2);
	}
	
	/**
	 * 加载歌曲，并更新tableModel
	 * @param sign 1表示已经下载的Table，2表示已经本地曲库的Table
	 */
	public void loadAllSongAndUpdateTableModel(String folder, DefaultTableModel dtm, int sign) {
		File parent = new File(folder);
		if (!parent.exists()) {
			return;
		}
		String[] list = parent.list();
    	if (list == null) {// 如果这个文件夹下没有歌曲
    		if (!parent.toString().equals(ConfigBo.getInstance().getNetMusicDownloadFolder())) {
        		songDao.removeLocalMusicFolder(parent.toString());
        		parent.delete();
    		}
    		return;
    	}
    	Object[] obj;
    	File tempFile;
    	Song tempSong;
    	for (int i = 0; i < list.length; i++) {
    		tempFile = new File(folder + File.separator + list[i]);
    		if (list[i].endsWith(".mp3") && tempFile.isFile()) {
    			tempSong = ID3Info.getInstance().parseSong(tempFile.toString());
    			if (sign == 1) {
	    			obj = new Object[] { "播放", tempSong.getSongName(), tempSong.getArtist(),
	    					tempSong.getAlbum(), ".mp3", FormatUtils.formatLengthToMB(tempSong.getTotalLength()),
	    					SystemDate.getTime(tempFile.lastModified()), tempSong.getLocalPath()};
	    			dtm.addRow(obj);
    			} else if (sign == 2) {
    				obj = new Object[] { "播放", tempSong.getSongName(), tempSong.getArtist(),
    						tempSong.getAlbum(), FormatUtils.formatLengthToMB(tempSong.getTotalLength()),
    						tempSong.getLocalPath()};
    				dtm.addRow(obj);
    			} else {
    			}
    		}
    	}
	}
	
	/**
	 * 得到下一首歌曲
	 * @param song
	 * @return
	 */
	public Song getNextSong(Song song) {
		if (getAllSongList().size() == 0) {// 如果列表被清空
			return null;
		}
		if (PlayModel.getInstance().getCircleModel().equals(CircleModel.单曲循环)) {
			return song;
		} else if (PlayModel.getInstance().getCircleModel().equals(CircleModel.随即播放)) {
			Random rand = new Random();
			int index = rand.nextInt(getAllSongList().size());
			return getAllSongList().get(index);
		} else if (PlayModel.getInstance().getCircleModel().equals(CircleModel.全部循环)) {
			int index = getAllSongList().indexOf(song);
			if (index == getAllSongList().size() - 1) {
				index = 0;
			} else {
				index++;
			}
			return getAllSongList().get(index);
		} else {
			return null;
		}
	}
	
	/**
	 * 得到上一首歌曲
	 * @param song
	 * @return
	 */
	public Song getPriorSong(Song song) {
		if (getAllSongList().size() == 0) {// 如果列表被清空
			return null;
		}
		if (PlayModel.getInstance().getCircleModel().equals(CircleModel.单曲循环)) {
			return song;
		} else if (PlayModel.getInstance().getCircleModel().equals(CircleModel.随即播放)) {
			Random rand = new Random();
			int index = rand.nextInt(getAllSongList().size());
			return getAllSongList().get(index);
		} else if (PlayModel.getInstance().getCircleModel().equals(CircleModel.全部循环)) {
			int index = getAllSongList().indexOf(song);
			if (index == 0) {
				index = getAllSongList().size() - 1;
			} else {
				index--;
			}
			return getAllSongList().get(index);
		} else {
			return null;
		}
	}
	
}
