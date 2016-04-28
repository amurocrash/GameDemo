package com.cngame.gamesdklib.http.core.downloadSample;

import java.io.Closeable;
import java.io.IOException;

public class IOUtils
{
    public static boolean close(Closeable io)
    {
        if (io != null)
        {
            try
            {
                io.close();
            }
            catch (IOException e)
            {
            }
        }
        return true;
    }
}
