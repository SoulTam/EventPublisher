package com.soul.publisher.basic;

import java.util.Properties;

import com.soul.publisher.api.PublisherApi;

public abstract class EventPublisher implements PublisherApi {

	public EventPublisher() {

	}

	public EventPublisher(String dataQueueName, Properties publisherProp) throws Exception {
	}
}
