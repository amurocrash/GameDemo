package com.cngame.gamesdklib.http.download;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Amuro on 2016/4/25.
 */
public class DownloadManager
{
    private DownloadManager(){}

    private static DownloadManager instance;

    public static DownloadManager getInstance()
    {
        if(instance == null)
        {
            synchronized (DownloadManager.class)
            {
                if(instance == null)
                {
                    instance = new DownloadManager();
                }
            }
        }

        return instance;
    }

    private Map<String, DownloadTask> taskMap = new HashMap<>();

    public void download(String url, String localFileDir, String fileName, IDownloadListener downloadListener)
    {
        DownloadTask task = taskMap.get(url);

        if(task == null)
        {
            task = new DownloadTask(url, localFileDir, fileName, downloadListener);
            taskMap.put(url, task);
            new Thread(task).start();
        }
        else
        {

        }
    }

    public void pause(String url)
    {
        DownloadTask task = taskMap.get(url);

        if(task != null)
        {
            task.pause();
        }
    }
}
