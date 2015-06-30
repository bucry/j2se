package com.bfchuan.mini.util;

import java.awt.Image;
import java.net.URL;

import javax.swing.ImageIcon;

/**
 * 图像解析类
 * 作者:Loenidas
 * 时间:2012-5-10
 * piaobomengxiang@163.com
 * 版本:v1.0
 *
 */
public class ImageTool {

	private static ImageTool imgTool = new ImageTool();

    private ImageTool() {
    }
    
    public ImageIcon getIcon(String path) {
    	ImageIcon icon = null;
        try {
            URL url = getClass().getResource("/org/jys/mini/resource/" + path);
            if (url == null) {
                return icon;
            }
            icon = new ImageIcon(url);
        } catch (Exception e) {
            System.out.println("parse Icon error");
        }
        return icon;
    }

    public Image getImage(String path) {
    	ImageIcon icon = this.getIcon(path);
    	if (icon == null) {
    		return null;
    	}
    	return icon.getImage();
    }
    
    public static ImageTool getInstance() {
    	return imgTool;
    }
}
