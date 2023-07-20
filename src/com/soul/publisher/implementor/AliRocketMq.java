package com.soul.publisher.implementor;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.OnExceptionContext;
import com.aliyun.openservices.ons.api.Producer;
import com.aliyun.openservices.ons.api.PropertyKeyConst;
import com.aliyun.openservices.ons.api.SendCallback;
import com.aliyun.openservices.ons.api.SendResult;
import com.soul.asynch.rocketmq.AliRocketMqAsynchSender;
import com.soul.asynch.taskpool.AsynchTaskPoolManager;
import com.soul.properties.PropertiesConfiger;
import com.soul.publisher.api.PublisherApi;
import com.soul.publisher.basic.EventPublisher;

public class AliRocketMq implements PublisherApi {
	private final String publicId = "public";

	private Properties prop = null;
	private Properties publicProp = null;
	private Producer producer = null;

	private AsynchTaskPoolManager poolManager = null;

	private void startAsynchTaskPool() {
		poolManager = new AsynchTaskPoolManager(publicProp);
	}

	private void startProducer() {
		System.out.println("RocketMQ starting...");
		producer = ONSFactory.createProducer(prop);
		producer.start();
		try {
			TimeUnit.SECONDS.sleep(5);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("RocketMQ is in service...");
	}

	@Override
	public void initiatePublisher(String dataQueueName, Properties publisherProp) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("Publisher of [" + dataQueueName + "] loading...");
		prop = PropertiesConfiger.installProperties(publisherProp, dataQueueName);
		publicProp = PropertiesConfiger.installProperties(publisherProp, publicId);

		refresh();
	}

	@Override
	public Future<?> publish(String message) throws Exception {
		// TODO Auto-generated method stub
		return sendAsynch(message);
	}

	public SendResult send(String message) {
		Message mqMessage = new Message(prop.getProperty("topic"), prop.getProperty("tag"), prop.getProperty("key"),
				message.getBytes());
		try {
			return producer.send(mqMessage);
		} catch (Exception e) {
			System.out.println("Server is not ready, retry once for the request.");
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e1) {
				// do nothing
			}
			return producer.send(mqMessage);
		}
	}

	/**
	 * Send message in asynch mode
	 * 
	 * @param message
	 * @return
	 * @throws Exception
	 */
	private Future<?> sendAsynch(String message) throws Exception {
		AliRocketMqAsynchSender asynchSender = new AliRocketMqAsynchSender(this, message);
		return poolManager.submit(asynchSender);
	}

	@Override
	public String trackId(Future<?> future) throws Exception {
		// TODO Auto-generated method stub
		SendResult result = (SendResult) track(future);
		if (result != null) {
			return result.getMessageId();
		}
		return "S" + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
	}

	@Override
	public Object track(Future<?> future) throws Exception {
		// TODO Auto-generated method stub
		if (future == null) {
			return null;
		}
		return future.get();
	}

	@Override
	public void shutdown() throws Exception {
		// TODO Auto-generated method stub
		System.out.println("Publisher is shutting down.");
		producer.shutdown();
		producer = null;
		poolManager.shutdown();
		poolManager = null;
	}

	public void refresh() {
		startProducer();
		startAsynchTaskPool();
	}

}
