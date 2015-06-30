package com.bfchuan.mini.ui.guicomps;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionListener;

import javax.swing.JPanel;


import com.bfchuan.mini.enums.ButtonType;
import com.bfchuan.mini.enums.PageState;
import com.bfchuan.mini.ui.myguis.ImageLabel;
import com.bfchuan.mini.ui.myguis.WindowsButton;
import com.bfchuan.mini.util.FormatUtils;
import com.bfchuan.mini.util.ImageTool;

/**
 * 此类为标题栏
 * 作者:Loenidas
 * 时间:2012-5-10
 * piaobomengxiang@163.com
 * 版本:v1.0
 *
 */
@SuppressWarnings("serial")
public class TitlePanel extends JPanel {

	private Image left_Image;// 左边的Image
	private Image mid_Image;// 中间的Image
	private Image right_Image;// 右边的Image
	private ImageLabel title_Label;// 系统的图标
	private Color skinColor;// 标题栏的颜色
	private String title = "Leonidas";// 标题
	private int titleX = 70;// 标题的位置x坐标
	private int titleY = 40;// 标题的位置y坐标
	private ImageTool imgTool = ImageTool.getInstance();// 图像处理工具
	private WindowsButton closeButton;// 关闭按钮
	private WindowsButton maxButton;// 最大化按钮
	private WindowsButton minButton;// 最小化按钮
	private WindowsButton skinButton;// 皮肤按钮
	private WindowsButton playingButton;// 正在播放选项卡
	private WindowsButton netMusicButton;// 网络歌曲选项卡
	private WindowsButton localMusicButton;// 本地歌曲选项卡
	private PageState lastPageState;// 保存当前选项卡状态
	private static TitlePanel tpnl;

	private TitlePanel() {
		initButtons();

		setLayout(null);
		iniTitleImageLabel();
		setPage(PageState.PLAYING);
	}

	public static TitlePanel getInstance() {
		if (tpnl == null) {
			tpnl = new TitlePanel();
		}
		return tpnl;
	}

	public void initButtons() {
		closeButton = new WindowsButton(imgTool
				.getIcon("images/closeButton.png"), ButtonType.WINDOWS_BUTTON);
		maxButton = new WindowsButton(imgTool.getIcon("images/MaxButton.png"),
				ButtonType.WINDOWS_BUTTON);
		minButton = new WindowsButton(imgTool.getIcon("images/MinButton.png"),
				ButtonType.WINDOWS_BUTTON);
		skinButton = new WindowsButton(
				imgTool.getIcon("images/skinButton.png"),
				ButtonType.WINDOWS_BUTTON);
		playingButton = new WindowsButton(imgTool
				.getIcon("images/NowPlayNormal.png"), ButtonType.PAGE_BUTTON);
		netMusicButton = new WindowsButton(imgTool
				.getIcon("images/netMusicBtn.png"), ButtonType.PAGE_BUTTON);
		localMusicButton = new WindowsButton(imgTool
				.getIcon("images/localMusicBtn.png"), ButtonType.PAGE_BUTTON);

		closeButton.setToolTipText("关闭");
		maxButton.setToolTipText("最大化");
		minButton.setToolTipText("最小化");
		skinButton.setToolTipText("主题");

		add(playingButton);
		add(netMusicButton);
		add(localMusicButton);
		add(closeButton);
		add(maxButton);
		add(minButton);
		add(skinButton);
	}

	/**
	 * 给按钮设置监听
	 * 
	 * @param listener
	 */
	public void setButtonListener(ActionListener listener) {
		playingButton.addActionListener(listener);
		netMusicButton.addActionListener(listener);
		localMusicButton.addActionListener(listener);
		closeButton.addActionListener(listener);
		maxButton.addActionListener(listener);
		minButton.addActionListener(listener);
		skinButton.addActionListener(listener);
	}

	public void setPage(PageState ps) {
		if (lastPageState != null && lastPageState.equals(ps)) {
			return;
		}
		if (lastPageState != null) {
			getButtonFromPage(lastPageState).setSelectState(false);
		}
		getButtonFromPage(ps).setSelectState(true);
		lastPageState = ps;
	}

