package com.haribo.kafka;

import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.ContainerProperties.AckMode;


@Configuration
@EnableKafka
public class KafkaConfig {

  private static final String SERVERS_CONFIG = "localhost:9092";

  @Bean
  public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
    ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
    factory.setConsumerFactory(consumerFactory());
    factory.getContainerProperties().setAckMode(AckMode.MANUAL);
    factory.setConcurrency(3);
    factory.getContainerProperties().setPollTimeout(5000);
    factory.getContainerProperties().setMessageListener(customAcknowledgingMessageListener());

    return factory;
  }

  public CustomAcknowledgingMessageListener customAcknowledgingMessageListener() {
    return new CustomAcknowledgingMessageListener();
  }

  public ConsumerFactory<String, String> consumerFactory() {
    return new DefaultKafkaConsumerFactory<>(consumerConfig());
  }

  @Bean
  public KafkaTemplate<String, String> kafkaTemplate() {
    return new KafkaTemplate<>(new DefaultKafkaProducerFactory<>(producerConfig()));
  }


  private static Map<String, Object> producerConfig() {
    Map<String, Object> properties = new HashMap<>();

    properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, SERVERS_CONFIG);
    properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
    properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

    return properties;
  }

  private static Map<String, Object> consumerConfig() {
    Map<String, Object> properties = new HashMap<>();

    properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, SERVERS_CONFIG);
    properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

    return properties;
  }
}
