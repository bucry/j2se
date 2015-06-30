package net.sf.nwn.loader;


import java.awt.image.BufferedImage;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.zip.GZIPInputStream;

import com.sun.j3d.utils.geometry.*;
import com.sun.j3d.utils.image.*;
import javax.media.j3d.*;
import javax.swing.event.ListSelectionEvent;
import javax.vecmath.*;

import java.lang.ref.*;


public class TrimeshGeomNode extends DummyGeomNode
{
    private Color3f ambient;
    private Color3f diffuse;
    private Color3f specular;
    private Color3f emissive;
    private float shininess;
    private ArrayList /*Point3f*/ verts = new ArrayList();
    private ArrayList /*Face*/faces = new ArrayList();
    private ArrayList /*TexCoord2f*/ tverts = new ArrayList();
    private ArrayList /*Color3f*/ colors = new ArrayList();
    private ArrayList /*Vector3f*/ normals = new ArrayList();
    protected ArrayList /*Float*/ constraints;
    private String bitmap;
    private float alpha = 1;

    private WeakReference cachedGeometry;

    public TrimeshGeomNode(GeomNode parent)
    {
        super(parent);
    }

    public String getType()
    {
        return "trimesh";
    }

    /**
     * Gets the ambient.
     * @return Returns a Color3f
     */
    public Color3f getAmbient()
    {
        return ambient;
    }

    /**
     * Sets the ambient.
     * @param ambient The ambient to set
     */
    public void setAmbient(Color3f ambient)
    {
        this.ambient = ambient;
    }

    /**
     * Gets the diffuse.
     * @return Returns a Color3f
     */
    public Color3f getDiffuse()
    {
        return diffuse;
    }

    /**
     * Sets the diffuse.
     * @param diffuse The diffuse to set
     */
    public void setDiffuse(Color3f diffuse)
    {
        this.diffuse = diffuse;
    }

    /**
     * Gets the shininess.
     * @return Returns a float
     */
    public float getShininess()
    {
        return shininess;
    }

    /**
     * Sets the shininess.
     * @param shininess The shininess to set
     */
    public void setShininess(float shininess)
    {
        this.shininess = shininess;
    }

    /**
     * Gets the specular.
     * @return Returns a Color3f
     */
    public Color3f getSpecular()
    {
        return specular;
    }

    /**
     * Sets the specular.
     * @param specular The specular to set
     */
    public void setSpecular(Color3f specular)
    {
        this.specular = specular;
    }

    public void addVert(Point3f vert)
    {
        verts.add(vert);
    }

    public void addTvert(TexCoord2f tvert)
    {
        tverts.add(tvert);
    }

    public void addFace(int v1, int v2, int v3, int m1,
        int t1, int t2, int t3, int m2)
    {
        Face f = new Face();

        f.setV1(v1);
        f.setV2(v2);
        f.setV3(v3);
        f.setM1(m1);
        f.setT1(t1);
        f.setT2(t2);
        f.setT3(t3);
        f.setM2(m2);
        faces.add(f);
    }

    public void addColor(Color3f color)
    {
        colors.add(color);
    }

    /**
     * Gets the bitmap.
     * @return Returns a String
     */
    public String getBitmap()
    {
        return bitmap;
    }

    /**
     * Sets the bitmap.
     * @param bitmap The bitmap to set
     */
    public void setBitmap(String bitmap)
    {
        this.bitmap = bitmap;
    }

    TexCoord2f zeroTvert = new TexCoord2f(0, 0);
    public TexCoord2f getTvert(int index)
    {
        if (index < 0)
        {
            System.err.println("WARN: Negative tvert for part " + getName());
            return zeroTvert;
        }
        if (index >= tverts.size())
        {
            System.err.println("WARN: Requested tvert index out of range " + index + " >= " + tverts.size() +
                " for part " + getName());
            return zeroTvert;
        }
        return (TexCoord2f) tverts.get(index);
    }

