package net.sf.nwn.viewer;


import java.awt.BorderLayout;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.*;

import javax.media.j3d.*;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.vecmath.Vector3d;

import net.sf.nwn.loader.AnimationBehavior;
import net.sf.nwn.loader.Model;
import net.sf.nwn.loader.NWNLoader;
import net.sf.nwn.loader.Walkmesh;

import com.sun.j3d.loaders.Scene;
import com.sun.j3d.demos.utils.scenegraph.traverser.*;


public class FilePanel extends JPanel
    implements ListSelectionListener
{
    private Display display;
    private AnimPanel anim;
    private ControlPanel control;
    private URL base;
    private JList list;
    private Object lastSelected;
    private Model currentModel;

    public FilePanel(Display aDisplay, AnimPanel anAnim, ControlPanel aControl, URL aBase, String[] names)
    {
        display = aDisplay;
        anim = anAnim;
        control = aControl;
        setLayout(new BorderLayout());
        base = aBase;

        list = new JList(names);
        list.addListSelectionListener(this);
        add(BorderLayout.CENTER, new JScrollPane(list));
    }

    private NWNLoader nwn = new NWNLoader();
    {
        nwn.enableModelCache(true);
    }

    public void setCurrentModel(final URL modelFile)
    {
        new Thread("Model Loader")
        {
            public void run()
            {
                try
                {
                    control.setAnimationBehavior(null, null);
                    anim.setAnimationBehavior(null);
                    display.getModelGroup().removeAllChildren();
                    currentModel = null;
                    display.addLoadingMessage();
                    setPriority(5);
                    sleep(100);
                    long start = System.currentTimeMillis();

                    Scene s = nwn.load(modelFile);
                    javax.media.j3d.BranchGroup bg = s.getSceneGroup();

                    setCapabilities(bg);

                    bg.compile();
                    AnimationBehavior ab = (AnimationBehavior) s.getNamedObjects().get("AnimationBehavior");
                    Walkmesh walkmesh = (Walkmesh) s.getNamedObjects().get("%WALKMESH%");

                    anim.setAnimationBehavior(ab);
                 //   control.setAnimationBehavior(ab, walkmesh);
                    display.removeLoadingMessage();
                    display.getModelGroup().addChild(bg);

                    System.err.println("Loaded in " + (System.currentTimeMillis() - start) + "ms");

                } catch (Exception exc)
                {
                    exc.printStackTrace();
                    JOptionPane.showMessageDialog(FilePanel.this, exc, "Error during load " + modelFile, JOptionPane.ERROR_MESSAGE);
                    display.removeLoadingMessage();
                }
            }
        }.start();
    }

    public void valueChanged(ListSelectionEvent arg0)
    {
        Object selected = list.getSelectedValue();

        //判断 Selected是否等于null 或者等于自己
        if (selected == null || selected == lastSelected)
            return;

        //将Selected的值 临时储存在lastSelected里
        lastSelected = selected;

        try
        {
            URL f = new URL(base, selected + ".mdl");

            setCurrentModel(f);
        } catch (MalformedURLException exc)
        {
            exc.printStackTrace();
            JOptionPane.showMessageDialog(FilePanel.this, exc, "Error during load " + selected, JOptionPane.ERROR_MESSAGE);
            display.removeLoadingMessage();
        }
    }

    private static ProcessNodeInterface capabilityProcessor = new ProcessNodeInterface()
        {
            public void processNode(Node node)
            {
                if (node instanceof Group)
                {
                    node.setCapability(Group.ALLOW_CHILDREN_READ);
                    if (node instanceof TransformGroup)
                    {
                        node.setCapability(TransformGroup.ALLOW_LOCAL_TO_VWORLD_READ);
                    }
                }
                else if (node instanceof Shape3D)
                {
                    Shape3D s = (Shape3D) node;

                    s.setCapability(Shape3D.ALLOW_APPEARANCE_READ);
                    Appearance app = s.getAppearance();
                    app.setCapability(Appearance.ALLOW_TEXTURE_UNIT_STATE_READ);
                    app.clearCapabilityIsFrequent(Appearance.ALLOW_TEXTURE_UNIT_STATE_READ);
                    app.setCapability(Appearance.ALLOW_POLYGON_ATTRIBUTES_WRITE);
                    app.clearCapabilityIsFrequent(Appearance.ALLOW_POLYGON_ATTRIBUTES_WRITE);
                    app.setCapability(Appearance.ALLOW_RENDERING_ATTRIBUTES_WRITE);
                    app.clearCapabilityIsFrequent(Appearance.ALLOW_RENDERING_ATTRIBUTES_WRITE);

                    int tuCount = s.getAppearance().getTextureUnitCount();
                    for (int i = 0; i < tuCount; i++)
                    {
                    	TextureUnitState tu = s.getAppearance().getTextureUnitState(i);
                        if (tu != null)
                        {
                            tu.setCapability(TextureUnitState.ALLOW_STATE_READ);
                            tu.getTexture().setCapability(Texture.ALLOW_ENABLE_WRITE);
                            tu.getTextureAttributes().setCapability(TextureAttributes.ALLOW_MODE_WRITE);
                            tu.getTexture().clearCapabilityIsFrequent(Texture.ALLOW_ENABLE_WRITE);
                            tu.getTextureAttributes().clearCapabilityIsFrequent(TextureAttributes.ALLOW_MODE_WRITE);
                        }
                    }
                }

            }
        };

    public void setCapabilities(BranchGroup bg)
    {
        TreeScan.findNode(bg, new Class[]
            {Group.class, Shape3D.class}, capabilityProcessor,
            false, false);
    }

}
