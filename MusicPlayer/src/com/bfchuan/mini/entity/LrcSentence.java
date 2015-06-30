package com.bfchuan.mini.entity;

/**
 * 歌词的结构体
 * 作者:Loenidas
 * 时间:2012-5-10
 * piaobomengxiang@163.com
 * 版本:v1.0
 *
 */
public class LrcSentence implements Comparable<LrcSentence> {

    private int time = 0;// 时间
    private String lrcString = "";// 歌词内容

    public LrcSentence() {
    }

    public LrcSentence(int time, String lrcString) {
        this.time = time;
        this.lrcString = lrcString;
    }

    public String getLrcString() {
        return lrcString;
    }

    public void setLrcString(String lrcString) {
        this.lrcString = lrcString;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int compareTo(LrcSentence lrcs) {
        if (this.time > lrcs.time) {
            return 1;
        } else if (this.time < lrcs.time) {
            return -1;
        }
        return 0;
    }

    @Override
    public String toString() {
        return "时间：" + this.time + "；歌词：" + this.lrcString + "\n" ;
    }

}
