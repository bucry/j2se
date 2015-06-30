package net.sf.nwn.loader;


import java.io.*;
import java.net.*;
import java.util.*;
import com.sun.j3d.utils.geometry.*;
import com.sun.j3d.utils.image.*;
import javax.media.j3d.*;
import javax.media.j3d.Texture2D;
import javax.media.j3d.TriangleArray;
import javax.swing.event.ListSelectionEvent;
import javax.vecmath.*;


public final class EmitterNode extends DummyGeomNode
{
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
    // spawnType 0 
    private float lifeExp;
    private float mass;
    private float spread;
    private float particleRot;
    private float velocity;
    private float randvel;
    private float fps;
    // random 1
    private int inherit;
    private int inherit_local;
    private int inherit_part;
    // inheritvel 0 
    private float xsize;
    private float ysize;
    // bounce 1 
    // bounce_co 0.2
    // loop 0 
    private int update;
    private int render;
    private int blend;
    // update_sel 1 
    // render_sel 1 
    // blend_sel 3 
    // deadspace 0.0 
    // opacity 0.5 
    // blurlength 10.0 
    // lightningDelay 0.0 
    // lightningRadius 0.0 
    // lightningScale 0.0 
    // blastRadius 0.0 
    // blastLength 0.0 
    // twosidedtex 0 
    // p2p 0 
    // p2p_sel 1 
    // p2p_type Bezier 
    private int p2pType;
    // p2p_bezier2 0.0 
    // p2p_bezier3 0.0 
    // combinetime 0.0 
    private float drag;
    private float grav;
    // threshold 0.0 
    private String texture;
    private int xgrid;
    private int ygrid;
    // affectedByWind false 
    // m_isTinted 0 
    // renderorder 0 
    // Splat 0  


    public EmitterNode(GeomNode parent)
    {
        super(parent);
    }

    /**
     * Gets the alphaEnd.
     * @return Returns a float
     */
    public float getAlphaEnd()
    {
        return alphaEnd;
    }

    /**
     * Sets the alphaEnd.
     * @param alphaEnd The alphaEnd to set
     */
    public void setAlphaEnd(float alphaEnd)
    {
        this.alphaEnd = alphaEnd;
    }

    /**
     * Gets the alphaStart.
     * @return Returns a float
     */
    public float getAlphaStart()
    {
        return alphaStart;
    }

    /**
     * Sets the alphaStart.
     * @param alphaStart The alphaStart to set
     */
    public void setAlphaStart(float alphaStart)
    {
        this.alphaStart = alphaStart;
    }

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

    /**
     * Gets the blend.
     * @return Returns a int
     */
    public int getBlend()
    {
        return blend;
    }

    /**
     * Sets the blend.
     * @param blend The blend to set
     */
    public void setBlend(int blend)
    {
        this.blend = blend;
    }

    public void setBlend(String bstr)
    {
        if (bstr.equalsIgnoreCase("Lighten"))
        {
            blend = BLEND_LIGHTEN;
        }
        else if (bstr.equalsIgnoreCase("Normal"))
        {
            blend = BLEND_NORMAL;
        }
        else
        {
            blend = BLEND_NORMAL;
            System.out.println("WARN: Unknown blend mode " + bstr);
        }
    }

    /**
     * Gets the colorEnd.
     * @return Returns a Color3f
     */
    public Color3f getColorEnd()
    {
        return colorEnd;
    }

    /**
     * Sets the colorEnd.
     * @param colorEnd The colorEnd to set
     */
    public void setColorEnd(Color3f colorEnd)
    {
        this.colorEnd = colorEnd;
    }

    /**
     * Gets the colorStart.
     * @return Returns a Color3f
     */
    public Color3f getColorStart()
    {
        return colorStart;
    }

    /**
     * Sets the colorStart.
     * @param colorStart The colorStart to set
     */
    public void setColorStart(Color3f colorStart)
    {
        this.colorStart = colorStart;
    }

    /**
     * Gets the fps.
     * @return Returns a float
     */
    public float getFps()
    {
        return fps;
    }

    /**
     * Sets the fps.
     * @param fps The fps to set
     */
    public void setFps(float fps)
    {
        this.fps = fps;
    }

