package com.cellulam.trans.msg.db.kafka.builder;

import org.apache.commons.lang3.StringUtils;

/**
 * @author eric.li
 * @date 2022-06-15 17:02
 */
public abstract class TopicBuilder {

    public static String buildTopicPrefix() {
        return "";
    }

    public static String buildAckTopicPrefix() {
        return "ack";
    }

    public static String buildTopic(String prefix, String rootSource, String transType) {
        return (StringUtils.isEmpty(prefix) ? "" : prefix + ".")
                + rootSource + "." + transType;
    }
}
