package com.soul.asynch.taskpool;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class AsynchTaskThreadFactory implements ThreadFactory {

	private final AtomicInteger threadNumber = new AtomicInteger(1);

	@Override
	public Thread newThread(Runnable runnable) {
		// TODO Auto-generated method stub
		Thread thread = new Thread(runnable, "asynch-task" + threadNumber);
		threadNumber.incrementAndGet();
		return thread;
	}

}
