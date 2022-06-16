package com.cellulam.msg.db.dynamic.config.properties;

import com.cellulam.trans.msg.db.common.ConfigurationProperties;
import com.cellulam.trans.msg.db.common.utils.YamlLoaderUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.trans.db.facade.ConsumerRegister;
import lombok.Data;
import lombok.Getter;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * @author eric.li
 * @date 2022-06-15 18:12
 */
@Getter
public class DynamicProperties {

    private final Map<String, Map<String, List<String>>> consumers;
    private final Map<String, List<String>> producerTransTypes;
    private final Map<String, List<ConsumerRegister>> registerMap;

    private DynamicProperties(Properties properties){
        this.consumers = properties.getTransMsg().getDynamic().getMessage();
        this.producerTransTypes = Maps.newHashMap();
        this.registerMap = Maps.newHashMap();
        this.init();
    }

    private void init() {
        for (String producer : this.consumers.keySet()) {
            Map<String, List<String>> transTypes = this.consumers.get(producer);
            producerTransTypes.put(producer, Lists.newArrayList());

            for (String transType : transTypes.keySet()) {
                this.producerTransTypes.get(producer).add(transType);

                List<String> consumers = transTypes.get(transType);
                for (String consumer : consumers) {
                    if (!registerMap.containsKey(consumer)) {
                        registerMap.put(consumer, Lists.newArrayList());
                    }
                    ConsumerRegister consumerRegister = new ConsumerRegister();
                    consumerRegister.setConsumer(consumer);
                    consumerRegister.setProducer(producer);
                    consumerRegister.setTransType(transType);

                    registerMap.get(consumer).add(consumerRegister);
                }
            }
        }
    }

    public static DynamicProperties loadFromResource(InputStream resourceStream) {
        Properties properties = YamlLoaderUtils.loadAs(resourceStream, Properties.class);

        return new DynamicProperties(properties);
    }

    @Data
    public final static class Properties extends ConfigurationProperties<Dynamic> {

    }

    @Data
    public final static class Dynamic {
        private Message dynamic;
    }

    @Data
    public final static class Message {
        private Map<String, Map<String, List<String>>> message;
    }
}
