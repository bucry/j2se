package com.bfchuan.mini.ui.guicomps;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;


import com.bfchuan.mini.bo.ConfigBo;
import com.bfchuan.mini.ui.myguis.MySliderUI;
import com.bfchuan.mini.ui.myguis.PlayerButton;
import com.bfchuan.mini.util.Global;
import com.bfchuan.mini.util.ImageTool;

/**
 * 底部容器类
 * 作者:Loenidas
 * 时间:2012-5-10
 * piaobomengxiang@163.com
 * 版本:v1.0
 *
 */
@SuppressWarnings("serial")
public class BottomPanel extends JPanel {

	private PlayerButton priorSongButton;//上一首按钮
	private PlayerButton playSongButton;//播放/暂停按钮
	private PlayerButton nextSongButton;//下一首按钮
	private PlayerButton stopSongButton;//停止按钮
	private PlayerButton noSoundButton;//静音按钮
    private JSlider soundSlider;//声音滑动条
    private JSlider timeSlider;//时间滑动条
    private JLabel songNameLabel;//显示歌名的Label
    private JLabel timeLabel;//显示时间的Label
    private Color bgColor;//背景颜色
    private static BottomPanel bpnl;//单例

    /**
     * 构造函数
     */
    private BottomPanel() {
    	
    	soundSlider = new JSlider(0, Global.MAX_VOICE, ConfigBo.getInstance().getVoiceValue());
    	timeSlider = new JSlider(0, 1000, 0);
    	songNameLabel = new JLabel();
    	timeLabel = new JLabel("00:00|00:00");
    	bgColor = new Color(255, 190, 255);
    	
        setLayout(null);
        initPlayerButtons();
        initOthers();
    }

    /**
     * 返回单例
     * @return
     */
    public static BottomPanel getInstance() {
    	if (bpnl == null) {
    		bpnl = new BottomPanel();
    	}
        return bpnl;
    }

    /**
     * 初始化控制按钮
     */
    public void initPlayerButtons() {
    	ImageTool imgTool = ImageTool.getInstance();
        priorSongButton = new PlayerButton(imgTool.getIcon("images/skipBackward.png"));
        priorSongButton.setIconPath("images/skipBackward.png");
        priorSongButton.setToolTipText("上一首(ALT+LEFT)");
        priorSongButton.setMnemonic(KeyEvent.VK_LEFT);
        add(priorSongButton);

        playSongButton = new PlayerButton(imgTool.getIcon("images/play.png"));
        playSongButton.setIconPath("images/play.png");
        playSongButton.setToolTipText("播放(ALT+UP)");
        playSongButton.setMnemonic(KeyEvent.VK_UP);
        add(playSongButton);

        nextSongButton = new PlayerButton(imgTool.getIcon("images/skipForward.png"));
        nextSongButton.setIconPath("images/skipForward.png");
        nextSongButton.setToolTipText("下一首(ALT+RIGHT)");
        nextSongButton.setMnemonic(KeyEvent.VK_RIGHT);
        add(nextSongButton);

        stopSongButton = new PlayerButton(imgTool.getIcon("images/stop.png"));
        stopSongButton.setIconPath("images/stop.png");
        stopSongButton.setToolTipText("停止(ALT+S)");
        stopSongButton.setMnemonic(KeyEvent.VK_S);
        add(stopSongButton);

        noSoundButton = new PlayerButton(imgTool.getIcon("images/sound.png"));
        noSoundButton.setIconPath("images/sound.png");
        noSoundButton.setToolTipText("静音(ALT+N)");
        noSoundButton.setMnemonic(KeyEvent.VK_N);
        add(noSoundButton);
    }

