package net.sf.nwn.loader;


import javax.media.j3d.*;
import javax.vecmath.*;

import java.util.*;


public class AnimNode
    implements java.io.Serializable
{
    private String name;
    private String parent;

    private float[] orientations = new float[15];
    private float[] positions = null;
    private int oTop = 0;
    private int pTop = 0;

    public AnimNode(String aName)
    {
        name = aName.toLowerCase();
    }

    /**
     * Gets the name.
     * @return Returns a String
     */
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
        this.name = name;
    }

    /**
     * Gets the parent.
     * @return Returns a String
     */
    public String getParent()
    {
        return parent;
    }

    /**
     * Sets the parent.
     * @param parent The parent to set
     */
    public void setParent(String parent)
    {
        this.parent = parent;
    }

    public void addPositionKey(KeyVector v)
    {
        if (positions == null)
            positions = new float[12];
        int index = pTop * 4;

        if (index >= positions.length)
        {
            float[] npositions = new float[index + 12];

            System.arraycopy(positions, 0, npositions, 0, index);
            positions = npositions;
        }
        positions[index] = v.getKey();
        positions[index + 1] = v.x;
        positions[index + 2] = v.y;
        positions[index + 3] = v.z;
        pTop++;
    }

    private static KeyAxisAngle tempKaa = new KeyAxisAngle();

    public void addOrientationKey(float key, float x, float y, float z, float a)
    {
        tempKaa.set(x, y, z, a);
        tempKaa.setKey(key);
        addOrientationKey(tempKaa);
    }

    public void addOrientationKey(KeyAxisAngle a)
    {
        if (a.x == 0 && a.y == 0 && a.z == 0)
        {
            a.z = 1;
        }
        kq.setKey(a.getKey());
        kq.set(a);

        kq.normalize();

        int index = oTop * 5;

        /*        if ( index > 0 )
         {
         if ( kq.w == orientations[index-4] &&
         kq.x == orientations[index-3] &&
         kq.y == orientations[index-2] &&
         kq.z == orientations[index-1] )
         {
         return;
         }
         }*/



        if (index >= orientations.length)
        {
            float[] norientations = new float[index + 15];

            System.arraycopy(orientations, 0, norientations, 0, index);
            orientations = norientations;
        }
        orientations[index] = kq.getKey();
        orientations[index + 1] = kq.w;
        orientations[index + 2] = kq.x;
        orientations[index + 3] = kq.y;
        orientations[index + 4] = kq.z;
        oTop++;
    }

    public void dump(StringBuffer sb)
    {
        KeyAxisAngle kaa = new KeyAxisAngle();

        sb.append("node dummy ").append(getName()).append("\n");
        sb.append("  parent ").append(getParent()).append("\n");

        sb.append("  positionkey\n");
        for (int i = 0; i < pTop; i++)
        {
            getPosition(i, kv);
            sb.append("    ").append(kv).append("\n");
        }
        sb.append("  endlist\n");

        sb.append("  orientationkey\n");
        for (int i = 0; i < oTop; i++)
        {
            getOrientation(i, kq);
            kaa.set(kq);
            sb.append("    ").append(kaa).append("\n");
        }
        sb.append("  endlist\n");

        sb.append("endnode\n");
    }

    private void getOrientation(int index, KeyQuat tmpQ)
    {
        index *= 5;
        tmpQ.setKey(orientations[index]);
        tmpQ.w = orientations[index + 1];
        tmpQ.x = orientations[index + 2];
        tmpQ.y = orientations[index + 3];
        tmpQ.z = orientations[index + 4];
    }

    private void getOrientation(int index, Quat4f tmpQ)
    {
        index *= 5;
        tmpQ.w = orientations[index + 1];
        tmpQ.x = orientations[index + 2];
        tmpQ.y = orientations[index + 3];
        tmpQ.z = orientations[index + 4];
    }

    private void getPosition(int index, KeyVector tmpV)
    {
        index *= 4;
        tmpV.setKey(positions[index]);
        tmpV.x = positions[index + 1];
        tmpV.y = positions[index + 2];
        tmpV.z = positions[index + 3];
    }

    private void getPosition(int index, Vector3f tmpV)
    {
        index *= 4;
        tmpV.x = positions[index + 1];
        tmpV.y = positions[index + 2];
        tmpV.z = positions[index + 3];
    }

    public void trimSlack()
    {
        if (positions != null && positions.length > pTop * 4)
        {
            float[] npositions = new float[pTop * 4];

            System.arraycopy(positions, 0, npositions, 0, pTop * 4);
            positions = npositions;
        }

        if (orientations.length > oTop * 5)
        {
            float[] norientations = new float[oTop * 5];

            System.arraycopy(orientations, 0, norientations, 0, oTop * 5);
            orientations = norientations;
        }
    }

    private static Quat4f QTt = new Quat4f();
    private static Quat4f QTtt = new Quat4f();
    private static Quat4f QTp = new Quat4f();
    private static Quat4f QRt = new Quat4f();
    private static Quat4f Qdiff = new Quat4f();
    private static Quat4f Qzero = new Quat4f(0, 0, 0, 1);

    private static Vector3f VTt = new Vector3f();
    private static Vector3f VTp = new Vector3f();
    private static Vector3f VRt = new Vector3f();

    private static KeyQuat kq = new KeyQuat();
    private static KeyQuat kqp = new KeyQuat();
    private static KeyVector kv = new KeyVector();
    private static KeyVector kvp = new KeyVector();

    private boolean getTargetOrientation(float stage, Quat4f q1)
    {

        if (oTop == 0)
            return false;

        if (oTop == 1 || stage <= 0)
        {
            getOrientation(0, q1);
            return true;
        }
        else
        {
            for (int i = 1; i < oTop; i++)
            {
                getOrientation(i, kq);
                if (kq.getKey() >= stage)
                {
                    getOrientation(i - 1, kqp);

                    q1.interpolate(kqp, kq,
                        (stage - kqp.getKey()) / (kq.getKey() - kqp.getKey())
                    );
                    q1.normalize();
                    return true;
                }
            }
            System.err.println("Cannot interpolate orientation " + getName() + " " + stage);
            getOrientation(oTop - 1, q1);
            return true;
        }
    }

    private Quat4f getRealOrientation(float t, float tt, Quat4f Qo)
    {
        if (oTop == 0)
            return null;

        if (!getTargetOrientation(t, QTt))
            return null;

        if (t + EPSILON >= tt)
        {
            return QTt;
        }

        getOrientation(0, kq);

        Qdiff.inverse(kq);
        Qdiff.mul(Qo, Qdiff);
        Qdiff.interpolate(Qzero, t / tt);

        QRt.mul(Qdiff, QTt);
        QRt.normalize();

        return QRt;
    }

    private boolean getTargetPosition(float t, Vector3f v1)
    {
        if (positions == null)
            return false;

        if (pTop == 1)
        {
            getPosition(0, v1);
            return true;
        }

        for (int i = 1; i < pTop; i++)
        {
            getPosition(i, kv);

            if (kv.getKey() >= t)
            {
                getPosition(i - 1, kvp);

                v1.interpolate(kvp, kv,
                    (t - kvp.getKey()) / (kv.getKey() - kvp.getKey())
                );
                return true;
            }
        }

        System.err.println("Cannot interpolate position " + getName() + " " + t);
        return false;
    }

    private Vector3f getRealPosition(float t, float tt, Vector3f Vo)
    {
        if (positions == null)
            return null;

        if (!getTargetPosition(t, VTt))
            return null;
        if (t + EPSILON >= tt)
        {
            return VTt;
        }

        getPosition(0, kv);
        VRt.sub(Vo, kv);
        VRt.scale(1 - t / tt);
        VRt.add(VTt);

        return VRt;
    }

    private static final float EPSILON = 0.0001f;

    // stage is guaranteed to be <= length
    public void update(Transform3D t3, NWNUserData data, float t, float tt)
    {
        if (t <= 0.001)
        {
            t3.get(data.oldOrientation, data.oldPosition);
        }

        Vector3f v = getRealPosition(t, tt, data.oldPosition);

        if (v != null)
        {
            t3.setTranslation(v);
        }

        Quat4f q = getRealOrientation(t, tt, data.oldOrientation);

        if (q != null)
        {
            t3.setRotation(q);
        }
    }

    /*
     
     Animation smoothing algoritm (I use word position here, but it
     is the same for orientation - interpolation means big circle 
     quaterion interpolation).
     
     When animation changes, we are at certain position O (original)
     Theoretic target animation position T(t) for time t is computed
     by linear interpolation between keyframes surrounding t.
     Real position R(t) for given t is computed in following manner, for
     transistion time tt.
     if t >= tt then 
     R(t) = T(t)
     else
     R(t) = T(t) + (O-T(0))*(1-t/tt)
     
     */

    private static final long serialVersionUID = 1;
}
