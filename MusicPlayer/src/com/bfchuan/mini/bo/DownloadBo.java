package com.bfchuan.mini.bo;

import java.io.File;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;


import com.bfchuan.mini.dao.DaoFactory;
import com.bfchuan.mini.dao.IDownloadDao;
import com.bfchuan.mini.entity.Song;
import com.bfchuan.mini.entity.TaskModel;
import com.bfchuan.mini.ui.guicomps.local.DownloadedTable;
import com.bfchuan.mini.ui.guicomps.local.DownloadingTable;
import com.bfchuan.mini.ui.guicomps.local.LocalTable;
import com.bfchuan.mini.util.DownloadThread;
import com.bfchuan.mini.util.ID3Info;
import com.bfchuan.mini.util.SystemDate;

/**
 * 网络歌曲下载的逻辑处理类
 * 作者:Loenidas
 * piaobomengxiang@163.com
 * 时间:2012-5-10
 * 版本:v1.0
 *
 */
public class DownloadBo {

	private static DownloadBo downBo;
	private IDownloadDao downDao = DaoFactory.getInstance().getDownloadDao();

	private DownloadBo() {
	}

	public static DownloadBo getInstance() {
		if (downBo == null) {
			downBo = new DownloadBo();
		}
		return downBo;
	}

	/**
	 * 保存任务列表
	 */
	public void saveTaskList() {
		downDao.saveTaskList();
	}

	public Vector<TaskModel> getAllTaskList() {
		return downDao.getAllTaskList();
	}

	/**
	 * 增加下载任务
	 * @param tm
	 */
	public void addTask(TaskModel tm) {
		if (getAllTaskList().contains(tm)) {
			return;
		}
		downDao.addTask(tm);
		DownloadThread dt = new DownloadThread(tm);
		downDao.addTaskThread(dt);
		dt.start();
		DefaultTableModel dtm = DownloadingTable.getInstance().getTableModel();
		Object[] obj = { "正在下载", tm.getSongName(), tm.getArtist(),
				tm.getAlbum(), ".mp3", tm.getMszie(), "0%" };
		dtm.addRow(obj);
	}

	/**
	 * 删除下载任务
	 * @param tt
	 */
	public void removeTask(DownloadThread tt) {
		if (!getAllTaskList().contains(tt.getTaskModel())) {// 如果这个任务不存在
			return;
		}
		DefaultTableModel dtm = DownloadingTable.getInstance().getTableModel();
		int rows = dtm.getRowCount();
		int songNameRow = dtm.findColumn("歌曲名称");
		int destRow = -1;
		
		for (int i = 0; i < rows; i++) {
			if (tt.getTaskModel().getSongName().equals(dtm.getValueAt(i, songNameRow))) {
				destRow = i;
				break;
			}
		}
		if (destRow != -1) {
			dtm.removeRow(destRow);
		}
		downDao.removeTask(tt.getTaskModel());
		downDao.removeTaskThread(tt);
	}
	
	/**
	 * 删除指定下标数组的所有任务
	 * @param index
	 */
	public void removeTask(int[] index) {
		DefaultTableModel dtm = DownloadingTable.getInstance().getTableModel();
		DownloadThread dt = null;
		int di;// 用来记录找到的线程下标
		for (int i = index.length - 1; i >= 0; i--) {
			dtm.removeRow(index[i]);
			di = findDownloadThread(getAllTaskList().elementAt(index[i]));
			if (di != -1) {
				dt = downDao.getAllTaskThread().get(di);
				downDao.removeTaskThread(dt);
				dt.setRun(false);
			}
		}
	}
	
	public Vector<DownloadThread> getAllTaskThread() {
		return downDao.getAllTaskThread();
	}

