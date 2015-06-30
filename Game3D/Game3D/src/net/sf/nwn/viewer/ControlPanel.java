package net.sf.nwn.viewer;


import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.beans.EventHandler;
import java.util.*;

import javax.media.j3d.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.vecmath.*;
import net.sf.nwn.loader.AnimationBehavior;
import net.sf.nwn.loader.ParticleCollection;
import net.sf.nwn.loader.Walkmesh;

import com.sun.j3d.demos.utils.scenegraph.traverser.*;


public class ControlPanel extends JPanel
{

    private AnimationBehavior anim;
    private AnimPanel animPanel;
    private Display display;

    private Walkmesh walkmesh;

    private JTabbedPane tabs = new JTabbedPane(SwingConstants.BOTTOM);
    private float currentExplosion = 1.0f;

    JRadioButton polygonModes[] = new JRadioButton[3];
    JRadioButton cullFaces[] = new JRadioButton[3];

    JCheckBox skeleton = new JCheckBox("Show skeleton", false);
    JCheckBox skeletonLabelsCB = new JCheckBox("Show labels", false);
    JCheckBox geometry = new JCheckBox("Show geometry", true);
    JCheckBox reflective = new JCheckBox("Enabled", true);
    JCheckBox reflectionBlend = new JCheckBox("Combine", true);
    JCheckBox maintex = new JCheckBox("Enabled", true);
    JCheckBox maintexTransp = new JCheckBox("Light blend", true);

    JCheckBox walkmeshCB = new JCheckBox("Walkmesh", false);

    JSlider animSlider = new JSlider();

    private static final int TEX_METAL = 1;
    private static final int TEX_MAIN = 0;

