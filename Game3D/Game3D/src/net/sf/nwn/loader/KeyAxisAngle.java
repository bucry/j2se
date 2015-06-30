package net.sf.nwn.loader;


import javax.vecmath.AxisAngle4d;
import javax.vecmath.AxisAngle4f;
import javax.vecmath.Vector3f;


public class KeyAxisAngle extends AxisAngle4f
{
    private float key;

    /**
     * Constructor for KeyAxisAngle.
     * @param f
     * @param f_0_
     * @param f_1_
     * @param f_2_
     */
    public KeyAxisAngle(float f, float f_0_, float f_1_, float f_2_, float aKey)
    {
        super(f, f_0_, f_1_, f_2_);
        key = aKey;
    }

    /**
     * Constructor for KeyAxisAngle.
     * @param axisangle4f_3_
     */
    public KeyAxisAngle(AxisAngle4f a, float aKey)
    {
        super(a);
    }

    public KeyAxisAngle(KeyAxisAngle a)
    {
        this(a, a.key);
    }

    /**
     * Constructor for KeyAxisAngle.
     * @param vector3f
     * @param f
     */
    public KeyAxisAngle(Vector3f vector3f, float f, float aKey)
    {
        super(vector3f, f);
        key = aKey;
    }

    /**
     * Constructor for KeyAxisAngle.
     */
    public KeyAxisAngle()
    {
        super();
    }

    public void set(KeyQuat kq)
    {
        super.set(kq);
        setKey(kq.getKey());
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
        return key + "  " + x + " " + y + " " + z + " " + angle;
    }

}
