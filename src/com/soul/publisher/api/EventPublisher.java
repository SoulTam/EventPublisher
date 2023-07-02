package com.soul.publisher.api;

import java.util.Properties;
import java.util.concurrent.Future;

public interface EventPublisher {
	public void initiatePublisher(String dataQueueName, Properties publisherProp) throws Exception;

	public Future<?> publish(String message) throws Exception;

	public String trackId(Future<?> future) throws Exception;

	public Object track(Future<?> future) throws Exception;

	public void shutdown() throws Exception;
}
