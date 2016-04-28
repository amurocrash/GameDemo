package com.cngame.gamesdklib.http.core.downloadSample;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.AbortPolicy;
import java.util.concurrent.TimeUnit;

public class ThreadManager
{
    public static final String DEFAULT_SINGLE_POOL_NAME = "DEFAULT_SINGLE_POOL_NAME";

    private static ThreadPoolProxy mLongPool = null;
    private static Object mLongLock = new Object();

    private static ThreadPoolProxy mShortPool = null;
    private static Object mShortLock = new Object();

    private static ThreadPoolProxy mDownloadPool = null;
    private static Object mDownloadLock = new Object();

    private static Map<String, ThreadPoolProxy> mMap = new HashMap<String, ThreadPoolProxy>();
    private static Object mSingleLock = new Object();

    public static ThreadPoolProxy getDownloadPool()
    {
        synchronized (mDownloadLock)
        {
            if (mDownloadPool == null)
            {
                mDownloadPool = new ThreadPoolProxy(3, 3, 5L);
            }
            return mDownloadPool;
        }
    }

    public static ThreadPoolProxy getLongPool()
    {
        synchronized (mLongLock)
        {
            if (mLongPool == null)
            {
                mLongPool = new ThreadPoolProxy(5, 5, 5L);
            }
            return mLongPool;
        }
    }

    public static ThreadPoolProxy getShortPool()
    {
        synchronized (mShortLock)
        {
            if (mShortPool == null)
            {
                mShortPool = new ThreadPoolProxy(2, 2, 5L);
            }
            return mShortPool;
        }
    }

    public static ThreadPoolProxy getSinglePool()
    {
        return getSinglePool(DEFAULT_SINGLE_POOL_NAME);
    }

    public static ThreadPoolProxy getSinglePool(String name)
    {
        synchronized (mSingleLock)
        {
            ThreadPoolProxy singlePool = mMap.get(name);
            if (singlePool == null)
            {
                singlePool = new ThreadPoolProxy(1, 1, 5L);
                mMap.put(name, singlePool);
            }
            return singlePool;
        }
    }

    public static class ThreadPoolProxy
    {
        private ThreadPoolExecutor mPool;
        private int mCorePoolSize;
        private int mMaximumPoolSize;
        private long mKeepAliveTime;

        private ThreadPoolProxy(int corePoolSize, int maximumPoolSize, long keepAliveTime)
        {
            mCorePoolSize = corePoolSize;
            mMaximumPoolSize = maximumPoolSize;
            mKeepAliveTime = keepAliveTime;
        }

        public synchronized void execute(Runnable run)
        {
            if (run == null)
            {
                return;
            }
            if (mPool == null || mPool.isShutdown())
            {
                mPool = new ThreadPoolExecutor(mCorePoolSize, mMaximumPoolSize, mKeepAliveTime, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(), Executors.defaultThreadFactory(), new AbortPolicy());
            }
            mPool.execute(run);
        }

        public synchronized void cancel(Runnable run)
        {
            if (mPool != null && (!mPool.isShutdown() || mPool.isTerminating()))
            {
                mPool.getQueue().remove(run);
            }
        }

        public synchronized boolean contains(Runnable run)
        {
            if (mPool != null && (!mPool.isShutdown() || mPool.isTerminating()))
            {
                return mPool.getQueue().contains(run);
            }
            else
            {
                return false;
            }
        }

        public void stop()
        {
            if (mPool != null && (!mPool.isShutdown() || mPool.isTerminating()))
            {
                mPool.shutdownNow();
            }
        }

        public synchronized void shutdown()
        {
            if (mPool != null && (!mPool.isShutdown() || mPool.isTerminating()))
            {
                mPool.shutdownNow();
            }
        }
    }
}
