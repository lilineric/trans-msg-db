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
    public void send(String transType, String message) {
        log.info("{} message send to RabbitMQ: {}", transType, message);
    }

    @Override
    public void send(String transType, byte[] message) {
        log.info("{} message send to RabbitMQ: {}", transType, message);
    }

    @Override
    public void ack(String transType, String transId) {
        log.info("{} ACK: {}", transId, transId);
    }
}
