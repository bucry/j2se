package net.sf.nwn.loader;


import java.io.*;
import java.io.IOException;
import java.net.*;
import java.util.*;
import java.util.zip.GZIPInputStream;

import com.sun.j3d.loaders.*;
import javax.media.j3d.*;


public class NWNLoader extends LoaderBase
{

    /*
     * @see Loader#load(String)
     */
    public Scene load(String arg0)
        throws FileNotFoundException, IncorrectFormatException, ParsingErrorException
    {
        File f = new File(arg0);

        try
        {
            return load(f.toURL());
        } catch (MalformedURLException e)
        {
            rethrow(e);
            return null;
        }
    }

    /*
     * @see Loader#load(URL)
     */
    public Scene load(URL arg0)
        throws FileNotFoundException, IncorrectFormatException, ParsingErrorException
    {
        if (getBaseUrl() == null)
            setBaseUrlFromUrl(arg0);

        if (modelCache != null)
        {
            Model m = (Model) modelCache.get(arg0);

            if (m != null)
                return createScene(m);
        }

        try
        {
            InputStream br;

            try
            {
                br = arg0.openStream();
            } catch (IOException exc)
            {
                br = new GZIPInputStream(new URL(arg0 + ".gz").openStream());
            }
            Scene s = load(br);

            br.close();
            return s;
        } catch (IOException exc)
        {
            rethrow(exc);
            return null;
        }
    }

    public Scene load(InputStream arg0)
        throws FileNotFoundException, IncorrectFormatException, ParsingErrorException
    {
    	ManualParser parser = new ManualParser(arg0);
        return load(parser);
    }
    /*
     * @see Loader#load(Reader)
     */
    public Scene load(Reader arg0)
        throws FileNotFoundException, IncorrectFormatException, ParsingErrorException
    {
        //return load(new ManualParser(arg0));                
        throw new RuntimeException("Reader interface not supported - use input stream");
    }

    private Scene load(ManualParser mp)
        throws FileNotFoundException, IncorrectFormatException, ParsingErrorException
    {
        initBase();

        try
        {
            Model model = mp.definition();

            return createScene(model);
        } catch (Exception exc)
        {
            ParsingErrorException pee = new ParsingErrorException(exc.getMessage());

            pee.initCause(exc);
            throw pee;
        }
    }

    public Scene createScene(Model model)
        throws FileNotFoundException, IncorrectFormatException, ParsingErrorException
    {
        return createScene(model, null);
    }

    public Scene createScene(Model model, URL modelURL)
        throws FileNotFoundException, IncorrectFormatException, ParsingErrorException
    {
        if (modelCache != null)
        {
            modelCache.put(model.getName(), model);
            if (modelURL != null)
            {
                modelCache.put(modelURL, model);
            }
        }

        model.fixupEmitters();

        Group g = model.getModelGeometry().createAllTG(getBaseUrl(), model.isMetallic());

        SceneBase s = new SceneBase();

        addNamed(s, g);

        BranchGroup bg = new BranchGroup();

        bg.setCapability(BranchGroup.ALLOW_DETACH);
        bg.addChild(g);

        Behavior b = createAnimationBehavior(model, s.getNamedObjects());

        bg.addChild(b);
        s.addNamedObject("AnimationBehavior", b);
        s.setSceneGroup(bg);
        return s;
    }

    private void addNamed(SceneBase s, Group g)
    {
        if (g.getUserData() instanceof NWNUserData)
        {
            s.addNamedObject(((NWNUserData) g.getUserData()).name, g);
        }

        for (int i = 0; i < g.numChildren(); i++)
        {
            javax.media.j3d.Node n = g.getChild(i);

            if (n instanceof Group)
            {
                addNamed(s, (Group) n);
            }
            else
            {
                if (n.getUserData() instanceof NWNUserData)
                {
                    s.addNamedObject(((NWNUserData) n.getUserData()).name, n);
                }
            }
        }
    }

    public Behavior createAnimationBehavior(Model model, Map named)
    {
        HashMap anims = new HashMap();

        //long start = System.currentTimeMillis();
        for (Model m = model; m != null; m = m.getSupermodel(getBaseUrl(), modelCache))
        {
            //System.out.println("Loaded in " + (System.currentTimeMillis()-start) + "ms");
            m.getAllAnimations(anims);
            //start = System.currentTimeMillis();
        }
        ArrayList defAnims = new ArrayList();

        Iterator it = anims.keySet().iterator();

        while (it.hasNext())
        {
            String name = (String) it.next();

            if ((name.indexOf("pause") >= 0 ||
                    name.indexOf("hturn") >= 0 //||true
                )
                // && name.startsWith(model.getName()+":")
            )
            {

                defAnims.add(name);
            }
        }

        HashMap transforms = new HashMap();
        HashMap emitters = new HashMap();

        it = named.entrySet().iterator();
        while (it.hasNext())
        {
            Map.Entry ent = (Map.Entry) it.next();

            if (ent.getValue() instanceof TransformGroup)
            {
                transforms.put(ent.getKey(), ent.getValue());
            }
            else if (ent.getValue() instanceof EmitterBehavior)
            {
                emitters.put(ent.getKey(), ent.getValue());
            }
        }

        Behavior b = new AnimationBehavior(transforms, emitters, anims, defAnims);

        return b;
    }

    public void setBaseUrlFromUrl(URL url)
        throws FileNotFoundException
    {
        String u = url.toString();
        String s;

        if (u.lastIndexOf('/') == -1)
        {
            s = url.getProtocol() + ":";
        }
        else
        {
            s = u.substring(0, u.lastIndexOf('/') + 1);
        }
        try
        {
            setBaseUrl(new URL(s));
        } catch (MalformedURLException e)
        {
            rethrow(e);
        }
    }

    private void initBase()
        throws FileNotFoundException
    {
        if (getBaseUrl() == null)
        {
            try
            {
                setBaseUrl(new File(".").toURL());
            } catch (MalformedURLException e)
            {
                rethrow(e);
            }
        }
    }

    private void rethrow(Throwable th)
        throws FileNotFoundException
    {
        FileNotFoundException fn = new FileNotFoundException(th.getMessage());

        fn.initCause(th);
        throw fn;
    }

    public void enableModelCache(boolean state)
    {
        if (state)
        {
            modelCache = new HashMap();
        }
        else
        {
            modelCache = null;
        }
    }

    private Map modelCache;

    public Map getModelCache()
    {
        return modelCache;
    }

}
