package net.sf.nwn.loader;


import java.util.*;


public final class AnimEmitterNode extends AnimNode
{

    private ArrayList birthratekey;

    public AnimEmitterNode(String aName)
    {
        super(aName);
    }

    public List getBirthratekeyList()
    {
        if (birthratekey == null)
        {
            birthratekey = new ArrayList();
        }
        return birthratekey;
    }

    private float interpolateFloat(ArrayList list, float t)
    {
        if (list.size() == 1)
        {
            return ((KeyFloat) list.get(0)).getVal();
        }

        for (int i = 1; i < list.size(); i++)
        {
            KeyFloat kf = (KeyFloat) list.get(i);

            if (kf.getKey() > t)
            {
                KeyFloat kfp = (KeyFloat) list.get(i - 1);

                return kfp.getVal() + (kf.getVal() - kfp.getVal()) *
                    ((t - kfp.getKey()) / (kf.getKey() - kfp.getKey()));
            }
        }
        System.err.println("Cannot interpolate orientation " + getName() + " " + t);
        return ((KeyFloat) list.get(list.size())).getVal();

    }

    public void update(EmitterBehavior eb, float t)
    {
        if (birthratekey != null && birthratekey.size() > 0)
        {
            eb.setBirthrate(interpolateFloat(birthratekey, t));
        }
    }

    public void fixupEmitter(EmitterNode em)
    {
        // this fixup is needed, because particle handler is allocated with
        // static number of particles - so initial birthrate has to be set to max
        // of all possible
        if (birthratekey != null)
        {
            for (int i = 0; i < birthratekey.size(); i++)
            {
                float val = ((KeyFloat) birthratekey.get(i)).getVal();

                if (val > em.getBirthrate())
                {
                    System.out.println("WARN: Fixing up birthrate in emitter node " + getName());
                    em.setBirthrate(val);
                }
            }
        }
    }

    private static final long serialVersionUID = 1;

}
