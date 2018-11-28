package br.com.saquepague;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {

	private final static Logger LOGGER = LoggerFactory.getLogger(KafkaConsumer.class);


	@KafkaListener(topics = "tracingZipkinTopic", groupId = "demo-group1")
	public void receive(ConsumerRecord<String, String> consumerRecord) throws Exception {
		
		LOGGER.info("tracingZipkinTopic received payload='{}'", consumerRecord.toString());

	}

	@KafkaListener(topics = "tracingZipkinTopic2", groupId = "demo-group1")
	public void receive2(ConsumerRecord<String, String> consumerRecord) throws Exception {
		
		LOGGER.info("tracingZipkinTopic2 received payload='{}'", consumerRecord.toString());
		
	}

}
