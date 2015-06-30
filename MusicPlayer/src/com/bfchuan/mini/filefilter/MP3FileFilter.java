package com.bfchuan.mini.filefilter;

import java.io.File;
import javax.swing.filechooser.FileFilter;

/**
 * MP3文件过滤器
 * 作者:Loenidas
 * 时间:2012-5-10
 * piaobomengxiang@163.com
 * 版本:v1.0
 *
 */
public class MP3FileFilter extends FileFilter {

	public String getDescription() {
		return "*.Mp3(音乐文件)";
	}

	public boolean accept(File file) {
		String name = file.getName();
		if (name.toLowerCase().endsWith(".mp3")
				|| !name.toLowerCase().contains(".")) {
			return true;
		} else {
			return false;
		}
	}

}
