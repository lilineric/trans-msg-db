package com.cellulam.trans.msg.db.kafka.config;

import lombok.Data;

/**
 * @author eric.li
 * @date 2022-06-15 15:01
 */
@Data
public class TransKafkaProperties {
    /**
     * server
     */
    private String bootstrapServers;

    /**
     * auto create topic
     */
    private boolean autoCreateTopic = true;

    /**
     * groupId
     * default same as appName
     */
    private String groupId;

    /**
     * partitions
     * default 1, need increase this value in production
     */
    private int partitions = 1;

    /**
     * replication
     * default 1, need increase this value in production
     */
    private short replication = 1;

    /**
     * only leader
     */
    private String ack = "1";

    /**
     * linger.ms
     */
    private int lingerMs = 10;

    /**
     * auto commit interval ms
     */
    private String autoCommitIntervalMs = "1000";

    /**
     * session.timeout.ms
     */
    private  int consumerSessionTimeoutMs = 10000;

    /**
     * request.timeout.ms
     */
    private int consumerRequestTimeoutMs = 30000;

    /**
     * fetch.max.wait.ms
     */
    private int fetchMaxWaitMs = 500;

    /**
     * group instance id
     *
     */
    private String groupInstanceId;
}
