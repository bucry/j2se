package net.sf.nwn.bif;

import java.io.*;
import java.nio.*;
import java.nio.channels.*;

public class CommonFile 
{
    FileInputStream fis;
    FileChannel fc;
    ByteBuffer bb;
	
    public CommonFile(File f) throws IOException
    {
        fis = new FileInputStream(f);
        fc = fis.getChannel();
        int sz = (int)fc.size();
        bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, sz).order(ByteOrder.LITTLE_ENDIAN);
	}
	
    public void close() throws IOException
    {
        fc.close();
        fis.close();
    }
    
    protected String getString(int len)
    {
        StringBuffer sb = new StringBuffer(len);
        while ( len-- > 0 )
        {
            char c = (char)bb.get();
            if ( c != 0 )
            {             
                sb.append(c);
            }
        }
        return sb.toString();
    }
    
    protected String getString(int off,int len)
    {
        int orig = bb.position();
        bb.position(off);
        String str = getString(len);
        bb.position(orig);
        return str;
    }
    
    public static String hex(int h)
    {
        return Integer.toHexString(h);
    }
    
}
