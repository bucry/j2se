package com.bfchuan.mini.bo;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import netlrc.search.LRC;
import netlrc.search.SearchLRC;
import netmp3.search.SearchMp3;

import com.bfchuan.mini.dao.DaoFactory;
import com.bfchuan.mini.entity.LrcSentence;
import com.bfchuan.mini.entity.Song;
import com.bfchuan.mini.ui.guicomps.LrcLabel;
import com.bfchuan.mini.ui.guicomps.LrcSearchDialog;
import com.bfchuan.mini.util.FormatUtils;

/**
 * 歌词解析类
 * 作者:Loenidas
 * 时间:2012-5-10
 * piaobomengxiang@163.com
 * 版本:v1.0
 *
 */
public class LrcBo {

    private static LrcBo lrcParse;
    private Set<LrcSentence> lrcStringSet;//用来存放歌词对象
    private String title;//歌曲名称
    private String artist;//歌手名称
    private String album;//专辑
    private String by;//歌词作者
    private int offset;//歌词偏移量

    private LrcBo() {
    	lrcStringSet = new TreeSet<LrcSentence>();
    }

    /**
     * 加载歌词
     */
    public void loadLrc() {
    	Song curSong = MusicBo.getInstance().getCurrentSong();
    	String songPath = curSong.getLocalPath();
    	String lrcPath = songPath.substring(0, songPath.length() - 4) + ".lrc";
    	File lrcFile = new File(lrcPath);
        if (!lrcFile.exists()) {
            lrcStringSet.clear();
            
            // 如果这首歌的URL中有歌词的URL那么直接加载他
            if (curSong.getLrcURL() != null && curSong.getLrcURL().startsWith("http://")) {
            	new LoadNetLrcThread(curSong.getLrcURL(), true).start();
            } else {
            	//如果没有歌词
            	LrcSearchDialog.getInstance().searchByName(curSong.getSongName());
            	//在搜索完歌词之后自动去加载第一个歌词
            	new AutoLoadLrcThread().start();
            }
            return;
        }
        readLrcFromFile(lrcFile);
        LrcLabel.getInstance().refresh(lrcStringSet);
    }
    
    /**
     * 根据lrcString来解析歌词
     * @param lrcString
     */
    public void loadLrc(String lrcString) {
    	
    	//保存这个歌词文件到歌曲同一目录下，方便下次使用
    	DaoFactory.getInstance().getLrcDao().saveLrcToFile(MusicBo.getInstance().getCurrentSong().getLocalPath(), lrcString);
    	
        String lrcArray[] = lrcString.split("(\r\n)|(\r)|(\n)");
        parseLrc(lrcArray);
        LrcLabel.getInstance().refresh(lrcStringSet);
    }
    
    /**
     * 加载网络上的歌词,通过线程加载,这个方法主要写给LrcSearchDialog调用
     * @param url
     */
    public void loadNetLrc(String url) {
    	new LoadNetLrcThread(url, false).start();
    }
    
    /**
     * 从文件中读取歌词
     * @param lrcFile
     */
    public void readLrcFromFile(File lrcFile) {
    	FileInputStream lrcIs = null;
        try {
            lrcIs = new FileInputStream(lrcFile);
            byte lrcByte[] = new byte[lrcIs.available()];
            lrcIs.read(lrcByte);
            
            String lrcString = new String(lrcByte, "GBK");
            String lrcArray[] = lrcString.split("\r\n");
            if (lrcArray.length == 1) {
                lrcArray = lrcArray[0].split("\n");
            }
            parseLrc(lrcArray);
        } catch (Exception e) {
        	//e.printStackTrace();
        } finally {
        	try {
				lrcIs.close();
			} catch (Exception e) {
			}
        }
    }

