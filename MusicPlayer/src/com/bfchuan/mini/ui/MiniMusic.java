package com.bfchuan.mini.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import com.bfchuan.mini.bo.ConfigBo;
import com.bfchuan.mini.bo.DownloadBo;
import com.bfchuan.mini.bo.MusicBo;
import com.bfchuan.mini.bo.SongBo;
import com.bfchuan.mini.enums.PageState;
import com.bfchuan.mini.enums.PlayerState;
import com.bfchuan.mini.ui.guicomps.BottomPanel;
import com.bfchuan.mini.ui.guicomps.LrcLabelPPMenuListener;
import com.bfchuan.mini.ui.guicomps.PlayPanel;
import com.bfchuan.mini.ui.guicomps.RightPanel;
import com.bfchuan.mini.ui.guicomps.SkinDialog;
import com.bfchuan.mini.ui.guicomps.SongListListener;
import com.bfchuan.mini.ui.guicomps.TitlePanel;
import com.bfchuan.mini.ui.guicomps.local.LocalMusicPanel;
import com.bfchuan.mini.ui.guicomps.net.NetMusicPanel;
import com.bfchuan.mini.ui.myguis.MyFrame;
import com.bfchuan.mini.util.DesktopInfo;
import com.bfchuan.mini.util.Global;

/**
 * 此类为主函数类
 * 作者:Loenidas
 * 时间:2012-5-10
 * piaobomengxiang@163.com
 * 版本:v1.0
 *
 */
@SuppressWarnings("serial")
public class MiniMusic extends MyFrame implements ActionListener {

	private static MiniMusic mini;//MiniMusic的单例对象
    private TitlePanel titlePanel = TitlePanel.getInstance();//标题栏
    //中间部分
    private PlayPanel playPnl = PlayPanel.getInstance();// 正在播放的Panel
    private PageState lastPageState = null;// 记录当前显示的Tab页
    private NetMusicPanel netPnl = NetMusicPanel.getInstance();// 网络曲库的Panel
    private LocalMusicPanel localPnl = LocalMusicPanel.getInstance();// 本地曲库的Panel
    private RightPanel rightPnl = RightPanel.getInstance();// 右边的Panel
    //底部控制UI
    private BottomPanel bottomPnl = BottomPanel.getInstance();// 底部的控制区Panel
    ///
    private MusicBo musicBo = MusicBo.getInstance();

    private MiniMusic() {
        setSize(DesktopInfo.getDimension());
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle(Global.MINIMUSIC_TITLE);
        setVisible(true);
        //这里初始化顺序不能乱
        setBorderColor(Color.black);
        initTitlePanel();
        pnl.add(rightPnl);
        pnl.add(bottomPnl);
        
        setPage(PageState.PLAYING);
        initListener();//增加监听器
        
        //设置主题
        ConfigBo configBo = ConfigBo.getInstance();
        configBo.setThemeIndex(configBo.getThemeByIndex());
        setBorderColor(ConfigBo.getInstance().getSongListDisplayColor());
    }
    
    public static MiniMusic getInstance() {
    	return mini;
    }
    
    public void initListener() {
    	titlePanel.setButtonListener(this);
    	bottomPnl.setButtonListener(this);
    }

    /**
     * 初始化标题栏
     */
    public void initTitlePanel() {
        titlePanel.setTitle(Global.MINIMUSIC_TITLE, 70, 40);
        pnl.add(titlePanel);
    }

    /**
     * 设置边框颜色
     * @param color
     */
    public void setBorderColor(Color color) {
        pnl.setBorder(BorderFactory.createLineBorder(color, 1));
    }
    
    /**
     * 设置中间页面显示部分
     */
    public void setPage(PageState ps) {
        if (lastPageState != null && lastPageState.equals(ps)) {
            return;
        }
        if (lastPageState != null) {
            pnl.remove(getPanelFromPage(lastPageState));
        }
        pnl.add(getPanelFromPage(ps));
        titlePanel.setPage(ps);
        lastPageState = ps;
        repaint();
    }

    /**
     * 根据不用的页面，获取相应的JPanel
     * @param ps
     * @return
     */
    private JPanel getPanelFromPage(PageState ps) {
        if (ps == PageState.PLAYING) {
        	netPnl.setVisible(false);
        	localPnl.setVisible(false);
        	playPnl.setVisible(true);
            return playPnl;
        } else if (ps == PageState.NET) {
        	playPnl.setVisible(false);
        	localPnl.setVisible(false);
        	netPnl.setVisible(true);
            return netPnl;
        } else if (ps == PageState.LOACL) {
        	playPnl.setVisible(false);
        	netPnl.setVisible(false);
        	localPnl.setVisible(true);
            return localPnl;
        }
        return null;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        titlePanel.setBounds(2, 2, getWidth() - 4, Global.BAR_HEIGHT);
        playPnl.setBounds(2, Global.BAR_HEIGHT + 3, getWidth() - 210, getHeight() - 2 * Global.BAR_HEIGHT);
        netPnl.setBounds(2, Global.BAR_HEIGHT + 3, getWidth() - 210, getHeight() - 2 * Global.BAR_HEIGHT + 1);
        localPnl.setBounds(2, Global.BAR_HEIGHT + 3, getWidth() - 210, getHeight() - 2 * Global.BAR_HEIGHT);
        rightPnl.setBounds(getWidth() - 208, Global.BAR_HEIGHT + 3, 206, getHeight() - 2 * Global.BAR_HEIGHT);
        bottomPnl.setBounds(2, getHeight() - 59, getWidth() - 4, 57);
    }

