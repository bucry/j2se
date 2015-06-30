package com.bfchuan.mini.ui.myguis;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.event.MouseInputListener;

import com.bfchuan.mini.util.DesktopInfo;
import com.bfchuan.mini.util.Global;
import com.bfchuan.mini.util.ImageTool;

/**
 * 自定义Frame
 * 作者:Loenidas
 * 时间:2012-5-10
 * piaobomengxiang@163.com
 * 版本:v1.0
 *
 */
@SuppressWarnings("serial")
public class MyFrame extends JFrame implements MouseInputListener {

	private Point preLocation;
	private Point preMouse;
	private Rectangle preRectangle;
	protected JLayeredPane pnl;
	protected Rectangle maxWindowSize;
	protected Rectangle beforeMaxWindowSize;
	private Image doubleBuffer;
	private Graphics gImage;

	/**
	 * 构造函数，自定义Frame
	 */
	public MyFrame() {
		pnl = new JLayeredPane();
		pnl.setLayout(null);
		add(pnl);
		addMouseListener(this);
		addMouseMotionListener(this);
		setUndecorated(true);
		setIconImage(ImageTool.getInstance().getImage("images/titleImage.jpg"));
		setMinimumSize(new Dimension(700, 500));
		maxWindowSize = DesktopInfo.getMaxSize();
	}

	public void mouseMoved(MouseEvent mouseevent) {
		if (mouseevent.getX() >= getWidth() - 8 && mouseevent.getY() <= 8) {
			pnl.setCursor(new Cursor(Cursor.NE_RESIZE_CURSOR));
		} else if (mouseevent.getX() <= 8 && mouseevent.getY() <= 8) {
			pnl.setCursor(new Cursor(Cursor.NW_RESIZE_CURSOR));
		} else if (mouseevent.getX() <= 8
				&& mouseevent.getY() >= getHeight() - 8) {
			pnl.setCursor(new Cursor(Cursor.SW_RESIZE_CURSOR));
		} else if (mouseevent.getX() >= getWidth() - 8
				&& mouseevent.getY() >= getHeight() - 8) {
			pnl.setCursor(new Cursor(Cursor.SE_RESIZE_CURSOR));
		} else if (mouseevent.getX() > 8 && mouseevent.getX() < getWidth() - 8
				&& mouseevent.getY() <= 8) {
			pnl.setCursor(new Cursor(Cursor.N_RESIZE_CURSOR));
		} else if (mouseevent.getX() >= getWidth() - 8
				&& mouseevent.getY() < getHeight() - 8 && mouseevent.getY() > 8) {
			pnl.setCursor(new Cursor(Cursor.E_RESIZE_CURSOR));
		} else if (mouseevent.getY() >= getHeight() - 8
				&& mouseevent.getX() > 8 && mouseevent.getX() < getWidth() - 8) {
			pnl.setCursor(new Cursor(Cursor.S_RESIZE_CURSOR));
		} else if (mouseevent.getX() <= 8
				&& mouseevent.getY() < getHeight() - 8 && mouseevent.getY() > 8) {
			pnl.setCursor(new Cursor(Cursor.W_RESIZE_CURSOR));
		} else {
			pnl.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
	}

	public void mouseReleased(MouseEvent e) {
	}

	public void mouseClicked(MouseEvent e) {
		if (e.getClickCount() == 2 && e.getY() >= 0
				&& e.getY() <= Global.BAR_HEIGHT
				&& pnl.getCursor().getType() == Cursor.DEFAULT_CURSOR
				&& !getBounds().equals(maxWindowSize)) {
			beforeMaxWindowSize = getBounds();
			setBounds(maxWindowSize);
			repaint();
		} else if (e.getClickCount() == 2 && e.getY() >= 0
				&& e.getY() <= Global.BAR_HEIGHT
				&& pnl.getCursor().getType() == Cursor.DEFAULT_CURSOR
				&& getBounds().equals(maxWindowSize)) {
			setBounds(beforeMaxWindowSize);
			repaint();
		}
	}

	public void mousePressed(MouseEvent e) {
		preLocation = this.getLocationOnScreen();// 得到绝对坐标
		preMouse = e.getLocationOnScreen();
		preRectangle = this.getBounds();
	}

	public void mouseDragged(MouseEvent e) {
		if (!getBounds().equals(maxWindowSize)) {
			Point curMouse;
			curMouse = e.getLocationOnScreen();
			int disX = curMouse.x - preMouse.x;
			int disY = curMouse.y - preMouse.y;
			if (pnl.getCursor().getType() == Cursor.DEFAULT_CURSOR) {
				this.setLocation(preLocation.x + disX, preLocation.y + disY);
			} else if (pnl.getCursor().getType() == Cursor.E_RESIZE_CURSOR) {
				this.setSize(preRectangle.width + disX, preRectangle.height);
			} else if (pnl.getCursor().getType() == Cursor.N_RESIZE_CURSOR) {
				this.setBounds(preRectangle.x, preRectangle.y + disY,
						preRectangle.width, preRectangle.height - disY);
			} else if (pnl.getCursor().getType() == Cursor.W_RESIZE_CURSOR) {
				this.setBounds(preRectangle.x + disX, preRectangle.y,
						preRectangle.width - disX, preRectangle.height);
			} else if (pnl.getCursor().getType() == Cursor.S_RESIZE_CURSOR) {
				this.setSize(preRectangle.width, preRectangle.height + disY);
			} else if (pnl.getCursor().getType() == Cursor.NE_RESIZE_CURSOR) {
				this.setBounds(preRectangle.x, preRectangle.y + disY,
						preRectangle.width + disX, preRectangle.height - disY);
			} else if (pnl.getCursor().getType() == Cursor.NW_RESIZE_CURSOR) {
				this.setBounds(preRectangle.x + disX, preRectangle.y + disY,
						preRectangle.width - disX, preRectangle.height - disY);
			} else if (pnl.getCursor().getType() == Cursor.SW_RESIZE_CURSOR) {
				this.setBounds(preRectangle.x + disX, preRectangle.y,
						preRectangle.width - disX, preRectangle.height + disY);
			} else if (pnl.getCursor().getType() == Cursor.SE_RESIZE_CURSOR) {
				this.setBounds(preRectangle.x, preRectangle.y,
						preRectangle.width + disX, preRectangle.height + disY);
			}
		}
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void paint(Graphics g) {// 这里使用双缓冲
		if (doubleBuffer == null || doubleBuffer.getWidth(this) != getWidth()
				|| doubleBuffer.getHeight(this) != getHeight()) {
			doubleBuffer = this.createImage(getWidth(), getHeight());
		}
		gImage = doubleBuffer.getGraphics();
		gImage.setColor(Color.WHITE);
		gImage.fillRect(0, 0, WIDTH, HEIGHT);
		super.paint(gImage);
		gImage.dispose();// 这句不能丢
		g.drawImage(doubleBuffer, 0, 0, null);
		g.dispose();
	}
}
