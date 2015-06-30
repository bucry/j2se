package net.sf.nwn.viewer;


import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileFilter;
import java.net.MalformedURLException;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.jvnet.substance.skin.SubstanceGreenMagicLookAndFeel;



/**
 * 
 * Demo application
 * 
 * @author Artur
 *
 */
public class Login
{
	
	static {
		JFrame.setDefaultLookAndFeelDecorated(true);
		JDialog.setDefaultLookAndFeelDecorated(true);
		try {
			UIManager.setLookAndFeel(new SubstanceGreenMagicLookAndFeel());
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Store for arguments
	 */
	public static String[] arguments = null;
    
	/**
	 * main method
	 * @param args
	 * @throws Exception
	 */
     public static void main(final String[] args)
        throws Exception{    	 
    	 
    	 // create a runnable launcher 
    	 LaunchMe launch = new LaunchMe(args);
    	 // 我喜欢Java3D的应用程序使用自己的主线程
    	 final Thread thread = new Thread(launch,"NWM Demo - LaunchMe thread");
         

         SwingUtilities.invokeLater(new Runnable(){
        	 public void run(){
        		 thread.start();
        	 }
         });
         
     }

     /**
      * This is the static launcher for this application
      * @param args
      */
    public static void launch(String[] args) 
    {
        File modelFile = null;
        File dir = new File(".");
        File[] files = new File[1];

        if (args.length > 0)
        {
            modelFile = new File(args[0]);
            if (!modelFile.exists()){
                System.err.println("" + modelFile + " not found");
                System.exit(1);
            }
            
            if (!modelFile.isDirectory()) {
                dir = modelFile;                              
                files[0] = dir;
            }
            else {
                dir = modelFile.getParentFile();
                files = dir.listFiles(new MDLFileFilter());
                modelFile = null;
            }
        }else{
        	// 任何文件或文件夹作为论据 
        	// 检查当前的这个文件夹
        	files = dir.listFiles(new MDLFileFilter());
        }
      
        String[] names = new String[files.length];

        for (int i = 0; i < files.length; i++)
        {
            names[i] = files[i].getName().substring(0, files[i].getName().indexOf(".mdl"));
            System.out.println(names[i]);
        }

        Display dis = new Display();
        AnimPanel anim = new AnimPanel();
        ControlPanel control = new ControlPanel(dis, anim);
        
        FilePanel fp = null;
		try {
			fp = new FilePanel(dis, anim, control, dir.toURL(), names);
		} catch (MalformedURLException e1) {
			// TODO 自动生成catch块
			e1.printStackTrace();
		}
		
		// 展示 is a Canvas3D instance
		// 设置为 100FPS,
		dis.getView().setMinimumFrameCycleTime(10L);

        final JFrame jf = new JFrame("角色显示");
        final JDialog fd = new JDialog(jf, "模型文件列表");
        final JDialog ad = new JDialog(jf, "骨骼动画列表");
        WindowListener windowListener = new WindowAdapter() {
           public void windowClosing(WindowEvent e){
               
               fd.setVisible(false);
               ad.setVisible(false);
               jf.setVisible(false);              
               
               fd.dispose();
               ad.dispose();
               jf.dispose();
               
               System.exit(0);
           }
        };
        
        fd.getContentPane().add(fp);
        fd.setSize(200, 300);
        fd.addWindowListener(windowListener);        
        fd.setVisible(true);

        jf.getContentPane().add(dis);
        jf.setSize(600, 600);
        jf.setLocation(fd.getWidth(), 0);
        jf.addWindowListener(windowListener);  
        jf.setVisible(true);

        ad.getContentPane().add(anim);
        ad.setSize(200, 300);
        ad.setLocation(0,fd.getHeight());
        ad.addWindowListener(windowListener);  
        ad.setVisible(true);

        if (modelFile != null)
        {
            if (modelFile.getName().endsWith(".gz"))
                modelFile = new File(modelFile.getPath().substring(0, modelFile.getPath().length() - 3));
            try {
				fp.setCurrentModel(modelFile.toURL());
			} catch (MalformedURLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        }

        /*
        int i =0;
        while (i++ < 10)
        {
            System.in.read();
            System.gc();
        }
        */

    }
}

/**
 * new filefinter
 * @author Alessandro
 *
 */
class MDLFileFilter implements FileFilter
{
    public boolean accept(File f)
    {
        return f.getName().endsWith(".mdl") || f.getName().endsWith(".mdl.gz");
    }
}

/**
 * Helper class to launch this application
 * @author Alessandro
 *
 */
class LaunchMe implements Runnable{
	/** arguments for launcher */
	private String[] arguments = null;
	
	/**
	 * Constructor
	 * @param args arguments for launch
	 */
	public LaunchMe(String[] args){
		arguments = args;
	}
	
	public void run(){
		try{                	 
            Login.launch(this.arguments);
            }catch(Exception e){
                e.printStackTrace();
            }
	}		
}
