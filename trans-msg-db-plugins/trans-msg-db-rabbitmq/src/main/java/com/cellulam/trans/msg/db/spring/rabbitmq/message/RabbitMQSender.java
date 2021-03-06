package com.cellulam.trans.msg.db.spring.rabbitmq.message;

import com.cellulam.trans.msg.db.spi.contract.MessageSender;
import lombok.extern.slf4j.Slf4j;

/**
 * @author eric.li
 * @date 2022-06-11 19:46
 */
@Slf4j
public class RabbitMQSender implements MessageSender {

    @Override
    public void send(String rootSource, String transType, String message) {
        log.info("{} message send to RabbitMQ: {}", transType, message);
    }
}
