package com.bfchuan.mini.util;

import java.util.Properties;

public class OSInfo {

	private static Properties props = System.getProperties();
	
	private OSInfo() {
	}
	
	public static String getOSName() {
		return props.getProperty("os.name");
	}
	
}
