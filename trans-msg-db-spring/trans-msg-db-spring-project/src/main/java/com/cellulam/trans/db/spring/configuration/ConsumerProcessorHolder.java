package com.cellulam.trans.db.spring.configuration;

import com.google.common.collect.Lists;
import com.trans.db.facade.TransMessageProcessor;

import java.util.List;

/**
 *
 */
public class ConsumerProcessorHolder {

    public static final ConsumerProcessorHolder holder = new ConsumerProcessorHolder();

    private List<TransMessageProcessor<?>> processors = Lists.newArrayList();

    private ConsumerProcessorHolder() {

    }

    public void addMessageProcessors(TransMessageProcessor<?> processor) {
        this.processors.add(processor);
    }

    public List<TransMessageProcessor<?>> getProcessors() {
        return this.processors;
    }
}
