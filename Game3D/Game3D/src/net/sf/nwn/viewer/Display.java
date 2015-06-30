package net.sf.nwn.viewer;


import javax.media.j3d.*;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;
import javax.vecmath.Vector3d;
import net.sf.nwn.loader.EmitterBehavior;

import com.sun.j3d.utils.behaviors.vp.OrbitBehavior;
import com.sun.j3d.utils.universe.PlatformGeometry;
import com.sun.j3d.utils.universe.SimpleUniverse;
import com.sun.j3d.utils.universe.ViewingPlatform;


public class Display extends Canvas3D
{
    private SimpleUniverse simpleUniverse;
    private float distance = 5f;
    private DirectionalLight directionalLight1;
    private DirectionalLight directionalLight2;
    private AmbientLight ambientLight;
    private BranchGroup scene;
    private BranchGroup loadingText;
    private BranchGroup modelGroup;

    public Display()
    {
        super(SimpleUniverse.getPreferredConfiguration());
        simpleUniverse = new SimpleUniverse(this);
        ViewingPlatform viewingPlatform = simpleUniverse.getViewingPlatform();
        //simpleUniverse.getViewer().getView().setTransparencySortingPolicy(View.TRANSPARENCY_SORT_GEOMETRY);
        //viewingPlatform.getViewPlatform().setActivationRadius(10);


        PlatformGeometry pg = new PlatformGeometry();
        BoundingSphere bounds =
            new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100.0);

        // Set up the ambient light
        Color3f ambientColor = new Color3f(0.1f, 0.1f, 0.1f);
        
        Color3f bgColor = new Color3f(0.3f,0.3f,0.3f);
        Background background = new Background(bgColor);
        background.setApplicationBounds(bounds);

        ambientLight = new AmbientLight(ambientColor);
        ambientLight.setInfluencingBounds(bounds);
        ambientLight.setCapability(Light.ALLOW_COLOR_READ);
        ambientLight.setCapability(Light.ALLOW_COLOR_WRITE);
        pg.addChild(ambientLight);

        // Set up the directional lights
        Color3f light1Color = new Color3f(1f, 1f, 1f);
        Vector3f light1Direction = new Vector3f(0.0f, 0.0f, -1.0f);
        Color3f light2Color = new Color3f(0.1f, 0.1f, 0.1f);
        Vector3f light2Direction = new Vector3f(-1.0f, -1.0f, -1.0f);

        directionalLight1 = new DirectionalLight(light1Color, light1Direction);
        directionalLight1.setInfluencingBounds(bounds);
        directionalLight1.setCapability(Light.ALLOW_COLOR_READ);
        directionalLight1.setCapability(Light.ALLOW_COLOR_WRITE);
        directionalLight1.setCapability(DirectionalLight.ALLOW_DIRECTION_READ);
        directionalLight1.setCapability(DirectionalLight.ALLOW_DIRECTION_WRITE);
        pg.addChild(directionalLight1);

        directionalLight2 = new DirectionalLight(light2Color, light2Direction);
        directionalLight2.setInfluencingBounds(bounds);
        directionalLight2.setCapability(Light.ALLOW_COLOR_READ);
        directionalLight2.setCapability(Light.ALLOW_COLOR_WRITE);
        directionalLight2.setCapability(DirectionalLight.ALLOW_DIRECTION_READ);
        directionalLight2.setCapability(DirectionalLight.ALLOW_DIRECTION_WRITE);
        pg.addChild(directionalLight2);

        viewingPlatform.setPlatformGeometry(pg);

        // This will move the ViewPlatform back a bit so the
        // objects in the scene can be viewed.
        Transform3D t3 = new Transform3D();

        t3.rotX(Math.PI * 0.6);
        Transform3D temp = new Transform3D();

        temp.rotY(Math.PI * 0.8);
        t3.mul(temp);
        t3.setTranslation(new Vector3f(4, distance, 3));

        viewingPlatform.getViewPlatformTransform().setTransform(t3);

        OrbitBehavior orbit = new OrbitBehavior(this,
                OrbitBehavior.REVERSE_ALL);

        orbit.setRotationCenter(new Point3d(0, 0, 0));
        orbit.setSchedulingBounds(bounds);
        viewingPlatform.setViewPlatformBehavior(orbit);

        scene = new BranchGroup();
        scene.setCapability(BranchGroup.ALLOW_CHILDREN_EXTEND);
        scene.setCapability(BranchGroup.ALLOW_CHILDREN_WRITE);
        scene.setCapability(BranchGroup.ALLOW_CHILDREN_READ);
        modelGroup = new BranchGroup();
        modelGroup.setCapability(BranchGroup.ALLOW_CHILDREN_EXTEND);
        modelGroup.setCapability(BranchGroup.ALLOW_CHILDREN_WRITE);
        modelGroup.setCapability(BranchGroup.ALLOW_CHILDREN_READ);
        scene.addChild(modelGroup);
        
