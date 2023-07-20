package com.soul.asynch.taskpool;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

public class AsynchTaskPoolRejectExecutionHandler implements RejectedExecutionHandler {

	@Override
	public void rejectedExecution(Runnable task, ThreadPoolExecutor executor) {
		// TODO Auto-generated method stub
		boolean stopLooping = false;
		while (!stopLooping) {
			try {
				System.out.println("[warn]Traffic jam! [" + task.toString() + "] is waiting for queue !");
				executor.getQueue().put(task);
				System.out.println("[warn][" + task.toString() + "] is in the queue !");
				stopLooping = true;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				
			}
		}
	}

}
