package com.cngame.gamesdklib.http.core;

import com.cngame.gamesdklib.http.utils.BaseEntityUtils;
import com.cngame.gamesdklib.utils.LogUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Map;

/**
 * Created by Amuro on 2016/4/22.
 */
public class DownloadRequest extends HttpRequestGet
{
    private File file;

    public DownloadRequest(String baseUrl, Map<String, String> paramMap, File file)
    {
        super(baseUrl, paramMap, 0);
        this.file = file;
    }

    @Override
    protected BaseEntity disposeResponse() throws Exception
    {
        InputStream input = conn.getInputStream();
        FileOutputStream output = null;

        if (file.exists())
        {
            LogUtils.e(file.getName() + " is existed.");

        }
        else
        {
            file.createNewFile();//新建文件
            output = new FileOutputStream(file);
            //读取大文件
            byte[] buffer = new byte[4 * 1024];
            int len = 0;
            while ((len = input.read(buffer)) != -1)
            {
                output.write(buffer, 0, len);
            }
            output.flush();
            output.close();
        }

        return BaseEntityUtils.getSuccessBaseEntity();
    }
}
