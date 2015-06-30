package com.bfchuan.mini.bo;

import java.io.File;

import javazoom.jlgui.basicplayer.BasicPlayerEvent;
import mp3x.ctl.PlayerControl;


import com.bfchuan.mini.enums.PlayerState;
import com.bfchuan.mini.ui.guicomps.RightPanel;

/**
 * 作者:Loenidas
 * 时间:2012-5-10
 * piaobomengxiang@163.com
 * 版本:v1.0
 *
 */
public class MyPlayerControl extends PlayerControl {

    private static MyPlayerControl control = null;

    private MyPlayerControl() {
        this.setTVShow(RightPanel.getInstance().getMp3TVShow());//注册TVShow
    }

    /**
     * 播放器状态更新
     */
    @Override
    public void stateUpdated(BasicPlayerEvent arg0) {
        if (arg0.getCode() == BasicPlayerEvent.EOM) {
            System.out.println("finish ++ ");
            MusicBo musicBo = MusicBo.getInstance();
            musicBo.setPlayerState(PlayerState.UNREALIZED);
            if (musicBo.getCurrentSong().isNetMusic()) {
            	new File(musicBo.getCurrentSong().getLocalPath()).delete();
            }
            MusicBo.getInstance().nextSong();
        }
    }

    public static MyPlayerControl getInstance() {
        if (control == null) {
        	control = new MyPlayerControl();
        }
        return control;
    }
}
