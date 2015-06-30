package net.sf.nwn.bif;


import java.io.*;
import java.nio.*;
import java.nio.channels.*;
import java.util.*;

public class BifFile extends CommonFile
{
    
    int entries;
    int[] offsets = new int[1<<14];
    int[] lengths = new int[1<<14];
    

	public BifFile(File f) throws IOException
    {
        
        super(f);
        bb.getInt();
        bb.getInt();
        entries = bb.getInt();
        bb.getInt();
        int off = bb.getInt();
        bb.position(off);
        
        for ( int i =0; i < entries; i++ )
        {
            int id = bb.getInt()&0x1fff;
            offsets[id] = bb.getInt();
            lengths[id] = bb.getInt();
            bb.getInt();            
        }
	}                    
    
    public void dumpResource(int index, File out) throws IOException
    {
        System.out.println("Writing " + out + " len " + lengths[index]);
        FileOutputStream fos = new FileOutputStream(out);
        FileChannel fc = fos.getChannel();
        bb.position(offsets[index]);        
        ByteBuffer slice = bb.slice();
        slice.limit(lengths[index]);               
        fc.write(slice);
        fc.close();
        fos.close();          
    }
    
    
    public void dump()
    {
        System.out.println("BIF " + entries + " entries");
        for ( int i =0; i < entries; i++ )
        {
            System.out.println("Id: " + i + " off:" + hex(offsets[i]) + " size:" + lengths[i]);
            //System.out.println(getString(offsets[i],lengths[i]));
            //System.out.println("----------------");            
        }
    }
           
	public static void main(String[] args) throws IOException
    {
        BifFile bf = new BifFile(new File(args[0]));
        bf.dump();
        bf.close();
	}
}
