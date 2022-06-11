package com.cellulam.trans.msg.db.core.message;

import com.cellulam.trans.msg.db.core.message.factories.MessageSenderFactory;
import com.cellulam.trans.msg.db.core.message.model.TransMessage;
import com.cellulam.trans.msg.db.core.message.model.TransMessageHeader;

import java.io.Serializable;
import java.util.UUID;

/**
 * @author eric.li
 * @date 2022-06-11 19:51
 */
public class TransMessageSender {
    private MessageSender messageSender;

    public TransMessageSender(String type) {
        this.messageSender = MessageSenderFactory.getInstance(type);
    }

    public <T extends Serializable> String send(T body) {
        TransMessage<T> message = new TransMessage<>();

        TransMessageHeader header = new TransMessageHeader();
        header.setTransId(UUID.randomUUID().toString());

        message.setHeader(header);
        message.setBody(body);

        return this.messageSender.send(message);
    }
}
