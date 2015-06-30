package net.sf.nwn.loader;


import java.io.*;
import java.util.*;
import javax.vecmath.*;


public final class ManualParser
{
    private static final int BUF_SIZE = 4096;
    private static final int SYMBOL_SIZE = 256;
    private static final boolean[] whitespace = new boolean[256];
    static
    {
        whitespace['\n'] = true;
        whitespace['\r'] = true;
        whitespace['\t'] = true;
        whitespace[' '] = true;
    }

    private byte[] buf = new byte[BUF_SIZE];
    private int index;
    private int limit;
    private char[] symbol = new char[SYMBOL_SIZE];
    private int slimit;
    private int line = 1;
    private InputStream reader;

    public static ManualParser sharedParser = new ManualParser();

    public static boolean internStrings = true;

    public ManualParser()
    {
    }

    public ManualParser(InputStream is)
    {
        reinit(is);
    }

    public ManualParser reinit(InputStream is)
    {
        index = 0;
        limit = 0;
        slimit = 0;
        line = 1;
        int read = 0;

        reader = is;
        return this;
    }

    private void fillBuffer()
    {
        try
        {
            System.arraycopy(buf, index, buf, 0, limit - index);
            int read = reader.read(buf, limit - index, BUF_SIZE - limit);

            if (read < 0)
            {
                reader = null;
                limit = limit - index;
                index = 0;
            }
            else
            {
                limit = limit - index + read;
                index = 0;
            }
        } catch (IOException exc)
        {
            exc.printStackTrace();
            reader = null;
        }

    }

    public void fillSymbol()
    {
        if (limit - index < SYMBOL_SIZE && reader != null)
        {
            fillBuffer();
        }

        slimit = 0;
        while (true)
        {
            int c = buf[index];

            if (!whitespace[c])
                break;
            if (c == '\n')
                line++;
            index++;

        }

        if (buf[index] == '#')
        {
            while (buf[index] != '\n')
                index++;
            fillSymbol();
            return;
        }

        while (true)
        {
            int c = buf[index];

            if (whitespace[c])
                break;

            symbol[slimit] = (char) c;
            index++;
            slimit++;
        }
    }

    public boolean isSymbol(String text)
    {
        if (slimit != text.length())
            return false;
        for (int i = 0; i < slimit; i++)
        {
            if (text.charAt(i) != symbol[i])
                return false;
        }
        return true;
    }

    public void readSymbol(String s)
    {
        fillSymbol();
        assertSymbol(s);
    }

    public void assertSymbol(String s)
    {
        if (!isSymbol(s))
            throw new RuntimeException("Expected " + s + " got " + getString() + " at line " + line);
    }

    public void unexpectedToken()
    {
        throw new RuntimeException("Unexpected token " + getString() + " at line " + line);
    }

    public void check(boolean correct, String message)
    {
        if (!correct)
        {
            System.err.println("WARN: Line " + line + ":" + message);
        }
    }

    public void checkId(String orig, String current)
    {
        if (!orig.equalsIgnoreCase(current))
        {
            System.err.println("WARN: Line " + line + ":" + " Identifier mismatch - expected '" + orig + "' got '" + current + "' ");
        }
    }

    public float getFloat()
    {
        float answer = 0;
        int sign = 1;
        char[] ch = symbol;
        int length = slimit;
        int i = 0;
        int before = 0;

        if (ch[0] == '-')
        {
            sign = -1;
            i++;
        }
        else if (ch[0] == '+')
        {
            i++;
        }

        for (; i < length; i++)
        {
            char c = ch[i];

            if (c == '.')
                break;
            before = before * 10 + (c - '0');
        }
        i++;
        answer = before;

        float divider = 10;

        all:
        for (; i < length; i++)
        {
            char c = ch[i];

            if (c == 'e' || c == 'E')
            {
                before = 0;
                int esign = 1;

                i++;
                if (ch[i] == '-')
                {
                    esign = -1;
                    i++;
                }
                else if (ch[i] == '+')
                {
                    i++;
                }
                for (; i < length; i++)
                {
                    c = ch[i];
                    before = before * 10 + (c - '0');
                }
                answer *= Math.pow(10, before * esign);
                break all;
            }
            answer += (c - '0') / divider;
            divider *= 10;
        }
        return answer * sign;
    }

    public float readFloat()
    {
        fillSymbol();
        return getFloat();
    }

