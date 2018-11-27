package br.com.saquepague;

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
	
	public KafkaConsumer(RestTemplate restTemplate) {
		super();
		this.restTemplate = restTemplate;
	}



	@KafkaListener(topics = "tracingZipkinTopicDependencia", groupId = "demo-group1")
	public void receive(ConsumerRecord<String, String> consumerRecord) throws Exception {
		
		ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8002/saqueepague/encadeamento", String.class);
		
		LOGGER.info("tracingTopic received payload='{}'", consumerRecord.toString());

	}

}
