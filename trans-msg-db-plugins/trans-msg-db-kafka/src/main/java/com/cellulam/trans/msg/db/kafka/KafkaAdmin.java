package com.cellulam.trans.msg.db.kafka;

import com.cellulam.trans.msg.db.kafka.config.TransKafkaProperties;
import com.cellulam.trans.msg.db.kafka.exceptions.MessageTopicCreateException;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.*;
import org.apache.kafka.common.errors.TopicExistsException;

import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ExecutionException;

/**
 * @author eric.li
 * @date 2022-06-15 22:13
 */
@Slf4j
public class KafkaAdmin {
    private final AdminClient adminClient;

    private final TransKafkaProperties properties;

    public KafkaAdmin(TransKafkaProperties properties) {
        this.properties = properties;

        Properties kafkaProperties = new Properties();
        kafkaProperties.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, properties.getBootstrapServers());
        this.adminClient = KafkaAdminClient.create(kafkaProperties);
    }

    public void createTopic(Set<String> topics) {
        try {
            List<NewTopic> newTopics = buildTopics(topics);
            adminClient.createTopics(newTopics, new CreateTopicsOptions().timeoutMs(10000))
                    .all()
                    .get();
        } catch (InterruptedException e) {
            throw new MessageTopicCreateException("Failed to create topic: " + topics, e);
        } catch (ExecutionException e) {
            if (e.getCause() instanceof TopicExistsException) {
                log.info("topic is exist !! " + e.getMessage());
            } else {
                throw new MessageTopicCreateException("Failed to create topic: " + topics, e);
            }
        }
    }

    private List<NewTopic> buildTopics(Set<String> topics) {
        List<NewTopic> topicList = Lists.newArrayList();

        for (String topic : topics) {
            topicList.add(new NewTopic(topic,
                    this.properties.getPartitions(),
                    this.properties.getReplication()
            ));
        }
        return topicList;
    }
}