    public int getInt()
    {
        int sign = 1;
        char[] ch = symbol;
        int length = slimit;
        int i = 0;
        int before = 0;

        if (ch[0] == '-')
        {
            sign = -1;
            i++;
        }
        else if (ch[0] == '+')
        {
            i++;
        }

        for (; i < length; i++)
        {
            char c = ch[i];

            before = before * 10 + (c - '0');
        }
        return before;
    }

    public int readInt()
    {
        fillSymbol();
        return getInt();
    }

    public String getString()
    {
        if (internStrings)
        {
            return new String(symbol, 0, slimit).intern();
        }
        else
        {
            return new String(symbol, 0, slimit);
        }
    }

    public String readString()
    {
        fillSymbol();
        return getString();
    }

    public Point3f readPoint()
    {
        return new Point3f(readFloat(), readFloat(), readFloat());
    }

    public Vector3f readVector()
    {
        return new Vector3f(readFloat(), readFloat(), readFloat());
    }

    public Color3f readColor()
    {
        return new Color3f(readFloat(), readFloat(), readFloat());
    }

    public AxisAngle4f readAxisa()
    {
        return new AxisAngle4f(readFloat(), readFloat(), readFloat(), readFloat());
    }

    //-------------------------------------------------------------------------

    public Model definition()
    {
        while (true)
        {
            fillSymbol();
            if (isSymbol(FILEDEPENDANCY))
            {
                readString();
                continue;
            }
            break;
        }

        assertSymbol(NEWMODEL);
        Model model = new Model(readString());

        while (true)
        {
            fillSymbol();

            if (isSymbol(DONEMODEL))
            {
                checkId(model.getName(), readString()); //id
                break;
            }
            else if (isSymbol(SETSUPERMODEL))
            {
                checkId(model.getName(), readString());
                model.setSupermodelName(readString());
            }
            else if (isSymbol(SETANIMATIONSCALE))
            {
                model.setAnimationScale(readFloat());
            }
            else if (isSymbol(CLASSIFICATION))
            {
                model.setClassification(readString());
            }
            else if (isSymbol(BEGINMODELGEOM))
            {
                model.setModelGeometry(modelGeometry());
            }
            else if (isSymbol(NEWANIM))
            {
                model.addModelAnimation(modelAnimation());
            }
            else
            {
                unexpectedToken();
            }
        }

        return model;
    }

    public GeomNode modelGeometry()
    {
        String id = readString(); //id
        GeomNode node;
        GeomNode root = null;
        HashMap map = new HashMap();

        while (true)
        {
            fillSymbol();
            if (isSymbol(ENDMODELGEOM))
            {
                checkId(id, readString());
                break;
            }
            else if (isSymbol(NODE))
            {
                node = geomNode(map);
                map.put(node.getName(), node);
                if (node.getSupernode() == null)
                    root = node;
            }
            else
            {
                unexpectedToken();
            }
        }
        return root;
    }

