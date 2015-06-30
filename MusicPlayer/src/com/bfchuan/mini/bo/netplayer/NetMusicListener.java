package com.bfchuan.mini.bo.netplayer;


import com.bfchuan.mini.bo.MusicBo;
import com.bfchuan.mini.bo.MyPlayerControl;
import com.bfchuan.mini.enums.PlayerState;
import com.bfchuan.mini.ui.guicomps.BottomPanel;

/**
 * 网络歌曲监听器，当网速不好时，能起到暂停播放继续缓冲的效果
 * 作者:Loenidas
 * 时间:2012-5-10
 * piaobomengxiang@163.com
 * 版本:v1.0
 *
 */
public class NetMusicListener extends Thread {

	private MyPlayerControl control = MyPlayerControl.getInstance();
	private NetMusicBuffer buffer;// 缓冲器
	
	public NetMusicListener(NetMusicBuffer buffer) {
		this.buffer = buffer;
	}
	
	@Override
	public void run() {
		while(true) {
			if (buffer.isFinish()) {
				break;
			}
			if (buffer.isListener()) {
				try {
					Thread.sleep(2000);
				} catch (Exception e) {
				}
				buffer.setListener(false);
			}
			if (MusicBo.getInstance().getPlayerState().equals(PlayerState.PLAY) && 
					!buffer.isBuffer() && control.playedRate() > 0.9) {// 需要自动暂停，继续缓冲音乐
				NetMusicPlayer.getInstance().pause();
				buffer.setBuffer(true);
				System.out.println("网速不给力，自动暂停...");
				BottomPanel.getInstance().getSongNameLabel().setText(
            			"网速不好，自动暂停...");
			}
			try {
				Thread.sleep(500);
			} catch (Exception e) {
			}
		}
	}

}