    public void dumpSingle(StringBuffer sb)
    {
        super.dumpSingle(sb);

        if (ambient != null)
        {
            sb.append("  ambient ");
            dump(sb, ambient);
            sb.append("\n");
        }

        if (diffuse != null)
        {
            sb.append("  diffuse ");
            dump(sb, diffuse);
            sb.append("\n");
        }

        if (specular != null)
        {
            sb.append("  specular ");
            dump(sb, specular);
            sb.append("\n");
        }

        if (emissive != null && emissive != black)
        {
            sb.append(" selfillumcolor ");
            dump(sb, emissive);
            sb.append("\n");
        }

        sb.append("  shininess ").append(shininess).append("\n");

        if (bitmap != null)
        {
            sb.append("  bitmap ").append(bitmap).append("\n");
        }

        sb.append("  verts ").append(verts.size()).append("\n");
        for (int i = 0; i < verts.size(); i++)
        {
            sb.append("    ");
            dump(sb, (Point3f) verts.get(i));
            sb.append("\n");
        }

        sb.append("  faces ").append(faces.size()).append("\n");
        for (int i = 0; i < faces.size(); i++)
        {
            sb.append("    ");
            sb.append(faces.get(i));
            sb.append("\n");
        }

        sb.append("  tverts ").append(tverts.size()).append("\n");
        for (int i = 0; i < tverts.size(); i++)
        {
            sb.append("    ");
            dump(sb, (TexCoord2f) tverts.get(i));
            sb.append(" 0\n");
        }

        if (colors.size() > 0)
        {
            sb.append("  colors ").append(colors.size()).append("\n");
            for (int i = 0; i < colors.size(); i++)
            {
                sb.append("    ");
                dump(sb, (Color3f) colors.get(i));
                sb.append("\n");
            }
        }
    }

    //------------------------------------------------

    static class Vertex
    {
        public Point3f p;
        public TexCoord2f t;
        public int group;

        public Vertex(Point3f ap, TexCoord2f at, int aGroup)
        {
            p = ap;
            t = at;
            group = aGroup;
        }

        public boolean equals(Vertex v)
        {
            return p.equals(v.p) && t.equals(v.t) && group == v.group;
        }

        public boolean equals(Object o)
        {
            return equals((Vertex) o);
        }

        public int hashCode()
        {
            return p.hashCode() ^ t.hashCode();
        }

    }

    public void indexify()
    {
        HashMap map = new HashMap();
        ArrayList nverts = new ArrayList();
        ArrayList ntverts = new ArrayList();
        ArrayList ncolors = new ArrayList();
        ArrayList nconstraints = null;

        if (constraints != null)
            nconstraints = new ArrayList();

        normals.clear();

        ArrayList normalP = new ArrayList();
        ArrayList normalV = new ArrayList();
        ArrayList normalG = new ArrayList();

        int count = 0;

        Vector3f a = new Vector3f();
        Vector3f b = new Vector3f();

        for (int i = 0; i < faces.size(); i++)
        {
            Face f = (Face) faces.get(i);

            // compute face normal
            Point3f p1 = (Point3f) verts.get(f.getV1());
            Point3f p2 = (Point3f) verts.get(f.getV2());
            Point3f p3 = (Point3f) verts.get(f.getV3());

            Vector3f normal = new Vector3f();

            a.sub(p3, p2);
            b.sub(p1, p2);
            normal.cross(a, b);
            normal.normalize();
            if (Float.isNaN(normal.x))
            {
                System.err.println("WARN: Illegal normal for part " + getName());
                System.err.println(" face " + i);
                System.err.println(" p1=" + f.getV1() + "(" + p1 + ")");
                System.err.println(" p2=" + f.getV2() + "(" + p2 + ")");
                System.err.println(" p3=" + f.getV3() + "(" + p3 + ")");
                normal.x = 1.0f;
                normal.y = 0;
                normal.z = 0;
            }

            normalP.add(p1);
            normalV.add(normal);
            normalP.add(p2);
            normalV.add(normal);
            normalP.add(p3);
            normalV.add(normal);
            Integer groupI = new Integer(f.getM1());

            normalG.add(groupI);
            normalG.add(groupI);
            normalG.add(groupI);

            Point3f p = (Point3f) verts.get(f.getV1());
            TexCoord2f t = getTvert(f.getT1());
            Vertex v = new Vertex(p, t, f.getM1());
            Integer index = (Integer) map.get(v);

            if (index == null)
            {
                index = new Integer(count);
                count++;
                map.put(v, index);
                nverts.add(p);
                ntverts.add(t);
                normals.add(normal);
                if (colors.size() > 0)
                    ncolors.add(colors.get(f.getV1()));
                if (constraints != null)
                    nconstraints.add(constraints.get(f.getV1()));
            }
            f.setV1(index.intValue());
            f.setT1(index.intValue());

            p = (Point3f) verts.get(f.getV2());
            t = getTvert(f.getT2());
            v = new Vertex(p, t, f.getM1());
            index = (Integer) map.get(v);
            if (index == null)
            {
                index = new Integer(count);
                count++;
                map.put(v, index);
                nverts.add(p);
                ntverts.add(t);
                normals.add(normal);
                if (colors.size() > 0)
                    ncolors.add(colors.get(f.getV2()));
                if (constraints != null)
                    nconstraints.add(constraints.get(f.getV2()));
            }
            f.setV2(index.intValue());
            f.setT2(index.intValue());

            p = (Point3f) verts.get(f.getV3());
            t = getTvert(f.getT3());
            v = new Vertex(p, t, f.getM1());
            index = (Integer) map.get(v);
            if (index == null)
            {
                index = new Integer(count);
                count++;
                map.put(v, index);
                nverts.add(p);
                ntverts.add(t);
                normals.add(normal);
                if (colors.size() > 0)
                    ncolors.add(colors.get(f.getV3()));
                if (constraints != null)
                    nconstraints.add(constraints.get(f.getV3()));
            }
            f.setV3(index.intValue());
            f.setT3(index.intValue());
        }

        verts = nverts;
        tverts = ntverts;
        colors = ncolors;
        constraints = nconstraints;

        ArrayList found = new ArrayList();

        while (normalP.size() > 0)
        {
            found.clear();
            Point3f p = (Point3f) normalP.get(normalP.size() - 1);
            Integer g = (Integer) normalG.get(normalG.size() - 1);
            Iterator pi = normalP.iterator();
            Iterator vi = normalV.iterator();
            Iterator gi = normalG.iterator();

            while (pi.hasNext())
            {
                Point3f currentP = (Point3f) pi.next();
                Vector3f currentV = (Vector3f) vi.next();
                Integer currentI = (Integer) gi.next();

                if (currentP.equals(p)/*&& currentI.equals(g)*/)
                {
                    pi.remove();
                    vi.remove();
                    gi.remove();
                    found.add(currentV);
                }
            }

            Vector3f normal = new Vector3f();

            for (int i = 0; i < found.size(); i++)
            {
                normal.add((Vector3f) found.get(i));

            }
            normal.normalize();
            if (Float.isNaN(normal.x))
            {
                System.err.println("Illegal normal");
                normal.x = 1.0f;
                normal.y = 0;
                normal.z = 0;
            }

            for (int index = 0; index < verts.size(); index++)
            {
                if (!p.equals(verts.get(index)))
                    continue;
                normals.set(index, normal);
            }
        }
    }

