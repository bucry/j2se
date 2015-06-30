/*

 Targa image loader.
 Original written by Ben Stahl <benstahl@earthlink.net>
 Corrections for NWN Artur Biesiadowski <abies@pg.gda.pl>

 This file is PUBLIC DOMAIN.
 */


package net.sf.nwn.loader;


import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.image.*;
import java.math.*;


public class TargaImage
{
    private static final int    NO_TRANSPARENCY = 255;
    private static final int    FULL_TRANSPARENCY = 0;

    private short               idLength;
    private short               colorMapType;
    private short               imageType;
    private int                 cMapStart;
    private int                 cMapLength;
    private short               cMapDepth;
    private int                 xOffset;
    private int                 yOffset;
    private int                 width;
    private int                 height;
    private short               pixelDepth;
    private short               imageDescriptor;
    private DirectColorModel    cm;
    public int[]                pixels;

    public TargaImage(File srcFile)
        throws IOException
    {
        InputStream is = new FileInputStream(srcFile);

        open(is);
        is.close();
    }

    public TargaImage(URL src)
        throws IOException
    {
        InputStream is = src.openStream();

        open(is);
        is.close();
    }

    public TargaImage(InputStream is)
        throws IOException
    {
        open(is);
    }

    private void open(InputStream fis)
        throws IOException
    {

        /* --- open the file streams --- */
        BufferedInputStream bis = new BufferedInputStream(fis, 8192);
        DataInputStream dis = new DataInputStream(bis);

        /* --- read targa header info --- */
        idLength = (short) dis.read();
        colorMapType = (short) dis.read();
        imageType = (short) dis.read();
        cMapStart = (int) flipEndian(dis.readShort());
        cMapLength = (int) flipEndian(dis.readShort());
        cMapDepth = (short) dis.read();
        xOffset = (int) flipEndian(dis.readShort());
        yOffset = (int) flipEndian(dis.readShort());
        width = (int) flipEndian(dis.readShort());
        height = (int) flipEndian(dis.readShort());
        pixelDepth = (short) dis.read();
        if (pixelDepth == 24)
        {
            cm = new DirectColorModel(24, 0xFF0000, 0xFF00, 0xFF);
        }
        else if (pixelDepth == 32)
        {
            cm = new DirectColorModel(
                        32, 0xFF0000, 0xFF00, 0xFF, 0xFF000000);
        }
        imageDescriptor = (short) dis.read();

        /* --- skip over image id info (if present) --- */
        if (idLength > 0)
        {
            bis.skip(idLength);
        }

        /* --- allocate the image buffer --- */
        pixels = new int[width * height];

        /* --- read the pixel data --- */


        if (pixelDepth == 32)
        {
            load32(bis);
        }
        else
        {
            load24(bis);
        }

        fis.close();
    }

    private static byte[] buf;

    private void load32(BufferedInputStream bis)
        throws IOException
    {

        if (buf == null || buf.length < width * 4)
        {
            buf = new byte[width * 4];
        }
        for (int i = (height - 1); i >= 0; i--)
        {
            int srcLine = i * width;

            bis.read(buf, 0, width * 4);
            for (int j = 0; j < width; j++)
            {
                int j4 = j << 2;
                int blue = buf[j4] & 0xff;
                int green = buf[j4 + 1] & 0xff;
                int red = buf[j4 + 2] & 0xff;
                int alpha = buf[j4 + 3] & 0xff;

                blue = (blue * alpha) >> 8;
                green = (green * alpha) >> 8;
                red = (red * alpha) >> 8;

                pixels[srcLine + j] =
                        alpha << 24 | red << 16 | green << 8 | blue;

            }
        }
    }

    private void load24(BufferedInputStream bis)
        throws IOException
    {
        if (buf == null || buf.length < width * 3)
        {
            buf = new byte[width * 3];
        }
        for (int i = (height - 1); i >= 0; i--)
        {
            int srcLine = i * width;

            bis.read(buf, 0, width * 3);
            for (int j = 0; j < width; j++)
            {
                int j3 = j + j + j;
                int blue = buf[j3] & 0xff;
                int green = buf[j3 + 1] & 0xff;
                int red = buf[j3 + 2] & 0xff;

                pixels[srcLine + j] = red << 16 | green << 8 | blue;
            }
        }
    }

