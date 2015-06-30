package net.sf.nwn.loader;


public class KeyFloat
{

    private float key;
    private float val;

    public KeyFloat(float aVal, float aKey)
    {
        val = aVal;
        key = aKey;
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

    /**
     * Gets the val.
     * @return Returns a float
     */
    public float getVal()
    {
        return val;
    }

    /**
     * Sets the val.
     * @param val The val to set
     */
    public void setVal(float val)
    {
        this.val = val;
    }

}
