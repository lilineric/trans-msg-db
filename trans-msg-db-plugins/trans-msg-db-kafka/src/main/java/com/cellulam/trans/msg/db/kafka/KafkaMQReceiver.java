package com.cellulam.trans.msg.db.kafka;

import com.cellulam.trans.msg.db.kafka.config.TransKafkaProperties;
import com.cellulam.trans.msg.db.spi.contract.MessageProcessor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.List;
import java.util.Properties;

/**
 * @author eric.li
 * @date 2022-06-15 16:17
 */
@Slf4j
public class KafkaMQReceiver {
    private final MessageProcessor messageProcessor;
    private final Consumer<String, String> consumer;
    private final String groupInstanceId;

    public KafkaMQReceiver(List<String> topics, TransKafkaProperties properties, String groupInstanceId, MessageProcessor messageProcessor) {
        this.groupInstanceId = groupInstanceId;
        this.messageProcessor = messageProcessor;
        this.consumer = new KafkaConsumer<>(buildKafkaConsumerProperties(properties));
        consumer.subscribe(topics, new KafkaConsumerRebalanceListener());
    }

    public void start() {
        new Thread(() -> this.start(this.consumer, this.messageProcessor))
                .start();
    }

    private void start(final Consumer<String, String> consumer, final MessageProcessor messageProcessor) {
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));
            for (ConsumerRecord<String, String> record : records) {
                log.debug("Receive message success, offset = {}, key = {}, value = {}",
                        record.offset(), record.key(), record.value());
                messageProcessor.process(record.value());
            }
            consumer.commitAsync();
        }
    }

    private Properties buildKafkaConsumerProperties(TransKafkaProperties properties) {
        Properties kafkaProperties = new Properties();
        kafkaProperties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, properties.getBootstrapServers());
        kafkaProperties.put(ConsumerConfig.GROUP_ID_CONFIG, properties.getGroupId());
        kafkaProperties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        kafkaProperties.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, properties.getAutoCommitIntervalMs());
        kafkaProperties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        kafkaProperties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        kafkaProperties.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, properties.getConsumerSessionTimeoutMs());
        kafkaProperties.put(ConsumerConfig.REQUEST_TIMEOUT_MS_CONFIG, properties.getConsumerRequestTimeoutMs());
        kafkaProperties.put(ConsumerConfig.FETCH_MAX_WAIT_MS_CONFIG, properties.getFetchMaxWaitMs());
        kafkaProperties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        if(StringUtils.isNotEmpty(groupInstanceId)) {
            kafkaProperties.put(ConsumerConfig.GROUP_INSTANCE_ID_CONFIG, groupInstanceId);
        }
        return kafkaProperties;
    }

}