    /**
     * Gets the frameEnd.
     * @return Returns a int
     */
    public int getFrameEnd()
    {
        return frameEnd;
    }

    /**
     * Sets the frameEnd.
     * @param frameEnd The frameEnd to set
     */
    public void setFrameEnd(int frameEnd)
    {
        this.frameEnd = frameEnd;
    }

    /**
     * Gets the frameStart.
     * @return Returns a int
     */
    public int getFrameStart()
    {
        return frameStart;
    }

    /**
     * Sets the frameStart.
     * @param frameStart The frameStart to set
     */
    public void setFrameStart(int frameStart)
    {
        this.frameStart = frameStart;
    }

    /**
     * Gets the lifeExp.
     * @return Returns a float
     */
    public float getLifeExp()
    {
        return lifeExp;
    }

    /**
     * Sets the lifeExp.
     * @param lifeExp The lifeExp to set
     */
    public void setLifeExp(float lifeExp)
    {
        this.lifeExp = lifeExp;
    }

    /**
     * Gets the mass.
     * @return Returns a float
     */
    public float getMass()
    {
        return mass;
    }

    /**
     * Sets the mass.
     * @param mass The mass to set
     */
    public void setMass(float mass)
    {
        this.mass = mass;
    }

    /**
     * Gets the randvel.
     * @return Returns a float
     */
    public float getRandvel()
    {
        return randvel;
    }

    /**
     * Sets the randvel.
     * @param randvel The randvel to set
     */
    public void setRandvel(float randvel)
    {
        this.randvel = randvel;
    }

    /**
     * Gets the sizeEnd.
     * @return Returns a float
     */
    public float getSizeEnd()
    {
        return sizeEnd;
    }

    /**
     * Sets the sizeEnd.
     * @param sizeEnd The sizeEnd to set
     */
    public void setSizeEnd(float sizeEnd)
    {
        this.sizeEnd = sizeEnd;
    }

    /**
     * Gets the sizeEnd_y.
     * @return Returns a float
     */
    public float getSizeEnd_y()
    {
        return sizeEnd_y;
    }

    /**
     * Sets the sizeEnd_y.
     * @param sizeEnd_y The sizeEnd_y to set
     */
    public void setSizeEnd_y(float sizeEnd_y)
    {
        this.sizeEnd_y = sizeEnd_y;
    }

    /**
     * Gets the sizeStart.
     * @return Returns a float
     */
    public float getSizeStart()
    {
        return sizeStart;
    }

    /**
     * Sets the sizeStart.
     * @param sizeStart The sizeStart to set
     */
    public void setSizeStart(float sizeStart)
    {
        this.sizeStart = sizeStart;
    }

    /**
     * Gets the sizeStart_y.
     * @return Returns a float
     */
    public float getSizeStart_y()
    {
        return sizeStart_y;
    }

    /**
     * Sets the sizeStart_y.
     * @param sizeStart_y The sizeStart_y to set
     */
    public void setSizeStart_y(float sizeStart_y)
    {
        this.sizeStart_y = sizeStart_y;
    }

    /**
     * Gets the spread.
     * @return Returns a float
     */
    public float getSpread()
    {
        return spread;
    }

    /**
     * Sets the spread.
     * @param spread The spread to set
     */
    public void setSpread(float spread)
    {
        this.spread = spread;
    }

    /**
     * Gets the texture.
     * @return Returns a String
     */
    public String getTexture()
    {
        return texture;
    }

    /**
     * Sets the texture.
     * @param texture The texture to set
     */
    public void setTexture(String texture)
    {
        this.texture = texture;
    }

    /**
     * Gets the velocity.
     * @return Returns a float
     */
    public float getVelocity()
    {
        return velocity;
    }

    /**
     * Sets the velocity.
     * @param velocity The velocity to set
     */
    public void setVelocity(float velocity)
    {
        this.velocity = velocity;
    }

    /**
     * Gets the xgrid.
     * @return Returns a int
     */
    public int getXgrid()
    {
        return xgrid;
    }

