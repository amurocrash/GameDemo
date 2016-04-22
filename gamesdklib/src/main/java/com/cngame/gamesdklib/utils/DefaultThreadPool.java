package com.cngame.gamesdklib.utils;

import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class DefaultThreadPool
{
	// 阻塞队列最大任务数量
	private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
	private static final int BLOCKING_QUEUE_SIZE = 128;
	private static final int THREAD_POOL_MAX_SIZE = CPU_COUNT * 2 + 1;
	private static final int THREAD_POOL_SIZE = CPU_COUNT + 1;
	private static final long KEEP_ALIVE_TIME = 1;
	
	private static DefaultThreadPool instance;
	
	public static DefaultThreadPool getInstance()
	{
		if(instance == null)
		{
			synchronized (DefaultThreadPool.class)
			{
				if(instance == null)
				{
					instance = new DefaultThreadPool();
				}
			}
		}
		return instance;
	}

	private ArrayBlockingQueue<Runnable> blockingTaskQueue;
	
	private AbstractExecutorService threadPool; 
	
	private DefaultThreadPool()
	{
		blockingTaskQueue = new ArrayBlockingQueue<Runnable>(BLOCKING_QUEUE_SIZE);
		threadPool = new ThreadPoolExecutor(
				THREAD_POOL_SIZE, 
				THREAD_POOL_MAX_SIZE, 
				KEEP_ALIVE_TIME, 
				TimeUnit.SECONDS, 
				blockingTaskQueue, 
				new ThreadPoolExecutor.DiscardOldestPolicy());
	}
	
	public void execute(Runnable r)
	{
		if(r == null)
			return;
		
		try
		{
			threadPool.execute(r);
		}
		catch(Exception e)
		{
			
		}
	}
	
	public void removeAllTask() 
	{
		blockingTaskQueue.clear();
	}

	public void removeTaskFromQueue(final Object obj) 
	{
		blockingTaskQueue.remove(obj);
	}

	public void shutdown() 
	{
		if (threadPool != null)
		{
			threadPool.shutdown();
		}
	}

	public void shutdownRightnow()
	{
		if (threadPool != null)
		{
			threadPool.shutdownNow();
			try
			{
				threadPool.awaitTermination(1, TimeUnit.MICROSECONDS);
			}
			catch (final InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}
}
































