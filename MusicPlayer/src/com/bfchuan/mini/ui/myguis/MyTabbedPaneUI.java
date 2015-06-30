package com.bfchuan.mini.ui.myguis;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.LayoutManager;
import javax.swing.JComponent;
import javax.swing.UIManager;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicTabbedPaneUI;

/**
 * 自定义TabbedPane的UI
 * 作者:Loenidas
 * 时间:2012-5-10
 * piaobomengxiang@163.com
 * 版本:v1.0
 *
 */
public class MyTabbedPaneUI extends BasicTabbedPaneUI {

	private static Color defaultColor = new Color(255, 204, 255);

	public static ComponentUI createUI(JComponent c) {
		return new MyTabbedPaneUI();
	}

	@Override
	protected void paintTabBackground(Graphics g, int tabPlacement,
			int tabIndex, int x, int y, int w, int h, boolean isSelected) {
		if (!isSelected) {
			g.setColor(defaultColor.brighter());
		} else {
			g.setColor(defaultColor);
		}
		switch (tabPlacement) {
		case LEFT:
			g.fillRect(x + 1, y + 1, w - 1, h - 3);
			break;
		case RIGHT:
			g.fillRect(x, y + 1, w - 2, h - 3);
			break;
		case BOTTOM:
			g.fillRect(x + 1, y, w - 3, h - 1);
			break;
		default:
			g.fillRect(x + 1, y + 1, w - 3, h - 1);
		}

	}

	@Override
	protected LayoutManager createLayoutManager() {
		return new TabbedPaneLayout();
	}

	public class TabbedPaneLayout extends BasicTabbedPaneUI.TabbedPaneLayout {

		@Override
		protected void calculateTabRects(int tabPlacement, int tabCount) {
			super.calculateTabRects(tabPlacement, tabCount);
			tabInsets.bottom = 3;// 设置选项卡高度
		}
	}

	public static void setDefaultColor(Color defaultColor) {
		MyTabbedPaneUI.defaultColor = defaultColor;
	}

	@Override
	protected void paintContentBorder(Graphics g, int tabPlacement,
			int selectedIndex) {
		int width = tabPane.getWidth();
		int height = tabPane.getHeight();
		Insets insets = tabPane.getInsets();
		Insets tabAreaInsets_ = getTabAreaInsets(tabPlacement);

		int x = insets.left;
		int y = insets.top;
		int w = width - insets.right - insets.left;
		int h = height - insets.top - insets.bottom;

		switch (tabPlacement) {
		case LEFT:
			x += calculateTabAreaWidth(tabPlacement, runCount, maxTabWidth);
			w -= (x - insets.left);
			break;
		case RIGHT:
			w -= calculateTabAreaWidth(tabPlacement, runCount, maxTabWidth);
			w += tabAreaInsets_.left;
			break;
		case BOTTOM:
			h -= calculateTabAreaHeight(tabPlacement, runCount, maxTabHeight);
			h += tabAreaInsets_.top;
			break;
		case TOP:
		default:
			y += calculateTabAreaHeight(tabPlacement, runCount, maxTabHeight);
			y -= tabAreaInsets_.bottom;
			h -= (y - insets.top);
		}

		// Fill region behind content area
		Color color = UIManager.getColor("TabbedPane.contentAreaColor");
		if (color != null) {
			g.setColor(color);
		} else {
			g.setColor(defaultColor);
		}
		g.fillRect(x, y, w, h);
		g.setColor(defaultColor);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setStroke(new BasicStroke(10.0f));

		g.drawLine(x + 6, y + 5, w, y + 5);
		g2d.setStroke(new BasicStroke(1.0f));

	}
}
