package com.bfchuan.entrance;


import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.jvnet.substance.skin.SubstanceGreenMagicLookAndFeel;

import com.bfchuan.controller.CentralController;
import com.bfchuan.controller.MainController;
import com.bfchuan.controller.MediaController;
import com.bfchuan.util.Global;
import com.bfchuan.view.MainFrame;

/**
 * myDownloader入口类
 * @author Administrator
 *
 */
public class MyDownloader {
	
	static {
		JFrame.setDefaultLookAndFeelDecorated(true);
		JDialog.setDefaultLookAndFeelDecorated(true);
		try {
			UIManager.setLookAndFeel(new SubstanceGreenMagicLookAndFeel());
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
	}

	 boolean packFrame = false;
//	 Logger logger;
	
	 /**
	  * 构造器，构造一个myDownloader下载器
	  */
	public MyDownloader(){

		/*创建myDownloader的其他组件是否完整，如不完整则创建*/
		this.createEnvironment();
		
	//	logger = Logger.getLogger(MyDownloader.class);
		
		/*构造主窗口 */ 
		MainFrame frame = MainFrame.getInstance();
		
		/*构造下载主控制器*/
		MainController mainController = new MainController();
		
		/*构造中央控制器*/
		CentralController centralController = new MediaController(mainController,frame);
		
		/*在主窗口中添加中央控制器*/
		frame.addCentralContoller(centralController);
		
		/*初始化主窗口*/
		frame.init();
		
		/*控制器初始化*/
		centralController.startInit();
		
        // Validate frames that have preset sizes
        // Pack frames that have useful preferred size info, e.g. from their layout
        if (packFrame) {
            frame.pack();
        } else {
            frame.validate();
        }

        // Center the window
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = frame.getSize();
        if (frameSize.height > screenSize.height) {
            frameSize.height = screenSize.height;
        }
        if (frameSize.width > screenSize.width) {
            frameSize.width = screenSize.width;
        }
        frame.setLocation((screenSize.width - frameSize.width) / 2,
                          (screenSize.height - frameSize.height) / 2);
        frame.setVisible(true);
        
      //  logger.info(" myDownloader启动！");
	}
	
	/**
	 * 搭建myDownloader的其他运行环境,如，建立主文件夹
	 */
	public void createEnvironment(){
		
		/*myDownloader主目录*/
		File myDownloaderHome = new File(Global.MYDOWNLOADER_PATH);
		if(!myDownloaderHome.exists()){
			myDownloaderHome.mkdir();
		}
		
		/*下载中文件夹*/
		File running = new File(Global.RUNNING_TASK_PATH);
		if(!running.exists()){
			running.mkdir();
		}
		
		/*下载完成文件夹*/
		File complete = new File(Global.COMPLETE_TASK_PATH);
		if(!complete.exists()){
			complete.mkdir();
		}
		
		/*垃圾箱文件夹*/
		File garbage = new File(Global.GARBAGE_TASK_PATH);
		if(!garbage.exists()){
			garbage.mkdir();
		}
		
		/*日志文件路径*/
		File log = new File(Global.LOG_PATH);
		if(!log.exists()){
			log.mkdir();
		}
	}
	
	/**
	 * main函数入口
	 * @param args
	 */
	public static void main(String[] args) {
		 SwingUtilities.invokeLater(new Runnable() {
	            public void run() {
	               /* try {
	                    UIManager.setLookAndFeel(UIManager.
	                                             getSystemLookAndFeelClassName());
	                } catch (Exception exception) {
	                    exception.printStackTrace();
	                }*/

	               new MyDownloader();
	            }
	        });
	}

}