    public GeometryArray createGeometry(boolean metallic)
    {
        return createGeometry(true, metallic);
    }

    static int icount = 0;

    public GeometryArray createGeometry(boolean strip, boolean metallic)
    {
        boolean singleIndex = true;

        if (cachedGeometry != null)
        {
            GeometryArray ga = (GeometryArray) cachedGeometry.get();

            if (ga != null)
                return ga;
        }
        indexify();

        GeometryInfo gi = new GeometryInfo(GeometryInfo.TRIANGLE_ARRAY);

        Point3f[] coords = new Point3f[verts.size()];

        verts.toArray(coords);
        gi.setCoordinates(coords);
        int[] vIndices = new int[faces.size() * 3];

        for (int i = 0; i < faces.size(); i++)
        {
            Face f = (Face) faces.get(i);

            vIndices[i * 3] = f.getV1();
            vIndices[i * 3 + 1] = f.getV2();
            vIndices[i * 3 + 2] = f.getV3();
        }
        gi.setCoordinateIndices(vIndices);
        gi.setUseCoordIndexOnly(singleIndex);

        if (colors.size() > 0)
        {
            Color3f[] colorA = new Color3f[colors.size()];

            colors.toArray(colorA);
            gi.setColors(colorA);
            if (!singleIndex)
                gi.setColorIndices(vIndices);
        }

        gi.setTextureCoordinateParams(1, 2);

        if (metallic)
        {

            gi.setTexCoordSetMap(new int[]
                { 0, -1 }
            );
        }
        else
        {
            gi.setTexCoordSetMap(new int[]
                { 0 }
            );
        }
        TexCoord2f[] texes = new TexCoord2f[tverts.size()];

        tverts.toArray(texes);
        gi.setTextureCoordinates(0, texes);

        if (!singleIndex)
            gi.setTextureCoordinateIndices(0, vIndices);

        Vector3f[] normalsA = new Vector3f[normals.size()];

        normals.toArray(normalsA);
        gi.setNormals(normalsA);
        if (!singleIndex)
            gi.setNormalIndices(vIndices);

        if (strip)
        {
            Stripifier st = new Stripifier();

            st.stripify(gi);
        }

        //long start = AnimationBehavior.getTime();
        try
        {
            IndexedGeometryArray iga = gi.getIndexedGeometryArray(false, false, false, singleIndex, false);

            //System.out.println(getName() + " geometry  " + (AnimationBehavior.getTime()-start) + "us");
            cachedGeometry = new WeakReference(iga);
            return iga;
        } catch (Exception exc)
        {
            RuntimeException e2 = new RuntimeException("Error while creating part " + getName());

            e2.initCause(exc);
            throw e2;
        }
    }

