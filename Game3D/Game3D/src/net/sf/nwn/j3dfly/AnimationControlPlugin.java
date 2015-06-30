/*
 *                         Sun Public License Notice
 *
 *  The contents of this file are subject to the Sun Public License Version
 *  1.0 (the "License"). You may not use this file except in compliance with
 *  the License. A copy of the License is available at http://www.sun.com/
 *  
 *  The Original Code is the Java 3D(tm) Scene Graph Editor.
 *  The Initial Developer of the Original Code is Paul Byrne.
 *  Portions created by Paul Byrne are Copyright (C) 2002.
 *  All Rights Reserved.
 *  
 *  Contributor(s): Paul Byrne.
 *  
 **/
package net.sf.nwn.j3dfly;

import java.awt.event.ActionListener;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JSeparator;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.ButtonGroup;


import org.jdesktop.j3dfly.event.*;
import org.jdesktop.j3dfly.plugins.*;
import org.jdesktop.j3dfly.*;

//import com.sun.j3d.demos.j3dfly.event.FileLoadEvent;
//import com.sun.j3d.demos.j3dfly.plugins.PluginPreference;
//import com.sun.j3d.demos.j3dfly.plugins.J3dFlyPlugin;
//import com.sun.j3d.demos.j3dfly.J3dFlyContext;

import com.sun.j3d.demos.utils.scenegraph.traverser.TreeScan;
import com.sun.j3d.demos.utils.scenegraph.traverser.NodeChangeProcessor;

import net.sf.nwn.loader.AnimationBehavior;

import java.util.Iterator;

/**
 *
 * @author  paulby
 */
public class AnimationControlPlugin extends J3dFlyPlugin
                                    implements FlyEventListener {
    
    /** Creates a new instance of AnimationControlPlugin */
    public AnimationControlPlugin() {
        super();
    }
    
    /**
     * Install the plugin into j3dfly with the supplied preferences
     *
     * Access this functionality through the preference object for this 
     * plugin
     */
    public void installPlugin( PluginPreference pluginPref, J3dFlyContext j3dflyContext ) {
        super.installPlugin( pluginPref, j3dflyContext );
        
        menu = new javax.swing.JMenu( "NWN Animation" );
        menu.setEnabled( false );
        
        if (pluginPref.isInstallInMenu())
            getMenu("Plugins").add( menu );
        
        j3dflyContext.getEventProcessor().addListener( this, FileLoadEvent.class );
    }
    
    public void uninstallPlugin() {
        super.uninstallPlugin();
        j3dflyContext.getEventProcessor().removeListener( this, FileLoadEvent.class );
        
        if (pluginPref.isInstallInMenu())
            getMenu("Plugins").remove( menu );
    }
    
    /**
     * Returns the control panel for this plugin, or null if there
     * is no control panel
     */
    public javax.swing.JPanel getControlPanel() {
        return null;
    }
    
    /**
     * Returns the class of the plugin preference used for the Tool wide
     * preferences.
     *
     * Plugins that require more preference information should provide a
     * subclass of PluginPrefernece that contains all the extra preference
     * data. This class must be Serializable.
     */
    public Class getPluginPreferenceClass() {
        return AnimationControlPluginPreference.class;
    }
    
    /**
     * Called by EventProcessor when an event occurs for which this
     * listener has registered interest
     */
    public void processFlyEvent(FlyEvent evt) {
        if ( !(evt instanceof FileLoadEvent))
            return;
        
        final javax.swing.JMenu submenu = new javax.swing.JMenu( ((FileLoadEvent)evt).getFile().getName() );
        menu.setEnabled(true);
        
        
        javax.media.j3d.BranchGroup roots[] = ((FileLoadEvent)evt).getBranchGroups();
        for(int i=0; i<roots.length; i++)
            TreeScan.findNode( roots[i],
                               net.sf.nwn.loader.AnimationBehavior.class,
                               new NodeChangeProcessor() {
                                   public void changeNode( final javax.media.j3d.Node node ) {
                                       System.out.println(node);
                                       final JCheckBoxMenuItem cbMi = new JCheckBoxMenuItem("Continous");
                                       submenu.add( cbMi );
                                       submenu.add( new JSeparator() );
                                       
                                        ActionListener actionListener = new ActionListener() {
                                            public void actionPerformed( java.awt.event.ActionEvent evt ) {
                                                System.out.println("Playing animation "+((JRadioButtonMenuItem)evt.getSource()).getText());
                                                ((AnimationBehavior)node).playAnimation( ((JRadioButtonMenuItem)evt.getSource()).getText(),
                                                                           cbMi.isSelected() );
                                            }
                                        };
                                        
                                       ButtonGroup buttonGroup = new ButtonGroup();
                                       Iterator it = ((AnimationBehavior)node).getAllAnimationNames().iterator();
                                       while(it.hasNext()) {
                                           JRadioButtonMenuItem mi = new JRadioButtonMenuItem((String)it.next());
                                           buttonGroup.add( mi );
                                           mi.addActionListener( actionListener );
                                           submenu.add( mi );
                                       }
                                       
                                       ((AnimationBehavior)node).setDefaultAnimations( new java.util.ArrayList() );
                                     
                                   }        // of changeNode method
                               },
                               false, 
                               true );
        menu.add( submenu );
                        
    }
    
    public static class AnimationControlPluginPreference extends PluginPreference {
        public AnimationControlPluginPreference() {
            super();
        }
        
        public AnimationControlPluginPreference(boolean enabled, boolean installed) {
            super( enabled, installed );
        }
        
        public J3dFlyPlugin instantiatePlugin() {
            return new AnimationControlPlugin();
        }
        
        /**
         * Return a description of this plugin
         */
        public String getDescription() {
            return "Controls for Never Winter Nights Animations";
        }
        
        /**
         * Return the name of the Plugin for this prefernece.
         * This is the name that will appear in the list of plugins
         */
        public String getName() {
            return "NWN Animation Controls";
        }
        
    }
}