    public GeomNode geomNode(Map map)
    {
        //GeomNode node = null;
        DummyGeomNode dummy = null;
        TrimeshGeomNode tri = null;
        EmitterNode emi = null;
        DanglymeshGeomNode dang = null;
        AabbGeomNode aabb = null;
        boolean ignoring = false;

        String type = readString();
        String name = readString();

        readSymbol(PARENT);
        String pname = readString();

        GeomNode parent = null;

        if (!pname.equalsIgnoreCase("NULL"))
        {
            parent = (GeomNode) map.get(pname.toLowerCase());
            if (parent == null)
            {
                check(parent != null, "Cannot find supernode " + parent);
            }
        }
        if (type.equals("trimesh"))
        {
            tri = new TrimeshGeomNode(parent);
            dummy = tri;
        }
        else if (type.equals("danglymesh"))
        {
            dang = new DanglymeshGeomNode(parent);
            tri = dang;
            dummy = tri;
        }
        else if (type.equals("aabb"))
        {
            aabb = new AabbGeomNode(parent);
            tri = aabb;
            dummy = aabb;
        }
        else if (type.equals("dummy") || type.equals("patch"))
        {
            // for now treat patch as dummy
            dummy = new DummyGeomNode(parent);
        }
        else if (type.equals("emitter"))
        {
            emi = new EmitterNode(parent);
            dummy = emi;
        }
        else if (type.equals("reference") || type.equals("light") || type.equals("aabb"))
        {
            System.err.println("Ignoring node " + type);
            dummy = new DummyGeomNode(parent);
            ignoring = true;
        }
        else
        {
            throw new RuntimeException("Unsupported node " + type);
        }
        dummy.setName(name);
        if (parent != null)
        {
            parent.addChild(dummy);
        }

        fillSymbol();
        while (true)
        {
            if (isSymbol(ENDNODE))
            {
                break;
            }
            else if (isSymbol(POSITION))
            {
                dummy.setPosition(readVector());
            }
            else if (isSymbol(ORIENTATION))
            {
                dummy.setOrientation(readAxisa());
            }
            else if (isSymbol(WIRECOLOR))
            {
                dummy.setWirecolor(readColor());
            }
            else if (isSymbol(SCALE))
            {
                dummy.setScale(readFloat());
            }
            else if (tri != null)
            {
                if (isSymbol(AMBIENT))
                {
                    tri.setAmbient(readColor());
                }
                else if (isSymbol(DIFFUSE))
                {
                    tri.setDiffuse(readColor());
                }
                else if (isSymbol(SPECULAR))
                {
                    tri.setSpecular(readColor());
                }
                else if (isSymbol(SHININESS))
                {
                    tri.setShininess(readFloat());
                }
                else if (isSymbol(SELFILLUMCOLOR))
                {
                    tri.setEmissive(readColor());
                }
                else if (isSymbol(BITMAP))
                {
                    tri.setBitmap(readString());
                }
                else if (isSymbol(ALPHA)) // danglymesh only ?
                {
                    tri.setAlpha(readFloat());
                }
                else if (isSymbol(VERTS))
                {
                    vertList(tri, readInt());
                }
                else if (isSymbol(FACES))
                {
                    faceList(tri, readInt());
                }
                else if (isSymbol(TVERTS))
                {
                    tvertList(tri, readInt());
                }
                else if (isSymbol(COLORS))
                {
                    colorsList(tri, readInt());
                }
                else if (dang != null)
                {
                    if (isSymbol(CONSTRAINTS))
                    {
                        constraintsList(dang, readInt());
                    }
                    else
                    {
                        //unexpectedToken();
                        System.err.println("Ignoring " + getString());
                    }
                }
                else if (aabb != null)
                {
                    if (isSymbol(AABB))
                    {
                        aabbList(aabb);
                        continue;
                    }
                    else
                    {
                        //unexpectedToken();
                        System.err.println("Ignoring " + getString());
                    }
                }
                else
                {
                    //unexpectedToken();
                    System.err.println("Ignoring " + getString());
                }
            }
            else if (emi != null)
            {
                if (isSymbol(COLORSTART))
                {
                    emi.setColorStart(readColor());
                }
                else if (isSymbol(COLOREND))
                {
                    emi.setColorEnd(readColor());
                }
                else if (isSymbol(ALPHASTART))
                {
                    emi.setAlphaStart(readFloat());
                }
                else if (isSymbol(ALPHAEND))
                {
                    emi.setAlphaEnd(readFloat());
                }
                else if (isSymbol(SIZESTART))
                {
                    emi.setSizeStart(readFloat());
                }
                else if (isSymbol(SIZEEND))
                {
                    emi.setSizeEnd(readFloat());
                }
                else if (isSymbol(SIZESTART_Y))
                {
                    emi.setSizeStart_y(readFloat());
                }
                else if (isSymbol(SIZESEND_Y))
                {
                    emi.setSizeEnd_y(readFloat());
                }
                else if (isSymbol(FRAMESTART))
                {
                    emi.setFrameStart(readInt());
                }
                else if (isSymbol(FRAMEEND))
                {
                    emi.setFrameEnd(readInt());
                }
                else if (isSymbol(BIRTHRATE))
                {
                    emi.setBirthrate(readFloat());
                }
                else if (isSymbol(LIFEEXP))
                {
                    emi.setLifeExp(readFloat());
                }
                else if (isSymbol(MASS))
                {
                    emi.setMass(readFloat());
                }
                else if (isSymbol(SPREAD))
                {
                    emi.setSpread(readFloat());
                }
                else if (isSymbol(VELOCITY))
                {
                    emi.setVelocity(readFloat());
                }
                else if (isSymbol(FPS))
                {
                    emi.setFps(readFloat());
                }
                else if (isSymbol(RANDVEL))
                {
                    emi.setRandvel(readFloat());
                }
                else if (isSymbol(BLEND))
                {
                    emi.setBlend(readString());
                }
                else if (isSymbol(TEXTURE))
                {
                    emi.setTexture(readString());
                }
                else if (isSymbol(XGRID))
                {
                    emi.setXgrid(readInt());
                }
                else if (isSymbol(YGRID))
                {
                    emi.setYgrid(readInt());
                }
                else if (isSymbol(PARTICLEROT))
                {
                    emi.setParticleRot(readFloat());
                }
                else if (isSymbol(XSIZE))
                {
                    emi.setXsize(readFloat());
                }
                else if (isSymbol(YSIZE))
                {
                    emi.setYsize(readFloat());
                }
                else if (isSymbol(RENDER))
                {
                    emi.setRender(readString());
                }
                else if (isSymbol(P2P_TYPE))
                {
                    emi.setP2pType(readString());
                }
                else if (isSymbol(DRAG))
                {
                    emi.setDrag(readFloat());
                }
                else if (isSymbol(GRAV))
                {
                    emi.setGrav(readFloat());
                }
                else if (isSymbol(INHERIT))
                {
                    emi.setInherit(readInt());
                }
                else if (isSymbol(INHERIT_LOCAL))
                {
                    emi.setInherit_local(readInt());
                }
                else if (isSymbol(INHERIT_PART))
                {
                    emi.setInherit_part(readInt());
                }
                else if (isSymbol(UPDATE))
                {
                    emi.setUpdate(readString());
                }
                else
                {
                    System.err.println("Ignoring " + getString());
                }
            }
            else if (ignoring)
            {
                System.err.print(getString() + " ");
            }
            else
            {
                unexpectedToken();
            }
            fillSymbol();
        }

        return dummy;
    }

