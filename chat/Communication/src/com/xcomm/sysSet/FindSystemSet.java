package com.xcomm.sysSet;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.Dictionary;
import java.util.Hashtable;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


/**
 * 系统设置
 * 作者：Leonidas 
 * piaobomengxiang@163.com
 * 时间：2013-9-14
 * 版本：1.0
 * 描述：系统设置，在此做例如头像，登录状态，个人资料
 * 等完整设置，同时还能设置是否有提示音乐
 */
public class FindSystemSet
{
	JFrame mainWin = new JFrame("系统设置");
	Box sliderBox = new Box(BoxLayout.Y_AXIS);
	JTextField showVal = new JTextField();
	ChangeListener listener;
	public void init() 
	{
		//定义一个监听器，用于监听所有滑动条
		listener = new ChangeListener()
		{  
			public void stateChanged(ChangeEvent event)
			{  
				//取出滑动条的值，并在文本中显示出来
				JSlider source = (JSlider) event.getSource();
				showVal.setText("当前滑动条的值为：" + source.getValue());
			}
		};
		//-----------添加一个普通滑动条-----------
		JSlider slider = new JSlider();
		addSlider(slider, "普通滑动条");

		//-----------添加保留区为30的滑动条-----------
		slider = new JSlider();
		slider.setExtent(30);
		addSlider(slider, "保留区为30");

		//-----------添加带主、次刻度的滑动条,并设置其最大值，最小值-----------
		slider = new JSlider(30 , 200);
		//设置绘制刻度
		slider.setPaintTicks(true);
		//设置主、次刻度的间距
		slider.setMajorTickSpacing(20);
		slider.setMinorTickSpacing(5);
		addSlider(slider, "有刻度");

		//-----------添加滑块必须停在刻度处滑动条-----------
		slider = new JSlider();
		//设置滑块必须停在刻度处
		slider.setSnapToTicks(true);
		//设置绘制刻度
		slider.setPaintTicks(true);
		//设置主、次刻度的间距
		slider.setMajorTickSpacing(20);
		slider.setMinorTickSpacing(5);
		addSlider(slider, "滑块停在刻度处");

		//-----------添加没有滑轨的滑动条-----------
		slider = new JSlider();
		//设置绘制刻度
		slider.setPaintTicks(true);
		//设置主、次刻度的间距
		slider.setMajorTickSpacing(20);
		slider.setMinorTickSpacing(5);
		//设置不绘制滑轨
		slider.setPaintTrack(false);
		addSlider(slider, "无滑轨");

		//-----------添加方向反转的滑动条-----------
		slider = new JSlider();
		//设置绘制刻度
		slider.setPaintTicks(true);
		//设置主、次刻度的间距
		slider.setMajorTickSpacing(20);
		slider.setMinorTickSpacing(5);
		//设置方向反转
		slider.setInverted(true);
		addSlider(slider, "方向反转");

		//-----------添加绘制默认刻度标签的滑动条-----------
		slider = new JSlider();
		//设置绘制刻度
		slider.setPaintTicks(true);
		//设置主、次刻度的间距
		slider.setMajorTickSpacing(20);
		slider.setMinorTickSpacing(5);
		//设置绘制刻度标签，默认绘制数值刻度标签
		slider.setPaintLabels(true);
		addSlider(slider, "数值刻度标签");

		//-----------添加绘制Label类型的刻度标签的滑动条----------- 
		slider = new JSlider();
		//设置绘制刻度
		slider.setPaintTicks(true);
		//设置主、次刻度的间距
		slider.setMajorTickSpacing(20);
		slider.setMinorTickSpacing(5);
		//设置绘制刻度标签
		slider.setPaintLabels(true);
		Dictionary<Integer, Component> labelTable = new Hashtable<Integer, Component>();
		labelTable.put(0, new JLabel("A"));
		labelTable.put(20, new JLabel("B"));
		labelTable.put(40, new JLabel("C"));
		labelTable.put(60, new JLabel("D"));
		labelTable.put(80, new JLabel("E"));
		labelTable.put(100, new JLabel("F"));
		//指定刻度标签，标签是JLabel
		slider.setLabelTable(labelTable);
		addSlider(slider, "JLable标签");

		//-----------添加绘制Label类型的刻度标签的滑动条----------- 
		slider = new JSlider();
		//设置绘制刻度
		slider.setPaintTicks(true);
		//设置主、次刻度的间距
		slider.setMajorTickSpacing(20);
		slider.setMinorTickSpacing(5);
		//设置绘制刻度标签
		slider.setPaintLabels(true);
		labelTable = new Hashtable<Integer, Component>();
		labelTable.put(0, new JLabel(new ImageIcon("ico/0.GIF")));
		labelTable.put(20, new JLabel(new ImageIcon("ico/2.GIF")));
		labelTable.put(40, new JLabel(new ImageIcon("ico/4.GIF")));
		labelTable.put(60, new JLabel(new ImageIcon("ico/6.GIF")));
		labelTable.put(80, new JLabel(new ImageIcon("ico/8.GIF")));
		//指定刻度标签，标签是ImageIcon
		slider.setLabelTable(labelTable);
		addSlider(slider, "Icon标签");

		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		mainWin.setLocation((screen.width - mainWin.getSize().width) / 2,
				(screen.height - mainWin.getSize().height) / 2);
		//修改LOGO
		Toolkit tk=Toolkit.getDefaultToolkit();
		Image image=tk.createImage("/home/java/Work/Communication/icon/icon.gif");
		mainWin.setIconImage(image);
		mainWin.setResizable(false);
		mainWin.add(sliderBox, BorderLayout.CENTER);
		mainWin.add(showVal, BorderLayout.SOUTH);
		mainWin.pack();
		mainWin.setVisible(true);

	}
	//定义一个方法，用于将滑动条添加到容器中
	public void addSlider(JSlider slider, String description)
	{        
		slider.addChangeListener(listener);
		Box box = new Box(BoxLayout.X_AXIS);
		box.add(new JLabel(description + "："));
		box.add(slider);
		sliderBox.add(box);
	}
}