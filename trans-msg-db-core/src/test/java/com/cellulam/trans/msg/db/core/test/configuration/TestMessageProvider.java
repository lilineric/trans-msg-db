package com.cellulam.trans.msg.db.core.test.configuration;

import com.cellulam.trans.msg.db.core.message.MessageProcessor;
import com.cellulam.trans.msg.db.core.message.MessageSender;
import com.cellulam.trans.msg.db.core.message.model.TransMessage;
import com.cellulam.trans.msg.db.core.message.model.TransMessageHeader;
import com.cellulam.trans.msg.db.core.message.spi.MessageProviderSPI;

import java.util.UUID;

/**
 * @author eric.li
 * @date 2022-06-12 01:57
 */
public class TestMessageProvider implements MessageProviderSPI {
    private MessageProcessor messageProcessor;

    @Override
    public void registerMessageProcessor(MessageProcessor processor) {
        this.messageProcessor = processor;
    }

    @Override
    public MessageSender getMessageSender() {
        return message -> {
            System.out.println("test send " + message);
            return UUID.randomUUID().toString();
        };
    }

    @Override
    public void start() {

        //TODO listen MQ success and invoke messageProcessor
        TransMessage message = new TransMessage();

        TransMessageHeader header = new TransMessageHeader();
        header.setTransId(UUID.randomUUID().toString());

        message.setHeader(header);
        message.setBody("test process message");
        this.messageProcessor.process(message);
    }

    @Override
    public String getType() {
        return "test";
    }
}
