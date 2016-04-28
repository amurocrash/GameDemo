package com.cngame.gamesdklib.http.core.downloadSample;

import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DownloadManager
{
    public static final int STATE_NONE = 0;
    public static final int STATE_WAITING = 1;
    public static final int STATE_DOWNLOADING = 2;
    public static final int STATE_PAUSED = 3;
    public static final int STATE_DOWNLOADED = 4;
    public static final int STATE_ERROR = 5;

    private static DownloadManager instance;

    private DownloadManager()
    {
    }

    private Map<Long, DownloadInfo> mDownloadMap = new ConcurrentHashMap<Long, DownloadInfo>();
    private List<DownloadObserver> mObservers = new ArrayList<DownloadObserver>();
    private Map<Long, DownloadTask> mTaskMap = new ConcurrentHashMap<Long, DownloadTask>();

    public static synchronized DownloadManager getInstance()
    {
        if (instance == null)
        {
            instance = new DownloadManager();
        }
        return instance;
    }

    public void registerObserver(DownloadObserver observer)
    {
        synchronized (mObservers)
        {
            if (!mObservers.contains(observer))
            {
                mObservers.add(observer);
            }
        }
    }

    public void unRegisterObserver(DownloadObserver observer)
    {
        synchronized (mObservers)
        {
            if (mObservers.contains(observer))
            {
                mObservers.remove(observer);
            }
        }
    }

    public void notifyDownloadStateChanged(DownloadInfo info)
    {
        synchronized (mObservers)
        {
            for (DownloadObserver observer : mObservers)
            {
                observer.onDownloadStateChanged(info);
            }
        }
    }

    public void notifyDownloadProgressed(DownloadInfo info)
    {
        synchronized (mObservers)
        {
            for (DownloadObserver observer : mObservers)
            {
                observer.onDownloadProgressed(info);
            }
        }
    }

    public synchronized void download(AppInfo appInfo)
    {
        DownloadInfo info = mDownloadMap.get(appInfo.getId());
        if (info == null)
        {
            info = DownloadInfo.clone(appInfo);
            mDownloadMap.put(appInfo.getId(), info);
        }
        if (info.getDownloadState() == STATE_NONE
                || info.getDownloadState() == STATE_PAUSED
                || info.getDownloadState() == STATE_ERROR)
        {
            info.setDownloadState(STATE_WAITING);
            notifyDownloadStateChanged(info);
            DownloadTask task = new DownloadTask(info);
            mTaskMap.put(info.getId(), task);
            ThreadManager.getDownloadPool().execute(task);
        }
    }

    public synchronized void pause(AppInfo appInfo)
    {
        stopDownload(appInfo);
        DownloadInfo info = mDownloadMap.get(appInfo.getId());
        if (info != null)
        {
            info.setDownloadState(STATE_PAUSED);
            notifyDownloadStateChanged(info);
        }
    }

    public synchronized void cancel(AppInfo appInfo)
    {
        stopDownload(appInfo);
        DownloadInfo info = mDownloadMap.get(appInfo.getId());
        if (info != null)
        {
            info.setDownloadState(STATE_NONE);
            notifyDownloadStateChanged(info);
            info.setCurrentSize(0);
            File file = new File(info.getPath());
            file.delete();
        }
    }

//    public synchronized void install(AppInfo appInfo)
//    {
//        stopDownload(appInfo);
//        DownloadInfo info = mDownloadMap.get(appInfo.getId());
//        if (info != null)
//        {
//            Intent installIntent = new Intent(Intent.ACTION_VIEW);
//            installIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            installIntent.setDataAndType(Uri.parse("file://" + info.getPath()),
//                    "application/vnd.android.package-archive");
//            AppUtil.getContext().startActivity(installIntent);
//        }
//        notifyDownloadStateChanged(info);
//    }
//
//    public synchronized void open(AppInfo appInfo)
//    {
//        try
//        {
//            Context context = AppUtil.getContext();
//            Intent intent = context.getPackageManager()
//                    .getLaunchIntentForPackage(appInfo.getPackageName());
//            context.startActivity(intent);
//        }
//        catch (Exception e)
//        {
//        }
//    }

    private void stopDownload(AppInfo appInfo)
    {
        DownloadTask task = mTaskMap.remove(appInfo.getId());
        if (task != null)
        {
            ThreadManager.getDownloadPool().cancel(task);
        }
    }

    public synchronized DownloadInfo getDownloadInfo(long id)
    {
        return mDownloadMap.get(id);
    }

    public synchronized void setDownloadInfo(long id, DownloadInfo info)
    {
        mDownloadMap.put(id, info);
    }

    public class DownloadTask implements Runnable
    {
        private DownloadInfo info;

        public DownloadTask(DownloadInfo info)
        {
            this.info = info;
        }

        @Override
        public void run()
        {
            info.setDownloadState(STATE_DOWNLOADING);// 先改变下载状态
            notifyDownloadStateChanged(info);
            File file = new File(info.getPath());// 获取下载文件

            try
            {
                URL url = new URL(info.getUrl());
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(30000);
                conn.setReadTimeout(30000);
                if (!file.exists())
                {
                    info.setCurrentSize(0);
                    file.delete();
                }
                else if (file.length() > info.getAppSize())
                {
                    info.setCurrentSize(0);
                    file.delete();
                }
                else if (file.length() == info.getAppSize())
                {

                }
                else if (file.length() < info.getAppSize())
                {
                    info.setCurrentSize(file.length());
                }
                if (info.getCurrentSize() == 0 || !file.exists() || file.length() != info.getCurrentSize())
                {
                    // 如果文件不存在，或者进度为0，或者进度和文件长度不相符，就需要重新下载
                    info.setCurrentSize(0);
                    file.delete();
                }
                else if (file.length() == info.getCurrentSize() && file.length() < info.getAppSize())
                {
                    conn.setRequestProperty("Range", "bytes=" + info.getCurrentSize() + "-" + info.getAppSize());
                }

                int code = conn.getResponseCode();
                RandomAccessFile raf = new RandomAccessFile(file, "rw");
                InputStream is = conn.getInputStream();
                byte[] buffer = new byte[1024 * 8];
                int len = -1;
                int total = 0;// 当前线程下载的总的数据的长度

                if (code == 200)
                {
                }
                else if (code == 206)
                {
                    raf.seek(file.length());
                }
                while (((len = is.read(buffer)) != -1) && (info.getDownloadState() == STATE_DOWNLOADING))
                {
                    // 下载数据的过程。
                    raf.write(buffer, 0, len);
                    total += len;// 需要记录当前的数据。
                    info.setCurrentSize(info.getCurrentSize() + len);
                    notifyDownloadProgressed(info);// 刷新进度
                }
                is.close();
                raf.close();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            if (info.getCurrentSize() == info.getAppSize())
            {
                info.setDownloadState(STATE_DOWNLOADED);
                notifyDownloadStateChanged(info);
            }
            else if (info.getDownloadState() == STATE_PAUSED)
            {
                notifyDownloadStateChanged(info);
            }
            else
            {
                info.setDownloadState(STATE_ERROR);
                notifyDownloadStateChanged(info);
                info.setCurrentSize(0);// 错误状态需要删除文件
                file.delete();
            }

            mTaskMap.remove(info.getId());
        }

    }

    public interface DownloadObserver
    {

        void onDownloadStateChanged(DownloadInfo info);

        void onDownloadProgressed(DownloadInfo info);
    }

}
