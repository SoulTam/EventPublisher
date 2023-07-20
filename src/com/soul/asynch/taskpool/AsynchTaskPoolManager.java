package com.soul.asynch.taskpool;

import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class AsynchTaskPoolManager {

	private final String defaultCorePoolSize = "3";
	private final String defaultMaximumPoolSize = "5";
	private final String defaultKeepAliveTime = "10";
	private final String defaultBlockingQueueSize = "10";

	private int corePoolSize;
	private int maximumPoolSize;
	private long keepAliveTime;
	private int blockingQueueSize;
	private TimeUnit timeUnit = TimeUnit.SECONDS;
	private BlockingQueue<Runnable> workQueue = null;
	private ThreadFactory threadFactory = null;
	private RejectedExecutionHandler handler = null;
	private ThreadPoolExecutor executor = null;

	public AsynchTaskPoolManager(Properties properties) {
		// TODO Auto-generated constructor stub
		loadAttribute(properties);
		initiate();
	}

	private void loadAttribute(Properties properties) {
		corePoolSize = Integer.parseInt(properties.getProperty("core.pool.size", defaultCorePoolSize));
		maximumPoolSize = Integer.parseInt(properties.getProperty("max.pool.size", defaultMaximumPoolSize));
		keepAliveTime = Long.parseLong(properties.getProperty("keep.alive.time.second", defaultKeepAliveTime));
		blockingQueueSize = Integer.parseInt(properties.getProperty("blocking.queue.size", defaultBlockingQueueSize));
	}

	private void initiate() {
		threadFactory = new AsynchTaskThreadFactory();
		workQueue = new ArrayBlockingQueue<Runnable>(blockingQueueSize);
		handler = new AsynchTaskPoolRejectExecutionHandler();

		executor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, timeUnit, workQueue,
				threadFactory, handler);
	}

	public Future<?> submit(Callable<?> task) throws RejectedExecutionException {
		if (executor != null) {
			return executor.submit(task);
		}
		return null;
	}

	public void shutdown() {
		if (executor != null) {
			executor.shutdown();
			executor = null;
		}
	}

}
