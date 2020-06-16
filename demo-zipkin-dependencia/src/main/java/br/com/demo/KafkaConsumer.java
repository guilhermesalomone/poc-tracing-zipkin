package br.com.demo;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class KafkaConsumer {

	private final static Logger LOGGER = LoggerFactory.getLogger(KafkaConsumer.class);

	private final RestTemplate restTemplate;
	
	private final KafkaProducer kafkaProducer;
	

	public KafkaConsumer(RestTemplate restTemplate, KafkaProducer kafkaProducer) {
		super();
		this.restTemplate = restTemplate;
		this.kafkaProducer = kafkaProducer;
	}




	@KafkaListener(topics = "tracingZipkinTopicDependencia", groupId = "demo-group1")
	public void receive(ConsumerRecord<String, String> consumerRecord) throws Exception {
		
		ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8002/encadeamento", String.class);
		
		kafkaProducer.send("tracingZipkinTopic2", consumerRecord.value());
		kafkaProducer.send("tracingZipkinTopicDependenciaError", consumerRecord.value());
		
		LOGGER.info("tracingTopic received payload='{}'", consumerRecord.toString());

	}
	
	@KafkaListener(topics = "tracingZipkinTopicDependenciaError", groupId = "demo-group1")
	public void receiveError(ConsumerRecord<String, String> consumerRecord) throws Exception {
		
		LOGGER.info("tracingZipkinTopicDependenciaError received payload='{}'", consumerRecord.toString());
		
		throw new Exception();
		
	}

}
