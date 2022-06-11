package com.cellulam.trans.msg.db.core.message;

import com.cellulam.trans.msg.db.core.coordinator.TransCoordinator;
import com.cellulam.trans.msg.db.core.message.factories.MessageSenderFactory;

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
        String transId = UUID.randomUUID().toString();
        //TODO insert message_trans to DB
        TransCoordinator.instance.asyncCommit(messageSender, transId, body);
        return transId;
    }
}
