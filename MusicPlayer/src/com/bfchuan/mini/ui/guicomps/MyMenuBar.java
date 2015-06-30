package com.bfchuan.mini.ui.guicomps;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;


import com.bfchuan.mini.bo.ConfigBo;
import com.bfchuan.mini.bo.DownloadBo;
import com.bfchuan.mini.bo.FileBo;
import com.bfchuan.mini.bo.MusicBo;
import com.bfchuan.mini.bo.PlayModel;
import com.bfchuan.mini.bo.SongBo;
import com.bfchuan.mini.enums.CircleModel;
import com.bfchuan.mini.enums.PlayerState;
import com.bfchuan.mini.util.ImageTool;

/**
 * 菜单栏类
 * 作者:Loenidas
 * 时间:2012-5-10
 * piaobomengxiang@163.com
 * 版本:v1.0
 *
 */
@SuppressWarnings("serial")
public class MyMenuBar extends JMenuBar implements ActionListener {

	private String[] menusLab = { " 文 件  ", " 播 放  ", "播放模式", "歌词背景" };
	private JMenu[] menus = new JMenu[4];
	private String[] fileMenuItemLab = { "添加音乐文件", "添加音乐文件夹", "退 出" };
	private JMenuItem[] fileMenuItem = new JMenuItem[3];
	private String[] controlMenuItemLab = { "播放/暂停", " 停 止  " };
	private JMenuItem[] controlMenuItem = new JMenuItem[2];
	private String[] modelMenuItemLab = { "单曲循环", "全部循环", "随即播放" };
	private JMenuItem[] modelMenuItem = new JMenuItem[3];
	private String[] bgImageMenuItemLab = { "风  车", "倾  听", "蒲公英", "许  愿", "无背景" };
	private JMenuItem[] bgImageMenuItem = new JMenuItem[5];
    private int modelNum = -1;
    private int bgImageNum = -1;

	private static MyMenuBar menu;

	/**
	 * 构造函数
	 */
	private MyMenuBar() {
		setLayout(null);
		setOpaque(false);
		setBorder(BorderFactory.createLineBorder(Color.black, 1));
		setBackground(Color.black);

		initMenus();
		
		intAccelerator();
		setMenuBackgroundColor(ConfigBo.getInstance().getThemeBgColor());
		
		setActionListener(this);
		
		setCircleModel("全部循环");
		setLRCBackImage(bgImageMenuItemLab[ConfigBo.getInstance().getLrcBgIndex()]);
	}

	/**
	 * 此处为单例模式,返回静态菜单变量
	 * 
	 * @return 内部实例
	 */
	public static MyMenuBar getInstance() {
		if (menu == null) {
			menu = new MyMenuBar();
		}
		return menu;
	}

	/**
	 * 初始化菜单
	 */
	private void initMenus() {
		for (int i = 0; i < menus.length; i++) {
			menus[i] = new JMenu(menusLab[i]);
			menus[i].setFont(new Font("宋体", 12, 12));
			// 这里特殊处理，因为播放模式是子菜单
			if (i <= 1) {
				menus[i].setBounds(i * 55, 0, 50, 20);
				menus[i].setBackground(Color.black);
				menus[i].setForeground(Color.white);
				add(menus[i]);
			}
		}
		initJMenuItem(fileMenuItemLab, fileMenuItem, menus[0]);
		initJMenuItem(modelMenuItemLab, modelMenuItem, menus[2]);
		initJMenuItem(bgImageMenuItemLab, bgImageMenuItem, menus[3]);
		JMenu[] tempMenus = { menus[2], menus[3] };
		initJMenuItem(controlMenuItemLab, controlMenuItem, tempMenus, menus[1]);
	}

	/**
	 * 初始化menu菜单中的菜单项,其中只包含JMenuItem
	 * 
	 * @param lab是JMenuItem的内容
	 * @param menuItem是对应的JMenuItem
	 * @param destmenu是目的JMenu
	 */
	private void initJMenuItem(String[] lab, JMenuItem[] menuItem,
			JMenu destmenu) {
		for (int i = 0; i < menuItem.length; i++) {
			menuItem[i] = new JMenuItem(lab[i]);
			menuItem[i].setFont(new Font("宋体", 12, 12));
			menuItem[i].setIconTextGap(1);
			destmenu.add(menuItem[i]);
			if (i < menuItem.length - 1) {
				destmenu.addSeparator();
			}
		}
	}

	/**
	 * 初始化menu菜单中的菜单项,其中包含JMenuItem和JMenu
	 * 
	 * @param lab为JMenuItem的内容
	 * @param menuItem为对应的JMenuItem
	 * @param srcMenu是源JMenu数组
	 * @param destMenu是目的JMenu,所有的对象都是加到JMenu中
	 */
	private void initJMenuItem(String[] lab, JMenuItem[] menuItem,
			JMenu srcMenu[], JMenu destMenu) {
		initJMenuItem(lab, menuItem, destMenu);
		destMenu.insertSeparator(menuItem.length + 1);
		for (int i = 0; i < srcMenu.length; i++) {
			destMenu.add(srcMenu[i]);
			if (i < srcMenu.length - 1) {
				destMenu.addSeparator();
			}
		}
	}

