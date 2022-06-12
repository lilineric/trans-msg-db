package com.cellulam.trans.msg.db.core.test.spi;

import com.cellulam.trans.msg.db.core.message.MessageProcessor;
import com.cellulam.trans.msg.db.core.message.MessageSender;
import com.cellulam.trans.msg.db.core.spi.MessageProviderSPI;

/**
 * @author eric.li
 * @date 2022-06-12 01:57
 */
public class TestMessageProviderSPI implements MessageProviderSPI {
    private MessageProcessor messageProcessor;
    private MessageSender messageSender = new TestMessageSender();

    @Override
    public void registerMessageProcessor(MessageProcessor processor) {
        this.messageProcessor = processor;
    }

    @Override
    public MessageSender getMessageSender() {
        return this.messageSender;
    }

    @Override
    public void start() {
        this.messageProcessor.process("test");
    }

    @Override
    public String getType() {
        return "test";
    }
}
