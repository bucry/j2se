/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hadeslee.yoyoplayer.util;

import com.hadeslee.yoyoplayer.lyric.*;
import com.hadeslee.yoyoplayer.playlist.PlayListItem;
import javax.swing.JFrame;

/**
 * 所有播放器都应该实现的接口
 * @author hadeslee
 */
public interface Playerable {
    public void setLyric(Lyric ly);
    public void setTime(long time);
    public void setShowLyric(boolean b);
    public JFrame getTopParent();
    public void play();
    public void pause();
    public void stop();
    public void nextSong();
    public void previousSong();
    public PlayListItem getCurrentItem();
    public Loader getLoader();
    public LyricUI getLyricUI();
    public long getTime();
}
