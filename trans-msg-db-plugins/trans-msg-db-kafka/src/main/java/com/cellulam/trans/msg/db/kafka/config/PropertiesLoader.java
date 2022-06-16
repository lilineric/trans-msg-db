package com.cellulam.trans.msg.db.kafka.config;

import com.cellulam.trans.msg.db.common.ConfigurationProperties;
import com.cellulam.trans.msg.db.common.utils.YamlLoaderUtils;
import lombok.Data;

import java.io.InputStream;

/**
 * @author eric.li
 * @date 2022-06-15 21:18
 */
public abstract class PropertiesLoader {
    public static TransKafkaProperties loadFromResource(InputStream resourceAsStream) {
        Properties properties = YamlLoaderUtils.loadAs(resourceAsStream, Properties.class);
        return properties.getTransMsg().getKafka();
    }

    public final static class Properties extends ConfigurationProperties<Kafka> {

    }

    @Data
    public final static class Kafka {
       private TransKafkaProperties kafka;
    }
}
