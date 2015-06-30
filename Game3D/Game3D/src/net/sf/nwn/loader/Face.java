package net.sf.nwn.loader;


public class Face
{
    private int v1, v2, v3;
    private int t1, t2, t3;
    private int m1, m2;

    /**
     * Gets the v1.
     * @return Returns a int
     */
    public int getV1()
    {
        return v1;
    }

    /**
     * Sets the v1.
     * @param v1 The v1 to set
     */
    public void setV1(int v1)
    {
        this.v1 = v1;
    }

    /**
     * Gets the v2.
     * @return Returns a int
     */
    public int getV2()
    {
        return v2;
    }

    /**
     * Sets the v2.
     * @param v2 The v2 to set
     */
    public void setV2(int v2)
    {
        this.v2 = v2;
    }

    /**
     * Gets the v3.
     * @return Returns a int
     */
    public int getV3()
    {
        return v3;
    }

    /**
     * Sets the v3.
     * @param v3 The v3 to set
     */
    public void setV3(int v3)
    {
        this.v3 = v3;
    }

    /**
     * Gets the t1.
     * @return Returns a int
     */
    public int getT1()
    {
        return t1;
    }

    /**
     * Sets the t1.
     * @param t1 The t1 to set
     */
    public void setT1(int t1)
    {
        this.t1 = t1;
    }

    /**
     * Gets the t2.
     * @return Returns a int
     */
    public int getT2()
    {
        return t2;
    }

    /**
     * Sets the t2.
     * @param t2 The t2 to set
     */
    public void setT2(int t2)
    {
        this.t2 = t2;
    }

    /**
     * Gets the t3.
     * @return Returns a int
     */
    public int getT3()
    {
        return t3;
    }

    /**
     * Sets the t3.
     * @param t3 The t3 to set
     */
    public void setT3(int t3)
    {
        this.t3 = t3;
    }

    /**
     * Gets the m1.
     * @return Returns a int
     */
    public int getM1()
    {
        return m1;
    }

    /**
     * Sets the m1.
     * @param m1 The m1 to set
     */
    public void setM1(int m1)
    {
        this.m1 = m1;
    }

    /**
     * Gets the m2.
     * @return Returns a int
     */
    public int getM2()
    {
        return m2;
    }

    /**
     * Sets the m2.
     * @param m2 The m2 to set
     */
    public void setM2(int m2)
    {
        this.m2 = m2;
    }

    public String toString()
    {
        return v1 + " " + v2 + " " + v3 + "  " + m1 + "  " +
            t1 + " " + t2 + " " + t3 + "  " + m2;
    }

}