	/**
	 * 更新下载任务，主要是更新下载进度
	 * @param tm
	 */
	public void updateTask(TaskModel tm) {
		DefaultTableModel dtm = DownloadingTable.getInstance().getTableModel();
		int progress = (int)((tm.getCurPos() * 100) / tm.getTotalSize());
		int rows = dtm.getRowCount();
		int progressCol = dtm.findColumn("进度");
		int songNameRow = dtm.findColumn("歌曲名称");

		int destRow = -1;
		for (int i = 0; i < rows; i++) {
			if (tm.getSongName().equals(dtm.getValueAt(i, songNameRow))) {
				destRow = i;
				break;
			}
		}
		if (progress == 100) {// 说明下载完成
			// 加入已经下载的Table和本地曲库的Table
			DefaultTableModel ddtm = DownloadedTable.getInstance().getTableModel();
			DefaultTableModel ldtm = LocalTable.getInstance().getTableModel();
			Song song = ID3Info.getInstance().parseSong(
					ConfigBo.getInstance().getNetMusicDownloadFolder()
							+ tm.getSongName() + ".mp3");
			Object[] obj = { "播放", song.getSongName(), tm.getArtist(),
					tm.getAlbum(), ".mp3", tm.getMszie(), SystemDate.getNowTime(), song.getLocalPath()};
			ddtm.addRow(obj);
			obj = new Object[] { "播放", song.getSongName(), tm.getArtist(),
					tm.getAlbum(), tm.getMszie(), song.getLocalPath()};
			ldtm.addRow(obj);
			dtm.removeRow(destRow);
		} else {
			if (destRow != -1 && progressCol != -1) {
				dtm.setValueAt(progress + "%", destRow, progressCol);// 更新进度
			}
		}
	}
	
	/**
	 * 开始下载任务
	 * @param index
	 */
	public void startTask(int[] index) {
		DefaultTableModel ddtm = DownloadingTable.getInstance().getTableModel();
		DownloadThread dt = null;
		for (int i = index.length - 1; i >= 0; i--) {
			ddtm.setValueAt("正在下载", index[i], 0);
			dt = new DownloadThread(getAllTaskList().elementAt(index[i]));
			downDao.addTaskThread(dt);
			dt.start();// 线程开始执行
		}
	}
	
	/**
	 * 停止下载任务
	 * @param index
	 */
	public void stopTask(int[] index) {
		DefaultTableModel ddtm = DownloadingTable.getInstance().getTableModel();
		DownloadThread dt = null;
		int di = -1;
		for (int i = index.length - 1; i >= 0; i--) {
			ddtm.setValueAt("停止下载", index[i], 0);
			di = findDownloadThread(getAllTaskList().elementAt(index[i]));
			if (di != -1) {
				dt = downDao.getAllTaskThread().get(di);
				downDao.removeTaskThread(dt);// 从列表中删除
				dt.setRun(false);// 停止这个下载线程
			}
		}
	}
	
	/**
	 * 根据TaskModel在下载线程列表中查找指定的DownloadThread
	 * @param tm
	 * @return
	 */
	public int findDownloadThread(TaskModel tm) {
		Vector<DownloadThread> taskThreas = getAllTaskThread();
		for (int i = 0; i < taskThreas.size(); i++) {
			if (taskThreas.elementAt(i).getTaskModel().equals(tm)) {
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * 重新下载该任务
	 * @param index
	 */
	public void reDownloadTask(int[] index) {
		DefaultTableModel ddtm = DownloadingTable.getInstance().getTableModel();
		DownloadThread dt = null;
		TaskModel tm;
		int di = -1;
		for (int i = index.length - 1; i >= 0; i--) {
			ddtm.setValueAt("停止下载", index[i], 0);
			tm = getAllTaskList().elementAt(index[i]);
			di = findDownloadThread(tm);
			if (di != -1) {
				dt = downDao.getAllTaskThread().get(di);
				downDao.removeTaskThread(dt);// 从列表中删除
				dt.setRun(false);// 停止这个下载线程
			}
			// 删除已经下载的文件
			File file = new File(ConfigBo.getInstance().getNetMusicDownloadFolder(), 
					tm.getSongName() + ".mp3");
			System.out.println(file.getAbsolutePath());
			while (file.exists()) {// 删除已经下载的文件
				boolean sign = file.delete();
				System.out.println("delete" + sign);
			}
			ddtm.setValueAt("正在下载", index[i], 0);
			dt = new DownloadThread(tm);
			downDao.addTaskThread(dt);
			dt.start();// 开始下载
		}
	}

	/**
	 * 双击事件的处理，如果是正在下载，则转为暂停下载，
	 * 如果是暂停下载，则转为正在下载
	 * @param index
	 */
	public void twoClickCount(int[] index) {// 这里index的长度肯定是1
		TaskModel tm = getAllTaskList().elementAt(index[0]);
		int di = findDownloadThread(tm);
		if (di == -1) {//说明是暂停下载的状态
			startTask(index);
		} else {
			stopTask(index);
		}
	}

}
