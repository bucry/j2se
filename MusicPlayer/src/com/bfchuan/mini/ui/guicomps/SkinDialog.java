package com.bfchuan.mini.ui.guicomps;

import java.awt.Color;
import java.awt.Scrollbar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JPanel;


import com.bfchuan.mini.bo.ConfigBo;
import com.bfchuan.mini.ui.MiniMusic;
import com.bfchuan.mini.ui.myguis.LinkButton;
import com.bfchuan.mini.ui.myguis.SelectedLabel;
import com.bfchuan.mini.util.Global;
import com.bfchuan.mini.util.ImageTool;

/**
 * 主题操作类
 * 作者:Loenidas
 * 时间:2012-5-10
 * piaobomengxiang@163.com
 * 版本:v1.0
 *
 */
@SuppressWarnings("serial")
public class SkinDialog extends JDialog implements ActionListener, AdjustmentListener {

    public static SkinDialog skinDlg;
    private TitlePanel titlePanel = TitlePanel.getInstance();
    private JPanel mainPnl;
    private JPanel themePnl;
    private JPanel themeListPnl;
    private Scrollbar themeScrollBar;
    private JButton themeButton[] = new JButton[30];
    private LinkButton ccButton = new LinkButton("更换颜色");
    private ImageTool imgTool = ImageTool.getInstance();
    private SelectedLabel selectLab;
    private ConfigBo configBo = ConfigBo.getInstance();
    private int selectNum = configBo.getThemeByIndex();

    private SkinDialog() {
    	mainPnl = new JPanel();
    	
        mainPnl.setLayout(null);
        mainPnl.setBackground(Color.white);

        themePnl = new JPanel();
        themePnl.setLayout(null);
        themeListPnl = new JPanel();
        themeListPnl.setLayout(null);
        themeListPnl.setBounds(0, 0, 210, 505);
        themeListPnl.setBackground(Color.white);
        initThemeLabs();
        themePnl.add(themeListPnl);
        themePnl.setBounds(10, 15, 210, 150);
        
        mainPnl.add(themePnl);

        themeScrollBar = new Scrollbar();
        themeScrollBar.setMinimum(0);
        themeScrollBar.setMaximum(365);
        themeScrollBar.setValue(0);
        themeScrollBar.setBounds(221, 15, 20, 150);
        themeScrollBar.addAdjustmentListener(this);
        mainPnl.add(themeScrollBar);

        ccButton.setBounds(10, 170, 65, 20);
        ccButton.addActionListener(this);
        mainPnl.add(ccButton);
        
        //初始化selectLab
        selectLab = new SelectedLabel(imgTool.getIcon("images/theme/1/small.jpg"));
        
        add(mainPnl);
        setTitle("主题");
        setIconImage(imgTool.getImage("images/titleImage.jpg"));
        this.setResizable(false);
        setSize(260, 230);
        //加载主题
        selectTheme(configBo.getThemeByIndex());
        refresh();
    }
    
    public static SkinDialog getInstance() {
    	if (skinDlg == null) {
    		skinDlg = new SkinDialog();
    	}
    	return skinDlg;
    }

    public void refresh() {
        themeListPnl.setBorder(BorderFactory.createLineBorder(configBo.getThemeFgColor(), 1));
        themePnl.setBorder(BorderFactory.createLineBorder(configBo.getThemeFgColor(), 1));
        ccButton.setDefaultColor(configBo.getThemeFgColor());
        //更换主界面边框颜色
        MiniMusic.getInstance().setBorderColor(configBo.getSongListDisplayColor());
    }

    /**
     * 初始化主题标签
     */
    public void initThemeLabs() {
        int length = themeButton.length;
        for (int i = 0; i < length; i++) {
            themeButton[i] = new JButton(imgTool.getIcon("images/theme/" + (i + 1) + "/small.jpg"));
            int boundsX;
            if (i % 3 == 0) {
                boundsX = 5;
            } else if (i % 3 == 1) {
                boundsX = 75;
            } else {
                boundsX = 145;
            }
            themeButton[i].setBounds(boundsX, 5 + (i / 3) * 50, 60, 40);
            themeButton[i].addActionListener(this);
            themeListPnl.add(themeButton[i]);
        }
    }

    /**
     * 选择主题
     * @param index
     */
    public void selectTheme(int index) {
        if (index >= 0 && index < Global.TOTAL_THEME_PROPERTIES) {
            if (!themeListPnl.isAncestorOf(selectLab)) {
                selectLab.setLabIcon(imgTool.getIcon("images/theme/" + (index + 1) + "/small.jpg"));
                themeListPnl.remove(themeButton[index]);
                selectLab.setBounds(themeButton[index].getBounds());
                themeListPnl.add(selectLab);
                configBo.setThemeIndex(index);
                refresh();
                selectNum = index;
            } else if (index != selectNum) {
                themeListPnl.add(themeButton[selectNum]);
                selectLab.setLabIcon(imgTool.getIcon("images/theme/" + (index + 1) + "/small.jpg"));
                themeListPnl.remove(themeButton[index]);
                selectLab.setBounds(themeButton[index].getBounds());
                configBo.setThemeIndex(index);
                refresh();
                selectNum = index;
            }
        }
    }

    public void actionPerformed(ActionEvent e) {
        Object obj = e.getSource();
        if (obj == ccButton) {
            Color newColor = JColorChooser.showDialog(this, "更换颜色", null);
            if (newColor != null) {
                titlePanel.setSkinColor(newColor);
            }
        } else {
            int length = themeButton.length;
            for (int i = 0; i < length; i++) {
                if (obj == themeButton[i]) {
                    selectTheme(i);
                    break;
                }
            }
        }
    }

    public void adjustmentValueChanged(AdjustmentEvent e) {
        themeListPnl.setLocation(themeListPnl.getLocation().x, -e.getValue());
    }

    /*public static void main(String args[]) {
        SkinDialog sd = SkinDialog.getInstance();
        sd.setVisible(true);
    }*/
  
}
