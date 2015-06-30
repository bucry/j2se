package com.bfchuan.mini.bo;

import java.awt.Color;
import java.awt.Image;
import com.bfchuan.mini.dao.DaoFactory;
import com.bfchuan.mini.dao.IConfigDao;
import com.bfchuan.mini.ui.guicomps.BottomPanel;
import com.bfchuan.mini.ui.guicomps.LrcLabel;
import com.bfchuan.mini.ui.guicomps.LrcLabelPPMenu;
import com.bfchuan.mini.ui.guicomps.MyMenuBar;
import com.bfchuan.mini.ui.guicomps.RightPanel;
import com.bfchuan.mini.ui.guicomps.SongListPPMenu;
import com.bfchuan.mini.ui.guicomps.TitlePanel;
import com.bfchuan.mini.ui.guicomps.local.DownloadedPPMenu;
import com.bfchuan.mini.ui.guicomps.local.DownloadingPPMenu;
import com.bfchuan.mini.ui.guicomps.local.LocalPPMenu;
import com.bfchuan.mini.ui.myguis.MyCellRenderer;
import com.bfchuan.mini.ui.myguis.MySliderUI;
import com.bfchuan.mini.ui.myguis.MyTabbedPaneUI;
import com.bfchuan.mini.ui.myguis.MyTreeCellRenderer;
import com.bfchuan.mini.util.FormatUtils;
import com.bfchuan.mini.util.ImageTool;

/**
 * 配置文件操作的逻辑处理类
 * 作者:Loenidas
 * 时间:2012-5-10
 * piaobomengxiang@163.com
 * 版本:v1.0
 *
 */
public class ConfigBo {

	private static ConfigBo configBo;
	private IConfigDao configDao = DaoFactory.getInstance().getConfigDao();

	private ConfigBo() {
	}

	public static ConfigBo getInstance() {
		if (configBo == null) {
			configBo = new ConfigBo();
		}
		return configBo;
	}

	/**
	 * 保存配置
	 * @return
	 */
	public boolean saveConfig() {
		return configDao.saveConfig();
	}

	/**
	 * 根据下标得到歌词背景
	 * @return
	 */
	public int getLrcBgIndex() {
		return FormatUtils.formatStringToInt(configDao.getLrcbg());
	}
	
	public void setLrcBgIndex(int index) {
		configDao.setLrcbg(index + "");
		LrcLabel.getInstance().repaint();//重绘
	}

	/**
	 * 跟就下标得到系统的主题
	 * @return
	 */
	public int getThemeByIndex() {
		return FormatUtils.formatStringToInt(configDao.getTheme());
	}
	
	/**
	 * 设置系统的主题，更新部分系统的UI
	 * @param i
	 */
	public void setThemeIndex(int i) {
		configDao.setTheme(i + "");
        TitlePanel.getInstance().setTheme(i);
        MyMenuBar.getInstance().setMenuBackgroundColor(getThemeBgColor());
        SongListPPMenu.getInstance().setBackgroundColor(getThemeBgColor());
        LrcLabelPPMenu.getInstance().setBackgroundColor(getThemeBgColor());
        DownloadingPPMenu.getInstance().setBackgroundColor(getThemeBgColor());
        DownloadedPPMenu.getInstance().setBackgroundColor(getThemeBgColor());
        LocalPPMenu.getInstance().setBackgroundColor(getThemeBgColor());
        BottomPanel bpnl = BottomPanel.getInstance();
        bpnl.setBackgroundColor(getThemeFgColor());
        MySliderUI.setSliderColor(getThemeFgColor());
        bpnl.getTimeSlider().setUI(new MySliderUI(bpnl.getTimeSlider()));
        bpnl.getSoundSlider().setUI(new MySliderUI(bpnl.getSoundSlider()));
        bpnl.getSongNameLabel().setForeground(getSongListDisplayColor());
        bpnl.getTimeLabel().setForeground(getSongListDisplayColor());
        MyTabbedPaneUI.setDefaultColor(getThemeFgColor());
        RightPanel.getInstance().setBgColor(getThemeBgColor());
        RightPanel.getInstance().getListTabPnl().setUI(new MyTabbedPaneUI());
        RightPanel.getInstance().getListTabPnl().setForeground(getSongListDisplayColor());
        MyCellRenderer.setForegroundColor(getSongListDisplayColor());
        MyCellRenderer.setBackgroundColor(getThemeBgColor());
        MyTreeCellRenderer.setForegroundColor(getThemeBgColor());
	}

	/**
	 * 得到声音的值
	 * @return
	 */
	public int getVoiceValue() {
		return FormatUtils.formatStringToInt(configDao.getVoice());
	}
	
	public void setVoiceValue(int value) {
		configDao.setVoice(value + "");
	}

	public String getNetMusicBufferFolder() {
		return configDao.getNetMusicBufferFolder();
	}
	
	public String getNetMusicDownloadFolder() {
		return configDao.getNetMusicDownloadFolder();
	}
	
    public void setNetMusicDownloadFolder(String folder) {
    	configDao.setNetMusicDownloadFolder(folder);
    }

	public Color getLrcFgColor() {
		return Color.decode(configDao.getLrcProperties().getProperty("fgColor"));
	}

	public Color getLrcBgColor() {
		return Color.decode(configDao.getLrcProperties().getProperty("bgColor"));
	}

	public Image getLrcBgImage() {
		return ImageTool.getInstance().getImage(
				configDao.getLrcProperties().getProperty("bgImage"));
	}
	
	public Color getThemeFgColor() {
		return Color.decode(configDao.getThemeProperties().getProperty("fgColor"));
	}
	
	public Color getThemeBgColor() {
		return Color.decode(configDao.getThemeProperties().getProperty("bgColor"));
	}
	
	public Color getSongListDisplayColor() {
		return Color.decode(configDao.getThemeProperties().getProperty("ltColor"));
	}

}
