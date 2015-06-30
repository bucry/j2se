package net.sf.nwn.loader;


import javax.media.j3d.*;
import javax.swing.event.ListSelectionEvent;
import javax.vecmath.*;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import com.sun.j3d.utils.scenegraph.io.SceneGraphObjectReferenceControl;


public class ParticleCollection extends Shape3D
    implements com.sun.j3d.utils.scenegraph.io.SceneGraphIO
{
    private QuadArray quads;
    private float[] verts;

    public ParticleCollection()
    {
    }

    public ParticleCollection(int maxParticles, Appearance app)
    {
        super();
        setAppearance(app);

        quads = new QuadArray(maxParticles * 4,
                    QuadArray.COORDINATES |
                    QuadArray.COLOR_4 |
                    QuadArray.TEXTURE_COORDINATE_2 |
                    QuadArray.BY_REFERENCE |
                    QuadArray.INTERLEAVED);
        quads.setCapability(QuadArray.ALLOW_REF_DATA_WRITE);
        quads.setCapability(QuadArray.ALLOW_REF_DATA_READ);
        quads.setCapability(QuadArray.ALLOW_COUNT_READ);

        verts = new float[maxParticles * 4 * 9];
        quads.setInterleavedVertices(verts);
        setGeometry(quads);
        setBoundsAutoCompute(false);
        //setBounds(new BoundingSphere(new Point3d(0, 0, 0), 5));
        setBounds(new BoundingBox(new Point3d(-5, -5, -5), new Point3d(5, 5, 5)));
        setCapability(Shape3D.ALLOW_LOCAL_TO_VWORLD_READ);
        setCapability(Shape3D.ALLOW_GEOMETRY_READ);
        setCapability(Shape3D.ALLOW_GEOMETRY_WRITE);
    }

    private float xwOff;
    private float ywOff;
    private float zwOff;
    private float xhOff;
    private float yhOff;
    private float zhOff;

    private final Vector3f unit = new Vector3f();

    private final Transform3D localToWorld = new Transform3D();
    private final Transform3D localToWorldI = new Transform3D();
    private Transform3D eye;
    private final Transform3D rotT = new Transform3D();
    private final Vector3f eyeVector = new Vector3f();

    public void updateTransforms(Transform3D eyeToWorld)
    {
        getLocalToVworld(localToWorld);
        localToWorldI.invert(localToWorld);
        eye = eyeToWorld;
        eyeVector.set(0, 0, 1);
        eye.transform(eyeVector);
        eyeVector.normalize();
    }

    public Transform3D getLocalTransform()
    {
        return localToWorld;
    }

    public Transform3D getLocalTransformI()
    {
        return localToWorldI;
    }

    public Vector3f getEyeVector()
    {
        return eyeVector;
    }

    public void updateGeometry(GeometryUpdater up)
    {
        if (quads == null)
        {
            if (numGeometries() > 1)
            {
                System.out.println("Workaround for geometry insertion bug in scenegraph");
                setGeometry(getGeometry(1), 0);
                removeGeometry(1);
            }
            quads = (QuadArray) getGeometry(0);
            verts = quads.getInterleavedVertices();
        }
        quads.updateData(up);
    }

    public void updateParticle(int particle,
        float x, float y, float z,
        float w, float h, float rotation,
        float r, float g, float b, float a,
        float[] texcoords)
    {

        rotT.rotZ(rotation);

        unit.set(0.5f, 0, 0);
        rotT.transform(unit);
        eye.transform(unit);
        localToWorldI.transform(unit);

        xwOff = unit.x;
        ywOff = unit.y;
        zwOff = unit.z;

        unit.set(0, 0.5f, 0);
        rotT.transform(unit);
        eye.transform(unit);
        localToWorldI.transform(unit);

        xhOff = unit.x;
        yhOff = unit.y;
        zhOff = unit.z;

        float hxw = xwOff * w;
        float hxh = xhOff * h;
        float hyw = ywOff * w;
        float hyh = yhOff * h;
        float hzw = zwOff * w;
        float hzh = zhOff * h;

        int i = particle * 4 * 9;
        float[] v = verts;

        v[i++] = texcoords[0];
        v[i++] = texcoords[1];
        v[i++] = r;
        v[i++] = g;
        v[i++] = b;
        v[i++] = a;
        v[i++] = x - hxw + hxh;
        v[i++] = y - hyw + hyh;
        v[i++] = z - hzw + hzh;

        v[i++] = texcoords[2];
        v[i++] = texcoords[3];
        v[i++] = r;
        v[i++] = g;
        v[i++] = b;
        v[i++] = a;
        v[i++] = x - hxw - hxh;
        v[i++] = y - hyw - hyh;
        v[i++] = z - hzw - hzh;

        v[i++] = texcoords[4];
        v[i++] = texcoords[5];
        v[i++] = r;
        v[i++] = g;
        v[i++] = b;
        v[i++] = a;
        v[i++] = x + hxw - hxh;
        v[i++] = y + hyw - hyh;
        v[i++] = z + hzw - hzh;

        v[i++] = texcoords[6];
        v[i++] = texcoords[7];
        v[i++] = r;
        v[i++] = g;
        v[i++] = b;
        v[i++] = a;
        v[i++] = x + hxw + hxh;
        v[i++] = y + hyw + hyh;
        v[i++] = z + hzw + hzh;
    }

    public javax.media.j3d.Node cloneNode(boolean forceDuplicate)
    {
        ParticleCollection usc = new ParticleCollection();

        usc.duplicateNode(this, forceDuplicate);
        return usc;
    }

    public void duplicateNode(javax.media.j3d.Node originalNode, boolean forceDuplicate)
    {
        super.duplicateNode(originalNode, forceDuplicate);
        Shape3D oshape = (Shape3D) originalNode;
        QuadArray oq = (QuadArray) oshape.getGeometry();
        int maxParticles = oq.getVertexCount() / 4;

        quads = new QuadArray(maxParticles * 4,
                    QuadArray.COORDINATES |
                    QuadArray.COLOR_4 |
                    QuadArray.TEXTURE_COORDINATE_2 |
                    QuadArray.BY_REFERENCE |
                    QuadArray.INTERLEAVED);
        quads.setCapability(QuadArray.ALLOW_REF_DATA_WRITE);
        quads.setCapability(QuadArray.ALLOW_REF_DATA_READ);

        verts = new float[maxParticles * 4 * 9];
        quads.setInterleavedVertices(verts);
        setGeometry(quads);

    }

    /**
     * @see SceneGraphIO#createSceneGraphObjectReferences(SceneGraphObjectReferenceControl)
     */
    public void createSceneGraphObjectReferences(SceneGraphObjectReferenceControl arg0)
    {
    }

    /**
     * @see SceneGraphIO#readSceneGraphObject(DataInput)
     */
    public void readSceneGraphObject(DataInput arg0)
        throws IOException
    {
    }

    /**
     * @see SceneGraphIO#restoreSceneGraphObjectReferences(SceneGraphObjectReferenceControl)
     */
    public void restoreSceneGraphObjectReferences(SceneGraphObjectReferenceControl arg0)
    {

    }

    /**
     * @see SceneGraphIO#saveChildren()
     */
    public boolean saveChildren()
    {
        return false;
    }

    /**
     * @see SceneGraphIO#writeSceneGraphObject(DataOutput)
     */
    public void writeSceneGraphObject(DataOutput arg0)
        throws IOException
    {
    }

}