    /**
     * 解析歌词文件
     * @param lrcArray
     */
    private void parseLrc(String[] lrcArray) {
        lrcStringSet.clear();
        int length = lrcArray.length;
        for (int i = 0; i < length; i++) {
            if (lrcArray[i].length() == 0) {
                continue;
            }
            if (!lrcArray[i].matches("(\\[\\d{2}:\\d{2}(\\.\\d{2}(\\d)?)?\\])+(.)*(\\s)*")) {
                if (lrcArray[i].startsWith("[ti:")) {
                    if (lrcArray[i].length() - 2 > 4) {
                        title = lrcArray[i].substring(4, lrcArray[i].length() - 2);
                    }
                } else if (lrcArray[i].startsWith("[ar:")) {
                    if (lrcArray[i].length() - 2 > 4) {
                        artist = lrcArray[i].substring(4, lrcArray[i].length() - 2);
                    }
                } else if (lrcArray[i].startsWith("[al:")) {
                    if (lrcArray[i].length() - 2 > 4) {
                        album = lrcArray[i].substring(4, lrcArray[i].length() - 2);
                    }
                } else if (lrcArray[i].startsWith("[by:")) {
                    if (lrcArray[i].length() - 2 > 4) {
                        by = lrcArray[i].substring(4, lrcArray[i].length() - 2);
                    }
                } else if (lrcArray[i].startsWith("[offset:")) {
                    if (lrcArray[i].length() - 2 > 8) {
                        offset = FormatUtils.formatStringToInt(lrcArray[i].substring(8, lrcArray[i].length() - 2));
                    }
                }
            } else {
                String tempArray[] = lrcArray[i].trim().split("]");
                int tempLength = tempArray.length;
                if (tempArray[tempLength - 1].matches("(\\[\\d{2}:\\d{2}(\\.\\d{2}(\\d)?)?)")) {
                    for (int j = 0; j < tempLength; j++) {
                        lrcStringSet.add(new LrcSentence(FormatUtils.formatTime(tempArray[j].substring(1), offset), ""));
                    }

                } else {
                    for (int j = 0; j < tempLength - 1; j++) {
                        int tempTime = FormatUtils.formatTime(tempArray[j].substring(1), offset);
                        //考虑到有些时间是一样的，这样就把空格替换掉
                        LrcSentence tempLrc = new LrcSentence(tempTime, "");
                        if (lrcStringSet.contains(tempLrc)) {
                            lrcStringSet.remove(tempLrc);
                        }
                        lrcStringSet.add(new LrcSentence(tempTime, tempArray[tempLength - 1]));
                    }
                }
            }
        }
    }

    public static LrcBo getInstance() {
    	if (lrcParse == null) {
    		lrcParse = new LrcBo();
    	}
        return lrcParse;
    }

    public String getAlbum() {
        return album;
    }

    public String getArtist() {
        return artist;
    }

    public String getBy() {
        return by;
    }

    public Set<LrcSentence> getLrcStringSet() {
        return lrcStringSet;
    }

    public String getTitle() {
        return title;
    }
    
    public int getOffset() {
    	return offset;
    }
    
    /**
     * 内部线程类，用来加载网络歌词
     * @author JYS
     *
     */
	private class LoadNetLrcThread extends Thread {
    	
    	private String url;
    	private boolean isYahu;//是否是雅虎的歌词
    	
    	public LoadNetLrcThread(String url, boolean isYahu) {
    		this.url = url;
    		this.isYahu = isYahu;
    	}

		@Override
		public void run() {
			//网络歌词的url
			if (url != null && url.startsWith("http://")) {
				String lrcString = "";
				if (isYahu) {
					lrcString = SearchMp3.getLrcString(url);
				} else {
					lrcString = SearchLRC.getLrcContent(url);
				}
				
				if (!"".equals(lrcString)) {
					LrcBo.this.loadLrc(lrcString);
				}
			}
		}
    }
	
	/**
	 * 内部线程类，在搜索完网络歌词之后，自动去加载第一个歌词
	 * @author JYS
	 *
	 */
	private class AutoLoadLrcThread extends Thread {
		
		@Override
		public void run() {
			
			boolean isShowDialog = true;
			List<LRC> lrcList;
			int count = 0;
			
			while (count < 5) {// 这个线程只存在5秒
				try {
					Thread.sleep(1000);
					lrcList = DaoFactory.getInstance().getLrcDao().getNetLrcList();
					if (lrcList.size() >= 1) {
						//加载第一个歌词
						LrcBo.getInstance().loadNetLrc(lrcList.get(0).getUrl());
						isShowDialog = false;
						break;
					}
				} catch (Exception e) {
				}
			}
			
			//是否显示搜索歌词的对话框(加载歌词失败才显示)
			if (isShowDialog) {
				LrcSearchDialog.getInstance().setVisible(true);
			}
		}
	}
}
