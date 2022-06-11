package com.cellulam.trans.msg.db.core.message.factories;

import com.cellulam.trans.msg.db.core.message.MessageSender;

/**
 * message sender factory
 *
 * @author eric.li
 * @date 2022-06-11 19:40
 */
public abstract class MessageSenderFactory {
    public static MessageSender getInstance(String type) {
        return MessageProviderFactory.getInstance(type).getMessageSender();
    }
}
