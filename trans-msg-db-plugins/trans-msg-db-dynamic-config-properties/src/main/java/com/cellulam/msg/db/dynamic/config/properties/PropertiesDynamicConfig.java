package com.cellulam.msg.db.dynamic.config.properties;

import com.cellulam.trans.msg.db.spi.DynamicConfigSPI;
import com.google.common.collect.Maps;
import com.trans.db.facade.Constants;
import com.trans.db.facade.ConsumerRegister;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;
import java.util.Map;

/**
 * @author eric.li
 * @date 2022-06-12 23:12
 */
@Slf4j
public class PropertiesDynamicConfig implements DynamicConfigSPI {

    private final static Map<String, List<String>> EMPTY = Maps.newHashMap();

    private final DynamicProperties dynamicProperties;

    public PropertiesDynamicConfig() {
        this.dynamicProperties = DynamicProperties.loadFromResource(this.getClass().getClassLoader()
                .getResourceAsStream(Constants.YAML_RESOURCE_NAME));

    }

    @Override
    public List<String> getConsumers(String transType, String producer) {
        List<String> consumers = this.dynamicProperties.getConsumers().getOrDefault(producer.toLowerCase(), EMPTY)
                .get(transType.toLowerCase());
        if (CollectionUtils.isEmpty(consumers)) {
            log.warn("Cannot find consumers for [producer: {}, transType: {}]",
                    producer, transType);
        }
        return consumers;
    }

    @Override
    public List<String> getTransTypes(String producer) {
        return this.dynamicProperties.getProducerTransTypes().get(producer);
    }

    @Override
    public List<ConsumerRegister> getRegistersByConsumer(String consumer) {
        return this.dynamicProperties.getRegisterMap().get(consumer);
    }

    @Override
    public String getType() {
        return "properties";
    }

}
