package com.learn.kafka.springbootkafka.oubound;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import lombok.Data;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.boot.context.properties.PropertyMapper;
import org.springframework.util.unit.DataSize;
import org.springframework.validation.annotation.Validated;

/**
 * @author nurulakbar
 */
@Data
@Validated
@ConfigurationProperties("com.uun.gobah.outbound")
public class OutboundProperties {

  @Valid
  @NestedConfigurationProperty
  private final PublisherSetting publisher = new PublisherSetting();

  @Data
  @Validated
  static class PublisherSetting {

    @Valid
    private final KafkaPublisher kafka = new KafkaPublisher();
  }

  @Data
  @Validated
  static class KafkaPublisher {

    @NotBlank
    private String clientId = "com-uun-gobah-publisher";
    private String ackCount;

    @NotEmpty
    private List<String> bootstrapServers = Collections.singletonList("localhost:9092");
    private Map<String, String> properties = new HashMap<>();
    private DataSize batchSize;
    private DataSize bufferMemory;
    private Integer retries;
    private String compressionType;
    private Class<?> keySerializer = StringSerializer.class;
    private Class<?> valueSerializer = StringSerializer.class;
    private Long reconnectBackoffMsConfig = 50L;
    private Long reconnectBackoffMaxMsConfig = 1000L;
    private Long maxBlockMsConfig = 60 * 1000L;
    private Integer transactionTimeoutConfig = 60000;
    private Integer requestTimeoutMsConfig = 30000;
    private Integer deliveryTimeoutMsConfig = 120 * 1000;


    public Map<String, Object> buildProperties () {

      ProducerProperties properties = new ProducerProperties();

      PropertyMapper mapper = PropertyMapper.get().alwaysApplyingWhenNonNull();
      mapper.from(this::getClientId).to(properties.in(ProducerConfig.CLIENT_ID_CONFIG));
      mapper.from(this::getAckCount).to(properties.in(ProducerConfig.ACKS_CONFIG));
      mapper.from(this::getBootstrapServers).to(properties.in(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG));
      mapper.from(this::getBatchSize).asInt(DataSize::toBytes).to(properties.in(ProducerConfig.BATCH_SIZE_CONFIG));
      mapper.from(this::getBufferMemory).as(DataSize::toBytes).to(properties.in(ProducerConfig.BUFFER_MEMORY_CONFIG));
      mapper.from(this::getCompressionType).to(properties.in(ProducerConfig.COMPRESSION_TYPE_CONFIG));
      mapper.from(this::getKeySerializer).to(properties.in(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG));
      mapper.from(this::getValueSerializer).to(properties.in(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG));
      mapper.from(this::getRetries).to(properties.in(ProducerConfig.RETRIES_CONFIG));
      mapper.from(this::getReconnectBackoffMsConfig).to(properties.in(ProducerConfig.RECONNECT_BACKOFF_MS_CONFIG));
      mapper.from(this::getReconnectBackoffMaxMsConfig).to(properties.in(ProducerConfig.RECONNECT_BACKOFF_MAX_MS_CONFIG));
      mapper.from(this::getMaxBlockMsConfig).to(properties.in(ProducerConfig.MAX_BLOCK_MS_CONFIG));
      mapper.from(this::getTransactionTimeoutConfig).to(properties.in(ProducerConfig.TRANSACTION_TIMEOUT_CONFIG));
      mapper.from(this::getRequestTimeoutMsConfig).to(properties.in(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG));
      mapper.from(this::getDeliveryTimeoutMsConfig).to(properties.in(ProducerConfig.DELIVERY_TIMEOUT_MS_CONFIG));

      properties.putAll(this.properties);
      return properties;
    }

  }


  /**
   * Utility type to simplify property mapping using {@link PropertyMapper}.
   */
  private static class ProducerProperties extends HashMap<String, Object> {
    <V> Consumer<V> in(String key) {
      return (value) -> put(key, value);
    }
  }
}