    public BufferedImage getImage()
    {

        /* --- set up an image from memory and return it --- */
        //return Toolkit.getDefaultToolkit().createImage(
        //    new MemoryImageSource(width, height, cm, pixels, 0, width));
        BufferedImage bi = new BufferedImage(width, height,
                (pixelDepth == 24) ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB);

        bi.setRGB(0, 0, width, height, pixels, 0, width);
        return bi;
    }

    /* -------------------------------------------------------- getSize */
    public Dimension getSize()
    {
        return new Dimension(width, height);
    }

    /* ----------------------------------------------------- flipEndian */
    private short flipEndian(short signedShort)
    {
        int input = signedShort & 0xFFFF;

        return (short) (input << 8 | (input & 0xFF00) >>> 8);
    }

    public Image getThumbnail(int maxSize, boolean smooth)
    {
        Dimension   thumbnailSize = new Dimension(0, 0);
        int         pixel = 0;
        int         srcX = 0;
        int         srcY = 0;
        double      multiplier = 0.0;
        int         smoothArea;

        if (((width == maxSize) && (height == maxSize)) ||
            ((width < maxSize) && (height < maxSize)))
        {
            smooth = false;
        }

        if (width >= height)
        {
            thumbnailSize.width = maxSize;
            thumbnailSize.height =
                    (int) (Math.round(((float) height / (float) width)
                            * (float) maxSize));
        }
        else
        {
            thumbnailSize.height = maxSize;
            thumbnailSize.width =
                    (int) (Math.round(((float) width / (float) height)
                            * (float) maxSize));
        }

        multiplier = (double) width / (double) thumbnailSize.width;

        int[] thumbnailData =
            new int[thumbnailSize.width * thumbnailSize.height];

        for (int i = 0; i < thumbnailSize.height; i++)
        {
            srcY = (int) (i * multiplier);
            for (int j = 0; j < thumbnailSize.width; j++)
            {
                srcX = (int) (j * multiplier);

                /* Smoothing algorithm (nearest neighbor - t pattern) */
                if (smooth)
                {
                    int red = 0;
                    int green = 0;
                    int blue = 0;
                    int[] kernel = new int[5];

                    /* Don't smooth as much if image is already square */
                    if (width == height)
                    {
                        smoothArea = 1;
                    }
                    else
                    {
                        smoothArea = 2;
                    }

                    kernel[2] = pixels[(srcY * width) + srcX];

                    if ((srcY - smoothArea) < 0)
                    {
                        kernel[0] = kernel[2];
                    }
                    else
                    {
                        kernel[0] = pixels[((srcY - smoothArea) * width)
                                + srcX];

                    }

                    if ((srcX - smoothArea) < 0)
                    {
                        kernel[1] = kernel[2];
                    }
                    else
                    {
                        kernel[1] = pixels[(srcY * width)
                                + srcX - smoothArea];
                    }

                    if ((srcX + smoothArea) > (width - 1))
                    {
                        kernel[3] = kernel[2];
                    }
                    else
                    {
                        kernel[3] = pixels[(srcY * width)
                                + srcX + smoothArea];
                    }

                    if ((srcY + smoothArea) > (height - 1))
                    {
                        kernel[4] = kernel[2];
                    }
                    else
                    {
                        kernel[4] = pixels[((srcY + smoothArea) * width)
                                + srcX];
                    }

                    for (int k = 0; k < kernel.length; k++)
                    {
                        red += ((kernel[k] & 0x00FF0000) >>> 16);
                        green += ((kernel[k] & 0x0000FF00) >>> 8);
                        blue += (kernel[k] & 0x000000FF);
                    }

                    red /= kernel.length;
                    green /= kernel.length;
                    blue /= kernel.length;
                    pixel = 0xFF000000 | red << 16 | green << 8 | blue;
                }
                else
                {
                    pixel = pixels[(srcY * width) + srcX];
                }
                thumbnailData[(i * thumbnailSize.width) + j] = pixel;
            }
        }
        DirectColorModel tcm =
            new DirectColorModel(24, 0xFF0000, 0xFF00, 0xFF);

        // --- set up an image from memory and return it ---
        return Toolkit.getDefaultToolkit().createImage(
                new MemoryImageSource(
                    thumbnailSize.width, thumbnailSize.height, tcm,
                    thumbnailData, 0, thumbnailSize.width));
    }

    public int getPixelDepth()
    {
        return pixelDepth;
    }

}
