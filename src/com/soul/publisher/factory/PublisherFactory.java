package com.soul.publisher.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import com.soul.publisher.api.EventPublisher;

public class PublisherFactory {

	private Properties publisherProperties = null;

	public PublisherFactory(String propertiesPath) throws FileNotFoundException, IOException {
		// TODO Auto-generated constructor stub
		publisherProperties = new Properties();
		publisherProperties.load(new FileInputStream(new File(propertiesPath)));
	}

	public EventPublisher getPublisherFor(String publisherId) throws Exception {

		if (publisherProperties != null) {

			Class<?> publisherObject = Class
					.forName(publisherProperties.getProperty(publisherId + ".publisher.class"));

			EventPublisher publisher = (EventPublisher) publisherObject.newInstance();
			publisher.initiatePublisher(publisherId, publisherProperties);

			return publisher;
		}
		return null;

	}

	public Properties getPublisherProperties() {
		return this.publisherProperties;
	}

	public void setPublisherProperties(Properties publisherProp) {
		this.publisherProperties = publisherProp;
	}

}