    public void actionPerformed(ActionEvent e) {
    	Object obj = e.getSource();
    	if (obj == titlePanel.getCloseButton()) {// 关闭系统
    		this.dispose();
    		ConfigBo.getInstance().saveConfig();
    		SongBo.getInstance().saveSongList();
    		DownloadBo.getInstance().saveTaskList();
    		System.exit(0);
    	} else if (obj == titlePanel.getMaxButton() && !getBounds().equals(maxWindowSize)) {// 窗口最大化
            beforeMaxWindowSize = getBounds();
            titlePanel.setWindowToMax();
            setBounds(maxWindowSize);
            repaint();
    	} else if (obj == titlePanel.getMaxButton() && getBounds().equals(maxWindowSize)) {// 恢复窗口
    		titlePanel.recoverWindow();
            setBounds(beforeMaxWindowSize);
            repaint();
        } else if (obj == titlePanel.getMinButton()) {// 最小化
        	SkinDialog.getInstance().setVisible(false);
            setExtendedState(ICONIFIED);
        } else if (obj == titlePanel.getSkinButton()) {// 更换皮肤
            Point point = titlePanel.getSkinButton().getLocationOnScreen();
            point.translate(0, 20);
            SkinDialog.getInstance().setLocation(point);
        	SkinDialog.getInstance().setVisible(true);
        } else if (obj == titlePanel.getPlayingButton()) {// 转为正在播放页面
        	setPage(PageState.PLAYING);
        } else if (obj == titlePanel.getLocalMusicButton()) {// 转到本地曲库页面
        	setPage(PageState.LOACL);
        } else if (obj == titlePanel.getNetMusicButton()) {// 转到网络曲库页面
        	setPage(PageState.NET);
        } else if (obj == bottomPnl.getPlaySongButton() &&
        		musicBo.getPlayerState().equals(PlayerState.PLAY)) {// 播放/暂停音乐
        	musicBo.pause();
        } else if (obj == bottomPnl.getPlaySongButton() &&
        		musicBo.getPlayerState().equals(PlayerState.PAUSE)) {// 播放/暂停音乐
        	musicBo.resume();
        } else if (obj == bottomPnl.getPlaySongButton() &&
        		musicBo.getPlayerState().equals(PlayerState.UNREALIZED)) {// 播放/暂停音乐
        	//.......
        } else if (obj == bottomPnl.getStopSongButton()) {// 停止播放
        	musicBo.stop();
        } else if (obj == bottomPnl.getPriorSongButton()) {// 上一首歌曲
        	musicBo.priorSong();
        } else if (obj == bottomPnl.getNextSongButton()) {// 下一首歌曲
        	musicBo.nextSong();
        } else if (obj == bottomPnl.getNoSoundButton() && musicBo.isNoSound()) {// 取消静音
        	musicBo.setNoSound(false);
        } else if (obj == bottomPnl.getNoSoundButton() && !musicBo.isNoSound()) {// 设置为静音
        	musicBo.setNoSound(true);
        }
    }
    
    public static void main(String args[]) {
        Runnable doCreateAndShowGUI = new Runnable() {

            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel("com.birosoft.liquid.LiquidLookAndFeel");
                    UIManager.put("MenuUI", "javax.swing.plaf.basic.BasicMenuUI");
                    UIManager.put("MenuItemUI", "javax.swing.plaf.basic.BasicMenuItemUI");
                    UIManager.put("ListUI", "javax.swing.plaf.basic.BasicListUI");
                    UIManager.put("MenuItem.arrowIcon", "javax.swing.plaf.metal.MetalIconFactory$MenuItemArrowIcon@7ae3c6");
                    UIManager.put("ButtonUI", "javax.swing.plaf.metal.MetalButtonUI");
                    System.setProperty("java.awt.im.style", "on-the-spot");
                } catch (Exception e) {
                    System.out.println("error" + e.getMessage());
                }
                mini = new MiniMusic();
                new SongListListener();//歌曲列表监听器
                new LrcLabelPPMenuListener();// 右键菜单的监听
            }
        };
        SwingUtilities.invokeLater(doCreateAndShowGUI);
    }
}


