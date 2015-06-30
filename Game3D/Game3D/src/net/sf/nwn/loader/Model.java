package net.sf.nwn.loader;


import java.net.URL;
import java.util.*;
import java.util.zip.*;

import javax.media.j3d.Group;


public class Model
{
    private String name;
    private String supermodelName;
    private float animationScale;
    private String classification;
    private GeomNode modelGeometry;
    private LinkedHashMap animations = new LinkedHashMap();

    public Model(String aName)
    {
        name = aName.toLowerCase();
    }

    public String getName()
    {
        return name;
    }

    /**
     * Sets the name.
     * @param name The name to set
     */
    public void setName(String name)
    {
        this.name = name.toLowerCase();
    }

    /**
     * Gets the animationScale.
     * @return Returns a float
     */
    public float getAnimationScale()
    {
        return animationScale;
    }

    /**
     * Sets the animationScale.
     * @param animationScale The animationScale to set
     */
    public void setAnimationScale(float animationScale)
    {
        this.animationScale = animationScale;
    }

    /**
     * Gets the classification.
     * @return Returns a String
     */
    public String getClassification()
    {
        return classification;
    }

    /**
     * Sets the classification.
     * @param classification The classification to set
     */
    public void setClassification(String classification)
    {
        this.classification = classification;
    }

    /**
     * Gets the supermodelName.
     * @return Returns a String
     */
    public String getSupermodelName()
    {
        return supermodelName;
    }

    /**
     * Sets the supermodelName.
     * @param supermodelName The supermodelName to set
     */
    public void setSupermodelName(String supermodelName)
    {
        this.supermodelName = supermodelName;
    }

    public Model getSupermodel(URL base, Map modelCache)
    {
        if (getSupermodelName() == null || getSupermodelName().equalsIgnoreCase("null"))
            return null;

        if (modelCache != null)
        {
            Model s = (Model) modelCache.get(getSupermodelName());

            if (s != null)
                return s;
        }
        try
        {
            java.io.InputStream is;

            try
            {
                is = new URL(base, getSupermodelName() + ".mdl").openStream();
            } catch (java.io.IOException e)
            {
                is = new GZIPInputStream(new URL(base, getSupermodelName() + ".mdl.gz").openStream());
            }

            Model m = ManualParser.sharedParser.reinit(is).definition();

            is.close();
            if (modelCache != null)
            {
                modelCache.put(getSupermodelName(), m);
            }
            return m;
        } catch (Exception exc)
        {
            exc.printStackTrace();
            return null;
        }
    }

    /**
     * Gets the modelGeometry.
     * @return Returns a ModelGeometry
     */
    public GeomNode getModelGeometry()
    {
        return modelGeometry;
    }

    /**
     * Sets the modelGeometry.
     * @param modelGeometry The modelGeometry to set
     */
    public void setModelGeometry(GeomNode modelGeometry)
    {
        this.modelGeometry = modelGeometry;
    }

    public void addModelAnimation(ModelAnimation anim)
    {
        animations.put(anim.getName(), anim);
    }

    public String toString()
    {
        return "Model(" + getName() + ") \n" + modelGeometry +
            "\n" + animations.values();
    }

    public void addNamed(Map m, Group g)
    {
        if (g.getUserData() instanceof String)
        {
            m.put((String) g.getUserData(), g);
        }

        for (int i = 0; i < g.numChildren(); i++)
        {
            javax.media.j3d.Node n = g.getChild(i);

            if (n instanceof Group)
                addNamed(m, (Group) n);
        }
    }

    public boolean isMetallic()
    {
        return (getSupermodelName() != null && !getSupermodelName().equalsIgnoreCase("null")) ||
            animations.size() > 0;
    }

    /*
     public javax.media.j3d.GeometryArray findGeometry(String name)
     {
     return ((TrimeshGeomNode) modelGeometry.findChild(name)).createGeometry();
     }


     public javax.media.j3d.Appearance findAppearance(URL base, String name)
     {
     return ((TrimeshGeomNode) modelGeometry.findChild(name)).createAppearance(base);
     }
     */
    public ModelAnimation findAnimation(String name)
    {
        return (ModelAnimation) animations.get(name);
    }

    public void getAllAnimations(Map map)
    {
        Iterator it = animations.entrySet().iterator();

        while (it.hasNext())
        {
            Map.Entry entry = (Map.Entry) it.next();

            map.put(getName() + ":" + entry.getKey(), entry.getValue());
        }
    }

    public void dump(StringBuffer sb)
    {
        sb.append("#Dumped by java\n");
        sb.append("newmodel ").append(getName()).append("\n");
        sb.append("setsupermodel ").append(getName()).append(" ").append(getSupermodelName()).append("\n");
        sb.append("classification ").append(getClassification()).append("\n");
        sb.append("setanimationscale ").append(getAnimationScale()).append("\n\n");
        sb.append("beginmodelgeom ").append(getName()).append("\n");
        modelGeometry.dump(sb);
        sb.append("endmodelgeom ").append(getName()).append("\n\n");
        Iterator it = animations.values().iterator();

        while (it.hasNext())
        {
            ((ModelAnimation) it.next()).dump(sb);
        }

        sb.append("donemodel ").append(getName()).append("\n");
    }

    public void fixupEmitters()
    {
        Iterator it = animations.values().iterator();

        while (it.hasNext())
        {
            ModelAnimation ma = (ModelAnimation) it.next();

            ma.fixupEmitters(modelGeometry);
        }
    }

}
