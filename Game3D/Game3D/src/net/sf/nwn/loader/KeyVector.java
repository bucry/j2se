
package net.sf.nwn.loader;


import javax.vecmath.Tuple3d;
import javax.vecmath.Tuple3f;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;


public class KeyVector extends Vector3f
{
    private float key;

    /**
     * Constructor for KeyVector.
     * @param f
     * @param f_0_
     * @param f_1_
     */
    public KeyVector(float f, float f_0_, float f_1_, float aKey)
    {
        super(f, f_0_, f_1_);
        key = aKey;
    }

    /**
     * Constructor for KeyVector.
     * @param vector3f_2_
     */
    public KeyVector(KeyVector v)
    {
        this(v, v.key);
    }

    /**
     * Constructor for KeyVector.
     * @param tuple3f
     */
    public KeyVector(Tuple3f tuple3f, float aKey)
    {
        super(tuple3f);
        key = aKey;
    }

    /**
     * Constructor for KeyVector.
     */
    public KeyVector()
    {
        super();
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
        return key + "  " + x + " " + y + " " + z;
    }

}
