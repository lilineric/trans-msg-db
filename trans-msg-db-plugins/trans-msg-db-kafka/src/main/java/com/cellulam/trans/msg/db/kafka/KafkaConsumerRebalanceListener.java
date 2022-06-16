package com.cellulam.trans.msg.db.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRebalanceListener;
import org.apache.kafka.common.TopicPartition;

import java.util.Collection;

/**
 * @author eric.li
 * @date 2022-06-16 01:57
 */
@Slf4j
public class KafkaConsumerRebalanceListener implements ConsumerRebalanceListener {
    @Override
    public void onPartitionsRevoked(Collection<TopicPartition> collection) {
        log.warn("Rebalance onPartitionsRevoked collection: {}", collection);
    }

    @Override
    public void onPartitionsAssigned(Collection<TopicPartition> collection) {
        log.warn("Rebalance onPartitionsAssigned collection: {}", collection);
    }
}