    /**
     * Sets the xgrid.
     * @param xgrid The xgrid to set
     */
    public void setXgrid(int xgrid)
    {
        this.xgrid = xgrid;
    }

    /**
     * Gets the ygrid.
     * @return Returns a int
     */
    public int getYgrid()
    {
        return ygrid;
    }

    /**
     * Sets the ygrid.
     * @param ygrid The ygrid to set
     */
    public void setYgrid(int ygrid)
    {
        this.ygrid = ygrid;
    }

    /**
     * Gets the particleRot.
     * @return Returns a float
     */
    public float getParticleRot()
    {
        return particleRot;
    }

    /**
     * Sets the particleRot.
     * @param particleRot The particleRot to set
     */
    public void setParticleRot(float particleRot)
    {
        this.particleRot = particleRot;
    }

    /**
     * Gets the xsize.
     * @return Returns a float
     */
    public float getXsize()
    {
        return xsize;
    }

    /**
     * Sets the xsize.
     * @param xsize The xsize to set
     */
    public void setXsize(float xsize)
    {
        this.xsize = xsize;
    }

    /**
     * Gets the ysize.
     * @return Returns a float
     */
    public float getYsize()
    {
        return ysize;
    }

    /**
     * Sets the ysize.
     * @param ysize The ysize to set
     */
    public void setYsize(float ysize)
    {
        this.ysize = ysize;
    }

    /**
     * Gets the render.
     * @return Returns a int
     */
    public int getRender()
    {
        return render;
    }

    /**
     * Sets the render.
     * @param render The render to set
     */
    public void setRender(int render)
    {
        this.render = render;
    }

    public void setRender(String r)
    {
        if (r.equalsIgnoreCase("normal"))
        {
            render = RENDER_NORMAL;
        }
        else if (r.equalsIgnoreCase("motion_blur"))
        {
            render = RENDER_MOTION_BLUR;
        }
        else if (r.equalsIgnoreCase("linked"))
        {
            render = RENDER_LINKED;
        }
        else if (r.equalsIgnoreCase("aligned_to_world_z"))
        {
            render = RENDER_ALIGNED_TO_WORLD_Z;
        }
        else
        {
            System.out.println("WARN: Unknown render type " + r);
            render = RENDER_NORMAL;
        }
    }

    /**
     * Gets the drag.
     * @return Returns a float
     */
    public float getDrag()
    {
        return drag;
    }

    /**
     * Sets the drag.
     * @param drag The drag to set
     */
    public void setDrag(float drag)
    {
        this.drag = drag;
    }

    /**
     * Gets the grav.
     * @return Returns a float
     */
    public float getGrav()
    {
        return grav;
    }

    /**
     * Sets the grav.
     * @param grav The grav to set
     */
    public void setGrav(float grav)
    {
        this.grav = grav;
    }

    /**
     * Gets the p2pType.
     * @return Returns a int
     */
    public int getP2pType()
    {
        return p2pType;
    }

    /**
     * Sets the p2pType.
     * @param p2pType The p2pType to set
     */
    public void setP2pType(int p2pType)
    {
        this.p2pType = p2pType;
    }

    public void setP2pType(String t)
    {
        if (t.equalsIgnoreCase("bezier"))
        {
            p2pType = P2P_TYPE_BEZIER;
        }
        else if (t.equalsIgnoreCase("gravity"))
        {
            p2pType = P2P_TYPE_GRAVITY;
        }
        else
        {
            System.out.println("WARN: Unknown p2p_type " + t);
            p2pType = P2P_TYPE_BEZIER;
        }
    }

    public void dumpSingle(StringBuffer sb)
    {
        super.dumpSingle(sb);
        sb.append("  colorStart ");
        dump(sb, colorStart);
        sb.append('\n');
        sb.append("  colorEnd ");
        dump(sb, colorEnd);
        sb.append('\n');
        sb.append("  alphaStart ").append(alphaStart).append('\n');
        sb.append("  alphaEnd ").append(alphaEnd).append('\n');
        sb.append("  sizeStart ").append(sizeStart).append('\n');
        sb.append("  sizeEnd ").append(sizeEnd).append('\n');
        sb.append("  sizeStart_y ").append(sizeStart_y).append('\n');
        sb.append("  sizeEnd_y ").append(sizeEnd_y).append('\n');
        // TODO finish rest of properties

    }

