package com.cellulam.trans.msg.db.core.test.spi;

import com.cellulam.trans.msg.db.spi.MessageProviderSPI;
import com.cellulam.trans.msg.db.spi.contract.MessageProcessor;
import com.cellulam.trans.msg.db.spi.contract.MessageSender;

/**
 * @author eric.li
 * @date 2022-06-12 01:57
 */
public class TestMessageProviderSPI implements MessageProviderSPI {
    private MessageProcessor messageConsumerProcessor;
    private MessageProcessor messageProducerProcessor;
    private MessageSender messageConsumerSender = new TestMessageSender();
    private MessageSender messageProducerSender = new TestMessageSender();

    @Override
    public void registerMessageConsumerProcessor(MessageProcessor processor) {
        this.messageConsumerProcessor = processor;
    }

    @Override
    public void registerMessageProducerProcessor(MessageProcessor processor) {
        this.messageProducerProcessor = processor;
    }

    @Override
    public MessageSender getProducerMsgSender() {
        return this.messageProducerSender;
    }

    @Override
    public MessageSender getConsumerMsgSender() {
        return this.messageConsumerSender;
    }

    @Override
    public void start() {
        this.messageConsumerProcessor.process("test msg");
        this.messageProducerProcessor.process("test ack msg");
    }

    @Override
    public String getType() {
        return "test";
    }
}