	/**
	 * 设置菜单的快捷键
	 */
	public void intAccelerator() {
		fileMenuItem[0].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M,
				InputEvent.CTRL_MASK));
		fileMenuItem[1].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F,
				InputEvent.CTRL_MASK));
		fileMenuItem[2].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E,
				InputEvent.CTRL_MASK));
		controlMenuItem[0].setAccelerator(KeyStroke.getKeyStroke("F5"));
		controlMenuItem[1].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
				InputEvent.CTRL_MASK));
	}

	/**
	 * 此方法为私有方法，为JMenuItem增加监听
	 * 
	 * @param menuItem为要加载的ActionListener对象
	 * @param listener为传入的ActionListener
	 */
	private void setActionListener(JMenuItem[] menuItem, ActionListener listener) {
		for (int i = 0; i < menuItem.length; i++) {
			menuItem[i].addActionListener(listener);
		}
	}

	/**
	 * 此方法为公共方法，为所有的JMenuItem增加监听
	 * 
	 * @param listener为传入的ActionListener
	 */
	public void setActionListener(ActionListener listener) {
		setActionListener(fileMenuItem, listener);
		setActionListener(controlMenuItem, listener);
		setActionListener(modelMenuItem, listener);
		setActionListener(bgImageMenuItem, listener);
	}

	/**
	 * 设置菜单背景颜色
	 * 
	 * @param newColor为菜单颜色
	 */
	public void setMenuBackgroundColor(Color newColor) {
        for (int i = 2; i < menus.length; i++) {
            menus[i].setBackground(newColor);
        }
        for (int i = fileMenuItem.length - 1; i >= 0; i--) {
            fileMenuItem[i].setBackground(newColor);
        }
        for (int i = controlMenuItem.length - 1; i >= 0; i--) {
            controlMenuItem[i].setBackground(newColor);
        }
        for (int i = modelMenuItem.length - 1; i >= 0; i--) {
            modelMenuItem[i].setBackground(newColor);
        }
        for (int i = bgImageMenuItem.length - 1; i >= 0; i--) {
            bgImageMenuItem[i].setBackground(newColor);
        }
	}
	
    /**
     * 用来设置播放模式
     * @param model是循环模式的字符串值
     */
    public void setCircleModel(String model) {
        for (int i = 0; i < modelMenuItem.length; i++) {
            if (model.equals(modelMenuItemLab[i]) && i != modelNum) {
                modelMenuItem[i].setIcon(ImageTool.getInstance().getIcon("images/select.png"));
                if (modelNum != -1) {
                    modelMenuItem[modelNum].setIcon(null);
                }
                PlayModel.getInstance().setCircleModel(CircleModel.valueOf(modelMenuItemLab[i]));
                modelNum = i;
                break;
            }
        }
    }

    /**
     * 设置歌词背景
     * @param bgImage是传入菜单项的字符串值
     */
    public void setLRCBackImage(String bgImage) {
        for (int i = 0; i < bgImageMenuItem.length; i++) {
            if (bgImage.equals(bgImageMenuItemLab[i]) && i != bgImageNum) {
                bgImageMenuItem[i].setIcon(ImageTool.getInstance().getIcon("images/select.png"));
                if (bgImageNum != -1) {
                    bgImageMenuItem[bgImageNum].setIcon(null);
                }
                ConfigBo.getInstance().setLrcBgIndex(i);
                bgImageNum = i;
                break;
            }
        }
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		String str = e.getActionCommand().trim();
		MusicBo musicBo = MusicBo.getInstance();
		if (str.equals("添加音乐文件")) {
			String msg = FileBo.getInstance().openMusicFile();
			if (!("".equals(msg))) {
				JOptionPane.showMessageDialog(null, msg);
			}
		} else if (str.equals("添加音乐文件夹")) {
			String msg = FileBo.getInstance().openMusicFolder();
			if (!("".equals(msg))) {
				JOptionPane.showMessageDialog(null, msg);
			}
		} else if (str.equals("退 出")) {
			ConfigBo.getInstance().saveConfig();
			SongBo.getInstance().saveSongList();
			DownloadBo.getInstance().saveTaskList();
			System.exit(0);
		} else if (str.equals("播放/暂停") &&
				musicBo.getPlayerState().equals(PlayerState.PLAY)) {
			musicBo.pause();
		} else if (str.equals("播放/暂停") &&
				musicBo.getPlayerState().equals(PlayerState.PAUSE)) {
			musicBo.resume();
		} else if (str.equals("播放/暂停") &&
				musicBo.getPlayerState().equals(PlayerState.UNREALIZED)) {
			//.......
		} else if (str.equals("停 止")) {
			musicBo.stop();
		} else if (str.equals("单曲循环") || str.equals("全部循环") || str.equals("随即播放")) {
			this.setCircleModel(str);
		} else if (str.equals("风  车") || str.equals("倾  听") || str.equals("蒲公英") || 
				str.equals("许  愿") || str.equals("无背景")) {
			this.setLRCBackImage(str);
		}
	}

}