    public ControlPanel(Display disp, AnimPanel anAnimPanel)
    {
        animPanel = anAnimPanel;
        display = disp;
        setLayout(new BorderLayout());
        add(BorderLayout.CENTER, tabs);

        JPanel geometryPanel = new JPanel();

        JPanel explodePanel = new JPanel();

        explodePanel.setLayout(new BoxLayout(explodePanel, BoxLayout.X_AXIS));
        explodePanel.setBorder(new TitledBorder("Explode parts"));
        final JTextField explodeFactor = new JTextField(5);

        explodeFactor.setText("1.0");
        explodePanel.add(explodeFactor);
        JButton explodeButton = new JButton("Explode");

        explodeButton.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent arg0)
                {
                    try
                    {
                        float e = Float.parseFloat(explodeFactor.getText());

                        if (anim == null)
                            return;

                        explodeTransforms(anim.getNamedTransforms().values(), e / currentExplosion);
                        currentExplosion = e;
                    } catch (NumberFormatException exc)
                    {
                        JOptionPane.showMessageDialog(ControlPanel.this, exc, "Wrong scale", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        );

        explodePanel.add(explodeButton);
        geometryPanel.add(explodePanel);

        skeleton.addChangeListener((ChangeListener)
            EventHandler.create(ChangeListener.class, this, "showSkeleton", "source.selected"));
        geometryPanel.add(skeleton);

        skeletonLabelsCB.addChangeListener((ChangeListener)
            EventHandler.create(ChangeListener.class, this, "showSkeletonLabels", "source.selected"));
        skeletonLabelsCB.setEnabled(false);
        geometryPanel.add(skeletonLabelsCB);

        geometry.addChangeListener((ChangeListener)
            EventHandler.create(ChangeListener.class, this, "showGeometry", "source.selected"));
        geometryPanel.add(geometry);

        final JCheckBox grid = new JCheckBox("Show grid", false);

        grid.addChangeListener((ChangeListener)
            EventHandler.create(ChangeListener.class, this, "showGrid", "source.selected"));
        geometryPanel.add(grid);

        walkmeshCB.addChangeListener((ChangeListener)
            EventHandler.create(ChangeListener.class, this, "showWalkmesh", "source.selected"));
        geometryPanel.add(walkmeshCB);

        JPanel appearancePanel = new JPanel();

        appearancePanel.setLayout(new FlowLayout());

        JPanel reflectionPanel = new JPanel();

        reflectionPanel.setBorder(new TitledBorder("Reflection map"));
        reflectionPanel.setLayout(new GridLayout(2, 1));

        reflective.addChangeListener(new ChangeListener()
            {
                boolean pstate = true;
                public void stateChanged(ChangeEvent arg0)
                {
                    if (reflective.isSelected() == pstate)
                        return;
                    pstate = reflective.isSelected();
                    setTextureUnitState(TEX_METAL, reflective.isSelected());
                    reflectionBlend.setEnabled(reflective.isSelected());
                }
            }
        );
        reflectionBlend.addChangeListener(new ChangeListener()
            {
                boolean pstate = true;
                public void stateChanged(ChangeEvent arg0)
                {
                    if (reflectionBlend.isSelected() == pstate)
                        return;
                    pstate = reflectionBlend.isSelected();
                    if (reflectionBlend.isSelected())
                    {
                        setTextureUnitMode(TEX_METAL, TextureAttributes.COMBINE);
                    }
                    else
                    {
                        setTextureUnitMode(TEX_METAL, TextureAttributes.REPLACE);
                    }
                }
            }
        );

        reflectionPanel.add(reflective);
        reflectionPanel.add(reflectionBlend);

        appearancePanel.add(reflectionPanel);

        JPanel mainPanel = new JPanel();

        mainPanel.setLayout(new GridLayout(2, 1));
        mainPanel.setBorder(new TitledBorder("Main texture"));

        maintex.addChangeListener(new ChangeListener()
            {
                boolean pstate = true;
                public void stateChanged(ChangeEvent arg0)
                {
                    if (maintex.isSelected() == pstate)
                        return;
                    pstate = maintex.isSelected();
                    setTextureUnitState(TEX_MAIN, maintex.isSelected());
                    maintexTransp.setEnabled(maintex.isSelected());
                }
            }
        );
        maintexTransp.addChangeListener(new ChangeListener()
            {
                boolean pstate = true;
                public void stateChanged(ChangeEvent arg0)
                {

                    if (maintexTransp.isSelected() == pstate)
                        return;
                    pstate = maintexTransp.isSelected();
                    if (maintexTransp.isSelected())
                    {
                        setTextureUnitMode(TEX_MAIN, TextureAttributes.MODULATE);
                    }
                    else
                    {
                        setTextureUnitMode(TEX_MAIN, TextureAttributes.REPLACE);
                    }
                }
            }
        );

        mainPanel.add(maintex);
        mainPanel.add(maintexTransp);

        appearancePanel.add(mainPanel);

        ChangeListener updatePoly = (ChangeListener) EventHandler.create(ChangeListener.class, this, "updatePolygonMode");

        JRadioButton jrb;

        JPanel wirePanel = new JPanel();

        wirePanel.setLayout(new GridLayout(3, 1));
        wirePanel.setBorder(new TitledBorder("Polygon fill"));
        ButtonGroup polyGroup = new ButtonGroup();

        jrb = new JRadioButton("Fill", true);
        jrb.addChangeListener(updatePoly);
        polyGroup.add(jrb);
        polygonModes[0] = jrb;
        wirePanel.add(jrb);
        jrb = new JRadioButton("Line");
        jrb.addChangeListener(updatePoly);
        polyGroup.add(jrb);
        polygonModes[1] = jrb;
        wirePanel.add(jrb);
        jrb = new JRadioButton("Point");
        jrb.addChangeListener(updatePoly);
        polyGroup.add(jrb);
        polygonModes[2] = jrb;
        wirePanel.add(jrb);
        appearancePanel.add(wirePanel);

        JPanel cullPanel = new JPanel();

        cullPanel.setLayout(new GridLayout(3, 1));
        cullPanel.setBorder(new TitledBorder("Cull faces"));
        ButtonGroup cullGroup = new ButtonGroup();

        jrb = new JRadioButton("Cull back", true);
        jrb.addChangeListener(updatePoly);
        cullGroup.add(jrb);
        cullFaces[0] = jrb;
        cullPanel.add(jrb);
        jrb = new JRadioButton("Cull front");
        jrb.addChangeListener(updatePoly);
        cullGroup.add(jrb);
        cullFaces[1] = jrb;
        cullPanel.add(jrb);
        jrb = new JRadioButton("Cull none");
        jrb.addChangeListener(updatePoly);
        cullGroup.add(jrb);
        cullFaces[2] = jrb;
        cullPanel.add(jrb);

        appearancePanel.add(cullPanel);

        JPanel animationControl = new JPanel();

        animationControl.setLayout(new FlowLayout());

        JPanel timescalePanel = new JPanel();

        timescalePanel.setLayout(new BoxLayout(timescalePanel, BoxLayout.X_AXIS));
        timescalePanel.setBorder(new TitledBorder("Time scale"));
        final JTextField timescaleFactor = new JTextField(5);

        timescaleFactor.setText("1.0");
        timescalePanel.add(timescaleFactor);
        JButton timescaleButton = new JButton("Set Scale");

        timescaleButton.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent arg0)
                {
                    try
                    {
                        float e = Float.parseFloat(timescaleFactor.getText());

                        if (anim == null)
                            return;
                        anim.setAnimationTimeScale(e);
                    } catch (NumberFormatException exc)
                    {
                        JOptionPane.showMessageDialog(ControlPanel.this, exc, "Wrong scale", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        );
        timescalePanel.add(timescaleButton);
        animationControl.add(timescalePanel);

        final JCheckBox animationLoop = new JCheckBox("Loop animation");

        animationLoop.addChangeListener((ChangeListener)
            EventHandler.create(ChangeListener.class, animPanel, "animationLoop", "source.selected"));
        animationControl.add(animationLoop);

        JPanel defaultAnims = new JPanel();

        defaultAnims.setBorder(new TitledBorder("Default animations"));
        defaultAnims.setLayout(new GridLayout(1, 2));
        JButton getAnims = new JButton("Display");
        JButton setAnims = new JButton("Set");

        getAnims.addActionListener((ActionListener)
            EventHandler.create(ActionListener.class, animPanel, "showDefaultAnimations"));
        setAnims.addActionListener((ActionListener)
            EventHandler.create(ActionListener.class, animPanel, "setDefaultAnimations"));

        defaultAnims.add(getAnims);
        defaultAnims.add(setAnims);
        animationControl.add(defaultAnims);

        animSlider.setMinimum(0);
        animSlider.setMaximum(512);
        animSlider.setBorder(new TitledBorder("Animation Slider"));

        animSlider.addChangeListener((ChangeListener)
            EventHandler.create(ChangeListener.class, this, "animSliderMoved"));
        animationControl.add(animSlider);

        tabs.addTab("Geometry", geometryPanel);
        tabs.addTab("Appearance", appearancePanel);
        tabs.addTab("Animation", animationControl);
    }

    public void setAnimationBehavior(AnimationBehavior anAnim, Walkmesh walk)
    {
        anim = anAnim;
        currentExplosion = 1.0f;

        skeleton.setSelected(false);
        geometry.setSelected(true);
        reflective.setSelected(true);
        reflectionBlend.setSelected(true);
        maintex.setSelected(true);
        maintexTransp.setSelected(true);
        polygonModes[0].setSelected(true);
        cullFaces[0].setSelected(true);
        walkmesh = walk;
        walkmeshCB.setSelected(false);
        walkmeshCB.setEnabled(walkmesh != null);
    }

    public void explodeTransforms(Collection transforms, float scale)
    {
        Vector3f v = new Vector3f();
        Transform3D t3 = new Transform3D();

        Iterator it = transforms.iterator();

        while (it.hasNext())
        {
            Object obj = it.next();

            if (obj instanceof TransformGroup)
            {
                TransformGroup tg = (TransformGroup) obj;

                tg.getTransform(t3);
                t3.get(v);
                v.scale(scale);
                t3.setTranslation(v);
                tg.setTransform(t3);
            }
        }
    }

    public void setTextureUnitState(final int unit, final boolean enabled)
    {
        treeScan(new AppearanceChangeProcessor()
            {
                public void changeAppearance(javax.media.j3d.Shape3D shape,
                    javax.media.j3d.Appearance app)
                {
                    if (shape instanceof ParticleCollection || shape instanceof Walkmesh)
                        return;

                    if (unit == TEX_METAL && app.getTextureUnitCount() == 1)
                        return;

                    TextureUnitState tus = app.getTextureUnitState(unit);

                    if (tus != null)
                    {
                        tus.getTexture().setEnable(enabled);
                    }

                }
            }, display.getModelGroup());
    }

    public void setTextureUnitMode(final int unit, final int mode)
    {
        treeScan(new AppearanceChangeProcessor()
            {
                public void changeAppearance(javax.media.j3d.Shape3D shape,
                    javax.media.j3d.Appearance app)
                {
                    if (shape instanceof ParticleCollection || shape instanceof Walkmesh)
                        return;

                    if (unit == TEX_METAL && app.getTextureUnitCount() == 1)
                        return;

                    TextureUnitState tus = app.getTextureUnitState(unit);

                    if (tus != null)
                    {
                        tus.getTextureAttributes().setTextureMode(mode);
                    }
                }
            }, display.getModelGroup());
    }

    int lastPoly = PolygonAttributes.POLYGON_FILL;
    int lastFace = PolygonAttributes.CULL_BACK;

    public void updatePolygonMode()
    {
        int poly = PolygonAttributes.POLYGON_FILL;
        int face = PolygonAttributes.CULL_BACK;

        if (polygonModes[1].isSelected())
        {
            poly = PolygonAttributes.POLYGON_LINE;
        }
        else if (polygonModes[2].isSelected())
        {
            poly = PolygonAttributes.POLYGON_POINT;
        }

        if (cullFaces[1].isSelected())
        {
            face = PolygonAttributes.CULL_FRONT;
        }
        else if (cullFaces[2].isSelected())
        {
            face = PolygonAttributes.CULL_NONE;
        }

        if (lastPoly == poly && lastFace == face)
        {
            return;
        }

        lastPoly = poly;
        lastFace = face;

        final int polyF = poly;
        final int faceF = face;

        treeScan(new AppearanceChangeProcessor()
            {
                public void changeAppearance(javax.media.j3d.Shape3D shape,
                    javax.media.j3d.Appearance app)
                {
                    if (shape instanceof Walkmesh)
                        return;
                    PolygonAttributes pa = new PolygonAttributes(polyF, faceF, 0);

                    app.setPolygonAttributes(pa);
                }
            }, display.getModelGroup());
    }

    public static void treeScan(ProcessNodeInterface pni, Node root)
    {
        TreeScan.findNode(root, Shape3D.class, pni, false, false);
    }

    private BranchGroup skeletonGroup;
    private boolean skeletonLabels;

    public void showSkeletonLabels(boolean show)
    {
        if (skeletonGroup == null)
        {
            skeletonLabels = show;
        }
        else if (skeletonLabels != show && skeletonGroup != null)
        {
            showSkeleton(true, show);
        }
    }

    public void showSkeleton(boolean show)
    {
        showSkeleton(show, skeletonLabels);
    }

    public void showSkeleton(boolean show, boolean labels)
    {
        skeletonLabelsCB.setEnabled(show);

        BranchGroup bg = display.getScene();

        if ((skeletonGroup != null) && (!show || (labels != skeletonLabels)))
        {
            bg.removeChild(skeletonGroup);
            skeletonGroup = null;
        }

        if (show && anim != null && skeletonGroup == null)
        {
            skeletonLabels = labels;

            skeletonGroup = new BranchGroup();
            skeletonGroup.setCapability(BranchGroup.ALLOW_DETACH);
            final Collection transforms = anim.getNamedTransforms().values();

            final PointArray jointPoints = new PointArray(
                    transforms.size(),
                    PointArray.COORDINATES);

            jointPoints.setCapability(PointArray.ALLOW_COORDINATE_WRITE);

            Appearance ap = new Appearance();

            ap.setPointAttributes(new PointAttributes(8, true));
            ap.setRenderingAttributes(new RenderingAttributes(false, true, RenderingAttributes.ALWAYS, 0));
            ap.setTransparencyAttributes(new TransparencyAttributes(TransparencyAttributes.BLENDED, 0.3f));
            Shape3D joints = new Shape3D(jointPoints, ap);

            final IndexedLineArray bones = new IndexedLineArray(
                    transforms.size(),
                    IndexedLineArray.COORDINATES | IndexedLineArray.USE_COORD_INDEX_ONLY,
                    (transforms.size() * 2) - 2
                );

            bones.setCapability(PointArray.ALLOW_COORDINATE_WRITE);

            int index = 0;
            HashMap tmap = new HashMap();
            Iterator it = transforms.iterator();

            while (it.hasNext())
            {
                tmap.put(it.next(), new Integer(index++));
            }

            index = 0;
            it = transforms.iterator();
            while (it.hasNext())
            {
                TransformGroup tg = (TransformGroup) it.next();

                for (int i = 0; i < tg.numChildren(); i++)
                {
                    Object child = tg.getChild(i);

                    if (child instanceof TransformGroup)
                    {
                        bones.setCoordinateIndex(index * 2, ((Integer) tmap.get(tg)).intValue());
                        bones.setCoordinateIndex(index * 2 + 1, ((Integer) tmap.get(child)).intValue());
                        index++;
                    }
                }

            }

            Shape3D bonesShape = new Shape3D(bones, ap);

            final LineArray orient = new LineArray(
                    transforms.size() * 6,
                    LineArray.COORDINATES | LineArray.COLOR_3);

            orient.setCapability(PointArray.ALLOW_COORDINATE_WRITE);
            orient.setCapability(PointArray.ALLOW_COLOR_WRITE);

            Color3f c = new Color3f(0, 1, 0);
            Color3f c2 = new Color3f(1, 0, 0);

            for (int i = 0; i < transforms.size(); i++)
            {
                orient.setColor(i * 6, c);
                orient.setColor(i * 6 + 1, c2);
                orient.setColor(i * 6 + 2, c);
                orient.setColor(i * 6 + 3, c2);
                orient.setColor(i * 6 + 4, c);
                orient.setColor(i * 6 + 5, c2);

            }
            Shape3D orientShape = new Shape3D(orient, ap);

            final Raster[] rlabs = skeletonLabels ? new Raster[transforms.size()] : null;

            if (skeletonLabels)
            {

                it = transforms.iterator();
                for (int i = 0; i < transforms.size(); i++)
                {
                    TransformGroup tg = (TransformGroup) it.next();
                    Raster r = createLabel(tg.getUserData().toString());
                    Shape3D rShape = new Shape3D(r, ap);

                    rlabs[i] = r;
                    skeletonGroup.addChild(rShape);
                }
            }

            setCoords(jointPoints, bones, orient, rlabs, transforms);
            Behavior updater = new Behavior()
                {
                    private WakeupOnElapsedFrames wakeup = new WakeupOnElapsedFrames(1, false);
                    public void initialize()
                    {
                        wakeupOn(wakeup);
                    }

                    public void processStimulus(Enumeration enumeration)
                    {
                        setCoords(jointPoints, bones, orient, rlabs, transforms);
                        wakeupOn(wakeup);
                    }
                };

            BoundingSphere bs = new BoundingSphere(new Point3d(0, 0, 0), 20.0);

            updater.setSchedulingBounds(bs);

            joints.setBoundsAutoCompute(false);
            joints.setBounds(bs);
            bonesShape.setBoundsAutoCompute(false);
            bonesShape.setBounds(bs);
            orientShape.setBoundsAutoCompute(false);
            orientShape.setBounds(bs);

            skeletonGroup.addChild(joints);
            skeletonGroup.addChild(bonesShape);
            skeletonGroup.addChild(orientShape);
            skeletonGroup.addChild(updater);
            bg.addChild(skeletonGroup);
        }
    }

    private Transform3D t3 = new Transform3D();
    private Transform3D tt = new Transform3D();
    private Vector3f v = new Vector3f();
    private Point3f p = new Point3f();

    private void setCoords(GeometryArray arr1, GeometryArray arr2, GeometryArray orient,
        Raster[] labels, Collection transforms)
    {
        Iterator it = transforms.iterator();
        int index = 0;

        while (it.hasNext())
        {
            TransformGroup tg = (TransformGroup) it.next();

            tg.getLocalToVworld(t3);
            tg.getTransform(tt);
            t3.mul(tt);

            t3.get(v);
            p.set(v);
            arr1.setCoordinate(index, p);
            arr2.setCoordinate(index, p);
            orient.setCoordinate(index * 6, p);
            orient.setCoordinate(index * 6 + 2, p);
            orient.setCoordinate(index * 6 + 4, p);
            if (labels != null)
            {
                labels[index].setPosition(p);
            }

            float len = 0.1f;

            v.set(len, 0, 0);
            t3.transform(v);
            p.add(v);
            orient.setCoordinate(index * 6 + 1, p);
            p.sub(v);

            v.set(0, len, 0);
            t3.transform(v);
            p.add(v);
            orient.setCoordinate(index * 6 + 3, p);
            p.sub(v);

            v.set(0, 0, len);
            t3.transform(v);
            p.add(v);
            orient.setCoordinate(index * 6 + 5, p);
            p.sub(v);

            index++;

        }
    }

    private Font font = new Font("monospaced", Font.PLAIN, 12);
    private FontRenderContext renderContext = new FontRenderContext(null, true, false);

    private Raster createLabel(String name)
    {
        Raster r = new Raster();

        r.setCapability(Raster.ALLOW_POSITION_WRITE);
        Rectangle2D rect = font.getStringBounds(name, renderContext);

        r.setSize((int) rect.getWidth(), (int) rect.getHeight());
        r.setType(Raster.RASTER_COLOR);
        r.setClipMode(Raster.CLIP_IMAGE);
        BufferedImage bi = new BufferedImage((int) rect.getWidth(), (int) rect.getHeight(), BufferedImage.TYPE_INT_RGB);
        java.awt.Graphics2D g2 = bi.createGraphics();

        g2.setColor(java.awt.Color.white);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.drawString(name, 0, (int) -rect.getY());
        ImageComponent2D ic = new ImageComponent2D(ImageComponent.FORMAT_RGB, bi);

        r.setImage(ic);
        return r;
    }

    private boolean geometryShown = true;
    public void showGeometry(final boolean state)
    {
        if (state != geometryShown)
        {
            geometryShown = state;
            treeScan(new AppearanceChangeProcessor()
                {
                    public void changeAppearance(javax.media.j3d.Shape3D shape,
                        javax.media.j3d.Appearance app)
                    {
                        if (shape instanceof Walkmesh)
                            return;
                        RenderingAttributes ra = new RenderingAttributes();

                        ra.setVisible(state);
                        app.setRenderingAttributes(ra);
                    }
                }, display.getModelGroup());
        }
    }

    private BranchGroup gridGroup;
    private boolean gridVisible;

    public void showGrid(final boolean state)
    {
        if (!state && gridVisible)
        {
            gridVisible = false;
            display.getScene().removeChild(gridGroup);
        }
        else if (state && !gridVisible)
        {
            gridVisible = true;
            if (gridGroup == null)
            {
                gridGroup = createGridGroup();
            }

            display.getScene().addChild(gridGroup);
        }
    }

    private BranchGroup createGridGroup()
    {
        BranchGroup bg = new BranchGroup();

        bg.setCapability(BranchGroup.ALLOW_DETACH);
        bg.clearCapabilityIsFrequent(BranchGroup.ALLOW_DETACH);

        LineArray la = new LineArray((11 + 11) * 2 + 12, LineArray.COORDINATES | LineArray.COLOR_3);
        Color3f color = new Color3f(0, 0, 0.9f);
        int index = 0;
        Point3f p = new Point3f();

        for (int x = -5; x <= 5; x++)
        {
            p.set(x, -5, 0);
            la.setColor(index, color);
            la.setCoordinate(index++, p);
            p.set(x, 5, 0);
            la.setColor(index, color);
            la.setCoordinate(index++, p);
            p.set(-5, x, 0);
            la.setColor(index, color);
            la.setCoordinate(index++, p);
            p.set(5, x, 0);
            la.setColor(index, color);
            la.setCoordinate(index++, p);
        }

        for (int i = 0; i < 3; i++)
        {
            p.set(1, -1, i);
            la.setColor(index, color);
            la.setCoordinate(index++, p);
            p.set(1, -1, i + 1);
            la.setColor(index, color);
            la.setCoordinate(index++, p);
            p.set(1, -1, i + 1);
            la.setColor(index, color);
            la.setCoordinate(index++, p);
            p.set(0.9f, -0.9f, i + 1);
            la.setColor(index, color);
            la.setCoordinate(index++, p);
        }

        Shape3D grid = new Shape3D(la);

        bg.addChild(grid);

        return bg;
    }

    public void animSliderMoved()
    {
        if (anim != null)
        {
            anim.setExplicitAnimationStage(animSlider.getValue() / 512.0f);
        }
    }

    public void showWalkmesh(boolean state)
    {
        if (walkmesh != null)
        {
            walkmesh.getAppearance().getRenderingAttributes().setVisible(state);
        }
    }

}
