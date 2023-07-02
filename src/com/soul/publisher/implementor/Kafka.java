package com.soul.publisher.implementor;

import java.util.Properties;
import java.util.concurrent.Future;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import com.soul.publisher.api.EventPublisher;

public class Kafka implements EventPublisher {

	private String topicId = "";

	private Properties kafkaProp = new Properties();

	private KafkaProducer<String, String> producer = null;

	public Kafka() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initiatePublisher(String dataQueueName, Properties publisherProp) throws Exception {
		// TODO Auto-generated method stub
		topicId = "KafkaTopic";
		kafkaProp.put("bootstrap.servers", publisherProp);
		kafkaProp.put("acks", publisherProp);
		kafkaProp.put("retries", publisherProp);
		kafkaProp.put("batchSize", publisherProp);
		kafkaProp.put("lingerMs", publisherProp);
		kafkaProp.put("bufferMemory", publisherProp);
		kafkaProp.put("keySerializer", publisherProp);
		kafkaProp.put("valueSerializer", publisherProp);

		producer = new KafkaProducer<String, String>(kafkaProp);
	}

	@Override
	public Future<?> publish(String message) throws Exception {
		// TODO Auto-generated method stub
		ProducerRecord<String, String> record = new ProducerRecord<String, String>(topicId, topicId, message);

		Future<RecordMetadata> future = producer.send(record);

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
		RecordMetadata metadata = (RecordMetadata) future.get();
		return null;
	}

	@Override
	public Object track(Future<?> future) throws Exception {
		// TODO Auto-generated method stub
		return future.get();
	}

}
