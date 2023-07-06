package com.soul.client;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.common.serialization.StringSerializer;

public class TestClient {

	public TestClient() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		  Properties props = new Properties();
		  props.put("bootstrap.servers", "master:9092,slave1:9092,slave2:9092");
		  props.put("acks", "all");
		  props.put("retries", 0);
		  props.put("batch.size", 16384);
		  props.put("key.serializer", StringSerializer.class.getName());
		  props.put("value.serializer", StringSerializer.class.getName());
		  KafkaProducer<String, String> producer = new KafkaProducer<String, String>(props);
	}

}
