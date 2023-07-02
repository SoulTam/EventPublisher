package com.soul.publisher.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import com.soul.publisher.api.EventPublisher;

public class PublisherFactory {

//	private static volatile PublisherFactory factory = null;

	private Properties publisherProperties = null;

	public PublisherFactory(String propertiesPath) throws FileNotFoundException, IOException {
		// TODO Auto-generated constructor stub
		publisherProperties = new Properties();
		publisherProperties.load(new FileInputStream(new File(propertiesPath)));
	}
//
//	public static PublisherFactory getInstance(String propertiesPath) throws FileNotFoundException, IOException {
//		if (factory == null) {
//			synchronized (PublisherFactory.class) {
//				if (factory == null) {
//					factory = new PublisherFactory(propertiesPath);
//				}
//			}
//		}
//		return factory;
//	}

	public EventPublisher createPublisherByName(String publisherName) throws Exception {

		Class<?> publisherObject = Class.forName(publisherName);

		EventPublisher publisher = (EventPublisher) publisherObject.newInstance();
		publisher.initiatePublisher(publisherName, publisherProperties);

		return publisher;
	}

	public Properties getPublisherProperties() {
		return this.publisherProperties;
	}

	public void setPublisherProperties(Properties publisherProp) {
		this.publisherProperties = publisherProp;
	}

}
