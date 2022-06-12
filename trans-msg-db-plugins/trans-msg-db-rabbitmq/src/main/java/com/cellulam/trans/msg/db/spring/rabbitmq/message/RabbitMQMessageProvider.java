package com.cellulam.trans.msg.db.spring.rabbitmq.message;

import com.cellulam.trans.msg.db.core.exceptions.TransMessageConfigurationException;
import com.cellulam.trans.msg.db.core.message.MessageProcessor;
import com.cellulam.trans.msg.db.core.message.MessageSender;
import com.cellulam.trans.msg.db.core.spi.MessageProviderSPI;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

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
        this.checkStatus();

        //TODO listen MQ success and invoke messageProcessor
        this.messageProcessor.process("test process message");

        //TODO listen ACK message and invoke ack
        this.messageProcessor.receiveACK("order-success", UUID.randomUUID().toString());
    }

    private void checkStatus() {
        if (this.messageProcessor == null) {
            throw new TransMessageConfigurationException("The messageProcessor is unregistered.");
        }
    }

    @Override
    public String getType() {
        return "RabbitMQ";
    }
}
