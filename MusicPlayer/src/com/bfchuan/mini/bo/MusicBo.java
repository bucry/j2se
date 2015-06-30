package com.bfchuan.mini.bo;

import java.io.File;

import javax.swing.JSlider;


import com.bfchuan.mini.bo.netplayer.NetMusicBuffer;
import com.bfchuan.mini.bo.netplayer.NetMusicListener;
import com.bfchuan.mini.bo.netplayer.NetMusicPlayer;
import com.bfchuan.mini.entity.Song;
import com.bfchuan.mini.enums.PlayerState;
import com.bfchuan.mini.ui.guicomps.BottomPanel;
import com.bfchuan.mini.ui.guicomps.RightPanel;
import com.bfchuan.mini.ui.guicomps.TimeSliderSington;
import com.bfchuan.mini.util.FormatUtils;
import com.bfchuan.mini.util.ImageTool;
import com.bfchuan.mini.util.NetPlayerTimer;

/**
 * 音乐处理逻辑类
 * 作者:Loenidas
 * 时间:2012-5-10
 * piaobomengxiang@163.com
 * 版本:v1.0
 *
 */
public class MusicBo {

	private static MusicBo musicBo;
	private MyPlayerControl control = MyPlayerControl.getInstance();
	private Song currentSong;// 当前播放的歌曲
	private PlayerState playState = PlayerState.UNREALIZED;// 播放状态
	private boolean noSound = false;// 是否是静音
	private NetMusicBuffer netBuffer;// 网络音乐缓冲器
	private NetMusicListener netListener;// 网络音乐监听器
	
	private MusicBo() {
	}
	
	public static MusicBo getInstance() {
		if (musicBo == null) {
			musicBo = new MusicBo();
		}
		return musicBo;
	}

	public Song getCurrentSong() {
		return currentSong;
	}
	
