package com.cellulam.trans.msg.db.kafka;

import com.cellulam.trans.msg.db.kafka.builder.TopicBuilder;
import com.cellulam.trans.msg.db.kafka.config.TransKafkaProperties;
import com.cellulam.trans.msg.db.spi.contract.MessageSender;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

/**
 * @author eric.li
 * @date 2022-06-15 14:27
 */
@Slf4j
public class KafkaMQSender implements MessageSender {

    private Producer<String, String> producer;
    private final String topicPrefix;

    public KafkaMQSender(String topicPrefix, TransKafkaProperties properties) {
        this.topicPrefix = topicPrefix;
        this.producer = new KafkaProducer<>(this.buildKafkaProperties(properties));
    }

    @Override
    public void send(String rootSource, String transType, String message) {
        ProducerRecord<String, String> record = this.buildProducerRecord(rootSource, transType, message);
        producer.send(record, (metadata, exception) -> {
            if (exception == null) {
                log.debug("Send message success, topic:{}, message: {}, metadata: {}",
                        record.topic(), record.value(), metadata);
            } else {
                log.error(String.format("Failed to send message, topic: %s, message: %s, metadata: %s",
                        record.topic(), record.value(), metadata), exception);
            }
        });
    }

    private ProducerRecord<String, String> buildProducerRecord(String rootSource, String transType, String message) {
        return new ProducerRecord<>(TopicBuilder.buildTopic(this.topicPrefix, rootSource, transType), message);
    }

    private Properties buildKafkaProperties(TransKafkaProperties properties) {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, properties.getBootstrapServers());
        props.put(ProducerConfig.ACKS_CONFIG, properties.getAck());
        props.put(ProducerConfig.RETRIES_CONFIG, 0);
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);
        props.put(ProducerConfig.LINGER_MS_CONFIG, properties.getLingerMs());
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        return props;
    }

}
