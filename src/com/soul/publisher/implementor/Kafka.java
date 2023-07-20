package com.soul.publisher.implementor;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.Future;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import com.soul.properties.PropertiesConfiger;
import com.soul.publisher.api.PublisherApi;

public class Kafka implements PublisherApi {

//	private String topicId = "";

	private Properties kafkaProp = new Properties();

	private KafkaProducer<String, String> producer = null;

	public Kafka() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initiatePublisher(String publisherId, Properties publisherProp) throws Exception {
		// TODO Auto-generated method stub

		kafkaProp = PropertiesConfiger.installProperties(publisherProp, publisherId);

		kafkaStartup();
	}

	@Override
	public Future<?> publish(String message) throws Exception {
		// TODO Auto-generated method stub
		ProducerRecord<String, String> record = new ProducerRecord<String, String>(this.kafkaProp.getProperty("topic"),
				this.kafkaProp.getProperty("key"), message);

		Future<RecordMetadata> future;

		try {
			future = producer.send(record);
		} catch (IllegalStateException e) {
			kafkaStartup();
			future = producer.send(record);
		}

		return future;
	}

	@Override
	public void shutdown() throws Exception {
		// TODO Auto-generated method stub
		if (producer != null) {
			producer.flush();
			producer.close();
			producer = null;
		}
	}

	@Override
	public String trackId(Future<?> future) throws Exception {
		// TODO Auto-generated method stub
		RecordMetadata metadata = (RecordMetadata) track(future);

		StringBuilder messageId = new StringBuilder();

		if (metadata.hasTimestamp()) {
			messageId.append(new SimpleDateFormat("HHmmss").format(new Date(metadata.timestamp())));
		} else {
			messageId.append("S").append(new SimpleDateFormat("HHmmss").format(new Date()));
		}
		
		if(metadata.hasOffset()) {
			messageId.append(metadata.offset());
		}

		return messageId.toString();
	}

	@Override
	public Object track(Future<?> future) throws Exception {
		// TODO Auto-generated method stub
		Object obj;

		try {
			obj = future.get();
		} catch (IllegalStateException e) {
			kafkaStartup();
			obj = future.get();
		}
		return obj;
	}

	/**
	 * To start producer with current setup
	 */
	private void kafkaStartup() {
		producer = new KafkaProducer<String, String>(kafkaProp);
	}

	@Override
	public Object send(String message) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
