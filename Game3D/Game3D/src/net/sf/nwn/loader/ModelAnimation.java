package net.sf.nwn.loader;


import javax.media.j3d.*;
import java.util.*;


public class ModelAnimation
    implements java.io.Serializable
{
    private String name;
    private String model;
    private float length;
    private float transTime;
    private String animRoot;
    private ArrayList /*AnimNode*/ nodes = new ArrayList();
    // events ?

    public ModelAnimation(String aName, String master)
    {
        name = aName.toLowerCase();
        model = master;
    }

    public String getName()
    {
        return name;
    }

    public String toString()
    {
        return getName();
    }

    /**
     * Gets the length.
     * @return Returns a float
     */
    public float getLength()
    {
        return length;
    }

    /**
     * Sets the length.
     * @param length The length to set
     */
    public void setLength(float length)
    {
        this.length = length;
    }

    /**
     * Gets the transTime.
     * @return Returns a float
     */
    public float getTransTime()
    {
        return transTime;
    }

    /**
     * Sets the transTime.
     * @param transTime The transTime to set
     */
    public void setTransTime(float transTime)
    {
        this.transTime = transTime;
    }

    /**
     * Gets the animRoot.
     * @return Returns a String
     */
    public String getAnimRoot()
    {
        return animRoot;
    }

    /**
     * Sets the animRoot.
     * @param animRoot The animRoot to set
     */
    public void setAnimRoot(String animRoot)
    {
        this.animRoot = animRoot;
    }

    /**
     * Gets the masterName.
     * @return Returns a String
     */
    public String getModel()
    {
        return model;
    }

    /**
     * Sets the masterName.
     * @param masterName The masterName to set
     */
    public void setModel(String masterName)
    {
        this.model = masterName;
    }

    public void addNode(AnimNode node)
    {
        nodes.add(node);
    }

    public void dump(StringBuffer sb)
    {
        sb.append("newanim ").append(getName()).append(" ").append(getModel()).append("\n");
        sb.append("  length ").append(getLength()).append("\n");
        sb.append("  transtime ").append(getTransTime()).append("\n");
        sb.append("animroot ").append(getAnimRoot()).append("\n");
        Iterator it = nodes.iterator();

        while (it.hasNext())
        {
            ((AnimNode) it.next()).dump(sb);
        }
        sb.append("doneanim ").append(getName()).append(" ").append(getModel()).append("\n\n");
    }

    public void fixupEmitters(GeomNode root)
    {
        for (int i = 0; i < nodes.size(); i++)
        {
            if (nodes.get(i) instanceof AnimEmitterNode)
            {
                AnimEmitterNode aen = (AnimEmitterNode) nodes.get(i);
                EmitterNode em = (EmitterNode) root.findChild(aen.getName());

                if (em == null)
                {
                    System.out.println("WARN : Missing EmitterBehavior for " + aen.getName());
                    continue;
                }
                aen.fixupEmitter(em);
            }
        }
    }

    private static Transform3D t3 = new Transform3D();

    public void update(Map named, Map emitters, float next, boolean interpolate)
    {

        if (next > getLength())
            return;

        for (int i = 0; i < nodes.size(); i++)
        {

            AnimNode an = (AnimNode) nodes.get(i);

            if (an.getParent().equalsIgnoreCase("null"))
                continue;
            TransformGroup tg = (TransformGroup) named.get(an.getName());

            if (tg == null)
            {
                //System.out.println("WARN : Missing TransformGroup for " + an.getName() );
                continue;
            }

            NWNUserData data = (NWNUserData) tg.getUserData();

            // fixup after cloning
            if (data.owner != tg)
            {
                tg.setUserData(new NWNUserData(data.name, tg));
            }

            tg.getTransform(t3);
            an.update(t3, data, next, interpolate ? getTransTime() : 0);
            tg.setTransform(t3);

            if (an instanceof AnimEmitterNode)
            {
                EmitterBehavior eb = (EmitterBehavior) emitters.get(an.getName() + EmitterNode.EMITTER_POSTFIX);

                if (eb == null)
                {
                    System.out.println("WARN : Missing EmitterBehavior for " + an.getName());
                    continue;
                }
                ((AnimEmitterNode) an).update(eb, next);
            }
        }
    }

    private static final long serialVersionUID = 1;

}
