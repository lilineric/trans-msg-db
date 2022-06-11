package com.cellulam.trans.db.spring.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author eric.li
 * @date 2022-06-12 00:00
 */
@Data
@ConfigurationProperties(prefix = "spring.trans.msg")
public class SpringTransProperties {

    private Message message;

    @Data
    public final static class Message {
        /**
         * thread pool size for message sending
         */
        private int sendThreadPoolSize = 10;

        /**
         * message provider type
         */
        private String providerType;
    }
}
