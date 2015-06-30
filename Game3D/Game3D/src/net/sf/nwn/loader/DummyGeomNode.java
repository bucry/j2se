package net.sf.nwn.loader;


import javax.vecmath.AxisAngle4f;
import javax.vecmath.Color3f;
import javax.vecmath.Vector3f;

import javax.media.j3d.*;

import com.sun.j3d.utils.geometry.Text2D;

import java.awt.Font;
import java.net.URL;
import java.util.Iterator;


public class DummyGeomNode extends GeomNode
{
    private Vector3f position;
    private AxisAngle4f orientation;
    private Color3f wirecolor;
    private float scale = 1.0f;

    public DummyGeomNode(GeomNode parent)
    {
        super(parent);
    }

    public String getType()
    {
        return "dummy";
    }

    /**
     * Gets the orientation.
     * @return Returns a AxisAngle4f
     */
    public AxisAngle4f getOrientation()
    {
        return orientation;
    }

    /**
     * Sets the orientation.
     * @param orientation The orientation to set
     */
    public void setOrientation(AxisAngle4f orientation)
    {
        this.orientation = orientation;
    }

    /**
     * Gets the position.
     * @return Returns a Vector3f
     */
    public Vector3f getPosition()
    {
        return position;
    }

    /**
     * Sets the position.
     * @param position The position to set
     */
    public void setPosition(Vector3f position)
    {
        this.position = position;
    }

    /**
     * Gets the wirecolor.
     * @return Returns a Color3f
     */
    public Color3f getWirecolor()
    {
        return wirecolor;
    }

    /**
     * Sets the wirecolor.
     * @param wirecolor The wirecolor to set
     */
    public void setWirecolor(Color3f wirecolor)
    {
        this.wirecolor = wirecolor;
    }

    public void dumpSingle(StringBuffer sb)
    {
        super.dumpSingle(sb);
        if (position != null)
        {
            sb.append("  position ");
            dump(sb, position);
            sb.append("\n");
        }
        if (orientation != null)
        {
            sb.append("  orientation ");
            dump(sb, orientation);
            sb.append("\n");
        }
        if (wirecolor != null)
        {
            sb.append("  wirecolor ");
            dump(sb, wirecolor);
            sb.append("\n");
        }
        if (scale != 1.0)
        {
            sb.append("  scale ").append(scale).append("\n");
        }
    }

    public TransformGroup createSingleTG(URL base, boolean metallic)
    {
        Transform3D t3 = new Transform3D();

        if (getOrientation() != null)
            t3.set(getOrientation());

        if (getPosition() != null)
        {
            t3.setTranslation(getPosition());
        }
        if (scale != 1.0)
        {
            t3.setScale(scale);
        }

        TransformGroup tg = new TransformGroup(t3);

        tg.setUserData(new NWNUserData(getName(), tg));
        tg.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
        tg.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

        return tg;
    }

    /**
     * Gets the scale.
     * @return Returns a float
     */
    public float getScale()
    {
        return scale;
    }

    /**
     * Sets the scale.
     * @param scale The scale to set
     */
    public void setScale(float scale)
    {
        this.scale = scale;
    }

}
