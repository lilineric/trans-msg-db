package com.cellulam.trans.db.spring.configuration;

import com.cellulam.trans.msg.db.core.conf.TransConfiguration;
import com.cellulam.trans.msg.db.core.conf.TransMsgInitializer;
import com.trans.db.facade.TransMessageProcessor;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * TransMsgInitialize
 */
@Component
public class ConsumerInitializer implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(ConsumerInitializer.class);

    @Autowired
    private TransConfiguration transConfiguration;

    @Override
    public void run(String... args) throws Exception {
        TransMsgInitializer.init(this.transConfiguration);
        List<TransMessageProcessor<?>> processors = ConsumerProcessorHolder.holder.getProcessors();

        logger.info("consumer processor initializer and processor size is {}", processors.size());
        if (CollectionUtils.isEmpty(processors)) {
            return;
        }

        for (TransMessageProcessor<?> processor : processors) {
            TransMsgInitializer.registerConsumerProcessor(processor);
        }

        TransMsgInitializer.start();
        logger.info("TransMsgInitializer start");
    }
}