	private WindowsButton getButtonFromPage(PageState ps) {
		if (ps == PageState.PLAYING) {
			return playingButton;
		} else if (ps == PageState.NET) {
			return netMusicButton;
		} else if (ps == PageState.LOACL) {
			return localMusicButton;
		}
		return null;
	}

	public WindowsButton getCloseButton() {
		return closeButton;
	}

	public WindowsButton getMaxButton() {
		return maxButton;
	}

	public WindowsButton getMinButton() {
		return minButton;
	}

	public WindowsButton getSkinButton() {
		return skinButton;
	}

	public WindowsButton getPlayingButton() {
		return playingButton;
	}

	public WindowsButton getNetMusicButton() {
		return netMusicButton;
	}

	public WindowsButton getLocalMusicButton() {
		return localMusicButton;
	}

	/**
	 * 设置标题栏主题
	 * 
	 * @param index
	 */
	public void setTheme(int index) {
		left_Image = imgTool.getImage("images/theme/" + (index + 1)
				+ "/TopPanelBKLeft.jpg");
		mid_Image = imgTool.getImage("images/theme/" + (index + 1)
				+ "/TopPanelBKMid.jpg");
		right_Image = imgTool.getImage("images/theme/" + (index + 1)
				+ "/TopPanelBKRight.jpg");
		repaint();
	}

	/**
	 * 初始化标题图片
	 */
	public void iniTitleImageLabel() {
		title_Label = new ImageLabel(imgTool.getImage("images/titleImage.jpg"));
		add(title_Label);
	}

	/**
	 * 设置标题
	 * 
	 * @param title
	 * @param x
	 * @param y
	 */
	public void setTitle(String title, int x, int y) {
		this.title = title;
		titleX = x;
		titleY = y;
	}

	/**
	 * 设置颜色
	 * 
	 * @param skinColor
	 */
	public void setSkinColor(Color skinColor) {
		this.skinColor = skinColor;
		repaint();
	}
	
	public void setWindowToMax() {
		maxButton.setIcon(imgTool.getIcon("images/MaxWindow.png"));
		maxButton.setToolTipText("还原");
	}
	
	public void recoverWindow() {
		maxButton.setIcon(imgTool.getIcon("images/MaxButton.png"));
		maxButton.setToolTipText("最大化");
	}

	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g.setFont(new Font("宋体", 20, 20));
		g.setColor(Color.white);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);// 反锯齿
		if (left_Image != null && mid_Image != null && right_Image != null) {
			g.drawImage(left_Image, 0, 0, left_Image.getWidth(this), getHeight(), this);
			for (int i = 0; i <= (getWidth() - left_Image.getWidth(this) - right_Image.getWidth(this)) / mid_Image.getWidth(this); i++) {
				g.drawImage(mid_Image, left_Image.getWidth(this) + mid_Image.getWidth(this) * i, 0, mid_Image.getWidth(this), getHeight(), this);
			}
			g.drawImage(right_Image, getWidth() - right_Image.getWidth(this), 0, right_Image.getWidth(this), getHeight(), this);
		}
		if (title_Label != null) {
			title_Label.setBounds(20, 15, 35, 35);
		}
		g.drawString(title, titleX, titleY);
		if (skinColor != null) {
			Rectangle clip = g.getClipBounds();
			Color newColor = FormatUtils.formatColorTransparent(skinColor, 120);
			g.setColor(newColor);
			g.fillRect(clip.x, clip.y, clip.width, clip.height);
		}
		playingButton.setBounds(200, getHeight() - 40, 90, 41);
		netMusicButton.setBounds(300, getHeight() - 40, 90, 41);
		localMusicButton.setBounds(400, getHeight() - 40, 90, 41);

		closeButton.setBounds(getWidth() - 30, 5, 16, 16);
		maxButton.setBounds(getWidth() - 50, 5, 16, 16);
		minButton.setBounds(getWidth() - 70, 5, 16, 16);
		skinButton.setBounds(getWidth() - 110, 5, 16, 16);
	}
}