    public void vertList(TrimeshGeomNode tri, int count)
    {
        while (count-- > 0)
        {
            tri.addVert(readPoint());
        }
    }

    public void tvertList(TrimeshGeomNode tri, int count)
    {
        while (count-- > 0)
        {
            tri.addTvert(new TexCoord2f(readFloat(), readFloat()));
            fillSymbol();
        }
    }

    public void colorsList(TrimeshGeomNode tri, int count)
    {
        while (count-- > 0)
        {
            tri.addColor(readColor());
        }
    }

    public void faceList(TrimeshGeomNode tri, int count)
    {
        while (count-- > 0)
        {
            tri.addFace(
                readInt(), readInt(), readInt(),
                readInt(),
                readInt(), readInt(), readInt(),
                readInt()
            );

        }
    }

    public void constraintsList(DanglymeshGeomNode dang, int count)
    {
        while (count-- > 0)
        {
            dang.addConstraint(readFloat());
        }
    }

    public void aabbList(AabbGeomNode aabb)
    {
        while (true)
        {
            fillSymbol();
            if (Character.isLetter(symbol[0]))
            {
                return;
            }

            aabb.addAabbEntry(getFloat(), readFloat(), readFloat(), readFloat(), readFloat(), readFloat(), readInt());
        }
    }

    public ModelAnimation modelAnimation()
    {
        String name = readString();
        String model = readString();
        ModelAnimation anim = new ModelAnimation(name, model);

        readSymbol(LENGTH);
        anim.setLength(readFloat());
        readSymbol(TRANSTIME);
        anim.setTransTime(readFloat());
        while (true)
        {
            fillSymbol();
            if (isSymbol(DONEANIM))
            {
                readString();
                readString();
                break;
            }
            else if (isSymbol(ANIMROOT))
            {
                readString();
            }
            else if (isSymbol(NODE))
            {
                AnimNode node = animNode();

                anim.addNode(node);
            }
            else if (isSymbol(EVENT))
            {
                readFloat();
                readString();
            }
            else
            {
                unexpectedToken();
            }
        }
        return anim;
    }

    public AnimNode animNode()
    {
        String type = readString();
        String name = readString();
        AnimNode an;
        AnimEmitterNode en = null;

        if (type.equals("emitter"))
        {
            en = new AnimEmitterNode(name);
            an = en;
        }
        else if (type.equals("dummy") || type.equals("trimesh"))
        {
            an = new AnimNode(name);
        }
        else
        {
            check(false, "Unknown anim node type " + type);
            an = new AnimNode(name);
        }

        readSymbol(PARENT);
        an.setParent(readString());
        while (true)
        {
            fillSymbol();
            if (isSymbol(ENDNODE))
            {
                break;
            }
            else if (isSymbol(POSITIONKEY))
            {
                pklist(an);
            }
            else if (isSymbol(ORIENTATIONKEY))
            {
                oklist(an);
            }
            else if (en != null)
            {
                if (isSymbol(BIRTHRATEKEY))
                {
                    singleFloatList(en.getBirthratekeyList());
                }
            }
            else
            {
                check(false, "Unknown animnode component " + getString());
            }
        }
        an.trimSlack();
        return an;
    }

    private KeyVector kv = new KeyVector();

