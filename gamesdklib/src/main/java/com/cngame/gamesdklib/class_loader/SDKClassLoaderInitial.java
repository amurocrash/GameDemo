package com.cngame.gamesdklib.class_loader;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import com.alibaba.fastjson.JSONArray;
import com.cngame.gamesdklib.http_async.core.HttpError;
import com.cngame.gamesdklib.http_async.core.HttpHelper;
import com.cngame.gamesdklib.utils.FileUtil;
import com.cngame.gamesdklib.utils.LogUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import dalvik.system.DexClassLoader;

/**
 * Created by Amuro on 2016/4/9.
 */
public final class SDKClassLoaderInitial
{
    private Context context;
    private DexClassLoader sdkClassLoader;
    private String sdkJarPaths;
    private File optDir;
    private File sdkSoDir;

    private List<String> pluginList;

    public SDKClassLoaderInitial(Context context)
    {
        this.context = context;
    }

    public void init()
    {
        requestPlugins();
    }

    public DexClassLoader getSDKClassLoader() throws Exception
    {
//        requestPlugins();
//        prepareFiles();
//
//        sdkClassLoader = new DexClassLoader(
//                sdkJarPaths,
//                optDir.getAbsolutePath(),
//                sdkSoDir.getAbsolutePath(),
//                context.getClass().getClassLoader());

        return sdkClassLoader;
    }

    File sdkJarDir;
    private void prepareFiles()
    {

//        File sdkJarDir = FileUtil.getDir(
//                Environment.getExternalStorageDirectory() + "/gamesdk/core/");

        String[] jarPaths = sdkJarDir.list(new FilenameFilter()
        {
            @Override
            public boolean accept(File dir, String filename)
            {
                return filename.endsWith(".jar");
            }
        });

        if(jarPaths != null || jarPaths.length != 0)
        {
            if(pluginList == null)
            {
                pluginList = new ArrayList<String>();
            }

            for(String pluginJarName : jarPaths)
            {
                String pluginName = pluginJarName.split("\\.")[0].replaceAll("_", "");


                pluginList.add(pluginName);
            }

            for(String str : pluginList)
            {
                LogUtils.e(str);
            }
        }



        sdkJarPaths = "";
        if (jarPaths != null)
        {
            for (int i = 0; i < jarPaths.length; i++)
            {
                if (i == 0)
                {
                    sdkJarPaths += sdkJarDir.getAbsolutePath() + "/" + jarPaths[i];
                }
                else
                {
                    sdkJarPaths += ":" + sdkJarDir.getAbsolutePath() + "/" + jarPaths[i];
                }
            }
        }

        optDir = FileUtil.getDir(context.getCacheDir().getAbsolutePath() + "/gamesdk/core");

        sdkSoDir = FileUtil.getDir(
                context.getFilesDir() + "/gamesdk/core/");
    }

    private void requestPlugins()
    {
        HttpHelper hp = new HttpHelper();
        hp.invokeUrl("http://192.168.202.64:8080/amuro/test/plugin_query", new HttpHelper.OnResponseListener()
        {
            @Override
            public void onSuccess(Object obj)
            {
                List<String> urls = new ArrayList<String>();
                for(Object o : (JSONArray)obj)
                {
                    urls.add((String)o);
                }

                for(String downloadUrl : urls)
                {
                    downloadPlugins(downloadUrl);
                }

                prepareFiles();

                sdkClassLoader = new DexClassLoader(
                        sdkJarPaths,
                        optDir.getAbsolutePath(),
                        sdkSoDir.getAbsolutePath(),
                        context.getClass().getClassLoader());

                LogUtils.e("");
            }

            @Override
            public void onFailed(HttpError error)
            {
                LogUtils.e(error.getErrorMessage());
            }
        }, Object.class);
    }

    private void downloadPlugins(String downloadUrl)
    {
        FileOutputStream output = null;
        try
        {
            URL url = new URL(downloadUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            sdkJarDir = FileUtil.getDir(
                    Environment.getExternalStorageDirectory() + "/gamesdk/core/");

            String[] strs = downloadUrl.split("/");
            String fileName = strs[strs.length - 1];
            String pathName = sdkJarDir + "/" + fileName;

            File file = new File(pathName);
            InputStream input = conn.getInputStream();

//            if (file.exists())
//            {
//                System.out.println("exits");
//                return;
//            }
//            else
//            {
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
//            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                output.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    public boolean isPluginExist(String pluginName)
    {
        if(TextUtils.isEmpty(pluginName))
        {
            return false;
        }

        if(pluginList == null || pluginList.size() == 0)
        {
            return false;
        }

        for(String plugN : pluginList)
        {
            if(plugN.equals(pluginName.toLowerCase()))
            {
                return true;
            }
        }

        return false;
    }
}