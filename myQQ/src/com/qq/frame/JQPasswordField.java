package com.qq.frame;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JPasswordField;
import javax.swing.text.DefaultCaret;

public class JQPasswordField extends JPasswordField {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public JQPasswordField(){
		setFont(new Font("Times New Roman",2,24));
		setDocument(new PwdDoc());
		setDragEnabled(false);
		setHighlighter(null);
		this.setCaret(new DefaultCaret(){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			@Override
			public void setSelectionVisible(boolean vis) {
					return;
			}
			@Override
			protected void moveCaret(MouseEvent e) {
				return ;
			}
			
		});
	}
	@Override
	public void cut() {
		return ;
	}
	@Override
	public void copy() {
		return ;
	}
	@Override
	public void paste() {
		return ;
	}
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		URL imgUrl=getClass().getResource("pics/keyboard.png");
		ImageIcon img=new ImageIcon(imgUrl);
		g.drawImage(img.getImage(), getSize().width-img.getIconWidth()-2, 1, img.getIconWidth(), img.getIconHeight(), this);
	}
	
}
