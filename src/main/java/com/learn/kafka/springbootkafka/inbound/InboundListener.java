package com.learn.kafka.springbootkafka.inbound;

/**
 * @author nurulakbar
 */
public interface InboundListener {

  void consume(String message);
}