    /**
     * 初始化其他组件
     */
    public void initOthers() {
        soundSlider.setOpaque(false);
        soundSlider.setUI(new MySliderUI(soundSlider));
        soundSlider.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(soundSlider);

        timeSlider.setOpaque(false);
        timeSlider.setValue(0);
        timeSlider.setUI(new MySliderUI(timeSlider));
        timeSlider.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(timeSlider);

        timeLabel.setFont(new Font("宋体", 20, 15));
        timeLabel.setForeground(new Color(102, 0, 102));
        add(timeLabel);

        songNameLabel.setForeground(new Color(102, 0, 102));
        songNameLabel.setFont(new Font("宋体", 20, 15));
        songNameLabel.setText("欢迎使用瑛乐盒");
        add(songNameLabel);
        
        // 初始化声音
        soundSlider.setValue(ConfigBo.getInstance().getVoiceValue());
        soundSlider.setToolTipText("音量: " + soundSlider.getValue() + "%");
        
        new SoundSliderListener(this);
        new TimeSliderListener(this);
    }

    /**
     * 增加控制按钮监听
     * @param listener
     */
    public void setButtonListener(ActionListener listener) {
        priorSongButton.addActionListener(listener);
        playSongButton.addActionListener(listener);
        nextSongButton.addActionListener(listener);
        stopSongButton.addActionListener(listener);
        noSoundButton.addActionListener(listener);
    }

    /**
     * 设置底部区域背景颜色
     * @param bgColor
     */
    public void setBackgroundColor(Color bgColor) {
        this.bgColor = bgColor;
        repaint();
    }

    /**
     * 得到显示时间标签
     * @return
     */
    public JLabel getTimeLabel() {
        return timeLabel;
    }

    /**
     * 得到显示歌名的标签
     * @return
     */
    public JLabel getSongNameLabel() {
        return songNameLabel;
    }

    /**
     * 得到时间滑动条
     * @return
     */
    public JSlider getTimeSlider() {
        return timeSlider;
    }

    /**
     * 得到声音的滑动条
     * @return
     */
    public JSlider getSoundSlider() {
        return soundSlider;
    }

    /**
     * 上一首按钮
     * @return
     */
    public PlayerButton getPriorSongButton() {
		return priorSongButton;
	}

    /**
     * 播放/暂停按钮
     * @return
     */
	public PlayerButton getPlaySongButton() {
		return playSongButton;
	}

	/**
	 * 下一首按钮
	 * @return
	 */
	public PlayerButton getNextSongButton() {
		return nextSongButton;
	}

	/**
	 * 停止按钮
	 * @return
	 */
	public PlayerButton getStopSongButton() {
		return stopSongButton;
	}

	/**
	 * 静音按钮
	 * @return
	 */
	public PlayerButton getNoSoundButton() {
		return noSoundButton;
	}

	@Override
    public void paintComponent(Graphics g) {
        Rectangle clip = g.getClipBounds();
        Graphics2D g2d = (Graphics2D) g;
        //渐变效果
        GradientPaint background = new GradientPaint(0f, 0f, Color.white, 0f, getHeight(), bgColor);
        g2d.setPaint(background);
        g2d.fillRect(clip.x, clip.y, clip.width, clip.height);
        //按钮位置
        priorSongButton.setBounds(10, getHeight() - Global.BAR_HEIGHT + 15, 30, 30);
        playSongButton.setBounds(47, getHeight() - Global.BAR_HEIGHT + 9, 45, 45);
        nextSongButton.setBounds(99, getHeight() - Global.BAR_HEIGHT + 15, 30, 30);
        stopSongButton.setBounds(136, getHeight() - Global.BAR_HEIGHT + 15, 30, 30);
        noSoundButton.setBounds(175, getHeight() - Global.BAR_HEIGHT + 15, 30, 30);
        soundSlider.setBounds(210, getHeight() - Global.BAR_HEIGHT + 18, 100, 26);
        timeSlider.setBounds(380, getHeight() - Global.BAR_HEIGHT + 15, getWidth() - 390, 20);
        timeLabel.setBounds(getWidth() - 115, getHeight() - Global.BAR_HEIGHT + 35, 100, 20);
        songNameLabel.setBounds(386, getHeight() - Global.BAR_HEIGHT + 35, getWidth() - 500, 20);
    }
}
