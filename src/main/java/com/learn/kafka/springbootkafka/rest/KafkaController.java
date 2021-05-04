package com.learn.kafka.springbootkafka.rest;

import com.learn.kafka.springbootkafka.oubound.DefaultOutboundProducer;
import com.learn.kafka.springbootkafka.oubound.OutboundProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author nurulakbar
 */
@RestController
@RequestMapping(value = "/kafka")
public class KafkaController {

  private final OutboundProducer defaultOutboundProducer;

  @Autowired
  public KafkaController(OutboundProducer outboundProducer) {
    this.defaultOutboundProducer = outboundProducer;
  }

  @PostMapping(value = "/publish")
  public void sendMessageToKafkaTopic(@RequestParam("message") String message) {
    this.defaultOutboundProducer.sendMessage(message);
  }
}
