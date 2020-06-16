package br.com.demo;

import org.springframework.beans.factory.annotation.Autowired;
//import java.math.BigDecimal;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@RestController
@RequestMapping("/")
public class DemoRest {

	private final RestTemplate restTemplate;

	private final KafkaProducer kafkaProducer;
	
	@Autowired
	public DemoRest(RestTemplate restTemplate, KafkaProducer kafkaProducer) {
		super();
		this.restTemplate = restTemplate;
		this.kafkaProducer = kafkaProducer;
	}

	
	@GetMapping("/primeira-requisicao")
	public ResponseEntity<String> primeiraRequisicao() {

		kafkaProducer.send("tracingZipkinTopicDependencia", "TESTE");
		
		return ResponseEntity.ok("Hello from Spring Boot!");
	}






	@RequestMapping("/encadeamento")
	public ResponseEntity<String> encadeamento() throws Exception {
		ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8001/primeira-requisicao", String.class);
		
//		throw new Exception();
		
		return ResponseEntity.ok("Encadeamento + " + response.getBody());
	}

}
