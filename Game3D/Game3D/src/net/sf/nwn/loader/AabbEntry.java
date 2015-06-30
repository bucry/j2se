package net.sf.nwn.loader;


import javax.vecmath.*;


public class AabbEntry
{
    private Point3f cornerA = new Point3f();
    private Point3f cornerB = new Point3f();
    private int face;

    public AabbEntry()
    {
    }

    public AabbEntry(float x1, float y1, float z1, float x2, float y2, float z2, int aFace)
    {
        setCornerA(x1, y1, z1);
        setCornerB(x2, y2, z2);
        setFace(aFace);
    }

    public void setCornerA(float x, float y, float z)
    {
        cornerA.x = x;
        cornerA.y = y;
        cornerA.z = z;
    }

    public Point3f getCornerA()
    {
        return cornerA;
    }

    public void setCornerB(float x, float y, float z)
    {
        cornerB.x = x;
        cornerB.y = y;
        cornerB.z = z;
    }

    public Point3f getCornerB()
    {
        return cornerB;
    }

    public void setFace(int face)
    {
        this.face = face;
    }

    public int getFace()
    {
        return face;
    }

}
