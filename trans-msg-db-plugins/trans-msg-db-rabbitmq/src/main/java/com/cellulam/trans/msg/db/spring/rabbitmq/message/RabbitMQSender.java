package com.cellulam.trans.msg.db.spring.rabbitmq.message;

import com.cellulam.trans.msg.db.core.message.MessageSender;
import lombok.extern.slf4j.Slf4j;

/**
 * @author eric.li
 * @date 2022-06-11 19:46
 */
@Slf4j
public class RabbitMQSender implements MessageSender {

    @Override
    public void send(String message) {
        log.info("Message send to RabbitMQ: {}", message);
    }

    @Override
    public void send(byte[] message) {
        log.info("Message send to RabbitMQ: {}", message);
    }
}
