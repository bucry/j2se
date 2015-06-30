package com.bfchuan.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Global {
	
	private static Properties properties = new Properties();

	/**
	 * 配置文件的路径(默认为当前目录下的 myDownloader.ini文件)
	 */
	private static String CONFIG_FILE = "myDownloader.ini";
	
	/**
	 * 下载器版本名称
	 */
	public static final String VERSION_NAME;
	
	/**
	 * 下载时，文件分块数<br>
	 * 缺省值为:20
	 */
	public static final int PIECES_SUM;
	
	/**
	 * 可设置的最大线程数<br>
	 * 缺省值为:20
	 */
	public static final int MAX_THREADS;
	
	/**
	 * 默认的下载器文件路径<br>
	 * 缺省为:F:/myDownloader
	 */
	public static final String MYDOWNLOADER_PATH;
	
	/**
	 * 下载中任务对象保存路径
	 * 缺省为:MYDOWNLOADER_PATH + "/running_task";
	 */
	public static final String RUNNING_TASK_PATH;
	
	/**
	 * 已完成任务对象保存路径<br>
	 * 缺省为:MYDOWNLOADER_PATH + "/complete_task"
	 */
	public static final String COMPLETE_TASK_PATH;
	
	/**
	 * 垃圾箱路径<br>
	 * 缺省值为：MYDOWNLOADER_PATH + "/garbage_bin"
	 */
	public static final String GARBAGE_TASK_PATH;
	
	/**
	 * 日志文件的路径<br>
	 * 缺省值：MYDOWNLOADER_PATH + "/log"
	 */
	public static final String LOG_PATH;
	
	/**
	 * 任务对象保存的后缀<br>
	 * 缺省值为：data
	 */
	public static final String TASK_OBJECT_POSTFIX;
	
	/**
	 * 操作系统的文件分隔符<br>
	 * 缺省为：/
	 */
	public static final String FILE_SEPARATOR;
	
	/**
	 * 支持读取配置文件的功能,静态块，加载类时初始化
	 */
	static{
		
		InputStream inputStream = null;
		try {
			inputStream = Global.class.getClassLoader().getResourceAsStream(CONFIG_FILE);
			properties.load(inputStream);
		} catch (FileNotFoundException e) {
			System.out.println("没有配置文件");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (inputStream != null)
					inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		Integer temp = null;
		String tempStr = null;
		/*下载器版本名称*/
		VERSION_NAME = (tempStr = getValue("version_name")) != null ? tempStr : "迅杭";
		/*操作系统文件分隔符*/
		FILE_SEPARATOR = (tempStr = System.getProperty("file.separator")) != null ? tempStr : "/";
		/*分块数*/
		PIECES_SUM = (temp = getIntValue("pieces_sum")) != null && temp <= 100 && temp > 1 ? temp : 20;
		/*最大线程数*/
		MAX_THREADS = (temp = getIntValue("max_threads")) != null && temp <= 100 && temp > 1 ? temp : 20;
		/*myDownloader——Home*/
		MYDOWNLOADER_PATH = (tempStr = getValue("myDownloader_path")) != null ? tempStr : "/opt";
		/*下载中文件夹*/
		String runnning = (tempStr = getValue("running_task_fileName")) != null ? tempStr : "running_task";
		RUNNING_TASK_PATH = MYDOWNLOADER_PATH + Global.FILE_SEPARATOR + runnning;
		/*已完成文件夹*/
		String complete = (tempStr = getValue("complete_task_fileName")) != null ? tempStr : "complete_task";
		COMPLETE_TASK_PATH = MYDOWNLOADER_PATH + Global.FILE_SEPARATOR + complete;
		/*垃圾箱*/
		String garbage = (tempStr = getValue("garbage_task_fileName")) != null ? tempStr : "garbage_bin";
		GARBAGE_TASK_PATH = MYDOWNLOADER_PATH + Global.FILE_SEPARATOR + garbage;
		/*日志文件夹*/
		String log = (tempStr = getValue("log_path")) != null ? tempStr : "log";
		LOG_PATH = MYDOWNLOADER_PATH + Global.FILE_SEPARATOR + log;
		/*临时文件的后缀*/
		TASK_OBJECT_POSTFIX = (tempStr = getValue("task_object_postfix")) != null ? tempStr : "data";
		
	}
	
	/**
	 * 要考虑多种情况<BR>
	 * 1. 没有这个key和value<BR>
	 * 2. 有key 没有 value
	 */
	private static Integer getIntValue(String key){
		if (key == null)
			throw new RuntimeException("key 不能为空");
		try {
			return new Integer(properties.getProperty(key));
		} catch (NumberFormatException e) {
			return null;
		}
	}

	/**
	 * 获取key对应的值，若发生异常则返回null
	 * @param key
	 * @return
	 */
	private static String getValue(String key) {
		try {
			return new String(properties.getProperty(key).getBytes("utf-8"));
		} catch (Exception e) {
			return null;
		}
	}	
	
}
