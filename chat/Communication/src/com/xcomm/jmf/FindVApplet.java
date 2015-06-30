package com.xcomm.jmf;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.media.Buffer;
import javax.media.CaptureDeviceInfo;
import javax.media.CaptureDeviceManager;
import javax.media.ConfigureCompleteEvent;
import javax.media.ControllerEvent;
import javax.media.DataSink;
import javax.media.Format;
import javax.media.IncompatibleSourceException;
import javax.media.Manager;
import javax.media.MediaLocator;
import javax.media.NoProcessorException;
import javax.media.Player;
import javax.media.Processor;
import javax.media.RealizeCompleteEvent;
import javax.media.control.FrameGrabbingControl;
import javax.media.datasink.DataSinkListener;
import javax.media.format.VideoFormat;
import javax.media.protocol.DataSource;
import javax.media.protocol.FileTypeDescriptor;
import javax.media.protocol.SourceCloneable;
import javax.media.util.BufferToImage;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;




public class FindVApplet extends JApplet implements ActionListener{
	
	private static final long serialVersionUID = 2988107272445043996L;
	
	private JPanel jContentPane= null;
	private JButton jbok,jbcancel,tuichuluxiang ;	
	private	MediaLocator ml = null;
	private	Player player = null;		 
	private DataSource datasource=null;		 
	private Buffer buffer=null;
	private BufferToImage bufferToImage=null;
	private Image image=null;
	private Processor processor=null;	
	//由原始数据源转变成的，可以被克隆的数据源 
	private DataSource cloneabledatasource = null;	
	//由可以克隆的数据源cloneabledatasource克隆出来的cloneddatasource 
	private DataSource cloneddatasource = null; 
	//保存录制数据的数据池 
	private DataSink dataSink = null;
	private StateHelper sh=null;

	//构造函数
	public FindVApplet() {
		super();
	}
	
	public void init() {		
		jbok=new JButton("拍   照");
		jbok.addActionListener(this);		
		jbcancel=new JButton("开始录像");
		jbcancel.addActionListener(this);		
		tuichuluxiang=new JButton("停止录像");
		tuichuluxiang.addActionListener(this);
		this.setSize(400, 320); //(650, 550)
		
		jContentPane=getJContentPane();
		jbok.setVisible(true);
		jbcancel.setVisible(true);
		tuichuluxiang.setVisible(true);
		
		Container cp=getContentPane();
		cp.add(jContentPane,BorderLayout.NORTH);
		cp.add(jbok,BorderLayout.WEST);
		cp.add(jbcancel,BorderLayout.CENTER);
		cp.add(tuichuluxiang,BorderLayout.EAST);
		
		this.setContentPane(cp);
		this.setName("VApplet");
	}
	
	//取系统所有可采集的硬件设备列表
	private CaptureDeviceInfo[] getDevices() {
		Vector<?> devices = CaptureDeviceManager.getDeviceList(null);
		CaptureDeviceInfo[] info = new CaptureDeviceInfo[devices.size()];
		for (int i = 0; i < devices.size(); i++) {
			info[i]= (CaptureDeviceInfo) devices.get(i);
		}
		return info;
	}
	
