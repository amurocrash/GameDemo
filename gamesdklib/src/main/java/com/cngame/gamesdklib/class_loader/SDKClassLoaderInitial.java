package com.cngame.gamesdklib.class_loader;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import com.alibaba.fastjson.JSONArray;
import com.cngame.gamesdklib.http.HttpSyncHelper;
import com.cngame.gamesdklib.http.core.DownloadRequest;
import com.cngame.gamesdklib.utils.FileUtil;
import com.cngame.gamesdklib.utils.LogUtils;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

import dalvik.system.DexClassLoader;

/**
 * Created by Amuro on 2016/4/9.
 */
public final class SDKClassLoaderInitial
{
    private Context context;
    private DexClassLoader sdkClassLoader;
    private File sdkJarDir;
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

    private void requestPlugins()
    {
        HttpSyncHelper hp = new HttpSyncHelper();
        Object obj = hp.invoke("http://192.168.202.64:8080/amuro/test/plugin_query", Object.class);

        List<String> urls = new ArrayList<String>();
        for (Object o : (JSONArray) obj)
        {
            urls.add((String) o);
        }

        for (String downloadUrl : urls)
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

    private void downloadPlugins(String downloadUrl)
    {
        sdkJarDir = FileUtil.getDir(
                Environment.getExternalStorageDirectory() + "/gamesdk/core/");

        String[] strs = downloadUrl.split("/");
        String fileName = strs[strs.length - 1];
        String pathName = sdkJarDir + "/" + fileName;

        File file = new File(pathName);

        DownloadRequest downloadRequest = new DownloadRequest(downloadUrl, null, file);
        downloadRequest.execute();

    }

    private void prepareFiles()
    {
        String[] jarPaths = sdkJarDir.list(new FilenameFilter()
        {
            @Override
            public boolean accept(File dir, String filename)
            {
                return filename.endsWith(".jar");
            }
        });

        if (jarPaths != null || jarPaths.length != 0)
        {
            if (pluginList == null)
            {
                pluginList = new ArrayList<String>();
            }

            for (String pluginJarName : jarPaths)
            {
                String pluginName = pluginJarName.split("\\.")[0].replaceAll("_", "");


                pluginList.add(pluginName);
            }

            for (String str : pluginList)
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

    public DexClassLoader getSDKClassLoader() throws Exception
    {
        return sdkClassLoader;
    }

    public boolean isPluginExist(String pluginName)
    {
        if (TextUtils.isEmpty(pluginName))
        {
            return false;
        }

        if (pluginList == null || pluginList.size() == 0)
        {
            return false;
        }

        for (String plugN : pluginList)
        {
            if (plugN.equals(pluginName.toLowerCase()))
            {
                return true;
            }
        }

        return false;
    }
}