package com.bfchuan.mini.util;

import java.io.InputStream;

/**
 * 文件流类，用于读取jar包内部的资源文件
 * 作者:Loenidas
 * 时间:2012-5-10
 * piaobomengxiang@163.com
 * 版本:v1.0
 *
 */
public class FileStreamUtils {

	public FileStreamUtils() {
	}

	public static InputStream getInputStream(String path) {
		InputStream is = null;
		try {
			is = FileStreamUtils.class.getClassLoader().getResourceAsStream(
					"org/jys/mini/resource/" + path);
		} catch (Exception g) {
		}
		return is;
	}
}