    public static final Color3f black = new Color3f();
    public static final Color3f white = new Color3f(1, 1, 1);

    public Appearance createAppearance(URL base, boolean metallic)
    {
        Appearance a = new Appearance();

        if (ambient == null)
        {
            System.out.println("WARN: ambient null for " + getName());
            ambient = black;
        }

        if (diffuse == null)
        {
            System.out.println("WARN: diffuse null for " + getName());
            diffuse = black;
        }

        if (specular == null)
        {
            System.out.println("WARN: specular null for " + getName());
            specular = black;
        }

        if (emissive == null)
        {
            emissive = black;
        }

        Material m = new Material(ambient, emissive, diffuse, specular, shininess);

        m.setLightingEnable(true);
        a.setMaterial(m);

        TextureUnitState main = null;

        Texture mainTex = findTexture(base, getBitmap());

        if (mainTex != null)
        {
            TextureAttributes texAttr2 = new TextureAttributes();

            texAttr2.setTextureMode(TextureAttributes.MODULATE);

            // add disabled tex coord generation for directx workaround
            TexCoordGeneration tcg = new TexCoordGeneration();

            tcg.setEnable(false);
            main = new TextureUnitState(mainTex, texAttr2, tcg);
        }

        if (metallic)
        {
            Texture tex = findTexture(base, "chrome1");
            if (tex != null) {
                TexCoordGeneration tcg = new TexCoordGeneration(
                        TexCoordGeneration.SPHERE_MAP,
                        TexCoordGeneration.TEXTURE_COORDINATE_2);

                TextureAttributes ta = new TextureAttributes();

               // ta.setTextureMode(TextureAttributes.COMBINE);
               // ta.setCombineRgbMode(TextureAttributes.COMBINE_INTERPOLATE);
               
                //the texture mode, one of:
                //MODULATE, DECAL, BLEND, REPLACE, or COMBINE
                ta.setTextureMode(TextureAttributes.COMBINE);
                
                // COMBINE_REPLACE, COMBINE_MODULATE, COMBINE_ADD, 
                //COMBINE_ADD_SIGNED, COMBINE_SUBTRACT, COMBINE_INTERPOLATE,
                ta.setCombineRgbMode(TextureAttributes.COMBINE_MODULATE);

                ta.setCombineRgbSource(0, TextureAttributes.COMBINE_PREVIOUS_TEXTURE_UNIT_STATE);
                ta.setCombineRgbFunction(0, TextureAttributes.COMBINE_SRC_COLOR);
                ta.setCombineRgbSource(1, TextureAttributes.COMBINE_TEXTURE_COLOR);
                ta.setCombineRgbFunction(1, TextureAttributes.COMBINE_SRC_COLOR);
                ta.setCombineRgbSource(2, TextureAttributes.COMBINE_PREVIOUS_TEXTURE_UNIT_STATE);
                ta.setCombineRgbFunction(2, TextureAttributes.COMBINE_SRC_ALPHA);

                TextureUnitState chrome = new TextureUnitState(tex, ta, tcg);

                a.setTextureUnitState(new TextureUnitState[]{main, chrome});
            }

            /*            
             TextureAttributes ta = new TextureAttributes();
             ta.setTextureMode(TextureAttributes.MODULATE);
             TextureUnitState chrome = new TextureUnitState(tex, ta, tcg);
             
             
             ta = new TextureAttributes();

             ta.setTextureMode(TextureAttributes.COMBINE);            
             ta.setCombineRgbMode(TextureAttributes.COMBINE_INTERPOLATE);
             ta.setCombineRgbSource(0,TextureAttributes.COMBINE_PREVIOUS_TEXTURE_UNIT_STATE);
             ta.setCombineRgbFunction(0,TextureAttributes.COMBINE_SRC_COLOR);
             ta.setCombineRgbSource(1,TextureAttributes.COMBINE_TEXTURE_COLOR);
             ta.setCombineRgbFunction(1,TextureAttributes.COMBINE_SRC_COLOR);            
             ta.setCombineRgbSource(2,TextureAttributes.COMBINE_TEXTURE_COLOR);
             ta.setCombineRgbFunction(2,TextureAttributes.COMBINE_ONE_MINUS_SRC_ALPHA);
             TexCoordGeneration ntcg = new TexCoordGeneration();
             ntcg.setEnable(false);
             
             TextureUnitState finish = new TextureUnitState(mainTex,ta,ntcg);
             
             a.setTextureUnitState(new TextureUnitState[]
             { main, chrome, finish }
             );
             */



        }
        else
        {
            a.setTextureUnitState(new TextureUnitState[]{ main });
        }

        if (alpha < 1 ||
            (!metallic &&
                (mainTex != null && mainTex.getFormat() == Texture.RGBA)
            )
        )
        {
            a.setTransparencyAttributes(
                new TransparencyAttributes(TransparencyAttributes.BLENDED, 0.01f));
            main.getTextureAttributes().setTextureMode(TextureAttributes.REPLACE);
        }

        return a;
    }

