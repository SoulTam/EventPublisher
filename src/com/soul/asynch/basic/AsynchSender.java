package com.soul.asynch.basic;

import java.util.concurrent.Callable;
import com.soul.publisher.api.PublisherApi;

public abstract class AsynchSender<V> implements Callable<V> {

	protected PublisherApi publisher = null;
	protected String message = null;

	public AsynchSender(PublisherApi publisher, String message) {
		this.publisher = publisher;
		this.message = message;
	}

	@Override
	public V call() throws Exception {
		// TODO Auto-generated method stub
		try {
			return send();
		} catch (Exception e) {
			return send();
		}
	}

	/**
	 * To define the send method which need to executed in asynch mode
	 * 
	 * @param message
	 * @return
	 */
	protected abstract V send() throws Exception;

}
