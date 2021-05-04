package com.learn.kafka.springbootkafka.oubound;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.converter.ByteArrayJsonMessageConverter;
import org.springframework.messaging.Message;
import org.springframework.util.Assert;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

/**
 * @author nurulakbar
 */
@Slf4j
public class DefaultOutboundProducer implements OutboundProducer {

  private static final String TOPIC = "users";


  private final KafkaTemplate<String, String> kafkaTemplate;


  public DefaultOutboundProducer(ProducerFactory<String, String> producerFactory,
      Map<String, Object> configOverrides,
      ObjectMapper objectMapper) {
    Assert.notNull(producerFactory, "producerFactory must be provided");
    Assert.notNull(configOverrides, "configOverrides must be provided");
    Assert.notNull(objectMapper, "objectMapper must be provided");
    this.kafkaTemplate = new KafkaTemplate<>(producerFactory, configOverrides);
    kafkaTemplate.setMessageConverter(new ByteArrayJsonMessageConverter(objectMapper));
  }

  @Override
  public void sendMessage(String msg) {
    log.info(String.format("#### -> Producing Message -> %s", msg));

    kafkaTemplate.send(TOPIC, msg);

  }

}
