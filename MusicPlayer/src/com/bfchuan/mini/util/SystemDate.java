package com.bfchuan.mini.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SystemDate {

	public static String getNowTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
		return sdf.format(new Date());
	}
	
	public static String getTime(long time) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
		Date date = new Date(time);
		return sdf.format(date);
	}

}
