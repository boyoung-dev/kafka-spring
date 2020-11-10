package com.haribo.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.AcknowledgingMessageListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
public class CustomAcknowledgingMessageListener implements AcknowledgingMessageListener<String, String> {

  @Override
  @KafkaListener(topics = "haribo", groupId="console-consumer-20237", containerFactory = "kafkaListenerContainerFactory")
  public void onMessage(ConsumerRecord<String, String> data, Acknowledgment acknowledgment) {
    try {
      acknowledgment.acknowledge();
    }catch (Exception e) {
      e.printStackTrace();
    }
  }
}