    public TransformGroup createSingleTG(URL base, boolean metallic)
    {
        TransformGroup tg = super.createSingleTG(base, metallic);

        EmitterBehavior eb = new EmitterBehavior(this, tg, createAppearance(base));

        eb.setUserData(new NWNUserData(getName() + EMITTER_POSTFIX, eb));
        tg.addChild(eb);
        return tg;
    }

    private static Color3f black = new Color3f(0, 0, 0);

    private Appearance createAppearance(URL base)
    {
        Appearance ap = new Appearance();
        Texture tex = TrimeshGeomNode.findTexture(base, texture);

        ap.setTexture(tex);

        Material m = new Material(black, black, black, black, 64);

        ap.setMaterial(m);

        TextureAttributes texAttr = new TextureAttributes();

        texAttr.setTextureMode(TextureAttributes.MODULATE);
        texAttr.setPerspectiveCorrectionMode(TextureAttributes.FASTEST);
        ap.setTextureAttributes(texAttr);

        TransparencyAttributes ta = new TransparencyAttributes();

        ta.setTransparencyMode(TransparencyAttributes.BLENDED);
        if (blend == BLEND_LIGHTEN)
        {
            ta.setSrcBlendFunction(TransparencyAttributes.BLEND_SRC_ALPHA);
            ta.setDstBlendFunction(TransparencyAttributes.BLEND_ONE);
        }
        else
        {
            ta.setSrcBlendFunction(TransparencyAttributes.BLEND_SRC_ALPHA);
            ta.setDstBlendFunction(TransparencyAttributes.BLEND_ONE_MINUS_SRC_ALPHA);
        }
        ap.setTransparencyAttributes(ta);
        return ap;
    }

    public static final int BLEND_LIGHTEN = 1;
    public static final int BLEND_NORMAL = 2;

    public static final int RENDER_NORMAL = 1;
    public static final int RENDER_MOTION_BLUR = 2;
    public static final int RENDER_LINKED = 3;
    public static final int RENDER_ALIGNED_TO_WORLD_Z = 4;

    public static final int P2P_TYPE_BEZIER = 1;
    public static final int P2P_TYPE_GRAVITY = 2;

    public static final int UPDATE_FOUNTAIN = 1;
    public static final int UPDATE_SINGLE = 2;

    public static final String EMITTER_POSTFIX = "%EMITTER";

    /**
     * Gets the inherit.
     * @return Returns a int
     */
    public int getInherit()
    {
        return inherit;
    }

    /**
     * Sets the inherit.
     * @param inherit The inherit to set
     */
    public void setInherit(int inherit)
    {
        this.inherit = inherit;
    }

    /**
     * Gets the inherit_local.
     * @return Returns a int
     */
    public int getInherit_local()
    {
        return inherit_local;
    }

    /**
     * Sets the inherit_local.
     * @param inherit_local The inherit_local to set
     */
    public void setInherit_local(int inherit_local)
    {
        this.inherit_local = inherit_local;
    }

    /**
     * Gets the inherit_part.
     * @return Returns a int
     */
    public int getInherit_part()
    {
        return inherit_part;
    }

    /**
     * Sets the inherit_part.
     * @param inherit_part The inherit_part to set
     */
    public void setInherit_part(int inherit_part)
    {
        this.inherit_part = inherit_part;
    }

    /**
     * Gets the update.
     * @return Returns a int
     */
    public int getUpdate()
    {
        return update;
    }

    /**
     * Sets the update.
     * @param update The update to set
     */
    public void setUpdate(int update)
    {
        this.update = update;
    }

    public void setUpdate(String u)
    {
        if (u.equalsIgnoreCase("fountain"))
        {
            update = UPDATE_FOUNTAIN;
        }
        else if (u.equalsIgnoreCase("single"))
        {
            update = UPDATE_SINGLE;
        }
        else
        {
            System.err.println("WARN: Unknown update type " + u);
            update = UPDATE_FOUNTAIN;
        }
    }

}