	//从已知设备中取所有视频设备的列表
	@SuppressWarnings("unused")
	private CaptureDeviceInfo[] getVideoDevices() {
		CaptureDeviceInfo[] info = getDevices();
		CaptureDeviceInfo[] videoDevInfo;
		Vector<CaptureDeviceInfo> vc = new Vector<CaptureDeviceInfo>();
		for (int i = 0; i < info.length; i++) {
			//取设备支持的格式，如果有一个是视频格式，则认为此设备为视频设备
			Format[] fmt = info[i].getFormats();
			for (int j = 0; j < fmt.length; j++) {
				if (fmt[j] instanceof VideoFormat) {
					vc.add(info[i]);
				}
				break;
			}
		}
		videoDevInfo = new CaptureDeviceInfo[vc.size()];
		for (int i = 0; i < vc.size(); i++) {
			videoDevInfo[i]= (CaptureDeviceInfo) vc.get(i);
		}
		return videoDevInfo;
	} 
	
private JPanel getJContentPane() {
	if (jContentPane == null) {
		BorderLayout borderLayout = new BorderLayout();
		jContentPane = new JPanel();
		jContentPane.setLayout(borderLayout);
	
		try {
		
			//这里我只有一个视频设备，直接取第一个
		// 取得当前设备的MediaLocator
		ml = getVideoDevices()[0].getLocator();////从已知设备中取所有视频设备的列表
		
		datasource = Manager.createDataSource(ml); 
		cloneabledatasource = Manager.createCloneableDataSource(datasource);
		cloneddatasource = ((SourceCloneable) cloneabledatasource).createClone(); 
		
		//用已经取得的MediaLocator得到一个Player
		player = Manager.createRealizedPlayer(cloneabledatasource); 
		player.start();
		
		//取得Player的AWT Component 
		Component comp = player.getVisualComponent(); 
		//如果是音频设备这个方法将返回null,这里要再判断一次
		if (comp != null) {
			//将Component加到窗体 
			jContentPane.add(comp,BorderLayout.NORTH); 
		 }
		}catch (Exception e) {
			e.printStackTrace();
		}
	  }
	  return jContentPane;
	}

	 
public void actionPerformed(ActionEvent e) {
	//保存图片   
	if(e.getSource()==jbok){//拍照
		Component comp = player.getVisualComponent(); 
		comp.getGraphics();
		FrameGrabbingControl fgc=(FrameGrabbingControl)player.getControl("javax.media.control.FrameGrabbingControl");
		buffer=fgc.grabFrame();
			
		bufferToImage=new BufferToImage((VideoFormat)buffer.getFormat());
		image=bufferToImage.createImage(buffer);
		if(image==null||image.equals("null")||image.equals(null)){
			JOptionPane.showMessageDialog(null, "请先拍照再保存"); 
		}else{
		 try {
			ImageIO.write((RenderedImage)image, "GIF", new File("D:\\2.jpg"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			}
		}			 
		}else if(e.getSource()==jbcancel&&processor!=null){//录像
			JOptionPane.showMessageDialog(null, "正在录像！"); 
		}else if(e.getSource()==jbcancel&&processor==null){//录像
			Frame saveluxiangframe = new Frame("保存视频文件"); 
			FileDialog fshipin = new FileDialog(saveluxiangframe, "保存视频文件", 
			FileDialog.SAVE); 
			fshipin.setFile("w.QUICKTIME"); 
			fshipin.setVisible(true); 
			String luxiangsavepath = fshipin.getDirectory(); 
			String luxiangsavename = fshipin.getFile(); 
//			 如果输入了保存名字（没有点取消）的话，才可运行 
			if (luxiangsavename != null) { 
//			 后面的路径不认\\，只认/ 
			luxiangsavepath.replace("\\", "/"); 
//			 录像源代码 
			try {// player用cloneabledatasource数据源，processor用cloneddatasource的数据源 
			processor = Manager.createProcessor(cloneddatasource); 
			sh = new StateHelper(processor); 
			} catch (IOException ez5) { 
			ez5.printStackTrace(); 
			System.exit(-1); 
			} catch (NoProcessorException ez6) { 
			ez6.printStackTrace(); 
			System.exit(-1); 
			} 

//			 Configure the processor,让processor进入configured状态 
			if (!sh.configure(10000)) { 
			System.out.println("configure wrong!"); 
			System.exit(-1); 
			} 

			/* 
			* if ( processor instanceof Processor) processor.configure(); 
			*/ 

//			 设置视频输出格式 
			processor.setContentDescriptor(new FileTypeDescriptor( 
			FileTypeDescriptor.QUICKTIME)); 

//			 realize the processor，让processor进入realized状态 
			if (!sh.realize(10000)) { 
			System.out.println("realize wrong!"); 
			System.exit(-1); 
			} 

//			 get the output of the processor，并且启动processor 
			DataSource outsource = processor.getDataOutput(); 
			MediaLocator dest = new MediaLocator(new java.lang.String( 
			"file:///" + luxiangsavepath + luxiangsavename)); 


			processor.start(); 
			try{
			////////////////////////////////////
				dataSink = Manager.createDataSink(outsource, dest);					
				dataSink.open(); 				
			    dataSink.start(); 
			  }catch(Exception ez1) { 
				ez1.printStackTrace(); 
				System.exit(-1); 
			  } 
			}
		}else if(e.getSource()==tuichuluxiang && processor == null){//退出录像
			JOptionPane.showMessageDialog(null, "请先单击录像才能停止！"); 
		}else if(e.getSource()==tuichuluxiang && processor!= null){//退出录像
			/* 
			* 如果要是能够连续录像，关键在于两点： 1 、重新设置cloneddatasource ， 
			* 我认为在cloneddatasource被调用后 ， cloneddatasource被改变了 2、清空processor 
			*/ 
			processor.close(); 
			processor.deallocate(); 
			dataSink.close(); 
			cloneddatasource = (DataSource)((SourceCloneable) cloneabledatasource).createClone(); 
			processor = null; 
		}
	 }

/////////////////////////状态辅助子类////////////////////////////////////////////////
	public class StateHelper implements javax.media.ControllerListener 
	{ 
	Player xplayer = null;
	boolean configured = false;
	boolean realized = false;
	boolean failed = false;
	boolean closed = false; 
	
	public StateHelper(Player p) 
	{ 
		xplayer = p; 
		p.addControllerListener(this); 
	} 

	public boolean configure(int timeOutMillis) 
	{ 
//	RealizeCompleteEvent发生了的话使ce事件与之比较，若相等，那么realized为true。 
	/*监听ConfigureCompleteEvent和ConfigureCompleteEvent事件的发生。 
	* 如ConfigureCompleteEvent事件发生，那么就会赋给configured为ture， 
	* 使得public boolean configure方法中的 
	* while (!configured && !failed){}这个循环退出。*/ 
	long startTime = System.currentTimeMillis(); 
	synchronized (this) 
	{ 
		if (xplayer instanceof Processor) 
			((Processor) xplayer).configure(); 
		else 
			return false; 
		while (!configured && !failed) 
		{ 
			try 
			{ 
				wait(timeOutMillis); 
			} catch (InterruptedException ie) 
			{ 
		} 
			if (System.currentTimeMillis() - startTime > timeOutMillis) 
				break; 
		} 
	} 
		return configured; 
	} 

	public boolean realize(int timeOutMillis) 
	{ 
		long startTime = System.currentTimeMillis(); 
		synchronized (this) 
		{ 
		xplayer.realize(); 
		while (!realized && !failed) 
		{ 
		try 
		{ 
		wait(timeOutMillis); 
		} catch (InterruptedException ie) 
		{ 
		} 
		if (System.currentTimeMillis() - startTime > timeOutMillis) 
		break; 
		} 
		} 
		return realized; 
		} 
	
		public synchronized void controllerUpdate(ControllerEvent ce) 
		{ 
		if (ce instanceof RealizeCompleteEvent) 
		{ 
		realized = true; 
		} else if (ce instanceof ConfigureCompleteEvent) 
		{ 
		configured = true; 
		} else 
		{ 
		return; 
		} 
		notifyAll(); 
		} 
	}//子类结束
	
	//保存图片的宽度和高度 
	private int imgWidth = 400;
	private int imgHeight = 300; 
////////////////////////图形面板子类///////////////////////////////////////////////////
	class ImagePanel extends Panel { 
		
		private static final long serialVersionUID = -8756264950067095559L;
		private Image ImagePanelimg = null; 
	
		public ImagePanel() { 
		setLayout(null); 
		setSize(imgWidth, imgHeight); 
		} 
	
		public void setImage(Image img) { 
		this.ImagePanelimg = img; 
		this.setVisible(true); 
		repaint(); 
		} 
	
		public void update(Graphics graphics) { 
		if (ImagePanelimg != null) { 
		graphics.drawImage(ImagePanelimg, 0, 0, this); 
		}  
		} 
	}
//////////////////////dataSink子类//////////////////////////////////////////////////	
	class dataSinkS implements DataSink{
		public void addDataSinkListener(DataSinkListener arg0) {
			// TODO Auto-generated method stub			
		}
		
		public void close() {
			try {
				super.clone();
			} catch (CloneNotSupportedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		public String getContentType() {
			// TODO Auto-generated method stub
			return null;
		}

		public MediaLocator getOutputLocator() {
			// TODO Auto-generated method stub
			return null;
		}

		public void open() throws IOException, SecurityException {
			// TODO Auto-generated method stub			
		}

		public void removeDataSinkListener(DataSinkListener arg0) {
			// TODO Auto-generated method stub			
		}

		public void setOutputLocator(MediaLocator arg0) {
			// TODO Auto-generated method stub			
		}

		public void start() throws IOException {
			// TODO Auto-generated method stub			
		}

		public void stop() throws IOException {
			// TODO Auto-generated method stub			
		}

		public void setSource(DataSource arg0) throws IOException, IncompatibleSourceException {
			// TODO Auto-generated method stub			
		}

		public Object getControl(String arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		public Object[] getControls() {
			// TODO Auto-generated method stub
			return null;
		}		
	}
	}

