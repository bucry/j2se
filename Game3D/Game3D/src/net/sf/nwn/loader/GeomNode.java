package net.sf.nwn.loader;


import javax.media.j3d.*;
import java.util.*;
import java.net.URL;


public abstract class GeomNode extends Node
{
    public GeomNode(GeomNode parent)
    {
        super(parent);
    }

    public GeomNode getSupernode()
    {
        return (GeomNode) supernode;
    }

    public abstract TransformGroup createSingleTG(URL base, boolean metallic);

    public TransformGroup createAllTG(URL base, boolean metallic)
    {
        TransformGroup tg = createSingleTG(base, metallic);

        Iterator it = children.values().iterator();

        while (it.hasNext())
        {
            tg.addChild(((GeomNode) it.next()).createAllTG(base, metallic));
        }
        return tg;
    }

}
