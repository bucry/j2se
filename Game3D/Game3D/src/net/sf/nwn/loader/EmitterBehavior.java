package net.sf.nwn.loader;


import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.*;

import com.sun.j3d.utils.scenegraph.io.SceneGraphObjectReferenceControl;
import com.sun.j3d.utils.timer.J3DTimer;
import javax.media.j3d.*;
import javax.vecmath.*;


public class EmitterBehavior extends Behavior
    implements GeometryUpdater, com.sun.j3d.utils.scenegraph.io.SceneGraphIO
{
    private WakeupOnElapsedFrames wakeup = new WakeupOnElapsedFrames(0, false);
    private ParticleCollection pc;

    private int maxParticles;
    private float[] life;
    private float[] position;
    private float[] velocity;

    private long lastTime = AnimationBehavior.getTime();
    private float partialBirth;

    // -- emitter properties
    private Color3f colorStart;
    private Color3f colorEnd;
    private float alphaStart;
    private float alphaEnd;
    private float sizeStart;
    private float sizeEnd;
    private float sizeStart_y;
    private float sizeEnd_y;
    private int frameStart;
    private int frameEnd;
    private float birthrate;
    private float lifeExp;
    private float mass;
    private float spread;
    private float particleRot;
    private float startVelocity;
    private float randvel;
    private float fps;
    private float xsize;
    private float ysize;
    private int blend;
    private int xgrid;
    private int ygrid;
    private int render;
    private int p2pType;
    private float grav;
    private float drag;
    private int inheritType;
    private int update;

    // -- end of emitter properties    

    public EmitterBehavior()
    {
    }

    public EmitterBehavior(EmitterNode emitter, TransformGroup local, Appearance ap)
    {

        setSchedulingBounds(new BoundingSphere(new Point3d(0, 0, 0), ACTIVATION_RADIUS));

        colorStart = emitter.getColorStart();
        colorEnd = emitter.getColorEnd();
        alphaStart = emitter.getAlphaStart();
        alphaEnd = emitter.getAlphaEnd();
        sizeStart = emitter.getSizeStart();
        sizeEnd = emitter.getSizeEnd();
        sizeStart_y = emitter.getSizeStart_y();
        sizeEnd_y = emitter.getSizeEnd_y();
        frameStart = emitter.getFrameStart();
        frameEnd = emitter.getFrameEnd();
        birthrate = emitter.getBirthrate();
        lifeExp = emitter.getLifeExp();
        mass = emitter.getMass();
        spread = emitter.getSpread();
        particleRot = emitter.getParticleRot();
        startVelocity = emitter.getVelocity();
        randvel = emitter.getRandvel();
        fps = emitter.getFps();
        xsize = emitter.getXsize();
        ysize = emitter.getYsize();
        blend = emitter.getBlend();
        xgrid = emitter.getXgrid();
        ygrid = emitter.getYgrid();
        render = emitter.getRender();
        p2pType = emitter.getP2pType();
        grav = emitter.getGrav();
        drag = emitter.getDrag();
        update = emitter.getUpdate();

        if (update == EmitterNode.UPDATE_SINGLE)
        {
            maxParticles = 1;
        }
        else
        {
            maxParticles = (int) Math.ceil(birthrate * lifeExp) + 2;
        }

        pc = new ParticleCollection(maxParticles, ap);
        life = new float[maxParticles];
        position = new float[maxParticles * 3];
        velocity = new float[maxParticles * 3];

        if (emitter.getInherit() == 1)
        {
            inheritType = INHERIT_ALL;
        }
        else if (emitter.getInherit_local() == 1)
        {
            inheritType = INHERIT_WORLD;
        }
        else if (emitter.getInherit_part() == 1)
        {
            inheritType = INHERIT_POSITION;
        }
        else
        {
            inheritType = INHERIT_ALL;
        }

        for (int i = 0; i < maxParticles; i++)
        {
            life[i] = -1;
        }
        local.addChild(pc);

        if (particleRot != 0 && render == EmitterNode.RENDER_MOTION_BLUR)
        {
            System.err.println("WARN: particleRot and motionBlur are not possible together - turning off particleRotation");
        }

    }

    public void initialize()
    {
        wakeupOn(wakeup);
    }

    public void processStimulus(Enumeration enumeration)
    {
        wakeupOn(wakeup);
        pc.updateGeometry(this);
    }

    private Transform3D eyeT = new Transform3D();
    private Transform3D eyeTI = new Transform3D();
    private Vector3f velVec = new Vector3f();
    private Vector3f dirVec = new Vector3f();
    private Point3f pos = new Point3f();
    private float[] tcoords = new float[]
        { 0, 0,
            0, 0.5f,
            0.5f, 0.5f,
            0.5f, 0 };

    private static float BIRTH_GRID_SCALE = 0.01f;
    private static float EMITTER_TIME_SCALE = 1;

    public void updateData(Geometry arg0)
    {
        if (canvas == null)
        {
            canvas = getView().getCanvas3D(0);
        }
        canvas.getImagePlateToVworld(eyeT);

        eyeT.setScale(1);
        eyeTI.invert(eyeT);

        pc.updateTransforms(eyeT);
        Transform3D local = pc.getLocalTransform();
        Transform3D localI = pc.getLocalTransformI();
        Vector3f eyeVec = pc.getEyeVector();

        long time = AnimationBehavior.getTime();
        float passed = (float) ((time - lastTime) / AnimationBehavior.TIMER_SCALE) * EMITTER_TIME_SCALE;

        lastTime = time;
        passed = (float) Math.min(passed, 0.05);
        partialBirth += passed * birthrate;

        for (int i = 0, o3 = 0; i < maxParticles; i++, o3 += 3)
        {
            pos.set(position[o3], position[o3 + 1], position[o3 + 2]);
            if (inheritType == INHERIT_WORLD)
            {
                localI.transform(pos);
            }
            else if (inheritType == INHERIT_POSITION)
            {
                dirVec.set(pos);
                localI.transform(dirVec);
                pos.set(dirVec);
            }

            if (life[i] < 0)
            {
                if ((update == EmitterNode.UPDATE_SINGLE) || partialBirth > 1)
                {
                    partialBirth -= 1;
                    life[i] = 0;
                    pos.x = (float) ((Math.random() - 0.5) * xsize * BIRTH_GRID_SCALE);
                    pos.y = (float) ((Math.random() - 0.5) * ysize * BIRTH_GRID_SCALE);
                    pos.z = 0;
                    double speed = startVelocity + (Math.random() - 0.5) * randvel * 2;
                    double xangle = (Math.random() - 0.5) * spread;
                    double zangle = (Math.random() - 0.5) * spread;
                    double vx = Math.sin(xangle);
                    double vy = Math.sin(zangle);
                    double vz = Math.cos(xangle) + Math.cos(zangle);

                    double d = speed / (Math.abs(vx) + Math.abs(vy) + Math.abs(vz));

                    velocity[o3] = (float) (d * vx);
                    velocity[o3 + 1] = (float) (d * vy);
                    velocity[o3 + 2] = (float) (d * vz);

                }
            }
            else
            {
                if ((update == EmitterNode.UPDATE_SINGLE) || (life[i] < lifeExp))
                {
                    dirVec.set(0, 0, -mass * passed * 10);
                    localI.transform(dirVec);
                    velocity[o3] += dirVec.x;
                    velocity[o3 + 1] += dirVec.y;
                    velocity[o3 + 2] += dirVec.z;

                    if (p2pType == EmitterNode.P2P_TYPE_GRAVITY)
                    {
                        float sum = Math.abs(pos.x)
                            + Math.abs(pos.y)
                            + Math.abs(pos.z);

                        velocity[o3] -= passed * grav * pos.x / sum;
                        velocity[o3 + 1] -= passed * grav * pos.y / sum;
                        velocity[o3 + 2] -= passed * grav * pos.z / sum;
                    }

                    pos.x += velocity[o3] * passed;
                    pos.y += velocity[o3 + 1] * passed;
                    pos.z += velocity[o3 + 2] * passed;
                    life[i] += passed;

                    if (p2pType == EmitterNode.P2P_TYPE_GRAVITY)
                    {
                        // different sign - passed through zero
                        if (pos.x * (pos.x - velocity[o3] * passed) < 0)
                        {
                            velocity[o3] *= drag;
                            velocity[o3 + 1] *= drag;
                            velocity[o3 + 2] *= drag;
                        }
                    }
                }
                else
                {
                    life[i] = -1;
                }
            }
            float l = life[i];

            if (l < 0)
            {
                pc.updateParticle(i,
                    0, 0, 0,
                    0.0f, 0.0f, 0,
                    0.0f, 0.0f, 0.0f, 0.0f,
                    tcoords);
            }
            else
            {
                float mature = l / lifeExp;

                if (update == EmitterNode.UPDATE_SINGLE)
                    mature = 0;

                Color3f start = colorStart;
                Color3f end = colorEnd;

                float r = start.x + (end.x - start.x) * mature;
                float g = start.y + (end.y - start.y) * mature;
                float b = start.z + (end.z - start.z) * mature;
                float a = alphaStart + (alphaEnd - alphaStart) * mature;

                float xs = sizeStart + (sizeEnd - sizeStart) * mature;
                float ys = xs;

                if (sizeStart_y != 0 || sizeEnd_y != 0)
                {
                    ys = sizeStart_y + (sizeEnd_y - sizeStart_y) * mature;
                }

                float tx = 1.0f / xgrid;
                float ty = 1.0f / ygrid;

                int frame = frameStart + ((int) (fps * l)) % (frameEnd + 1 - frameStart);

                float stx = tx * (frame % xgrid);
                float sty = tx * ((frame / xgrid) % ygrid);
                float rotation = l * particleRot;

                tcoords[0] = stx;
                tcoords[1] = sty;
                tcoords[2] = stx;
                tcoords[3] = sty + ty;
                tcoords[4] = stx + tx;
                tcoords[5] = sty + ty;
                tcoords[6] = stx + tx;
                tcoords[7] = sty;

                if (render == EmitterNode.RENDER_MOTION_BLUR)
                {
                    velVec.set(velocity[o3], velocity[o3 + 1], velocity[o3 + 2]);
                    float speed = velVec.length();

                    local.transform(velVec);
                    velVec.normalize();
                    float dot = Math.abs(velVec.dot(eyeVec));
                    float blurlen = 1 * speed;

                    blurlen = Math.min(blurlen, speed * l);
                    ys = ys * dot + blurlen * (1 - dot);

                    eyeTI.transform(velVec);
                    velVec.z = 0;

                    dirVec.set(0, 1, 0);

                    rotation = velVec.angle(dirVec);
                    if (velVec.x > 0)
                        rotation = -rotation;

                }
                else if (render == EmitterNode.RENDER_ALIGNED_TO_WORLD_Z)
                {
                    // todo
                }
                else if (render == EmitterNode.RENDER_LINKED)
                {
                    System.err.println("Render mode 'linked' not supported");
                }

                pc.updateParticle(i,
                    pos.x, pos.y, pos.z,
                    xs, ys, rotation,
                    r, g, b, a,
                    tcoords);
            }
            if (inheritType == INHERIT_WORLD)
            {
                local.transform(pos);
            }
            else if (inheritType == INHERIT_POSITION)
            {
                dirVec.set(pos);
                local.transform(dirVec);
                pos.set(dirVec);
            }
            position[o3] = pos.x;
            position[o3 + 1] = pos.y;
            position[o3 + 2] = pos.z;
        }
        if ((update != EmitterNode.UPDATE_SINGLE) && partialBirth > 1)
        {
            System.err.println("Partial birth " + partialBirth);
        }

    }

    private static Canvas3D canvas;

    /**
     * Gets the birthrate.
     * @return Returns a float
     */
    public float getBirthrate()
    {
        return birthrate;
    }

    /**
     * Sets the birthrate.
     * @param birthrate The birthrate to set
     */
    public void setBirthrate(float birthrate)
    {
        this.birthrate = birthrate;
    }

    public javax.media.j3d.Node cloneNode(boolean forceDuplicate)
    {
        EmitterBehavior usc = new EmitterBehavior();

        usc.duplicateNode(this, forceDuplicate);
        return usc;
    }

    public void duplicateNode(javax.media.j3d.Node originalNode, boolean forceDuplicate)
    {
        super.duplicateNode(originalNode, forceDuplicate);
        EmitterBehavior emitter = (EmitterBehavior) originalNode;

        colorStart = emitter.colorStart;
        colorEnd = emitter.colorEnd;
        alphaStart = emitter.alphaStart;
        alphaEnd = emitter.alphaEnd;
        sizeStart = emitter.sizeStart;
        sizeEnd = emitter.sizeEnd;
        sizeStart_y = emitter.sizeStart_y;
        sizeEnd_y = emitter.sizeEnd_y;
        frameStart = emitter.frameStart;
        frameEnd = emitter.frameEnd;
        birthrate = emitter.birthrate;
        lifeExp = emitter.lifeExp;
        mass = emitter.mass;
        spread = emitter.spread;
        particleRot = emitter.particleRot;
        startVelocity = emitter.startVelocity;
        randvel = emitter.randvel;
        fps = emitter.fps;
        xsize = emitter.xsize;
        ysize = emitter.ysize;
        blend = emitter.blend;
        xgrid = emitter.xgrid;
        ygrid = emitter.ygrid;
        render = emitter.render;
        p2pType = emitter.p2pType;
        grav = emitter.grav;
        drag = emitter.drag;
        maxParticles = emitter.maxParticles;
        inheritType = emitter.inheritType;

        life = new float[maxParticles];
        position = new float[maxParticles * 3];
        velocity = new float[maxParticles * 3];
        for (int i = 0; i < maxParticles; i++)
        {
            life[i] = -1;
        }

        // to be updated by NodeReferenceTable 
        pc = emitter.pc;
    }

    public void updateNodeReferences(NodeReferenceTable table)
    {
        super.updateNodeReferences(table);
        pc = (ParticleCollection) table.getNewObjectReference(pc);
    }

    private int particleCollectionReference;

    /**
     * @see SceneGraphIO#createSceneGraphObjectReferences(SceneGraphObjectReferenceControl)
     */
    public void createSceneGraphObjectReferences(SceneGraphObjectReferenceControl refControl)
    {
        particleCollectionReference = refControl.addReference(pc);
    }

    /**
     * @see SceneGraphIO#readSceneGraphObject(DataInput)
     */
    public void readSceneGraphObject(DataInput data)
        throws IOException
    {
        particleCollectionReference = data.readInt();
        maxParticles = data.readInt();
        colorStart = readColor(data);
        colorEnd = readColor(data);

        alphaStart = data.readFloat();
        alphaEnd = data.readFloat();
        sizeStart = data.readFloat();
        sizeEnd = data.readFloat();
        sizeStart_y = data.readFloat();
        sizeEnd_y = data.readFloat();
        frameStart = data.readInt();
        frameEnd = data.readInt();
        birthrate = data.readFloat();
        lifeExp = data.readFloat();
        mass = data.readFloat();
        spread = data.readFloat();
        particleRot = data.readFloat();
        startVelocity = data.readFloat();
        randvel = data.readFloat();
        fps = data.readFloat();
        xsize = data.readFloat();
        ysize = data.readFloat();
        blend = data.readInt();
        xgrid = data.readInt();
        ygrid = data.readInt();
        render = data.readInt();
        p2pType = data.readInt();
        grav = data.readFloat();
        drag = data.readFloat();
        inheritType = data.readInt();

        life = new float[maxParticles];
        position = new float[maxParticles * 3];
        velocity = new float[maxParticles * 3];
        for (int i = 0; i < maxParticles; i++)
        {
            life[i] = -1;
        }
        lastTime = AnimationBehavior.getTime();
        partialBirth = 0;

    }

    /**
     * @see SceneGraphIO#restoreSceneGraphObjectReferences(SceneGraphObjectReferenceControl)
     */
    public void restoreSceneGraphObjectReferences(SceneGraphObjectReferenceControl refControl)
    {
        pc = (ParticleCollection) refControl.resolveReference(particleCollectionReference);
    }

    /**
     * @see SceneGraphIO#saveChildren()
     */
    public boolean saveChildren()
    {
        return false;
    }

    /**
     * @see SceneGraphIO#writeSceneGraphObject(DataOutput)
     */
    public void writeSceneGraphObject(DataOutput data)
        throws IOException
    {
        data.writeInt(particleCollectionReference);

        data.writeInt(maxParticles);
        writeColor(data, colorStart);
        writeColor(data, colorEnd);

        data.writeFloat(alphaStart);
        data.writeFloat(alphaEnd);
        data.writeFloat(sizeStart);
        data.writeFloat(sizeEnd);
        data.writeFloat(sizeStart_y);
        data.writeFloat(sizeEnd_y);
        data.writeInt(frameStart);
        data.writeInt(frameEnd);
        data.writeFloat(birthrate);
        data.writeFloat(lifeExp);
        data.writeFloat(mass);
        data.writeFloat(spread);
        data.writeFloat(particleRot);
        data.writeFloat(startVelocity);
        data.writeFloat(randvel);
        data.writeFloat(fps);
        data.writeFloat(xsize);
        data.writeFloat(ysize);
        data.writeInt(blend);
        data.writeInt(xgrid);
        data.writeInt(ygrid);
        data.writeInt(render);
        data.writeInt(p2pType);
        data.writeFloat(grav);
        data.writeFloat(drag);
        data.writeInt(inheritType);
    }

    private void writeColor(DataOutput data, Color3f color)
        throws IOException
    {
        data.writeFloat(color.x);
        data.writeFloat(color.y);
        data.writeFloat(color.z);
    }

    private Color3f readColor(DataInput data)
        throws IOException
    {
        return new Color3f(data.readFloat(), data.readFloat(), data.readFloat());
    }

    public static float ACTIVATION_RADIUS = 15f;

    public static int INHERIT_WORLD = 1;
    public static int INHERIT_POSITION = 2;
    public static int INHERIT_ALL = 3;
}
