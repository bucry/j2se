package net.sf.nwn.loader;


import javax.vecmath.Quat4f;


public class KeyQuat extends Quat4f
{
    private float key;

    public KeyQuat()
    {
    }

    public KeyQuat(KeyAxisAngle kaa)
    {
        super();
        set(kaa);
    }

    public void set(KeyAxisAngle kaa)
    {
        super.set(kaa);
        key = kaa.getKey();
    }

    /**
     * Gets the key.
     * @return Returns a float
     */
    public float getKey()
    {
        return key;
    }

    /**
     * Sets the key.
     * @param key The key to set
     */
    public void setKey(float key)
    {
        this.key = key;
    }

    public String toString()
    {
        return key + "  " + x + " " + y + " " + z + " " + w;
    }

}