	/**
	 * 初始化歌曲
	 * @param song
	 * @return
	 */
	private boolean initPlayer(Song song) {
		if (song == null) {
			return false;
		}
		if (currentSong != null && currentSong.isNetMusic() && 
				netBuffer != null && netBuffer.isAlive()) {
			netBuffer.stopBuffer(true);
		}
		stop();
		
		try {
			File parent = new File(ConfigBo.getInstance().getNetMusicBufferFolder());
			if (!parent.exists()) {
				parent.mkdirs();
			}
			if (song.isNetMusic()) {//如果是网络歌曲，并且缓冲文件存在
				File music = new File(song.getLocalPath());
				if (!music.exists()) {
					music.createNewFile();
				}
			} else if (!song.isNetMusic()) {//如果是本地歌曲，但是歌曲文件不存在
				File music = new File(song.getLocalPath());
				if (!music.exists()) {
					BottomPanel.getInstance().getSongNameLabel().setText("该歌曲不存在...");
					return false;
				} else {
					control.openSong(song.getLocalPath());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		currentSong = song;
		return true;
	}

	public boolean isNoSound() {
		return noSound;
	}

	/**
	 * 设置是否静音
	 * @param noSound
	 */
	public void setNoSound(boolean noSound) {
		BottomPanel bmpl = BottomPanel.getInstance();
		if (noSound == true) {
			control.setVolumnGain(0);
			bmpl.getNoSoundButton().setIcon(ImageTool.getInstance().getIcon("images/nosound.png"));
			bmpl.getNoSoundButton().setIconPath("images/nosound.png");
		} else {
			initVoiceValue();
			bmpl.getNoSoundButton().setIcon(ImageTool.getInstance().getIcon("images/sound.png"));
			bmpl.getNoSoundButton().setIconPath("images/sound.png");
		}
		this.noSound = noSound;
	}
	
	/**
	 * 初始化声音大小
	 */
	public void initVoiceValue() {
		BottomPanel bmpl = BottomPanel.getInstance();
		JSlider slider = bmpl.getSoundSlider();
		double pre = slider.getValue() * 1.0 / slider.getMaximum();
		control.setVolumnGain(pre);
	}

	/**
	 * 播放歌曲
	 * @param song
	 */
	public void play(Song song) {
		if (!initPlayer(song)) {// 如果初始化失败
			return;
		}
		if (currentSong != null) {
			RightPanel rpnl = RightPanel.getInstance();
			if (currentSong.isNetMusic()) {
				if (netBuffer != null && netBuffer.isBuffer()) {// 如果正在缓冲，则等待线程自己播放
					return;
				}
				rpnl.getDefaultSongList().setSelectedValue(this.currentSong, true);
				LrcBo.getInstance().loadLrc();
				netBuffer = new NetMusicBuffer(currentSong);
				netListener = new NetMusicListener(netBuffer);
				netBuffer.start();
				netListener.start();
			} else {
				rpnl.getDefaultSongList().setSelectedValue(this.currentSong, true);
		        LrcBo.getInstance().loadLrc();
				control.play();
				initVoiceValue();
				playState = PlayerState.PLAY;
				BottomPanel bmpl = BottomPanel.getInstance();
				bmpl.getSongNameLabel().setText(this.currentSong.getSongName() + " -- 正在播放");
				bmpl.getTimeLabel().setText("00:00|" + 
						FormatUtils.formatTime(this.getPlayerTotalTime()));
				BottomPanel.getInstance().getPlaySongButton().setIcon(
						ImageTool.getInstance().getIcon("images/pause.png"));
		        BottomPanel.getInstance().getPlaySongButton().setIconPath("images/pause.png");
		        BottomPanel.getInstance().getPlaySongButton().setToolTipText("暂停(ALT+UP)");
		        TimeSliderSington.getInstance().stop();
		        TimeSliderSington.getInstance().start();
			}
		}
	}
	
	/**
	 * 暂停播放
	 */
	public void pause() {
		if (currentSong != null) {
			if (!currentSong.isNetMusic()) {
				control.pause();
				playState = PlayerState.PAUSE;
				BottomPanel bmpl = BottomPanel.getInstance();
				bmpl.getPlaySongButton().setIcon(
						ImageTool.getInstance().getIcon("images/play.png"));
				bmpl.getPlaySongButton().setIconPath("images/play.png");
				bmpl.getPlaySongButton().setToolTipText("播放(ALT+UP)");
		        bmpl.getSongNameLabel().setText(this.currentSong.getSongName() + " -- 暂停播放");	
			} else {
				NetMusicPlayer.getInstance().pause();
			}
			TimeSliderSington.getInstance().pause();
		}
	}
	
	/**
	 * 恢复播放
	 */
	public void resume() {
		if (currentSong != null) {
			if (currentSong.isNetMusic() && netBuffer != null && netBuffer.isBuffer()) {
				return;
			} else if (!currentSong.isNetMusic()) {
				control.resume();
				playState = PlayerState.PLAY;
				BottomPanel bmpl = BottomPanel.getInstance();
				bmpl.getPlaySongButton().setIcon(
						ImageTool.getInstance().getIcon("images/pause.png"));
				bmpl.getPlaySongButton().setIconPath("images/pause.png");
				bmpl.getPlaySongButton().setToolTipText("暂停(ALT+UP)");
				bmpl.getSongNameLabel().setText(this.currentSong.getSongName() + " -- 正在播放");
			} else {
				NetMusicPlayer.getInstance().resume();
			}
			TimeSliderSington.getInstance().resume();
		}
	}
	
	/**
	 * 停止播放
	 */
	public void stop() {
		if (currentSong != null) {
			if (!currentSong.isNetMusic()) {
				control.stop();
				playState = PlayerState.UNREALIZED;
				BottomPanel bmpl = BottomPanel.getInstance();
				bmpl.getPlaySongButton().setIcon(
						ImageTool.getInstance().getIcon("images/play.png"));
				bmpl.getPlaySongButton().setIconPath("images/play.png");
				bmpl.getPlaySongButton().setToolTipText("播放(ALT+UP)");
				bmpl.getSongNameLabel().setText(this.currentSong.getSongName() + " -- 停止播放");
			} else {
				netBuffer.setFinish(true);//停止缓冲
				NetMusicPlayer.getInstance().stop();// 停止播放
			}
			TimeSliderSington.getInstance().stop();
		}
	}
	
	/**
	 * 下一首
	 */
	public void nextSong() {
		Song song = SongBo.getInstance().getNextSong(this.getCurrentSong());
		this.play(song);
	}
	
	/**
	 * 上一首
	 */
	public void priorSong() {
		Song song = SongBo.getInstance().getPriorSong(this.getCurrentSong());
		this.play(song);
	}
	
	public PlayerState getPlayerState() {
		return playState;
	}
	
	public void setPlayerState(PlayerState playState) {
		this.playState = playState;
	}
	
	/**
	 * 得到当前播放的时长
	 * @return
	 */
	public int getPlayerCurTime() {
		int time = 0;
		int offset = FormatUtils.roundDouble(LrcBo.getInstance().getOffset() * 1.0 / 1000);
		if (!this.currentSong.isNetMusic()) {
			time = FormatUtils.roundDouble(control.getTotalTimeSecond() * control.playedRate()) + offset;
		} else { // 网络音乐计时这里用计时器实现
			return NetPlayerTimer.getInstance().getTime() + offset;
		}
		return time;
	}
	
	/**
	 * 得到播放的总时间
	 * @return
	 */
	public int getPlayerTotalTime() {
		if (!this.currentSong.isNetMusic()) {
			return (int)(control.getTotalTimeSecond());
		} else {
			return this.currentSong.getTotalTime();
		}
	}
	
}