        scene.addChild(background);

        simpleUniverse.addBranchGraph(scene);

        loadingText = createLoadingMessage();

    }

    private long start = System.currentTimeMillis();
    private int frames = 0;
    private int lastFrame = -1;
    private long fpsThreshold = 256;

    public void postRender()
    {
    	/**
    	 * implemets a FPS counter
    	 */
        frames++;
        if (frames > fpsThreshold)
        {
            long curr = System.currentTimeMillis();

            lastFrame = (int) (frames * 1000L / (curr - start));
            start = curr;
            System.out.println("Display FPS:" + lastFrame);
            frames = 0;
        }
    }

    /**
     * Gets the directionalLight1.
     * @return Returns a DirectionalLight
     */
    public DirectionalLight getDirectionalLight1()
    {
        return directionalLight1;
    }

    /**
     * Sets the directionalLight1.
     * @param directionalLight1 The directionalLight1 to set
     */
    public void setDirectionalLight1(DirectionalLight directionalLight1)
    {
        this.directionalLight1 = directionalLight1;
    }

    /**
     * Gets the directionalLight2.
     * @return Returns a DirectionalLight
     */
    public DirectionalLight getDirectionalLight2()
    {
        return directionalLight2;
    }

    /**
     * Sets the directionalLight2.
     * @param directionalLight2 The directionalLight2 to set
     */
    public void setDirectionalLight2(DirectionalLight directionalLight2)
    {
        this.directionalLight2 = directionalLight2;
    }

    /**
     * Gets the ambientLight.
     * @return Returns a AmbientLight
     */
    public AmbientLight getAmbientLight()
    {
        return ambientLight;
    }

    /**
     * Sets the ambientLight.
     * @param ambientLight The ambientLight to set
     */
    public void setAmbientLight(AmbientLight ambientLight)
    {
        this.ambientLight = ambientLight;
    }

    /**
     * Gets the scene.
     * @return Returns a BranchGroup
     */
    public BranchGroup getScene()
    {
        return scene;
    }

    /**
     * Sets the scene.
     * @param scene The scene to set
     */
    public void setScene(BranchGroup scene)
    {
        this.scene = scene;
    }

    public void addLoadingMessage()
    {
        scene.addChild(loadingText);
    }

    public void removeLoadingMessage()
    {
        scene.removeChild(loadingText);
    }

    public BranchGroup getModelGroup()
    {
        return modelGroup;
    }

    public void destoryEverything()
    {
        simpleUniverse.removeAllLocales();
    }

    private BranchGroup createLoadingMessage()
    {
        String textString = "Loading Model";
        String fontName = "serif";
        float tessellation = 0.1f;
        float sl = textString.length();
        // Create the root of the branch graph
        BranchGroup objRoot = new BranchGroup();

        // Create a Transformgroup to scale all objects so they
        // appear in the scene.
        TransformGroup objScale = new TransformGroup();
        Transform3D t3d = new Transform3D();

        // Assuming uniform size chars, set scale to fit string in view
        t3d.rotX(Math.PI / 2);
        t3d.setScale(2 / sl);
        objScale.setTransform(t3d);
        objRoot.addChild(objScale);

        // Create the transform group node and initialize it to the
        // identity.  Enable the TRANSFORM_WRITE capability so that
        // our behavior code can modify it at runtime.  Add it to the
        // root of the subgraph.
        TransformGroup objTrans = new TransformGroup();

        objTrans.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        objTrans.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
        objScale.addChild(objTrans);

        Font3D f3d;

        f3d = new Font3D(new java.awt.Font(fontName, java.awt.Font.PLAIN, 2),
                    tessellation,
                    new FontExtrusion());

        Text3D txt = new Text3D(f3d, textString,
                new Point3f(-sl / 2.0f, -1.f, -1.f));
        Shape3D sh = new Shape3D();
        Appearance app = new Appearance();
        Material mm = new Material();

        mm.setLightingEnable(true);
        app.setMaterial(mm);
        sh.setGeometry(txt);
        sh.setAppearance(app);
        objTrans.addChild(sh);

        BoundingSphere bounds =
            new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100.0);

        Transform3D yAxis = new Transform3D();
        Alpha rotationAlpha = new Alpha(-1, Alpha.INCREASING_ENABLE,
                0, 0,
                4000, 0, 0,
                0, 0, 0);

        RotationInterpolator rotator =
            new RotationInterpolator(rotationAlpha, objTrans, yAxis,
                0.0f, (float) -Math.PI * 2.0f);

        rotator.setSchedulingBounds(bounds);
        objTrans.addChild(rotator);

        objRoot.setCapability(BranchGroup.ALLOW_DETACH);
        objRoot.compile();
        return objRoot;

    }
}
