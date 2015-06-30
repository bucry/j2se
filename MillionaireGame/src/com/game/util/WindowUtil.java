/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.game.util;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;

/**
 *
 * @author perfecking
 */
public class WindowUtil {
    public static void centre(Component component){
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x =(int)(dimension.getWidth()-component.getWidth())/2;
        int y =(int)(dimension.getHeight()-component.getHeight())/2;
        component.setLocation(x, y);
    }
}
