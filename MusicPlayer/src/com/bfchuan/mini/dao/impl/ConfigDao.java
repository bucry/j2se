package com.bfchuan.mini.dao.impl;

import java.io.File;
import java.util.Properties;


import com.bfchuan.mini.dao.IConfigDao;
import com.bfchuan.mini.util.OSInfo;

/**
 * 配置文件的dao层实现类
 * 作者:Loenidas
 * 时间:2012-5-10
 * piaobomengxiang@163.com
 * 版本:v1.0
 *
 */
public class ConfigDao extends BaseDao implements IConfigDao {

	private Properties syspp;// 系统属性文件
	private Properties lrcpp;//歌词属性文件
	private Properties themepp;//主题属性文件

	public ConfigDao() {
		syspp = new Properties();
		boolean sign = loadProperties(syspp, "resource" + File.separator
				+ "mini.properties", false);// 加载配置文件
		if (sign == false) {
			syspp.setProperty("lrcbg", "1");
			if (OSInfo.getOSName().toLowerCase().startsWith("windows")) {// windows系统
				syspp.setProperty("musictemp", "C:" + File.separator + "temp"
						+ File.separator);
				syspp.setProperty("download", "C:" + File.separator + "download"
						+ File.separator);
			} else {
				syspp.setProperty("musictemp", "minitemp" + File.separator);
				syspp.setProperty("download", "minidownload" + File.separator);
			}
			syspp.setProperty("voice", "90");
			syspp.setProperty("theme", "6");
			System.out.println("加载配置文件失败...");
		}
		lrcpp = new Properties();
		themepp = new Properties();
		loadProperties(lrcpp, "properties/lrc/lrc" + syspp.getProperty("lrcbg") + ".properties", true);
		loadProperties(themepp, "properties/theme/theme" + syspp.getProperty("theme") + ".properties", true);
	}
	
	/**
	 * 保存配置
	 */
	public boolean saveConfig() {
		return saveProperties(syspp, "resource" + File.separator + "mini.properties", "mini Info");
	}
	
	/**
	 * 得到本系统的属性
	 */
	public Properties getSystemProperties() {
		return syspp;
	}
	
	/**
	 * 得到歌词的属性
	 */
	public Properties getLrcProperties() {
		return lrcpp;
	}
	
	/**
	 * 得到系统主题的属性
	 */
	public Properties getThemeProperties() {
		return themepp;
	}
	
	/**
	 * 得到歌词背景图片路径
	 */
	public String getLrcbg() {
		return syspp.getProperty("lrcbg");
	}
	
	/**
	 * 得到主题的下标
	 */
    public String getTheme() {
    	return syspp.getProperty("theme");
    }

    /**
     * 得到声音的大小
     */
    public String getVoice() {
    	return syspp.getProperty("voice");
    }

    /**
     * 得到网络音乐缓冲的目录
     */
    public String getNetMusicBufferFolder() {
        return syspp.getProperty("musictemp");
    }
    
    /**
     * 得到网络音乐下载的目录
     */
    public String getNetMusicDownloadFolder() {
        return syspp.getProperty("download");
    }
    
    /**
     * 设置歌词背景
     */
	public void setLrcbg(String lrcbg) {
		syspp.setProperty("lrcbg", lrcbg);
		loadProperties(lrcpp, "properties/lrc/lrc" + lrcbg + ".properties", true);
	}
	
	/**
	 * 设置系统主题
	 */
    public void setTheme(String theme) {
    	syspp.setProperty("theme", theme);
		loadProperties(themepp, "properties/theme/theme" + theme + ".properties", true);
    }

    /**
     * 设置声音
     */
    public void setVoice(String voice) {
    	syspp.setProperty("voice", voice);
    }

    /**
     * 设置网络音乐缓存的目录
     */
    public void setNetMusicBufferFolder(String folder) {
        syspp.setProperty("musictemp", folder);
    }
    
    /**
     * 设置网络音乐下载的目录
     */
    public void setNetMusicDownloadFolder(String folder) {
        syspp.setProperty("download", folder);
    }

}
