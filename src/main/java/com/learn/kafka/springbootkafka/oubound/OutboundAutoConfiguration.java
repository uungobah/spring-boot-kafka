package com.learn.kafka.springbootkafka.oubound;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication.Type;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.ProducerFactory;

/**
 * @author nurulakbar
 */
@Configuration
@EnableConfigurationProperties(OutboundProperties.class)
@ConditionalOnWebApplication(type = Type.REACTIVE)
@AutoConfigureAfter(KafkaAutoConfiguration.class)
public class OutboundAutoConfiguration {

  @Autowired
  OutboundProperties properties;

  @Bean
  @ConditionalOnClass(ProducerFactory.class)
  OutboundProducer outboundProducer(ProducerFactory<String, String> producerFactory,
      ObjectProvider<ObjectMapper> mapperProvider){

    Map<String, Object> configOverrides = properties.getPublisher().getKafka().buildProperties();
    ObjectMapper objectMapper = mapperProvider.getIfUnique(() -> {
      ObjectMapper customMapper = new ObjectMapper();
      customMapper.findAndRegisterModules();
      return customMapper;
    });

    return new DefaultOutboundProducer(producerFactory, configOverrides, objectMapper);
  }
}
