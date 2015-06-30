package net.sf.nwn.bif;

import java.io.*;
import java.nio.*;
import java.nio.channels.*;

import java.util.*;

public class KeyFile extends CommonFile
{
    int bifCount;
    int resCount;
    int bifOffset;
    int resOffset;
    ArrayList bifEntries = new ArrayList();
    ArrayList resEntries = new ArrayList();

	public KeyFile(File f) throws IOException
    {
        super(f);
        
        
        bb.getInt(); // signature
        bb.getInt(); // version
        bifCount = bb.getInt();
        resCount = bb.getInt();
        bifOffset = bb.getInt();
        resOffset = bb.getInt();
        
        bb.position(bifOffset);
        for ( int i =0; i < bifCount; i++ )
        {
            bifEntries.add(new BifEntry(bb.getInt(),getString(bb.getInt(),bb.getShort())));
            bb.getShort(); // disk position
        }
        
        bb.position(resOffset);
        for ( int i =0; i < resCount; i++ )
        {
            resEntries.add(new ResEntry(getString(16),bb.getShort(),bb.getInt()));
        }
               
        fc.close();
        fis.close();
	}
            
    public void dump()
    {
        System.out.println("BIF count: " + bifCount + " at offset " + hex(bifOffset));
        System.out.println("Res count: " + resCount + " at offset " + hex(resOffset));
        System.out.println("BIFLIST");
        for ( int i =0; i < bifEntries.size(); i++ )
        {
            System.out.println(bifEntries.get(i));
        }
        System.out.println("RESLIST");
        for ( int i =0; i < resEntries.size(); i++ )
        {
            System.out.println(resEntries.get(i));
        }

    }
    
    public void unpack(File mainPath, File outPath) throws IOException
    {
        BifFile[] bifs = new BifFile[bifCount];
        File[] outdirs = new File[bifCount];
        for ( int i =0; i < bifCount; i++ )
        {
            bifs[i] = new BifFile(new File(mainPath,(((BifEntry)bifEntries.get(i)).name)));
            outdirs[i] = new File(outPath,(((BifEntry)bifEntries.get(i)).name+".dir"));
            outdirs[i].mkdirs();
        }
        
        for ( int i =0; i < resCount; i++ )
        {
            ResEntry res = (ResEntry)resEntries.get(i);
            BifFile bf = bifs[res.getSourceIndex()];
            File f = new File(outdirs[res.getSourceIndex()],res.name + "." + ext(res.type));
            bf.dumpResource(res.getFileIndex(),f);
        }
        
        for (int i =0; i < bifCount; i++)
        {
            bifs[i].close();
        }
        
    }
    
    public String ext(int type)
    {
        switch(type)
        {
            case 0x0001: return "bmp";
            case 0x0003: return "tga";
            case 0x0006: return "plt";
            case 0x0007: return "ini";
            case 0x07d2: return "mdl";
            case 0x07d9: return "scr";
            case 0x07da: return "ncs"; // compiled script
            case 0x07dd: return "tileset";
            case 0x07df: return "bic";
            case 0x07e0: return "walkmesh";
            case 0x07e1: return "2da";
            case 0x07e9: return "uti";
            case 0x07eb: return "utc";
            case 0x07ee: return "itp";
            case 0x07f0: return "utt";
            case 0x07f4: return "ltr";
            case 0x07f5: return "gff";
            case 0x07fa: return "utd";
            case 0x07fc: return "utp";
            case 0x080a: return "utw";
            
            
            case 0x000a:
            case 0x07ef:
            case 0x0804:
            case 0x0805:
            case 0x270c:            
            default:
                return hex(type);
        }
    }
            
    public static void main(String[] argv) throws IOException
    {
        if ( argv.length == 0 || argv.length > 2 )
        {
            System.out.println("Usage: keyfile - if you want a contents list");
            System.out.println("Usage: keyfile outdir - if you want unpack");
            System.exit(1);
        }
        File keyfile = new File(argv[0]).getAbsoluteFile();
        if ( argv.length == 1 )
        {
            KeyFile kf = new KeyFile(keyfile);
            kf.close();
            kf.dump();
            return;
        }
        
        File mainPath = keyfile.getParentFile();
        File outPath = new File(argv[1]);
        
        KeyFile kf = new KeyFile(keyfile);
        kf.close();
        kf.unpack(mainPath,outPath);
    }
    
    
    static class BifEntry
    {
        int length;
        String name;
        
        public BifEntry(int aLen, String aName)
        {
            length =aLen;
            name = aName;
        }
        
        public String toString()
        {
            return "BIF: " + name + " "+ length;
        }
    }
    
    static class ResEntry
    {
        String name;
        int type;
        int code;
        
        public ResEntry(String aName, int aType, int aCode)
        {
            name = aName;
            type = aType;
            code = aCode;
        }
        
        public int getSourceIndex()
        {
            return (code >>20)&0xfff;
        }
        
        public int getTilesetIndex()
        {
            return (code >>14)&0x1f;
        }
        
        public int getFileIndex()
        {
            return code&0x1fff;
        }
        
        public String toString()
        {
            return "RES: " + name + " type:" + hex(type) + " code:" + getSourceIndex() + "/" + getTilesetIndex() + "/" + getFileIndex();
        }
        
    }
}
