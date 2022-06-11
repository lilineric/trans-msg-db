package com.cellulam.trans.msg.db.spring.rabbitmq.message;

import com.cellulam.trans.msg.db.core.message.MessageProcessor;
import com.cellulam.trans.msg.db.core.message.MessageSender;
import com.cellulam.trans.msg.db.core.message.spi.MessageProviderSPI;
import lombok.extern.slf4j.Slf4j;

/**
 * @author eric.li
 * @date 2022-06-11 19:30
 */
@Slf4j
public class RabbitMQMessageProvider implements MessageProviderSPI {
    private MessageProcessor messageProcessor;

    private RabbitMQSender messageSender;

    public RabbitMQMessageProvider() {
        this.messageSender = new RabbitMQSender();
    }

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
        //TODO listen MQ success and invoke messageProcessor
    }

    @Override
    public String getType() {
        return "RabbitMQ";
    }
}
