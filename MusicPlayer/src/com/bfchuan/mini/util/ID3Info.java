package com.bfchuan.mini.util;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;
import java.util.Arrays;

import netmp3.search.MusicInfo;

import com.bfchuan.mini.bo.ConfigBo;
import com.bfchuan.mini.entity.Song;

public class ID3Info {

	private static ID3Info id3;

	private ID3Info() {
	}

	public static ID3Info getInstance() {
		if (id3 == null) {
			id3 = new ID3Info();
		}
		return id3;
	}

	/**
	 * 根据所过的网络歌曲转化为Song对象
	 * 
	 * @param netMusic
	 * @return
	 */
	public Song parseSong(MusicInfo netMusic) {
		Song newSong = new Song();
		newSong.setKey(TimeStamp.getInstance().getSequence());
		newSong.setSongURL(netMusic.getSongUrl());
		newSong.setLrcURL(netMusic.getLrcUrl());
		newSong.setSongName(netMusic.getSongName());
		newSong.setLocalPath(ConfigBo.getInstance().getNetMusicBufferFolder()
				+ netMusic.getSongName()
				+ TimeStamp.getInstance().getSequence() + ".mp3");
		newSong.setArtist(netMusic.getSinger());
		newSong.setAlbum(netMusic.getAlbum());
		newSong.setNetMusic(true);
		return newSong;
	}

	/**
	 * 根据本地路径解析歌曲信息
	 * 
	 * @param songPath
	 * @return
	 */
	public Song parseSong(String songPath) {
		Song newSong = new Song();
		newSong.setLocalPath(songPath);
		newSong.setNetMusic(false);
		newSong.setKey(TimeStamp.getInstance().getSequence());
		RandomAccessFile raf = null;
		try {
			File songFile = new File(songPath);
			newSong.setSongName(songFile.getName().substring(0, songFile.getName().length() - 4));
			raf = new RandomAccessFile(songFile, "r");
			newSong.setTotalLength(raf.length());
			byte[] array = new byte[128];
			raf.seek(raf.length() - 128);
			raf.read(array);
			readSongInfo(array, newSong);
			return newSong;
		} catch (Exception e) {
		} finally {
			try {
				raf.close();
			} catch (Exception e) {
			}// 这句要加上
		}
		return null;
	}

	/**
	 * 读取MP3后面的128个字节内容
	 */
	private void readSongInfo(byte[] tag, Song newSong) {
		if (tag.length != 128 || !isValid(tag)) {
			return;
		} else {
			String songName = getString(tag, 3, 33);
			if (!songName.equals("")) {
				newSong.setSongName(songName);
			}
			newSong.setArtist(getString(tag, 33, 63));
			newSong.setAlbum(getString(tag, 63, 93));
			newSong.setYear(getString(tag, 93, 97));
			newSong.setComment(getString(tag, 97, 125));
		}
	}

	/**
	 * 测试是否是有效的信息
	 * 
	 * @param tag
	 * @return
	 */
	private boolean isValid(byte[] tag) {
		byte[] array = Arrays.copyOfRange(tag, 0, 3);
		String s = new String(array, Charset.forName("GBK"));
		return "TAG".equals(s);
	}

	/**
	 * 读取字节信息，转化为字符串信息
	 * 
	 * @param from
	 * @param to
	 * @return
	 */
	private String getString(byte[] tag, int from, int to) {
		byte[] array = Arrays.copyOfRange(tag, from, to);
		int length = 0;
		for (byte b : array) {
			if (b != 0) {
				length++;
			} else {
				break;
			}
		}
		return new String(array, 0, length, Charset.forName("GBK")).trim();
	}

}
