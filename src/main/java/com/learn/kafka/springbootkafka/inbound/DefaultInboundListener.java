package com.learn.kafka.springbootkafka.inbound;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;

/**
 * @author nurulakbar
 */
public class DefaultInboundListener implements InboundListener{

  private final Logger logger = LoggerFactory.getLogger(DefaultInboundListener.class);

  @KafkaListener(topics = "users", groupId = "group_id")
  public void consume(String message){
    logger.info(String.format("#### -> Consumed message -> %s", message));
  }
}
