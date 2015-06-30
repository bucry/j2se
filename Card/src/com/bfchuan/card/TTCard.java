package com.bfchuan.card;
//Download by http://www.codefans.net
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class TTCard extends JLabel implements MouseListener{

	
	private static final long serialVersionUID = -4590127474545680976L;
	
	TTMain main;//Main�������
	String name;//ͼƬurl����
	boolean up;//�Ƿ�������
	boolean canClick=false;//�Ƿ�ɱ����
	boolean clicked=false;//�Ƿ�����
	public TTCard(TTMain m,String name,boolean up){
		this.main=m;
		this.name=name;
		this.up=up;
	    if(this.up)
	    	this.turnFront();
	    else {
			this.turnRear();
		}
		this.setSize(71, 96);
		this.setVisible(true);
		this.addMouseListener(this);
	}
	//����
	public void turnFront() {
		this.setIcon(new ImageIcon("images/" + name + ".gif"));
		this.up = true;
	}
	//����
	public void turnRear() {
		this.setIcon(new ImageIcon("images/rear.gif"));
		this.up = false;
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
	}
	public void mouseEntered(MouseEvent arg0) {}
	public void mouseExited(MouseEvent arg0) {}
	public void mousePressed(MouseEvent e) {
		if(canClick)
		{
			Point from=this.getLocation();
			int step; //�ƶ��ľ���
			if(clicked)
				step=-20;
			else {
				step=20;
			}
			clicked=!clicked; //����
			//����ѡ�е�ʱ����ǰ�ƶ�һ��/����һ��
			TTCommon.move(this,from,new Point(from.x,from.y-step));
		}
	}
	public void mouseReleased(MouseEvent arg0) {
		
	}

}