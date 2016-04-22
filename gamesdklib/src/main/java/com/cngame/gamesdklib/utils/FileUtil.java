package com.cngame.gamesdklib.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * Created by Administrator on 2016/3/3.
 */
public class FileUtil
{
    public static void copyFile(File sourceFile, File targetFile)
    {
        BufferedInputStream inBuff = null;
        BufferedOutputStream outBuff = null;
        try
        {
            // 新建文件输入流并对它进行缓冲
            inBuff = new BufferedInputStream(new FileInputStream(sourceFile));
            // 新建文件输出流并对它进行缓冲
            outBuff = new BufferedOutputStream(new FileOutputStream(targetFile));
            // 缓冲数组
            byte[] b = new byte[1024 * 5];
            int len;
            while ((len = inBuff.read(b)) != -1)
            {
                outBuff.write(b, 0, len);
            }
            // 刷新此缓冲的输出流
            outBuff.flush();
        }
        catch (Exception e)
        {

        }
        finally
        {
            // 关闭流
            try
            {
                if (inBuff != null)
                    inBuff.close();
                if (outBuff != null)
                    outBuff.close();
            }
            catch (Exception e2)
            {
                e2.printStackTrace();
            }
        }
    }

    public static String readFromFile(String filePath)
    {
        BufferedInputStream inBuff = null;
        ByteArrayOutputStream outBuff = new ByteArrayOutputStream();
        try
        {
            // 新建文件输入流并对它进行缓冲
            inBuff = new BufferedInputStream(new FileInputStream(filePath));
            // 新建文件输出流并对它进行缓冲
            // 缓冲数组
            byte[] b = new byte[1024 * 5];
            int len;
            while ((len = inBuff.read(b)) != -1)
            {
                outBuff.write(b, 0, len);
            }
            // 刷新此缓冲的输出流
            outBuff.flush();
            return new String(outBuff.toByteArray());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            // 关闭流
            try
            {
                if (inBuff != null)
                    inBuff.close();
                if (outBuff != null)
                    outBuff.close();
            }
            catch (Exception e2)
            {
                e2.printStackTrace();
            }
        }
        return null;
    }

    public static File getDir(String dir)
    {
        File fileDir = new File(dir);
        if (!fileDir.exists())
        {
            fileDir.mkdirs();
        }
        return fileDir;
    }

}