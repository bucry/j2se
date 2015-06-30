package com.bfchuan.mini.bo.netplayer;


import com.bfchuan.mini.bo.MusicBo;
import com.bfchuan.mini.bo.MyPlayerControl;
import com.bfchuan.mini.entity.Song;
import com.bfchuan.mini.enums.PlayerState;
import com.bfchuan.mini.ui.guicomps.BottomPanel;
import com.bfchuan.mini.ui.guicomps.TimeSliderSington;
import com.bfchuan.mini.util.FormatUtils;
import com.bfchuan.mini.util.ImageTool;
import com.bfchuan.mini.util.NetPlayerTimer;

/**
 * 网络歌曲播放类
 * 作者:Loenidas
 * 时间:2012-5-10
 * piaobomengxiang@163.com
 * 版本:v1.0
 *
 */
public class NetMusicPlayer {

	private static NetMusicPlayer netPlayer;
	private MyPlayerControl control = MyPlayerControl.getInstance();// 播放控制类
	private MusicBo musicBo = MusicBo.getInstance();

	private NetMusicPlayer() {
	}

	public static NetMusicPlayer getInstance() {
		if (netPlayer == null) {
			netPlayer = new NetMusicPlayer();
		}
		return netPlayer;
	}
	
	/**
	 * 播放网络歌曲
	 * @param song
	 */
	public void play(Song song) {
		control.openSong(song.getLocalPath());
		control.play();
		musicBo.initVoiceValue();
		musicBo.setPlayerState(PlayerState.PLAY);
		BottomPanel bmpl = BottomPanel.getInstance();
		bmpl.getSongNameLabel().setText(musicBo.getCurrentSong().getSongName() + " -- 正在播放");
		bmpl.getTimeLabel().setText("00:00|" + 
				FormatUtils.formatTime(musicBo.getCurrentSong().getTotalTime()));
		BottomPanel.getInstance().getPlaySongButton().setIcon(
				ImageTool.getInstance().getIcon("images/pause.png"));
        BottomPanel.getInstance().getPlaySongButton().setIconPath("images/pause.png");
        BottomPanel.getInstance().getPlaySongButton().setToolTipText("播放(ALT+UP)");
        NetPlayerTimer.getInstance().start();// 开始计时
        TimeSliderSington.getInstance().stop();
        TimeSliderSington.getInstance().start();
	}

	/**
	 * 暂停播放
	 */
	public void pause() {
		control.pause();
		MusicBo.getInstance().setPlayerState(PlayerState.PAUSE);
		BottomPanel bmpl = BottomPanel.getInstance();
		bmpl.getPlaySongButton().setIcon(
				ImageTool.getInstance().getIcon("images/play.png"));
		bmpl.getPlaySongButton().setIconPath("images/play.png");
		bmpl.getPlaySongButton().setToolTipText("播放(ALT+UP)");
		bmpl.getSongNameLabel().setText(musicBo.getCurrentSong().getSongName() + " -- 暂停播放");
		NetPlayerTimer.getInstance().stop();
	}

	/**
	 * 恢复播放
	 */
	public void resume() {
		control.resume();
		MusicBo.getInstance().setPlayerState(PlayerState.PLAY);
		BottomPanel bmpl = BottomPanel.getInstance();
		bmpl.getPlaySongButton().setIcon(
				ImageTool.getInstance().getIcon("images/pause.png"));
		bmpl.getPlaySongButton().setIconPath("images/pause.png");
		bmpl.getPlaySongButton().setToolTipText("暂停(ALT+UP)");
		bmpl.getSongNameLabel().setText(musicBo.getCurrentSong().getSongName() + " -- 正在播放");
		NetPlayerTimer.getInstance().resume();
	}

	/**
	 * 停止播放
	 */
	public void stop() {
		control.stop();
		MusicBo.getInstance().setPlayerState(PlayerState.UNREALIZED);
		BottomPanel bmpl = BottomPanel.getInstance();
		bmpl.getPlaySongButton().setIcon(
				ImageTool.getInstance().getIcon("images/play.png"));
		bmpl.getPlaySongButton().setIconPath("images/play.png");
		bmpl.getPlaySongButton().setToolTipText("播放(ALT+UP)");
		bmpl.getSongNameLabel().setText(musicBo.getCurrentSong().getSongName() + " -- 停止播放");
		NetPlayerTimer.getInstance().stop();
	}

}
