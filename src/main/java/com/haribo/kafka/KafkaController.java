package com.haribo.kafka;

import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KafkaController {

  private final KafkaTemplate kafkaTemplate;
  private static final String TOPIC = "haribo";

  public KafkaController(KafkaTemplate kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }

  @PostMapping("/send")
  public ResponseEntity<String> sendMessage(String message) {
    System.out.println("sending.......");
    kafkaTemplate.send(TOPIC, message);
    System.out.println(message);
    return ResponseEntity.ok("");
  }
}
