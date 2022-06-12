package com.cellulam.msg.db.dynamic.config.properties;

import com.cellulam.msg.db.dynamic.config.properties.config.ConsumerProperties;
import com.cellulam.trans.msg.db.core.spi.DynamicConfigSPI;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author eric.li
 * @date 2022-06-12 23:12
 */
@Component
@Slf4j
public class PropertiesDynamicConfig implements DynamicConfigSPI {

    private final static Map<String, List<String>> EMPTY = Maps.newHashMap();

    @Autowired
    private ConsumerProperties consumerProperties;

    @Override
    public List<String> getConsumers(String transType, String producer) {
        List<String> consumers = consumerProperties.getConsumers().getOrDefault(producer.toLowerCase(), EMPTY)
                .get(transType.toLowerCase());
        if (CollectionUtils.isEmpty(consumers)) {
            log.warn("Cannot find consumers for [producer: {}, transType: {}]",
                    producer, transType);
        }
        return consumers;
    }

    @Override
    public String getType() {
        return "properties";
    }
}
