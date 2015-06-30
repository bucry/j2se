package net.sf.nwn.viewer;


import java.io.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.awt.*;
import javax.swing.event.*;
import javax.swing.*;
import net.sf.nwn.loader.*;


public class AnimPanel extends JPanel
    implements ListSelectionListener
{
    private JList list;
    private AnimationBehavior anim;
    private String lastAnim;
    private boolean animationLoop;
    private boolean inChange;

    public AnimPanel()
    {
        setLayout(new BorderLayout());
        list = new JList();
        list.addListSelectionListener(this);
        add(BorderLayout.CENTER, new JScrollPane(list));
        setSize(400,300);
        setVisible(true);
    }

    public void setAnimationBehavior(AnimationBehavior anAnim)
    {
        anim = anAnim;
        SwingUtilities.invokeLater(new Runnable()
            {
                public void run()
                {

                    final Object[] allNames;

                    if (anim == null)
                        allNames = new Object[0];
                    else
                        allNames = anim.getAllAnimationNames().toArray();

                    Arrays.sort(allNames);

                    list.setModel(new AbstractListModel()
                        {
                            public int getSize()
                            {
                                return allNames.length;
                            }

                            public Object getElementAt(int i)
                            {
                                return allNames[i];
                            }
                        }
                    );
                }
            }
        );
    }

    public void valueChanged(ListSelectionEvent arg0)
    {
        if (inChange)
            return;

        String s = (String) list.getSelectedValue();

        if (s == null || s == lastAnim)
            return;

        lastAnim = s;
        anim.playAnimation(s, animationLoop);
    }

    public boolean getAnimationLoop()
    {
        return animationLoop;
    }

    public void setAnimationLoop(boolean animationLoop)
    {
        this.animationLoop = animationLoop;
    }

    public void showDefaultAnimations()
    {
        if (anim == null)
            return;
        inChange = true;
        list.clearSelection();
        ListModel lm = list.getModel();
        java.util.List al = anim.getDefaultAnimations();

        for (int i = 0; i < lm.getSize(); i++)
        {
            if (al.contains(lm.getElementAt(i)))
            {
                list.addSelectionInterval(i, i);
            }
        }
        inChange = false;
    }

    public void setDefaultAnimations()
    {
        if (anim == null)
            return;
        anim.setDefaultAnimations(Arrays.asList(list.getSelectedValues()));
    }

}
