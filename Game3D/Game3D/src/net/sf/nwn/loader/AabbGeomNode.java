package net.sf.nwn.loader;


import java.net.URL;
import java.util.*;
import javax.media.j3d.*;
import javax.vecmath.*;


public class AabbGeomNode extends TrimeshGeomNode
{
    private ArrayList tree = new ArrayList();

    public AabbGeomNode(GeomNode parent)
    {
        super(parent);
    }

    public String getType()
    {
        return "aabb";
    }

    public void addAabbEntry(float x1, float y1, float z1, float x2, float y2, float z2, int aFace)
    {
        tree.add(new AabbEntry(x1, y1, z1, x2, y2, z2, aFace));
    }

    public GeometryArray createTreeGeometry()
    {
        LineArray la = new LineArray(
                tree.size() * 12,
                IndexedLineArray.COORDINATES
            );
        Point3f c = new Point3f();

        for (int i = 0; i < tree.size(); i++)
        {
            Point3f a = ((AabbEntry) tree.get(i)).getCornerA();
            Point3f b = ((AabbEntry) tree.get(i)).getCornerB();
            int io = i * 12;

            c.set(a.x, a.y, a.z);
            la.setCoordinate(io, c);
            la.setCoordinate(io + 2, c);
            la.setCoordinate(io + 4, c);

            c.set(b.x, a.y, a.z);
            la.setCoordinate(io + 1, c);
            c.set(a.x, b.y, a.z);
            la.setCoordinate(io + 3, c);
            c.set(a.x, a.y, b.z);
            la.setCoordinate(io + 5, c);

            c.set(b.x, b.y, b.z);
            la.setCoordinate(io + 6, c);
            la.setCoordinate(io + 8, c);
            la.setCoordinate(io + 10, c);

            c.set(a.x, b.y, b.z);
            la.setCoordinate(io + 7, c);
            c.set(b.x, a.y, b.z);
            la.setCoordinate(io + 9, c);
            c.set(b.x, b.y, a.z);
            la.setCoordinate(io + 11, c);

        }
        return la;
    }

    public Appearance createAppearance(URL base)
    {
        Appearance ap = new Appearance();
        Material m = new Material();

        m.setEmissiveColor(0, 0, 1);
        m.setAmbientColor(0, 0, 0);
        m.setDiffuseColor(0, 0, 0);
        m.setSpecularColor(0, 0, 0);
        ap.setMaterial(m);
        PolygonAttributes pa = new PolygonAttributes(PolygonAttributes.POLYGON_LINE,
                PolygonAttributes.CULL_NONE, 0);

        ap.setPolygonAttributes(pa);
        ap.setRenderingAttributes(new RenderingAttributes(false, true, RenderingAttributes.ALWAYS, 0));

        ap.setCapability(Appearance.ALLOW_RENDERING_ATTRIBUTES_READ);
        ap.clearCapabilityIsFrequent(Appearance.ALLOW_RENDERING_ATTRIBUTES_READ);
        ap.getRenderingAttributes().setCapability(RenderingAttributes.ALLOW_VISIBLE_WRITE);
        ap.getRenderingAttributes().clearCapabilityIsFrequent(RenderingAttributes.ALLOW_VISIBLE_WRITE);
        ap.getRenderingAttributes().setVisible(false);
        TransparencyAttributes ta = new TransparencyAttributes();

        ta.setTransparency(0.75f);
        ta.setTransparencyMode(TransparencyAttributes.BLENDED);
        ta.setSrcBlendFunction(TransparencyAttributes.BLEND_SRC_ALPHA);
        ta.setDstBlendFunction(TransparencyAttributes.BLEND_ONE_MINUS_SRC_ALPHA);
        ap.setTransparencyAttributes(ta);

        LineAttributes la = new LineAttributes();

        la.setLineWidth(2);
        ap.setLineAttributes(la);
        return ap;
    }

    public Shape3D createShape(URL base, boolean metallic)
    {
        Walkmesh shape = new Walkmesh(createGeometry(false), createAppearance(base));

        shape.setUserData(new NWNUserData("%WALKMESH%", shape));
        return shape;
    }

}
