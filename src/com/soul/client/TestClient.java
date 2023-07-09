package com.soul.client;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.Future;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.common.serialization.StringSerializer;

import com.soul.publisher.api.EventPublisher;
import com.soul.publisher.factory.PublisherFactory;

public class TestClient {

	public TestClient() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Properties sysProperties = new Properties();
		String publisherId = "SYDHUBJP.EVENTQ";
		try {
			sysProperties.load(new FileReader(new File("config/system.properties")));
			
			String publisherPath = "";
			
			// 1. Obtain the publisher properties path
			publisherPath = sysProperties.getProperty(publisherId+".publisher.properties.path");
			if(publisherPath == null || "".equals(publisherPath)) {
				publisherPath = sysProperties.getProperty("default.publisher.properties.path");
			}
			
			// 2. Get PublisherFactory instanced.
			PublisherFactory factory = new PublisherFactory(publisherPath);
			
			// 3. Load publisher according to the publisher ID(DataQ)
			EventPublisher publisher = factory.getPublisherFor(publisherId);
			
			// 4. Publish message
			Future<?> future = publisher.publish("Soul Test");
			
			// 5. Get status
			System.out.println(publisher.trackId(future));
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