    public void pklist(AnimNode an)
    {
        while (true)
        {
            fillSymbol();
            if (isSymbol(ENDLIST))
            {
                break;
            }
            else
            {
                kv.setKey(getFloat());
                kv.set(readFloat(), readFloat(), readFloat());
                an.addPositionKey(kv);
            }
        }
    }

    private KeyAxisAngle kaa = new KeyAxisAngle();

    public void oklist(AnimNode an)
    {
        while (true)
        {
            fillSymbol();
            if (isSymbol(ENDLIST))
            {
                break;
            }
            else
            {
                an.addOrientationKey(getFloat(), readFloat(), readFloat(), readFloat(), readFloat());
            }
        }
    }

    public void singleFloatList(List l)
    {
        while (true)
        {
            fillSymbol();
            if (isSymbol(ENDLIST))
            {
                break;
            }
            else
            {
                float key = getFloat();

                l.add(new KeyFloat(readFloat(), key));
            }
        }
    }

    //-------------------------------------------------------------------------

    public static final String  FILEDEPENDANCY = "filedependancy";
    public static final String  NEWMODEL = "newmodel";
    public static final String  DONEMODEL = "donemodel";
    public static final String  SETSUPERMODEL = "setsupermodel";
    public static final String  CLASSIFICATION = "classification";
    public static final String  SETANIMATIONSCALE = "setanimationscale";
    public static final String  BEGINMODELGEOM = "beginmodelgeom";
    public static final String  ENDMODELGEOM = "endmodelgeom";
    public static final String  NEWANIM = "newanim";
    public static final String  DONEANIM = "doneanim";
    public static final String  NODE = "node";
    public static final String  ENDNODE = "endnode";
    public static final String  BITMAP = "bitmap";
    public static final String  VERTS = "verts";
    public static final String  FACES = "faces";
    public static final String  TVERTS = "tverts";
    public static final String  PARENT = "parent";
    public static final String  POSITION = "position";
    public static final String  ORIENTATION = "orientation";
    public static final String  WIRECOLOR = "wirecolor";
    public static final String  AMBIENT = "ambient";
    public static final String  DIFFUSE = "diffuse";
    public static final String  SPECULAR = "specular";
    public static final String  SHININESS = "shininess";
    public static final String  LENGTH = "length";
    public static final String  TRANSTIME = "transtime";
    public static final String  ANIMROOT = "animroot";
    public static final String  POSITIONKEY = "positionkey";
    public static final String  ORIENTATIONKEY = "orientationkey";
    public static final String  ENDLIST = "endlist";
    public static final String  EVENT = "event";
    public static final String  COLORS = "colors";
    public static final String  SCALE = "scale";
    public static final String  SELFILLUMCOLOR = "selfillumcolor";
    public static final String  ALPHA = "alpha";
    public static final String  CONSTRAINTS = "constraints";
    public static final String  AABB = "aabb";

    public static final String  COLORSTART = "colorStart";
    public static final String  COLOREND = "colorEnd";
    public static final String  ALPHASTART = "alphaStart";
    public static final String  ALPHAEND = "alphaEnd";
    public static final String  SIZESTART = "sizeStart";
    public static final String  SIZEEND = "sizeEnd";
    public static final String  SIZESTART_Y = "sizeStart_y";
    public static final String  SIZESEND_Y = "sizeEnd_y";
    public static final String  FRAMESTART = "frameStart";
    public static final String  FRAMEEND = "frameEnd";
    public static final String  BIRTHRATE = "birthrate";
    public static final String  LIFEEXP = "lifeExp";
    public static final String  MASS = "mass";
    public static final String  SPREAD = "spread";
    public static final String  VELOCITY = "velocity";
    public static final String  RANDVEL = "randvel";
    public static final String  FPS = "fps";
    public static final String  BLEND = "Blend";
    public static final String  TEXTURE = "texture";
    public static final String  XGRID = "xgrid";
    public static final String  YGRID = "ygrid";
    public static final String  PARTICLEROT = "particleRot";
    public static final String  XSIZE = "xsize";
    public static final String  YSIZE = "ysize";
    public static final String  RENDER = "render";
    public static final String  P2P_TYPE = "p2p_type";
    public static final String  DRAG = "drag";
    public static final String  GRAV = "grav";
    public static final String  INHERIT = "inherit";
    public static final String  INHERIT_LOCAL = "inherit_local";
    public static final String  INHERIT_PART = "inherit_part";
    public static final String  UPDATE = "update";

    public static final String  BIRTHRATEKEY = "birthratekey";

}
