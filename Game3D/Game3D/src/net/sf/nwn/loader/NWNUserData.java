package net.sf.nwn.loader;


public class NWNUserData
    implements java.io.Serializable
{
    public String name;
    public javax.vecmath.Quat4f oldOrientation = new javax.vecmath.Quat4f();
    public javax.vecmath.Vector3f oldPosition = new javax.vecmath.Vector3f();
    public javax.media.j3d.Node owner;

    public NWNUserData(String aName, javax.media.j3d.Node anOwner)
    {
        name = aName;
        owner = anOwner;
    }

    public String toString()
    {
        return name;
    }

    private static final long serialVersionUID = 1;
}
