package com.bfchuan.mini.bo.netplayer;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javazoom.jl.decoder.Bitstream;
import javazoom.jl.decoder.BitstreamException;
import javazoom.jl.decoder.Header;


import com.bfchuan.mini.bo.MusicBo;
import com.bfchuan.mini.entity.Song;
import com.bfchuan.mini.enums.PlayerState;
import com.bfchuan.mini.ui.guicomps.BottomPanel;

/**
 * 网络歌曲缓冲类
 * 作者:Loenidas
 * 时间:2012-5-10
 * piaobomengxiang@163.com
 * 版本:v1.0
 *
 */
public class NetMusicBuffer extends Thread {

	private Song song;
	private int distance = 200000;// 200000字节
	private boolean buffer = true;// 是否显示缓冲的信息
	private boolean finish;// 是否结束的标志
	private boolean stop = false;// 是否停止这首歌
	private boolean listener = true;// 是否监听音乐
	private int alreadyPlay = 0;// 已经缓冲的字节
	private File tempFile;
	private NetMusicPlayer netPlayer;// 网络歌曲播放器

	public NetMusicBuffer(Song song) {
		this.song = song;
		this.tempFile = new File(song.getLocalPath());
		netPlayer = NetMusicPlayer.getInstance();
		finish = false;
	}

	/**
	 * 根据缓冲的进度，设置播放器状态
	 * @param c
	 */
	public void setPlayState(int c) {
		MusicBo musicBo = MusicBo.getInstance();
		if (c >= alreadyPlay + distance) {
			setListener(true);
			if (PlayerState.UNREALIZED.equals(musicBo.getPlayerState())) {
				netPlayer.play(song);
				// System.out.println("开始播放");
			} else if (buffer) {// 如果正在缓冲
				netPlayer.resume();
				// System.out.println("恢复播放");
			}
			alreadyPlay = alreadyPlay + distance;
			setListener(true);
			buffer = false;
		} else {
			if (buffer || musicBo.getPlayerState() == PlayerState.UNREALIZED) {
				// System.out.println("正在缓冲:" + (c - alreadyPlay) * 100 /
				// distance + "%");
				BottomPanel.getInstance().getSongNameLabel().setText(
						MusicBo.getInstance().getCurrentSong().getSongName() + " -- " + 
						"正在缓冲: " + (c - alreadyPlay) * 100 / distance + "%");
			}
		}
	}

	/**
	 * 向文件中写入网络歌曲的输入流
	 * @param saveToFile
	 * @param ins
	 */
	private void writeFile(File saveToFile, InputStream ins) {
		OutputStream bos = null;
		try {
			bos = new FileOutputStream(saveToFile, false);
			int bytesRead = 0;
			int c = 0;
			byte[] buffer = new byte[1024];
			while ((bytesRead = ins.read(buffer, 0, 1024)) != -1 && !stop) {
				bos.write(buffer, 0, bytesRead);
				c += bytesRead;
				setPlayState(c);
			}
			setFinish(true);// 缓冲结束
		} catch (Exception e) {
		} finally {
			try {
				bos.flush();
				bos.close();
			} catch (Exception e) { 
			} finally {
				try {
					ins.close();
				} catch (Exception e) {
				}
			}
		}
	}

	public boolean isFinish() {
		return finish;
	}

	public void setFinish(boolean finish) {
		this.buffer = false;
		this.finish = finish;
	}

	public boolean isBuffer() {
		return buffer;
	}

	public void setBuffer(boolean buffer) {
		this.buffer = buffer;
	}

	public void stopBuffer(boolean stop) {
		this.stop = stop;
	}

	public boolean isListener() {
		return listener;
	}

	public void setListener(boolean listener) {
		this.listener = listener;
	}

	@Override
	public void run() {
		try {
			// 连接指定的网络资源,获取网络输入，并计算MP3的时长
			BottomPanel.getInstance().getSongNameLabel().setText("正在链接音频...");
			URL urlfile;
			urlfile = new URL(song.getSongURL());
			URLConnection con = urlfile.openConnection();
			int b = con.getContentLength();// 得到音乐文件的总长度
			MusicBo.getInstance().getCurrentSong().setTotalLength(b);
			BufferedInputStream bis = new BufferedInputStream(con.getInputStream());
			Bitstream bt = new Bitstream(bis);
			Header h = bt.readFrame();
			if (h == null) {
				BottomPanel.getInstance().getSongNameLabel().setText("未知错误...");
				MusicBo.getInstance().nextSong();
				return;
			}
			int time = (int) h.total_ms(b);
			MusicBo.getInstance().getCurrentSong().setTotalTime(time / 1000);//设置音乐的时间
			writeFile(tempFile, bis);
		} catch (MalformedURLException e) {
			BottomPanel.getInstance().getSongNameLabel().setText("链接失败...");
			MusicBo.getInstance().nextSong();
		} catch (IOException e) {
			BottomPanel.getInstance().getSongNameLabel().setText("音乐文件无效...");
			MusicBo.getInstance().nextSong();
		} catch (BitstreamException e) {
			BottomPanel.getInstance().getSongNameLabel().setText("音乐文件无效...");
			MusicBo.getInstance().nextSong();
		} catch (Exception e) {
			e.printStackTrace();
			BottomPanel.getInstance().getSongNameLabel().setText("未知错误...");
			MusicBo.getInstance().nextSong();
		}
	}

}
