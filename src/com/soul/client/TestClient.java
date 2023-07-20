package com.soul.client;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import com.aliyun.openservices.ons.api.SendResult;
import com.soul.publisher.api.PublisherApi;
import com.soul.publisher.factory.PublisherFactory;

public class TestClient {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Properties sysProperties = new Properties();
		String publisherId = "rocket.test";
		try {
			sysProperties.load(new FileReader(new File("config/system.properties")));

			String publisherPath = "";

			// 1. Obtain the publisher properties path
			System.out.println("1. Obtain the publisher properties path");
			publisherPath = sysProperties.getProperty(publisherId + ".publisher.properties.path");
			if (publisherPath == null || "".equals(publisherPath)) {
				publisherPath = sysProperties.getProperty("default.publisher.properties.path");
			}

			// 2. Get PublisherFactory instanced.

			System.out.println("2. Get PublisherFactory instanced.");
			PublisherFactory factory = new PublisherFactory(publisherPath);

			// 3. Load publisher according to the publisher ID(DataQ)
			System.out.println("3. Load publisher according to the publisher ID(DataQ)");
			PublisherApi publisher = factory.getPublisherFor(publisherId);

			// 4. Publish message
			System.out.println("4. Publish message." + new Date());
			boolean isError = true;
			ArrayList<Future<?>> list = new ArrayList<Future<?>>();
			for (int i = 0; i < 100; i++) {
				while (isError) {
					try {
						Future<?> future = publisher.publish("Soul Test " + i);
						list.add(future);
						isError = false;
					} catch (Exception e) {
						e.printStackTrace();
						TimeUnit.SECONDS.sleep(1);
					}
				}

				isError = true;
			}

			// 5. Get status
			System.out.println("Step 5.");
			for (int i = 0; i < list.size(); i++) {
				System.out.println("Result " + (i + 1) + ":" + ((SendResult) list.get(i).get()).getMessageId());
			}

			publisher.shutdown();

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
