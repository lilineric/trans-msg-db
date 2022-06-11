package com.cellulam.trans.msg.db.spring.rabbitmq.message;

import com.cellulam.trans.msg.db.core.message.MessageSender;
import com.cellulam.trans.msg.db.core.message.model.TransMessage;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

/**
 * @author eric.li
 * @date 2022-06-11 19:46
 */
@Slf4j
public class RabbitMQSender implements MessageSender {

    @Override
    public <T extends Serializable> String send(TransMessage<T> message) {
        log.info("Message send to RabbitMQ: {}", message);
        return message.getHeader().getTransId();
    }
}
