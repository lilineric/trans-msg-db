package com.cellulam.trans.msg.db.core.conf;

import com.cellulam.trans.msg.db.core.context.TransContext;
import com.cellulam.trans.msg.db.core.factories.MessageProviderFactory;
import com.cellulam.trans.msg.db.core.message.ConsumerProcessorWrap;
import com.cellulam.trans.msg.db.core.message.MessageConsumerReceiveProcessor;
import com.cellulam.trans.msg.db.core.message.MessageProducerReceiveProcessor;
import com.cellulam.trans.msg.db.core.recovery.MessageRecover;
import com.cellulam.trans.msg.db.core.utils.ClassUtil;
import com.cellulam.trans.msg.db.spi.MessageProviderSPI;
import com.google.common.collect.Maps;
import com.trans.db.facade.TransMessageProcessor;
import org.apache.commons.collections4.MapUtils;

import java.io.Serializable;
import java.util.Map;

/**
 * initializer
 *
 * @author eric.li
 * @date 2022-06-12 00:12
 */
public abstract class TransMsgInitializer {

    private static Map<String, ConsumerProcessorWrap<? extends Serializable>> consumerProcessors;

    /**
     * init
     *
     * @param configuration
     */
    public static void init(TransConfiguration configuration) {
        consumerProcessors = Maps.newHashMap();

        TransContext.init(configuration);
    }

    public static <T extends Serializable> void registerConsumerProcessor(TransMessageProcessor<T> processor) {
        ConsumerProcessorWrap<T> processorWrap = ConsumerProcessorWrap.<T>builder()
                .producer(processor.getProducer())
                .transType(processor.getTransType())
                .bodyClass(ClassUtil.getSuperClassGenericType(processor.getClass(), 0))
                .processor(processor)
                .build();
        consumerProcessors.put(processorWrap.getKey(), processorWrap);
    }

    public static void start() {
        // start message provider
        MessageProviderSPI messageProvider = MessageProviderFactory.getInstance(TransContext.getConfiguration().getMessageProviderType());

        if (MapUtils.isNotEmpty(consumerProcessors)) {
            messageProvider.registerMessageConsumerProcessor(new MessageConsumerReceiveProcessor(consumerProcessors));
        }
        messageProvider.registerMessageProducerProcessor(new MessageProducerReceiveProcessor());
        messageProvider.start();

        // start message recover
        new MessageRecover().start();
    }
}
