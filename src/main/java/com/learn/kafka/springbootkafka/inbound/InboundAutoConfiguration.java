package com.learn.kafka.springbootkafka.inbound;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author nurulakbar
 */

@Configuration
public class InboundAutoConfiguration {

  @Bean
  InboundListener defaultInboundListener(){
    return new DefaultInboundListener();
  }
}