    public Shape3D createShape(URL base, boolean metallic)
    {
        return new Shape3D(createGeometry(metallic), createAppearance(base, metallic));
    }

    public boolean hasShape()
    {
        if (verts.size() == 0)
        {
            System.out.println("WARN: No vertices for trimesh " + getName());
            return false;
        }
        if (faces.size() == 0)
        {
            System.out.println("WARN: No faces for trimesh " + getName());
            return false;
        }
        return true;
    }

    public TransformGroup createSingleTG(URL base, boolean metallic)
    {
        TransformGroup tg = super.createSingleTG(base, metallic);

        if (hasShape())
        {
            tg.addChild(createShape(base, metallic));
        }
        return tg;
    }

    //----------------------------------------------------    

    private static Map textures = new HashMap();
    private static Map images = new HashMap();

    /**
     * Modified to store image not Texture instances, 
     *  as it can be compiled and not changed later
     * @param base
     * @param tname
     * @return
     */
    public static Texture findTexture(URL base, String tname)
    {

        if (tname == null) return null;
        
        Texture tex = null;// (Texture) textures.get(tname);
        TargaImage buffImg = (TargaImage)images.get(tname);
        
        //if (tex == null && !textures.containsKey(tname))
        if (buffImg == null && !images.containsKey(tname))
        {
            try
            {
                TargaImage img;

                try
                {
                  img = new TargaImage(new URL(base, tname + ".tga"));
                } catch (IOException e)
                {
                    InputStream is = new GZIPInputStream(new URL(base, tname + ".tga.gz").openStream());

                    img = new TargaImage(is);
                    is.close();
                }                
                /*
                buffImg = img.getImage();
                String format = (buffImg.getPixelDepth() == 32) ? "RGBA" : "RGB";
                TextureLoader texl = new TextureLoader(buffImg, format);
                tex = texl.getTexture();
                tex.setCapability(Texture.ALLOW_FORMAT_READ);   
                */
                buffImg = img;
                images.put(tname, buffImg);
                
            } catch (Exception exc)
            {
                //exc.printStackTrace();
                System.out.println("WARN: Cannot load texture " + tname);
                return null;
            }
           // textures.put(tname, tex);
        }
        
        String format = (buffImg.getPixelDepth() == 32) ? "RGBA" : "RGB";
        BufferedImage bi = buffImg.getImage();
        TextureLoader texl = new TextureLoader(bi, format);
        tex = texl.getTexture();
        tex.setCapability(Texture.ALLOW_FORMAT_READ);        

        return tex;
    }

    /**
     * Gets the emissive.
     * @return Returns a Color3f
     */
    public Color3f getEmissive()
    {
        return emissive;
    }

    /**
     * Sets the emissive.
     * @param emissive The emissive to set
     */
    public void setEmissive(Color3f emissive)
    {
        this.emissive = emissive;
    }

    /**
     * Gets the alpha.
     * @return Returns a float
     */
    public float getAlpha()
    {
        return alpha;
    }

    /**
     * Sets the alpha.
     * @param alpha The alpha to set
     */
    public void setAlpha(float alpha)
    {
        this.alpha = alpha;
    }

}
