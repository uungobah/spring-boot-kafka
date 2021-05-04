package com.learn.kafka.springbootkafka.oubound;

import reactor.core.publisher.Mono;

/**
 * @author nurulakbar
 */
public interface OutboundProducer {

  void sendMessage(String message);
}
