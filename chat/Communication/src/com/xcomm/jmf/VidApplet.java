package com.xcomm.jmf;

import java.awt.Button;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.media.Format;
import javax.media.Manager;
import javax.media.Processor;
import javax.media.ProcessorModel;
import javax.media.control.MonitorControl;
import javax.media.format.AudioFormat;
import javax.media.format.VideoFormat;
import javax.media.protocol.DataSource;
import javax.media.protocol.FileTypeDescriptor;
import javax.swing.JApplet;
/**
*
* @author Administrator
*/
public class VidApplet extends JApplet implements ActionListener {
	private static final long serialVersionUID = -3145913774795403376L;
	public void actionPerformed(ActionEvent e) {
     
    }
    private Panel panel = new Panel();
    private Button button = new Button();
    private Processor processor = null;
    private Component component = null;
    private Component monitor = null;
    private DataSource ds = null;
    private MonitorControl mc = null;
    @SuppressWarnings("unused")
	private void startMonitoring() {
        if (processor != null) {
            processor.stop();
            processor.close();
        }
        if (monitor != null) {
            panel.remove(monitor);
            monitor = null;
        }
        //音频格式
        //AudioFormat af = new AudioFormat(AudioFormat.LINEAR);
        AudioFormat af = new AudioFormat(AudioFormat.LINEAR, 44100, 16, 2);
        //视频大小
        Dimension size = new Dimension(160, 120);
        //视频格式
        VideoFormat vf = new VideoFormat("RGB", size, Format.NOT_SPECIFIED, null, 15);
        ds = CaptureUtil.getCaptureDS(af, vf);
        //视频格式
        FileTypeDescriptor ftd = new FileTypeDescriptor(FileTypeDescriptor.QUICKTIME);
        Format[] formats = null;
        if (af != null && vf != null) {
            formats = new Format[]{new AudioFormat(null), new VideoFormat(null)};
        }
        if (af == null) {
            formats = new Format[]{new VideoFormat(null)};
        }
        //处理器
        ProcessorModel pm = new ProcessorModel(ds, formats, ftd);
        try {
            processor = Manager.createRealizedProcessor(pm);
        } catch (Exception e) {
            ds.disconnect();            return;
        }
        //监视器控制方式
        mc = (MonitorControl) ds.getControl("javax.media.control.MonitorControl");
        if (mc != null) {
            component = mc.getControlComponent();
            panel.add(component);
            mc.setEnabled(true);
        }
        panel.add(button);
    }
    public void destroy() {
        processor = null;
        monitor = null;
        if (component != null) {
            panel.remove(component);
            component = null;
        }
        ds = null;
        super.destroy();
    }
    public void init() {
        super.init();
        button.setLabel("拍照");
        button.addActionListener(this);
        //panel.add(button);
        panel.setLayout(new GridLayout());
        this.add(panel);
    }
    public void start() {
        super.start();
        startMonitoring();
    }
    @Override
    public void stop() {
        super.stop();
        if (processor != null) {
            processor.stop();
            processor.close();
        }
        if (monitor != null) {
            panel.remove(monitor);
            monitor = null;
        }
        ds = null;
    }
    public static void main(String[] args){
    VidApplet applet = new VidApplet();
    applet.init();
    applet.start();
    applet.stop();
    applet.destroy();
    }
}
