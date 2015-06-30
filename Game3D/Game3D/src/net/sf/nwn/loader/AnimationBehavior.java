
package net.sf.nwn.loader;


import java.util.*;
import com.sun.j3d.utils.timer.J3DTimer;
import javax.media.j3d.*;


public class AnimationBehavior extends Behavior
    implements com.sun.j3d.utils.scenegraph.io.SceneGraphIO
{
    private WakeupOnElapsedFrames wakeup = new WakeupOnElapsedFrames(FRAME_SLEEP, false);
    private Map /*String,TransformGroup*/ namedTransforms;
    private Map /*String,EmitterBehavior*/ namedEmitters;
    private Map /*String,ModelAnimation*/ animations;
    private List /*String*/ defaultAnims;
    private float animationTimeScale = 1f;

    private ModelAnimation currentAnimation;
    private ModelAnimation nextAnimation;
    private long start;
    private boolean loop;

    private float explicitAnimationStage = -1;

    public AnimationBehavior()
    {
    }

    public AnimationBehavior(Map aNamedTransforms, Map aNamedEmitters, Map anims, List aDefaultAnims)
    {
        setSchedulingBounds(new BoundingSphere(new javax.vecmath.Point3d(0, 0, 0), ACTIVATION_RADIUS));
        namedTransforms = aNamedTransforms;
        animations = anims;
        defaultAnims = aDefaultAnims;
        namedEmitters = aNamedEmitters;
    }

    public void initialize()
    {
        wakeupOn(wakeup);
    }

    static long totalTime;
    static int count;

    public void processStimulus(Enumeration enumeration)
    {
        wakeupOn(wakeup);

        if (nextAnimation != null)
        {
            start = getTime();
            currentAnimation = nextAnimation;
            nextAnimation = null;
        }

        if (currentAnimation == null)
        {
            playDefaultAnimation();
            return;
        }

        long time = getTime();
        float next = (float) ((time - start) / (TIMER_SCALE * animationTimeScale));

        boolean interpolate = true;

        if (explicitAnimationStage >= 0)
        {
            next = currentAnimation.getLength() * explicitAnimationStage;
            interpolate = false;
        }
        else
        {
            if (next >= currentAnimation.getLength())
            {
                if (!loop)
                {
                    playDefaultAnimation();
                    return;
                }
                nextAnimation = currentAnimation;
                return;
            }
        }
        currentAnimation.update(namedTransforms, namedEmitters, next, interpolate);

    }

    public synchronized void playDefaultAnimation()
    {

        int count = defaultAnims.size();

        if (count == 0)
            return;
        count = (int) (Math.random() * count);
        playAnimation(
            (ModelAnimation) animations.get(defaultAnims.get(count)),
            false);
        return;
    }

    public void playAnimation(String name, boolean wantLoop)
    {
        ModelAnimation ma = (ModelAnimation) animations.get(name);

        if (ma == null)
            return;
        playAnimation(ma, wantLoop);
    }

    public void playAnimation(ModelAnimation ma, boolean wantLoop)
    {
        nextAnimation = ma;
        loop = wantLoop;
    }

    public List /*String*/ getDefaultAnimations()
    {
        return defaultAnims;
    }

    public synchronized void setDefaultAnimations(List/*String*/ anims)
    {
        defaultAnims = anims;
    }

    public Set /*String*/ getAllAnimationNames()
    {
        return animations.keySet();
    }

    public Collection /*ModelAnimation*/ getAllAnimations()
    {
        return animations.values();
    }

    public float getAnimationTimeScale()
    {
        return animationTimeScale;
    }

    public void setAnimationTimeScale(float animationTimeScale)
    {
        this.animationTimeScale = animationTimeScale;
    }

    public Map getNamedTransforms()
    {
        return namedTransforms;
    }

    public javax.media.j3d.Node cloneNode(boolean forceDuplicate)
    {
        AnimationBehavior usc = new AnimationBehavior();

        usc.duplicateNode(this, forceDuplicate);
        return usc;
    }

    public void duplicateNode(javax.media.j3d.Node originalNode, boolean forceDuplicate)
    {
        super.duplicateNode(originalNode, forceDuplicate);
        AnimationBehavior a = (AnimationBehavior) originalNode;

        if (forceDuplicate)
        {
            animations = new HashMap(a.animations);
            defaultAnims = new ArrayList(a.defaultAnims);
        }
        else
        {
            animations = a.animations;
            defaultAnims = a.defaultAnims;
        }

        namedTransforms = new HashMap(a.namedTransforms);
        namedEmitters = new HashMap(a.namedEmitters);
    }

    public void updateNodeReferences(NodeReferenceTable table)
    {
        super.updateNodeReferences(table);
        Iterator it = namedTransforms.entrySet().iterator();

        while (it.hasNext())
        {
            Map.Entry entry = (Map.Entry) it.next();

            entry.setValue(table.getNewObjectReference((TransformGroup) entry.getValue()));
        }

        it = namedEmitters.entrySet().iterator();

        while (it.hasNext())
        {
            Map.Entry entry = (Map.Entry) it.next();

            entry.setValue(table.getNewObjectReference((EmitterBehavior) entry.getValue()));
        }
    }

    public void setExplicitAnimationStage(float stage)
    {
        explicitAnimationStage = stage;
    }

    public void disableExplicitAnimationStage()
    {
        explicitAnimationStage = -1;
    }

    private static boolean timerWorkaround = false;
    private static boolean timerSystem = false;
    static
    {
        try
        {
            timerWorkaround = System.getProperty("j3dtimer.workaround", "false").equalsIgnoreCase("true");
            if (timerWorkaround)
                System.out.println("Beta 1 j3dtimer workaround active");
            timerSystem =
                    System.getProperty(
                        "j3dtimer.usesystem",
                        "" + System.getProperty("os.name", "other").equalsIgnoreCase("linux")
                    ).equalsIgnoreCase("true");
            if (timerSystem)
            {
                System.out.println("Timer will use System.currentTimeMillis");
            }
        } catch (Exception exc)
        {
            exc.printStackTrace();
        }
    }

    public static final double TIMER_SCALE = timerWorkaround ? 1000000000.0 : 1000000000.0;
    
    

    /**
     * Clock time in nnoseconds
     * @return
     */
    public static long getTime()
    {
        if (timerWorkaround)
        {
           // return J3DTimer.getValue() * J3DTimer.getResolution();
        	return J3dClock.currentTimeMillis() * 1000000l;
        }
        else if (timerSystem)
        {
            return System.currentTimeMillis() * 1000000l;
        }
        else
        {
            //return J3DTimer.getValue();
        	return J3dClock.currentTimeMillis() * 1000000l;
        }
    }

    public static int FRAME_SLEEP = 0;

    //  ---- SCENEGRAPH IO ----------------------------------------------

    private HashMap /*String, TransformGroupReference */ namedTransformReferences = null;
    private HashMap /*String, TransformGroupReference */ namedEmitterReferences = null;

    public void createSceneGraphObjectReferences(com.sun.j3d.utils.scenegraph.io.SceneGraphObjectReferenceControl sceneGraphObjectReferenceControl)
    {
        // Write named TransformGroups

        namedTransformReferences = new HashMap();
        namedEmitterReferences = new HashMap();

        Iterator it = namedTransforms.entrySet().iterator();

        while (it.hasNext())
        {
            Map.Entry entry = (Map.Entry) it.next();

            //if( entry.getValue() instanceof TransformGroup )
            namedTransformReferences.put(entry.getKey(), new Integer(sceneGraphObjectReferenceControl.addReference((TransformGroup) entry.getValue())));
        }
        it = namedEmitters.entrySet().iterator();
        while (it.hasNext())
        {
            Map.Entry entry = (Map.Entry) it.next();

            //if( entry.getValue() instanceof TransformGroup )
            namedEmitterReferences.put(entry.getKey(), new Integer(sceneGraphObjectReferenceControl.addReference((EmitterBehavior) entry.getValue())));
        }
    }

    public void readSceneGraphObject(java.io.DataInput dataInput)
        throws java.io.IOException
    {
        int size = dataInput.readInt();
        byte[] bytes = new byte[size];

        dataInput.readFully(bytes);
        java.io.ByteArrayInputStream byteStream = new java.io.ByteArrayInputStream(bytes);
        java.io.ObjectInputStream oi = new java.io.ObjectInputStream(byteStream);

        try
        {
            animations = (Map) oi.readObject();
            defaultAnims = (List) oi.readObject();
            namedTransformReferences = (HashMap) oi.readObject();
            namedEmitterReferences = (HashMap) oi.readObject();
        } catch (ClassNotFoundException cnfE)
        {
            throw new java.io.IOException("Class Not Found while loading, class " + cnfE.getMessage());
        }
    }

    public void restoreSceneGraphObjectReferences(com.sun.j3d.utils.scenegraph.io.SceneGraphObjectReferenceControl sceneGraphObjectReferenceControl)
    {
        // Restore named transform groups
        namedTransforms = new HashMap();
        namedEmitters = new HashMap();

        Iterator it = namedTransformReferences.entrySet().iterator();

        while (it.hasNext())
        {
            Map.Entry entry = (Map.Entry) it.next();

            namedTransforms.put(entry.getKey(), sceneGraphObjectReferenceControl.resolveReference(((Integer) entry.getValue()).intValue()));
        }

        it = namedEmitterReferences.entrySet().iterator();
        while (it.hasNext())
        {
            Map.Entry entry = (Map.Entry) it.next();

            namedEmitters.put(entry.getKey(), sceneGraphObjectReferenceControl.resolveReference(((Integer) entry.getValue()).intValue()));
        }
        namedTransformReferences = null;
        namedEmitterReferences = null;

    }

    public boolean saveChildren()
    {
        // We don't have any children
        return false;
    }

    public void writeSceneGraphObject(java.io.DataOutput dataOutput)
        throws java.io.IOException
    {
        java.io.ByteArrayOutputStream byteStream = new java.io.ByteArrayOutputStream();
        java.io.ObjectOutputStream oo = new java.io.ObjectOutputStream(byteStream);

        oo.writeObject(animations);
        oo.writeObject(defaultAnims);
        oo.writeObject(namedTransformReferences);
        oo.writeObject(namedEmitterReferences);

        oo.close();
        dataOutput.writeInt(byteStream.size());
        dataOutput.write(byteStream.toByteArray());

        namedTransformReferences = null;
        namedEmitterReferences = null;
    }

    public static float ACTIVATION_RADIUS = 5f;
}
